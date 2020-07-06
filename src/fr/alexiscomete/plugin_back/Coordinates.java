package fr.alexiscomete.plugin_back;

import java.io.Serializable;

public class Coordinates implements Serializable {

    private double x = 0, y = 0, z = 0;

    public Coordinates() {
    }

    public Coordinates(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getDist(double x, double y, double z) {
        return Math.sqrt(Math.pow(this.x-x, 2) + Math.pow(this.z-z, 2) + Math.pow(this.y-y, 2));
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void setXYZ(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
