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
public class EditCustomerController implements Initializable {
    
    private Parent fxml;
    @FXML
    private AnchorPane editCustomerAnchorPane;
    
    @FXML
    private TextField idField;
    
    @FXML
    private TextField nbSirenField;

    @FXML
    private TextField nameField;
    @FXML
    private TextField commercialNameField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField addressField;


    /**
     * Attributs à envoyer en BDD
     */    
    int id;
    int nbSiren;
    String nbSirenText;
    String idText;
    String name;
    String commercialName;
    String phone;
    String address;
    
        /**
     * Attributs gestion des erreurs
     */  
    @FXML
    private Label errorLabelName;
    @FXML
    private Label errorLabelCommercialName;
    @FXML
    private Label errorLabelPhone;
    @FXML
    private Label errorLabelAddress;
    
    @FXML
    private Label successLabel;
    
    @FXML
    private Button buttonConsultUsers;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        

    }    

   @FXML
    private void editCustomer(ActionEvent event) throws ClassNotFoundException {
        
            clearLabel();
                    
            idText = idField.getText();
            id = Integer.parseInt(idText);
            
            nbSirenText = nbSirenField.getText();
            nbSiren = Integer.parseInt(nbSirenText);
            
            name = nameField.getText();
            
            commercialName = commercialNameField.getText();
            
            phone = phoneField.getText();
            
            address = addressField.getText();
            
            
            Alert confirmationEdit = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationEdit.setHeaderText(null);
            confirmationEdit.setTitle("Confirmer la modification");
            confirmationEdit.setContentText("Êtes-vous sur de vouloir modifier le client "+name+" ?");
            Optional<ButtonType> confirmationResult = confirmationEdit.showAndWait();
                            
            if(confirmationResult.get() == ButtonType.OK){
            
            try{
            Connection con = dbConnect.connect();
            PreparedStatement stat;
            ResultSet  result;          
            String sql = "UPDATE EXN_CUSTOMERS "
                    + "SET name = ?, commercial_name = ?, phone = ?, address = ?"
                    + "WHERE id_user = ? AND nb_siren = ?";

                if(!name.equals("")&&!commercialName.equals("")&&!phone.equals("")&&!address.equals("")){
            stat = con.prepareStatement(sql);
            stat.setString(1, name);
            stat.setString(2, commercialName);
            stat.setString(3, phone);
            stat.setString(4, address);
            stat.setInt(5, id);
            stat.setInt(6, nbSiren);
            
            stat.execute();
            clearField();
            successLabel.setText("Client modifié avec succés !");
            buttonConsultUsers.setVisible(true);
            
                }else{

                    if(name.equals("")){
                        errorLabelName.setText("Le nom est requis");
                    }
                    if(commercialName.equals("")){
                        errorLabelCommercialName.setText("Le nom commercial est requis");
                    }
                    if(phone.equals("")){
                        errorLabelPhone.setText("Le numéro de téléphone est requis"); 
                }
                        if(address.equals("")){
                        errorLabelAddress.setText("L'adresse du client est requise"); 
                }

                }
            
            }catch(SQLException ex){
                Logger.getLogger(EditUserController.class.getName()).log(Level.SEVERE, null, ex);
            }
                    
            }
    }
    
    private void clearLabel(){

         if(!errorLabelName.getText().equals("")){
        errorLabelName.setText("");
         }
          if(!errorLabelCommercialName.getText().equals("")){
        errorLabelCommercialName.setText("");
          }
           if(!errorLabelPhone.getText().equals("")){
        errorLabelPhone.setText("");
           }
            if(!errorLabelAddress.getText().equals("")){
        errorLabelAddress.setText("");
            }
    }
    
    private void clearField(){
        
        nameField.setText("");
        commercialNameField.setText("");
        phoneField.setText("");
        addressField.setText("");
     
    }
    
    @FXML
    private void consultUsers(MouseEvent event){
        
        try {
            
            fxml = FXMLLoader.load(getClass().getResource("/Views/Interfaces/user.fxml"));
            editCustomerAnchorPane.getChildren().removeAll();
            editCustomerAnchorPane.getChildren().setAll(fxml);
            
        } catch (IOException ex) {
            Logger.getLogger(EditCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
        
    public void getDataFromCustomerController(int nbSiren, int id_user, String name, String commercialName, String phone, String address){
        
        nbSirenField.setText(Integer.toString(nbSiren));
        int idUser = id_user;
        idField.setText(Integer.toString(idUser));
        nameField.setText(name);
        commercialNameField.setText(commercialName);
        phoneField.setText(phone);
        addressField.setText(address);
                    
    }
   
}
