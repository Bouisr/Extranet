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
public class AddServiceTypeController implements Initializable {
    
    Parent fxml;

    @FXML
    private AnchorPane addServiceTypeAnchorPane;
    @FXML
    private TextField labelServiceTypeField;
    @FXML
    private Label errorLabelServiceType;
    @FXML
    private Label successLabel;
    @FXML
    private Button buttonConsultServices;
    
    String labelServiceType;
  

    @FXML
    private void addServiceType(ActionEvent event) {
        
        clearLabel();
                
        try {
            
            // On récupère le label du type de service envoyé par formulaire
            labelServiceType = labelServiceTypeField.getText();
            
            Connection con = dbConnect.connect();           
            PreparedStatement stat;
            ResultSet  result;          
            String sql = "INSERT INTO EXN_SERVICE_TYPES "
                    + "(label_service_type)"
                    + "VALUES(?)";
            
            if(!labelServiceType.equals("")){
                stat = con.prepareStatement(sql);
                stat.setString(1, labelServiceType);
                
                stat.execute();
                clearField();
                successLabel.setText("Type de service ajouté avec succés !");
                buttonConsultServices.setVisible(true);
                
            }else{ 
                
                if(labelServiceType.equals("")){
                    errorLabelServiceType.setText("Le nom du type de service est requis");
                }

            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddServiceTypeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AddServiceTypeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void consultServices(MouseEvent event) {
        
            try {
            fxml = FXMLLoader.load(getClass().getResource("/Views/Interfaces/service.fxml"));
            addServiceTypeAnchorPane.getChildren().removeAll();
            addServiceTypeAnchorPane.getChildren().setAll(fxml);
            
        } catch (IOException ex) {
            Logger.getLogger(AddServiceTypeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
         private void clearLabel(){

         if(!errorLabelServiceType.getText().equals("")){
        errorLabelServiceType.setText("");
         }

    }
    
    private void clearField(){
        
        labelServiceTypeField.setText("");

    }    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
}
