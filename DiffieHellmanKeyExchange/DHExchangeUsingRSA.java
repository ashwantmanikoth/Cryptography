package DiffieHellmanKeyExchange;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class DHExchangeUsingRSA {
    public static final BigInteger p = new BigInteger("50702342087986984684596540672785294493370824085308498450535565701730450879745310594069460940052367603038103747343106687981163754506284021184158903198888031001641800021787453760919626851704381009545624331468658731255109995186698602388616345118779571212089090418972317301933821327897539692633740906524461904910061687459642285855052275274576089050579224477511686171168825003847462222895619169935317974865296291598100558751976216418469984937110507061979400971905781410388336458908816885758419125375047408388601985300884500733923194700051030733653434466714943605845143519933901592158295809020513235827728686129856549511535000228593790299010401739984240789015389649972633253273119008010971111107028536093543116304613269438082468960788836139999390141570158208410234733780007345264440946888072018632119778442194822690635460883177965078378404035306423001560546174260935441728479454887884057082481520089810271912227350884752023760663");
    public static final BigInteger q = new BigInteger("63762351364972653564641699529205510489263266834182771617563631363277932854227");
    public static final BigInteger g = new BigInteger("2");

    public static BigInteger aliceID = new BigInteger("1");
    public static BigInteger bobID = new BigInteger("2");


    static BigInteger T, row, tagB, y, X, x;
    static BigInteger k, Y,skB,skA;
    static List vkA;
    static List vkB;


    public static void main(String args[]) {
        k = new BigInteger(q.bitLength(), new SecureRandom());
        DHExchangeUsingRSA dhExchange = new DHExchangeUsingRSA();

        List aliceKey = dhExchange.keyGen();
        vkA = (List) aliceKey.get(1);
        skA = (BigInteger) aliceKey.get(0);

        List bobKey = dhExchange.keyGen();
        vkB = (List) bobKey.get(1);
        skB = (BigInteger) bobKey.get(0);

        List<BigInteger> XandT = dhExchange.aliceGenerateSession();
        BigInteger X = XandT.get(0);
        BigInteger T = XandT.get(1);

        List returnBobParams = dhExchange.bobComputeTag(X, T);
        BigInteger Y = (BigInteger) returnBobParams.get(1);
        BigInteger tagBob = (BigInteger) returnBobParams.get(3);
        List signatureBob = (List) returnBobParams.get(4);


        List returnAliceParams = dhExchange.aliceComputeTag(T,Y,bobID,tagBob,signatureBob);
        if(returnAliceParams !=null) {
            BigInteger tagAlice = (BigInteger) returnAliceParams.get(2);
            List signatureAlice = (List) returnAliceParams.get(3);

            List bobParameters = (List) returnBobParams.get(5);

            dhExchange.bobVerify(tagAlice, signatureAlice, bobParameters);

        }
    }

    private void bobVerify(BigInteger tagAlice, List signatureAlice, List bobParameters) {
        BigInteger tagDash  = sha256(new BigInteger(bobParameters.get(1).toString()+T.toString()+aliceID.toString()));

        if (!tagDash.equals(tagAlice)){
            System.out.print("Tag and signature verification results by Bob: Failed" );

        }
        else if(verify(vkA,bobParameters.get(2).toString(),signatureAlice)) {
            System.out.print("Tag and signature verification results by Bob: Success" );
        }else {
            System.out.print("Tag and signature verification results by Bob: Failed" );
        }
    }

    private List aliceComputeTag(BigInteger T, BigInteger Y, BigInteger bobID, BigInteger tagBob, List signatureBob) {
        BigInteger Z = Y.modPow(x,q);
        BigInteger K = sha256(Z);
        String binary = K.toString(2);
        String k0 = binary.substring(0,binary.length()/2);
        String k1 = binary.substring(binary.length()/2);
        String m = X.toString()+T.toString()+Y.toString();

        BigInteger tagDash = sha256(new BigInteger(k1.toString() + T.toString() + bobID.toString()));
        assert tagDash != null;
        if(!tagDash.equals(tagBob)){
            System.out.print("Tag and signature verification results by Bob: Failed" );
            return null;
        }else if(verify(vkB,m,signatureBob)){
            List signatureAlice  = signing(skA,vkA,m);
            System.out.println("printing signature of Alice:"+signatureAlice);
            BigInteger tagAlice = sha256(new BigInteger(k1+T+aliceID.toString()));
            System.out.println("printing tag of Alice:"+tagAlice);

            List aliceParams = new ArrayList<>();
            aliceParams.add(T);
            aliceParams.add(aliceID);
            aliceParams.add(tagAlice);
            aliceParams.add(signatureAlice);
            aliceParams.add(k0);
            aliceParams.add(k1);
            System.out.println("Keys derived by Alice:" +k0+ " "+k1);
            return aliceParams;

        }else {
            return null;
        }
    }

    private boolean verify(List vkB, String m, List signatureBob) {
        BigInteger y = (BigInteger) vkB.get(0);
        BigInteger h = (BigInteger) vkB.get(1);
        BigInteger r = (BigInteger) signatureBob.get(0);
        BigInteger s = (BigInteger) signatureBob.get(1);
        BigInteger w = s.modInverse(q);
        BigInteger u1 = w.multiply(new BigInteger(m)).mod(q);
        BigInteger u2 = r.multiply(w).mod(q);
        BigInteger v = ((h.modPow(u1, p).multiply(y.modPow(u2, p))).mod(p)).mod(q);
        if (v.equals(r)) {
            return true;
        }
        return false;

    }

    private BigInteger sha256(BigInteger input){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte array[] = md.digest(input.toByteArray());
            return new BigInteger(array);

        } catch (Exception e) {
           return null;
        }
    }

    private List bobComputeTag(BigInteger X, BigInteger T) {
        BigInteger y = new BigInteger(q.bitLength(), new SecureRandom());
        BigInteger Y = g.modPow(y, q);
        BigInteger Z = X.modPow(y, q);
        BigInteger K = sha256(Z);
        String binary = K.toString(2);
        String k0 = binary.substring(0,binary.length()/2);
        String k1 = binary.substring(binary.length()/2);
        System.out.println("Keys K0 derived by Bob: "+k0);
        System.out.println("Keys K1 derived by Bob: "+k1);

        String m = X.toString()+T.toString()+Y.toString();

        List signatureBob = signing(skB,vkB,m);
        System.out.println("Printing Signature of Bob: "+signatureBob);

        BigInteger tagBob = sha256(new BigInteger(k1+T+bobID.toString()));
        System.out.println("Printing tag of Bob: "+tagBob);
        System.out.println("Tag and signature verification results by Alice:");

        List bobParams = new ArrayList<>();
        bobParams.add(k0);
        bobParams.add(k1);
        bobParams.add(m);
        List returnParams = new ArrayList<>();
        returnParams.add(T);
        returnParams.add(Y);
        returnParams.add(bobID);
        returnParams.add(tagBob);
        returnParams.add(signatureBob);
        returnParams.add(bobParams);

        return returnParams;
    }

    private List signing(BigInteger skB, List vkB, String m) {
        BigInteger x = skB;
        BigInteger y,h;
        y= (BigInteger) vkB.get(0);
        h = (BigInteger) vkB.get(1);
        BigInteger k = new BigInteger(q.bitLength(), new SecureRandom());
        if (k.compareTo(new BigInteger("2")) < 0) {
            k = k.add(new BigInteger("2"));
        }
        if (k.compareTo(q) >= 0) {
            k = k.mod(q.subtract(new BigInteger("2")).add(new BigInteger("2")));
        }
        BigInteger r = (h.modPow(k, p)).mod(q);
        BigInteger kDash = k.modInverse(q);
        BigInteger s = kDash.multiply((new BigInteger(m).add(r.multiply(x))).mod(q));
        List randS = new ArrayList<>();
        randS.add(r);
        randS.add(s);

        return randS;
    }

    private List<BigInteger> aliceGenerateSession() {
        X = g.modPow(x, q);
        System.out.println("DSA private key for Alice x" + x);
        System.out.println("DSA public key for Alice X" + x);
        T = new BigInteger(32, new SecureRandom());
        List<BigInteger> list = new ArrayList<>();
        list.add(X);
        list.add(T);
        return list;

    }

    public List keyGen() {
        BigInteger h = g.modPow((p.subtract(BigInteger.ONE)).divide(q), p);
        x = new BigInteger(q.bitLength(), new SecureRandom());
        if (x.compareTo(new BigInteger("2")) < 0) {
            x = x.add(new BigInteger("2"));
        }
        if (x.compareTo(q) >= 0) {
            x = x.mod(q.subtract(new BigInteger("2")).add(new BigInteger("2")));
        }
        BigInteger y = h.modPow(x, p);
        List list = new ArrayList<>();
        list.add(x);
        List vk = new ArrayList();
        vk.add(y);
        vk.add(h);
        list.add(vk);
        return list;
    }


}
