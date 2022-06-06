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
public class EditServiceTypeController implements Initializable {
    
    Parent fxml;

    @FXML
    private AnchorPane editServiceTypeAnchorPane;
    @FXML
    private TextField labelServiceTypeField;
    @FXML
    private Label errorLabelServiceType;
    @FXML
    private Label successLabel;
    @FXML
    private Button buttonConsultServices;
    @FXML
    private TextField idServiceTypeField;
  

    @FXML
    private void editServiceType(ActionEvent event) throws ClassNotFoundException {
        
        clearLabel();
        
         int idServiceType = Integer.parseInt(idServiceTypeField.getText());
         String labelServiceType = labelServiceTypeField.getText();
         
            Alert confirmationEdit = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationEdit.setHeaderText(null);
            confirmationEdit.setTitle("Confirmer la modification");
            confirmationEdit.setContentText("Êtes-vous sur de vouloir modifier le type de service "+labelServiceType+" ?");
            Optional<ButtonType> confirmationResult = confirmationEdit.showAndWait();
                            
            if(confirmationResult.get() == ButtonType.OK){
            
            try{
            Connection con = dbConnect.connect();
            PreparedStatement stat;    
            String sql = "UPDATE EXN_SERVICE_TYPES "
                    + "SET label_service_type = ? "
                    + "WHERE id_service_type = ?";

                if(!labelServiceType.equals("")){
            stat = con.prepareStatement(sql);
            stat.setString(1, labelServiceType);
            stat.setInt(2, idServiceType);            
            stat.execute();
            clearField();
            successLabel.setText("Type de service modifié avec succés !");
            buttonConsultServices.setVisible(true);
            
                }else{

                    if(labelServiceType.equals("")){
                        errorLabelServiceType.setText("Le label est requis");
                    }

                }
            
            }catch(SQLException ex){
                Logger.getLogger(EditServiceTypeController.class.getName()).log(Level.SEVERE, null, ex);
            }
                    
            }
    }

    @FXML
    private void consultServices(MouseEvent event) {
        
             try {
            fxml = FXMLLoader.load(getClass().getResource("/Views/Interfaces/service.fxml"));
            editServiceTypeAnchorPane.getChildren().removeAll();
            editServiceTypeAnchorPane.getChildren().setAll(fxml);
            
        } catch (IOException ex) {
            Logger.getLogger(EditServiceTypeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
         public void getDataFromServiceTypeController(int id_service_type, String label_service_type){
        
        idServiceTypeField.setText(Integer.toString(id_service_type));
        labelServiceTypeField.setText(label_service_type);
        
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
