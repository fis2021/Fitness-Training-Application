package org.fta.Services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.fta.Exceptions.InvalidFieldException;
import org.fta.Exceptions.BlankFieldException;
import org.fta.Exceptions.InvalidCredentialsException;
import org.fta.Models.ClientModel;
import org.fta.Models.FitnessProgramModel;

class FitnessProgramServiceTest {

    public static final String EXERCISENAME = "exercise";
    public static final String REPS = "15";
    public static final String SETS = "3";
    public static final String ZOOM = "link";
    public static final String TRAINERNAME = "trainer";

    @AfterEach
    void tearDown() {
        System.out.println("After each");
        FitnessProgramService.getDatabase().close();
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
        FileSystemService.APPLICATION_FOLDER = ".test-fta";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        FitnessProgramService.initDatabase();
    }

    @Test
    @DisplayName("Database is initialized, and there are no programs")
    void testDatabaseIsInitializedAndThereAreNoPrograms(){
        assertThat(FitnessProgramService.getAllPrograms()).isNotNull();
        assertThat(FitnessProgramService.getAllPrograms()).isEmpty();
    }

    @Test
    @DisplayName("Program is successfully added to the database")
    void testProgramIsAddedToDatabase(){
        FitnessProgramService.addProgram(EXERCISENAME, REPS, SETS, ZOOM, TRAINERNAME);
        assertThat(FitnessProgramService.getAllPrograms()).isNotEmpty();
        assertThat(FitnessProgramService.getAllPrograms()).size().isEqualTo(1);
        FitnessProgramModel program = FitnessProgramService.getAllPrograms().get(0);
        assertThat(program).isNotNull();
        assertThat(program.getExerciseName()).isEqualTo(EXERCISENAME);
        assertThat(program.getTrainerName()).isEqualTo(TRAINERNAME);
        assertThat(program.getZoomLink()).isEqualTo(ZOOM);
        assertThat(program.getReps()).isEqualTo(Integer.parseInt(REPS));
        assertThat(program.getSets()).isEqualTo(Integer.parseInt(SETS));
    }

    @Test
    @DisplayName("Correct program is retrieved from database")
    void testCorrectProgramIsRetrievedFromDatabase() {
        FitnessProgramService.addProgram(EXERCISENAME, REPS, SETS, ZOOM, TRAINERNAME);
        FitnessProgramService.addProgram(EXERCISENAME+1, REPS+1, SETS+1, ZOOM+1, TRAINERNAME+1);
        FitnessProgramModel program = FitnessProgramService.returnProgram(2);
        assertThat(program).isNotNull();
        assertThat(program.getTrainerName()).isEqualTo(TRAINERNAME+1);
        assertThat(program.getZoomLink()).isEqualTo(ZOOM+1);
        assertThat(program.getSets()).isEqualTo(Integer.parseInt(SETS+1));
        assertThat(program.getReps()).isEqualTo(Integer.parseInt(REPS+1));
        assertThat(program.getExerciseName()).isEqualTo(EXERCISENAME+1);
    }

    @Test
    @DisplayName("Program number is successfully returned")
    void testShirtNumberIsReturnedCorrectly() {
        FitnessProgramService.addProgram(EXERCISENAME, REPS, SETS, ZOOM, TRAINERNAME);
        FitnessProgramService.addProgram(EXERCISENAME+1, REPS+1, SETS+1, ZOOM+1, TRAINERNAME+1);
        FitnessProgramService.addProgram(EXERCISENAME+2, REPS+2, SETS+2, ZOOM+2, TRAINERNAME+2);
        assertThat(FitnessProgramService.getProgramNumber()).isEqualTo(3);
    }

    @Test
    @DisplayName("Program is successfully edited in database")
    void testShirtIsSuccessfullyEditedInDatabase()
    {
        FitnessProgramService.addProgram(EXERCISENAME, REPS, SETS, ZOOM, TRAINERNAME);
        FitnessProgramService.editProgram(EXERCISENAME, REPS, SETS, ZOOM, "name");
        FitnessProgramModel program = FitnessProgramService.getAllPrograms().get(0);
        assertThat(program.getTrainerName()).isEqualTo("name");
    }

    @Test
    @DisplayName("Program is successfully deleted in database")
    void testShirtIsSuccessfullyDeletedInDatabase()
    {
        FitnessProgramService.addProgram(EXERCISENAME, REPS, SETS, ZOOM, TRAINERNAME);
        FitnessProgramService.addProgram(EXERCISENAME+1, REPS+1, SETS+1, ZOOM+1, TRAINERNAME+1);
        FitnessProgramService.deleteProgram(EXERCISENAME, REPS, SETS, ZOOM, TRAINERNAME);
        assertThat(FitnessProgramService.getAllPrograms()).size().isEqualTo(1);
    }

    @Test
    @DisplayName("Blank exercise name field cannot be entered")
    void testBlankExerciseNameFieldCannotBeEntered(){
        assertThrows(BlankFieldException.class, () -> {
            FitnessProgramService.verifyBlankFields("", REPS, SETS, ZOOM, TRAINERNAME);
        });
    }

    @Test
    @DisplayName("Blank trainer name field cannot be entered")
    void testBlankTrainerNameFieldCannotBeEntered(){
        assertThrows(BlankFieldException.class, () -> {
            FitnessProgramService.verifyBlankFields(EXERCISENAME, REPS, SETS, ZOOM, "");
        });
    }

    @Test
    @DisplayName("Blank reps field cannot be entered")
    void testBlankRepsFieldCannotBeEntered(){
        assertThrows(BlankFieldException.class, () -> {
            FitnessProgramService.verifyBlankFields(EXERCISENAME, "", SETS, ZOOM, TRAINERNAME);
        });
    }

    @Test
    @DisplayName("Blank sets field cannot be entered")
    void testBlankSetsFieldCannotBeEntered(){
        assertThrows(BlankFieldException.class, () -> {
            FitnessProgramService.verifyBlankFields(EXERCISENAME, REPS, "", ZOOM, TRAINERNAME);
        });
    }

    @Test
    @DisplayName("Blank zoom field cannot be entered")
    void testBlankZoomFieldCannotBeEntered(){
        assertThrows(BlankFieldException.class, () -> {
            FitnessProgramService.verifyBlankFields(EXERCISENAME, REPS, SETS, "", TRAINERNAME);
        });
    }

    @Test
    @DisplayName("All blank fields cannot be entered")
    void testAllBlankFieldsCannotBeEntered(){
        assertThrows(BlankFieldException.class, () -> {
            FitnessProgramService.verifyBlankFields("","","","","");
        });
    }

    @Test
    @DisplayName("Program does not exist in database when editing/deleting program")
    void testCanNotEnterProgramThatDoesNotExistWhenEditingOrDeleting()
    {
        assertThrows(InvalidFieldException.class, () -> {
            FitnessProgramService.addProgram(EXERCISENAME, REPS, SETS, ZOOM, TRAINERNAME);
            FitnessProgramService.verifyInvalidFields("exercose1", REPS, SETS, ZOOM, TRAINERNAME);
        });
    }



}