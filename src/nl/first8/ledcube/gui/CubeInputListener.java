package nl.first8.ledcube.gui;

public interface CubeInputListener {
	void onPixelChange(int x, int y, int z, boolean value);
    void onCubeChange(boolean[][][] cube);
}
