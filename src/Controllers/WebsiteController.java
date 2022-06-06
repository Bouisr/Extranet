/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.User;
import Models.Website;
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
public class WebsiteController implements Initializable {

    private Parent fxml;
    @FXML
    private AnchorPane websiteAnchorPane;
    
    @FXML
    private Button buttonRefreshWebsites;
    
    @FXML
    private Label labelValidationWebsites;
    
  /**
 * Attributs Websites pour la TableView
 *
 * @author PC
 */
    @FXML
    private TableView<Website> tbVWebsites;
    @FXML
    private TableColumn<Website, Integer> tcIdWebsite;
    @FXML
    private TableColumn<Website, Integer> tcNbSiren;
    @FXML
    private TableColumn<Website, Integer> tcIdUser;
    @FXML
    private TableColumn<Website, Integer> tcIdWebsiteType;
    @FXML
    private TableColumn<Website, String> tcLabelWebsite;
    @FXML
    private TableColumn<Website, String> tcUrl;
    @FXML
    private ObservableList<Website> websiteList;
        
 /**
 * Attributs WebsiteTypes pour la TableView
 *
 * @author PC
 */
 
    @FXML
    private TableView<WebsiteType> tbVWebsiteTypes;
    @FXML
    private TableColumn<WebsiteType, Integer> tcIdType;
    @FXML
    private TableColumn<WebsiteType, String> tcLabelWebsiteType;
    @FXML
    private ObservableList<WebsiteType> websiteTypeList;
    
    @FXML
    private Button buttonRefreshWebsiteTypes;
    @FXML
    private Label labelValidationWebsiteTypes;
    
    @FXML
    private void displayAddWebsiteForm(MouseEvent event) throws IOException {
        
             fxml = FXMLLoader.load(getClass().getResource("/Views/Interfaces/add_website.fxml"));
            websiteAnchorPane.getChildren().removeAll();
            websiteAnchorPane.getChildren().setAll(fxml);
    }

    @FXML
    private void refreshWebsite(MouseEvent event) throws IOException {
        
         fxml = FXMLLoader.load(getClass().getResource("/Views/Interfaces/website.fxml"));

        websiteAnchorPane.getChildren().removeAll();
        websiteAnchorPane.getChildren().setAll(fxml);
        
    }

