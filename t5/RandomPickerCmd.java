import java.util.Scanner;

class RandomPickerCmd {

	public static void main(String[] args) {	
		ListShuffle list = new ListShuffle(args[0]);
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Would you like an online shuffle (random.org)? \nThen, input 1. Otherwise, input other number.");
		int resp = scan.nextInt();

		switch (resp) {
			case 1: 
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