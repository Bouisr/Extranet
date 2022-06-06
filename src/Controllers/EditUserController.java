/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;
import Models.User;
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
public class EditUserController implements Initializable {
    
    private Parent fxml;
    @FXML
    private AnchorPane editUserAnchorPane;
    
    @FXML
    private TextField idField;

    @FXML
    private TextField firstnameField;
    @FXML
    private TextField lastnameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;

    /**
     * Attributs à envoyer en BDD
     */    
    int id;
    String idText;
    String firstname;
    String lastname;
    String password;
    String email;
    String phone;
    
        /**
     * Attributs gestion des erreurs
     */  
    @FXML
    private Label errorLabelFirstname;
    @FXML
    private Label errorLabelLastname;
    @FXML
    private Label errorLabelPassword;
    @FXML
    private Label errorLabelEmail;
    @FXML
    private Label errorLabelPhone;
    
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
    private void editUser(ActionEvent event) throws ClassNotFoundException {
        
            clearLabel();
                    
            idText = idField.getText();
            id = Integer.parseInt(idText);
            
            firstname = firstnameField.getText();
            
            lastname = lastnameField.getText();
            
            password = passwordField.getText();
            
            email = emailField.getText();
            
            phone = phoneField.getText();
            

            Alert confirmationEdit = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationEdit.setHeaderText(null);
            confirmationEdit.setTitle("Confirmer la modification");
            confirmationEdit.setContentText("Êtes-vous sur de vouloir modifier l'utilisateur "+firstname+" "+lastname+" ?");
            Optional<ButtonType> confirmationResult = confirmationEdit.showAndWait();
                            
            if(confirmationResult.get() == ButtonType.OK){
            
            try{
            Connection con = dbConnect.connect();
            PreparedStatement stat;
            ResultSet  result;          
            String sql = "UPDATE EXN_USERS "
                    + "SET firstname = ?, lastname = ?, password = ?, email = ?, phone = ?"
                    + "WHERE id_user = ?";

                if(!firstname.equals("")&&!lastname.equals("")&&!password.equals("")&&!email.equals("")&&!phone.equals("")){
            stat = con.prepareStatement(sql);
            stat.setString(1, firstname);
            stat.setString(2, lastname);
            stat.setString(3, password);
            stat.setString(4, email);
            stat.setString(5, phone);
            stat.setInt(6, id);
            
            stat.execute();
            clearField();
            successLabel.setText("Utilisateur modifié avec succés !");
            buttonConsultUsers.setVisible(true);
            
                }else{

                    if(firstname.equals("")){
                        errorLabelFirstname.setText("Le prénom est requis");
                    }
                    if(lastname.equals("")){
                        errorLabelLastname.setText("Le nom est requis");
                    }
                    if(password.equals("")){
                        errorLabelPassword.setText("Le mot de passe est requis"); 
                }
                        if(email.equals("")){
                        errorLabelEmail.setText("Le courriel est requis est requis"); 
                }
                                            if(phone.equals("")){
                        errorLabelPhone.setText("Le numéro de téléphone est requis"); 
                }
                }
            
            }catch(SQLException ex){
                Logger.getLogger(AddUserController.class.getName()).log(Level.SEVERE, null, ex);
            }
                    
            }
    }
    
    private void clearLabel(){

         if(!errorLabelFirstname.getText().equals("")){
        errorLabelFirstname.setText("");
         }
          if(!errorLabelLastname.getText().equals("")){
        errorLabelLastname.setText("");
          }
           if(!errorLabelPassword.getText().equals("")){
        errorLabelPassword.setText("");
           }
            if(!errorLabelEmail.getText().equals("")){
        errorLabelEmail.setText("");
            }
             if(!errorLabelPhone.getText().equals("")){
        errorLabelPhone.setText("");
             }
    }
    
    private void clearField(){
        
        firstnameField.setText("");
        lastnameField.setText("");
        passwordField.setText("");
        emailField.setText("");
        phoneField.setText("");
    }
    
    @FXML
    private void consultUsers(MouseEvent event){
        
        try {
            
            fxml = FXMLLoader.load(getClass().getResource("/Views/Interfaces/user.fxml"));
            editUserAnchorPane.getChildren().removeAll();
            editUserAnchorPane.getChildren().setAll(fxml);
            
        } catch (IOException ex) {
            Logger.getLogger(EditUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
        
    public void getDataFromUserController(int id_user, String firstname, String lastname, String email, String phone){
        
        try {
            int idUser = id_user;
            idField.setText(Integer.toString(idUser));
            firstnameField.setText(firstname);
            lastnameField.setText(lastname);
            emailField.setText(email);
            phoneField.setText(phone);
            
            Connection con = dbConnect.connect();
            PreparedStatement stat;
            ResultSet  result;          
            String sql = "SELECT password FROM EXN_USERS WHERE id_user = ?";
            stat = con.prepareStatement(sql);
            stat.setInt(1, idUser);
            result = stat.executeQuery();
            
            while(result.next()){
                passwordField.setText(result.getString("password"));
                System.out.println(result.getString("password"));
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EditUserController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(EditUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
                    
    }
   
}
