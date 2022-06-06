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
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class EditWebsiteTypeController implements Initializable {

    Parent fxml;
    
    @FXML
    private AnchorPane editWebsiteTypeAnchorPane;
    @FXML
    private TextField labelWebsiteTypeField;
    @FXML
    private Label errorLabelWebsiteType;
    @FXML
    private Label successLabel;
    @FXML
    private Button buttonConsultWebsites;
    @FXML
    private TextField idWebsiteTypeField;  

    @FXML
    private void editWebsiteType(ActionEvent event) throws ClassNotFoundException {
        
        clearLabel();
        
         int idWebsiteType = Integer.parseInt(idWebsiteTypeField.getText());
         String labelWebsiteType = labelWebsiteTypeField.getText();
         
            Alert confirmationEdit = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationEdit.setHeaderText(null);
            confirmationEdit.setTitle("Confirmer la modification");
            confirmationEdit.setContentText("Êtes-vous sur de vouloir modifier le type de site web "+labelWebsiteType+" ?");
            Optional<ButtonType> confirmationResult = confirmationEdit.showAndWait();
                            
            if(confirmationResult.get() == ButtonType.OK){
            
            try{
            Connection con = dbConnect.connect();
            PreparedStatement stat;    
            String sql = "UPDATE EXN_WEBSITE_TYPES "
                    + "SET label_website_type = ? "
                    + "WHERE id_website_type = ?";

                if(!labelWebsiteType.equals("")){
            stat = con.prepareStatement(sql);
            stat.setString(1, labelWebsiteType);
            stat.setInt(2, idWebsiteType);            
            stat.execute();
            clearField();
            successLabel.setText("Type de site web modifié avec succés !");
            buttonConsultWebsites.setVisible(true);
            
                }else{

                    if(labelWebsiteType.equals("")){
                        errorLabelWebsiteType.setText("Le label est requis");
                    }

                }
            
            }catch(SQLException ex){
                Logger.getLogger(EditWebsiteTypeController.class.getName()).log(Level.SEVERE, null, ex);
            }
                    
            }
    }

    @FXML
    private void consultWebsites(MouseEvent event) {
        
             try {
            fxml = FXMLLoader.load(getClass().getResource("/Views/Interfaces/website.fxml"));
            editWebsiteTypeAnchorPane.getChildren().removeAll();
            editWebsiteTypeAnchorPane.getChildren().setAll(fxml);
            
        } catch (IOException ex) {
            Logger.getLogger(EditWebsiteTypeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
         public void getDataFromWebsiteTypeController(int id_website_type, String label_website_type){
        
        idWebsiteTypeField.setText(Integer.toString(id_website_type));
        labelWebsiteTypeField.setText(label_website_type);
        
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
