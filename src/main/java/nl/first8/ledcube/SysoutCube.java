package nl.first8.ledcube;

/**
 * An output cube that dumps its view on System.out.
 */
public class SysoutCube extends BufferedCube implements CubeOutput {
    @Override
    public String getName() {
        return "SysoutCube";
    }
	@Override
	public synchronized void init() {
		System.out.println("Init called on dummy cube");
	}
	
	@Override
	public synchronized void flush() {
		System.out.println("Flush called on dummy cube:");
		dump();
	}
}
