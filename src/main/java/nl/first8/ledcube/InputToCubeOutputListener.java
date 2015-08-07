package nl.first8.ledcube;

/**
 * An input listener that updates an output cube.
 */
public class InputToCubeOutputListener  implements CubeInputListener{
    private final CubeOutput output;

    public InputToCubeOutputListener(CubeOutput output) {
        this.output = output;
    }

    @Override
    public void onPixelChange(int x, int y, int z, boolean value) {
        System.out.println("writing " + x + "," + y  + "," + z + "=" + value +" to " + output.getName());
        output.setPixel(x, y, z, value);
    }

    @Override
    public void onCubeChange(boolean[][][] cube) {
        output.setCube(cube);
        output.flush();
    }
    
    @Override
    public void flush() {
        output.flush();
    }
}
