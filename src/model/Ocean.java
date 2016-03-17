package model;

import java.util.Observable;
import java.util.Random;
//import java.io.*;

/**
 * The Ocean class contains a two dimensional array. Each location of the array may or may not contain <code>Algae</code>
 * and may or may not contain <code>BigFish</code> or <code>LittleFish</code>
 * A location may contain both algae and a fish, but no location can contain more than one fish
 * @author Raymond McBride
 */
 public class Ocean extends Observable{

	//two dimensional array of location objects
	private Location[][] ocean;
	private int numberOfBigFish;
	private int numberOfLittleFish;
	private int numberOfAlgae;
	private int maxRow;
	private int maxCol;
	private static Random generator = new Random(System.currentTimeMillis());

	/**
	 * The constructor for the <code>Ocean</code> specifies the number of rows and columns in the array
	 * @param rows The number of rows in the ocean
	 * @param columns The number of columns in the ocean
	 */
	public Ocean(int rows, int columns) {
		ocean = new Location[rows][columns];
		numberOfBigFish = 0;
		numberOfLittleFish = 0;
		numberOfAlgae = 0;
		maxRow = rows;
		maxCol = columns;
		for(int row = 0; row < maxRow; row++) {
			for(int col = 0; col < maxCol; col++)
				(ocean[row][col]) =  new Location(row, col);
		}
	}

	/**
	 * Set the number of <code>BigFish</code> to be added to the <code>Ocean</code>
	 * @param number The number to be added
	 * @return <code>true</code> if the required number of <code>BigFish</code> was successfully added
	 */
	public boolean setNumberOfBigFish(int number) {
		if(((maxRow * maxCol - numberOfLittleFish) < number) || number < 0)
			return false;
		else {
			remove(new BigFish());
			numberOfBigFish = 0;
			while(numberOfBigFish < number) {
				add(new BigFish());
			}
			this.calculateAndReset();
			//display();
			return true;
		}
	}

	/**
	 * Set the number of <code>LittleFish</code> to be added to the <code>Ocean</code>
	 * @param number The number to be added
	 * @return <code>true</code> if the required number of <code>LittleFish</code> was successfully added
	 */
	public boolean setNumberOfLittleFish(int number) {
		if(((maxRow * maxCol - numberOfBigFish) < number) || number < 0)
			return false;
		else {
			remove(new LittleFish());
			numberOfLittleFish = 0;
			while(numberOfLittleFish < number) {
				add(new LittleFish());
			}
			this.calculateAndReset();
			//display();
			return true;
		}
	}

	/**
	 * Set the number of <code>Algae</code> to be added to the <code>Ocean</code>
	 * @param number The number to be added
	 * @return <code>true</code> if the required number of <code>Algae</code> was successfully added
	 */
	public boolean setNumberOfAlgae(int number) {
		if(((maxRow * maxCol) < number) || number < 0)
			return false;
		else {
			remove(new Algae());
			numberOfAlgae = 0;
			while(numberOfAlgae < number) {
				add(new Algae());
			}
			this.calculateAndReset();
			//display();
			return true;
		}
	}

	/**
	 * Get the current number of <code>BigFish</code> in the <code>Ocean</code>
	 * @return The current number
	 */
	public int getNumberOfBigFish() {
		return numberOfBigFish;
	}

	/**
	 * Get the current number of <code>LittleFish</code> in the <code>Ocean</code>
	 * @return The current number
	 */
	public int getNumberOfLittleFish() {
		return numberOfLittleFish;
	}

	/**
	 * Get the current number of <code>Algae</code> in the <code>Ocean</code>
	 * @return The current number
	 */
	public int getNumberOfAlgae() {
		return numberOfAlgae;
	}

	/**
	 * Set the number of turns between the time a <code>BigFish</code> can reproduce
	 * @param steps The number of turns
	 * @throws MyException Custom exception thrown if the parameter is negative
	 */
	public void setBigFishGestationPeriod(int steps) {
		try {
			if(steps < 0){
				BigFish.setBigFishGestationPeriod(15);
				throw new MyException();
			}
			else BigFish.setBigFishGestationPeriod(steps);
		}
		catch(MyException e){
			System.out.println("Cannot use negative value to set Big Fish Gestation Period"
				+ " - Gestation Period set to 15 steps");
		}
	}

	/**
	 * Set the number of turns between the time a <code>LittleFish</code> can reproduce
	 * @param steps The number of turns
	 * @throws MyException Custom exception thrown if the parameter is negative
	 */
	public void setLittleFishGestationPeriod(int steps) {
		try {
			if(steps < 0) {
				LittleFish.setLittleFishGestationPeriod(15);
				throw new MyException();
			}
			else LittleFish.setLittleFishGestationPeriod(steps);
		}
		catch(MyException e){
			System.out.println("Cannot use negative value to set Little Fish Gestation Period"
				+ " - Gestation Period set to 15 steps");
		}
	}

	/**
	 * Set the probability that <code>Algae</code> will try to extend to an adjacent location
	 * @param probability The probability
	 * @throws MyException Custom exception thrown if the parameter is less than 0 or greater than 1
	 */
	public void setAlgaeGrowthProbability(double probability) {
		try {
			if(probability < 0 || probability > 1){
				Algae.setAlgaeGrowthProbability(0.1);
				throw new MyException();
			}
			else Algae.setAlgaeGrowthProbability(probability);
		}
		catch(MyException e){
			System.out.println("Algae Growth Probability must be between 0 and 1"
				+ " - Growth Probability set to 0.1");
		}
	}

	/**
	 * Set the number of turns a <code>BigFish</code> can survive without eating
	 * @param steps Set the number of turns
	 * @throws MyException Custom exception thrown if the parameter is negative
	 */
	public void setBigFishStarvationPeriod(int steps){
		try {
			if(steps < 0) {
				BigFish.setBigFishStarvationPeriod(10);
				throw new MyException();
			}
			else BigFish.setBigFishStarvationPeriod(steps);
		}
		catch(MyException e){
			System.out.println("Cannot use negative value to set Big Fish Starvation Period"
				+ " - Starvation Period set to 10 steps");
		}
	}

	/**
	 * Set the number of turns a <code>LittleFish</code> can survive without eating
	 * @param steps Set the number of turns
	 * @throws MyException Custom exception thrown if the parameter is negative
	 */
	public void setLittleFishStarvationPeriod(int steps) {
		try {
			if(steps < 0) {
				LittleFish.setLittleFishStarvationPeriod(10);
				throw new MyException();
			}
			else LittleFish.setLittleFishStarvationPeriod(steps);
		}
		catch(MyException e) {
			System.out.println("Cannot use negative value to set Little Fish Starvation Period"
				+ " - Starvation Period set to 10 steps");
		}
	}

	/**
	 * Execute one step in the simulation and notify observers
	 */
	public void step() {
		for(int row = 0; row < maxRow; row++){
			for(int col = 0; col < maxCol; col++){
				if(containsBigFish(row, col)) {
					BigFish bigFish = (BigFish)(ocean[row][col]).getFish();
					int [] dir = bigFish.getDirection();
					bigFish.move(this, ocean[row][col], ocean[(dir[0]+row+maxRow)%maxRow][(dir[1]+col+maxCol)%maxCol]);
				}
				else if(containsLittleFish(row, col)) {
					LittleFish littleFish = (LittleFish)(ocean[row][col]).getFish();
					int [] dir = littleFish.getDirection();
					littleFish.move(this, ocean[row][col], ocean[(dir[0]+row+maxRow)%maxRow][(dir[1]+col+maxCol)%maxCol]);
				}
				if(containsAlgae(row, col)){
					Algae algae = (ocean[row][col]).getAlgae();
					int [] dir = algae.getDirection();
					algae.move(this, ocean[(dir[0]+row+maxRow)%maxRow][(dir[1]+col+maxCol)%maxCol]);
				}
			}
		}
		this.calculateAndReset();
		setChanged();
		notifyObservers();
	}

	/**
	 * Returns <code>true</code> if the given location of the array contains
	 * a <code>BigFish</code> and <code>false</code> otherwise
	 * @param row The row of the <code>Ocean</code>
	 * @param col The column of the <code>Ocean</code>
	 * @return true if a <code>BigFish</code> is there
	 */
	public boolean containsBigFish(int row, int col) {
		Fish fish = (ocean[row][col]).getFish();
		if(fish == null || !(fish instanceof BigFish))
			return false;
		return true;
	}

	/**
	 * Returns <code>true</code> if the given location of the array contains
	 * a <code>LittleFish</code> and <code>false</code> otherwise
	 * @param row The row of the <code>Ocean</code>
	 * @param col The column of the <code>Ocean</code>
	 * @return true if a <code>LittleFish</code> is there
	 */
	public boolean containsLittleFish(int row, int col) {
		Fish fish = (ocean[row][col]).getFish();
		if(fish == null || !(fish instanceof LittleFish))
			return false;
		return true;
	}

	/**
	 * Returns <code>true</code> if the given location of the array contains
	 * <code>Algae</code> and <code>false</code> otherwise
	 * @param row The row of the <code>Ocean</code>
	 * @param col The column of the <code>Ocean</code>
	 * @return true if <code>Algae</code> is there
	 */
	public boolean containsAlgae(int row, int col) {
		Algae algae = (ocean[row][col]).getAlgae();
		if(algae == null)
			return false;
		return true;
	}

	//private method to remove all creatures of a particular type from the ocean
	private void remove(Creature c) {
		for(int row = 0; row < maxRow; row++) {
			for(int col = 0; col < maxCol; col++) {
				if(c instanceof BigFish && this.containsBigFish(row, col))
					(ocean[row][col]).removeFish();
				if(c instanceof LittleFish && this.containsLittleFish(row, col))
					(ocean[row][col]).removeFish();
				if(c instanceof Algae && this.containsAlgae(row, col))
					(ocean[row][col]).removeAlgae();
			}
		}
	}

	//private method to add a single instance of given creature type, to the ocean
	private void add(Creature c){
		int newRow = generator.nextInt(maxRow);
		int newCol = generator.nextInt(maxCol);
               if(c instanceof Algae){
                    if(!(this.containsAlgae(newRow, newCol))){
                        (ocean[newRow][newCol]).addAlgae(new Algae());
		        numberOfAlgae++;
                    }
                }
                else if(c instanceof LittleFish){
                    if(!(this.containsLittleFish(newRow, newCol) || this.containsBigFish(newRow, newCol))){
				(ocean[newRow][newCol]).addFish(new LittleFish());
				numberOfLittleFish++;
                    }
                }
                else if(c instanceof BigFish){
                    if(!(this.containsLittleFish(newRow, newCol) || this.containsBigFish(newRow, newCol))){
				(ocean[newRow][newCol]).addFish(new BigFish());
				numberOfBigFish++;
                    }
                }
	}

	/*private method to calculate how many instances of each creature are in the ocean
	and to reset all the creatures to 'havent moved' status*/
	private void calculateAndReset() {
		numberOfBigFish = 0;
		numberOfLittleFish = 0;
		numberOfAlgae = 0;
		for(int row = 0; row < maxRow; row++){
			for(int col = 0; col < maxCol; col++){
				if((ocean[row][col]).getFish() != null){
					if((ocean[row][col]).getFish() instanceof BigFish)
						numberOfBigFish++;
					else if((ocean[row][col]).getFish() instanceof LittleFish)
						numberOfLittleFish++;
					((ocean[row][col]).getFish()).reset();
				}
				if((ocean[row][col]).getAlgae() != null){
					numberOfAlgae++;
					((ocean[row][col]).getAlgae()).reset();
				}
			}
		}
	}

	//Debug method to display the contents of the Ocean
	/*public void display(){
		for(int row = 0; row < maxRow; row++){
			for(int col = 0; col < maxCol; col++){
				if(this.containsBigFish(row,col))
					System.out.println("BigFish @ " + row + " " + col);
				if(this.containsLittleFish(row,col))
					System.out.println("LittleFish @ " + row + " " + col);
				if(this.containsAlgae(row,col))
					System.out.println("Algae @ " + row + " " + col);
			}
		}
	}*/

	//Custom exception
	class MyException extends Exception {
		public MyException() {}
		public MyException(String msg) { super(msg); }
	}


}