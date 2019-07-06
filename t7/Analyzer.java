import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;

// gson imports
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.*;

// Classe que irá fazer a análise do repositório
public class Analyzer {
	private String url; // url do repositório
	private ArrayList<CommitObject> commitList; // arraylist com os commits
	private boolean readed = false; // flag que informa se o repositório foi lido

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return this.url;
	}

	public boolean wasRead() {
		return this.readed;
	}

	// Gera a lista de commits.
	public void setCommitList() {
		this.commitList = new ArrayList<CommitObject>();
		
		int i=0;
		while (true) {
			try {
				URL url = new URL(this.url + "?page=" + i);
			    HttpURLConnection con = (HttpURLConnection) url.openConnection();
			    con.setRequestMethod("GET");
			    con.setRequestProperty("User-Agent", "Mozilla/5.0");
			    System.out.println("Response code: " + con.getResponseCode());

			    // Caso tenha sido excedido o número de aquisições, o usuário pode ler até onde
			    // o programa conseguiu ler algo. E caso chegue em uma página que não existe,
			    // o break é efetuado também.
			    if (con.getResponseCode() != 200) {
			    	break;
			    }
			    
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

				// Outra condição de parada, é caso o número de commits da página
				// seja menor que 30.
				if (results.size() < 30) break;
			}
			catch(IOException e) {}
			
			i++;
		}

		// Só irá ser permitido acessar os itens caso exista algum commit
		// no repositório em questão.
		if (this.commitList.size() > 0)
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

	// Retorna o tamanho médio das mensagens do repositório.
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

	// Retorna a data do commit mais recente deste repositório.
	public Date getMostRecentCommit() {
		Date date = commitList.get(0).getAuthor().getDateValue();

		for (int i=1 ; i<commitList.size() ; i++) {
			if (commitList.get(i).getAuthor().getDateValue().compareTo(date) > 0) {
				date = commitList.get(i).getAuthor().getDateValue();
			}
		}

		return date;
	}

	// Retorna a data do commit mais antigo deste repositório.
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
