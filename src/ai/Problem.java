package ai;

import java.util.List;

public interface Problem<S extends State> {
	public S getInitialState();
	public boolean reachedGoal(S state);
	public S getGoal();
	List<Config<S>> getSuccessors(Config<S> config);
}
