package org.fta;


import static org.testfx.assertions.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.fta.Exceptions.UsernameAlreadyExistsException;
import org.fta.Services.ProgramApplyService;
import org.fta.Services.ClientService;
import org.fta.Services.FileSystemService;
import org.fta.Services.FitnessProgramService;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;



import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)

class ModifyProgramTest {

    public static final String EXERCISENAME = "exercise";
    public static final String REPS = "15";
    public static final String SETS = "3";
    public static final String ZOOM = "link";
    public static final String TRAINERNAME = "trainer";
    public static final String USERNAMETRAINER = "username";
    public static final String PASSWORD = "password";

    @AfterEach
    void tearDown() {
        FitnessProgramService.getDatabase().close();
        ClientService.getDatabase().close();
        ProgramApplyService.getDatabase().close();
    }

    @BeforeEach
    void setUp() throws Exception{
        FileSystemService.APPLICATION_FOLDER=".test-shirt-application";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        FitnessProgramService.initDatabase();
        ClientService.initDatabase();
        ProgramApplyService.initDatabase();
        ClientService.addUser(USERNAMETRAINER, PASSWORD,"Shop Owner");
        FitnessProgramService.addProgram("Lunges","20","4","link","name");
    }

    @Start
    void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(App.class.getResource("Login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 850, 500));
        primaryStage.show();
    }

    @Test
    void testGoToLogoutButton(FxRobot robot) throws UsernameAlreadyExistsException {
        robot.clickOn("#username");
        robot.write(USERNAMETRAINER);
        robot.clickOn("#password");
        robot.write(PASSWORD);
        robot.clickOn("#role");
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.ENTER);
        robot.clickOn("#loginButton");
        robot.clickOn("#logoutButton");
    }

    @Test
    void testAddProgram(FxRobot robot) {
        robot.clickOn("#username");
        robot.write(USERNAMETRAINER);
        robot.clickOn("#password");
        robot.write(PASSWORD);
        robot.clickOn("#role");
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.ENTER);
        robot.clickOn("#loginButton");
        robot.clickOn("#myProfileButton");

