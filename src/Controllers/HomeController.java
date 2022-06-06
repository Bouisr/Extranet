/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class HomeController implements Initializable {

    private Parent fxml; 
    @FXML
    private AnchorPane homeContentAnchorPane;

    @FXML
    private void displayHome(MouseEvent event) {
        
        try {
            fxml = FXMLLoader.load(getClass().getResource("/Views/Interfaces/home.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void displayProfile(MouseEvent event) {
        
        try {
            fxml = FXMLLoader.load(getClass().getResource("/Views/Interfaces/profile.fxml"));
            homeContentAnchorPane.getChildren().removeAll();
            homeContentAnchorPane.getChildren().setAll(fxml);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void displayUsers(MouseEvent event) {
        
        try {
            fxml = FXMLLoader.load(getClass().getResource("/Views/Interfaces/user.fxml"));
            homeContentAnchorPane.getChildren().removeAll();
            homeContentAnchorPane.getChildren().setAll(fxml);
            
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void displayWebsites(MouseEvent event) {
        
        try {
            fxml = FXMLLoader.load(getClass().getResource("/Views/Interfaces/website.fxml"));
            homeContentAnchorPane.getChildren().removeAll();
            homeContentAnchorPane.getChildren().setAll(fxml);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void displayServices(MouseEvent event) {
        
        try {
            fxml = FXMLLoader.load(getClass().getResource("/Views/Interfaces/service.fxml"));
            homeContentAnchorPane.getChildren().removeAll();
            homeContentAnchorPane.getChildren().setAll(fxml);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
