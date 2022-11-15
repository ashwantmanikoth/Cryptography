package assignment2.question1;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Scanner;

public class DigitalSignature {
//    public static final BigInteger p = new BigInteger("5809605995369958062791915965639201402176612226902900533702900882779736177890990861472" +
//            "0947744773395811473734101856463783280437298007504700982109244878669350591643715881680" +
//            "4754094398164451663275506750162643455639819318662899007124866081936120511979369398543" +
//            "3297036118232914410171876807536457391277857011849897410207519105333355801121109356897" +
//            "4594262718454713979526759594407934930716283941227805101246184882326024646498768504588" +
//            "6124578424092925842628769970531258450962541951346360515542801716571446536309402160929" +
//            "0561084025893662561222573202082865797821865270991145082200656978177192827024538990239" +
//            "9691755461907706456858934380117144304264093386763147435711545371420315730042764287014" +
//            "3303638180170530865983075119035294602548205993130657100472736247968841557470259694645" +
//            "7770284148435989129632853918392117997472632693078113129886487399347796982772784615865" +
//            "232621289656944284216824611318709764535152507354116344703769998514148343807");
    public static final BigInteger p = new BigInteger("641");
//    public static final BigInteger q = new BigInteger("63762351364972653564641699529205510489263266834182771617563631363277932854227");
public static final BigInteger q = new BigInteger("19");

    public static final BigInteger g = new BigInteger("2");
    BigInteger h;
    BigInteger x;
    BigInteger r;
    BigInteger s;
    BigInteger y;
    static BigInteger input;
    byte[] array;

    public static void main(String[] args){DigitalSignature digitalSignature = new DigitalSignature();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter message to be digitally signed");
        input  = scanner.nextBigInteger();
        digitalSignature.keyGen();
        digitalSignature.signing();
        if(digitalSignature.verify()) {
            System.out.println("Digital signature verified");
        }
        else {
            System.out.println("Digital signature failed");
        }
    }

    public Boolean verify(){
        BigInteger w = s.modInverse(q);
        BigInteger u1 = w.multiply(new BigInteger(array)).mod(q);
        BigInteger u2 = r.multiply(w).mod(q);

        BigInteger v = ((h.modPow(u1,p).multiply(y.modPow(u2,p))).mod(p)).mod(q);
        System.out.println("V = "+v);
        if (v.equals(r)){
            return true;
        }
        return false;
    }
    private BigInteger signing() {
        BigInteger k = new BigInteger(q.bitLength(),new SecureRandom());
        if(k.compareTo(new BigInteger("2"))<0){
            k = k.add(new BigInteger("2"));
        }
        if (k.compareTo(q)>=0){
            k = k.mod(q.subtract(new BigInteger("2")).add(new BigInteger("2")));
        }
        r = (h.modPow(k,p)).mod(q);
        System.out.println("r ="+r);
        BigInteger kDash = k.modInverse(q);
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            array = md.digest(input.toByteArray());
            s = kDash.multiply((new BigInteger(array).add(r.multiply(x))).mod(q));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public BigInteger keyGen(){
        h = g.multiply(p.subtract(BigInteger.ONE).divide(q));
        System.out.println(h);
        x = new BigInteger(q.bitLength(),new SecureRandom());
        if(x.compareTo(new BigInteger("2"))<0){
            x = x.add(new BigInteger("2"));
        }
        if (x.compareTo(q)>=0){
            x = x.mod(q.subtract(new BigInteger("2")).add(new BigInteger("2")));
        }
        System.out.println("DSA signing key " +x);
        y = h.modPow(x,p);
        System.out.println(y);
        return null;
    }
}
