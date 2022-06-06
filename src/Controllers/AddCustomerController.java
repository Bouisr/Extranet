/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.User;
import java.sql.Connection;
import database.dbConnect;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class AddCustomerController implements Initializable {
    
    @FXML
    private ObservableList<User> userList = FXCollections.observableArrayList();
    
    Parent fxml;

    @FXML
    private AnchorPane addUserAnchorPane;
    @FXML
    private TextField nameField;
    @FXML
    private TextField commercialNameField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField addressField;
    @FXML
    private Label errorLabelUser;
    @FXML
    private Label errorLabelNbSiren;
    @FXML
    private Label errorLabelName;
    @FXML
    private Label errorLabelCommercialName;
    @FXML
    private Label errorLabelPhone;
    @FXML
    private Label successLabel;
    @FXML
    private Button buttonConsultUsers;
    @FXML
    private ComboBox<User> userComboBox;
    @FXML
    private TextField nbSirenField;
    @FXML
    private Label errorLabelAddress;
    
    private int id;
    private String nbSiren;
    private String name;
    private String commercialName;
    private String phone;
    private String address;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            this.userList = FXCollections.observableArrayList();
            Connection con = dbConnect.connect();
            selectUsers();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AddCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    

    @FXML
    private void addCustomer(ActionEvent event) {
        
         clearLabel();
        
        try {
            id = userComboBox.getValue().getId();
            nbSiren = nbSirenField.getText();
            name = nameField.getText();
            commercialName = commercialNameField.getText();
            phone = phoneField.getText();
            address = addressField.getText();
            
            Connection con = dbConnect.connect();
            PreparedStatement stat;     
            String sql = "INSERT INTO EXN_CUSTOMERS "
                    + "(id_user, nb_siren, name, commercial_name, phone, address)"
                    + "VALUES(?, ?, ?, ?, ? , ?)";
            
            if(!nbSiren.equals("")&&!name.equals("")&&!commercialName.equals("")&&!phone.equals("")&&!address.equals("")){
                stat = con.prepareStatement(sql);
                stat.setInt(1, id);
                stat.setString(2, nbSiren);
                stat.setString(3, name);
                stat.setString(4, commercialName);
                stat.setString(5, phone);
                stat.setString(6, address);
                
                stat.execute();
                clearField();
                successLabel.setText("Client ajouté avec succés !");
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
                    errorLabelAddress.setText("L'adresse est requise");
                }
                if(nbSiren.equals("")){
                    errorLabelNbSiren.setText("Le numéro de Siren est requis");
                }
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AddCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

    @FXML
    private void consultUsers(MouseEvent event) {
        
        try {
            fxml = FXMLLoader.load(getClass().getResource("/Views/Interfaces/user.fxml"));
            addUserAnchorPane.getChildren().removeAll();
            addUserAnchorPane.getChildren().setAll(fxml);
        } catch (IOException ex) {
            Logger.getLogger(AddCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
        @FXML
    private void selectUsers() {
                              
        try {
            Connection con;

            con = dbConnect.connect();
            PreparedStatement stat;
            ResultSet  result;
            String sql = "SELECT id_user, firstname, lastname FROM EXN_USERS";
            
            stat = con.prepareStatement(sql);
            result = stat.executeQuery();
            while(result.next()){
                



               
               userList.add(new User(result.getInt("id_user"),result.getString("firstname"), result.getString("lastname")));

               
                

                
            }   } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AddCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }

         userComboBox.setItems(userList);
              
         userComboBox.getSelectionModel().selectFirst(); //select the first element
         
         // On convertit la valeur par défault de la combobox : Objet -> Texte
         userComboBox.setButtonCell(new ListCell<User>() {
    @Override
    protected void updateItem(User t, boolean bln) {
        super.updateItem(t, bln);
        if (t != null) {
            setText(t.getId()+" : "+t.getFirstname() + " " + t.getLastname());
        } else {
            setText(null);
        }
    }
});
         // On convertit la valeur de la liste déroulante de la combobox : Objet -> Texte
         userComboBox.setCellFactory(new Callback<ListView<User>,ListCell<User>>(){

            @Override
            public ListCell<User> call(ListView<User> p) {
                
                final ListCell<User> cell = new ListCell<User>(){

                    @Override
                    protected void updateItem(User t, boolean bln) {
                        super.updateItem(t, bln);
                        
                        if(t != null){
                            setText(t.getId()+" : "+t.getFirstname() + " " + t.getLastname());
                        }else{
                            setText(null);
                        }
                    }
 
                };
                
                return cell;
            }
        });

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
             if(!errorLabelNbSiren.getText().equals("")){
        errorLabelNbSiren.setText("");
             }
    }
    
    private void clearField(){
        
        nameField.setText("");
        commercialNameField.setText("");
        phoneField.setText("");
        addressField.setText("");
        nbSirenField.setText("");
    }
    
}
