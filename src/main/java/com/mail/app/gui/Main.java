package com.mail.app.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = new FXMLLoader(getClass().getClassLoader().getResource("view/main.fxml")).load();
        primaryStage.setTitle("Mail Sender");
        primaryStage.setMinHeight(550);
        primaryStage.setMinWidth(550);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("styles/styles.css").toString());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
