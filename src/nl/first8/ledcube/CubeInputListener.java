package nl.first8.ledcube;

/**
 * A listener that can handle changes in cube display.
 */
public interface CubeInputListener {
	void onPixelChange(int x, int y, int z, boolean value);
    void onCubeChange(boolean[][][] cube);
    void flush();
}
