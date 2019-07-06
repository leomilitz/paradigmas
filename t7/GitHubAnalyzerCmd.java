// java imports

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

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

    // Pega o maior ou o menor indice do repositório com maior ou menor número.
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

	// Inicia a lista de repositórios com base no número de links válidos.
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

	// Pega o commit mais recente de todos repositórios.
	private static Date getAllRepoMostRecentCommit(ArrayList<Analyzer> analyzer) {
		Date date = analyzer.get(0).getMostRecentCommit();

		for (int i=1 ; i<analyzer.size() ; i++) {
			if (analyzer.get(i).getMostRecentCommit().compareTo(date) > 0) {
				date = analyzer.get(i).getMostRecentCommit();
			}
		}

		return date;
	}

	// Pega o índice do commit mais recente de todos repositórios.
	private static int getAllRepoMostRecentCommitIndex(ArrayList<Analyzer> analyzer) {
		Date date = analyzer.get(0).getMostRecentCommit();
		int index = 0;

		for (int i=1 ; i<analyzer.size() ; i++) {
			if (analyzer.get(i).getMostRecentCommit().compareTo(date) > 0) {
				date = analyzer.get(i).getMostRecentCommit();
				index = i;
			}
		}

		return index;
	}

	// Pega o commit mais antigo de todos repositórios.
	private static Date getAllRepoOldestCommit(ArrayList<Analyzer> analyzer) {
		Date date = analyzer.get(0).getOldestCommit();

		for (int i=1 ; i<analyzer.size() ; i++) {
			if (analyzer.get(i).getOldestCommit().compareTo(date) < 0) {
				date = analyzer.get(i).getOldestCommit();
			}
		}

		return date;
	}

	// Pega o índice do commit mais antigo de todos repositórios.
	private static int getAllRepoOldestCommitIndex(ArrayList<Analyzer> analyzer) {
		Date date = analyzer.get(0).getOldestCommit();
		int index = 0;

		for (int i=1 ; i<analyzer.size() ; i++) {
			if (analyzer.get(i).getOldestCommit().compareTo(date) < 0) {
				date = analyzer.get(i).getOldestCommit();
				index = i;
			}
		}

		return index;
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
	        	"\n\tNumber of Commits: " + analyzer.get(i).getCommitNumber()
	        );
        }

        System.out.println(
        	"\n-------------------------------------" +
        	"\nRepository with most commits: [" + getMostCommitsRepoIndex(analyzer, 1) + "]" +
        	"\nRepository with less commits: [" + getMostCommitsRepoIndex(analyzer, 0) + "]" +
        	"\nRepository with most recent commit: [" + getAllRepoMostRecentCommitIndex(analyzer) + "]" +
        	" - " + getAllRepoMostRecentCommit(analyzer) +
        	"\nRepository with oldest commit: ["  + getAllRepoOldestCommitIndex(analyzer) + "]" +
        	" - " + getAllRepoOldestCommit(analyzer)
        );
	}
}