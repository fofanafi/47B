package ai;


/**
 * Functional for calculating a heuristic that estimates the total number of moves
 * required to get from tray to goalTray.
 */
public interface Heuristic<S extends State> {

	/**
	 * Calculates a heuristic that estimates the total number of moves
	 * required to get from tray to goalTray.
	 */
	public int calcHeuristic(S current, S goal);
}
