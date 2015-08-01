package nl.first8.ledcube.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Java bootstrapping of a rest application.
 */
@ApplicationPath("/")
public class LedCubeRestApplication extends Application {
	private static final WebCube WEBCUBE = new WebCube();

	public LedCubeRestApplication() {
	}

	public static WebCube getInstance() {
	    return WEBCUBE;
	}

}
