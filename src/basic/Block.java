package basic;

import solver.Tray.BlockException;


public class Block {
	private final int length; // number of rows
	private final int width; // number of cols
	private int row; // of upper left corner of block
	private int col; // of upper left corner of block
	private final int blockNum; // used in printing

	public Block(int length, int width, int row, int col, int blockNum) {
		this.length = length;
		this.width = width;
		this.row = row;
		this.col = col;
		this.blockNum = blockNum;
	}
	
	public Block(Block b) {
		this.length = b.length;
		this.width = b.width;
		this.row = b.row;
		this.col = b.col;
		this.blockNum = b.blockNum;
	}
	
	public int getLength() {
		return length;
	}

	public int getWidth() {
		return width;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public void setCol(int col) {
		this.col = col;
	}
	
	public void moveBlock(Direction d) {
		switch(d) {
		case Left:
			this.setCol(col - 1);
			break;
		case Right:
			this.setCol(col + 1);
			break;
		case Up:
			this.setRow(row - 1);
			break;
		case Down:
			this.setRow(row + 1);
			break;
		default: throw new BlockException();
		}
	}

	public int getBlockNum() {
		return blockNum;
	}
	
	public boolean isSameSizeAs(Block other) {
		return this.length == other.length && this.width == other.width;
	}
	
	public int rowDist(Block other) {
		return Math.abs(row - other.row);
	}
	
	public int colDist(Block other) {
		return Math.abs(col - other.col);
	}
	
	public boolean isAtSamePositionAs(Block other) {
		return this.row == other.row && this.col == other.col;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Block)) {
			return false;
		}
		Block other = (Block) obj;
		return isSameSizeAs(other) && isAtSamePositionAs(other);
	}
	
	@Override
	public int hashCode() {
		int hash = 1;
		hash = hash * 17 + length;
        hash = hash * 31 + width;
        hash = hash * 13 + row;
        hash = hash * 19 + col;
        return hash;
	}

	@Override
	public String toString() {
		return String.format("%d. length: %d width: %d row: %d col: %d", 
				blockNum, length, width, row, col);
	}
}
