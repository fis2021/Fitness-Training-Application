package org.fta;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.commons.io.FileUtils;
import org.fta.Services.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.fta.Exceptions.UsernameAlreadyExistsException;
import org.fta.Controllers.ClientHomeController;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)

class ViewPastApplicationsAcClientTest {

    public static final String USERNAMECLIENT = "client";
    public static final String PASSWORD = "password";

    @AfterEach
    void tearDown() {
        PastApplicationsService.getDatabase().close();
        ClientService.getDatabase().close();
    }

    @BeforeEach
    void setUp() throws Exception{
        FileSystemService.APPLICATION_FOLDER = ".test-fta";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        ClientService.initDatabase();
        PastApplicationsService.initDatabase();
        ClientService.addUser(USERNAMECLIENT, PASSWORD,"Client");
    }

    @Start
    void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(App.class.getResource("Login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 900, 900));
        primaryStage.show();
    }

    @Test
    void testViewApplications(FxRobot robot) throws UsernameAlreadyExistsException{
        PastApplicationsService.addPastApplicationToDatabase("flotari","andrei" ,"zzzz" );
        PastApplicationsService.addPastApplicationToDatabase("abdomen","maria" ,"zzzz" );
        PastApplicationsService.addPastApplicationToDatabase("squats","andreea" ,"zzzz" );
        robot.clickOn("#username");
        robot.write(USERNAMECLIENT);
        robot.clickOn("#password");
        robot.write(PASSWORD);
        robot.clickOn("#role");
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.DOWN); //selects Client role
        robot.type(KeyCode.ENTER);
        robot.clickOn("#loginButton");
        robot.clickOn("#gopast");

    }

    @Test
    void testGoToLogoutButton(FxRobot robot) throws Exception {
        PastApplicationsService.addPastApplicationToDatabase("flotari","ion","16");
        robot.clickOn("#username");
        robot.write(USERNAMECLIENT);
        robot.clickOn("#password");
        robot.write(PASSWORD);
        robot.clickOn("#role");
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.DOWN); //selects Client role
        robot.type(KeyCode.ENTER);
        robot.clickOn("#loginButton");
        robot.clickOn("#gopast");
        robot.clickOn("#logoutButton");
    }

}