import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

class ListShuffle {
	protected ArrayList<String> nameList;
	protected String data;

	public ListShuffle(File file) {
		try {
			Scanner scanner  = new Scanner(file);
			this.nameList = new ArrayList<String>();
			
			while (scanner.hasNextLine()) {
	        	this.nameList.add(scanner.nextLine()); 
			}

			scanner.close();
		}
		catch (FileNotFoundException e) {
      		System.out.println("An error occurred.");
      		e.printStackTrace();
      		System.exit(1);
    	}
	}

	public void shuffleOffline() {
		Collections.shuffle(this.nameList);
	}

	private void setData() {
		this.data = "list=";
		for (int i=0 ; i<this.nameList.size() ; i++) {
			if (i < this.nameList.size() - 1)
				this.data = this.data + this.nameList.get(i) + "%0D%0A";
			else {
				this.data = this.data + this.nameList.get(i) + "&format=plain&rnd=new";
			}
		}
	}

	public String getData() {
		return this.data;
	}

	public String getText() {
		String str = new String();
		for (int i=0 ; i<this.nameList.size() ; i++) {
			str = str + this.nameList.get(i) + "\n";
		}

		return str;
	}

	public void shuffleOnline() {
		this.setData();

		try {
	      String urlstr = "https://www.random.org/lists/?mode=advanced";
	      URL url = new URL(urlstr);
	      HttpURLConnection con = (HttpURLConnection) url.openConnection();
	      
	      con.setRequestMethod("POST");
	      con.setRequestProperty("User-Agent", "Mozilla/5.0");
	      con.setDoOutput(true);
	      
	      // Envia dados pela conexão aberta
	      con.getOutputStream().write(this.getData().getBytes("UTF-8"));
	      System.out.println("Response code: " + con.getResponseCode());

	      // Cria objeto que fará leitura da resposta pela conexão aberta
	      BufferedReader in = new BufferedReader(
	        new InputStreamReader(con.getInputStream()));

	      // Lê resposta, linha por linha
	      String responseLine;
	      ArrayList<String> response = new ArrayList<String>();
	      
	      //StringBuffer response = new StringBuffer(); // fazer o arraylist aqui
	      while ((responseLine = in.readLine()) != null) {
	        response.add(responseLine); // arraylist
	      }

	      in.close();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	}

	public void printCmd() {
		Scanner sysPause = new Scanner(System.in); // scanner usado para pausar a execução no console
		for (int i=0 ; i<this.nameList.size() ; i++) {
	      		
      		if (i < this.nameList.size() - 1)
      			System.out.println("\t- " + this.nameList.get(i) + ";");
      		else
      			System.out.println("\t- " + this.nameList.get(i) + ".");

      		sysPause.nextLine();
      	}
  	
      	sysPause.close();
	}
}