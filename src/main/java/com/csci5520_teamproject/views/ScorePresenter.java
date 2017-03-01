package com.csci5520_teamproject.views;

import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.impl.charm.a.b.b.i;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

/**
 *
 * @author jesse
 */
public class ScorePresenter {

    String url = "jdbc:mysql://tayuyadbinstance.cyvaijzbdonk.us-east-1.rds.amazonaws.com:3306/";
    String userName = "jesseprn";
    String password = "breezing";
    String dbName = "tayuyaDB";
    String driver = "com.mysql.jdbc.Driver";

    public String userString = "";
    public String chapterString = "";
    //public String sectionString = "";
    //public String questionString = "";
    public ArrayList<String> questionArray = new ArrayList<String>();

    @FXML
    private View selection;

    @FXML
    private TextArea textArea;

    @FXML
    private GridPane grid;
    @FXML
    private Label label;

    @FXML
    private ComboBox chapCB, userCB;

    public void initialize() throws ClassNotFoundException, SQLException {

        chapCB.getItems().addAll(
                "Chapter 1: Introduction to Computers, Programs, and Java ",
                "Chapter 2: Elementary Programming",
                "Chapter 3: Selections",
                "Chapter 4: Mathematical Functions, Characters, and Strings",
                "Chapter 5: Loops",
                "Chapter 6: Methods",
                "Chapter 7: Single-Dimensional Arrays",
                "Chapter 8: Multidimensional Arrays",
                "Chapter 9: Objects and Classes",
                "Chapter 10: Object-Oriented Thinking",
                "Chapter 11: Inheritance and Polymorphism",
                "Chapter 12: Exception Handling and Text I/O",
                "Chapter 13: Abstract Classes and Interfaces",
                "Chapter 14: JavaFX Basics",
                "Chapter 15: Event-Driven Programming and Animations",
                "Chapter 16: JavaFX UI Controls and Multimedia",
                "Chapter 17: Binary I/O",
                "Chapter 18: Recursion",
                "Chapter 19: Generics",
                "Chapter 20: Lists, Stacks, Queues, and Priority Queues",
                "Chapter 21: Sets and Maps",
                "Chapter 22: Developing Efficient Algorithms ",
                "Chapter 23: Sorting",
                "Chapter 24: Implementing Lists, Stacks, Queues, and Priority Queues",
                "Chapter 25: Binary Search Trees",
                "Chapter 26: AVL Trees",
                "Chapter 27: Hashing",
                "Chapter 28: Graphs and Applications",
                "Chapter 29: Weighted Graphs and Applications",
                "Chapter 30: Aggregate Operations on Collection Streams",
                "Chapter 31: Advanced JavaFX",
                "Chapter 32: Multithreading and Parallel Programming",
                "Chapter 33: Networking",
                "Chapter 34: Java Database Programming",
                "Chapter 35: Advanced Database Programming",
                "Chapter 36: Internationalization",
                "Chapter 37: Servlets",
                "Chapter 38: JavaServer Pages",
                "Chapter 39: JavaServer Faces",
                "Chapter 40: Remote Method Invocation",
                "Chapter 41: Web Services",
                "Chapter 42: 2-4 Trees and B-Trees",
                "Chapter 43: Red-Black Trees",
                "Chapter 44: Testing Using JUnit");

        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(url + dbName, userName, password);
        PreparedStatement ps = con.prepareStatement("select username from users");
        try {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                userCB.getItems().addAll(FXCollections.observableArrayList(rs.getString(1)));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void dropChapter() {
        chapterString = (String) chapCB.getSelectionModel().getSelectedItem();
        chapterString = chapterString.substring(0, chapterString.lastIndexOf(':'));
        chapterString.toLowerCase();
        if (chapterString.length() == 9) {
            chapterString = "chapter0" + chapterString.charAt(8);
        } else {
            chapterString = "chapter" + chapterString.charAt(8) + chapterString.charAt(9);
        }
        //secCB.setItems(findSections(chapterString));
        //sectionString = "";
        //questionString = "";
    }

    @FXML
    void dropUser() {
        userString = (String) userCB.getSelectionModel().getSelectedItem();

    }

    @FXML
    void go() throws Exception {

        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(url + dbName, userName, password);
        PreparedStatement ps = con.prepareStatement("select * from " + chapterString + " where username= ?");

        ps.setString(1, userString);
        //System.out.println("ps is: select " + userString + " from " + chapterString);

        try {

            ResultSet rs = ps.executeQuery();
            //ResultSet rs2 = ps.executeQuery();
            int columnCount = rs.getMetaData().getColumnCount();
            String row = "";
            grid.add(new Label("Question   "), 0, 0);
            grid.add(new Label("First Attempt   "), 1, 0);
            grid.add(new Label("Last Attempt"), 2, 0);
            int num1, num2, sum1, sum2;
            num1 = num2 = sum1 = sum2 = 0;

            for (int i = 2; i <= columnCount; i = i + 2) {
                grid.add(new Label(rs.getMetaData().getColumnName(i)), 0, i);

                //row += rs.getMetaData().getColumnName(i) + "\t";
            }

            /* textArea.appendText(row + '\n');
            ArrayList<String> arrayList = new ArrayList<String>();
             */
            while (rs.next()) {
                row = "";
                for (int i = 2; i < columnCount; i = i + 2) {
                    grid.add(new Label(rs.getString(i) + "   "), 1, i);
                    //add num to numerator
                    num1 = num1 + Integer.parseInt(rs.getString(i));
                    sum1++;
                    grid.add(new Label(rs.getString(i + 1)), 2, i);
                    num2 = num2 + Integer.parseInt(rs.getString(i + 1));
                    sum2++;
                    //row += rs.getString(i) + "\t";

                }
                //
            }

            textArea.clear();
            textArea.appendText("User's First Attempt Score: " + (double) num1 / sum1 + "\n");

            textArea.appendText("User's Last Attempt Score: " + (double) num2 / sum2 + "\n");

            /*while (rs.next()) {              
                int i = 1;
                while(i <= columnCount) {
                  arrayList.add(rs.getString(i++));
                 }
            }
             */
            //for (int p = 1; p < columnCount+1; p = p+2){
            //    grid.add(new Label(rs2.getMetaData().getColumnName(p)), 0, p );
            //}
            /*
            for(int j = 1; j < columnCount+1; j=j+2){
                
                    grid.add(new Label(arrayList.get(j)), 0, j+1);
                    grid.add(new Label(arrayList.get(j+1)), 1, j+1);
                
            }
            /*
            
            //String row = "";
            //for (int i = 1; i <= columnCount; i++) { 
                //row += rs.getMetaData().getColumnName(i) + "\t"; 
        // } 
        
         //textArea.appendText(row + '\n'); 
           /* for(int i = 0; i < 3; i++){
                while(rs.next()){
                    grid.addItems().addAll(FXCollections.observableArrayList(rs.getString(1)));
                }
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void columnNames() {

    }
}
