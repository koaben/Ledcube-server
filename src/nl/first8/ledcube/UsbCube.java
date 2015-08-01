package nl.first8.ledcube;

import java.io.IOException;
import java.util.Arrays;

import com.codeminders.hidapi.HIDDevice;
import com.codeminders.hidapi.HIDManager;

/**
 * An output view that hooks up to an USB Led Cube.
 */
public class UsbCube extends BufferedCube implements CubeOutput {
    private static final int VENDOR_ID = 5824;
    private static final int PRODUCT_ID = 1152;

    private HIDDevice dev;

    @Override
    public String getName() {
        return "USBCube";
    }

    public void init() throws CubeException {
        try {
            if (dev == null) {
                com.codeminders.hidapi.ClassPathLibraryLoader.loadNativeHIDLibrary();
                dev = HIDManager.getInstance().openById(VENDOR_ID, PRODUCT_ID, null);
                flush();
            }
        } catch (Exception e) {
            throw new CubeException(e);
        }
    }

    public void flush() {
        try {
            byte[] block = Arrays.copyOf(buffer.toByteArray(), 64);
            byte[] blockWithReportId = new byte[65];
            System.arraycopy(block, 0, blockWithReportId, 1, 64);
            dev.write(blockWithReportId);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
