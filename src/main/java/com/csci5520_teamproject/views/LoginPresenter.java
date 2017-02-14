package com.csci5520_teamproject.views;

import com.gluonhq.charm.glisten.mvc.View;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class LoginPresenter {

    public String chapterString = "   ";
    public String sectionString = "   ";
    public String questionString = "   ";
    public ArrayList<String> questionArray = new ArrayList<String>();

    @FXML
    private View selection;

    @FXML
    private TextField usernameIn, passwordIn;

    public void initialize() {  
    }

    @FXML
    void login() throws Exception {
        Parent pane = FXMLLoader.load(getClass().getResource("../views/selection.fxml"));
        usernameIn.getScene().setRoot(pane);
    }
    
    @FXML
    void createAccount(){
        
    }

   
}
