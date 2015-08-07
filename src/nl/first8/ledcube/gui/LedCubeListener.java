package nl.first8.ledcube.gui;

public interface LedCubeListener {
	void onPixelChange(int x, int y, int z, boolean value);
    void onCubeChange(boolean[][][] cube);
}
