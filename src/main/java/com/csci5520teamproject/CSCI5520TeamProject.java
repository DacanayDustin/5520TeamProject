package com.csci5520teamproject;

import com.csci5520teamproject.views.LoginView;
import com.gluonhq.charm.glisten.application.MobileApplication;
import javafx.scene.Scene;

public class CSCI5520TeamProject extends MobileApplication {

    public static final String PRIMARY_VIEW = HOME_VIEW;
    
    @Override
    public void init() {
        addViewFactory(PRIMARY_VIEW, () -> new LoginView(PRIMARY_VIEW).getView());
    }

    @Override
    public void postInit(Scene scene) {
    }
}