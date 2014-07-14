import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import solver.BlocksProblem;
import solver.Debug;
import solver.SimpleManhattanDistanceHeuristic;
import solver.Tray;
import solver.TrayState;
import ai.Config;
import ai.Heuristic;
import ai.Search;
import basic.Direction;
import basic.Move;

public class Solver {
	
	private static Debug debug;
	private static Tray initialTray;
	private static Tray goalTray;

	public static void main(String[] args) throws IOException {
		// Read in arguments
		try{
			if (args.length == 3 && args[0].startsWith("-o")) {
				readDebuggingInfo(args[0].substring(2));
				readInitialConfigFile(args[1]);
				readGoalConfigFile(args[2]);
			} else if (args.length == 2) {
				readDebuggingInfo("");
				readInitialConfigFile(args[0]);
				readGoalConfigFile(args[1]);
			} else {
				System.out.println("Usage: java Solver -oinfo init goal");
				return;
			}
		} catch (FileNotFoundException e) {
			System.out.println(e.getLocalizedMessage());
			return;
		}
		
		if(debug.printInitialInfo()) {
			System.out.print("Initial Tray:\n" + initialTray.printAllToString());
			System.out.println("Goal:\n" + goalTray.printAllToString());
		}
		
		if(debug.play()) {
			play();
    		System.exit(0);
		}
		
		// Construct a new problem.
		BlocksProblem problem = new BlocksProblem(initialTray, goalTray);
		// Attempt to solve the problem
		Search<TrayState> search = new Search<TrayState>(problem);
		Heuristic<TrayState> heuristic = new SimpleManhattanDistanceHeuristic();
		Config<TrayState> solution = search.doSearch(heuristic);
		
		// If no solution, exit
		if (solution == null) {
			System.out.println("no solution");
			System.exit(1);
		} else { // Else print the list of moves
			assert(problem.reachedGoal(solution.getState()));
			for (Move move : solution.getMoves()) {
				initialTray.move(move);
				System.out.println(initialTray);
				System.in.read(); // wait for enter
			}
			System.out.println("reached goal");
			System.exit(0);
		}
	}
	
	private static void play() {
		int blockNum = 0;
		Tray tray = initialTray;
		
		String instr = "Play the blocks game! Press a number to choose the block to move.\n" +
				"Use wasd to move that block. Hit 'g' to show the goal tray,\n" +
				"'t' to show the tray as it is now. Press enter to continue";
		System.out.print(instr);
		
		// Wait for enter
		Scanner reader = new Scanner(System.in);
		reader.nextLine();
		
		System.out.print("Goal:\n" + goalTray + "Tray:\n" + tray);
		System.out.println("Currently moving block " + blockNum);
		
		while(!tray.reachedGoal(goalTray)) {
			// read in number or arrow
			char k = reader.next().charAt(0);
			
			// act on number or arrow
			// number: change block num and print it.
			if (k >= 48 && k <= 57) {
				blockNum = k - 48;
				System.out.println("Currently moving block " + blockNum);
			}
			// arrow: move block and print tray.
			Direction dir;
			switch(k) {
			case 'w':
				dir = Direction.Up;
				break;
			case 'a':
				dir = Direction.Left;
				break;
			case 's':
				dir = Direction.Down;
				break;
			case 'd':
				dir = Direction.Right;
				break;
			case 'g':
				System.out.print("Goal:\n" + goalTray);
				continue;
			case 't':
				System.out.print("Tray:\n" + tray);
				continue;
			default: continue;
			}
			
			if (!tray.canMove(blockNum, dir)) {
	        	System.out.println("Can't move block");
	        } else {
	        	tray.move(blockNum, dir);
	        	System.out.print(tray.toString());
	        	if (tray.reachedGoal(goalTray)) {
	        		System.out.println("You reached the goal! Congratulations!");
	        	}
	        }
		}
	}

	private static void readDebuggingInfo(String info) {
		if (info.equals("options")) {
			Debug.printOptions();
			System.exit(0);
		} else {
			debug = new Debug(info);
		}
	}

	private static void readInitialConfigFile(String initialConfig) throws FileNotFoundException {
		File file = new File(initialConfig);
		Scanner sc = new Scanner(file);
		int length = sc.nextInt();
		int width = sc.nextInt();
		initialTray = new Tray(length, width);
		while(sc.hasNext()) {
			int blockLength = sc.nextInt();
			int blockWidth = sc.nextInt();
			int row = sc.nextInt();
			int col = sc.nextInt();
			initialTray.addBlock(blockLength, blockWidth, row, col);
		}
	}

	private static void readGoalConfigFile(String goalConfig) throws FileNotFoundException {
		File file = new File(goalConfig);
		Scanner sc = new Scanner(file);
		goalTray = new Tray(initialTray.getLength(), initialTray.getWidth());
		while(sc.hasNext()) {
			int blockLength = sc.nextInt();
			int blockWidth = sc.nextInt();
			int row = sc.nextInt();
			int col = sc.nextInt();
			goalTray.addBlock(blockLength, blockWidth, row, col);
		}
	}

}
