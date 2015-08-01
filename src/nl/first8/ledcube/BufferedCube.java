package nl.first8.ledcube;

import java.util.BitSet;

/**
 * A Cube output implemented with a buffer. Requires sync to actually display
 * the changes held in the buffer.
 * 
 * @author arjanl
 */
public abstract class BufferedCube implements CubeOutput {
    protected BitSet buffer = new BitSet(WIDTH * HEIGHT * DEPTH);

    protected int getBitIndex(int x, int y, int z) {
        return z * WIDTH * HEIGHT + y * WIDTH + x;
    }

    public synchronized void dump() {
        System.out.println("");
        for (int y = 0; y < HEIGHT; y++) {
            for (int z = 0; z < DEPTH; z++) {
                for (int x = 0; x < WIDTH; x++) {
                    if (getPixel(x, y, z)) {
                        System.out.print("o");
                    } else {
                        System.out.print(".");
                    }
                }
                System.out.print(" ");
            }
            System.out.print("\n");
        }
    }

    public void setPixel(int x, int y, int z, boolean value) {
        int bitIndex = getBitIndex(x, y, z);
        buffer.set(bitIndex, value);
    }

    public boolean getPixel(int x, int y, int z) {
        return buffer.get(getBitIndex(x, y, z));
    }

    public synchronized void clear() {
        buffer.clear();
    }
}
