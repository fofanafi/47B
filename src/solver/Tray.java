package solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import basic.Block;
import basic.Direction;
import basic.Move;

public class Tray {
	private int length; // number of rows
	private int width; // number of cols
	private List<Block> blocks;
	private Block[][] array;
	private List<Move> possibleMoves;
	
	public Tray(int length, int width) {
		this.length = length;
		this.width = width;
		blocks = new ArrayList<Block>();
		array = new Block[length][width];
	}
	
	public void setUpArray(Collection<Block> blocks) {
		for (Block b : blocks) {
			addToArray(b);
		}
	}

	public TrayState getState() {
		return new TrayState(getBlocks());
	}

	/**
	 * Whether this tray contains all the blocks in Tray goal in the correct positions.
	 */
	public boolean reachedGoal(Tray goal) {
		return getBlocks().containsAll(goal.getBlocks());
	}

	public List<Move> getPossibleMoves() {
		if (possibleMoves == null) {
			generatePossibleMoves();
		}
		return possibleMoves;
	}
	
	public void generatePossibleMoves() {
		possibleMoves = new ArrayList<Move>();
		for (Block b : getBlocks()) {
			for (Direction d : Direction.values()) {
				if (canMove(b, d)) {
					possibleMoves.add(new Move(b, d));
				}
			}
		}
	}
	
	public Block addBlock(int length, int width, int row, int col) {
		Block b = new Block(length, width, row, col, getBlocks().size());
		addBlock(b);
		return b;
	}
	
	private void addBlock(Block b) {
		getBlocks().add(b);
		addToArray(b);
	}
	
	private void addToArray(Block b) {
		if (Debug.addBlock()) {
			System.out.print("Adding block " + b + "\nBefore:\n" + this);
		}
		int row = b.getRow();
		int col = b.getCol();
		for( int i = row; i < row + b.getLength(); i++ ) {
			for( int j = col; j < col + b.getWidth(); j++ ) {
				if (array[i][j] != null) {
					throw new BlockException();
				}
				array[i][j]	= b;
			}
		}
		if (Debug.addBlock()) {
			System.out.println("After:\n" + this);
		}
	}

	public Collection<Block> getBlocks() {
		return blocks;
	}

	public int getLength() {
		return length;
	}

	public int getWidth() {
		return width;
	}
	
	public boolean canMove(int blockNum, Direction d) {
		return canMove(blocks.get(blockNum), d);
	}
	
	/**
	 * Checks if Block b can be moved in Direction d.
	 */
	public boolean canMove(Block b, Direction d) {
		int colToMoveInto = colToMoveInto(b, d);
		int firstRow; // the first row of the column to move
		int lengthOfCol; // the length of the column to move
		int endColForDir; // the last column for the given direction
		if (d.getDirOfRowToMove() == Direction.RowDir.Row) { // moving a row (up, down)
			firstRow = b.getCol();
			lengthOfCol = b.getWidth();
			endColForDir = this.getLength();
		} else { // moving a col (left, right)
			firstRow = b.getRow();
			lengthOfCol = b.getLength();
			endColForDir = this.getWidth();
		}
		
		// check if at boundary
		boolean atBoundary;
		switch (d) {
		case Left:
		case Up:
			atBoundary = colToMoveInto < 0;
			break;
		case Down:
		case Right:
			atBoundary = colToMoveInto == endColForDir;
			break;
		default: throw new BlockException();
		}
		
		if (atBoundary)
			return false;
		
		// check that column can be moved
		Block spaceToMoveInto;
		for( int i = firstRow; i < firstRow + lengthOfCol; i++ ) {
			if (d.getDirOfRowToMove() == Direction.RowDir.Col) {
				spaceToMoveInto = array[i][colToMoveInto];
			} else {
				spaceToMoveInto = array[colToMoveInto][i];
			}
			
			if( spaceToMoveInto != null ) {
				return false;
			}
		}
		
		return true;
	}
	
	public void move(int blockNum, Direction d) {
		move(blocks.get(blockNum), d);
	}
	
	public void move(Move move) {
		move(move.getBlock(), move.getDirection());
	}
	
	/**
	 * Move Block b in Direction d. Throws an exception if block cannot be moved.
	 */
	public void move(Block b, Direction d) {
		if (!canMove(b, d)) {
			throw new BlockException();
		}
		int colToMoveInto = colToMoveInto(b, d);
		int colToMoveOutOf = colToMoveOutOf(b, d);
		// Get top left corner.
		int topLeftRow = b.getRow();
		int topLeftCol = b.getCol();
		
		// In array, add block to all of column to move into.
		// In array, delete block from all of column to move out of.
		if (d.getDirOfRowToMove() == Direction.RowDir.Col) { // left, right
			for( int i = topLeftRow; i < topLeftRow + b.getLength(); i++ ) {
				array[i][colToMoveInto] = b;
				array[i][colToMoveOutOf] = null;
			}
		} else { // up, down
			for( int i = topLeftCol; i < topLeftCol + b.getWidth(); i++ ) {
				array[colToMoveInto][i] = b;
				array[colToMoveOutOf][i] = null;
			}
		}
		
		b.moveBlock(d);
	}

	private int colToMoveInto(Block b, Direction d) {
		switch (d) {
		case Left: return b.getCol() - 1;
		case Right: return b.getCol() + b.getWidth();
		case Up: return b.getRow() - 1;
		case Down: return b.getRow() + b.getLength();
		default: throw new BlockException();
		}
	}

	private int colToMoveOutOf(Block b, Direction d) {
		switch (d) {
		case Left: return b.getCol() + b.getWidth() - 1;
		case Right: return b.getCol();
		case Up: return b.getRow() + b.getLength() - 1;
		case Down: return b.getRow();
		default: throw new BlockException();
		}
	}

	@Override
	public String toString() {
		String s = "";
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < width; j++) {
				if (array[i][j] == null) {
					s += "* ";
				} else {
					s += Integer.toString(array[i][j].getBlockNum()) + " ";
				}
			}
			s += "\n";
		}
		
		return s;
	}
	
	public String printAllToString() {
		String s = String.format("length: %d width: %d\n", length, width);
		for (Block b : getBlocks()) {
			s += b + "\n";
		}
		
		s += this;
		
		return s;
	}
	
	@SuppressWarnings("serial")
	public static class BlockException extends RuntimeException {
	}
	
	public static void main(String[] args) {
		Tray tray;
		Block b, b2;
		boolean exceptionThrown;
		
		tray = new Tray(1,1);
		b = tray.addBlock(1, 1, 0, 0);
		assert(tray.canMove(b, Direction.Left) == false);
		assert(tray.canMove(b, Direction.Right) == false);
		assert(tray.canMove(b, Direction.Up) == false);
		assert(tray.canMove(b, Direction.Down) == false);
		
		tray = new Tray(2,2);
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
		
		tray = new Tray(4,3);
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
		
		tray = new Tray(3, 5);
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
		
		Tray goalTray = new Tray(5,6);
		goalTray.addBlock(2, 2, 0, 0);
		goalTray.addBlock(2, 2, 0, 4);
		
		tray = new Tray(5,6);
		tray.addBlock(2, 2, 0, 4);
		tray.addBlock(2, 2, 0, 0);
		tray.addBlock(1, 3, 3, 0);
		
		System.out.println(goalTray);
		System.out.println(tray);
		
		assert(tray.reachedGoal(goalTray));
		
		// checks assert on
		assert(false);
	}

	public void printAll() {
		System.out.println(printAllToString());
	}
}
