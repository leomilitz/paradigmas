import java.util.Scanner;
import java.io.*;


class RandomPickerCmd {

	public static void main(String[] args) {	
		File file = new File(args[0]);
		ListShuffle list = new ListShuffle(file);

		Scanner scan = new Scanner(System.in);
		
		System.out.println("Would you like an online shuffle (random.org)? \nThen, input 1.");
		String resp = scan.nextLine();

		switch (resp) {
			case "1": 
				System.out.println("\nOnline Shuffled " + args[0] + ":\n");
				list.shuffleOnline(); 
				break;
			default:
				System.out.println("\nOffline Shuffled " + args[0] + ":\n");
				list.shuffleOffline();
				break;		
		}
	
		list.printCmd();
	}
}