package Model;

import java.awt.*;

public class modShip extends Point {
    public static final boolean SHIP_ORIENTATION_VERT = true;
    public static final boolean SHIP_ORIENTATION_HORZ = false;
    private boolean bOrientation;
    public static final byte SHIP_TYPE_1 = 1;
    public static final byte SHIP_TYPE_2 = 2;
    public static final byte SHIP_TYPE_3 = 3;
    public static final byte SHIP_TYPE_4 = 4;
    private byte btType;
    public static final byte SHIP_STATE_UNDEF = 0;
    public static final byte SHIP_STATE_HEALTHY = 1;
    public static final byte SHIP_STATE_INJURED = 2;
    public static final byte SHIP_STATE_KILLED = 3;
    private byte btState;

    public modShip( int X, int Y, boolean bOrientation, byte btType ) {
        super(X,Y);
        this.bOrientation = bOrientation;
        this.btType = btType;
        this.btState = SHIP_STATE_HEALTHY;
    }

    public boolean getOrientation() {
        return this.bOrientation;
    }

    public byte getType() {
        return this.btType;
    }

    public void setState( byte btState ) {
        this.btState = btState;
    }

    public byte getState() {
        return this.btState;
    }

}
