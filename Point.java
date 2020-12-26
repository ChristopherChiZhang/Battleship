
public class Point {
	// Declaration
	int x, y; // X and y coordinates of a Point

	public Point() { // Point Constructor
		x = 0;
		y = 0;
	}

	public Point(int x, int y) { // Point Constructor
		this.x = x;
		this.y = y;
	}

	/*
	 * This method overrides the toString method to display Points as text, used for
	 * testing purposes
	 */
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

	/*
	 * This method overrides the equals method. It returns true if two Point objects
	 * are equal, false otherwise
	 */
	public boolean equals(Object obj) {
		Point other = (Point) obj;
		if (obj == null)
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	/*
	 * This method creates a hashCode for each object.
	 */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}
}
