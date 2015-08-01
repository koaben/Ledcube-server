package nl.first8.ledcube;

public class WebOutputListener implements CubeInputListener {

    @Override
    public void onPixelChange(int x, int y, int z, boolean value) {
        try {
            
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onCubeChange(boolean[][][] cube) {
        try {
            
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void flush() {
    }

}
