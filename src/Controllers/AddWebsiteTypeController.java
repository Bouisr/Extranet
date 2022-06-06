/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import database.dbConnect;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class AddWebsiteTypeController implements Initializable {
    
    Parent fxml;

    @FXML
    private AnchorPane addWebsiteTypeAnchorPane;
    @FXML
    private TextField labelWebsiteTypeField;
    @FXML
    private Label errorLabelWebsiteType;
    @FXML
    private Label successLabel;
    @FXML
    private Button buttonConsultWebsites;
    
    String labelWebsiteType;  

    @FXML
    private void addWebsiteType(ActionEvent event) {
        
        clearLabel();
        
        
        try {
            
            // On récupère le label du type de site web envoyé par formulaire
            labelWebsiteType = labelWebsiteTypeField.getText();
            
            Connection con = dbConnect.connect();           
            PreparedStatement stat;
            ResultSet  result;          
            String sql = "INSERT INTO EXN_WEBSITE_TYPES "
                    + "(label_website_type)"
                    + "VALUES(?)";
            
            if(!labelWebsiteType.equals("")){
                stat = con.prepareStatement(sql);
                stat.setString(1, labelWebsiteType);
                
                stat.execute();
                clearField();
                successLabel.setText("Type de site web ajouté avec succés !");
                buttonConsultWebsites.setVisible(true);
                
            }else{ 
                
                if(labelWebsiteType.equals("")){
                    errorLabelWebsiteType.setText("Le nom du type de site web est requis");
                }

            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddWebsiteTypeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AddWebsiteTypeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void consultWebsites(MouseEvent event) {
        
            try {
            fxml = FXMLLoader.load(getClass().getResource("/Views/Interfaces/website.fxml"));
            addWebsiteTypeAnchorPane.getChildren().removeAll();
            addWebsiteTypeAnchorPane.getChildren().setAll(fxml);
            
        } catch (IOException ex) {
            Logger.getLogger(AddWebsiteTypeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
         private void clearLabel(){

         if(!errorLabelWebsiteType.getText().equals("")){
        errorLabelWebsiteType.setText("");
         }

    }
    
    private void clearField(){
        
        labelWebsiteTypeField.setText("");

    }
    
        /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
}
