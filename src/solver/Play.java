package solver;



import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import basic.Direction;

public class Play implements KeyListener {
	private Tray tray;
	private Tray goal;
	private int blockNum = 0;
	
	public Play(Tray tray, Tray goal) {
		this.tray = tray;
		this.goal = goal;
	}

	@Override
	public void keyPressed(KeyEvent ke) {
		int c = ke.getKeyCode();
		Direction dir;
		
		// act on number or arrow
		// number: change block num and print it.
		if (c >= 48 && c <= 57) {
			blockNum = c - 48;
			println("Currently moving block " + blockNum + "\n");
			return;
		}
		
		// arrow: move block and print tray.
        if (c==KeyEvent.VK_UP) {                
            dir = Direction.Up;
        } else if(c==KeyEvent.VK_DOWN) {                
            dir = Direction.Down;
        } else if(c==KeyEvent.VK_LEFT) {                
            dir = Direction.Left;
        } else if(c==KeyEvent.VK_RIGHT) {                
            dir = Direction.Right;
        } else {
        	return;
        }
        
        if (!tray.canMove(blockNum, dir)) {
        	println("Can't move block\n");
        } else {
        	tray.move(blockNum, dir);
        	println(tray.toString());
        	if (tray.reachedGoal(goal)) {
        		println("You reached the goal! Congratulations!\n");
        		System.exit(0);
        	}
        }
	}
	
	private void println(String s) {
		System.out.println(s);
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
