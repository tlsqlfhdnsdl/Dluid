package org.kok202.dluid.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.kok202.dluid.CanvasFacade;
import org.kok202.dluid.application.content.ContentRootController;
import org.kok202.dluid.application.menu.MenuController;
import org.kok202.dluid.application.singleton.AppPropertiesSingleton;
import org.kok202.dluid.application.singleton.AppWidgetSingleton;

public class Main extends Application {
    private MenuBar menuBar;
    private Stage primaryStage;
    private ContentRootController contentRootController;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        setPrimaryStage(primaryStage);
        BorderPane borderPane = new FXMLLoader(getClass().getResource("/frame/main.fxml")).load();
        borderPane.setTop(createTopFrame());
        borderPane.setCenter(createCenterFrame());
        setWidgetOnGlobalWidget();
        initWindowFrame(primaryStage, borderPane);
        showWindowFrame(primaryStage);
    }

    private void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
        primaryStage.getIcons().add(new Image("images/icon.png"));
    }

    private MenuBar createTopFrame() throws Exception{
        menuBar = new MenuController().createView();
        return menuBar;
    }

    private AnchorPane createCenterFrame() throws Exception{
        contentRootController = new ContentRootController();
        return contentRootController.createView();
    }

    private void setWidgetOnGlobalWidget(){
        AppWidgetSingleton.getInstance().setPrimaryStage(primaryStage);
        AppWidgetSingleton.getInstance().setMenuBar(menuBar);
        AppWidgetSingleton.getInstance().setContentRootController(contentRootController);
        CanvasFacade.initialize();
    }

    private void initWindowFrame(Stage primaryStage, Parent fxmlRoot){
        Scene scene = new Scene(fxmlRoot,
                AppPropertiesSingleton.getInstance().getInt("frame.size.width"),
                AppPropertiesSingleton.getInstance().getInt("frame.size.height"));
        primaryStage.setTitle(AppPropertiesSingleton.getInstance().get("frame.title"));
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        applyTheme(fxmlRoot);
    }

    private void applyTheme(Parent root){
    }

    private void showWindowFrame(Stage primaryStage){
        primaryStage.show();
    }
}