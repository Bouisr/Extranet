/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import database.dbConnect;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class LoginController implements Initializable {
    
    private String email;
    private String password;
    private String passwordDb;
    private int role;
   private Parent fxml;
    

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label labelError;
    
    @FXML
    private AnchorPane loginAnchorPane;
    
        @FXML
     public void login(ActionEvent event) throws ClassNotFoundException, Exception{
         Connection con = dbConnect.connect();
         PreparedStatement stat;
         ResultSet  rs;
           String sql = "SELECT * FROM EXN_USERS WHERE email = ?";
           email = emailField.getText();
           password = passwordField.getText();
         
         try{
               stat = con.prepareStatement(sql);
               stat.setString(1, email);
               rs = stat.executeQuery();
               if(!rs.isBeforeFirst()){
                   
                   labelError.setText("Courriel ou mot de passe incorrect");
                   
               }else{
                   while(rs.next()){
                       passwordDb = rs.getString("password");
                       if(passwordDb.equals(password)){
                           role = rs.getInt("id_role");
                           
                           switch(role){
                               
                               case 999:
                               Stage login = (Stage) loginAnchorPane.getScene().getWindow();
                               login.close();
                               Stage dashboard = new Stage();
                               fxml = FXMLLoader.load(getClass().getResource("/Views/home.fxml"));
                               Scene dashboardScene = new Scene(fxml);
                               dashboard.setScene(dashboardScene);
                               dashboard.setTitle("Tableau de bord");
                               dashboard.show();
                                   
                               case 100:
                                  
                                   
                               //labelError.setText("Redirection vers client");    
                                   
                               case 1: 
                                   
                               //labelError.setText("Redirection vers utilisateur");
                           }
                       }else{
                           labelError.setText("Courriel ou mot de passe incorrect");
                       }
                   }
               }
         
         }catch(SQLException e){

                System.out.println(e.getMessage());
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
