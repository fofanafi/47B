package solver;

public class Debug {

	private boolean initialInfo = false;
	private boolean play = false;
	
	public Debug(String info) {
		if(info.contains("i")) {
			initialInfo = true;
		}
		if(info.contains("p")) {
			play = true;
		}
	}

	public static void printOptions() {
		
	}

	public boolean printInitialInfo() {
		return initialInfo;
	}
	
	public boolean play() {
		return play;
	}
}
