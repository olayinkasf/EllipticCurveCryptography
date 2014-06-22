/*
 * Do whatever you want with it.
 */
package ecc;

import java.util.HashSet;

/**
 *
 * @author Olayinka olayinka.sf@gmail.com
 */
public class Main {

    public static void main(String[] args) throws Exception {
        HashSet<Point> set = new HashSet<>();
        EllipticCurve ecc = new EllipticCurve("23", "1", "4");
        for (Integer i = 0; i < 23; i++) {
            for (Integer j = 0; j < 23; j++) {
                Point p = new Point(i.toString(), j.toString());
                if (ecc.isOnCurve(p)) {
                    set.add(p);
                }
            }
        }
        System.out.print("[ IDENTITY, ");
        for (Point p : set) {
            System.out.print("[" + p.getX().toString() + ", " + p.getY() + "], ");
        }
        System.out.print("]");
    }
}
