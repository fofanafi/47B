package solver;

public class Debug {

	private static boolean initialInfo = false;
	private static boolean play = false;
	private static boolean addBlock = false;
	private static boolean voiceover = false;
	
	public static void setDebug(String info) {
		if(info.contains("i")) {
			initialInfo = true;
		}
		if(info.contains("p")) {
			play = true;
		}
		if(info.contains("a")) {
			addBlock = true;
		}
		if(info.contains("v")) {
			voiceover = true;
		}
	}

	public static void printOptions() {
		
	}

	public static boolean printInitialInfo() {
		return initialInfo;
	}
	
	public static boolean play() {
		return play;
	}

	public static boolean addBlock() {
		return addBlock;
	}

	public static boolean voiceover() {
		return voiceover;
	}
}
