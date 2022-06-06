/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.ServiceType;
import database.dbConnect;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
import javafx.scene.control.DatePicker;
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
public class EditServiceController implements Initializable {
    
    Parent fxml;
    
    @FXML
    private ObservableList<ServiceType> serviceTypeList = FXCollections.observableArrayList();

    @FXML
    private AnchorPane editServiceAnchorPane;
    @FXML
    private TextField labelServiceField;
    @FXML
    private TextField purchasePriceField;
    @FXML
    private Label errorLabelServiceType;
    @FXML
    private Label errorLabelServiceName;
    @FXML
    private Label errorLabelProviderName;
    @FXML
    private Label errorLabelSubscriptionDate;
    @FXML
    private Label errorLabelRenewalDate;
    @FXML
    private Label successLabel;
    @FXML
    private Button buttonConsultServices;
    @FXML
    private ComboBox<ServiceType> serviceTypeComboBox;
    @FXML
    private DatePicker providerSubscriptionDatePicker;
    @FXML
    private DatePicker providerRenewalDatePicker;
    @FXML
    private TextField providerField;
    @FXML
    private Label errorLabelPurchasePrice;
    @FXML
    private TextField idServiceField;
    
    int idService;
    int idServiceType;
    String labelService;
    String providerName;
    Float purchasePrice;
    Date providerSubscriptionDate;
    Date providerRenewalDate;

    @FXML
    private void editService(ActionEvent event) throws ClassNotFoundException {
        
            // On vide les labels d'erreurs
            clearLabel();
                    
            // On récupère l'identifiant du service
            idService = Integer.parseInt(idServiceField.getText());
            
            // On récupère l'identifiant du type de service envoyé par formulaire
            idServiceType = serviceTypeComboBox.getValue().getId();
            
            // On récupère le label du service et le nom du fournisseur
            labelService = labelServiceField.getText();
            providerName = providerField.getText();
            
            // On récupère le prix d'achat du service qu'on convertit en réel
            purchasePrice = Float.parseFloat(purchasePriceField.getText());
            
            // On récupère la date d'abonnement qu'on convertit au format anglophone
            providerSubscriptionDate = parseDate(providerSubscriptionDatePicker.getValue().toString());
            
            // On récupère la date de renouvellement qu'on convertit au format anglophone
            providerRenewalDate = parseDate(providerRenewalDatePicker.getValue().toString());  
            
            Alert confirmationEdit = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationEdit.setHeaderText(null);
            confirmationEdit.setTitle("Confirmer la modification");
            confirmationEdit.setContentText("Êtes-vous sur de vouloir modifier le service "+labelService+" ?");
            Optional<ButtonType> confirmationResult = confirmationEdit.showAndWait();
                            
            if(confirmationResult.get() == ButtonType.OK){
            
            try{
            Connection con = dbConnect.connect();
            PreparedStatement stat;    
            String sql = "UPDATE EXN_SERVICES "
                    + "SET id_service_type = ?, label_service = ?, provider = ?, purchase_price = ?, provider_subscription_date = ?, provider_renewal_date = ? "
                    + "WHERE id_service = ?";

            // Si les champs ne sont pas vides
            if(!(idServiceType == 0 )&&!labelService.equals("")&&!providerName.equals("")&&!purchasePrice.equals("")&&!providerSubscriptionDate.equals("")&&!providerRenewalDate.equals("")){
            stat = con.prepareStatement(sql);
            stat.setInt(1, idServiceType);
            stat.setString(2, labelService);
            stat.setString(3, providerName);
            stat.setFloat(4, purchasePrice);
            
            // Conversion des types dates en type SQL date 
            var providerSubscriptionDateToSql = convert(providerSubscriptionDate);
            stat.setDate(5, providerSubscriptionDateToSql);
                
            var providerRenewalDateToSql = convert(providerRenewalDate);
            stat.setDate(6, providerRenewalDateToSql);            
            
            stat.setInt(7, idService);            
            stat.execute();
            
            // On vide les champs
            clearField();
            
            successLabel.setText("Site web modifié avec succés !");
            
            // On affiche le bouton permettant de consulter les services
            buttonConsultServices.setVisible(true);
            
                }else{

                // On affiche des messages d'erreur en fonction des champs remplis
                if(idServiceType == 0){
                    errorLabelServiceType.setText("Vous devez séléctionner un type de service");
                }
                if(labelServiceField.equals("")){
                    errorLabelServiceName.setText("Le nom du service est requis"); 
                }
                if(providerName.equals("")){
                    errorLabelProviderName.setText("Le nom du fournisseur est requis"); 
                }
                if(purchasePrice.equals("")){
                    errorLabelPurchasePrice.setText("Vous devez indiquer le prix du service"); 
                }
                if(providerSubscriptionDate.equals("")){
                    errorLabelSubscriptionDate.setText("Vous devez séléctionner la date d'abonnement chez le fournisseur"); 
                }
                if(providerRenewalDate.equals("")){
                    errorLabelRenewalDate.setText("Vous devez séléctionner la date de renouvellement de l'abonnement chez le fournisseur"); 
                } 

                }
            
            }catch(SQLException ex){
                Logger.getLogger(EditServiceController.class.getName()).log(Level.SEVERE, null, ex);
            }
                    
            }        
    }

