package basic;


public class Move {
	
	private Block block;
	private Direction direction;
	
	public Move(Block b, Direction d) {
		this.setBlock(b);
		this.setDirection(d);
	}

	public Block getBlock() {
		return block;
	}

	private void setBlock(Block b) {
		this.block = b;
	}

	public Direction getDirection() {
		return direction;
	}

	private void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	@Override
	public String toString() {
		return "{" + Integer.toString(block.getBlockNum()) + "," + direction + "}";
	}
	
	@Override
	public boolean equals(Object obj) {
		Move other = (Move) obj;
		return this.block == other.block && this.direction == other.direction;
	}
}
