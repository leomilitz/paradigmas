import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.io.FileNotFoundException;

class RandomPickerCmd {

	public static void main(String[] args) {	
		try {
			File file = new File(args[0]);
		    Scanner scanner = new Scanner(file);
	      	Scanner sysPause = new Scanner(System.in); // scanner usado para pausar a execução no console
			ArrayList<String> nameList = new ArrayList<String>();
			
			// Adiciona os elementos do txt no array list
			while (scanner.hasNextLine()) {
	        	nameList.add(scanner.nextLine()); 
	      	}

	      	Collections.shuffle(nameList); // Embaralha a lista de elementos

	      	// Imprime os nomes embaralhados um por um
	      	System.out.println("\nShuffled " + args[0] + ":\n");
	      	for (int i=0 ; i<nameList.size() ; i++) {
	      		
	      		if (i < nameList.size() - 1)
	      			System.out.println("\t- " + nameList.get(i) + ";");
	      		else
	      			System.out.println("\t- " + nameList.get(i) + ".");

	      		sysPause.nextLine();
	      	}

	      	scanner.close();
	    }
	    catch (FileNotFoundException e) {
      		System.out.println("An error occurred.");
      		e.printStackTrace();
    	} 
	}
}