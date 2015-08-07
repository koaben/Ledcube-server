package nl.first8.ledcube.rest;

import nl.first8.ledcube.BufferedCube;
import nl.first8.ledcube.CubeException;

/**
 * Our Web cube singleton holding the website's state.
 */
public class WebCube extends BufferedCube {

    @Override
    public String getName() {
        return "SingletonWebCube";
    }

    @Override
    public void init() throws CubeException {
    }

    @Override
    public void flush() {
    }

}
