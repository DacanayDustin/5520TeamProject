package com.csci5520_teamproject.views;

import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.mvc.View;
import com.csci5520_teamproject.Question;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class QuestionPresenter extends SelectionPresenter {

    public ArrayList<String> questionArray2 = new ArrayList<String>();

    public String chapterString, sectionString, questionString;
    ArrayList<Question> questionArray = new ArrayList<>();
    ArrayList<RadioButton> radioButtonArray = new ArrayList<>();
    ArrayList<CheckBox> checkBoxArray = new ArrayList<>();
    ToggleGroup group = new ToggleGroup();
    static int count = 0;
    static int correct = 0;
    @FXML
    private View question;

    @FXML
    private Button back, show;

    @FXML
    ComboBox chapCB, secCB, questCB;
    @FXML
    private Label questionLabel, hintLabel, feedBack;

    @FXML
    private VBox vBoxQuest;

    @FXML
    private TextFlow questionTF;

    ToggleGroup questionTog = new ToggleGroup();
    public Question t = new Question();

    public void initialize() {
        question.setShowTransitionFactory(BounceInRightTransition::new);
        questionsToTxtFile();
        txtFileToArray();
        ArrayToScene();

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

    public void questionsToTxtFile() {
        try {
            String line;
            try (BufferedReader reader = new BufferedReader(new FileReader("temp.txt"))) {
                line = reader.readLine();
            }
            String[] parts = line.split(",");
            chapterString = parts[0];
            sectionString = parts[1];
            questionString = parts[2];
            if (sectionString.contains("No Sections")) {
                sectionString = " ";
            }
        } catch (IOException e) {
            //System.err.format("Exception occurred trying to read '%s'.", filename);
        }

        try {
            BufferedWriter writer;
            try (BufferedReader reader = new BufferedReader(new FileReader("mcquestions/" + chapterString + ".txt"))) {
                writer = new BufferedWriter(new FileWriter("questions.txt"));
                String line;
                boolean go = true;
                while ((line = reader.readLine()) != null && go) {
                    if (line.contains(sectionString)) {
                        while ((line = reader.readLine()) != null && go) {
                            if (line.contains(questionString)) {
                                writer.write(line);
                                writer.newLine();
                                while ((line = reader.readLine()) != null && go) {
                                    writer.write(line);
                                    writer.newLine();
                                    if (line.contains("KEY")) {
                                        go = false;
                                    }
                                }
                            }
                        }
                    }
                }
                writer.newLine();
                writer.write("ENDQUESTION");
            }
            writer.close();
        } catch (IOException e) {
        }

    }

    public void txtFileToArray() {
        try {
            questionArray.add(new Question());
            int index = questionArray.size() - 1;
            try (BufferedReader reader = new BufferedReader(new FileReader("questions.txt"))) {
                String line;
                boolean go = true;
                while ((line = reader.readLine()) != null && go) {
                    if (line.length() >= 2) {
                        if (('a' <= line.charAt(0)) && (line.charAt(0) <= 'z')) {
                            line.substring(2);
                            questionArray.get(index).addOption(line);
                        } else if (line.contains("KEY:")) {
                            line = line.substring(4);
                            for (int i = 0; i < line.length(); i++) {
                                questionArray.get(index).addKey(line.charAt(i) + "");
                                if (line.charAt(i) == ' ') {
                                    questionArray.get(index).addHint(line.substring(i + 1));
                                    i = line.length() + 1;
                                }
                            }
                        } else if (line.contains("ENDQUESTION")) {
                            //Do Nothing
                        } else {
                            questionArray.get(index).addLineToQuestion(line);
                        }
                    }
                }
            }
        } catch (IOException e) {
            //System.err.format("Exception occurred trying to read '%s'.", filename);

        }
    }

    public void ArrayToScene() {
        for (int i = 0; i < questionArray.size(); i++) {
            for (int j = 0; j < questionArray.get(i).getQuestionStringArray().size(); j++) {
                questionLabel.setText(questionLabel.getText() + "\n" + questionArray.get(i).getQuestionStringArray().get(j));
            }
        }
        questionTF.getChildren().clear();
        questionTF.getChildren().add(new Text(questionLabel.getText()));
        for (int i = 0; i < questionArray.size(); i++) {
            for (int j = 0; j < questionArray.get(i).getOptions().size(); j++) {
                if (questionArray.get(i).getKeyArray().size() == 1) {
                    radioButtonArray.add(new RadioButton(questionArray.get(i).getOptions().get(j)));
                    vBoxQuest.getChildren().add(radioButtonArray.get(j));
                    radioButtonArray.get(j).setToggleGroup(group);
                } else {
                    checkBoxArray.add(new CheckBox(questionArray.get(i).getOptions().get(j)));
                    vBoxQuest.getChildren().add(checkBoxArray.get(j));
                    checkBoxArray.get(j).isSelected();
                }
            }
        }

    }

    @FXML
    void go2() throws Exception {
        Parent pane = FXMLLoader.load(getClass().getResource("../views/selection.fxml"));
        back.getScene().setRoot(pane);
    }
    int bestcorrect = 0;
    int firstCorrect;

    @FXML
    void submit() {

        if (true) {
            for (int i = 0; i < questionArray.size(); i++) {
                for (int j = 0; j < questionArray.get(i).getHintArray().size(); j++) {
                    show.setVisible(true);
                    show.setDisable(false);

                }
            }

        }

        for (int i = 0; i < questionArray.size(); i++) {
            System.out.println(questionArray.get(i).toString());
            if (questionArray.get(i).getKeyArray().size() == 1) {
                String selected = group.getSelectedToggle().toString();
                selected = selected.charAt(selected.indexOf(']') + 2) + "";
                for (int j = 0; j < questionArray.get(i).getKeyArray().size(); j++) {

                    if (questionArray.get(i).getKeyArray().get(j).contains(selected)) {
                        if (count == 0) {

                            bestcorrect++;
                            firstCorrect++;
                        } else {
                            correct++;
                            feedBack.setText("Correct");
                        }

                    } else {
                        count++;
                        feedBack.setText("Incorrect");

                        System.out.println(count);
                    }
                }
            } else {
                String selected = "";
                String finSelect = "";
                String keyString = "";
                for (int j = 0; j < questionArray.get(i).getKeyArray().size(); j++) {
                    keyString = keyString + questionArray.get(i).getKeyArray().get(j);
                }

                for (int j = 0; j < checkBoxArray.size(); j++) {
                    if (checkBoxArray.get(j).isSelected()) {
                        selected = checkBoxArray.get(j).toString();
                        finSelect = finSelect + selected.charAt(selected.indexOf(']') + 2);
                    }
                }
                keyString = keyString.trim();
                if (keyString.equals(finSelect.trim())) {
                    feedBack.setText("Correct");
                } else {
                    feedBack.setText("Wrong");
                    count++;
                }
            }
        }

    }
    @FXML
    Tooltip hints;

    @FXML
    void showHint() {
        for (int i = 0; i < questionArray.size(); i++) {
            for (int j = 0; j < questionArray.get(i).getHintArray().size(); j++) {

                hints.setText(
                        questionArray.get(i).getHintArray().get(j)
                );
                if(hints.isWrapText()){
                    hints.setWrapText(true);
                }
               show.setTooltip(hints);

            }
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
        secCB.setItems(findSections(chapterString));
        sectionString = "";
        questionString = "";
    }

    @FXML
    void dropSection() {
        sectionString = (String) secCB.getSelectionModel().getSelectedItem();
        if (sectionString == "No Sections") {
            questCB.setItems(findQuestions("Chapter"));
        } else {
            questCB.setItems(findQuestions(sectionString));
        }
        questionString = "";
    }

    @FXML
    void dropQuestion() {
        questionString = (String) questCB.getValue();
    }

    @FXML
    void go() throws Exception {
        count = 0;
        if (questionString != "") {
            for (int i = 0; i < questionArray2.size(); i++) {
                System.out.println(questionArray2.get(i));
            }
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("temp.txt"))) {
                bw.write(chapterString + "," + sectionString + "," + questionString);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent pane = FXMLLoader.load(getClass().getResource("../views/question.fxml"));
            chapCB.getScene().setRoot(pane);
        }
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
            questionArray2.clear();
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
                                questionArray2.add(line);
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
