import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.scene.control.ListView;

public class Analyzer {
	private ArrayList<String> urlList;

	public void setUrlList(File file) {
		try {
			Scanner scanner  = new Scanner(file);
			this.urlList = new ArrayList<String>();
			
			while (scanner.hasNextLine()) {
	        	this.urlList.add(scanner.nextLine()); 
			}

			scanner.close();
		}
		catch (FileNotFoundException e) {
      		System.out.println("An error occurred.");
      		e.printStackTrace();
      		System.exit(1);
    	}
	}

	public void setListView(ListView lv) {		
		lv.getItems().clear();

		for (int i=0 ; i<urlList.size() ; i++) {
			lv.getItems().add(this.urlList.get(i));
		}

	}

	public ArrayList<String> getUrlList() {
		return this.urlList;
	}
}