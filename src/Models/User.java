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
public class User {
    
     SimpleIntegerProperty Id;
     SimpleIntegerProperty Role;
     SimpleStringProperty Firstname;
     SimpleStringProperty Lastname;
     SimpleStringProperty Password;
     SimpleStringProperty Email;
     SimpleStringProperty Phone;

    public User(int id, int role, String firstname, String lastname, String email, String phone) {
        Id = new SimpleIntegerProperty(id);
        Role = new SimpleIntegerProperty(role);
        Firstname = new SimpleStringProperty(firstname);
        Lastname = new SimpleStringProperty(lastname);

        Email = new SimpleStringProperty(email);
        Phone = new SimpleStringProperty(phone);

    }
    
        public User(int id, String firstname, String lastname, String email, String phone) {
        Id = new SimpleIntegerProperty(id);
        Firstname = new SimpleStringProperty(firstname);
        Lastname = new SimpleStringProperty(lastname);
        Email = new SimpleStringProperty(email);
        Phone = new SimpleStringProperty(phone);

    }
        
        public User(int id, String firstname, String lastname){
        Id = new SimpleIntegerProperty(id);
        Firstname = new SimpleStringProperty(firstname);
        Lastname = new SimpleStringProperty(lastname);
        }

    public int getId() {
        return Id.get();
    }

    public void setId(int id) {
        this.Id.set(id);
    }

    public int getRole() {
        return Role.get();
    }

    public void setRole(int role) {
        this.Role.set(role);
    }

    public String getFirstname() {
        return Firstname.get();
    }

    public void setFirstname(String firstname) {
        this.Firstname.set(firstname);
    }

    public String getLastname() {
        return Lastname.get();
    }

    public void setLastname(String lastname) {
        this.Lastname.set(lastname);
    }

    public String getPassword() {
        return Password.get();
    }

    public void setPassword(String password) {
        this.Password.set(password);
    }

    public String getEmail() {
        return Email.get();
    }

    public void setEmail(String email) {
        this.Email.set(email);
    }

    public String getPhone() {
        return Phone.get();
    }

    public void setPhone(String phone) {
        this.Phone.set(phone);
    }    
}
