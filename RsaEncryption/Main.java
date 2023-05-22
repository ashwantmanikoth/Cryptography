package RsaEncryption;

import java.math.BigInteger;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        int inputChoice;
        do {
            System.out.println("Enter your choice \n 1-Encrypt\n 2-Decrypt\n 3-Exit");
            Scanner scanner = new Scanner(System.in);
            inputChoice = scanner.nextInt();

            if (inputChoice == 1) {
                System.out.println("\nEnter the numerical message which needs to be encrypted\n");
                BigInteger value;
                value = scanner.nextBigInteger();
                KeyGen keyGen = new KeyGen();
                System.out.println("e  is ->" + KeyGen.E);
                BigInteger cipherText = keyGen.encrypt(value);
                System.out.println("\nThe Encrypted cipher is \n" + cipherText);
            } else if (inputChoice == 2) {
                System.out.println("\nEnter the cipher text which needs to be decrypted\n");
                BigInteger cipherText;
                cipherText = scanner.nextBigInteger();
                KeyGen keyGen = new KeyGen();
                System.out.println("e  is ->" + KeyGen.E);
                BigInteger decryptedMessage = keyGen.decrypt(cipherText);
                System.out.println("\nThe decrypted numerical 131message is\n" + decryptedMessage);
            }
        } while (inputChoice != 3);
    }
}

