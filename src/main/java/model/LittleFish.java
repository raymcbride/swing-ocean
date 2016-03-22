package model;

class LittleFish extends Fish {

	private static int gestationPeriod;
	private static int starvationPeriod;

	public LittleFish() {
		super();
	}

	public LittleFish(int starvation) {
		super(starvation);
	}

	public static void setLittleFishGestationPeriod(int steps) {
		gestationPeriod = steps;
	}

	public static void setLittleFishStarvationPeriod(int steps) {
		starvationPeriod = steps;

	}

	public void eat(Location loc) {
		loc.removeAlgae();
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
		super.reproduce(oldLoc, newLoc, new LittleFish(starvationPeriod));
	}

	public boolean canMove(Ocean ocean, Location loc) {
		return (!(ocean.containsBigFish(loc.getRow(), loc.getCol()) || ocean.containsLittleFish(loc.getRow(), loc.getCol())));
	}

	public boolean canEat(Ocean ocean, Location loc) {
		return (ocean.containsAlgae(loc.getRow(), loc.getCol()));
	}

	public boolean canReproduce() {
		return (this.lastReproduce() >= this.getGestationPeriod());
	}

	public void move(Ocean ocean, Location oldLoc, Location newLoc){
		super.move(ocean, oldLoc, newLoc);
	}
}