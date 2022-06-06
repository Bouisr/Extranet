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
public class ServiceType {
    
    SimpleIntegerProperty Id;
    SimpleStringProperty LabelServiceType;
    
        public ServiceType(int id, String labelServiceType){
        
        Id = new SimpleIntegerProperty(id);
        LabelServiceType = new SimpleStringProperty(labelServiceType);
        
    }
    
        public int getId() {
        return Id.get();
    }

    public void setId(int id) {
        this.Id.set(id);
    }
    
        public String getLabelServiceType() {
        return LabelServiceType.get();
    }

    public void setLabelServiceType(String labelServiceType) {
        this.LabelServiceType.set(labelServiceType);
    }
}
