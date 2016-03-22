package model;

import java.util.Random;

abstract class Creature {

	protected static Random generator = new Random(System.currentTimeMillis());
	private boolean hasMoved;

	public Creature() {
		hasMoved = true;
	}

	public int[] getDirection() {
		int direction = generator.nextInt(4) + 1;
		int[] directionArray = new int[2];
		if(direction == 1) {
			directionArray[0] = 1;
			directionArray[1] = 0;
		}
		if(direction == 2) {
			directionArray[0] = 0;
			directionArray[1] = 1;
		}
		if(direction == 3) {
			directionArray[0] = -1;
			directionArray[1] = 0;
		}
		if(direction == 4) {
			directionArray[0] = 0;
			directionArray[1] = -1;
		}
		return directionArray;
	}

	public void move(){
	}

	public void set() {
		hasMoved = true;
	}

	public void reset() {
		hasMoved = false;
	}

	public boolean moved() {
		return hasMoved;
	}
}

