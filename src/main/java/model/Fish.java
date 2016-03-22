package model;

abstract class Fish extends Creature{

	private int hunger;
	private int reproduce;

	public Fish() {
		super();
		int starvation = 30;
		int gestation = 30;
		hunger = starvation - (generator.nextInt(starvation/2) + 1);
		reproduce = generator.nextInt(gestation) + 1;
	}

	public Fish(int starvation) {
		hunger = starvation;
		reproduce = 1;
	}

	public int getHunger() {
		return hunger;
	}

	public void starve(Location loc) {
		hunger--;
		if(this.getHunger() <= 0) {
			loc.removeFish();
		}
	}

	public void move(Ocean ocean, Location oldLoc, Location newLoc){
		if(!(this.moved())){
			if(this.canMove(ocean, newLoc)) {
				if(this.canEat(ocean, newLoc))
					this.eat(newLoc);
				else this.starve(oldLoc);
				if(oldLoc.getFish() != null) {
					if(this.canReproduce())
						this.giveBirth(oldLoc, newLoc);
					else {
						this.gestate();
						newLoc.addFish(this);
						oldLoc.removeFish();
					}
				}
			}
			else {
				this.starve(oldLoc);
				if(oldLoc.getFish() != null) {
					this.gestate();
				}
			}
			this.set();
		}
	}

	public void setHunger(int food) {
		hunger = food;
	}


	public void gestate() {
		reproduce++;
	}

	public int lastReproduce() {
		return reproduce;
	}

	public int[] getDirection() {
		return super.getDirection();
	}

	abstract void eat(Location location);


	public void reproduce(Location oldLoc, Location newLoc, Fish fish){
		reproduce = 1;
		newLoc.addFish(this);
		oldLoc.addFish(fish);
	}

	abstract boolean canMove(Ocean ocean, Location loc);

	abstract boolean canEat(Ocean ocean, Location loc);

	abstract boolean canReproduce();

	abstract void giveBirth(Location oldLoc, Location newLoc);



}