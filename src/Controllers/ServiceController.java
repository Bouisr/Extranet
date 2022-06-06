/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.Service;
import Models.ServiceType;
import Models.WebsiteType;
import database.dbConnect;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class ServiceController implements Initializable {
    
    Parent fxml;
    
    @FXML
    private ObservableList<Service> serviceList;

    @FXML
    private AnchorPane serviceAnchorPane;
    @FXML
    private Button buttonRefreshServices;
    @FXML
    private Label labelValidationServices;
    @FXML
    private TableView<Service> tbVServices;
    @FXML
    private TableColumn<Service, Integer> tcIdService;
    @FXML
    private TableColumn<Service, Integer> tcIdServiceType;
    @FXML
    private TableColumn<Service, String> tcLabelService;
    @FXML
    private TableColumn<Service, String> tcProviderName;
    @FXML
    private TableColumn<Service, Float> tcPurchasePrice;
    @FXML
    private TableColumn<Service, Date> tcProviderSubscriptionDate;
    @FXML
    private TableColumn<Service, Date> tcProviderRenewalDate;
    @FXML
    private Button buttonRefreshServiceTypes;
    @FXML
    private Label labelValidationServiceTypes;

    @FXML
    private ObservableList<ServiceType> serviceTypeList;    
    @FXML
    private TableView<ServiceType> tbVServiceTypes;
    @FXML
    private TableColumn<ServiceType, Integer> tcIdType;
    @FXML
    private TableColumn<ServiceType, String> tcLabelServiceType; 

    @FXML
    private void displayAddServiceForm(MouseEvent event) throws IOException {
        
            fxml = FXMLLoader.load(getClass().getResource("/Views/Interfaces/add_service.fxml"));
            serviceAnchorPane.getChildren().removeAll();
            serviceAnchorPane.getChildren().setAll(fxml);
    }

    @FXML
    private void refreshService(MouseEvent event) throws IOException {
        
         fxml = FXMLLoader.load(getClass().getResource("/Views/Interfaces/service.fxml"));

        serviceAnchorPane.getChildren().removeAll();
        serviceAnchorPane.getChildren().setAll(fxml);        
    }

    @FXML
    private void showServices() {
        
                try{
            
            tbVServices.getItems().clear();
            
            Connection con = dbConnect.connect();
            PreparedStatement stat;
            ResultSet  result;
            String sql = "SELECT * FROM EXN_SERVICES";
            try{
                
                stat = con.prepareStatement(sql);
                result = stat.executeQuery();
                while(result.next()){
                    
                    serviceList.add(new Service(result.getInt("id_service"),result.getInt("id_service_type"),result.getString("label_service"),result.getFloat("purchase_price"),result.getString("provider"), result.getString("provider_subscription_date"), result.getString("provider_renewal_date")));
                    
                }
                
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
            
            tcIdService.setCellValueFactory(new PropertyValueFactory<Service, Integer>("Id"));
            tcIdServiceType.setCellValueFactory(new PropertyValueFactory<Service, Integer>("IdServiceType"));
            tcLabelService.setCellValueFactory(new PropertyValueFactory<Service, String>("LabelService"));
            tcPurchasePrice.setCellValueFactory(new PropertyValueFactory<Service, Float>("PurchasePrice"));
            tcProviderName.setCellValueFactory(new PropertyValueFactory<Service, String>("Provider"));
            tcProviderSubscriptionDate.setCellValueFactory(new PropertyValueFactory<Service, Date>("ProviderSubscriptionDate"));
            tcProviderRenewalDate.setCellValueFactory(new PropertyValueFactory<Service, Date>("ProviderRenewalDate"));
            
            buttonEditService();
            buttonDeleteService();
            
            
            tbVServices.setItems(serviceList);
            
            this.serviceList = FXCollections.observableArrayList();
            
        }catch(ClassNotFoundException ex){
            Logger.getLogger(ServiceController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ServiceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
         @FXML
         private void buttonEditService() {
        TableColumn<Service, Void> colBtn = new TableColumn("Action");

        Callback<TableColumn<Service, Void>, TableCell<Service, Void>> cellFactory = new Callback<TableColumn<Service, Void>, TableCell<Service, Void>>() {
            @Override
            public TableCell<Service, Void> call(final TableColumn<Service, Void> param) {
                final TableCell<Service, Void> cell = new TableCell<Service, Void>() {

                    private final Button btn = new Button("Modifier");
                    

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            try {
                               
                                Service service = getTableView().getItems().get(getIndex());
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Interfaces/edit_service.fxml"));
                                fxml = loader.load();
                                EditServiceController ServiceController = loader.getController();
                                ServiceController.getDataFromServiceController(service.getId(), service.getLabelService(),  service.getPurchasePrice(),service.getProvider(),service.getProviderSubscriptionDate(),service.getProviderRenewalDate());
                                serviceAnchorPane.getChildren().removeAll();
                                serviceAnchorPane.getChildren().setAll(fxml);

                            } catch (IOException ex) {
                                Logger.getLogger(ServiceController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        tbVServices.getColumns().add(colBtn);

    }
         
         @FXML
         private void buttonDeleteService() {
        TableColumn<Service, Void> colBtn = new TableColumn("Action");

        Callback<TableColumn<Service, Void>, TableCell<Service, Void>> cellFactory = new Callback<TableColumn<Service, Void>, TableCell<Service, Void>>() {
            @Override
            public TableCell<Service, Void> call(final TableColumn<Service, Void> param) {
                final TableCell<Service, Void> cell = new TableCell<Service, Void>() {
                    

                    private final Button btn = new Button("Supprimer");
                    
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            
                            Service service = getTableView().getItems().get(getIndex());
                            String confirmationLabelService = service.getLabelService();
                            Alert confirmationDelete = new Alert(Alert.AlertType.CONFIRMATION);
                            confirmationDelete.setHeaderText(null);
                            confirmationDelete.setTitle("Confirmer la suppression");
                            confirmationDelete.setContentText("Êtes-vous sur de vouloir supprimer le service "+confirmationLabelService+" ?");
                            Optional<ButtonType> result = confirmationDelete.showAndWait();
                            
                            if(result.get() == ButtonType.OK){
                                
                                try {
                                    Connection con = dbConnect.connect();
                                    PreparedStatement stat;
                                    int idService = service.getId();
                                    String sql = "DELETE FROM EXN_SERVICES WHERE id_service = ?";
                                    stat = con.prepareStatement(sql);
                                    stat.setInt(1, idService);
                                    stat.execute();
                                    tbVServices.getItems().clear();
                                    buttonRefreshServices.setVisible(true);
                                    
                                    labelValidationServices.setText("Le service "+confirmationLabelService+" a bien été suprimmé");
                                    
                                } catch (ClassNotFoundException ex) {
                                    Logger.getLogger(ServiceController.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (SQLException ex) {
                                    Logger.getLogger(ServiceController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                
                            }else{
                                
                            }                       
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        tbVServices.getColumns().add(colBtn);

    }
         
    @FXML
    private void displayAddServiceTypeForm(MouseEvent event) throws IOException {

            fxml = FXMLLoader.load(getClass().getResource("/Views/Interfaces/add_service_type.fxml"));
            serviceAnchorPane.getChildren().removeAll();
            serviceAnchorPane.getChildren().setAll(fxml);        
    }

    @FXML
    private void showServiceTypes() {
        
                try{
            
            tbVServiceTypes.getItems().clear();
            
            Connection con = dbConnect.connect();
            PreparedStatement stat;
            ResultSet  result;
            String sql = "SELECT * FROM EXN_SERVICE_TYPES";
            try{
                
                stat = con.prepareStatement(sql);
                result = stat.executeQuery();
                while(result.next()){
                    
                    serviceTypeList.add(new ServiceType(result.getInt("id_service_type"),result.getString("label_service_type")));
                    
                }
                
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
            
            tcIdType.setCellValueFactory(new PropertyValueFactory<ServiceType, Integer>("Id"));
            tcLabelServiceType.setCellValueFactory(new PropertyValueFactory<ServiceType, String>("LabelServiceType"));

            
            buttonEditServiceType();
            buttonDeleteServiceType();
            
            
            tbVServiceTypes.setItems(serviceTypeList);
            
            this.serviceList = FXCollections.observableArrayList();
            
        }catch(ClassNotFoundException ex){
            Logger.getLogger(ServiceController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ServiceController.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }
    
         @FXML
         private void buttonEditServiceType() {
        TableColumn<ServiceType, Void> colBtn = new TableColumn("Action");

        Callback<TableColumn<ServiceType, Void>, TableCell<ServiceType, Void>> cellFactory = new Callback<TableColumn<ServiceType, Void>, TableCell<ServiceType, Void>>() {
            @Override
            public TableCell<ServiceType, Void> call(final TableColumn<ServiceType, Void> param) {
                final TableCell<ServiceType, Void> cell = new TableCell<ServiceType, Void>() {

                    private final Button btn = new Button("Modifier");
                    

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            try {
                               
                                ServiceType serviceType = getTableView().getItems().get(getIndex());
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Interfaces/edit_service_type.fxml"));
                                fxml = loader.load();
                                EditServiceTypeController EditServiceTypeController = loader.getController();
                                EditServiceTypeController.getDataFromServiceTypeController(serviceType.getId(), serviceType.getLabelServiceType());
                                serviceAnchorPane.getChildren().removeAll();
                                serviceAnchorPane.getChildren().setAll(fxml);

                            } catch (IOException ex) {
                                Logger.getLogger(ServiceController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        tbVServiceTypes.getColumns().add(colBtn);

    }
         
         @FXML
         private void buttonDeleteServiceType() {
        TableColumn<ServiceType, Void> colBtn = new TableColumn("Action");

        Callback<TableColumn<ServiceType, Void>, TableCell<ServiceType, Void>> cellFactory = new Callback<TableColumn<ServiceType, Void>, TableCell<ServiceType, Void>>() {
            @Override
            public TableCell<ServiceType, Void> call(final TableColumn<ServiceType, Void> param) {
                final TableCell<ServiceType, Void> cell = new TableCell<ServiceType, Void>() {
                    

                    private final Button btn = new Button("Supprimer");
                    
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            
                            ServiceType serviceType = getTableView().getItems().get(getIndex());
                            String confirmationLabelServiceType = serviceType.getLabelServiceType();
                            Alert confirmationDelete = new Alert(Alert.AlertType.CONFIRMATION);
                            confirmationDelete.setHeaderText(null);
                            confirmationDelete.setTitle("Confirmer la suppression");
                            confirmationDelete.setContentText("Êtes-vous sur de vouloir supprimer le type de service "+confirmationLabelServiceType+" ?");
                            Optional<ButtonType> result = confirmationDelete.showAndWait();
                            
                            if(result.get() == ButtonType.OK){
                                
                                try {
                                    Connection con = dbConnect.connect();
                                    PreparedStatement stat;
                                    int idServiceType = serviceType.getId();
                                    String sql = "DELETE FROM EXN_SERVICE_TYPES WHERE id_service_type = ?";
                                    stat = con.prepareStatement(sql);
                                    stat.setInt(1, idServiceType);
                                    stat.execute();
                                    tbVServiceTypes.getItems().clear();
                                    buttonRefreshServiceTypes.setVisible(true);
                                    
                                    labelValidationServiceTypes.setText("Le type de service "+confirmationLabelServiceType+" a bien été suprimmé");
                                    
                                } catch (ClassNotFoundException ex) {
                                    Logger.getLogger(ServiceController.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (SQLException ex) {
                                    Logger.getLogger(ServiceController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                
                            }else{
                                
                            }                       
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        tbVServiceTypes.getColumns().add(colBtn);

    }    
      /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            this.serviceList = FXCollections.observableArrayList();
            this.serviceTypeList = FXCollections.observableArrayList();
            Connection con = dbConnect.connect();
            showServices();
            showServiceTypes();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServiceController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ServiceController.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }   
    
}