    @FXML
    private void showWebsites() {
        
        try{
            
            tbVWebsites.getItems().clear();
            
            Connection con = dbConnect.connect();
            PreparedStatement stat;
            ResultSet  result;
            String sql = "SELECT * FROM EXN_WEBSITES";
            try{
                
                stat = con.prepareStatement(sql);
                result = stat.executeQuery();
                while(result.next()){
                    
                    websiteList.add(new Website(result.getInt("id_website"),result.getInt("nb_siren"),result.getInt("id_user"),result.getInt("id_website_type"),result.getString("label_website"), result.getString("url")));
                    
                }
                
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
            
            tcIdWebsite.setCellValueFactory(new PropertyValueFactory<Website, Integer>("Id"));
            tcNbSiren.setCellValueFactory(new PropertyValueFactory<Website, Integer>("NbSiren"));
            tcIdUser.setCellValueFactory(new PropertyValueFactory<Website, Integer>("IdUser"));
            tcIdWebsiteType.setCellValueFactory(new PropertyValueFactory<Website, Integer>("IdWebsiteType"));
            tcLabelWebsite.setCellValueFactory(new PropertyValueFactory<Website, String>("LabelWebsite"));
            tcUrl.setCellValueFactory(new PropertyValueFactory<Website, String>("Url"));
            
            buttonEditWebsite();
            buttonDeleteWebsite();
            
            
            tbVWebsites.setItems(websiteList);
            
            this.websiteList = FXCollections.observableArrayList();
        }catch(ClassNotFoundException ex){
            Logger.getLogger(WebsiteController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(WebsiteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void displayAddWebsiteTypeForm(MouseEvent event) throws IOException {
        
            fxml = FXMLLoader.load(getClass().getResource("/Views/Interfaces/add_website_type.fxml"));
            websiteAnchorPane.getChildren().removeAll();
            websiteAnchorPane.getChildren().setAll(fxml);
    }

    @FXML
    private void showWebsiteTypes() {
        
                try{
            
            tbVWebsiteTypes.getItems().clear();
            
            Connection con = dbConnect.connect();
            PreparedStatement stat;
            ResultSet  result;
            String sql = "SELECT * FROM EXN_WEBSITE_TYPES";
            try{
                
                stat = con.prepareStatement(sql);
                result = stat.executeQuery();
                while(result.next()){
                    
                    websiteTypeList.add(new WebsiteType(result.getInt("id_website_type"),result.getString("label_website_type")));
                    
                }
                
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
            
            tcIdType.setCellValueFactory(new PropertyValueFactory<WebsiteType, Integer>("Id"));
            tcLabelWebsiteType.setCellValueFactory(new PropertyValueFactory<WebsiteType, String>("LabelWebsiteType"));
            
            buttonEditWebsiteType();
            buttonDeleteWebsiteType();
            
            
            tbVWebsiteTypes.setItems(websiteTypeList);
            
            this.websiteList = FXCollections.observableArrayList();
        }catch(ClassNotFoundException ex){
            Logger.getLogger(WebsiteController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(WebsiteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
         @FXML
         private void buttonEditWebsite() {
        TableColumn<Website, Void> colBtn = new TableColumn("Action");

        Callback<TableColumn<Website, Void>, TableCell<Website, Void>> cellFactory = new Callback<TableColumn<Website, Void>, TableCell<Website, Void>>() {
            @Override
            public TableCell<Website, Void> call(final TableColumn<Website, Void> param) {
                final TableCell<Website, Void> cell = new TableCell<Website, Void>() {

                    private final Button btn = new Button("Modifier");
                    

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            try {
                               
                                Website website = getTableView().getItems().get(getIndex());
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Interfaces/edit_website.fxml"));
                                fxml = loader.load();
                                EditWebsiteController EditWebsiteController = loader.getController();
                                EditWebsiteController.getDataFromWebsiteController(website.getId(), website.getNbSiren(), website.getIdUser(),  website.getLabelWebsite(),website.getUrl());
                                websiteAnchorPane.getChildren().removeAll();
                                websiteAnchorPane.getChildren().setAll(fxml);

                            } catch (IOException ex) {
                                Logger.getLogger(WebsiteController.class.getName()).log(Level.SEVERE, null, ex);
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

        tbVWebsites.getColumns().add(colBtn);

    }
         
         @FXML
         private void buttonDeleteWebsite() {
        TableColumn<Website, Void> colBtn = new TableColumn("Action");

        Callback<TableColumn<Website, Void>, TableCell<Website, Void>> cellFactory = new Callback<TableColumn<Website, Void>, TableCell<Website, Void>>() {
            @Override
            public TableCell<Website, Void> call(final TableColumn<Website, Void> param) {
                final TableCell<Website, Void> cell = new TableCell<Website, Void>() {
                    

                    private final Button btn = new Button("Supprimer");
                    
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            
                            Website website = getTableView().getItems().get(getIndex());
                            String confirmationLabelWebsite = website.getLabelWebsite();
                            Alert confirmationDelete = new Alert(Alert.AlertType.CONFIRMATION);
                            confirmationDelete.setHeaderText(null);
                            confirmationDelete.setTitle("Confirmer la suppression");
                            confirmationDelete.setContentText("Êtes-vous sur de vouloir supprimer le site web "+confirmationLabelWebsite+" ?");
                            Optional<ButtonType> result = confirmationDelete.showAndWait();
                            
                            if(result.get() == ButtonType.OK){
                                
                                try {
                                    Connection con = dbConnect.connect();
                                    PreparedStatement stat;
                                    int idWebsite = website.getId();
                                    String sql = "DELETE FROM EXN_WEBSITES WHERE id_website = ?";
                                    stat = con.prepareStatement(sql);
                                    stat.setInt(1, idWebsite);
                                    stat.execute();
                                    tbVWebsites.getItems().clear();
                                    buttonRefreshWebsites.setVisible(true);
                                    
                                    labelValidationWebsites.setText("Le site web "+confirmationLabelWebsite+" a bien été suprimmé");
                                    
                                } catch (ClassNotFoundException ex) {
                                    Logger.getLogger(WebsiteController.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (SQLException ex) {
                                    Logger.getLogger(WebsiteController.class.getName()).log(Level.SEVERE, null, ex);
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

        tbVWebsites.getColumns().add(colBtn);

    }
    
         @FXML
         private void buttonEditWebsiteType() {
        TableColumn<WebsiteType, Void> colBtn = new TableColumn("Action");

        Callback<TableColumn<WebsiteType, Void>, TableCell<WebsiteType, Void>> cellFactory = new Callback<TableColumn<WebsiteType, Void>, TableCell<WebsiteType, Void>>() {
            @Override
            public TableCell<WebsiteType, Void> call(final TableColumn<WebsiteType, Void> param) {
                final TableCell<WebsiteType, Void> cell = new TableCell<WebsiteType, Void>() {

                    private final Button btn = new Button("Modifier");
                    

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            try {
                               
                                WebsiteType websiteType = getTableView().getItems().get(getIndex());
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Interfaces/edit_website_type.fxml"));
                                fxml = loader.load();
                                EditWebsiteTypeController EditWebsiteTypeController = loader.getController();
                                EditWebsiteTypeController.getDataFromWebsiteTypeController(websiteType.getId(), websiteType.getLabelWebsiteType());
                                websiteAnchorPane.getChildren().removeAll();
                                websiteAnchorPane.getChildren().setAll(fxml);

                            } catch (IOException ex) {
                                Logger.getLogger(WebsiteController.class.getName()).log(Level.SEVERE, null, ex);
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

        tbVWebsiteTypes.getColumns().add(colBtn);

    }
         
         @FXML
         private void buttonDeleteWebsiteType() {
        TableColumn<WebsiteType, Void> colBtn = new TableColumn("Action");

        Callback<TableColumn<WebsiteType, Void>, TableCell<WebsiteType, Void>> cellFactory = new Callback<TableColumn<WebsiteType, Void>, TableCell<WebsiteType, Void>>() {
            @Override
            public TableCell<WebsiteType, Void> call(final TableColumn<WebsiteType, Void> param) {
                final TableCell<WebsiteType, Void> cell = new TableCell<WebsiteType, Void>() {
                    

                    private final Button btn = new Button("Supprimer");
                    
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            
                            WebsiteType websiteType = getTableView().getItems().get(getIndex());
                            String confirmationLabelWebsiteType = websiteType.getLabelWebsiteType();
                            Alert confirmationDelete = new Alert(Alert.AlertType.CONFIRMATION);
                            confirmationDelete.setHeaderText(null);
                            confirmationDelete.setTitle("Confirmer la suppression");
                            confirmationDelete.setContentText("Êtes-vous sur de vouloir supprimer le type de site web "+confirmationLabelWebsiteType+" ?");
                            Optional<ButtonType> result = confirmationDelete.showAndWait();
                            
                            if(result.get() == ButtonType.OK){
                                
                                try {
                                    Connection con = dbConnect.connect();
                                    PreparedStatement stat;
                                    int idWebsiteType = websiteType.getId();
                                    String sql = "DELETE FROM EXN_WEBSITE_TYPES WHERE id_website_type = ?";
                                    stat = con.prepareStatement(sql);
                                    stat.setInt(1, idWebsiteType);
                                    stat.execute();
                                    tbVWebsiteTypes.getItems().clear();
                                    buttonRefreshWebsiteTypes.setVisible(true);
                                    
                                    labelValidationWebsiteTypes.setText("Le type de site web "+confirmationLabelWebsiteType+" a bien été suprimmé");
                                    
                                } catch (ClassNotFoundException ex) {
                                    Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (SQLException ex) {
                                    Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
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

        tbVWebsiteTypes.getColumns().add(colBtn);

    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            this.websiteList = FXCollections.observableArrayList();
            this.websiteTypeList = FXCollections.observableArrayList();
            Connection con = dbConnect.connect();
            showWebsites();
            showWebsiteTypes();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(WebsiteController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(WebsiteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}
