/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author PC
 */
public class Role {
    
    SimpleIntegerProperty Id;
    SimpleStringProperty Label;

    public Role(int id, String label) {
        Id = new SimpleIntegerProperty(id);
        Label = new SimpleStringProperty(label);
    }
    
        public int getId() {
        return Id.get();
    }
        
        public String getLabel(){
            return Label.get();
        }
    
    
    
}
