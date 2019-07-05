import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

// gson imports
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.*;

import javafx.scene.control.ListView;

public class Analyzer {
	private ArrayList<String> urlList;
	private ArrayList<CommitObject> commitList;

	public void setUrlList(File file) {
		try {
			Scanner scanner  = new Scanner(file);
			this.urlList = new ArrayList<String>();
			this.commitList = new ArrayList<CommitObject>();
			
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

	public void setCommitList() {
		try {
			URL url = new URL(this.urlList.get(0));
		    HttpURLConnection con = (HttpURLConnection) url.openConnection();
		    con.setRequestMethod("GET");
		    con.setRequestProperty("User-Agent", "Mozilla/5.0");
		    System.out.println("Response code: " + con.getResponseCode());
		    
		    BufferedReader in = new BufferedReader(
		      new InputStreamReader(con.getInputStream())
		    );

		    // Response header (includes pagination links)
		    System.out.println(con.getHeaderFields().get("Link").get(0));

		    // Parse a nested JSON response using Gson
			JsonParser parser = new JsonParser();
		    JsonArray results = parser.parse(in.readLine()).getAsJsonArray();

		    System.out.println("Size: "+ results.size());

		    for (JsonElement json : results) {
		    	Gson gson = new Gson();
		    	
		    	Person author = gson.fromJson(json
		    		.getAsJsonObject().get("commit")
		    		.getAsJsonObject().get("author"), 
		    		Person.class);

		    	Person committer = gson.fromJson(json
		    		.getAsJsonObject().get("commit")
		    		.getAsJsonObject().get("committer"), 
		    		Person.class);

		    	String message  = gson.fromJson(json
		    		.getAsJsonObject().get("commit")
		    		.getAsJsonObject().get("message"), 
		    		String.class);

		    	System.out.println(message);

		    	CommitObject comObj = new CommitObject();

		    	comObj.setAuthor(author);
		    	comObj.setCommitter(committer);
		    	comObj.setMessage(message);

		    	//comObj.print();

		    	this.commitList.add(comObj);		    
		    }
			
			in.close();
		}
		catch(IOException e) {}
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