        robot.clickOn("#exerciseNameField");
        robot.write("Squats");
        robot.clickOn("#repsField");
        robot.write("20");
        robot.clickOn("#setsField");
        robot.write("2");
        robot.clickOn("#zoomField");
        robot.write("link");
        robot.clickOn("#trainerNameField");
        robot.write("My Name");
        robot.clickOn("#addButton");
        robot.clickOn("#refreshButton");
        assertThat(robot.lookup("#successMessage").queryText()).hasText("Added program");
    }

    @Test
    void testBlankFieldsCannotBeEnteredWhenAddingAProgram(FxRobot robot) {
        robot.clickOn("#username");
        robot.write(USERNAMETRAINER);
        robot.clickOn("#password");
        robot.write(PASSWORD);
        robot.clickOn("#role");
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.ENTER);
        robot.clickOn("#loginButton");
        robot.clickOn("#myProfileButton");

        robot.clickOn("#exerciseNameField");
        robot.write("");
        robot.clickOn("#repsField");
        robot.write("20");
        robot.clickOn("#setsField");
        robot.write("2");
        robot.clickOn("#zoomField");
        robot.write("link");
        robot.clickOn("#trainerNameField");
        robot.write("My Name");
        robot.clickOn("#addButton");

        assertThat(robot.lookup("#successMessage").queryText()).hasText(String.format("Please complete all fields"));
    }

    @Test
    void testEditProgram(FxRobot robot) {
        robot.clickOn("#username");
        robot.write(USERNAMETRAINER);
        robot.clickOn("#password");
        robot.write(PASSWORD);
        robot.clickOn("#role");
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.ENTER);
        robot.clickOn("#loginButton");
        robot.clickOn("#myProfileButton");

        robot.clickOn("#exerciseNameField");
        robot.write("Lunges");
        robot.clickOn("#repsField");
        robot.write("10");
        robot.clickOn("#setsField");
        robot.write("2");
        robot.clickOn("#zoomField");
        robot.write("link");
        robot.clickOn("#trainerNameField");
        robot.write("name");
        robot.clickOn("#editButton");
        robot.clickOn("#refreshButton");

        assertThat(robot.lookup("#successMessage").queryText()).hasText("Edited program");
    }

    @Test
    void testWrongFieldsWhenEditing(FxRobot robot) {
        robot.clickOn("#username");
        robot.write(USERNAMETRAINER);
        robot.clickOn("#password");
        robot.write(PASSWORD);
        robot.clickOn("#role");
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.ENTER);
        robot.clickOn("#loginButton");
        robot.clickOn("#myProfileButton");

        robot.clickOn("#exerciseNameField");
        robot.write("Lungesss");
        robot.clickOn("#repsField");
        robot.write("10");
        robot.clickOn("#setsField");
        robot.write("2");
        robot.clickOn("#zoomField");
        robot.write("link");
        robot.clickOn("#trainerNameField");
        robot.write("name");

        robot.clickOn("#editButton");
        robot.clickOn("#refreshButton");

        assertThat(robot.lookup("#successMessage").queryText()).hasText(String.format("Please complete all fields correctly"));
    }

    @Test
    void testBlankFieldsWhenEditing(FxRobot robot) {
        robot.clickOn("#username");
        robot.write(USERNAMETRAINER);
        robot.clickOn("#password");
        robot.write(PASSWORD);
        robot.clickOn("#role");
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.ENTER);
        robot.clickOn("#loginButton");
        robot.clickOn("#myProfileButton");

        robot.clickOn("#exerciseNameField");
        robot.write("");
        robot.clickOn("#repsField");
        robot.write("10");
        robot.clickOn("#setsField");
        robot.write("2");
        robot.clickOn("#zoomField");
        robot.write("link");
        robot.clickOn("#trainerNameField");
        robot.write("name");

        robot.clickOn("#editButton");
        robot.clickOn("#refreshButton");

        assertThat(robot.lookup("#successMessage").queryText()).hasText(String.format("Please complete all fields"));
    }

    @Test
    void testDeleteProgram(FxRobot robot) {
        robot.clickOn("#username");
        robot.write(USERNAMETRAINER);
        robot.clickOn("#password");
        robot.write(PASSWORD);
        robot.clickOn("#role");
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.ENTER);
        robot.clickOn("#loginButton");
        robot.clickOn("#myProfileButton");

        FitnessProgramService.addProgram("Push-up","10","3","link","Mihai");
        robot.clickOn("#exerciseNameField");
        robot.write("Lunges");
        robot.clickOn("#repsField");
        robot.write("10");
        robot.clickOn("#setsField");
        robot.write("2");
        robot.clickOn("#zoomField");
        robot.write("link");
        robot.clickOn("#trainerNameField");
        robot.write("name");

        robot.clickOn("#deleteButton");
        robot.clickOn("#refreshButton");
        assertThat(robot.lookup("#successMessage").queryText()).hasText("Deleted program");
    }

    @Test
    void testWrongFieldsWhenRemovingShirt(FxRobot robot) {
        robot.clickOn("#username");
        robot.write(USERNAMETRAINER);
        robot.clickOn("#password");
        robot.write(PASSWORD);
        robot.clickOn("#role");
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.ENTER);
        robot.clickOn("#loginButton");
        robot.clickOn("#myProfileButton");

        robot.clickOn("#exerciseNameField");
        robot.write("Lung");
        robot.clickOn("#repsField");
        robot.write("10");
        robot.clickOn("#setsField");
        robot.write("2");
        robot.clickOn("#zoomField");
        robot.write("link");
        robot.clickOn("#trainerNameField");
        robot.write("name");

        robot.clickOn("#deleteButton");
        robot.clickOn("#refreshButton");
        assertThat(robot.lookup("#successMessage").queryText()).hasText(String.format("Please complete all fields correctly"));
    }

    @Test
    void testBlankFieldsWhenDeleting(FxRobot robot) {
        robot.clickOn("#username");
        robot.write(USERNAMETRAINER);
        robot.clickOn("#password");
        robot.write(PASSWORD);
        robot.clickOn("#role");
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.ENTER);
        robot.clickOn("#loginButton");
        robot.clickOn("#myProfileButton");

        robot.clickOn("#exerciseNameField");
        robot.write("");
        robot.clickOn("#repsField");
        robot.write("10");
        robot.clickOn("#setsField");
        robot.write("2");
        robot.clickOn("#zoomField");
        robot.write("link");
        robot.clickOn("#trainerNameField");
        robot.write("name");

        robot.clickOn("#deleteButton");
        robot.clickOn("#refreshButton");
        assertThat(robot.lookup("#successMessage").queryText()).hasText(String.format("Please complete all fields"));
    }

    @Test
    void testGoToZoomApplicants(FxRobot robot) throws UsernameAlreadyExistsException {
        robot.clickOn("#username");
        robot.write(USERNAMETRAINER);
        robot.clickOn("#password");
        robot.write(PASSWORD);
        robot.clickOn("#role");
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.ENTER);

        robot.clickOn("#loginButton");
        robot.clickOn("#zoomApplicantsButton");
    }


}