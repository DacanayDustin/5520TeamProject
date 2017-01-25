package com.csci5520_teamproject.views;

import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.csci5520_teamproject.CSCI5520_TeamProject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class SelectionPresenter {

    public String chapterString = "   ";
    public String sectionString = "   ";
    public String questionString = "   ";
    public ArrayList<String> questionArray = new ArrayList<String>();

    @FXML
    private View selection;

    @FXML
    private Label label;

    @FXML
    private ComboBox chapCB, secCB, questCB;

    public void initialize() {
        selection.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e
                        -> MobileApplication.getInstance().showLayer(CSCI5520_TeamProject.MENU_LAYER)));
                appBar.setTitleText("Selection");
                appBar.getActionItems().add(MaterialDesignIcon.SEARCH.button(e
                        -> System.out.println("Search")));
            }
        });

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
        secCB.setItems(findSections(chapterString));
        //setQuestionArray("CHAPTER", chapterString);
        sectionString = "   ";
        questionString = "   ";
    }

    @FXML
    void dropSection() {
        sectionString = (String) secCB.getSelectionModel().getSelectedItem();
        if (sectionString == "No Sections") {
            questCB.setItems(findQuestions("Chapter"));
        } else {
            questCB.setItems(findQuestions(sectionString));
        }
        questionString = "   ";
    }

    @FXML
    void dropQuestion() {
        questionString = (String) questCB.getValue();
    }

    @FXML
    void go() throws Exception {
        for (int i = 0; i < questionArray.size(); i++){
            System.out.println(questionArray.get(i));
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("temp.txt"))) {
            String content = "This is the content to write into file\n";
            bw.write(chapterString + "," + sectionString + "," + questionString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent pane = FXMLLoader.load(getClass().getResource("../views/question.fxml"));
        chapCB.getScene().setRoot(pane);
    }

    public ObservableList<String> findSections(String chapter) {
        ObservableList<String> sections = FXCollections.observableArrayList();
        try {
            String chapterFile = "mcquestions/" + chapter + ".txt";
            BufferedReader reader = new BufferedReader(new FileReader(chapterFile));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("Section")) {
                    sections.add(line);
                }
            }
            reader.close();
        } catch (Exception e) {
            //System.err.format("Exception occurred trying to read '%s'.", filename);
            e.printStackTrace();
        }
        if (sections.size() == 0) {
            sections.add("No Sections");
            sectionString = "No Sections";
        }
        return sections;
    }

    public ObservableList<String> findQuestions(String section) {
        ObservableList<String> questions = FXCollections.observableArrayList();
        try {
            questionArray.clear();
            String chapterFile = "mcquestions/" + chapterString + ".txt";
            BufferedReader reader = new BufferedReader(new FileReader(chapterFile));
            String line;
            boolean go = true;
            while ((line = reader.readLine()) != null && go) {
                if (line.contains(section)) {
                    while ((line = reader.readLine()) != null && go) {
                        if (line.contains("#") && go) {
                            line = reader.readLine();
                            if (!line.contains("Section")) {
                                line = line.substring(0, line.indexOf('.'));
                                questions.add(line);
                                questionArray.add(line);
                            } else {
                                go = false;
                            }
                        }
                    }
                }
            }
            reader.close();
        } catch (Exception e) {
            //System.err.format("Exception occurred trying to read '%s'.", filename);
            e.printStackTrace();
        }
        return questions;
    }

    public void setQuestionArray(String type, String part) {
        if (type == "CHAPTER") {

        }
        if (type == "SECTION") {

        }
        if (type == "QUESTION") {

        }
    }
}
