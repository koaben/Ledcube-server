package nl.first8.ledcube;

import nl.first8.ledcube.gui.Coordinate3D;

public interface Cube {

	void init() throws CubeException;

	void flush();

	default void setPixel(Coordinate3D coord, boolean value) {
		setPixel(coord.getX(), coord.getY(), coord.getZ(), value);
	}

	default boolean getPixel(Coordinate3D coord) {
		return getPixel(coord.getX(), coord.getY(), coord.getZ());
	}

	void setPixel(int x, int y, int z, boolean value);

	boolean getPixel(int x, int y, int z);

	void clear();

	void randomize();

}
