package HybridEncryptionAlgorithm;

import java.math.BigInteger;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Bob {
    public Cipher encrypter;
    public Cipher decrypter;

    public String AESEncryption(String message, SecretKey secretKey) {
        try {
            encrypter = Cipher.getInstance("AES");
            encrypter.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedData = encrypter.doFinal(message.getBytes());
            System.out.println("AES message encryption completed:" + Base64.getEncoder().encodeToString(encryptedData)+"\n");
            return Base64.getEncoder().encodeToString(encryptedData);
        } catch (Exception ex) {
            System.out.println(ex.getStackTrace());
        }

        return null;
    }
    public String AESDecryption(String data, BigInteger cipherKeyInBigInt) {
        try {
            Alice alice = new Alice();
            BigInteger decryptedKey = alice.decrypt(cipherKeyInBigInt);
            SecretKey decryptedSecretKey = new SecretKeySpec(decryptedKey.toByteArray(), "AES");
            System.out.println("\nEncrypted Secret Key is decrypted by Bob\n");
            decrypter = Cipher.getInstance("AES");
            decrypter.init(Cipher.DECRYPT_MODE, decryptedSecretKey);
            byte[] decryptedText = decrypter.doFinal(Base64.getDecoder().decode(data.getBytes()));
            return new String(decryptedText);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
