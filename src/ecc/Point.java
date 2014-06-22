/*
 * Do whatever you want with it.
 */
package ecc;

import java.math.BigInteger;
import java.util.Objects;

/**
 *
 * @author Olayinka olayinka.sf@gmail.com
 */
public class Point {

    private final BigInteger x;
    private final BigInteger y;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.x);
        hash = 59 * hash + Objects.hashCode(this.y);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Point other = (Point) obj;

        return x.equals(other.x) && y.equals(other.y);
    }

    public Point(String x, String y) {
        this.x = new BigInteger(x);
        this.y = new BigInteger(y);
    }

    public Point(BigInteger x, BigInteger y) {
        this(x.toString(), y.toString());
    }

    public BigInteger getX() {
        return x;
    }

    public BigInteger getY() {
        return y;
    }

    Point getXReflection(BigInteger prime) {
        return new Point(x, y.negate().mod(prime));
    }

    @Override
    public String toString() {
        return "Point{" + "x=" + x.toString() + ", y=" + y.toString() + '}';
    }

}
