package org.fta.Services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import org.apache.commons.io.FileUtils;
import org.fta.Models.PastApplicationsModel;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.fta.Services.PastApplicationsService;


class PastApplicationsServiceTest {

    public static final String ZOOM_LINK = "team";
    public static final String CUSTOMER_NAME = "Aurel";
    public static final String EXERCISE_NAME = "abdomene";

    @AfterEach
    void tearDown() {
        System.out.println("After each");
        PastApplicationsService.getDatabase().close();
    }

    @BeforeAll
    static void beforeAll() {
        System.out.println("Before Class");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("After Class");
    }

    @BeforeEach
    void setUp() throws Exception{
        FileSystemService.APPLICATION_FOLDER=".fis-fta";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        PastApplicationsService.initDatabase();
    }


    @Test
    @DisplayName("Database is initialized, and there are no applications")
    void testDatabaseIsInitializedAndApplicationIsThere() {
        assertThat(PastApplicationsService.getAllApplications()).isNotNull();
        assertThat(PastApplicationsService.getAllApplications()).isEmpty();
    }

    @Test
    @DisplayName("Program is successfully persisted to the database")
    void testProgramIsAddedToDatabase() {
        PastApplicationsService.addPastApplicationToDatabase(EXERCISE_NAME, CUSTOMER_NAME , ZOOM_LINK);
        assertThat(PastApplicationsService.getAllApplications()).isNotEmpty();
        assertThat(PastApplicationsService.getAllApplications()).size().isEqualTo(1);
        PastApplicationsModel Program = PastApplicationsService.getAllApplications().get(0);
        assertThat(Program).isNotNull();
        assertThat(Program.getExerciseName()).isEqualTo(EXERCISE_NAME);
        assertThat(Program.getCustomerName()).isEqualTo(CUSTOMER_NAME);
        assertThat(Program.getZoomLink()).isEqualTo(ZOOM_LINK);
    }

    @Test
    @DisplayName("Correct program is retrieved from database")
    void testCorrectProgramIsRetrievedFromDatabase() {
        PastApplicationsService.addPastApplicationToDatabase(EXERCISE_NAME, CUSTOMER_NAME , ZOOM_LINK);
        PastApplicationsService.addPastApplicationToDatabase(EXERCISE_NAME+1, CUSTOMER_NAME+1, ZOOM_LINK+1);
        PastApplicationsModel Program=PastApplicationsService.returnPastApplication(2);
        assertThat(Program).isNotNull();
        assertThat(Program.getCustomerName()).isEqualTo(CUSTOMER_NAME+1);
        assertThat(Program.getExerciseName()).isEqualTo(EXERCISE_NAME+1);
        assertThat(Program.getZoomLink()).isEqualTo(ZOOM_LINK+1);
    }

    @Test
    @DisplayName("Program number is succesfully returned")
    void testProgramNumberIsReturned() {
        PastApplicationsService.addPastApplicationToDatabase(EXERCISE_NAME, CUSTOMER_NAME , ZOOM_LINK);
        PastApplicationsService.addPastApplicationToDatabase(EXERCISE_NAME+1, CUSTOMER_NAME+1, ZOOM_LINK+1);
        PastApplicationsService.addPastApplicationToDatabase(EXERCISE_NAME+2, CUSTOMER_NAME+2, ZOOM_LINK+2);
        assertThat(PastApplicationsService.getPastApplicationNumber()).isEqualTo(3);
    }

}