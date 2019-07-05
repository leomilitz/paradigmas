// -------------------------- IMPORTS ---------------------------------------

// java imports

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
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

// .stage imports
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Modality;

// .geometry imports
import javafx.geometry.Pos;
import javafx.geometry.Insets;

// -------------------------------------------------------------------------

public class GitHubAnalyzerGUI extends Application {

// ----------------------- Auxiliar Methods ------------------------

	private ArrayList<String> setUrlList(File file) {
		try {
			Scanner scanner  = new Scanner(file);
			ArrayList<String> urlList = new ArrayList<String>();
			
			while (scanner.hasNextLine()) {
	        	urlList.add(scanner.nextLine()); 
			}

			scanner.close();

			return urlList;
		}
		catch (FileNotFoundException e) {
      		System.out.println("An error occurred.");
      		e.printStackTrace();
      		return null;
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
    	Analyzer analyzer = new Analyzer();

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

// -------------------------- List View ----------------------------

        ListView listView = new ListView();
        VBox vbListView = new VBox();
        listView.setPrefSize(850, 550);

        vbListView.setPadding(new Insets(0, 10, 0, 10));
        vbListView.getChildren().add(listView);

// ----------------------- Button Actions --------------------------
        itemOpen.setOnAction(e -> { 
        	FileChooser fc = createFileChooser();
        	File selectedFile = fc.showOpenDialog(primaryStage);

        	// evita que o programa faça operações com uma file caso ela seja null.
            if (selectedFile != null) {
            	analyzer.setUrlList(selectedFile);
            	analyzer.setListView(listView);
            
            	itemCommitAnalyzer.setDisable(false);
            }
        });

        itemCommitAnalyzer.setOnAction(e -> {
        	analyzer.setCommitList();
        });

        // Ação do botão about, que mostra o nome do programador e o nome do aplicativo.
        itemAbout.setOnAction(e -> { showAppInfo(); });

        // Ação do botão exit, que fecha o estágio primário - encerrando o aplicativo.
        itemExit.setOnAction(e -> { primaryStage.close(); });
// ---------------------------- Root -------------------------------
		
		VBox root = new VBox();
        root.setSpacing(10);
       	root.getChildren().addAll(vbMenuBar, vbListView);
        
        Scene scene = new Scene(root, 900, 600);

        primaryStage.setScene(scene);
        primaryStage.show();

	}
}