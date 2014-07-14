package solver;


import java.util.Collection;
import java.util.HashSet;

import ai.State;
import basic.Block;

@SuppressWarnings("serial")
public class TrayState extends HashSet<Block> implements State {
	
	public TrayState() {
		super();
	}
	
	public TrayState(Collection<Block> collection) {
		super(collection);
	}
}
