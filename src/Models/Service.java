/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author PC
 */
public class Service {
    
    SimpleIntegerProperty Id;
    SimpleIntegerProperty IdServiceType;
    SimpleStringProperty LabelService;
    SimpleFloatProperty PurchasePrice;
    SimpleStringProperty Provider;
    SimpleStringProperty ProviderSubscriptionDate;
    SimpleStringProperty ProviderRenewalDate;

    public Service(int id, int idServiceType, String labelService, float purchasePrice, String provider, String providerSubscriptionDate, String providerRenewalDate){
        
         Id = new SimpleIntegerProperty(id);
         IdServiceType = new SimpleIntegerProperty(idServiceType);
         LabelService = new SimpleStringProperty(labelService);
         PurchasePrice = new SimpleFloatProperty(purchasePrice);
         Provider = new SimpleStringProperty(provider);
         ProviderSubscriptionDate = new SimpleStringProperty(providerSubscriptionDate);
         ProviderRenewalDate = new SimpleStringProperty(providerRenewalDate);
        
    }
    
     public int getId() {
        return Id.get();
    }

     public int getIdServiceType() {
        return IdServiceType.get();
    }

     public String getLabelService() {
        return LabelService.get();
    }     

     public float getPurchasePrice() {
        return PurchasePrice.get();
    }

     public String getProvider() {
        return Provider.get();
    }

     public String getProviderSubscriptionDate() {
        return ProviderSubscriptionDate.get();
    }

     public String getProviderRenewalDate() {
        return ProviderRenewalDate.get();
    }
// A tester pour les dates  
//    public static Date parseDate(String date) {
// try {
//     return new SimpleDateFormat("yyyy-MM-dd").parse(date);
// } catch (ParseException e) {
//     return null;
// }
//}
    
}
