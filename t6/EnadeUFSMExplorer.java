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

// ----------------------- Auxiliar Classes ------------------------

// ----------------------- Auxiliar Methods ------------------------

    private static EnadeTable createEnade(String[] metadata) {
        System.out.println("\nCriando objeto...");

        EnadeTable aux = new EnadeTable();

        aux.setAno(metadata[1]);
        aux.setProva(metadata[2]);
        aux.setTipoQuestao(metadata[3]);
        aux.setIdQuestao(metadata[4]);
        aux.setObjeto(metadata[5]);
        aux.setAcertosCurso(metadata[8]);
        aux.setAcertosRegiao(metadata[9]);
        aux.setAcertosBrasil(metadata[10]);
        aux.setAcertosDif(metadata[11]);

        System.out.println("Ano: " + metadata[1] + "\n"
            + "Prova: " + metadata[2] + "\n"
            + "Tipo questao: " + metadata[3] + "\n"
            + "Id questao: " + metadata[4] + "\n"
            + "objeto: " + metadata[5] + "\n" 
            + "acertos Curso: " + metadata[8] + "\n" 
            + "acertos regiao: " + metadata[9] + "\n"
            + "acertos brasil: " + metadata[10] + "\n"
            + "acertos dif: " + metadata[11] + "\n------------------\n");

        // create and return book of this metadata
        return aux;
    }


    private static ArrayList<EnadeTable> readEnadeFromCSV(String fileName) {
        ArrayList<EnadeTable> enade = new ArrayList<>();
        Path pathToFile = Paths.get(fileName);

        // create an instance of BufferedReader
        // using try with resource, Java 7 feature to close resources
        try {
            FileReader fr = new FileReader(fileName);
            Scanner scan = new Scanner(fr);
            scan.useDelimiter("CC");
            scan.next();

            while(scan.hasNext()) {
                String line = scan.next();

                System.out.println("----------------\n" + line);

                String[] attributes = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                EnadeTable enadeObj = createEnade(attributes);
                enade.add(enadeObj);
            }

            scan.close();
            fr.close();
        } 
        catch (IOException ioe) {
            ioe.printStackTrace();
       }

        return enade;
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
        
        ObservableList<EnadeTable> data = FXCollections.observableArrayList(readEnadeFromCSV("enade1.csv"));
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