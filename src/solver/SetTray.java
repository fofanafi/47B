package solver;


import java.util.Arrays;
import java.util.Collection;

import basic.Block;
import basic.Direction;
import basic.Move;

public class SetTray extends Tray {
	private TrayState blocks;

	public SetTray(int length, int width) {
		super(length, width);
		blocks = new TrayState();
	}
	
	public SetTray(int length, int width, TrayState blocks) {
		super(length, width);
		this.blocks = blocks;
		super.setUpArray(blocks);
	}
	
	@Override
	public TrayState getState() {
		return blocks;
	}
	
	@Override
	public Collection<Block> getBlocks() {
		return blocks;
	}
	
	@Override
	public boolean canMove(int blockNum, Direction d) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void move(int blockNum, Direction d) {
		throw new UnsupportedOperationException();
	}

	public static void main(String[] args) {
		Tray tray;
		Block b, b2;
		boolean exceptionThrown;
		
		tray = new SetTray(1,1);
		b = tray.addBlock(1, 1, 0, 0);
		assert(tray.canMove(b, Direction.Left) == false);
		assert(tray.canMove(b, Direction.Right) == false);
		assert(tray.canMove(b, Direction.Up) == false);
		assert(tray.canMove(b, Direction.Down) == false);
		
		tray = new SetTray(2,2);
		b = tray.addBlock(1, 1, 0, 1);
		tray.printAll();
		assert(tray.canMove(b, Direction.Left));
		assert(tray.canMove(b, Direction.Down));
		assert(!tray.canMove(b, Direction.Up));
		assert(!tray.canMove(b, Direction.Right));
		tray.move(b, Direction.Left);
		assert(!tray.canMove(b, Direction.Left));
		assert(tray.canMove(b, Direction.Down));
		assert(!tray.canMove(b, Direction.Up));
		assert(tray.canMove(b, Direction.Right));
		b2 = tray.addBlock(1, 1, 0, 1);
		tray.printAll();
		assert(!tray.canMove(b, Direction.Right));
		assert(!tray.canMove(b2, Direction.Left));
		assert(tray.canMove(b2, Direction.Down));
		assert(!tray.canMove(b2, Direction.Up));
		assert(!tray.canMove(b2, Direction.Right));
		exceptionThrown = false;
		try {
			tray.move(b2, Direction.Left);
		} catch (BlockException e) {
			exceptionThrown = true;
		} finally {
			assert(exceptionThrown);
		}
		
		tray = new SetTray(4,3);
		b = tray.addBlock(2, 1, 0, 0);
		tray.printAll();
		assert(!tray.canMove(b, Direction.Left));
		assert(tray.canMove(b, Direction.Down));
		assert(!tray.canMove(b, Direction.Up));
		assert(tray.canMove(b, Direction.Right));
		tray.move(b, Direction.Down);
		tray.printAll();
		assert(!tray.canMove(b, Direction.Left));
		assert(tray.canMove(b, Direction.Down));
		assert(tray.canMove(b, Direction.Up));
		assert(tray.canMove(b, Direction.Right));
		tray.move(b, Direction.Down);
		tray.printAll();
		assert(!tray.canMove(b, Direction.Left));
		assert(!tray.canMove(b, Direction.Down));
		assert(tray.canMove(b, Direction.Up));
		assert(tray.canMove(b, Direction.Right));
		tray.move(b, Direction.Right);
		tray.move(b, Direction.Right);
		assert(tray.canMove(b, Direction.Left));
		assert(!tray.canMove(b, Direction.Down));
		assert(tray.canMove(b, Direction.Up));
		assert(!tray.canMove(b, Direction.Right));
		tray.printAll();
		b2 = tray.addBlock(1, 2, 1, 0);
		assert(!tray.canMove(b2, Direction.Left));
		assert(tray.canMove(b2, Direction.Down));
		assert(tray.canMove(b2, Direction.Up));
		assert(tray.canMove(b2, Direction.Right));
		tray.printAll();
		tray.move(b2, Direction.Right);
		assert(tray.canMove(b2, Direction.Left));
		assert(!tray.canMove(b2, Direction.Down));
		assert(tray.canMove(b2, Direction.Up));
		assert(!tray.canMove(b2, Direction.Right));
		assert(!tray.canMove(b, Direction.Up));
		tray.printAll();
		
		Move[] moves = {new Move(b, Direction.Left), new Move(b2, Direction.Left),
				new Move(b2, Direction.Up)};
		System.out.println("Possible moves:\n" + tray.getPossibleMoves() + "\n");
		assert(tray.getPossibleMoves().containsAll(Arrays.asList(moves)));
		assert(Arrays.asList(moves)).containsAll(tray.getPossibleMoves());
		
		exceptionThrown = false;
		try {
			b = tray.addBlock(2, 2, 0, 0);
		} catch (BlockException e) {
			exceptionThrown = true;
		} finally {
			assert(exceptionThrown);
		}
		
		tray = new SetTray(3, 5);
		b = tray.addBlock(2, 3, 0, 0);
		b2 = tray.addBlock(1, 3, 2, 2);
		tray.printAll();
		assert(!tray.canMove(b, Direction.Left));
		assert(!tray.canMove(b, Direction.Down));
		assert(!tray.canMove(b, Direction.Up));
		assert(tray.canMove(b, Direction.Right));
		assert(tray.canMove(b2, Direction.Left));
		assert(!tray.canMove(b2, Direction.Down));
		assert(!tray.canMove(b2, Direction.Up));
		assert(!tray.canMove(b2, Direction.Right));
		
		System.out.println("Possible moves:\n" + tray.getPossibleMoves() + "\n");
		
		Tray goalTray = new SetTray(5,6);
		goalTray.addBlock(2, 2, 0, 0);
		goalTray.addBlock(2, 2, 0, 4);
		
		tray = new SetTray(5,6);
		tray.addBlock(2, 2, 0, 4);
		tray.addBlock(2, 2, 0, 0);
		tray.addBlock(1, 3, 3, 0);
		
		System.out.println(goalTray);
		System.out.println(tray);
		
		assert(tray.reachedGoal(goalTray));
		
		// checks assert on
		assert(false);
	}
}
