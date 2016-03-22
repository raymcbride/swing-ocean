package model;

class Location {

	private Fish fish;
	private Algae algae;
	private int row;
	private int column;

	public Location(int thisRow, int thisColumn) {
		row = thisRow;
		column = thisColumn;
	}

	public void addFish(Fish aFish) {
		fish = aFish;
	}

	public void removeFish() {
		fish = null;
	}

	public Fish getFish() {
		return fish;
	}

	public void addAlgae(Algae someAlgae) {
		algae = someAlgae;
	}

	public void removeAlgae() {
		algae = null;
	}

	public Algae getAlgae() {
		return algae;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return column;
	}
}