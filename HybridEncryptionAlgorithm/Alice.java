package HybridEncryptionAlgorithm;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class Alice {
    public static BigInteger E;

    public static BigInteger n;
    public static BigInteger phi;
    public static BigInteger d;

    BigInteger p1 = new BigInteger("1921191698199047261893632290862186398687698714631732117547745963615695356147500873387" +
            "0517275438245830106443145241548501528064000686696553079813968930084003413592173929258" +
            "2395455385590595228930014155403832377127878058572486689214755030290122100917986244014" +
            "9355132183673917029056934388514640273411971462276191887447398784922465882120349268369" +
            "2059569546468953937059529709368583742816455260753650612502430591087268113652659115398" +
            "868234585603351162620007030560547611");
    BigInteger q1 = new BigInteger("4940095716354775745252877534656042064535382750446981370244709505724199840335582190539" +
            "5551250978714023163401985077729384422721713135644084394023796644398582673187943364713" +
            "3156172718027729495774647121047372081483385288349817203215321259577825176996920811751" +
            "0756379548228165433329469393054349178035979985630084130180487031241256763672337355770" +
            "0882499622073341225199446003974972311496703259471182056856143760293363135470539860065" +
            "760306974196552067736902898897585691");



    public void init() {
        n = p1.multiply(q1);
        phi = (p1.subtract(BigInteger.ONE)).multiply(q1.subtract(BigInteger.ONE));
        if (E == null) {
            E = getGcd();//random generated and stored
        }
        d = E.modInverse(phi);
    }

    private BigInteger getGcd() {
        BigInteger counter = new BigInteger(phi.bitLength(), new SecureRandom());
        while (!phi.gcd(counter).equals(BigInteger.ONE)) {
            counter = new BigInteger(phi.bitLength(), new Random());
        }
        return counter;
    }

    public BigInteger encryptKey(BigInteger messageInBigInt) {
        BigInteger c;
        c = messageInBigInt.modPow(E, n);
        return c;
    }

    public BigInteger decrypt(BigInteger cipher){
        BigInteger m;
        m = cipher.modPow(d, n);
        return m;
    }

}
