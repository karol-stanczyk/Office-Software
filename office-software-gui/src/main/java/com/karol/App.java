package com.karol;

import com.airhacks.afterburner.injection.Injector;
import com.karol.presentation.layout.LayoutView;
import com.karol.presentation.layout.control.LayoutService;
import com.karol.utils.Bundles;
import impl.org.controlsfx.i18n.Localization;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Localization.setLocale(new Locale("PL","PL"));
        LayoutService layoutService = Injector.instantiateModelOrService(LayoutService.class);
        layoutService.setApplicationStage(stage);
        setCloseEvent(stage, layoutService);
        configureApplicationStage(stage);
        stage.show();
    }

    private void configureApplicationStage(Stage stage) {
        stage.setTitle(Bundles.get("application.title"));
        stage.setScene(createMainScene());
        stage.setResizable(false);
    }

    public Scene createMainScene() {
        Scene scene = new Scene(new LayoutView().getView());
        final String uri = getClass().getResource("app.css").toExternalForm();
        scene.getStylesheets().add(uri);
        return scene;
    }

    private void setCloseEvent(Stage stage, LayoutService layoutService) {
        stage.setOnCloseRequest(event -> {
            layoutService.closeApplication();
            event.consume();
        });
    }


    @Override
    public void stop() throws Exception {
        Injector.forgetAll();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
