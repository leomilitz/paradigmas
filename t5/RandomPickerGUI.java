import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.application.Application;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import javafx.stage.FileChooser;
import javafx.scene.control.Button;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;

import javafx.scene.control.TextArea;

import javafx.scene.control.Label;

import javafx.geometry.Pos;


public class RandomPickerGUI extends Application{
	public static void main(String[] args) {
		launch(args);
  	}

    public void shuffleAndWrite(TextArea textArea) {
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

  	@Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Random Picker");

        // ------------------------------------
        // Cria menu bar
        MenuBar menuBar = new MenuBar();
        
        TextArea textArea = new TextArea();
        textArea.setPrefRowCount(23);
        
        Menu menu1 = new Menu("File");
        Menu menu2 = new Menu("Help");
        
        FileChooser fileChooser = new FileChooser();

        MenuItem open = new MenuItem("Open");
        MenuItem exit = new MenuItem("Exit");
        MenuItem about = new MenuItem("About");

        Button btnShuffle = new Button("Shuffle");
        Button btnNext 	  = new Button("Next");
        btnShuffle.setMinWidth(70);
        btnNext.setMinWidth(50);
        
        btnShuffle.setOnAction(e -> { shuffleAndWrite(textArea); });
        // provisório, depois cria outra vbox pra mostrar o nome e o autor
        about.setOnAction(e -> { System.out.println("Leonardo Militz, Random Picker"); });
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
        
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(menuBar, textArea, btnShuffle);
        
        Scene scene = new Scene(vBox, 450, 600);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}