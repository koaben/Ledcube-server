package nl.first8.ledcube;

import java.util.ArrayList;
import java.util.List;

/**
 * A buffered (output) cube that notifies listeners of changes (input).
 */
public abstract class InputOutputCube extends BufferedCube implements CubeOutput, CubeInput {

    private List<CubeInputListener> listeners = new ArrayList<>();

    @Override
    public void flush() {
        listeners.forEach(l->l.flush());
    }
    
    @Override
    public void setPixel(int x, int y, int z, boolean value) {
        super.setPixel(x, y, z, value);
        listeners.forEach( l -> l.onPixelChange(x, y, z, value));
    }
    
    @Override
    public synchronized void clear() {
        super.clear();
        listeners.forEach( l -> l.onCubeChange(new boolean[WIDTH][HEIGHT][DEPTH]));
    }
    

    @Override
    public void addLedCubeListener(CubeInputListener listener) {
        listeners.add(listener);
    }

    @Override
    public boolean removeListener(CubeInputListener listener) {
        return listeners.remove(listener);
    }
    
}
