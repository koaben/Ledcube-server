package nl.first8.ledcube.snake;

import java.util.Random;

import nl.first8.ledcube.Coordinate3D;
import nl.first8.ledcube.InputOutputCube;

public class SnakeGame extends InputOutputCube {
	private static final int MAX_SNAKE_LENGTH = 8 * 8 * 8;
	private static final Random RANDOM = new Random();

	private static final long CANDY_DELAY = 133;
	private static final long SNAKE_DELAY = 500;

	private Coordinate3D candy;
	private Coordinate3D[] snake = new Coordinate3D[MAX_SNAKE_LENGTH];
	private Direction direction;

	private int snakeHead;
	private int snakeTail;

	private long time;
	private long candyTime;
	private int candyTicker;

	public SnakeGame() {
		reset();
	}
	
	@Override
	public String getName() {
	    return "SnakeGame";
	}
	
	public void init() {
	    reset();
	}
	
	public synchronized void reset() {
		time = System.currentTimeMillis();
		candyTime = System.currentTimeMillis();
		candyTicker = 0;
		direction = Direction.UP;
		
		snakeHead = 1;
        snakeTail = 0;
        
        snake[snakeTail] = new Coordinate3D(3, 3, 0);
        snake[snakeHead] = new Coordinate3D(3,3,1);
		placeCandy();
	}


	public synchronized void tick() throws SnakeDeathException {
		long now = System.currentTimeMillis();

		if (now - candyTime > CANDY_DELAY) {
			candyTime = System.currentTimeMillis();
			candyTicker++;
			setPixel(candy, (candyTicker % 2 == 0));
		}

		if (now - time > SNAKE_DELAY) {
			time = System.currentTimeMillis();
			moveSnake();
			drawSnake();
		}
		
		flush();

	}

	private synchronized void drawSnake() throws SnakeDeathException {
		clear();

		if (snakeHead > snakeTail) {
			for (int i = snakeHead; i >= snakeTail; i--) {
				if (getPixel(snake[i])) {
					throw new SnakeDeathException();
				}
				setPixel(snake[i], true);
			}
		} else {
			for (int i = 0; i <= snakeHead; i++) {
				if (getPixel(snake[i])) {
					throw new SnakeDeathException();
				}
				setPixel(snake[i], true);
			}
			for (int i = MAX_SNAKE_LENGTH - 1; i >= snakeTail; i--) {
				if (getPixel(snake[i])) {
					throw new SnakeDeathException();
				}
				setPixel(snake[i], true);
			}
		}
	}

	private synchronized void moveSnake() throws SnakeDeathException {
		Coordinate3D oldHead = snake[snakeHead];

		snakeHead++;
		if (snakeHead >= MAX_SNAKE_LENGTH) {
			snakeHead = 0;
		}

		snake[snakeHead] = new Coordinate3D(oldHead.getX() + direction.getDx(), oldHead.getY() + direction.getDy(), oldHead.getZ() + direction.getDz());

		if (candy.equals(snake[snakeHead])) {
			System.out.println("increasing length to " + snakeLength());
			placeCandy();
		} else {
			snakeTail++;
			if (snakeTail > MAX_SNAKE_LENGTH) {
				snakeTail = 0;
			}
		}

		if (snake[snakeHead].getX() < 0 || snake[snakeHead].getX() > 7 || snake[snakeHead].getY() < 0 || snake[snakeHead].getY() > 7
				|| snake[snakeHead].getZ() < 0 || snake[snakeHead].getZ() > 7) {
			throw new SnakeDeathException();
		}
	}

	private synchronized void placeCandy() {
		candy = new Coordinate3D(RANDOM.nextInt(8), RANDOM.nextInt(8), RANDOM.nextInt(8));
		if (getPixel(candy)) {
			placeCandy();
		}
	}

	private synchronized int snakeLength() {
		return (snakeHead > snakeTail ? snakeHead - snakeTail : snakeHead + (MAX_SNAKE_LENGTH - snakeTail)) + 1;
	}

	public synchronized void changeDirection(Direction direction) {
		this.direction = direction;
	}

}
