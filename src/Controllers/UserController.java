/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.Customer;
import Models.User;
import database.dbConnect;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class UserController implements Initializable {
    
    private Parent fxml; 
    @FXML
    private AnchorPane userAnchorPane;

 /**
 * Attributs Utilisateurs pour la TableView
 *
 * @author PC
 */
    
    @FXML
    private TableView<User> tbVUsers;
    @FXML
    private TableColumn<User, Integer> tcIdUser;
    @FXML
    private TableColumn<User, Integer> tcIdRole;
    @FXML
    private TableColumn<User, String> tcFirstname;
    @FXML
    private TableColumn<User, String> tcLastname;
    @FXML
    private TableColumn<User, String> tcEmail;
    @FXML
    private TableColumn<User, String> tcPhone;
    
    @FXML
    private Button buttonRefreshUser;
    @FXML
    private Label labelValidation;
    @FXML
    private ObservableList<User> userList;
    
 /**
 * Attributs Clients pour la TableView
 *
 * @author PC
 */
    @FXML
    private ObservableList<Customer> customerList;
    @FXML
    private TableView<Customer> tbVCustomers;
    @FXML
    private TableColumn<Customer, Integer> tcNbSirenCustomer;
    @FXML
    private TableColumn<Customer, Integer> tcIdUserCustomer;
    @FXML 
    private TableColumn<Customer, String> tcNameCustomer;
    @FXML      
    private TableColumn<Customer, String> tcCommercialNameCustomer;
     @FXML          
    private TableColumn<Customer, String> tcPhoneCustomer;
    @FXML
    private TableColumn<Customer, String> tcAddressCustomer;
    @FXML
    private Button buttonRefreshCustomer;
    @FXML
    private Label labelValidationCustomer;
    
 /**
 * Attributs Administrateurs pour la TableView
 *
 * @author PC
 */    
    @FXML
    private ObservableList<User> adminList;
    @FXML
    private TableView<User> tbVAdmins;
    @FXML
    private TableColumn<User, Integer> tcIdAdmin;
    @FXML
    private TableColumn<User, String> tcFirstnameAdmin;
    @FXML 
    private TableColumn<User, String> tcLastnameAdmin;
    @FXML      
    private TableColumn<User, String> tcEmailAdmin;
    @FXML           
    private TableColumn<User, String> tcPhoneAdmin;
     
  /**
 * Méthode d'affichage des utilisateurs dans la TableView
 *
 * @author PC
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
 */
    @FXML
    public void showUsers() throws ClassNotFoundException, SQLException{
        tbVUsers.getItems().clear();
        
         Connection con = dbConnect.connect();
         PreparedStatement stat;
         ResultSet  result;
        String sql = "SELECT * FROM EXN_USERS WHERE id_role != ?";
        try{
             stat = con.prepareStatement(sql);
             stat.setString(1, "999");
             result = stat.executeQuery();
             while(result.next()){
                 
                 userList.add(new User(result.getInt("id_user"),result.getInt("id_role"),result.getString("firstname"),result.getString("lastname"),result.getString("email"), result.getString("phone")));
                 
             }
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
        tcIdUser.setCellValueFactory(new PropertyValueFactory<User, Integer>("Id"));
        tcIdRole.setCellValueFactory(new PropertyValueFactory<User, Integer>("Role"));
        tcFirstname.setCellValueFactory(new PropertyValueFactory<User, String>("Firstname"));
        tcLastname.setCellValueFactory(new PropertyValueFactory<User, String>("Lastname"));
        tcEmail.setCellValueFactory(new PropertyValueFactory<User, String>("Email"));
        tcPhone.setCellValueFactory(new PropertyValueFactory<User, String>("Phone"));
        
        buttonEditUser();
        buttonDeleteUser();
        
                
        tbVUsers.setItems(userList);
        
        this.userList = FXCollections.observableArrayList();
        
    }
    
        @FXML
        public void showCustomers() throws ClassNotFoundException, SQLException{
            
         tbVCustomers.getItems().clear();
         Connection con = dbConnect.connect();
         PreparedStatement stat;
         ResultSet  result;
        String sql = "SELECT * FROM EXN_CUSTOMERS";
        try{
             stat = con.prepareStatement(sql);
             result = stat.executeQuery();
             while(result.next()){
                 
                 customerList.add(new Customer(result.getInt("nb_siren"),result.getInt("id_user"),result.getString("name"),result.getString("commercial_name"),result.getString("phone"), result.getString("address")));
                 
             }
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
        tcNbSirenCustomer.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("Siren"));
        tcIdUserCustomer.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("Id"));
        tcNameCustomer.setCellValueFactory(new PropertyValueFactory<Customer, String>("Name"));
        tcCommercialNameCustomer.setCellValueFactory(new PropertyValueFactory<Customer, String>("CommercialName"));
        tcPhoneCustomer.setCellValueFactory(new PropertyValueFactory<Customer, String>("Phone"));
        tcAddressCustomer.setCellValueFactory(new PropertyValueFactory<Customer, String>("Address"));
        
        buttonEditCustomer();
        buttonDeleteCustomer();
        
        tbVCustomers.setItems(customerList);
    }
        
        @FXML
        public void showAdmins() throws ClassNotFoundException, SQLException{
         tbVAdmins.getItems().clear();
            
         Connection con = dbConnect.connect();
         PreparedStatement stat;
         ResultSet  result;
        String sql = "SELECT * FROM EXN_USERS WHERE id_role = ?";
        try{
             stat = con.prepareStatement(sql);
             stat.setString(1, "999");
             result = stat.executeQuery();
             while(result.next()){
                 
                 adminList.add(new User(result.getInt("id_user"),result.getString("firstname"),result.getString("lastname"),result.getString("email"), result.getString("phone")));
                 
             }
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
        tcIdAdmin.setCellValueFactory(new PropertyValueFactory<User, Integer>("Id"));
        tcFirstnameAdmin.setCellValueFactory(new PropertyValueFactory<User, String>("Firstname"));
        tcLastnameAdmin.setCellValueFactory(new PropertyValueFactory<User, String>("Lastname"));
        tcEmailAdmin.setCellValueFactory(new PropertyValueFactory<User, String>("Email"));
        tcPhoneAdmin.setCellValueFactory(new PropertyValueFactory<User, String>("Phone"));
        tbVAdmins.setItems(adminList);
            
        }
                
     @FXML   
     private void displayAddUserForm(MouseEvent event) throws IOException{
         
            fxml = FXMLLoader.load(getClass().getResource("/Views/Interfaces/add_user.fxml"));
            userAnchorPane.getChildren().removeAll();
            userAnchorPane.getChildren().setAll(fxml);

     }
         @FXML
         private void buttonEditUser() {
        TableColumn<User, Void> colBtn = new TableColumn("Action");

        Callback<TableColumn<User, Void>, TableCell<User, Void>> cellFactory = new Callback<TableColumn<User, Void>, TableCell<User, Void>>() {
            @Override
            public TableCell<User, Void> call(final TableColumn<User, Void> param) {
                final TableCell<User, Void> cell = new TableCell<User, Void>() {

                    private final Button btn = new Button("Modifier");
                    

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            try {
                                // envoyer les données dans la vue EditUser
                                User user = getTableView().getItems().get(getIndex());
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Interfaces/edit_user.fxml"));
                                fxml = loader.load();
                                EditUserController EditUserController = loader.getController();
                                EditUserController.getDataFromUserController(user.getId(), user.getFirstname(), user.getLastname(), user.getEmail(), user.getPhone());
                                userAnchorPane.getChildren().removeAll();
                                userAnchorPane.getChildren().setAll(fxml);

                            } catch (IOException ex) {
                                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
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

        tbVUsers.getColumns().add(colBtn);

    }
         @FXML
         private void buttonDeleteUser() {
        TableColumn<User, Void> colBtn = new TableColumn("Action");

        Callback<TableColumn<User, Void>, TableCell<User, Void>> cellFactory = new Callback<TableColumn<User, Void>, TableCell<User, Void>>() {
            @Override
            public TableCell<User, Void> call(final TableColumn<User, Void> param) {
                final TableCell<User, Void> cell = new TableCell<User, Void>() {
                    

                    private final Button btn = new Button("Supprimer");
                    
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            
                            User user = getTableView().getItems().get(getIndex());
                            String confirmationFirstname = user.getFirstname();
                            String confirmationLastname = user.getLastname();
                            Alert confirmationDelete = new Alert(AlertType.CONFIRMATION);
                            confirmationDelete.setHeaderText(null);
                            confirmationDelete.setTitle("Confirmer la suppression");
                            confirmationDelete.setContentText("Êtes-vous sur de vouloir supprimer l'utilisateur "+confirmationFirstname+" "+confirmationLastname+" ?");
                            Optional<ButtonType> result = confirmationDelete.showAndWait();
                            
                            if(result.get() == ButtonType.OK){
                                
                                try {
                                    Connection con = dbConnect.connect();
                                    PreparedStatement stat;
                                    int idUser = user.getId();
                                    String sql = "DELETE FROM EXN_USERS WHERE id_user = ?";
                                    stat = con.prepareStatement(sql);
                                    stat.setInt(1, idUser);
                                    stat.execute();
                                    tbVUsers.getItems().clear();
                                    buttonRefreshUser.setVisible(true);
                                    
                                    labelValidation.setText("L'utilisateur "+confirmationFirstname+" "+confirmationLastname+" a bien été suprimmé");
                                    
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

        tbVUsers.getColumns().add(colBtn);

    }
              @FXML   
     private void displayAddCustomerForm(MouseEvent event) throws IOException{
         
            fxml = FXMLLoader.load(getClass().getResource("/Views/Interfaces/add_customer.fxml"));
            userAnchorPane.getChildren().removeAll();
            userAnchorPane.getChildren().setAll(fxml);

     }
         
         @FXML
         private void buttonEditCustomer() {
        TableColumn<Customer, Void> colBtn = new TableColumn("Action");

        Callback<TableColumn<Customer, Void>, TableCell<Customer, Void>> cellFactory = new Callback<TableColumn<Customer, Void>, TableCell<Customer, Void>>() {
            @Override
            public TableCell<Customer, Void> call(final TableColumn<Customer, Void> param) {
                final TableCell<Customer, Void> cell = new TableCell<Customer, Void>() {

                    private final Button btn = new Button("Modifier");
                    

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            try {
                
                                Customer customer = getTableView().getItems().get(getIndex());

                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Interfaces/edit_customer.fxml"));
                                fxml = loader.load();
                                EditCustomerController EditCustomerController = loader.getController();
                                EditCustomerController.getDataFromCustomerController(customer.getSiren(), customer.getId(), customer.getName(), customer.getCommercialName(), customer.getPhone(), customer.getAddress());
                                userAnchorPane.getChildren().removeAll();
                                userAnchorPane.getChildren().setAll(fxml);

                            } catch (IOException ex) {
                                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
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

        tbVCustomers.getColumns().add(colBtn);

    }
         @FXML
         private void buttonDeleteCustomer() {
        TableColumn<Customer, Void> colBtn = new TableColumn("Action");

        Callback<TableColumn<Customer, Void>, TableCell<Customer, Void>> cellFactory = new Callback<TableColumn<Customer, Void>, TableCell<Customer, Void>>() {
            @Override
            public TableCell<Customer, Void> call(final TableColumn<Customer, Void> param) {
                final TableCell<Customer, Void> cell = new TableCell<Customer, Void>() {
                    

                    private final Button btn = new Button("Supprimer");
                    
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            
                            Customer customer = getTableView().getItems().get(getIndex());
                            String confirmationName = customer.getName();
                            Alert confirmationDelete = new Alert(AlertType.CONFIRMATION);
                            confirmationDelete.setHeaderText(null);
                            confirmationDelete.setTitle("Confirmer la suppression");
                            confirmationDelete.setContentText("Êtes-vous sur de vouloir supprimer le client "+confirmationName+" ?");
                            Optional<ButtonType> result = confirmationDelete.showAndWait();
                            
                            if(result.get() == ButtonType.OK){
                                
                                try {
                                    Connection con = dbConnect.connect();
                                    PreparedStatement stat;
                                    int idUser = customer.getId();
                                    int nbSiren = customer.getSiren();
                                    String sql = "DELETE FROM EXN_CUSTOMERS WHERE id_user = ? AND nb_siren = ?";
                                    stat = con.prepareStatement(sql);
                                    stat.setInt(1, idUser);
                                    stat.setInt(2, nbSiren);
                                    stat.execute();
                                    tbVCustomers.getItems().clear();
                                    buttonRefreshCustomer.setVisible(true);
                                    
                                    labelValidationCustomer.setText("Le client "+confirmationName+" a bien été suprimmé");
                                    
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

        tbVCustomers.getColumns().add(colBtn);

    }
    
    @FXML
    private void refreshUser(MouseEvent event) throws IOException{
        
        fxml = FXMLLoader.load(getClass().getResource("/Views/Interfaces/user.fxml"));

        userAnchorPane.getChildren().removeAll();
        userAnchorPane.getChildren().setAll(fxml);
        
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {

            this.userList = FXCollections.observableArrayList();
            this.customerList = FXCollections.observableArrayList();
            this.adminList = FXCollections.observableArrayList();
            Connection con = dbConnect.connect();
            showUsers();
            showCustomers();
            showAdmins();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}
