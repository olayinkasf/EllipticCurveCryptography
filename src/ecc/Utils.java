/*
 * Do whatever you want with it.
 */
package ecc;

import static ecc.EllipticCurve.DEF_PRIME;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Random;

/**
 *
 * @author Olayinka olayinka.sf@gmail.com
 */
public final class Utils {

    public static byte[] createChecksum(String filename) throws Exception {
        MessageDigest complete;
        try (InputStream fis = new FileInputStream(filename)) {
            byte[] buffer = new byte[1024];
            complete = MessageDigest.getInstance("MD5");
            int numRead;
            do {
                numRead = fis.read(buffer);
                if (numRead > 0) {
                    complete.update(buffer, 0, numRead);
                }
            } while (numRead != -1);
        }
        return complete.digest();
    }

    public static String getMD5Checksum(String filename) throws Exception {
        byte[] b = createChecksum(filename);
        String result = "";

        for (int i = 0; i < b.length; i++) {
            result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
        }
        return new BigInteger(result, 16).toString();
    }

    public static BigInteger randomBigInteger(BigInteger order) {
        Random rnd = new Random();
        int nlen = order.bitLength();
        BigInteger nm1 = order.subtract(BigInteger.ONE);
        BigInteger r, s;
        do {
            s = new BigInteger(nlen + 100, rnd);
            r = s.mod(order);
        } while (s.subtract(r).add(nm1).bitLength() >= nlen + 100);
        return r;
    }

}
