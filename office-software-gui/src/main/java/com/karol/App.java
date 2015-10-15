package com.karol;

import com.airhacks.afterburner.injection.Injector;
import com.karol.presentation.layout.LayoutView;
import com.karol.presentation.layout.control.LayoutService;
import com.karol.presentation.layout.control.ViewsCache;
import com.karol.utils.Bundles;
import impl.org.controlsfx.i18n.Localization;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.management.OperationsException;
import java.io.File;
import java.util.Locale;

public class App extends Application {

    public static final String DATABASE_DIRECTORY = "C:\\Office-Software";

    @Override
    public void start(Stage stage) throws Exception {
        createDatabaseDirectory();
        Localization.setLocale(new Locale("pl", "PL"));
        LayoutService layoutService = Injector.instantiateModelOrService(LayoutService.class);

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

    private void createDatabaseDirectory() throws Exception {
        File file = new File(DATABASE_DIRECTORY);
        if (!file.exists()) {
            boolean directoryCreated = file.mkdir();
            if(!directoryCreated) throw new Exception();
        }
    }

    @Override
    public void stop() throws Exception {
        Injector.forgetAll();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
