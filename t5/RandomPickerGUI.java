// -------------------------- IMPORTS ---------------------------------------

// java imports
import java.net.*;
import java.io.*;
import java.nio.file.Paths;

// javafx imports
import javafx.application.Application;

// .scene imports
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
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

public class RandomPickerGUI extends Application{
    private ListShuffle list; // Lista "global" usada pelo aplicativo.

    // Classe Name Browser serve para navegar entre os elementos da lista.
    private class NameBrowser {
        private ListShuffle list; // Lista utilizada pelo name broswer para navegar.
        private int curr; // Elemento atual de navegação.

        public NameBrowser() {
            this.reset();
        }

        // Vai pro próximo elemento da lista e o retorna.
        public String next() {
            this.curr = this.curr + 1;
            
            return this.list.getItem(this.curr);
        }

        // Reseta a posição inicial do browser.
        public void reset() {
            this.curr = 0;
        }

        // Define a lista do name browser.
        public void setList(ListShuffle l) {
            this.list = l;
        }
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
	
    // Função principal.
    public static void main(String[] args) {
		launch(args);
  	}

    // Método responsável pelo embaralhamento e escrita em uma área de texto.
    // Este método cria um arquivo temporário com a text area para ser utilizado
    // pela shuffle list.
    private void shuffleAndWrite(TextArea textArea) {
        try {
            File temp = new File("temp.txt");
            temp.createNewFile();
            
            FileWriter myWriter = new FileWriter("temp.txt");
            myWriter.write(textArea.getText());
            myWriter.close();
            
            ListShuffle tempList = new ListShuffle(temp);
            tempList.shuffle();
            textArea.setText(tempList.getText());

            list = tempList;
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    // Método responsável por mostrar o item do menu "about".
    private void showAppInfo() {
        Label lb = new Label("Author: Leonardo Militz\nApp: Random Picker");
        VBox vb = new VBox();
        
        vb.setAlignment(Pos.CENTER);
        vb.getChildren().add(lb);

        Stage stage = new Stage();
        Scene scene = new Scene(vb);

        stage.setScene(scene);
        stage.show();
    }

// --------------------------- START -------------------------------
  	@Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Random Picker");

// ------------- Name Browser -------------------

        NameBrowser nb = new NameBrowser();

// -------------- Menu Bar ----------------------

        VBox vbMenuBar = new VBox();
        MenuBar menuBar = new MenuBar();
        vbMenuBar.getChildren().add(menuBar);

        Menu menu1 = new Menu("File");
        Menu menu2 = new Menu("Help");
        
        MenuItem open = new MenuItem("Open");
        MenuItem exit = new MenuItem("Exit");
        MenuItem about = new MenuItem("About");

        menu1.getItems().add(open);
        menu1.getItems().add(exit);
        menu2.getItems().add(about);

        menuBar.getMenus().add(menu1);
        menuBar.getMenus().add(menu2);

// -------------- Text Area ---------------------
        
        VBox vbTextArea = new VBox();
        TextArea textArea = new TextArea();
        
        textArea.setPrefRowCount(23);
        vbTextArea.getChildren().add(textArea);
        vbTextArea.setMargin(textArea, new Insets(0,10,0,10));

// ---------------- Labels ----------------------

        VBox vbLabel = new VBox();
        Label current = new Label("");
        
        vbLabel.setAlignment(Pos.CENTER);
        vbLabel.setSpacing(15);
        vbLabel.getChildren().add(current);

// --------------- Buttons ----------------------

        HBox hbBtns = new HBox();
        Button btnShuffle = new Button("Shuffle");
        Button btnNext    = new Button("Next");

        btnNext.setDisable(true);
        btnShuffle.setMinWidth(70);
        btnNext.setMinWidth(70);
        
        hbBtns.getChildren().addAll(btnShuffle, btnNext);
        hbBtns.setSpacing(15);
        hbBtns.setAlignment(Pos.CENTER);
        
// ------------ Button Actions ------------------ 

        // Ação do botão about, que mostra o nome do programador e o nome do aplicativo.
        about.setOnAction(e -> { showAppInfo(); });
        
        // Ação do botão exit, que fecha o estágio primário - encerrando o aplicativo.
        exit.setOnAction(e -> { primaryStage.close(); });        
        
        // Ação do botão open, que abre o arquivo txt a ser tratado.
        open.setOnAction(e -> { 
            current.setText(""); // reseta a label.

            FileChooser fileChooser = createFileChooser();

            File selectedFile = fileChooser.showOpenDialog(primaryStage);
        	list = new ListShuffle(selectedFile);
            
            textArea.setText(list.getText());

            btnShuffle.setDisable(false);
            btnNext.setDisable(true);
        });

        // Tratamento do botão shuffle para a text area
        btnShuffle.setOnAction(e -> { 
            btnNext.setDisable(false);
            btnShuffle.setDisable(true);
            
            shuffleAndWrite(textArea);

            current.setText(list.getItem(0)); // Inicia a label com o primeiro elemento da lista
            nb.setList(list); // A lista embaralhada foi adicionada ao Name Browser
        });
        
        // Tratamento do botão next, que ativará quando shuffle for clicado.
        btnNext.setOnAction(e -> {
            current.setText(nb.next()); // cada vez que for clicado, será atualizada a label.

            // Caso a label seja o ultimo item da lista, o botão next sera desativado,
            // e poderá ser realizado um novo embaralhamento.
            if (current.getText() == list.getLast()) {                   
                btnNext.setDisable(true);
                btnShuffle.setDisable(false);
                
                nb.reset(); // reseta a posição do name browser.
            }
        });

// ---------------- Root ------------------------
        
        VBox root = new VBox();
        root.setSpacing(10);
        root.getChildren().addAll(vbMenuBar, vbTextArea, hbBtns, vbLabel);
        
        Scene scene = new Scene(root, 450, 610);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}