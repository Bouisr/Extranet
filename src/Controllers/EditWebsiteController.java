/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.WebsiteType;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
public class EditWebsiteController implements Initializable {
    
    private Parent fxml;

    @FXML
    private AnchorPane editWebsiteAnchorPane;
    
    @FXML
    private ObservableList<WebsiteType> websiteTypeList = FXCollections.observableArrayList();
        
    private Label labelWebsiteName;
    @FXML
    private TextField labelWebsiteField;
    @FXML
    private TextField urlWebsiteField;
    @FXML
    private Label errorLabelWebsiteType;
    @FXML
    private Label errorLabelWebsiteName;
    @FXML
    private Label errorLabelUrlWebsite;
    @FXML
    private Label successLabel;
    @FXML
    private Button buttonConsultWebsites;
    @FXML
    private ComboBox<WebsiteType> websiteTypeComboBox;
    @FXML
    private TextField idWebsiteField;
    @FXML
    private TextField nbSirenField;
    @FXML
    private TextField idUserField;
    
       /**
     * Attributs à envoyer en BDD
     */    
    int idWebsite;
    int nbSiren;
    int idUser;
    String idWebsiteText;
    String nbSirenText;
    String idUserText;
    int idWebsiteType;
    String labelWebsite;
    String urlWebsite; 

    @FXML
    private void editWebsite(ActionEvent event) throws ClassNotFoundException {
        
            clearLabel();
                    
            idWebsiteText = idWebsiteField.getText();
            idWebsite = Integer.parseInt(idWebsiteText);
            
            nbSirenText = nbSirenField.getText();
            nbSiren = Integer.parseInt(nbSirenText);
            
            idUserText = idUserField.getText();
            idUser = Integer.parseInt(idUserText);
            
            idWebsiteType = websiteTypeComboBox.getValue().getId();
            labelWebsite = labelWebsiteField.getText();
            urlWebsite = urlWebsiteField.getText();            
            
            Alert confirmationEdit = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationEdit.setHeaderText(null);
            confirmationEdit.setTitle("Confirmer la modification");
            confirmationEdit.setContentText("Êtes-vous sur de vouloir modifier le site web "+labelWebsite+" ?");
            Optional<ButtonType> confirmationResult = confirmationEdit.showAndWait();
                            
            if(confirmationResult.get() == ButtonType.OK){
            
            try{
            Connection con = dbConnect.connect();
            PreparedStatement stat;    
            String sql = "UPDATE EXN_WEBSITES "
                    + "SET id_website_type = ?, label_website = ?, url = ?"
                    + "WHERE id_website = ? AND id_user = ? AND nb_siren = ?";

                if(!labelWebsite.equals("")&&!urlWebsite.equals("")){
            stat = con.prepareStatement(sql);
            stat.setInt(1, idWebsiteType);
            stat.setString(2, labelWebsite);
            stat.setString(3, urlWebsite);
            stat.setInt(4, idWebsite);
            stat.setInt(5, idUser);
            stat.setInt(6, nbSiren);
            
            stat.execute();
            clearField();
            successLabel.setText("Site web modifié avec succés !");
            buttonConsultWebsites.setVisible(true);
            
                }else{

                    if(labelWebsite.equals("")){
                        errorLabelWebsiteName.setText("Le nom est requis");
                    }
                    if(urlWebsite.equals("")){
                        errorLabelUrlWebsite.setText("L'url est requis");
                    }

                }
            
            }catch(SQLException ex){
                Logger.getLogger(EditWebsiteController.class.getName()).log(Level.SEVERE, null, ex);
            }
                    
            }
    }

    @FXML
    private void consultWebsites(MouseEvent event) {
        
            try {
            fxml = FXMLLoader.load(getClass().getResource("/Views/Interfaces/website.fxml"));
            editWebsiteAnchorPane.getChildren().removeAll();
            editWebsiteAnchorPane.getChildren().setAll(fxml);
        } catch (IOException ex) {
            Logger.getLogger(EditWebsiteController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
    
        public void getDataFromWebsiteController(int idWebsite, int nbSiren, int id_user, String label_website, String url){
        
        idWebsiteField.setText(Integer.toString(idWebsite));
        nbSirenField.setText(Integer.toString(nbSiren));
        idUserField.setText(Integer.toString(id_user));
        labelWebsiteField.setText(label_website);
        
        urlWebsiteField.setText(url);                    
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
          if(!errorLabelUrlWebsite.getText().equals("")){
        errorLabelUrlWebsite.setText("");
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
            this.websiteTypeList = FXCollections.observableArrayList();
            Connection con = dbConnect.connect();
            selectWebsiteTypes();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EditWebsiteController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(EditWebsiteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
    
}
