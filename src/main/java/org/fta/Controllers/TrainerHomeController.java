package org.fta.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.fta.App;

public class TrainerHomeController {
    @FXML
    private Button logoutButton;
    @FXML
    private Button myProfileButton;
    @FXML
    private Button zoomApplicantsButton;

    public void handleMyProfileAction (ActionEvent event) throws Exception{
        Stage stage=(Stage)myProfileButton.getScene().getWindow();
        Parent root = FXMLLoader.load(App.class.getResource("TrainerMyProfile.fxml"));
        stage.setTitle("Trainer Profile");
        stage.setScene(new Scene(root, 847, 512));
        stage.show();
    }

    public void handleZoomApplicantsAction (ActionEvent event) throws Exception{
        Stage stage=(Stage)zoomApplicantsButton.getScene().getWindow();
        Parent root = FXMLLoader.load(App.class.getResource("ZoomApplicants.fxml"));
        stage.setTitle("Zoom Applicants");
        stage.setScene(new Scene(root, 847, 512));
        stage.show();
    }

    public void handleLogOutAction (ActionEvent event) throws Exception{
        Stage stage=(Stage)logoutButton.getScene().getWindow();
        Parent root = FXMLLoader.load(App.class.getResource("Login.fxml"));
        stage.setTitle("Login");
        stage.setScene(new Scene(root, 350, 300));
        stage.show();
    }

}
