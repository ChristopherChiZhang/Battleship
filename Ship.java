
abstract class Ship {
	// Declaration
	int life, length; // Life and length of Ship
	Point start, end; // Start and end Point of Ship
	// isSunk is true if life = 0, placed is true if user has placed Ship
	boolean isSunk, placed;
	String name; // Name of type of Ship

	public Ship(int n) { // Ship constructor
		life = n;
		length = n;
		isSunk = false;
		placed = false;
		start = new Point(0, 0);
		end = new Point(0, 0);
	}

	/*
	 * This method returns true if the ship is placed
	 */
	public boolean isPlaced() {
		return placed;
	}

	/*
	 * This method overrides the toString method and returns the start and end
	 * Points of a Ship
	 */
	public String toString() {
		return "Start: " + start.toString() + " End: " + end.toString();
	}

	/*
	 * This method resets a Ship object
	 */
	public void restart() {
		placed = false;
		start = new Point(0, 0);
		end = new Point(0, 0);
	}
}
