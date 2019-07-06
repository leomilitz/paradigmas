// java imports

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GitHubAnalyzerCmd {
	// Verifica se uma URL é válida ou não.
    public static boolean urlValidator(String url) {
        try {
            new URL(url).toURI();
            return true;
        }
        catch (URISyntaxException exception) {
            return false;
        }
        catch (MalformedURLException exception) {
            return false;
        }
    }

	private static int getMostCommitsRepoIndex( ArrayList<Analyzer> analyzer, int option) {
		int greater = 0;
		int lesser = 0;

		for (int i=1 ; i<analyzer.size() ; i++) {
			if (analyzer.get(i).getCommitNumber() > analyzer.get(greater).getCommitNumber()) {
				greater = i;
			}
			else {
				lesser = i;
			}
		}

		// Se a opção escolhida for 1, retorna o maior. Se for 0, retorna o menor.
		if (option == 1)
			return greater;
		else
			return lesser;
	}

	private static void setAnalyzerList(File file, ArrayList<Analyzer> analyzer, ArrayList<String> urlList) {
		try {
			Scanner scanner  = new Scanner(file);
			ArrayList<String> urlListAux = new ArrayList<String>();
	
			while (scanner.hasNextLine()) {
		        String str = scanner.nextLine();
	        	
	        	if (urlValidator(str)) {
		        	Analyzer an = new Analyzer();
		        	an.setUrl(str);

		        	analyzer.add(an);
		        	urlList.add(str); 
		        }
			}

			scanner.close();
		}
		catch (FileNotFoundException e) {
      		System.out.println("An error occurred.");
      		e.printStackTrace();
    	}
	}

	public static void main(String[] args) {
		ArrayList<String> urlList = new ArrayList<>();
		ArrayList<Analyzer> analyzer = new ArrayList<>();

		File file = new File(args[0]);
		setAnalyzerList(file, analyzer, urlList);

		for (int i=0 ; i<analyzer.size() ; i++) {
        	analyzer.get(i).setCommitList();

	        System.out.println(
	        	"\n========================\n" +
	        	"Repository [" + i + "]:\n" +
	        	"\tAverage Message Length: " + analyzer.get(i).getMessageAverageSize() +
	        	"\t\nNumber of Commits: " + analyzer.get(i).getCommitNumber()
	        );
        }

        System.out.println(
        	"\n-------------------------------------" +
        	"\nRepository with most commits: " + getMostCommitsRepoIndex(analyzer, 1) +
        	"\nRepository with less commits: " + getMostCommitsRepoIndex(analyzer, 0) +
        	"\nRepository with most recent commit: " + //-> algo aqui <-//
        	"\nRepository with least recent commit: "  //-> algo aqui <-//
        	);
	}
}