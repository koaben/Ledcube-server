package nl.first8.ledcube;

import nl.first8.ledcube.gui.Coordinate3D;

/**
 * A CubeOutput can display a Cube.
 */
public interface CubeOutput extends Cube {

    String getName();
    
	void init() throws CubeException;

	void flush();

	default void setPixel(Coordinate3D coord, boolean value) {
		setPixel(coord.getX(), coord.getY(), coord.getZ(), value);
	}

	default void setCube(boolean[][][] cube) {
	    forEachPixel( c-> {
	        setPixel(c.getX(), c.getY(), c.getZ(),cube[c.getX()][c.getY()][c.getZ()]);
	        return null;
	    });
	}
	
	void setPixel(int x, int y, int z, boolean value);
	
    default void clear() {
        forEachPixel( c-> {
            setPixel(c.getX(), c.getY(), c.getZ(),false);
            return null;
        });
    }
    
    default boolean getPixel(Coordinate3D coord) {
        return getPixel(coord.getX(), coord.getY(), coord.getZ());
    }

    boolean getPixel(int x, int y, int z);

}
