package model;

class Algae extends Creature{

	private static double growthProbability;
	private double wontGrow;

	public Algae() {
	}

	public static void setAlgaeGrowthProbability(double probability) {
		growthProbability = probability;
	}

	public double getGrowthProbability() {
		return growthProbability;
	}

	public Algae grow() {
		return new Algae();

	}

	public int[] getDirection() {
			return super.getDirection();
	}

	public boolean canMove(Ocean ocean, Location loc) {
			return !(ocean.containsLittleFish(loc.getRow(), loc.getCol()) || ocean.containsBigFish(loc.getRow(), loc.getCol()) ||
			ocean.containsAlgae(loc.getRow(), loc.getCol()));
	}

	public void move(Ocean ocean, Location loc){
		if(!(this.moved())){
			if(canMove(ocean, loc)) {
				wontGrow = generator.nextDouble();
				if(this.getGrowthProbability() >= wontGrow) {
					loc.addAlgae(this.grow());
				}
			}
			this.set();
		}
	}
}