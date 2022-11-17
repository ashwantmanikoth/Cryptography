package assignment2.question1;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Scanner;

public class DigitalSignature {
    public static final BigInteger p = new BigInteger("50702342087986984684596540672785294493370824085308498450535565701730450879745310594069460940052367603038103747343106687981163754506284021184158903198888031001641800021787453760919626851704381009545624331468658731255109995186698602388616345118779571212089090418972317301933821327897539692633740906524461904910061687459642285855052275274576089050579224477511686171168825003847462222895619169935317974865296291598100558751976216418469984937110507061979400971905781410388336458908816885758419125375047408388601985300884500733923194700051030733653434466714943605845143519933901592158295809020513235827728686129856549511535000228593790299010401739984240789015389649972633253273119008010971111107028536093543116304613269438082468960788836139999390141570158208410234733780007345264440946888072018632119778442194822690635460883177965078378404035306423001560546174260935441728479454887884057082481520089810271912227350884752023760663");
    public static final BigInteger q = new BigInteger("63762351364972653564641699529205510489263266834182771617563631363277932854227");
    public static final BigInteger g = new BigInteger("2");
    BigInteger h;
    BigInteger x;
    BigInteger r;
    BigInteger s;
    BigInteger y;
    static BigInteger input;
    byte[] array;

    public static void main(String[] args) {
        DigitalSignature digitalSignature = new DigitalSignature();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter message to be digitally signed");
        input = scanner.nextBigInteger();
        digitalSignature.keyGen();
        digitalSignature.signing();
        if (digitalSignature.verify()) {
            System.out.println("Digital signature verified");
        } else {
            System.out.println("Digital signature failed");
        }
    }

    public Boolean verify() {
        BigInteger w = s.modInverse(q);
        BigInteger u1 = w.multiply(new BigInteger(array)).mod(q);
        BigInteger u2 = r.multiply(w).mod(q);

        BigInteger v = ((h.modPow(u1, p).multiply(y.modPow(u2, p))).mod(p)).mod(q);
        System.out.println("V = " + v);
        if (v.equals(r)) {
            return true;
        }
        return false;
    }

    private BigInteger signing() {
        BigInteger k = new BigInteger(q.bitLength(), new SecureRandom());
        if (k.compareTo(new BigInteger("2")) < 0) {
            k = k.add(new BigInteger("2"));
        }
        if (k.compareTo(q) >= 0) {
            k = k.mod(q.subtract(new BigInteger("2")).add(new BigInteger("2")));
        }
        r = (h.modPow(k, p)).mod(q);
        System.out.println("r =" + r);
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

    public BigInteger keyGen() {
        h = g.modPow((p.subtract(BigInteger.ONE)).divide(q), p);
//        h = g.multiply(p.subtract(BigInteger.ONE).divide(q));
        System.out.println("h" + h);
        x = new BigInteger(q.bitLength(), new SecureRandom());
        if (x.compareTo(new BigInteger("2")) < 0) {
            x = x.add(new BigInteger("2"));
        }
        if (x.compareTo(q) >= 0) {
            x = x.mod(q.subtract(new BigInteger("2")).add(new BigInteger("2")));
        }
        System.out.println("DSA signing key " + x);
        y = h.modPow(x, p);
        System.out.println(y);
        return null;
    }
}
