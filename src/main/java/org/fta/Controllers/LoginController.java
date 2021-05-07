package org.fta.Controllers;

import org.fta.Services.ClientService;
import org.fta.Exceptions.UsernameAlreadyExistsException;
import org.fta.Exceptions.InvalidCredentialsException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.fta.App;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Text loginMessage;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;
    @FXML
    private ChoiceBox Role;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Role.getItems().addAll("Client", "Trainer");
    }

    @FXML
    public void handleRegisterRedirectAction(ActionEvent event) throws Exception{
        Stage primary=(Stage)loginMessage.getScene().getWindow();
        Parent root = FXMLLoader.load(App.class.getResource("Register.fxml"));
        primary.setTitle("Register");
        primary.setScene(new Scene(root, 370, 300));
        primary.show();
    }

    @FXML
    public void handleLoginPageAction() throws Exception {
        Stage primary=(Stage)loginMessage.getScene().getWindow();
        try {
            ClientService.VerifyLogin(usernameField.getText(),passwordField.getText());
            String Rol=(String)Role.getValue();
            if(Rol.equals("Client"))
            {
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("ClientHome.fxml"));
                primary.setTitle("Client Home");
                primary.setScene(new Scene(root, 950, 500));
                primary.show();
            }
            if(Rol.equals("Trainer"))
            {
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("TrainerHome.fxml"));
                primary.setTitle("Trainer Home");
                primary.setScene(new Scene(root, 800, 500));
                primary.show();
            }
        }catch(InvalidCredentialsException e) {
            loginMessage.setText(e.getMessage());
        }
    }

}

