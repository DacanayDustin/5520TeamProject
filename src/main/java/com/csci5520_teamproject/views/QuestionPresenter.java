package com.csci5520_teamproject.views;

import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.layout.layer.FloatingActionButton;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.csci5520_teamproject.CSCI5520_TeamProject;
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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class QuestionPresenter {

    public String chapterString, sectionString, questionString;
    ArrayList<Question> questionArray = new ArrayList<Question>();

    @FXML
    private View question;

    @FXML
    private Button back;

    @FXML
    private Label questionLabel, hintLabel;

    @FXML
    private VBox vBoxQuest;
    

    ToggleGroup questionTog = new ToggleGroup();
    public Question t = new Question();

    public void initialize() {
        question.setShowTransitionFactory(BounceInRightTransition::new);

        question.getLayers().add(new FloatingActionButton(MaterialDesignIcon.INFO.text,
                e -> System.out.println("Info")).getLayer());

        question.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e
                        -> MobileApplication.getInstance().showLayer(CSCI5520_TeamProject.MENU_LAYER)));
                appBar.setTitleText("Question");
                appBar.getActionItems().add(MaterialDesignIcon.FAVORITE.button(e
                        -> System.out.println("Favorite")));
            }
        });
        questionsToTxtFile();
        txtFileToArray();
        ArrayToScene();
    }

    public void questionsToTxtFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("temp.txt"));
            String line = reader.readLine();
            reader.close();
            String[] parts = line.split(",");
            chapterString = parts[0];
            sectionString = parts[1];
            questionString = parts[2];
            if (sectionString.contains("No Sections")) {
                sectionString = " ";
            }
        } catch (Exception e) {
            //System.err.format("Exception occurred trying to read '%s'.", filename);
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader("mcquestions/" + chapterString + ".txt"));
            BufferedWriter writer = new BufferedWriter(new FileWriter("questions.txt"));
            String line;
            boolean go = true;
            while ((line = reader.readLine()) != null && go) {

                if (line.contains(sectionString)) {
                    System.out.println(line);
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
            };
            writer.newLine();
            writer.write("ENDQUESTION");
            reader.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void txtFileToArray() {
        try {
            questionArray.add(new Question());
            int index = questionArray.size() - 1;
            BufferedReader reader = new BufferedReader(new FileReader("questions.txt"));
            String line;
            boolean go = true;
            while ((line = reader.readLine()) != null && go) {
                if (line.length() >= 2) {
                    if (('a' <= line.charAt(0)) && (line.charAt(0) <= 'z')) {
                        line.substring(2);
                        questionArray.get(index).addOption(line);
                        System.out.println("Option");
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
            reader.close();
        } catch (Exception e) {
            //System.err.format("Exception occurred trying to read '%s'.", filename);
            e.printStackTrace();
        }
    }

    public void ArrayToScene() {
        for (int i = 0; i < questionArray.size(); i++) {
            for (int j = 0; j < questionArray.get(i).getQuestionStringArray().size(); j++) {
                questionLabel.setText(questionLabel.getText() + "\n" + questionArray.get(i).getQuestionStringArray().get(j));
            }
        }

        for (int i = 0; i < questionArray.size(); i++) {
            for (int j = 0; j < questionArray.get(i).getOptions().size(); j++) {
                vBoxQuest.getChildren().add(new RadioButton(questionArray.get(i).getOptions().get(j)));
            }
        }

        for (int i = 0; i < questionArray.size(); i++) {
            for (int j = 0; j < questionArray.get(i).getHintArray().size(); j++) {
                System.out.println(questionArray.get(i).getHintArray().get(j));
                hintLabel.setText(questionArray.get(i).getHintArray().get(j));
            }
        }

    }

    @FXML
    void go2() throws Exception {
        Parent pane = FXMLLoader.load(getClass().getResource("../views/selection.fxml"));
        back.getScene().setRoot(pane);
    }
}
