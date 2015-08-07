package nl.first8.ledcube;

import java.util.function.Function;

/**
 * Anything that looks like a cube.
 */
public interface Cube {
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;
    public static final int DEPTH = 8;
    
    
    default void forEachPixel( Function<Coordinate3D,Void> f ) {
        for (int x=0; x<WIDTH; x++) {
            for (int y=0; y<HEIGHT; y++) {
                for (int z=0; z<DEPTH; z++) {
                    f.apply(new Coordinate3D(x, y, z));
                }
            }
        }
    }
}
