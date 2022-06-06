/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;

/**
 *
 * @author PC
 */
public class Customer {
    
    SimpleIntegerProperty Siren;
    SimpleIntegerProperty Id;
    SimpleStringProperty Name;
    SimpleStringProperty CommercialName;
    SimpleStringProperty Phone;
    SimpleStringProperty Address;
    Button DeleteButton; 

    public Customer(int siren, int id, String name, String commercialName, String phone, String address) {
        Siren = new SimpleIntegerProperty(siren);
        Id = new SimpleIntegerProperty(id);
        Name = new SimpleStringProperty(name);
        CommercialName = new SimpleStringProperty(commercialName);
        Phone = new SimpleStringProperty(phone);
        Address = new SimpleStringProperty(address);
        this.DeleteButton = new Button("Supprimer");
    }
    
    public Customer(int id, int siren, String name){
        
        Id = new SimpleIntegerProperty(id);
        Siren = new SimpleIntegerProperty(siren);
        Name = new SimpleStringProperty(name);
        
    }
    
    public int getSiren() {
        return Siren.get();
    }

    public void setSiren(int siren) {
        this.Siren.set(siren);
    }

    public int getId() {
        return Id.get();
    }

    public void setId(int id) {
        this.Id.set(id);
    }

    public String getName() {
        return Name.get();
    }

    public void setName(String name) {
        this.Name.set(name);
    }

    public String getCommercialName() {
        return CommercialName.get();
    }

    public void setCommercialName(String commercialName) {
        this.CommercialName.set(commercialName);
    }

    public String getPhone() {
        return Phone.get();
    }

    public void setPhone(String phone) {
        this.Phone.set(phone);
    }

    public String getAddress() {
        return Address.get();
    }

    public void setAddress(String address) {
        this.Address.set(address);
    }
    
    public void setDeleteButton(Button delete){
        this.DeleteButton = delete;
    }
    
    public Button getDeleteButton(){
        return DeleteButton;
    }
    
}
