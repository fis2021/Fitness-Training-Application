package org.fta;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.assertions.api.Assertions.assertThat;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.fta.Exceptions.InvalidCredentialsException;
import org.fta.Exceptions.UsernameAlreadyExistsException;
import org.fta.Services.FileSystemService;
import org.fta.Services.ClientService;
import org.fta.Services.FitnessProgramService;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)

class LoginTest {
    public static final String USERNAMETRAINER = "trainer";
    public static final String USERNAMECLIENT = "client";
    public static final String PASSWORD = "password";

    @AfterEach
    void tearDown() {
        ClientService.getDatabase().close();
        FitnessProgramService.getDatabase().close();
    }

    @BeforeEach
    void setup() throws Exception{
        FileSystemService.APPLICATION_FOLDER = ".test-fta";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        ClientService.initDatabase();
        FitnessProgramService.initDatabase();
    }

    @Start
    void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(App.class.getResource("Login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.show();
    }

    @Test
    void testLogin(FxRobot robot) throws UsernameAlreadyExistsException  {
        ClientService.addUser(USERNAMETRAINER, PASSWORD,"Trainer");
        FitnessProgramService.addProgram("squat","1","10","10","Paul");
        robot.clickOn("#username");
        robot.write(USERNAMETRAINER);
        robot.clickOn("#password");
        robot.write(PASSWORD);
        robot.clickOn("#role");
        robot.type(KeyCode.DOWN);//selects Trainer role
        robot.type(KeyCode.ENTER);
        robot.clickOn("#loginButton");
    }

    @Test
    void testClientLogin(FxRobot robot) throws UsernameAlreadyExistsException {
        ClientService.addUser(USERNAMECLIENT, PASSWORD,"Client");
        FitnessProgramService.addProgram("abdomen","2","10","a","claudiu");
        robot.clickOn("#username");
        robot.write(USERNAMECLIENT);
        robot.clickOn("#password");
        robot.write(PASSWORD);
        robot.clickOn("#role");
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.DOWN);//selects Client role
        robot.type(KeyCode.ENTER);
        robot.clickOn("#loginButton");
    }

    @Test
    void testCustomerCanNotEnterInvalidCredentials(FxRobot robot) throws UsernameAlreadyExistsException,InvalidCredentialsException{
        ClientService.addUser(USERNAMECLIENT, PASSWORD,"Client");
        robot.clickOn("#username");
        robot.write("clientWRONG");
        robot.clickOn("#password");
        robot.write(PASSWORD);
        robot.clickOn("#role");
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.DOWN);//selects Client role
        robot.type(KeyCode.ENTER);

        robot.clickOn("#loginButton");
        assertThat(robot.lookup("#loginMessage").queryText()).hasText(String.format("Invalid username/password. Please try again!"));
    }

    @Test
    void testRedirectToRegister(FxRobot robot) {
        robot.clickOn("#redirectToRegisterButton");
    }
}