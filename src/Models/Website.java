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
public class Website {
    
     SimpleIntegerProperty Id;
     SimpleIntegerProperty NbSiren;
     SimpleIntegerProperty IdUser;
     SimpleIntegerProperty IdWebsiteType;
     SimpleStringProperty LabelWebsite;
     SimpleStringProperty Url;

         public Website(int id, int siren, int idUser, int websiteType, String labelWebsite, String url) {
        Id = new SimpleIntegerProperty(id);
        NbSiren = new SimpleIntegerProperty(siren);
        IdUser = new SimpleIntegerProperty(idUser);
        IdWebsiteType = new SimpleIntegerProperty(websiteType);
        LabelWebsite = new SimpleStringProperty(labelWebsite);
        Url = new SimpleStringProperty(url);

    }
         
         public int getId() {
        return Id.get();
    }

    public void setId(int id) {
        this.Id.set(id);
    }
    
        public int getNbSiren() {
        return NbSiren.get();
    }

    public void setNbSiren(int siren) {
        this.NbSiren.set(siren);
    }
    
        public int getIdUser() {
        return IdUser.get();
    }

    public void setIdUser(int idUser) {
        this.IdUser.set(idUser);
    }
    
        public int getIdWebsiteType() {
        return IdWebsiteType.get();
    }

    public void setIdWebsiteType(int websiteType) {
        this.IdWebsiteType.set(websiteType);
    }
    
        public String getLabelWebsite() {
        return LabelWebsite.get();
    }

    public void setLabelWebsite(String labelWebsite) {
        this.LabelWebsite.set(labelWebsite);
    }
    
        public String getUrl() {
        return Url.get();
    }

    public void setUrl(String url) {
        this.Url.set(url);
    }
    
}
