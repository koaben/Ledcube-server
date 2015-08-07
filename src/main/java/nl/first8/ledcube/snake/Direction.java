package nl.first8.ledcube.snake;

public enum Direction {
	UP(0,0,1), DOWN(0,0,-1), LEFT(-1,0,0), RIGHT(1,0,0), FORWARD(0,1,0), BACKWARD(0,-1,0);

	private final int dx;
	private final int dy;
	private final int dz;

	Direction(int dx, int dy, int dz) {
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
		
	}

	public int getDx() {
		return dx;
	}

	public int getDy() {
		return dy;
	}

	public int getDz() {
		return dz;
	}

}
