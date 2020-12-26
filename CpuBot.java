import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CpuBot {
	// Declaration and Initialization
	List<Point> vicinity = new ArrayList<>(); // Store adjacent points
	List<Point> guessed = new ArrayList<>(); // Store points already guessed
	Random rand = new Random();
	Point originalHit = new Point(); // The first hit
	Point previousHit = new Point(); // The second hit

	/*
	 * This method randomly generates a point to guess and returns it as a Point
	 */
	public Point guess() {
		Point pGuess = new Point(); // Declare and initialize guessing Point
		if (!vicinity.isEmpty()) { // If vicinity contains points
			pGuess = vicinity.get(0); // Set guessing Point to adjacent point
		} else { // If vicinity is empty
			pGuess.x = rand.nextInt(10); // Generate random x value of Point
			pGuess.y = rand.nextInt(10); // Generate random y value of Point
			// If generated point has already been guessed, generate new point
			while (guessed.contains(pGuess)) {
				pGuess.x = rand.nextInt(10);
				pGuess.y = rand.nextInt(10);
			}
		}
		return pGuess; // Return guessed Point
	}

	/*
	 * This method adds all available Points that are adjacent to the Point the
	 * CpuBot has chosen
	 */
	public void addAllVicinity(Point cpuGuess) {
		Point nextPoint;
		if (cpuGuess.x - 1 >= 0) { // Up
			nextPoint = new Point(cpuGuess.x - 1, cpuGuess.y); // The upward adjacent Point
			if (!guessed.contains(nextPoint)) // If upward adjacent point has not been guessed
				vicinity.add(nextPoint); // add Point to vicinity
		}
		if (cpuGuess.y + 1 <= 9) { // Right
			nextPoint = new Point(cpuGuess.x, cpuGuess.y + 1); // The right adjacent Point
			if (!guessed.contains(nextPoint)) // If right adjacent point has not been guessed
				vicinity.add(nextPoint); // add Point to vicinity
		}
		if (cpuGuess.x + 1 <= 9) { // Down
			nextPoint = new Point(cpuGuess.x + 1, cpuGuess.y); // The downward adjacent Point
			if (!guessed.contains(nextPoint)) // If downward adjacent point has not been guessed
				vicinity.add(nextPoint); // add Point to vicinity
		}
		if (cpuGuess.y - 1 >= 0) { // Left
			nextPoint = new Point(cpuGuess.x, cpuGuess.y - 1); // The left adjacent Point
			if (!guessed.contains(nextPoint)) // If left adjacent point has not been guessed
				vicinity.add(nextPoint); // add Point to vicinity
		}
	}

	/*
	 * This method adds only the necessary Points that are adjacent to the CpuBot's
	 * selection
	 */
	public void addMinVicinity() {
		Point nextPoint;
		if (originalHit.x == previousHit.x) { // If hits are horizontal
			vicinity.removeIf(p -> (p.x != originalHit.x));
			if (previousHit.y + 1 <= 9) { // Right
				nextPoint = new Point(previousHit.x, previousHit.y + 1); // The right adjacent Point
				if (!guessed.contains(nextPoint)) // If right adjacent point has not been guessed
					vicinity.add(nextPoint); // add Point to vicinity
			}
			if (previousHit.y - 1 >= 0) { // Left
				nextPoint = new Point(previousHit.x, previousHit.y - 1); // The left adjacent Point
				if (!guessed.contains(nextPoint)) // If left adjacent point has not been guessed
					vicinity.add(nextPoint); // add Point to vicinity
			}
		} else if (originalHit.y == previousHit.y) { // If hits are vertical
			vicinity.removeIf(p -> (p.y != originalHit.y));
			if (previousHit.x - 1 >= 0) { // Up
				nextPoint = new Point(previousHit.x - 1, previousHit.y); // The upward adjacent Point
				if (!guessed.contains(nextPoint)) // If upward adjacent point has not been guessed
					vicinity.add(nextPoint); // add Point to vicinity
			}
			if (previousHit.x + 1 <= 9) { // Down
				nextPoint = new Point(previousHit.x + 1, previousHit.y); // The downward adjacent Point
				if (!guessed.contains(nextPoint)) // If downward adjacent point has not been guessed
					vicinity.add(nextPoint); // add Point to vicinity
			}
		}
	}

	/*
	 * This method resets all necessary values of the CpuBot
	 */
	public void restart() {
		vicinity.clear();
		guessed.clear();
		rand = new Random();
		originalHit = new Point();
		previousHit = new Point();
	}
}
