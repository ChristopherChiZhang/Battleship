import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Battlefield {
	// Declaration and Initialization
	int row = 10, col = 10; // Row and Column length
	int randIndex = 0; // Random index
	boolean field[][] = new boolean[row][col]; // Stores placement of Ships
	boolean isSet; // If true, ship is set
	Random rand;
	static List<Point> possibleEndPoints = new ArrayList<>(); // Stores the possible end points of a Ship
	Point randEndPoint; // The random endpoint Point

	/*
	 * This method takes a parameter of type Ship and randomly places it onto the
	 * field
	 */
	public void setToField(Ship s) {
		isSet = false;
		rand = new Random();
		while (!isSet) { // While Ship is not set, run
			int r = rand.nextInt(10); // Get random row index
			int c = rand.nextInt(10); // Get random col index
			if (r - (s.length - 1) >= 0) { // If Ship can be placed upward
				if (placeUp(r, c, s)) {
					// Set start coordinates of ship
					s.start.x = r;
					s.start.y = c;
					// Add possible end point to possibleEndPoints
					possibleEndPoints.add(new Point(r - (s.length - 1), c));
				}
			}
			if (c + (s.length - 1) <= 9) { // If ship can be placed right
				if (placeRight(r, c, s)) {
					// Set start coordinates of ship
					s.start.x = r;
					s.start.y = c;
					// Add possible end point to possibleEndPoints
					possibleEndPoints.add(new Point(r, c + (s.length - 1)));
				}
			}
			if (r + (s.length - 1) <= 9) { // If ship can be placed downward
				if (placeDown(r, c, s)) {
					// Set start coordinates of ship
					s.start.x = r;
					s.start.y = c;
					// Add possible end point to possibleEndPoints
					possibleEndPoints.add(new Point(r + (s.length - 1), c));
				}
			}
			if (c - (s.length - 1) >= 0) { // If ship can be placed left
				if (placeLeft(r, c, s)) {
					// Set start coordinates of ship
					s.start.x = r;
					s.start.y = c;
					// Add possible end point to possibleEndPoints
					possibleEndPoints.add(new Point(r, c - (s.length - 1)));
				}
			}
			if (!possibleEndPoints.isEmpty()) { // If there are possible end points
				// Generate random index
				randIndex = rand.nextInt(possibleEndPoints.size());
				// Choose point at generated random index
				randEndPoint = possibleEndPoints.get(randIndex);
				// Set end coordinates of ship
				s.end.x = randEndPoint.x;
				s.end.y = randEndPoint.y;
				// Set placed indices to true
				if (s.start.x == randEndPoint.x) {// If points horizontal
					for (int i = Math.min(s.start.y, randEndPoint.y); i <= Math.max(s.start.y, randEndPoint.y); i++) {
						field[s.start.x][i] = true;
					}
				} else {// If points vertical
					for (int i = Math.min(s.start.x, randEndPoint.x); i <= Math.max(s.start.x, randEndPoint.x); i++) {
						field[i][s.start.y] = true;
					}
				}
				// Set to true after placed
				isSet = true;
				// Clear possibleEndPoints
				possibleEndPoints.clear();
			}
		}
	}

	/*
	 * This method takes a parameter of type Ship and places it onto the field based
	 * on the Ship's start and end points
	 */
	public void fillField(Ship s) {
		if (s.start.x == s.end.x) { // Horizontal
			for (int i = Math.min(s.start.y, s.end.y); i <= Math.max(s.start.y, s.end.y); i++) {
				field[s.start.x][i] = true;
			}
		} else { // Vertical
			for (int i = Math.min(s.start.x, s.end.x); i <= Math.max(s.start.x, s.end.x); i++) {
				field[i][s.start.y] = true;
			}
		}
	}

	/*
	 * This method checks if there will be an intersection between ships upwardly
	 */
	public boolean placeUp(int r, int c, Ship s) {
		for (int i = r; i >= r - (s.length - 1); i--) {
			if (field[i][c])
				return false;
		}
		return true;
	}

	/*
	 * This method checks if there will be an intersection between ships toward the
	 * right
	 */
	public boolean placeRight(int r, int c, Ship s) {
		for (int i = c; i <= c + (s.length - 1); i++) {
			if (field[r][i])
				return false;
		}
		return true;
	}

	/*
	 * This method checks if there will be an intersection between ships downwardly
	 */
	public boolean placeDown(int r, int c, Ship s) {
		for (int i = r; i <= r + (s.length - 1); i++) {
			if (field[i][c])
				return false;
		}
		return true;
	}

	/*
	 * This method checks if there will be an intersection between ships toward the
	 * left
	 */
	public boolean placeLeft(int r, int c, Ship s) {
		for (int i = c; i >= c - (s.length - 1); i--) {
			if (field[r][i])
				return false;
		}
		return true;
	}

	/*
	 * This method sets each field value to false
	 */
	public void restart() {
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				field[i][j] = false;
			}
		}
	}
}
