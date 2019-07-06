// -------------------------- IMPORTS ---------------------------------------

// java imports
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Date;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

// gson imports
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.*;

// javafx imports
import javafx.application.Application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

// .scene imports
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

// .stage imports
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Modality;

// .geometry imports
import javafx.geometry.Pos;
import javafx.geometry.Insets;


// -------------------------------------------------------------------------

public class GitHubAnalyzerGUI extends Application {
	private ArrayList<String> urlList; // lista de url a ser tratada pelo programa.
	private int listIndex; // indice atual da list view em pauta.

// ----------------------- Auxiliar Methods ------------------------

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

    // Pega o maior ou o menor número de commits entre os repositórios.
    private int getMostCommitsRepo( ArrayList<Analyzer> analyzer, int option) {
        int g_index = 0;
        int l_index = 0;
    	int greater = analyzer.get(l_index).getCommitNumber();
		int lesser = greater;

		for (int i=1 ; i<analyzer.size() ; i++) {
			if (analyzer.get(i).getCommitNumber() > analyzer.get(g_index).getCommitNumber()) {
				greater = analyzer.get(i).getCommitNumber();
                g_index = i;
			}
			else if (analyzer.get(i).getCommitNumber() < analyzer.get(l_index).getCommitNumber()){
				lesser = analyzer.get(i).getCommitNumber();
			    l_index = i;
            }
		}

		// Se a opção escolhida for 1, retorna o maior. Se for 0, retorna o menor.
		if (option == 1)
			return greater;
		else
			return lesser;
    }

