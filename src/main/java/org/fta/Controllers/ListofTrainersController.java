package org.fta.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.fta.App;

public class ListofTrainersController {
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
    private Button pastbutton;

    @FXML
    public void handlePastApplications(ActionEvent event) throws Exception{
        Stage primary=(Stage)pastbutton.getScene().getWindow();
        Parent root = FXMLLoader.load(App.class.getResource("PastApplications.fxml"));
        primary.setTitle("Past Applications");
        primary.setScene(new Scene(root, 370, 300));
        primary.show();
    }
}
