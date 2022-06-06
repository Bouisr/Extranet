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
public class WebsiteType {
    
    SimpleIntegerProperty Id;
    SimpleStringProperty LabelWebsiteType;
    
    public WebsiteType(int id, String labelWebsiteType){
        
        Id = new SimpleIntegerProperty(id);
        LabelWebsiteType = new SimpleStringProperty(labelWebsiteType);
        
    }
    
        public int getId() {
        return Id.get();
    }

    public void setId(int id) {
        this.Id.set(id);
    }
    
        public String getLabelWebsiteType() {
        return LabelWebsiteType.get();
    }

    public void setLabelWebsiteType(String labelWebsiteType) {
        this.LabelWebsiteType.set(labelWebsiteType);
    }
    
}
