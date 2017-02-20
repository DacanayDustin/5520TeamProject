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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class QuestionPresenter {

    public String chapterString, sectionString, questionString;
    ArrayList<Question> questionArray = new ArrayList<>();
    ArrayList<RadioButton> radioButtonArray = new ArrayList<>();
    ArrayList<CheckBox> checkBoxArray = new ArrayList<>();
    ToggleGroup group = new ToggleGroup();
    int count = 0;

    @FXML
    private View question;

    @FXML
    private Button back, show;

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

    @FXML
    void submit() {

        if (true) {
            for (int i = 0; i < questionArray.size(); i++) {
                for (int j = 0; j < questionArray.get(i).getHintArray().size(); j++) {
                    show.setVisible(true);
                    show.setDisable(false);
                    System.out.println(count);
                }
            }

        }

        for (int i = 0; i < questionArray.size(); i++) {
            if (questionArray.get(i).getKeyArray().size() == 1) {
                String selected = group.getSelectedToggle().toString();
                selected = selected.charAt(selected.indexOf(']') + 2) + "";
                for (int j = 0; j < questionArray.get(i).getKeyArray().size(); j++) {
                    if (questionArray.get(i).getKeyArray().get(j).contains(selected)) {
                        feedBack.setText("Correct");
                    } else {
                        feedBack.setText("Wrong");
                        count++;
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
    void showHint() {
        for (int i = 0; i < questionArray.size(); i++) {
            for (int j = 0; j < questionArray.get(i).getHintArray().size(); j++) {
                hintLabel.setText(questionArray.get(i).getHintArray().get(j));
            }
        }
    }
}
