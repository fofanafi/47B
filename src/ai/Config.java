package ai;


import java.util.ArrayList;
import java.util.List;

import basic.Move;

public class Config<S extends State> {

	private S state;
	private List<Move> moves;
	
	public Config(S state, List<Move> moves) {
		this.state = state;
		this.moves = moves;
	}
	
	public Config(S state) {
		this.state = state;
		this.moves = new ArrayList<Move>();
	}
	
	public S getState() {
		return state;
	}

	public List<Move> getMoves() {
		return moves;
	}
}
