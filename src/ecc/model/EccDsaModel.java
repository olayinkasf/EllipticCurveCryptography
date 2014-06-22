/*
 * Do whatever you want with it.
 */
package ecc.model;

import ecc.EllipticCurve;
import static ecc.EllipticCurve.DEF_ORDER;
import ecc.Point;
import ecc.Utils;
import ecc.view.Home;
import java.io.File;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Olayinka olayinka.sf@gmail.com
 */
public class EccDsaModel {

    String xA;
    String s1;
    String s2;
    Point yA;
    String kUnique;
    String u1, u2, s;
    private File file;

    public void validate() throws Exception {
        BigInteger inv = new BigInteger(s2).modInverse(EllipticCurve.DEF_ORDER);
        BigInteger u1_ = inv.multiply(new BigInteger(Utils.getMD5Checksum(file.getAbsolutePath())))
                .mod(EllipticCurve.DEF_ORDER);
        BigInteger u2_ = inv.multiply(new BigInteger(s1))
                .mod(EllipticCurve.DEF_ORDER);
        Point p = Home.ECC_CURVE.add(
                Home.ECC_CURVE.pow(Home.BASE, u1_),
                Home.ECC_CURVE.pow(yA, u2_)
        );

        u1 = u1_.toString();
        u2 = u2_.toString();
        s = p.getX().toString();
    }

    public String getS1() {
        return s1;
    }

    public void sign() {
        try {
            s1 = Home.ECC_CURVE
                    .pow(Home.BASE, new BigInteger(kUnique))
                    .getX().mod(DEF_ORDER).toString();
        } catch (Exception ex) {
            Logger.getLogger(EccDsaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            s2 = new BigInteger(kUnique)
                    .modInverse(EllipticCurve.DEF_ORDER)
                    .multiply(
                            new BigInteger(Utils.getMD5Checksum(file.getAbsolutePath()))
                            .add(new BigInteger(xA).multiply(new BigInteger(s1)))
                    ).mod(EllipticCurve.DEF_ORDER)
                    .toString();
        } catch (Exception ex) {
            Logger.getLogger(EccDsaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getU1() {
        return u1;
    }

    public String getU2() {
        return u2;
    }

    public String getS() {
        return s;
    }

    public String getS2() {
        return s2;
    }

    public String getxA() {
        return xA;
    }

    public EccDsaModel setxA(String xA) {
        this.xA = xA;
        return this;
    }

    public Point getyA() {
        return yA;
    }

    public EccDsaModel setyA(Point yA) {
        this.yA = yA;
        return this;
    }

    public String getkUnique() {
        return kUnique;
    }

    public EccDsaModel setkUnique(String kUnique) {
        this.kUnique = kUnique;
        return this;
    }

    public EccDsaModel setFile(File selectedFile) {
        if (selectedFile == null) {
            return this;
        }
        this.file = selectedFile;
        return this;
    }

    public File getFile() {
        return file;
    }

}
