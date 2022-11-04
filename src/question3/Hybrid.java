package question3;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;


public class Hybrid {

    public static int DATA_LENGTH = 102;
    public static int KEY_LENGTH = 128;
    public static void main(String[] args) {
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
            String decryptedMessage = bob.AESDecryption(encryptedMessage, cipherKeyInBigInt);
            System.out.println("decrypted Message is " + decryptedMessage.substring(0,32));

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
