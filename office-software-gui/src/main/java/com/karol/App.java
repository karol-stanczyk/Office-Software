package com.karol;

import com.airhacks.afterburner.injection.Injector;
import com.karol.presentation.layout.LayoutService;
import com.karol.presentation.layout.LayoutView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

public class App extends Application {

    public static final String APPLICATION_NAME = "Office-Software";

    @Override
    public void start(Stage stage) throws Exception {
        /*
         * Properties of any type can be easily injected.
         */
        LocalDate date = LocalDate.of(4242, Month.JULY, 21);
        Map<Object, Object> customProperties = new HashMap<>();
        customProperties.put("date", date);
        /*
         * any function which accepts an Object as key and returns
         * and return an Object as result can be used as source.
         */
        Injector.setConfigurationSource(customProperties::get);
        LayoutService layoutService = Injector.instantiateModelOrService(LayoutService.class);
        layoutService.setApplicationStage(stage);

        System.setProperty("happyEnding", " Enjoy the flight!");
        stage.setTitle(APPLICATION_NAME);
        stage.setScene(createMainScene());
        stage.setResizable(false);
        stage.show();
    }

    public Scene createMainScene() {
        Scene scene = new Scene(new LayoutView().getView());
        final String uri = getClass().getResource("app.css").toExternalForm();
        scene.getStylesheets().add(uri);
        return scene;
    }


    @Override
    public void stop() throws Exception {
        Injector.forgetAll();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
