package model;

class BigFish extends Fish {

	private static int gestationPeriod;
	private static int starvationPeriod;

	public BigFish() {
		super();
	}

	public BigFish(int starvation) {
			super(starvation);
	}

	public static void setBigFishGestationPeriod(int steps) {
		gestationPeriod = steps;
	}

	public static void setBigFishStarvationPeriod(int steps) {
		starvationPeriod = steps;
	}

	public void eat(Location loc) {
		loc.removeFish();
		this.setHunger(starvationPeriod);
		loc.addFish(this);
	}

	public void starve(Location loc){
		super.starve(loc);
	}

	public int getGestationPeriod() {
		return gestationPeriod;
	}

	public int getStarvationPeriod() {
			return starvationPeriod;
	}

	public void giveBirth(Location oldLoc, Location newLoc) {
		super.reproduce(oldLoc, newLoc, new BigFish(starvationPeriod));
	}

	public int[] getDirection(){
		return super.getDirection();
	}

	public boolean canMove(Ocean ocean, Location loc) {
		return (!(ocean.containsBigFish(loc.getRow(), loc.getCol())));
	}

	public boolean canEat(Ocean ocean, Location loc) {
		return (ocean.containsLittleFish(loc.getRow(), loc.getCol()));
	}

	public boolean canReproduce() {
		return (this.lastReproduce() >= this.getGestationPeriod());
	}

	public void move(Ocean ocean, Location oldLoc, Location newLoc){
		super.move(ocean, oldLoc, newLoc);
	}
}