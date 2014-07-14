package solver;

import basic.Block;
import ai.Heuristic;

/**
 * A heuristic that calculates for each goal block, the distance to the closest
 * matching block in the tray, and returns a sum of these.
 */
public class SimpleManhattanDistanceHeuristic implements Heuristic<TrayState> {

	@Override
	public int calcHeuristic(TrayState current, TrayState goal) {
		
		int sum = 0;
		for (Block gb: goal) {
			int minDist = Integer.MAX_VALUE;
			for (Block b: current) {
				if (b.isSameSizeAs(gb)) {
					int dist = manhattanDistance(gb, b);
					if (dist < minDist) {
						minDist = dist;
					}
				}
			}
			if (minDist == Integer.MAX_VALUE) { // no matching block found
				System.exit(1);
			} else {
				sum += minDist;
			}
		}
		
		return sum;
	}
	
	/**
	 * The manhattan distance between two blocks.
	 */
	public int manhattanDistance(Block b1, Block b2) {
		return b1.rowDist(b2) + b1.colDist(b2);
	}

}
