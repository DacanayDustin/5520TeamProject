package com.csci5520_teamproject.views;

import com.gluonhq.charm.glisten.mvc.View;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class AccountPresenter {
    
    String url = "jdbc:mysql://tayuyadbinstance.cyvaijzbdonk.us-east-1.rds.amazonaws.com:3306/";
    String userName = "jesseprn";
    String password = "breezing";
    String dbName = "tayuyaDB";
    String driver = "com.mysql.jdbc.Driver";  
    
    //@FXML
    //private View account;
    
    @FXML
    private TextField usernameIn, passwordIn, firstName, lastName;
    
    public void initialize() {  
    }
    
    @FXML
    void create() throws Exception {
        
        try {
            //loading drivers for mysql
            Class.forName("com.mysql.jdbc.Driver");
            //setting connection to db
            Connection con = DriverManager.getConnection(url + dbName, userName, password);
            
            String username, password, first, last;
            username = usernameIn.getText();
            password = passwordIn.getText();
            first = firstName.getText();
            last = lastName.getText();
            
            PreparedStatement ps = con.prepareStatement("insert into users values(?,?,?,?)");
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, first);
            ps.setString(4, last);
            
            int i = ps.executeUpdate();
            
        } catch(Exception e){
            
        }    
    
    
    }
}