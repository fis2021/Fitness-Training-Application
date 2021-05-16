package org.fta.Services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.apache.commons.io.FileUtils;
import org.fta.Models.PastApplicationsModel;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.fta.Models.FitnessProgramModel;
import org.fta.Models.ProgramApplyModel;

class ProgramApplyServiceTest {

    public static final String CUSTOMERNAME = "customer";
    public static final String TRAININGLEVEL = "level";
    public static final String EXERCISENAME = "exercise";

    @AfterEach
    void tearDown() {
        System.out.println("After each");
        ProgramApplyService.getDatabase().close();
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
        ProgramApplyService.initDatabase();
    }

    @Test
    @DisplayName("Application is successfully persisted to the database")
    void testApplicationIsAddedToDatabase() {
        ProgramApplyService.addApplicationToDatabase(CUSTOMERNAME, TRAININGLEVEL, EXERCISENAME);
        assertThat(ProgramApplyService.getAllApplications()).isNotEmpty();
        assertThat(ProgramApplyService.getAllApplications()).size().isEqualTo(1);
        ProgramApplyModel programApply = ProgramApplyService.getAllApplications().get(0);
        assertThat(programApply).isNotNull();
        assertThat(programApply.getCustomerTrainingLevel()).isEqualTo(TRAININGLEVEL);
        assertThat(programApply.getExerciseName()).isEqualTo(EXERCISENAME);
        assertThat(programApply.getCustomerName()).isEqualTo(CUSTOMERNAME);
    }

    @Test
    @DisplayName("Correct application is retrieved from database")
    void testCorrectProgramIsRetrievedFromDatabase() {
        ProgramApplyService.addApplicationToDatabase(CUSTOMERNAME, TRAININGLEVEL, EXERCISENAME);
        ProgramApplyService.addApplicationToDatabase(CUSTOMERNAME+1, TRAININGLEVEL+1, EXERCISENAME+1);
        ProgramApplyModel programApply = ProgramApplyService.returnProgramApply(2);
        assertThat(programApply).isNotNull();
        assertThat(programApply.getCustomerName()).isEqualTo(CUSTOMERNAME+1);
        assertThat(programApply.getExerciseName()).isEqualTo(EXERCISENAME+1);
        assertThat(programApply.getCustomerTrainingLevel()).isEqualTo(TRAININGLEVEL+1);
    }

    @Test
    @DisplayName("Application is successfully accepted and removed from the database")
    void testApplicationIsAcceptedAndRemovedFromDatabase() {
        ProgramApplyService.addApplicationToDatabase(CUSTOMERNAME, TRAININGLEVEL, EXERCISENAME);
        ProgramApplyService.addApplicationToDatabase(CUSTOMERNAME+1, TRAININGLEVEL+1, EXERCISENAME+1);
        ProgramApplyService.acceptApplication(CUSTOMERNAME,EXERCISENAME);
        assertThat(ProgramApplyService.getAllApplications()).size().isEqualTo(1);
    }

    @Test
    @DisplayName("Application is successfully denied and removed from the database")
    void testOrderIsDeniedAndRemovedFromDatabase() {
        ProgramApplyService.addApplicationToDatabase(CUSTOMERNAME, TRAININGLEVEL, EXERCISENAME);
        ProgramApplyService.addApplicationToDatabase(CUSTOMERNAME+1, TRAININGLEVEL+1, EXERCISENAME+1);
        ProgramApplyService.denyApplication(CUSTOMERNAME,EXERCISENAME);
        assertThat(ProgramApplyService.getAllApplications()).size().isEqualTo(1);
    }

    @Test
    @DisplayName("Application number is successfully returned")
    void testApplicationNumberIsReturned() {
        ProgramApplyService.addApplicationToDatabase(CUSTOMERNAME, TRAININGLEVEL, EXERCISENAME);
        ProgramApplyService.addApplicationToDatabase(CUSTOMERNAME+1, TRAININGLEVEL+1, EXERCISENAME+1);
        ProgramApplyService.addApplicationToDatabase(CUSTOMERNAME+2, TRAININGLEVEL+2, EXERCISENAME+2);
        assertThat(ProgramApplyService.getProgramApplyNumber()).isEqualTo(3);
    }

}