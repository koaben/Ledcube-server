package nl.first8.ledcube;


/**
 * An input is something that can make changes to a cube display. It needs to accept listeners which respond to those changes.
 */
public interface CubeInput extends Cube {
    String getName();
    void addLedCubeListener(final CubeInputListener listener);
    boolean removeListener(final CubeInputListener listener);
}
