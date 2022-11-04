package Question2;

import java.math.BigInteger;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int a;
        do {
            System.out.println("Enter your choice \n 1-Encrypt\n 2-Decrypt\n");
            Scanner scanner = new Scanner(System.in);
            a = scanner.nextInt();
            if (a == 1) {
                System.out.println("\nEnter the message which needs to be encrypted\n");
                BigInteger value;
                value = scanner.nextBigInteger();
                KeyGenCRT KeyGenCRT = new KeyGenCRT();
                System.out.println("e  is ->" + KeyGenCRT.E);
                BigInteger cipherText = KeyGenCRT.encrypt(value);
                System.out.println("\nThe Encrypted cipher is \n" + cipherText);
            } else if (a == 2) {
                System.out.println("\nEnter the cipher text which needs to be decrypted\n");
                BigInteger value;
                value = scanner.nextBigInteger();
                KeyGenCRT KeyGenCRT = new KeyGenCRT();
                System.out.println("e  is ->" + KeyGenCRT.E);
                BigInteger decryptedMessage = KeyGenCRT.decrypt(value);
                System.out.println("\nThe decrypted message is\n" + decryptedMessage);
            }
        } while (a != 3);
    }
}

