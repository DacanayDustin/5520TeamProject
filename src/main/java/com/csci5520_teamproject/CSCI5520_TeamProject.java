package com.csci5520_teamproject;

import com.csci5520_teamproject.views.SelectionView;
import com.csci5520_teamproject.views.QuestionView;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.layout.layer.SidePopupView;
import com.gluonhq.charm.glisten.visual.Swatch;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class CSCI5520_TeamProject extends MobileApplication {

    public static final String SELECTION_VIEW = HOME_VIEW;
    public static final String QUESTION_VIEW = "Question View";
    public static final String MENU_LAYER = "Side Menu";
    
    @Override
    public void init() {
        addViewFactory(SELECTION_VIEW, () -> new SelectionView(SELECTION_VIEW).getView());
        addViewFactory(QUESTION_VIEW, () -> new QuestionView(QUESTION_VIEW).getView());
        
        addLayerFactory(MENU_LAYER, () -> new SidePopupView(new DrawerManager().getDrawer()));
    }

    @Override
    public void postInit(Scene scene) {
        Swatch.BLUE.assignTo(scene);

        
        ((Stage) scene.getWindow()).getIcons().add(new Image(CSCI5520_TeamProject.class.getResourceAsStream("/icon.png")));
    }
}
