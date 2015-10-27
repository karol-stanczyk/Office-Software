package com.karol;

import com.airhacks.afterburner.injection.Injector;
import com.karol.presentation.cache.ViewsCache;
import com.karol.presentation.forms.start.StartView;
import com.karol.presentation.layout.LayoutView;
import com.karol.presentation.layout.control.LayoutService;
import com.karol.repository.access.EntityManager;
import com.karol.utils.Bundles;
import impl.org.controlsfx.i18n.Localization;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.Locale;

public class App extends Application {

    public static final String DATABASE_DIRECTORY = "C:\\Office-Software";
    private static final Logger log = Logger.getLogger(App.class);

    @Override
    public void start(Stage stage) throws Exception {
        Stage loadingPage = createLoadingPage();
        loadingPage.show();
            new Thread(() -> {
                try {
                    initializeApplicationData(stage);
                    Platform.runLater(() -> {
                        setApplicationIcon(stage);
                        configureApplicationStage(stage);
                        stage.show();
                    });
                } catch (Exception e) {
                    log.error(e);
                    stage.close();
                } finally {
                    Platform.runLater(loadingPage::close);
                }
            }).start();
    }

    private void initializeApplicationData(Stage stage) throws Exception {
        createDatabaseDirectory();
        Localization.setLocale(new Locale("pl", "PL"));
        LayoutService layoutService = Injector.instantiateModelOrService(LayoutService.class);
        EntityManager entityManager = Injector.instantiateModelOrService(EntityManager.class);
        ViewsCache.init();
        layoutService.setApplicationStage(stage);
        setCloseEvent(stage, layoutService, entityManager);
    }

    private Stage createLoadingPage() {
        Stage stage = new Stage();
        Scene scene = new Scene(new StartView().getView());
        loadCssStyles(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        return stage;
    }

    private void configureApplicationStage(Stage stage) {
        stage.setTitle(Bundles.get("application.title"));
        stage.setScene(createMainScene());
        stage.setResizable(false);
    }

    public Scene createMainScene() {
        Parent startView  = new LayoutView().getView();
        Scene scene = new Scene(startView);
        loadCssStyles(scene);
        return scene;
    }

    private void loadCssStyles(Scene scene) {
        final String uri = getClass().getResource("app.css").toExternalForm();
        scene.getStylesheets().add(uri);
    }

    private void setCloseEvent(Stage stage, LayoutService layoutService, EntityManager entityManager) {
        stage.setOnCloseRequest(event -> {
            layoutService.closeApplication();
            entityManager.close();
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
