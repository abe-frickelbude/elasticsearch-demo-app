package de.frickelbude.es.sandbox.entity;

/**
 * Capacitors. The capacitance value is in [Farad], tolerance is specified in %.
 * 
 * @author Ibragim Kuliev
 *
 */
public class Capacitor extends PassiveDevice {

    public static final String TYPE = "capacitor";
    private float value;
    private int tolerance;

    public Capacitor() {
        super(TYPE);
    }

    public float getValue() {
        return value;
    }

    public void setValue(final float value) {
        this.value = value;
    }

    public int getTolerance() {
        return tolerance;
    }

    public void setTolerance(final int tolerance) {
        this.tolerance = tolerance;
    }
}
