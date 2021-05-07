package org.fta.Services;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.fta.Exceptions.UsernameAlreadyExistsException;
import org.fta.Models.ClientModel;
import org.fta.Exceptions.InvalidCredentialsException;
import static org.fta.Services.FileSystemService.getPathToFile;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class ClientService {

    private static ObjectRepository<ClientModel> clientRepository;

    public static void initDatabase() {

        Nitrite database = Nitrite.builder()
                .filePath(getPathToFile("fis-fta.db").toFile())
                .openOrCreate("test", "test");

        clientRepository = database.getRepository(ClientModel.class);
    }

    private static void checkUserDoesNotAlreadyExist(String username) throws UsernameAlreadyExistsException {

        for (ClientModel client : clientRepository.find()) {
            if (Objects.equals(username, client.getUsername()))
                throw new UsernameAlreadyExistsException(username);
        }
    }

    private static MessageDigest getMessageDigest() {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-512 does not exist!");
        }
        return md;
    }

    private static String encodePassword(String salt, String password) {
        MessageDigest md = getMessageDigest();
        md.update(salt.getBytes(StandardCharsets.UTF_8));

        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

        // This is the way a password should be encoded when checking the credentials
        return new String(hashedPassword, StandardCharsets.UTF_8)
                .replace("\"", ""); //to be able to save in JSON format
    }

    public static void addUser(String username, String password, String role) throws UsernameAlreadyExistsException {
        checkUserDoesNotAlreadyExist(username);
        clientRepository.insert(new ClientModel(username, encodePassword(username, password), role));
    }

    public static void VerifyLogin(String username, String password) throws InvalidCredentialsException {
        CheckUserCredentialsInLogin(username, password);
    }

    private static void CheckUserCredentialsInLogin(String username, String password) throws InvalidCredentialsException {
        int k = 0; //contor
        for (ClientModel client : clientRepository.find()) {
            if (Objects.equals(username, client.getUsername())) {
                if (Objects.equals(client.getPassword(), encodePassword(username, password))) {
                    k++;
                }
            }
        }
        if (k == 0) {
            throw new InvalidCredentialsException();
        }
    }
}
