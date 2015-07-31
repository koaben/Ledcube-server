package nl.first8.ledcube;

import java.util.function.Consumer;
import java.util.function.Function;

public class CubeFactory {

	private static Cube instance = null;
	private static Function<Cube, Void> callback;

	public static Cube getInstance() {
		if (instance == null) {
			try {
				instance = new UsbCube();
				instance.init();
			} catch (CubeException e) {
				System.err.println("Falling back on dummy cube since USB cube could not be initialized");
				instance = new DummyCube();
				try {
					instance.init();
				} catch (CubeException f) {
					throw new IllegalArgumentException(f);
				}
			}
		}
		return instance;
	}

	public static Cube getInstance(String url, Consumer<Cube> callback) {
		if (instance == null) {
			try {
				instance = new RestCube(url, callback);
				instance.init();
			} catch (CubeException e) {
				throw new IllegalArgumentException(e);
			}
		}
		return instance;
	}


}
