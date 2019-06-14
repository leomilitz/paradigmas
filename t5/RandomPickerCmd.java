import java.io.*;


class RandomPickerCmd {

	public static void main(String[] args) {	
		File file = new File(args[0]);
		ListShuffle list = new ListShuffle(file);

		list.shuffle();
	
		list.printCmd();
	}
}