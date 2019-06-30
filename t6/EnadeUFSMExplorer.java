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
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;


// .stage imports
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Modality;

// .geometry imports
import javafx.geometry.Pos;
import javafx.geometry.Insets;

// -------------------------------------------------------------------------

public class EnadeUFSMExplorer extends Application {
    // Lista de dados a ser usada na tableview
    private ObservableList<EnadeTable> data;
    // String deafult de download da tableview
    private String urlStr = "https://docs.google.com/spreadsheets/d/e/2PACX-1vTO06Jdr3J1kPYoTPRkdUaq8XuslvSD5--" +
                    "FPMht-ilVBT1gExJXDPTiX0P3FsrxV5VKUZJrIUtH1wvN/pub?gid=0&single=true&output=csv";


// ----------------------- Auxiliar Methods ------------------------

    // Método responsável em criar a label com as informações sobre o objeto
    private static String enadeToString(EnadeTable data) {
        String aux = "Ano: "            + data.getAno() + "\n" +
                     "Prova: "          + data.getProva() + "\n" +
                     "Tipo Questão: "   + data.getTipoQuestao() + "\n" +
                     "ID Questão: "     + data.getIdQuestao() + "\n" +
                     "Objeto: "         + data.getObjeto() + "\n" +
                     "Acertos Curso: "  + data.getAcertosCurso() + "\n" +
                     "Acertos Região: " + data.getAcertosRegiao() + "\n" +
                     "Acertos Brasil: " + data.getAcertosBrasil() + "\n" +
                     "Acertos Dif. (Curso - Brasil): " + data.getAcertosDif() + "\n" +
                     "\nGabarito: "       + data.getGabarito() + "\n" +
                     "Imagem: ";


        return aux;
    }

    // Metodo responsável em atribuir cada elmento do objeto os metadados obtidos
    // pela leitura do csv.
    private static EnadeTable createEnade(String[] metadata) {

        EnadeTable aux = new EnadeTable();

        aux.setCurso(metadata[0]);
        aux.setAno(metadata[1]);
        aux.setProva(metadata[2]);
        aux.setTipoQuestao(metadata[3]);
        aux.setIdQuestao(metadata[4]);
        aux.setObjeto(metadata[5]);
        aux.setObjetoDetalhado(metadata[6]);
        aux.setGabarito(metadata[7]);
        aux.setAcertosCurso(metadata[8]);
        aux.setAcertosRegiao(metadata[9]);
        aux.setAcertosBrasil(metadata[10]);
        aux.setAcertosDif(metadata[11]);
        aux.setTexto(metadata[12]);
        aux.setImagem(metadata[13]);
        aux.setUrlProva(metadata[14]);
        aux.setUrlSintese(metadata[15]);
        aux.setUrlCurso(metadata[16]);

        return aux;
    }

    // Checa se o arquivo existe no diretório.
    private boolean checkFileExistence(String fileName) {
        boolean result;
        try
        {
            final Path path = Files.createTempFile(fileName, ".csv");
            result = Files.exists(path); 
        } 
        catch (IOException e) {
        }

        return false;
    }

