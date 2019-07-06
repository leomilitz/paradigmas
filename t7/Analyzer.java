import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

// gson imports
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.*;


import java.util.Date;

public class Analyzer {
	private String url;
	private ArrayList<CommitObject> commitList;
	private boolean readed = false;

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return this.url;
	}

	public boolean wasRead() {
		return this.readed;
	}

	public void setCommitList() {
		this.commitList = new ArrayList<CommitObject>();
		
		int i=0;
		while (i < 1) {
			try {
				URL url = new URL(this.url + "?page=" + i);
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

			    System.out.println("Size: " + results.size());

			    for (JsonElement json : results) {
			    	Gson gson = new Gson();
			    	
			    	Person author = gson.fromJson(json
			    		.getAsJsonObject().get("commit")
			    		.getAsJsonObject().get("author"), 
			    		Person.class);

			    	author.setDate(author.getDate());

			    	Person committer = gson.fromJson(json
			    		.getAsJsonObject().get("commit")
			    		.getAsJsonObject().get("committer"), 
			    		Person.class);
					
					committer.setDate(committer.getDate());

			    	String message  = gson.fromJson(json
			    		.getAsJsonObject().get("commit")
			    		.getAsJsonObject().get("message"), 
			    		String.class);

			    	CommitObject comObj = new CommitObject();

			    	comObj.setAuthor(author);
			    	comObj.setCommitter(committer);
			    	comObj.setMessage(message);

			    	this.commitList.add(comObj);		    
			    }
				
				in.close();
			}
			catch(IOException e) {}
			i++;
		}

		this.readed = true;
	}

	public ArrayList<CommitObject> getCommitList() {
		return this.commitList;
	}

	public CommitObject getCommitObject(int index) {
		return commitList.get(index);
	}

	public int getCommitNumber() {
		return commitList.size();
	}

	public float getMessageAverageSize() {
		int sum = 0;
		for (int i=0 ; i<commitList.size() ; i++) {
			sum = sum + commitList.get(i).getMessage().length();
		}

		if (commitList.size() > 0)
			return (sum / commitList.size());
		else
			return 0;
	}


	public Date getMostRecentCommit() {
		Date date = commitList.get(0).getAuthor().getDateValue();

		for (int i=1 ; i<commitList.size() ; i++) {
			if (commitList.get(i).getAuthor().getDateValue().compareTo(date) > 0) {
				date = commitList.get(i).getAuthor().getDateValue();
			}
		}

		return date;
	}

	public Date getOldestCommit() {
		Date date = commitList.get(0).getAuthor().getDateValue();
		
		for (int i=1 ; i<commitList.size() ; i++) {
			if (commitList.get(i).getAuthor().getDateValue().compareTo(date) < 0) {
				date = commitList.get(i).getAuthor().getDateValue();
			}
		}
		
		return date;
	}

}
