package org.fta.Services;

import org.apache.commons.io.FileUtils;
import org.fta.Exceptions.UsernameAlreadyExistsException;
import org.fta.Models.ClientModel;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.testfx.assertions.api.Assertions.assertThat;

class ClientServiceTest {

    public static final String ADMIN = "admin";

    @AfterEach
    void tearDown() {
        System.out.println("After each");
        ClientService.getDatabase().close();
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
    void setup() throws Exception{
        FileSystemService.APPLICATION_FOLDER=".test-fta";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        ClientService.initDatabase();
        System.out.println("Before each");
    }

    @Test
    @DisplayName("Database is initialized, and there are no users")
    void testDatabaseIsInitializedAndNoUserIsPersisted(){
        assertThat(ClientService.getAllUsers()).isNotNull();
        assertThat(ClientService.getAllUsers()).isEmpty();
    }

    @Test
    @DisplayName("User is successfully persisted to Database")
    void testUserIsAddedToDatabase() throws UsernameAlreadyExistsException{
        ClientService.addUser(ADMIN,ADMIN,ADMIN);
        assertThat(ClientService.getAllUsers()).isNotEmpty();
        assertThat(ClientService.getAllUsers()).size().isEqualTo(1);
        ClientModel user = ClientService.getAllUsers().get(0);
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo(ADMIN);
        assertThat(user.getPassword()).isEqualTo(ClientService.encodePassword(ADMIN, ADMIN));
        assertThat(user.getRole()).isEqualTo(ADMIN);
    }

    @Test
    @DisplayName("User can not be added twice")
    void testUserCanNotBeAddedTwice(){
        assertThrows(UsernameAlreadyExistsException.class, () -> {
            ClientService.addUser(ADMIN, ADMIN, ADMIN);
            ClientService.addUser(ADMIN, ADMIN, ADMIN);
        });
    }


}