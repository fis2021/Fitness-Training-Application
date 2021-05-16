package org.fta;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.fta.Services.*;
import org.fta.Services.ChooseService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        ClientService.initDatabase();
        FitnessProgramService.initDatabase();
        ProgramApplyService.initDatabase();
        ChooseService.initDatabase();
        PastApplicationsService.initDatabase();
        Parent root = FXMLLoader.load(App.class.getResource("Register.fxml"));
        stage.setTitle("FTA");
        stage.setScene(new Scene(root, 400, 300));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }


}