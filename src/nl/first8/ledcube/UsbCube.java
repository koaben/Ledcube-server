package nl.first8.ledcube;
import static nl.first8.ledcube.CubeUtil.CUBE_LOCK;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Random;

import nl.first8.ledcube.gui.Coordinate3D;

import com.codeminders.hidapi.HIDDevice;
import com.codeminders.hidapi.HIDDeviceNotFoundException;
import com.codeminders.hidapi.HIDManager;

public class UsbCube extends BaseCube {
	private static final int VENDOR_ID = 5824;
	private static final int PRODUCT_ID = 1152;

	private HIDDevice dev;

	public void init() throws CubeException {
		try {
			synchronized (CUBE_LOCK) {
				if (dev==null) {
					com.codeminders.hidapi.ClassPathLibraryLoader.loadNativeHIDLibrary();
					dev = HIDManager.getInstance().openById(VENDOR_ID, PRODUCT_ID, null);
					flush();
				}
			}
		} catch (Exception e) {
			throw new CubeException(e);
		}
	}

	public void flush() {
		try {
			synchronized (CUBE_LOCK) {
				byte[] block = Arrays.copyOf(buffer.toByteArray(), 64);
				byte[] blockWithReportId = new byte[65];
				System.arraycopy(block, 0, blockWithReportId, 1, 64);
				dev.write(blockWithReportId);
			}
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}


}
