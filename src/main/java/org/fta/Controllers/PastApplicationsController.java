package org.fta.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.fta.App;

public class PastApplicationsController {
    @FXML
    private Button logoutbutton;

    public void handleLogoutAction(ActionEvent event) throws Exception{
        Stage stage=(Stage)logoutbutton.getScene().getWindow();
        Parent root = FXMLLoader.load(App.class.getResource("Login.fxml"));
        stage.setTitle("Login");
        stage.setScene(new Scene(root, 350, 300));
        stage.show();
    }

    @FXML
    private Button listbutton;

    @FXML
    public void handleListofTrainersRedirect(ActionEvent event) throws Exception{
        Stage primary=(Stage)listbutton.getScene().getWindow();
        Parent root = FXMLLoader.load(App.class.getResource("ListofTrainers.fxml"));
        primary.setTitle("List of Trainers");
        primary.setScene(new Scene(root, 370, 300));
        primary.show();
    }
}
