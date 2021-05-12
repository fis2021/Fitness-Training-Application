package org.fta;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.fta.Services.ClientService;
import org.fta.Services.FileSystemService;
import org.fta.Services.FitnessProgramService;
import org.fta.Services.ProgramApplyService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        initDirectory();
        ClientService.initDatabase();
        FitnessProgramService.initDatabase();
        ProgramApplyService.initDatabase();
        Parent root = FXMLLoader.load(App.class.getResource("Register.fxml"));
        stage.setTitle("FTA");
        stage.setScene(new Scene(root, 400, 300));
        stage.show();
    }

    private void initDirectory() {
        Path applicationHomePath = FileSystemService.APPLICATION_HOME_PATH;
        if (!Files.exists(applicationHomePath))
            applicationHomePath.toFile().mkdirs();
    }

    public static void main(String[] args) {
        launch();
    }

}