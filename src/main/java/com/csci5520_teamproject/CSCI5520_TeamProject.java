package com.csci5520_teamproject;

import com.csci5520_teamproject.views.LoginView;
import com.gluonhq.charm.glisten.application.MobileApplication;
import javafx.scene.Scene;

public class CSCI5520_TeamProject extends MobileApplication {

    public static final String PRIMARY_VIEW = HOME_VIEW;
    
    @Override
    public void init() {
        addViewFactory(PRIMARY_VIEW, () -> new LoginView(PRIMARY_VIEW).getView());
    }

    @Override
    public void postInit(Scene scene) {
    }
}
