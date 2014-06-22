/*
 * Do whatever you want with it.
 */
package ecc.model;

import ecc.Point;

/**
 *
 * @author Olayinka olayinka.sf@gmail.com
 */
public class DiffieHellmanModel {

    String xA;
    String xB;
    private Point yA;
    private Point yB;

    public String getxA() {
        return xA;
    }

    public DiffieHellmanModel setxA(String xA) {
        this.xA = xA;
        return this;
    }

    public String getxB() {
        return xB;
    }

    public DiffieHellmanModel setxB(String xB) {
        this.xB = xB;
        return this;
    }

    public DiffieHellmanModel setyA(Point yA) {
        this.yA = yA;
        return this;
    }

    public Point getyA() {
        return yA;
    }

    public Point getyB() {
        return yB;
    }

    public DiffieHellmanModel setyB(Point key) {
        this.yB = key;
        return this;
    }

}
