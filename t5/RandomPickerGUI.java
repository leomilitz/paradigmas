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


public class RandomPickerGUI extends Application{
	public static void main(String[] args) {
		launch(args);
  	}

  	@Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Random Picker");

        // ------------------------------------
        // Cria menu bar
        MenuBar menuBar = new MenuBar();
        Menu menu1 = new Menu("File");
        Menu menu2 = new Menu("Help");


        MenuItem open = new MenuItem("Open");
        MenuItem exit = new MenuItem("Exit");
        MenuItem about = new MenuItem("About");

        // provisório, depois cria outra vbox pra mostrar o nome e o autor
        about.setOnAction(e -> { System.out.println("Random Picker, Leonardo Militz"); });
        exit.setOnAction(e -> { primaryStage.close(); });        
        	// ------------------------------------
        	// subsessão filechooser

        	FileChooser fileChooser = new FileChooser();
        	open.setOnAction(e -> { 
        		File selectedFile = fileChooser.showOpenDialog(primaryStage); // depois, a file vai ser "listShuffle.file"
        	});

        	// ------------------------------------

        menu1.getItems().add(open);
		menu1.getItems().add(exit);
		menu2.getItems().add(about);

        menuBar.getMenus().add(menu1);
        menuBar.getMenus().add(menu2);
        // ------------------------------------

        
        VBox vBox = new VBox();
        vBox.getChildren().addAll(menuBar);
        
        Scene scene = new Scene(vBox, 960, 600);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}