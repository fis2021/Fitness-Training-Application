package org.fta.Services;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.fta.Models.ChooseModel;
import org.fta.Models.FitnessProgramModel;
import org.fta.Services.FileSystemService;

class ChooseServiceTest {
    private static final String TRAINER = "TRAINER";
    private static final String EXERCISE = "ABDOMENE";

    @AfterEach
    void tearDown() {
        System.out.println("After each");
        ChooseService.getDatabase().close();
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
        ChooseService.initDatabase();
    }

    @Test
    @DisplayName("Database is initialized, and there are no chosen plans")
    void testDatabaseIsInitializedAndNoChosenIsThere() {
        assertThat(ChooseService.getAllChosen()).isNotNull();
        assertThat(ChooseService.getAllChosen()).isEmpty();
    }

    @Test
    @DisplayName("The chosen plan is successfully persisted to the database")
    void testChosenisAddedDatabase() {
        ChooseService.addChosenProgram(TRAINER, EXERCISE);
        assertThat(ChooseService.getAllChosen()).isNotEmpty();
        assertThat(ChooseService.getAllChosen()).size().isEqualTo(1);
        ChooseModel chosen = ChooseService.getAllChosen().get(0);
        assertThat(chosen).isNotNull();
        assertThat(chosen.gettrainer()).isEqualTo(TRAINER);
        assertThat(chosen.getexercise()).isEqualTo(EXERCISE);
    }


    @Test
    @DisplayName("The plans database is empty after removing all its' elements")
    void testChosenIsEmptied() {
        ChooseService.addChosenProgram(TRAINER, EXERCISE);
        ChooseService.addChosenProgram(TRAINER+1, EXERCISE+1);
        ChooseService.addChosenProgram(TRAINER+2, EXERCISE+2);
        ChooseService.removeChosen();
        assertThat(ChooseService.getAllChosen()).size().isEqualTo(0);
    }

}