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

public class LoginPresenter {
    
    String url = "jdbc:mysql://tayuyadbinstance.cyvaijzbdonk.us-east-1.rds.amazonaws.com:3306/";
    String userName = "jesseprn";
    String password = "breezing";
    String dbName = "tayuyaDB";
    String driver = "com.mysql.jdbc.Driver";  

    public String chapterString = "   ";
    public String sectionString = "   ";
    public String questionString = "   ";
    public ArrayList<String> questionArray = new ArrayList<String>();
    
    //public static String currentUser;

    @FXML
    private View selection;

    @FXML
    private TextField usernameIn, passwordIn;

    public void initialize() {  
    }

    @FXML
    void login() throws Exception {
        
        
        
        try {
            //loading drivers for mysql
            Class.forName("com.mysql.jdbc.Driver");
            //setting connection to db
            Connection con = DriverManager.getConnection(url + dbName, userName, password);
            
            String username, password;         
            
            username = usernameIn.getText();
            password = passwordIn.getText();
            
            PreparedStatement ps = con.prepareStatement("select password from users where username = ?");
            ps.setString(1, username);
            //ps.setString(2, password);            
              
            ResultSet rs = ps.executeQuery();
            
            if(rs.next() == false){
                //output that account doesn't exist
                System.out.println("username invalid");
            }
            else{
                String dbPassword = rs.getString(1);
                if(dbPassword.equals(password)){
                    
                    //stores current user as static variable
                    globals.currentGlobalUser = username;
                    
                    Parent pane = FXMLLoader.load(getClass().getResource("../views/selection.fxml"));
                    usernameIn.getScene().setRoot(pane);                                      
                    
                }
                else{
                    //output that passwords don't match
                    System.out.println("password invalid");
                }
            }           
         }
         catch(Exception se){
             
         }       
    }
    
    @FXML
    void createAccount() throws IOException{
        
        Parent pane = FXMLLoader.load(getClass().getResource("../views/account.fxml"));
        usernameIn.getScene().setRoot(pane);        
    }  
    
    @FXML
    void toScores() throws IOException{
        Parent pane = FXMLLoader.load(getClass().getResource("../views/score.fxml"));
        usernameIn.getScene().setRoot(pane);
    }
}
