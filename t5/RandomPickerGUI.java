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

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;

import javafx.geometry.Pos;
import javafx.geometry.Insets;



public class RandomPickerGUI extends Application{
    private ListShuffle list;
    private NameBrowser nb;

    private class NameBrowser {
        private ListShuffle list;
        private int curr;

        public NameBrowser() {
            this.list = list;
            this.curr = 0;
        }

        public String next() {
            this.curr = this.curr + 1;
            return this.list.getItem(this.curr);
        }

        public int getCurrent() {
            return this.curr;
        }

        public void reset() {
            this.curr = 0;
        }

        public void setList(ListShuffle l) {
            this.list = l;
        }
    }
	
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
            noFileList.shuffle();
            textArea.setText(noFileList.getText());

            list = noFileList;
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

        NameBrowser nb = new NameBrowser();

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

        HBox hbBtns = new HBox();
        Button btnShuffle = new Button("Shuffle");
        Button btnNext    = new Button("Next");

        VBox vbLabel = new VBox();
        Label current = new Label("");
        vbLabel.setAlignment(Pos.CENTER);
        vbLabel.setSpacing(15);
        vbLabel.getChildren().add(current);

        btnNext.setDisable(true);
        btnShuffle.setMinWidth(70);
        btnNext.setMinWidth(70);
        
        hbBtns.getChildren().addAll(btnShuffle, btnNext);
        hbBtns.setSpacing(15);
        hbBtns.setAlignment(Pos.CENTER);
        
        btnShuffle.setOnAction(e -> { 
            btnNext.setDisable(false);
            shuffleAndWrite(textArea);
            btnShuffle.setDisable(true);

            current.setText(list.getItem(0));

            nb.setList(list);
        
            btnNext.setOnAction(event -> {
                current.setText(nb.next());

                if (current.getText() == list.getLast()) {                    
                    btnNext.setDisable(true);
                    btnShuffle.setDisable(false);
                    nb.reset();
                }
            });
         });

        about.setOnAction(e -> { showAppInfo(); });
        exit.setOnAction(e -> { primaryStage.close(); });        
        open.setOnAction(e -> { 
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
        	list = new ListShuffle(selectedFile);
            textArea.setText(list.getText());

            btnShuffle.setDisable(false);
            btnNext.setDisable(true);


        	btnShuffle.setOnAction(action -> { 
                btnNext.setDisable(false);
                shuffleAndWrite(textArea);  
                btnShuffle.setDisable(true);
                current.setText(list.getItem(0));
                
                nb.setList(list);

                btnNext.setOnAction(event -> {
                    current.setText(nb.next());

                    if (current.getText() == list.getLast()) {                    
                        btnNext.setDisable(true);
                        btnShuffle.setDisable(false);
                        nb.reset();
                    }
                });
            });
            
        });


        menu1.getItems().add(open);
		menu1.getItems().add(exit);
		menu2.getItems().add(about);

        menuBar.getMenus().add(menu1);
        menuBar.getMenus().add(menu2);

        // -----------------------------------------------------------------------------------------
        
        VBox root = new VBox();
        root.setSpacing(10);
        root.getChildren().addAll(vbMenuBar, vbTextArea, hbBtns, vbLabel);
        
        Scene scene = new Scene(root, 450, 610);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}