    // Pega o maior ou o menor indice do repositório com maior ou menor número.
	private int getMostCommitsRepoIndex( ArrayList<Analyzer> analyzer, int option) {
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

	// Abre a janela que mostra as informações detalhadas de um commits específico.
	private void showCommitInfo(CommitObject commit) {
		Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        Button exit = new Button("Exit");
        Label info = new Label(commit.getInfo());

        VBox vbLbl = new VBox();
        vbLbl.setAlignment(Pos.CENTER);
        vbLbl.setPadding(new Insets(10, 20, 10, 20));
        vbLbl.getChildren().add(info);

        exit.setOnAction(e -> { stage.close(); });

        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(10);
        root.getChildren().addAll(vbLbl, exit);
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
	}

	// Mostra a listview com os commits de um repositório, e possibilita
	// interação do usuário com cada item.
	private void showRepository(Analyzer analyzer) {

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        Label lblRepoInfo = new Label("");
        VBox vbLbl = new VBox();
        vbLbl.setAlignment(Pos.CENTER);
        vbLbl.setPadding(new Insets(10, 20, 10, 20));
        vbLbl.getChildren().add(lblRepoInfo);

        lblRepoInfo.setText("Commit number: " + analyzer.getCommitNumber()
			        					+ "        Average Message Length: " + 
			        					analyzer.getMessageAverageSize());

        Button exit = new Button("Exit");
        Button seeInfo = new Button("Commit Details");
        HBox hb = new HBox();

        hb.setAlignment(Pos.CENTER);
        hb.setSpacing(10);
        hb.setPadding(new Insets(20, 10, 20, 10));
        hb.getChildren().addAll(seeInfo, exit);
        
        ListView listView = new ListView();
        VBox vbListView = new VBox();
        listView.setPrefSize(850, 550);

        vbListView.setPadding(new Insets(20, 10, 20, 10));
        vbListView.getChildren().add(listView);

        for (int i=0 ; i<analyzer.getCommitList().size() ; i++) {
        	listView.getItems().add("Commit [" + i + "]");
        }

        VBox vb = new VBox();
        vb.setAlignment(Pos.CENTER);
        vb.getChildren().addAll(vbListView, vbLbl);
        
        exit.setOnAction(e -> { stage.close(); });

        seeInfo.setOnAction(e -> { showCommitInfo(analyzer.getCommitObject(listIndex)); });
       	
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        listIndex = listView.getSelectionModel().getSelectedIndex();
		    }
		});

        VBox root = new VBox();
        root.setSpacing(10);
        root.getChildren().addAll(vb, hb);
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    // Mostra informações gerais sobre os repositórios, com gráfico de barras.
    private void showGeneralInfo(ArrayList<Analyzer> analyzer) {
    	Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

    	Label lbl = new Label(
        	"Repository with most commits: [" + getMostCommitsRepoIndex(analyzer, 1) + "]" +
        	" - " + getMostCommitsRepo(analyzer, 1) + " commits" +
        	"\nRepository with less commits: [" + getMostCommitsRepoIndex(analyzer, 0) + "]" +
        	" - " + getMostCommitsRepo(analyzer, 0) + " commits" +
        	"\nRepository with most recent commit: [" + getAllRepoMostRecentCommitIndex(analyzer) + "]" +
        	" - " + getAllRepoMostRecentCommit(analyzer) +
        	"\nRepository with oldest commit: ["  + getAllRepoOldestCommitIndex(analyzer) + "]" +
        	" - " + getAllRepoOldestCommit(analyzer)
        );

        VBox vbLbl = new VBox();
        vbLbl.setAlignment(Pos.CENTER);
        vbLbl.setPadding(new Insets(10, 20, 10, 20));
        vbLbl.getChildren().add(lbl);

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis   yAxis = new NumberAxis();
        
        BarChart<String,Number> bc = new BarChart<>(xAxis,yAxis);
        bc.setTitle("Commits per Repository");
       
        yAxis.setLabel("Commit Number");
        xAxis.setLabel("Repository Index");

        XYChart.Series series = new XYChart.Series();
        series.setName("Repository");

        for (int i=0 ; i<analyzer.size() ; i++) {
        	series.getData().add(new XYChart.Data("[" + i + "]", analyzer.get(i).getCommitNumber()));
        }
        
        bc.getData().addAll(series);
        bc.setBarGap(10);

        Button exit = new Button("Exit");

        VBox vb = new VBox();
        vb.setAlignment(Pos.CENTER);
        vb.getChildren().addAll(vbLbl, bc, exit);
        
        exit.setOnAction(e -> { stage.close(); });

        VBox root = new VBox();
        root.setSpacing(20);
        root.getChildren().addAll(vb);
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    // Inicia a lista de repositórios com base no número de links válidos.
	private void setAnalyzerList(File file, ArrayList<Analyzer> analyzer) {
		try {
			Scanner scanner  = new Scanner(file);
			ArrayList<String> urlListAux = new ArrayList<String>();
	
			while (scanner.hasNextLine()) {
		        String str = scanner.nextLine();
		        
		        if (urlValidator(str)) {
		        	Analyzer an = new Analyzer();
		        	an.setUrl(str);

		        	analyzer.add(an);
		        	urlListAux.add(str);
		        } 
			}

			scanner.close();

			urlList = urlListAux;
		}
		catch (FileNotFoundException e) {
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

	// Gera a list view dos repositórios com as urls e seus respectivos indices. 
	private void setListView(ListView lv) {		
		lv.getItems().clear();

		for (int i=0 ; i<urlList.size() ; i++) {
			lv.getItems().add("[" + i + "] - " + urlList.get(i));
		}
	}

	// Método responsável por mostrar o item do menu "about".
	private void showAppInfo() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        
        Label lb = new Label("Author: Leonardo Militz\nApp: GitHub Analyzer");
        VBox vb = new VBox();
        
        vb.setAlignment(Pos.CENTER);
        vb.getChildren().add(lb);

        Scene scene = new Scene(vb);

        stage.setScene(scene);
        stage.show();
    }

    // cria o file chooser, com os filtros de txt e all files. 
    // O diretório inicial é o mesmo da execução do programa.
    private FileChooser createFileChooser() { 
        FileChooser fileChooser = new FileChooser();
        
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Text Files", "*.txt"),
            new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
        fileChooser.setInitialDirectory(new File(currentPath));

        return fileChooser;
    }

// ---------------------------- Main -------------------------------
	public static void main(String[] args) {
		launch(args);
  	}
// ---------------------------- Start ------------------------------
  	@Override
    public void start(final Stage primaryStage) {
    	ArrayList<Analyzer> analyzer = new ArrayList<>(); // Lista de repositórios.

    	primaryStage.setTitle("GitHub Analyzer");

// -------------------------- Menu bar -----------------------------
    	VBox vbMenuBar  = new VBox();
        MenuBar menuBar = new MenuBar();
        vbMenuBar.getChildren().add(menuBar);

        Menu menuFile  = new Menu("File");
        Menu menuTools = new Menu("Tools");
        Menu menuHelp  = new Menu("Help");
        
        MenuItem itemOpen = new MenuItem("Open");
        MenuItem itemExit = new MenuItem("Exit");
        MenuItem itemCommitAnalyzer = new MenuItem("Commit Analyzer");
        MenuItem itemAbout = new MenuItem("About");

        menuFile.getItems().add(itemOpen);
        menuFile.getItems().add(itemExit);
        menuTools.getItems().add(itemCommitAnalyzer);
        menuHelp.getItems().add(itemAbout);

        itemCommitAnalyzer.setDisable(true);

        menuBar.getMenus().addAll(menuFile, menuTools, menuHelp);

// -------------------------- Buttons ------------------------------

        Button btnRepoInfo = new Button("Repository Information");
        Button btnGeneralInfo = new Button("General Information");
        HBox hbBtn = new HBox();
        hbBtn.setSpacing(10);
        hbBtn.setAlignment(Pos.CENTER);
        hbBtn.setPadding(new Insets(20, 10, 20, 10));

        btnGeneralInfo.setDisable(true); btnRepoInfo.setDisable(true);

        hbBtn.getChildren().addAll(btnRepoInfo, btnGeneralInfo);

// -------------------------- List View ----------------------------

        ListView listView = new ListView();
        VBox vbListView = new VBox();
        listView.setPrefSize(850, 550);

        vbListView.setPadding(new Insets(0, 10, 0, 10));
        vbListView.getChildren().add(listView);

// ----------------------- Button Actions --------------------------
        
        // Tratamento do MenuItem open, que retorna um arquivo para ser lido. 
        itemOpen.setOnAction(e -> { 
        	FileChooser fc = createFileChooser();
        	File selectedFile = fc.showOpenDialog(primaryStage);

        	// evita que o programa faça operações com uma file caso ela seja null.
            if (selectedFile != null) {
            	// cria a lista de analyzers.
            	setAnalyzerList(selectedFile, analyzer);
            	setListView(listView);

            	// O usuário só pode usar o commit analizer caso tenha carregado um arquivo
            	// válido.
            	itemCommitAnalyzer.setDisable(false);
            }
        });

        // Tratamento analisador de commits.
        itemCommitAnalyzer.setOnAction(e -> {
        	// O item é desabilitado para que o usuário não tente acionar
        	// multiplas vezes.
        	itemCommitAnalyzer.setDisable(true);
        	
        	// Thread que processa as informações requisitadas pela api separadamente
        	// da interface gráfica.
        	Thread t1 = new Thread(() -> {
        		for (int i=0 ; i<analyzer.size() ; i++) {
        			analyzer.get(i).setCommitList();
        		}
        	});

	        btnGeneralInfo.setDisable(false); 
	        btnRepoInfo.setDisable(false);
        	
        	t1.start();
        });

        // Ação do botão about, que mostra o nome do programador e o nome do aplicativo.
        itemAbout.setOnAction(e -> { showAppInfo(); });

        // Ação do botão exit, que fecha o estágio primário - encerrando o aplicativo.
        itemExit.setOnAction(e -> { primaryStage.close(); });
 	
 		// Pega o índice do item selecionado pela ListView.
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        listIndex = listView.getSelectionModel().getSelectedIndex();
		    }
		});

        // Abre a janela que mostra informação do repositório selecionado.
        // Só funciona caso o ultimo repositório tenha sido lido, ou seja,
        // todos repositórios foram processados.
        btnRepoInfo.setOnAction(e -> {
        	if (analyzer.get(analyzer.size() - 1).wasRead())
        		showRepository(analyzer.get(listIndex));
        });

        // Abre a janela que mostra a informação geral de todos repositórios,
        // Só funciona caso o ultimo repositório tenha sido lido, ou seja,
        // todos repositórios foram processados.
        btnGeneralInfo.setOnAction(e -> {
        	if (analyzer.get(analyzer.size() - 1).wasRead())
        		showGeneralInfo(analyzer);
        });

// ---------------------------- Root -------------------------------
		
		VBox root = new VBox();
        root.setSpacing(10);
       	root.getChildren().addAll(vbMenuBar, vbListView, hbBtn);
        
        Scene scene = new Scene(root, 950, 650);

        primaryStage.setScene(scene);
        primaryStage.show();
	}
}