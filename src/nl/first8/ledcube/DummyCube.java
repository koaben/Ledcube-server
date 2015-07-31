package nl.first8.ledcube;

public class DummyCube extends BaseCube {

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
