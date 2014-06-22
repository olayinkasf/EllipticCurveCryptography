 /*
 * Do whatever you want with it.
 */
package ecc;

import java.math.BigInteger;

/**
 *
 * @author Folorunso Olayinka olayinka.sf@gmail.com
 */
public class EllipticCurve {

    public static final BigInteger ZERO = new BigInteger("0");
    public static final BigInteger ONE = new BigInteger("1");
    public static final BigInteger TWO = new BigInteger("2");
    public static final BigInteger THREE = new BigInteger("3");

    public static final Point IDENTITY = new Point(ZERO, ZERO);
    public static final BigInteger DEF_PRIME = new BigInteger("115792089210356248762697446949407573530086143415290314195533631308867097853951");
    public static final BigInteger DEF_A = THREE.negate();
    public static final BigInteger DEF_B = new BigInteger("5ac635d8aa3a93e7b3ebbd55769886bc651d06b0cc53b0f63bce3c3e27d2604b", 16);
    public static final BigInteger DEF_ORDER = new BigInteger("115792089210356248762697446949407573529996955224135760342422259061068512044369");
    public static final BigInteger DEF_GX = new BigInteger("6b17d1f2e12c4247f8bce6e563a440f277037d812deb33a0f4a13945d898c296", 16);
    public static final BigInteger DEF_GY = new BigInteger("4fe342e2fe1a7f9b8ee7eb4a7c0f9e162bce33576b315ececbb6406837bf51f5", 16);

    private final BigInteger prime;
    private final BigInteger a;
    private final BigInteger b;

    public EllipticCurve() throws Exception {
        this(DEF_PRIME, DEF_A, DEF_B);
    }

    public EllipticCurve(BigInteger prime, BigInteger a, BigInteger b) throws Exception {
        if (prime.signum() <= 0) {
            throw new Exception("Illegal value for prime.");
        }
        this.prime = prime;
        this.a = a.mod(prime);
        this.b = b.mod(prime);
    }

    public EllipticCurve(String prime, String a, String b) throws Exception {
        this(new BigInteger(prime),
                new BigInteger(a),
                new BigInteger(b)
        );
    }

    public Point add(Point p1, Point p2) {
        if (p1 == IDENTITY) {
            return p2;
        }
        if (p2 == IDENTITY) {
            return p1;
        }
        if (p1.equals(p2.getXReflection(prime))) {
            return IDENTITY;
        }

        BigInteger alphaN, alphaD, alpha;

        if (p1 == p2 || p1.equals(p2)) {
            alphaN = THREE.multiply(p1.getX().modPow(TWO, prime))
                    .add(a).mod(prime);
            alphaD = TWO.multiply(p1.getY()).mod(prime);
        } else {
            alphaD = p1.getX().subtract(p2.getX()).mod(prime);
            alphaN = p1.getY().subtract(p2.getY()).mod(prime);
        }

        alpha = alphaN.multiply(alphaD.modInverse(prime))
                .mod(prime);
        BigInteger rX = alpha
                .modPow(TWO, prime)
                .subtract(p1.getX()).mod(prime)
                .subtract(p2.getX()).mod(prime);
        BigInteger rY = alpha
                .multiply(p1.getX().subtract(rX)).mod(prime)
                .subtract(p1.getY()).mod(prime);
        return new Point(rX, rY);
    }

    public Point pow(Point p, BigInteger k) throws Exception {
        if (k.compareTo(ZERO) <= 0) {
            throw new Exception("Illegal value for k");
        }
        if (p == IDENTITY) {
            return IDENTITY;
        }
        if (k.equals(ONE)) {
            return p;
        }
        if (k.equals(TWO)) {
            return add(p, p);
        }
        if (k.mod(TWO).equals(ZERO)) {
            return pow(pow(p, k.divide(TWO)), TWO);
        } else {
            return add(pow(pow(p, k.divide(TWO)), TWO), p);
        }
    }

    public String imageOf(String x) {
        return imageOf(new BigInteger(x)).toString();
    }

    public BigInteger imageOf(BigInteger x) {
        return x.modPow(THREE, prime)
                .add(a.multiply(x)).mod(prime)
                .add(b).mod(prime);
    }

    boolean isOnCurve(Point p) {
        return p.getY().modPow(TWO, prime)
                .equals(imageOf(p.getX()));
    }

    public static void main(String[] args) throws Exception {
        EllipticCurve ecc = new EllipticCurve(DEF_PRIME, DEF_A, DEF_B);
        Point base = new Point(DEF_GX, DEF_GY);

        //verifier que l'ordre est correct
        System.out.println("ORDRE * PRIME =: " + DEF_ORDER.multiply(DEF_PRIME).mod(DEF_PRIME));

        //verifier que le point de base est bien un point de la courbe
        System.out.println("Image  de  G_X  =: \n" + ecc.imageOf(DEF_GX).toString(16));
        System.out.println("Valeur de G_Y^2 =: \n" + DEF_GY.modPow(TWO, DEF_PRIME).toString(16));

        Point res = ecc.pow(base, DEF_ORDER);

        System.out.println(res == EllipticCurve.IDENTITY);

    }

    public BigInteger getPrime() {
        return prime;
    }

}
