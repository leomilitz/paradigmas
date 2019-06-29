// -------------------------- IMPORTS ---------------------------------------

// java imports

import java.net.*;
import java.io.*;
import java.nio.file.Paths;

// javafx imports
import javafx.application.Application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// .scene imports
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;

// .stage imports
import javafx.stage.FileChooser;
import javafx.stage.Stage;

// .geometry imports
import javafx.geometry.Pos;
import javafx.geometry.Insets;

// -------------------------------------------------------------------------

public class EnadeUFSMExplorer extends Application {

private final ObservableList<EnadeTable> data = 
    FXCollections.observableArrayList(
        new EnadeTable()
    );

// ----------------------- Auxiliar Classes ------------------------

// ----------------------- Auxiliar Methods ------------------------

	private void readCsvFileToTable() {
        
    }

    // Método responsável por mostrar o item do menu "about".
	private void showAppInfo() {
        Label lb = new Label("Author: Leonardo Militz\nApp: ENADE-UFSM Explorer");
        VBox vb = new VBox();
        
        vb.setAlignment(Pos.CENTER);
        vb.getChildren().add(lb);

        Stage stage = new Stage();
        Scene scene = new Scene(vb);

        stage.setScene(scene);
        stage.show();
    }

// ---------------------------- Main -------------------------------
    
    // Função principal.
    public static void main(String[] args) {
		launch(args);
  	}

// ---------------------------- Start ------------------------------
  	@Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("ENADE-UFSM Explorer");

// -------------------------- Menu bar -----------------------------

		VBox vbMenuBar = new VBox();
        MenuBar menuBar = new MenuBar();
        vbMenuBar.getChildren().add(menuBar);

        Menu menuFile = new Menu("File");
        Menu menuHelp = new Menu("Help");
        
        MenuItem itemReload = new MenuItem("Reload");
        MenuItem itemSource = new MenuItem("Source");
        MenuItem itemExit = new MenuItem("Exit");
        MenuItem itemAbout = new MenuItem("About");

        menuFile.getItems().add(itemReload);
        menuFile.getItems().add(itemSource);
        menuFile.getItems().add(itemExit);
        menuHelp.getItems().add(itemAbout);

        menuBar.getMenus().add(menuFile);
        menuBar.getMenus().add(menuHelp);

// ------------------------- Table View ----------------------------

        VBox vbTableView = new VBox();
        TableView tableView = new TableView();

        TableColumn<String, EnadeTable> tcAno 			= new TableColumn<>("Ano");
        TableColumn<String, EnadeTable> tcProva 		= new TableColumn<>("Prova");
        TableColumn<String, EnadeTable> tcTipoQuestao 	= new TableColumn<>("Tipo Questão");
        TableColumn<String, EnadeTable> tcIdQuestao 	= new TableColumn<>("ID Questão");
        TableColumn<String, EnadeTable> tcObjeto 		= new TableColumn<>("Objeto");
        TableColumn<String, EnadeTable> tcAcertosCurso 	= new TableColumn<>("Acertos Curso");
        TableColumn<String, EnadeTable> tcAcertosRegiao = new TableColumn<>("Acertos Região");
        TableColumn<String, EnadeTable> tcAcertosBrasil = new TableColumn<>("Acertos Brasil");
        TableColumn<String, EnadeTable> tcAcertosDif 	= new TableColumn<>("Dif. (Curso-Brasil)");

        tcAno.setCellValueFactory(new PropertyValueFactory<>("ano"));
        tcProva.setCellValueFactory(new PropertyValueFactory<>("prova"));
        tcTipoQuestao.setCellValueFactory(new PropertyValueFactory<>("tipoQuestao"));
        tcIdQuestao.setCellValueFactory(new PropertyValueFactory<>("idQuestao"));
        tcObjeto.setCellValueFactory(new PropertyValueFactory<>("objeto"));
        tcAcertosCurso.setCellValueFactory(new PropertyValueFactory<>("acertosCurso"));
        tcAcertosRegiao.setCellValueFactory(new PropertyValueFactory<>("acertosRegiao"));
        tcAcertosBrasil.setCellValueFactory(new PropertyValueFactory<>("acertosBrasil"));
        tcAcertosDif.setCellValueFactory(new PropertyValueFactory<>("acertosDif"));

        tableView.getColumns().addAll(tcAno, tcProva, tcTipoQuestao, tcIdQuestao);
        tableView.getColumns().addAll(tcObjeto, tcAcertosCurso, tcAcertosRegiao);
        tableView.getColumns().addAll(tcAcertosBrasil, tcAcertosDif);
        
        tableView.setItems(data);

        vbTableView.setPadding(new Insets(0, 10, 0, 10));
        vbTableView.getChildren().addAll(tableView);

// ----------------------- Button Actions --------------------------
 	
    // Ação do botão about, que mostra o nome do programador e o nome do aplicativo.
    itemAbout.setOnAction(e -> { showAppInfo(); });   

    // Ação do botão exit, que fecha o estágio primário - encerrando o aplicativo.
    itemExit.setOnAction(e -> { primaryStage.close(); });


// ---------------- Root ------------------------

        VBox root = new VBox();
        root.setSpacing(10);
       	root.getChildren().addAll(vbMenuBar, vbTableView);
        
        Scene scene = new Scene(root, 950, 650);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}