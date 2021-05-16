package org.fta;

import static org.testfx.assertions.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.fta.Exceptions.UsernameAlreadyExistsException;
import org.fta.Services.FitnessProgramService;
import org.fta.Services.ClientService;
import org.fta.Services.ProgramApplyService;
import org.fta.Services.FileSystemService;
import org.fta.Services.PastApplicationsService;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
class AcceptDenyApplicationsAsTrainerTest {

    public static final String USERNAMETRAINER = "trainer";
    public static final String PASSWORD = "password";
    public static final String CUSTOMERNAME = "name";
    public static final String EXERCISENAME= "exercise";
    public static final String ZOOMLINK= "link";

    @AfterEach
    void tearDown() {
        FitnessProgramService.getDatabase().close();
        ClientService.getDatabase().close();
        ProgramApplyService.getDatabase().close();
        PastApplicationsService.getDatabase().close();
    }

    @BeforeEach
    void setup() throws Exception{
        FileSystemService.APPLICATION_FOLDER = ".test-fta";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        ClientService.initDatabase();
        FitnessProgramService.initDatabase();
        ProgramApplyService.initDatabase();
        PastApplicationsService.initDatabase();
        ClientService.addUser(USERNAMETRAINER, PASSWORD, "Trainer");
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
        FitnessProgramService.addProgram("Lunges","15","2","link","Maria");
        ProgramApplyService.addApplicationToDatabase(CUSTOMERNAME,"Beginner",EXERCISENAME);
        robot.clickOn("#username");
        robot.write(USERNAMETRAINER);
        robot.clickOn("#password");
        robot.write(PASSWORD);
        robot.clickOn("#role");
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.ENTER);
        robot.clickOn("#loginButton");
        robot.clickOn("#zoomApplicantsButton");
        robot.clickOn("#logoutButton");
    }

    @Test
    void testGoToMyProfile(FxRobot robot) throws UsernameAlreadyExistsException{
        FitnessProgramService.addProgram("Lunges","15","2","link","Maria");
        ProgramApplyService.addApplicationToDatabase(CUSTOMERNAME,"Beginner",EXERCISENAME);
        robot.clickOn("#username");
        robot.write(USERNAMETRAINER);
        robot.clickOn("#password");
        robot.write(PASSWORD);
        robot.clickOn("#role");
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.ENTER);
        robot.clickOn("#loginButton");
        robot.clickOn("#zoomApplicantsButton");
        robot.clickOn("#myProfileButton");
    }

    @Test
    void TestAcceptApplication(FxRobot robot) throws UsernameAlreadyExistsException{
        FitnessProgramService.addProgram("Lunges","15","2","link","Maria");
        ProgramApplyService.addApplicationToDatabase(CUSTOMERNAME,"Beginner",EXERCISENAME);
        robot.clickOn("#username");
        robot.write(USERNAMETRAINER);
        robot.clickOn("#password");
        robot.write(PASSWORD);
        robot.clickOn("#role");
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.ENTER);
        robot.clickOn("#loginButton");
        robot.clickOn("#zoomApplicantsButton");

        robot.clickOn("#customerNameField");
        robot.write(CUSTOMERNAME);
        robot.clickOn("#exerciseNameField");
        robot.write(EXERCISENAME);
        robot.clickOn("#zoomLinkField");
        robot.write(ZOOMLINK);
        robot.clickOn("#acceptButton");
        robot.clickOn("#refreshButton");

        assertThat(robot.lookup("#applicationMessage").queryText()).hasText("Application accepted");
    }

    @Test
    void TestDenyApplication(FxRobot robot) throws UsernameAlreadyExistsException{
        FitnessProgramService.addProgram("Lunges","15","2","link","Maria");
        ProgramApplyService.addApplicationToDatabase(CUSTOMERNAME,"Beginner",EXERCISENAME);
        robot.clickOn("#username");
        robot.write(USERNAMETRAINER);
        robot.clickOn("#password");
        robot.write(PASSWORD);
        robot.clickOn("#role");
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.ENTER);
        robot.clickOn("#loginButton");
        robot.clickOn("#zoomApplicantsButton");

        robot.clickOn("#customerNameField");
        robot.write(CUSTOMERNAME);
        robot.clickOn("#exerciseNameField");
        robot.write(EXERCISENAME);
        robot.clickOn("#zoomLinkField");
        robot.write(ZOOMLINK);
        robot.clickOn("#denyButton");
        robot.clickOn("#refreshButton");

        assertThat(robot.lookup("#applicationMessage").queryText()).hasText("Application denied");
    }

}