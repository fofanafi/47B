package solver;

import java.util.ArrayList;
import java.util.List;

import ai.Config;
import ai.Problem;
import basic.Block;
import basic.Move;

public class BlocksProblem implements Problem<TrayState> {
	TrayState initial;
	TrayState goal;
	int length;
	int width;
	
	public BlocksProblem(Tray initial, Tray goal) {
		this.initial = initial.getState();
		this.goal = goal.getState();
		this.length = initial.getLength();
		this.width = initial.getWidth();
	}

	@Override
	public TrayState getInitialState() {
		return initial;
	}

	@Override
	public boolean reachedGoal(TrayState state) {
		TrayState tray = (TrayState) state;
		return tray.containsAll(goal);
	}

	@Override
	public TrayState getGoal() {
		return goal;
	}

	@Override
	public List<Config<TrayState>> getSuccessors(Config<TrayState> config) {
		// Get possible moves
		SetTray tray = new SetTray(length, width, config.getState());
		List<Move> possibleMoves = tray.getPossibleMoves();
		
		List<Config<TrayState>> successors = new ArrayList<Config<TrayState>>();
		// For each move, generate a new config
		for (Move move : possibleMoves) {
			TrayState nextState = makeNextState(config.getState(), move);
			List<Move> nextMoves = new ArrayList<Move>(config.getMoves());
			nextMoves.add(move);
			
			successors.add(new Config<TrayState>(nextState, nextMoves));
		}
		
		return successors;
	}

	private TrayState makeNextState(TrayState state, Move move) {
		TrayState nextState = new TrayState(state);
		Block block = move.getBlock();
		assert(nextState.remove(block));
	
		Block movedBlock = new Block(block);
		movedBlock.moveBlock(move.getDirection());
		nextState.add(movedBlock);
		return nextState;
	}

}
