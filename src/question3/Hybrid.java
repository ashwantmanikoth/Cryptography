package question3;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;


public class Hybrid {

    public static int DATA_LENGTH = 102;
    public static int KEY_LENGTH = 128;
    public Cipher encrypter;
    public Cipher decrypter;


    public static void main(String args[]) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(KEY_LENGTH);
            //key generation
            SecretKey secretKey = keyGenerator.generateKey();

            //key object to bigInteger
            BigInteger secretKeyInBigInt = new BigInteger(secretKey.getEncoded()).abs();

            secretKey = new SecretKeySpec(secretKeyInBigInt.toByteArray(),"AES");

            System.out.println("\nGenerated Secret Key in HEX format : " + secretKeyInBigInt.toString(16));

            char[] constructMessage = new char[DATA_LENGTH];
            Arrays.fill(constructMessage, 'f');

            String bigMessage = new String(constructMessage);

            //encryption is done here
            /**
             *
             * Encryption of the message is done in BOB class
             */
            Bob bob = new Bob();
            String encryptedMessage = bob.AESEncryption(bigMessage, secretKey);

            /**
             * Alice class initialises the RSA privateKey public key
             */
            Alice alice = new Alice();
            alice.init();
            /**
             * encrypting the secret Key
             */
            BigInteger cipherKeyInBigInt = alice.encryptKey(secretKeyInBigInt);

            System.out.println("Encrypted Key->" + cipherKeyInBigInt.toString().substring(0,32) + "\nAnd the Encrypted Message (32 bit)->" + encryptedMessage.substring(0, 32));

            System.out.println("Transferring Encrypted Secret Key and Encrypted Message to bob");


            /**
             * Decryption of the message with two encrypted data
             * @param encryptedMessage
             * @param cipherKeyInBigInt
             */
            String decryptedMessage = bob.AESDecryption(encryptedMessage, cipherKeyInBigInt).substring(0,10);
            System.out.println("decrypted Message is " + decryptedMessage.substring(0,32));

//            //making key unsigned for AES
//            secretKey = new SecretKeySpec(secretKeyInBigInt.toByteArray(), "AES");
//
//            Hybrid hybrid = new Hybrid();
//
//            hybrid.alice();
//
//            String cipherText = hybrid.AESEncryption(bigMessage, secretKey);
//
//            System.out.println("Message Encryption completed:" + cipherText.substring(0, 32));
//
//            //now encrypting secretKey
//
//            RSAEncryption rsaEncryption = new RSAEncryption();
//
//            BigInteger cipherBigInteger = rsaEncryption.encrypt(secretKeyInBigInt);
//
//            System.out.println("Key encryption using RSA complete:" + cipherBigInteger);
//
//            BigInteger n = rsaEncryption.getN();//random number
//
//            BigInteger d = rsaEncryption.getD();//rsa D
//
//            System.out.println("private key to decrypt is N:" + n + " D is:" + d);
//
//            BigInteger decryptedBigInt = rsaEncryption.decrypt(cipherBigInteger, rsaEncryption.getN(), rsaEncryption.getD());
//
//            SecretKey decryptedSecretKey = new SecretKeySpec(decryptedBigInt.toByteArray(), 0, decryptedBigInt.toByteArray().length, "AES");
//
//            hybrid.AESDecryption(cipherText, decryptedSecretKey);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
