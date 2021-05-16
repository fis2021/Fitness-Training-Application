package org.fta;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.assertions.api.Assertions.assertThat;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.fta.Controllers.ClientHomeController;
import org.fta.Exceptions.UsernameAlreadyExistsException;
import org.fta.Models.FitnessProgramModel;
import org.fta.Services.FileSystemService;
import org.fta.Services.ProgramApplyService;
import org.fta.Services.ChooseService;
import org.fta.Services.FitnessProgramService;
import org.fta.Services.ClientService;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)

class ChooseandApplyTest {

    public static final String USERNAMECLIENT = "client";
    public static final String PASSWORD = "password";

    @AfterEach
    void tearDown() {
        FitnessProgramService.getDatabase().close();
        ClientService.getDatabase().close();
        ChooseService.getDatabase().close();
        ProgramApplyService.getDatabase().close();
    }


    @BeforeEach
    void setUp() throws Exception{
        FileSystemService.APPLICATION_FOLDER = ".test-fta";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        ClientService.initDatabase();
        FitnessProgramService.initDatabase();
        ChooseService.initDatabase();
        ProgramApplyService.initDatabase();
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
    void testGoToLogoutButton(FxRobot robot) throws Exception {
        FitnessProgramService.addProgram("flotari","4","16","aa20","Florin");
        robot.clickOn("#username");
        robot.write(USERNAMECLIENT);
        robot.clickOn("#password");
        robot.write(PASSWORD);
        robot.clickOn("#role");
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.DOWN); //selects Client role
        robot.type(KeyCode.ENTER);
        robot.clickOn("#loginButton");
        robot.clickOn("#golist");
        robot.clickOn("#logoutfromClientButton");
    }

    @Test
    void testChoose(FxRobot robot) {
        FitnessProgramService.addProgram("pilates","4","16","ss20","Carmen");
        FitnessProgramService.addProgram("intinderi","2","6","2f0","Razvan");
        FitnessProgramService.addProgram("fandari","5","5","4f0","Octavian");
        FitnessProgramService.addProgram("sarituri jacks","6","10","6f0","Eddy");

        robot.clickOn("#username");
        robot.write(USERNAMECLIENT);
        robot.clickOn("#password");
        robot.write(PASSWORD);
        robot.clickOn("#role");
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.DOWN); //selects Client role
        robot.type(KeyCode.ENTER);
        robot.clickOn("#loginButton");
        robot.clickOn("#golist");
        robot.clickOn("#selectPlan");
        robot.clickOn("#chooseButton");
    }

    @Test
    void testApply(FxRobot robot) {
        FitnessProgramService.addProgram("pilates","4","16","ss20","Carmen");
        FitnessProgramService.addProgram("intinderi","2","6","2f0","Razvan");
        FitnessProgramService.addProgram("fandari","5","5","4f0","Octavian");
        FitnessProgramService.addProgram("sarituri jacks","6","10","6f0","Eddy");;

        robot.clickOn("#username");
        robot.write(USERNAMECLIENT);
        robot.clickOn("#password");
        robot.write(PASSWORD);
        robot.clickOn("#role");
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.DOWN); //selects Client role
        robot.type(KeyCode.ENTER);
        robot.clickOn("#loginButton");
        robot.clickOn("#golist");

        robot.clickOn("#selectPlan");
        robot.clickOn("#chooseButton");
        robot.clickOn("#enteredname");
        robot.write("Paul Andronik");
        robot.clickOn("#enteredlevel");
        robot.write("experimentat");
        robot.clickOn("#applyButton");
        assertThat(robot.lookup("#appliedmessage").queryText()).hasText("Applied succesfully!");
    }
}