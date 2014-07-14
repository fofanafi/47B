package ai;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class Search<S extends State> {
	private Set<State> prevSeen = new HashSet<State>();
	private Queue<Config<S>> fringe;
	private Problem<S> problem;
	
	public Search(Problem<S> problem) {
		this.problem = problem;
	}
	
	private static class HeuristicComparator<S extends State> implements Comparator<Config<S>> {
		private Heuristic<S> heuristic;
		private Map<State, Integer> heuristicInfo; // cache of heuristics
		private S goal;
		
		public HeuristicComparator(Heuristic<S> h, S goal) {
			this.heuristic = h;
			this.goal = goal;
			this.heuristicInfo = new HashMap<State, Integer>();
		}

		@Override
		public int compare(Config<S> c1, Config<S> c2) {
			Integer h1 = heuristicInfo.get(c1);
			if (h1 == null) {
				h1 = heuristic.calcHeuristic(c1.getState(), goal);
				heuristicInfo.put(c1.getState(), h1);
			}
			Integer h2 = heuristicInfo.get(c2);
			if (h2 == null) {
				h2 = heuristic.calcHeuristic(c2.getState(), goal);
				heuristicInfo.put(c2.getState(), h2);
			}
			
			return h1 - h2;
		}
	}
	
	/**
	 * Finds a set of moves between initial and goal.
	 * 
	 * Returns null if no such moves exist.
	 */
	public Config<S> doSearch(Heuristic<S> heuristic) {
		Comparator<Config<S>> heuristicComparator = new HeuristicComparator<S>(heuristic, problem.getGoal());
		fringe = new PriorityQueue<Config<S>>(11, heuristicComparator);
		fringe.add(new Config<S>(problem.getInitialState()));
		
		while(true) {
			if (fringe.isEmpty()) {
				return null;
			}
			Config<S> config = fringe.remove();
			if (problem.reachedGoal(config.getState())) {
				return config;
			}
			if (!prevSeen.contains(config.getState())) {
				prevSeen.add(config.getState());
				for (Config<S> child : problem.getSuccessors(config)) {
					fringe.add(child);
				}
			}
		}
	}
}
