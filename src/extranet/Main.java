/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extranet;


import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author PC
 */
public class Main extends Application {
    
    
    /**
     *
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage){
        
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/Views/login.fxml"));
                
                Scene Scene;
                Scene = new Scene(root);                
                primaryStage.setTitle("S'identifiez");
                primaryStage.setScene(Scene);
                primaryStage.show();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
     

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