    @FXML
    private void consultServices(MouseEvent event) {
        
            try {
            fxml = FXMLLoader.load(getClass().getResource("/Views/Interfaces/service.fxml"));
            editServiceAnchorPane.getChildren().removeAll();
            editServiceAnchorPane.getChildren().setAll(fxml);
        } catch (IOException ex) {
            Logger.getLogger(EditServiceController.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
        // Méthode qui instancie les champs avec les données du service 
        public void getDataFromServiceController(int idService, String labelService, Float purchasePrice, String providerName, String subscriptionDate, String renewalDate){
        
         idServiceField.setText(Integer.toString(idService));
         labelServiceField.setText(labelService);
         purchasePriceField.setText(Float.toString(purchasePrice));
         providerField.setText(providerName);
         providerSubscriptionDatePicker.setValue(convert(subscriptionDate));
         providerRenewalDatePicker.setValue(convert(renewalDate));
                          
    }
        
    @FXML
    private void selectServiceTypes() {
                              
        try {
            Connection con;

            con = dbConnect.connect();
            PreparedStatement stat;
            ResultSet  result;
            String sql = "SELECT id_service_type, label_service_type FROM EXN_SERVICE_TYPES";
            
            stat = con.prepareStatement(sql);
            result = stat.executeQuery();
            while(result.next()){
                
               serviceTypeList.add(new ServiceType(result.getInt("id_service_type"),result.getString("label_service_type")));
                
            }   } catch (ClassNotFoundException ex) {
            Logger.getLogger(EditServiceController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(EditServiceController.class.getName()).log(Level.SEVERE, null, ex);
        }

         serviceTypeComboBox.setItems(serviceTypeList);
              
        serviceTypeComboBox.getSelectionModel().selectFirst(); //select the first element
         
         // On convertit la valeur par défault de la combobox : Objet -> Texte
         serviceTypeComboBox.setButtonCell(new ListCell<ServiceType>() {
    @Override
    protected void updateItem(ServiceType t, boolean bln) {
        super.updateItem(t, bln);
        if (t != null) {

              setText(t.getLabelServiceType());
        } else {
            setText(null);
        }
    }
});
         // On convertit la valeur de la liste déroulante de la combobox : Objet -> Texte
         serviceTypeComboBox.setCellFactory(new Callback<ListView<ServiceType>,ListCell<ServiceType>>(){

            @Override
            public ListCell<ServiceType> call(ListView<ServiceType> p) {
                
                final ListCell<ServiceType> cell = new ListCell<ServiceType>(){

                    @Override
                    protected void updateItem(ServiceType t, boolean bln) {
                        super.updateItem(t, bln);
                        
                        if(t != null){

                              setText(t.getLabelServiceType());
                        }else{
                            setText(null);
                        }
                    }
 
                };
                
                return cell;
            }
        });

}
       
    // Méthode qui vide les labels d'erreur
          private void clearLabel(){

         if(!errorLabelServiceType.getText().equals("")){
        errorLabelServiceType.setText("");
         }
          if(!errorLabelServiceName.getText().equals("")){
             errorLabelServiceName.setText("");
          }
          if(!errorLabelProviderName.getText().equals("")){
             errorLabelProviderName.setText("");
          }
          if(!errorLabelPurchasePrice.getText().equals("")){
             errorLabelPurchasePrice.setText("");
          }
          if(!errorLabelSubscriptionDate.getText().equals("")){
             errorLabelSubscriptionDate.setText("");
          }
          if(!errorLabelRenewalDate.getText().equals("")){
             errorLabelRenewalDate.setText("");
          }          

    }
    
    // Méthode qui vide les champs
    private void clearField(){
        
        labelServiceField.setText("");
        providerField.setText("");
        purchasePriceField.setText("");
        providerSubscriptionDatePicker.setValue(null);
        providerRenewalDatePicker.setValue(null);

    }   
    
 // Méthode qui convertit une chaine en localDate
public static LocalDate convert(String date) {

  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  //convert String to LocalDate
  LocalDate localDate = LocalDate.parse(date, formatter);
  return localDate;
}

    // Méthode qui convertit une chaine en java.util.date au format Anglophone
    private Date parseDate(String date) {
 try {
     return new SimpleDateFormat("yyyy-MM-dd").parse(date);
 } catch (ParseException e) {
     return null;
 }
}    
    // Méthode qui converit une java.util.Date en java.sql.Date
    private static java.sql.Date convert(java.util.Date uDate) {
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        return sDate;
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            this.serviceTypeList = FXCollections.observableArrayList();
            Connection con = dbConnect.connect();
            selectServiceTypes();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EditServiceController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(EditServiceController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }    
}
