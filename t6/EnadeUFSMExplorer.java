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

public class EnadeUFSMExplorer extends Application {

// ----------------------- Auxiliar Classes ------------------------

// ----------------------- Auxiliar Methods ------------------------

// ---------------------------- Main -------------------------------
    
    // Função principal.
    public static void main(String[] args) {
		launch(args);
  	}

// ---------------------------- Start ------------------------------
  	@Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("ENADE-UFSM Explorer");
    	
// ---------------- Root ------------------------

        VBox root = new VBox();
        root.setSpacing(10);
       // root.getChildren().addAll();
        
        Scene scene = new Scene(root, 850, 600);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}