    // Função responsável por ler o csv e retornar uma arraylist com os objetos
    // adquiridos a partir da leitura.
    private static ArrayList<EnadeTable> readEnadeFromCSV(String fileName) {
        ArrayList<EnadeTable> enade = new ArrayList<>();
        Path pathToFile = Paths.get(fileName);

        try {
            FileReader fr = new FileReader(fileName);
            Scanner scan = new Scanner(fr);
            scan.useDelimiter("CC,|SI,"); // Lê até encontrar o nome dos dois cursos
            scan.next();

            while(scan.hasNext()) {
                String line = "," + scan.next();
                //                    A linha será repartida de acordo com este regex.
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

    // Faz download da url.
    private static void download(String url, String fileName) {
        try (InputStream in = URI.create(url).toURL().openStream()) {
            Files.copy(in, Paths.get(fileName));
        }
        catch(IOException e) {
        }
    }

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

    // Método responsável por testar se a url possui uma imagem.
    private static boolean testImage(String url) {  
        try {  
            
            BufferedImage image = ImageIO.read(new URL(url));  

            if (image != null) return true;
            else               return false;
        
        } 
        catch (MalformedURLException e) {  
            return false;
        } 
        catch (IOException e) {  
            e.printStackTrace();
            return false;
        }
    }  

    // Método responsável por mostrar o item do menu "about".
	private void showAppInfo() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        
        Label lb = new Label("Author: Leonardo Militz\nApp: ENADE-UFSM Explorer");
        VBox vb = new VBox();
        
        vb.setAlignment(Pos.CENTER);
        vb.getChildren().add(lb);

        Scene scene = new Scene(vb);

        stage.setScene(scene);
        stage.show();
    }

    // Mostra as informações sobre questão.
    private void showQuestionWindow(EnadeTable data) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        Label lb = new Label(enadeToString(data));
        Button exit = new Button("Exit");
        
        VBox vb = new VBox();
        HBox hb = new HBox();

        ImageView imageView;
        VBox vbImg = new VBox();
        
        // Primeiro checa se o tamanho é maior que dois, pros verificador de imagens não rodar sempre.
        if (data.getImagem().length() > 2) {
            // Cria a imagem a partir  de uma URL válida.
            if (testImage(data.getImagem())) {
                Image image = new Image(data.getImagem());
                imageView = new ImageView(image);
                
                vbImg.getChildren().add(imageView);
                vbImg.setSpacing(10);
                vbImg.setAlignment(Pos.CENTER);
                vbImg.setPadding(new Insets(0, 10, 0, 10));
            }
        }
        
        vb.getChildren().add(lb);
        hb.getChildren().add(exit);
        hb.setAlignment(Pos.CENTER);

        exit.setOnAction(e  -> { stage.close(); });
        
        VBox root = new VBox();
        root.setSpacing(10);
        root.getChildren().addAll(vb, vbImg, hb);
        
        Scene scene = new Scene(root);
        
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

    public void start(final Stage primaryStage) {
        
        primaryStage.setTitle("ENADE-UFSM Explorer");
        String fileName = "enade";
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
        tableView.setPrefSize(600, 900);

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

        if (checkFileExistence(fileName) == true) {
            data = FXCollections.observableArrayList(readEnadeFromCSV(fileName + ".csv"));
        }
        else {
            download(urlStr, fileName + ".csv");
            data = FXCollections.observableArrayList(readEnadeFromCSV(fileName + ".csv"));
        }

        tableView.setItems(data);
        
        vbTableView.setPadding(new Insets(0, 10, 0, 10));
        vbTableView.getChildren().addAll(tableView);

// ----------------------- Button Actions --------------------------
 	
        // Ação do botão about, que mostra o nome do programador e o nome do aplicativo.
        itemAbout.setOnAction(e -> { showAppInfo(); });   

        // Ação do botão exit, que fecha o estágio primário - encerrando o aplicativo.
        itemExit.setOnAction(e -> { primaryStage.close(); });

        // Ação do botão reload, que deleta o arquivo e o recarrega da URL de download.
        itemReload.setOnAction(e -> { 
            tableView.getItems().clear();
            
            File file = new File(fileName + ".csv");
            file.delete();
            
            download(urlStr, fileName + ".csv"); 
            
            data = FXCollections.observableArrayList(readEnadeFromCSV(fileName + ".csv"));
            tableView.setItems(data);
        });

        // Ação do item que abre a janela source, que é responsável por
        // receber um link, checar sua validez e trocar a url base a ser carregada.
        // Ao mudar a url padrão, deve-se usar o reload. 
        itemSource.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                Stage stage = new Stage();
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(primaryStage);       

                Label lb = new Label("Type the new url value:");
                Button confirm = new Button("Confirm");
                Button cancel = new Button("Cancel");
                TextField tf = new TextField();
                VBox vb = new VBox();
                HBox hb = new HBox();

                vb.setAlignment(Pos.CENTER);
                vb.getChildren().addAll(lb, tf);
                
                hb.getChildren().addAll(confirm, cancel);
                hb.setAlignment(Pos.CENTER);
                hb.setSpacing(10);

                confirm.setOnAction(e -> { 
                    if (urlValidator(tf.getText())) {
                        urlStr = tf.getText();
                        stage.close();
                    }
                    else {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Invalid URL");
                        alert.setContentText("Escolha uma URL válida.");

                        alert.showAndWait();
                    }
                 });
                
                cancel.setOnAction(e  -> { stage.close(); });

                VBox root = new VBox();
                root.setSpacing(10);
                root.getChildren().addAll(vb, hb);
                
                Scene scene = new Scene(root);
                
                stage.setScene(scene);
                stage.show();
            }
        });

        // Ação ao clicar duas vezes nos itens da tableview.
        tableView.setRowFactory( tv -> {
            TableRow<EnadeTable> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    EnadeTable clickedData = row.getItem();
                    
                    showQuestionWindow(clickedData);
                }
            });
            
            return row ;
        });


// ---------------- Root ------------------------

        VBox root = new VBox();
        root.setSpacing(10);
       	root.getChildren().addAll(vbMenuBar, vbTableView);
        
        Scene scene = new Scene(root, 950, 650);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}