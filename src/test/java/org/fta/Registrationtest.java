package org.fta;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.fta.Services.FileSystemService;
import org.fta.Services.ClientService;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.AbstractLabeledAssert;
import org.testfx.assertions.api.ButtonAssert;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.testfx.assertions.api.Assertions.assertThat;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)

class Registrationtest {

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    @AfterEach
    void tearDown(){
        ClientService.getDatabase().close();
    }

    @BeforeEach
    void setup() throws Exception{
        FileSystemService.APPLICATION_FOLDER = ".test-fta";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        ClientService.initDatabase();
    }

    @Start
    void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(App.class.getResource("Register.fxml"));
        primaryStage.setTitle("FTA");
        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.show();
    }

    @Test
    void testRegistration(FxRobot robot){
        robot.clickOn("#username");
        robot.write(USERNAME);
        robot.clickOn("#password");
        robot.write(PASSWORD);
        robot.clickOn("#role");
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.ENTER);
        robot.clickOn("#registerButton");

        assertThat(robot.lookup("#registrationMessage").queryText()).hasText("Account created successfully!");
        assertThat(ClientService.getAllUsers()).size().isEqualTo(1);

        robot.clickOn("#registerButton");
        assertThat(robot.lookup("#registrationMessage").queryText()).hasText(
                String.format("An account with this username %s already exists", USERNAME));

        robot.clickOn("#username");
        robot.write("1");
        robot.clickOn("#registerButton");

        assertThat(robot.lookup("#registrationMessage").queryText()).hasText("Account created successfully!");
        assertThat(ClientService.getAllUsers()).size().isEqualTo(2);
    }

    @Test
    void testGoToLoginPage(FxRobot robot) {
        robot.clickOn("#gotoLoginButton");
    }
}