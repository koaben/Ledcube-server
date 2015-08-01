package nl.first8.ledcube.gui;

import nl.first8.ledcube.CubeException;
import nl.first8.ledcube.CubeOutput;

/**
 * Wraps the JavaFX cube so we can access as an input and output source for a Cube.
 */
public class ScreenCube implements CubeOutput {

    private JavaFXCube cube;

    public ScreenCube(JavaFXCube cube) {
        this.cube = cube;
    }

    @Override
    public String getName() {
        return "OnScreenCube";
    }
    
    @Override
    public void init() throws CubeException {
        
    }

    @Override
    public void flush() {
        // JavaFX cube is not buffered so no need to flush
    }

    @Override
    public void setPixel(int x, int y, int z, boolean value) {
        cube.changeColor(new Coordinate3D(x, y, z), (value?LedColor.ON:LedColor.OFF));
        
    }

    @Override
    public boolean getPixel(int x, int y, int z) {
        LedColor c = cube.getColor(new Coordinate3D(x, y, z));
        return LedColor.ON==c;
    }
    
}
