import java.net.*;
import java.io.*;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

import javafx.application.Application;
import javafx.application.Application;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javafx.scene.layout.VBox;
import javafx.scene.Scene;

import javafx.geometry.Pos;
import javafx.geometry.Insets;


public class RandomPickerGUI extends Application{
	public static void main(String[] args) {
		launch(args);
  	}

    private void shuffleAndWrite(TextArea textArea) {
        try {
            File temp = new File("temp.txt");
            temp.createNewFile();
            
            FileWriter myWriter = new FileWriter("temp.txt");
            myWriter.write(textArea.getText());
            myWriter.close();
            
            ListShuffle noFileList = new ListShuffle(temp);
            noFileList.shuffleOffline();
            
            textArea.setText(noFileList.getText());
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void showAppInfo() {
        Label lb = new Label("Autor: Leonardo Militz\nApp: Random Picker");
        VBox vb = new VBox();
        vb.setAlignment(Pos.CENTER);

        vb.getChildren().add(lb);

        Stage stage = new Stage();
        Scene scene = new Scene(vb);

        stage.setScene(scene);
        stage.show();
    }

  	@Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Random Picker");

        // Cria menu bar
        VBox vbMenuBar = new VBox();
        MenuBar menuBar = new MenuBar();
        vbMenuBar.getChildren().add(menuBar);
                
        VBox vbTextArea = new VBox();
        TextArea textArea = new TextArea();
        textArea.setPrefRowCount(23);
        vbTextArea.getChildren().add(textArea);
        vbTextArea.setMargin(textArea, new Insets(0,10,0,10));
        
        Menu menu1 = new Menu("File");
        Menu menu2 = new Menu("Help");
        
        FileChooser fileChooser = new FileChooser();

        MenuItem open = new MenuItem("Open");
        MenuItem exit = new MenuItem("Exit");
        MenuItem about = new MenuItem("About");

        VBox vbBtns = new VBox();
        Button btnShuffle = new Button("Shuffle");
        Button btnNext 	  = new Button("Next");
        btnShuffle.setMinWidth(70);
        btnNext.setMinWidth(50);
        vbBtns.getChildren().add(btnShuffle);
        vbBtns.setAlignment(Pos.CENTER);
        
        btnShuffle.setOnAction(e -> { shuffleAndWrite(textArea); });

        // provisório, depois cria outra vbox pra mostrar o nome e o autor
        about.setOnAction(e -> { showAppInfo(); });
        exit.setOnAction(e -> { primaryStage.close(); });        
        open.setOnAction(e -> { 
       	 	File selectedFile = fileChooser.showOpenDialog(primaryStage); // depois, a file vai ser "listShuffle.file"
        	ListShuffle list = new ListShuffle(selectedFile);
            textArea.setText(list.getText());

        	btnShuffle.setOnAction(action -> { shuffleAndWrite(textArea); });
        });

        menu1.getItems().add(open);
		menu1.getItems().add(exit);
		menu2.getItems().add(about);

        menuBar.getMenus().add(menu1);
        menuBar.getMenus().add(menu2);

        // -----------------------------------------------------------------------------------------
        
        VBox root = new VBox();
        root.setSpacing(10);
        root.getChildren().addAll(vbMenuBar, vbTextArea, vbBtns);
        
        Scene scene = new Scene(root, 450, 580);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}