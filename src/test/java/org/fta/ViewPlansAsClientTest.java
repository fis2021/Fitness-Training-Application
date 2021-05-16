package org.fta;

import static org.junit.jupiter.api.Assertions.*;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.fta.Services.FileSystemService;
import org.fta.Exceptions.UsernameAlreadyExistsException;
import org.fta.Services.ClientService;
import org.fta.Services.FitnessProgramService;
import org.fta.Services.ProgramApplyService;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)

class ViewPlansAsClientTest {

    public static final String USERNAMECLIENT = "client";
    public static final String PASSWORD = "password";

    @AfterEach
    void tearDown() {
        FitnessProgramService.getDatabase().close();
        ClientService.getDatabase().close();
    }

    @BeforeEach
    void setUp() throws Exception{
        FileSystemService.APPLICATION_FOLDER = ".test-fta";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        ClientService.initDatabase();
        FitnessProgramService.initDatabase();
        ClientService.addUser(USERNAMECLIENT, PASSWORD,"Customer");
    }

    @Start
    void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(App.class.getResource("Login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.show();
    }

    @Test
    void testViewShirts(FxRobot robot) throws UsernameAlreadyExistsException {
        FitnessProgramService.addProgram("squat","1","10","6z0","Paula");
        FitnessProgramService.addProgram("abdomene","2","6","2z0","Cezar");
        FitnessProgramService.addProgram("genuflexiuni","3","5","4z0","Bogdan");
        FitnessProgramService.addProgram("sarituri coarda","4","16","z10","Vladimir");
        robot.clickOn("#username");
        robot.write(USERNAMECLIENT);
        robot.clickOn("#password");
        robot.write(PASSWORD);
        robot.clickOn("#role");
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.DOWN); //selects Client role
        robot.type(KeyCode.ENTER);
        robot.clickOn("#loginButton");
    }
}