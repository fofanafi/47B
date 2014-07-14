package basic;


/**
 * Direction of movement of block.
 */
public enum Direction { 
	Left(Direction.RowDir.Col),
	Right(Direction.RowDir.Col),
	Up(Direction.RowDir.Row),
	Down(Direction.RowDir.Row);
	
	public static enum RowDir { Row, Col }

	private final Direction.RowDir dirOfRowToMove; // direction of row to move, either col or row
	
	Direction(Direction.RowDir rowDir) {
		this.dirOfRowToMove = rowDir;
	}
	
	public Direction.RowDir getDirOfRowToMove() {
		return dirOfRowToMove;
	}
}