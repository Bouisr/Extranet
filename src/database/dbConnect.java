/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author PC
 */
public class dbConnect {

//    private static final String url = "futaie.me/phpmyadmin"; //"//falbala.futaie.org:3306/bouisr"
//    private static final String login = "admfuta_bouisr";
//    private static final String password = "tahvecee3h";
    private static final String url = "jdbc:mysql://localhost/extranet";
    private static final String login = "root";
    private static final String password = "";  
    
    public static Connection connect() throws ClassNotFoundException, SQLException{
    
        try{
    
    Class.forName("com.mysql.cj.jdbc.Driver");
    Connection conn = DriverManager.getConnection(url, login, password);
    
    return conn;
    
        } catch (ClassNotFoundException e) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
}
}
