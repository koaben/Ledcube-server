package nl.first8.ledcube;
import static nl.first8.ledcube.CubeUtil.CUBE_LOCK;
import java.util.BitSet;
import java.util.Random;

abstract class BaseCube implements Cube {
	protected BitSet buffer = new BitSet(8 * 8 * 8);
	
	private static final Random RANDOM = new Random();

	protected int getBitIndex(int x, int y, int z) {
		return z * 8 * 8 + y * 8 + x;
	}

	public synchronized void dump() {
		System.out.println("");
		for (int y = 0; y < 8; y++) {
			for (int z = 0; z < 8; z++) {
				for (int x = 0; x < 8; x++) {
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
		synchronized (CUBE_LOCK) {
			buffer.set(bitIndex, value);
		}
	}

	public boolean getPixel(int x, int y, int z) {
		synchronized (CUBE_LOCK) {
			return buffer.get(getBitIndex(x, y, z));
		}
	}

	public synchronized void randomize() {
		for (int i = 0; i < buffer.size(); i++) {
			buffer.set(i, RANDOM.nextBoolean());
		}
	}

	public synchronized void clear() {
		buffer.clear();
	}
}
