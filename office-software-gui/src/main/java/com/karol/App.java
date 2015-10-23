package com.karol;

import com.airhacks.afterburner.injection.Injector;
import com.karol.presentation.layout.LayoutView;
import com.karol.presentation.layout.control.LayoutService;
import com.karol.presentation.cache.ViewsCache;
import com.karol.presentation.services.NavigationService;
import com.karol.utils.Bundles;
import impl.org.controlsfx.i18n.Localization;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.util.Locale;

public class App extends Application {

    public static final String DATABASE_DIRECTORY = "C:\\Office-Software";

    @Override
    public void start(Stage stage) throws Exception {
        createDatabaseDirectory();
        Localization.setLocale(new Locale("pl", "PL"));
        LayoutService layoutService = Injector.instantiateModelOrService(LayoutService.class);
        setApplicationIcon(stage);
        ViewsCache.init();

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
        Parent startView  = new LayoutView().getView();
        NavigationService.setCurrentState(startView);
        Scene scene = new Scene(startView);
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

    private void createDatabaseDirectory() throws Exception {
        File file = new File(DATABASE_DIRECTORY);
        if (!file.exists()) {
            boolean directoryCreated = file.mkdir();
            if(!directoryCreated) throw new Exception();
        }
    }

    private void setApplicationIcon(Stage stage) {
        stage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("app-icon.png")));
    }

    @Override
    public void stop() throws Exception {
        Injector.forgetAll();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
