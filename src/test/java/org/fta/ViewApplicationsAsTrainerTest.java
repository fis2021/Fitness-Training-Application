package org.fta;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.fta.Exceptions.UsernameAlreadyExistsException;
import org.fta.Services.FileSystemService;
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

class ViewApplicationsAsTrainerTest {

    public static final String USERNAMETRAINER = "trainer";
    public static final String PASSWORD = "password";
    public static final String CUSTOMERNAME = "name";
    public static final String TRAININGLEVEL= "Beginner";

    @AfterEach
    void tearDown() {
        FitnessProgramService.getDatabase().close();
        ClientService.getDatabase().close();
        ProgramApplyService.getDatabase().close();
    }

    @BeforeEach
    void setup() throws Exception{
        FileSystemService.APPLICATION_FOLDER = ".test-fta";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        ClientService.initDatabase();
        FitnessProgramService.initDatabase();
        ProgramApplyService.initDatabase();
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
    void testViewOrders(FxRobot robot) throws UsernameAlreadyExistsException{
        ProgramApplyService.addApplicationToDatabase(CUSTOMERNAME, TRAININGLEVEL, "Lunges" );
        ProgramApplyService.addApplicationToDatabase(CUSTOMERNAME+1, TRAININGLEVEL+1, "Crunches" );
        ProgramApplyService.addApplicationToDatabase(CUSTOMERNAME+2, TRAININGLEVEL+2, "Squats" );
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