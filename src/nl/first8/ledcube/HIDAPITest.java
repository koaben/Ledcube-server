package nl.first8.ledcube;

import java.io.IOException;

import com.codeminders.hidapi.HIDDevice;
import com.codeminders.hidapi.HIDDeviceInfo;
import com.codeminders.hidapi.HIDManager;

public class HIDAPITest {
	private static final long READ_UPDATE_DELAY_MS = 50L;

	// MBED
	static final int VENDOR_ID = 0;
	static final int PRODUCT_ID = 0;
	private static final int BUFSIZE = 2048;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		com.codeminders.hidapi.ClassPathLibraryLoader.loadNativeHIDLibrary();
		listDevices();
		readDevice();
	}

	private static void readDevice() {
		HIDDevice dev;
		try {
			dev = HIDManager.getInstance().openById(VENDOR_ID, PRODUCT_ID, null);
			System.err.print("Manufacturer: " + dev.getManufacturerString() + "\n");
			System.err.print("Product: " + dev.getProductString() + "\n");
			System.err.print("Serial Number: " + dev.getSerialNumberString() + "\n");
			try {
				byte[] buf = new byte[BUFSIZE];
				dev.enableBlocking();
				while (true) {
					int n = dev.read(buf);
					for (int i = 0; i < n; i++) {
						int v = buf[i];
						if (v < 0)
							v = v + 256;
						String hs = Integer.toHexString(v);
						if (v < 16)
							System.err.print("0");
						System.err.print(hs + " ");
					}
					System.err.println("");

					try {
						Thread.sleep(READ_UPDATE_DELAY_MS);
					} catch (InterruptedException e) {
						// Ignore
					}
				}
			} finally {
				dev.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void listDevices() {
		String property = System.getProperty("java.library.path");
		System.err.println(property);

		try {
			HIDDeviceInfo[] devs = HIDManager.getInstance().listDevices();
			System.err.println("Devices:\n\n");
			for (int i = 0; i < devs.length; i++) {
				System.err.println("" + i + ".\t" + devs[i]);
				System.err.println("---------------------------------------------\n");
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

}