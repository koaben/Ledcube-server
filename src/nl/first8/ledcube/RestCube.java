package nl.first8.ledcube;

import static nl.first8.ledcube.CubeUtil.CUBE_LOCK;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.function.Consumer;
import java.util.function.Function;

import org.apache.commons.io.IOUtils;

public class RestCube extends BaseCube {

	private static final int SLEEP_DELAY = 10;
	private final String baseUrl;
	private Consumer<Cube> callback;

	public RestCube(String baseUrl, Consumer<Cube> callback) {
		this.baseUrl = baseUrl;
		this.callback = callback;
	}

	@Override
	public void init() throws CubeException {
		Thread t = new Thread(() -> {
			for (;;) {
				try {
					Thread.sleep(SLEEP_DELAY);
				} catch (Exception e) {
				}
				synchronized(CUBE_LOCK) {
					sync();
					callback.accept(this);
				}
			}
		});
		t.setDaemon(true);
		t.start();
	}

	private void sync() {
		try {
			URL obj = new URL(baseUrl+"bulk");
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");

			String result = IOUtils.toString(con.getInputStream());
			CubeUtil.stringToCube(this, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void flush() {
		synchronized (CUBE_LOCK) {
			try {
				URL obj = new URL(baseUrl+"bulk");
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				con.setRequestMethod("POST");
				con.setDoOutput(true);

				String result = CubeUtil.cubeToString(this);
				IOUtils.write(result, con.getOutputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
