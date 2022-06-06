/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.Customer;
import Models.WebsiteType;
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
public class AddWebsiteController implements Initializable {
    
    @FXML
    private ObservableList<Customer> customerList = FXCollections.observableArrayList();
    
    @FXML
    private ObservableList<WebsiteType> websiteTypeList = FXCollections.observableArrayList();
    
    Parent fxml;

    @FXML
    private AnchorPane addWebsiteAnchorPane;
    @FXML
    private TextField labelWebsiteField;
    @FXML
    private TextField urlWebsiteField;
    @FXML
    private Label errorLabelCustomer;
    @FXML
    private Label errorLabelWebsiteType;
    @FXML
    private Label errorLabelWebsiteName;
    @FXML
    private Label errorLabelUrl;
    @FXML
    private Label successLabel;
    @FXML
    private Button buttonConsultWebsites;
    @FXML
    private ComboBox<Customer> customerComboBox;
    @FXML
    private ComboBox<WebsiteType> websiteTypeComboBox;
    @FXML
    private Label labelDataIdCustomer;
    @FXML
    private Label labelDataIdWebsiteType;
    
    private int nbSiren;
    private int idUser;
    private int idWebsiteType;
    private String labelWebsite;
    private String urlWebsite;    

    @FXML
    private void addWebsite(ActionEvent event) {
        
         clearLabel();     
        
        try {
            
            // On récupère l'identifiant et le num Siren du client envoyé par formulaire
            idUser = customerComboBox.getValue().getId();
            nbSiren = customerComboBox.getValue().getSiren();
            
            // On récupère l'identifiant du type de site web envoyé par formulaire
            idWebsiteType = websiteTypeComboBox.getValue().getId();

            labelWebsite = labelWebsiteField.getText();
            urlWebsite = urlWebsiteField.getText();
            Connection con = dbConnect.connect();           
            PreparedStatement stat;
            ResultSet  result;          
            String sql = "INSERT INTO EXN_WEBSITES "
                    + "(id_user, nb_siren, id_website_type, label_website, url)"
                    + "VALUES(?, ?, ?, ?, ?)";
            
            if(!labelWebsite.equals("")&&!urlWebsite.equals("")){
                stat = con.prepareStatement(sql);
                stat.setInt(1, idUser);
                stat.setInt(2, nbSiren);
                stat.setInt(3, idWebsiteType);
                stat.setString(4, labelWebsite);
                stat.setString(5, urlWebsite);
                
                stat.execute();
                clearField();
                successLabel.setText("Site web ajouté avec succés !");
                buttonConsultWebsites.setVisible(true);
                
            }else{ 
                
                if(labelWebsite.equals("")){
                    errorLabelWebsiteName.setText("Le nom du site web est requis");
                }
                if(urlWebsite.equals("")){
                    errorLabelUrl.setText("L'url du site web est requis"); 
                }

            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddWebsiteController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AddWebsiteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void consultWebsites(MouseEvent event) {
        
            try {
            fxml = FXMLLoader.load(getClass().getResource("/Views/Interfaces/website.fxml"));
            addWebsiteAnchorPane.getChildren().removeAll();
            addWebsiteAnchorPane.getChildren().setAll(fxml);
        } catch (IOException ex) {
            Logger.getLogger(AddWebsiteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void selectCustomers() {
                              
        try {
            Connection con;

            con = dbConnect.connect();
            PreparedStatement stat;
            ResultSet  result;
            String sql = "SELECT id_user, nb_siren, name FROM EXN_CUSTOMERS";
            
            stat = con.prepareStatement(sql);
            result = stat.executeQuery();
            while(result.next()){
                
               customerList.add(new Customer(result.getInt("id_user"),result.getInt("nb_siren"), result.getString("name")));
                
            }   } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddWebsiteController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AddWebsiteController.class.getName()).log(Level.SEVERE, null, ex);
        }

         customerComboBox.setItems(customerList);
              
         customerComboBox.getSelectionModel().selectFirst(); //select the first element
         
         // On convertit la valeur par défault de la combobox : Objet -> Texte
         customerComboBox.setButtonCell(new ListCell<Customer>() {
    @Override
    protected void updateItem(Customer t, boolean bln) {
        super.updateItem(t, bln);
        if (t != null) {
//            setText("Id client : "+t.getId()+"; num Siren : "+t.getSiren()+ "; Nom : " + t.getName());
            setText(t.getName());
        } else {
            setText(null);
        }
    }
});
         // On convertit la valeur de la liste déroulante de la combobox : Objet -> Texte
         customerComboBox.setCellFactory(new Callback<ListView<Customer>,ListCell<Customer>>(){

            @Override
            public ListCell<Customer> call(ListView<Customer> p) {
                
                final ListCell<Customer> cell = new ListCell<Customer>(){

                    @Override
                    protected void updateItem(Customer t, boolean bln) {
                        super.updateItem(t, bln);
                        
                        if(t != null){
//                            setText("Id client : "+t.getId()+"; num Siren : "+t.getSiren()+ "; Nom : " + t.getName());
                              setText(t.getName());
                        }else{
                            setText(null);
                        }
                    }
 
                };
                
                return cell;
            }
        });

}
    
    @FXML
    private void selectWebsiteTypes() {
                              
        try {
            Connection con;

            con = dbConnect.connect();
            PreparedStatement stat;
            ResultSet  result;
            String sql = "SELECT id_website_type, label_website_type FROM EXN_WEBSITE_TYPES";
            
            stat = con.prepareStatement(sql);
            result = stat.executeQuery();
            while(result.next()){
                
               websiteTypeList.add(new WebsiteType(result.getInt("id_website_type"),result.getString("label_website_type")));
                
            }   } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddWebsiteController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AddWebsiteController.class.getName()).log(Level.SEVERE, null, ex);
        }

         websiteTypeComboBox.setItems(websiteTypeList);
              
         websiteTypeComboBox.getSelectionModel().selectFirst(); //select the first element
         
         // On convertit la valeur par défault de la combobox : Objet -> Texte
         websiteTypeComboBox.setButtonCell(new ListCell<WebsiteType>() {
    @Override
    protected void updateItem(WebsiteType t, boolean bln) {
        super.updateItem(t, bln);
        if (t != null) {
//            setText("Id type de site web : "+t.getId()+"; Label : "+t.getLabelWebsiteType());
              setText(t.getLabelWebsiteType());
        } else {
            setText(null);
        }
    }
});
         // On convertit la valeur de la liste déroulante de la combobox : Objet -> Texte
         websiteTypeComboBox.setCellFactory(new Callback<ListView<WebsiteType>,ListCell<WebsiteType>>(){

            @Override
            public ListCell<WebsiteType> call(ListView<WebsiteType> p) {
                
                final ListCell<WebsiteType> cell = new ListCell<WebsiteType>(){

                    @Override
                    protected void updateItem(WebsiteType t, boolean bln) {
                        super.updateItem(t, bln);
                        
                        if(t != null){
//                            setText("Id type de site web : "+t.getId()+"; Label : "+t.getLabelWebsiteType());
                              setText(t.getLabelWebsiteType());
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

         if(!errorLabelWebsiteName.getText().equals("")){
        errorLabelWebsiteName.setText("");
         }
          if(!errorLabelUrl.getText().equals("")){
        errorLabelUrl.setText("");
          }

    }
    
    private void clearField(){
        
        labelWebsiteField.setText("");
        urlWebsiteField.setText("");

    }
    
        /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
            try {
                
            this.customerList = FXCollections.observableArrayList();
            this.websiteTypeList = FXCollections.observableArrayList();
            Connection con = dbConnect.connect();
            selectCustomers();
            selectWebsiteTypes();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddWebsiteController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AddWebsiteController.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }
    
}
