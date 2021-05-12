package org.fta.Controllers;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.fta.App;
import org.fta.Models.ProgramApplyModel;
import org.fta.Models.FitnessProgramModel;
import org.fta.Services.FitnessProgramService;
import org.fta.Services.ProgramApplyService;

import java.net.URL;
import java.util.ResourceBundle;


public class ZoomApplicantsController implements Initializable {
    @FXML
    private Button logoutButton;
    @FXML
    private Button myProfileButton;
    @FXML
    private Button zoomApplicantsButton;
    @FXML
    private TableView<ProgramApplyModel> applicantsTable;
    @FXML
    private TableColumn<ProgramApplyModel, String> clientNameColumn;
    @FXML
    private TableColumn<ProgramApplyModel, String> exerciseNameColumn;
    @FXML
    private TableColumn<ProgramApplyModel, String> trainingLevelColumn;
    private int count;
    ObservableList<ProgramApplyModel> list = FXCollections.observableArrayList();


    public void handleMyProfileAction (ActionEvent event) throws Exception{
        Stage stage = (Stage)myProfileButton.getScene().getWindow();
        Parent root = FXMLLoader.load(App.class.getResource("TrainerMyProfile.fxml"));
        stage.setTitle("Trainer Profile");
        stage.setScene(new Scene(root, 847, 512));
        stage.show();
    }

    public void handleZoomApplicantsAction (ActionEvent event) throws Exception{
        Stage stage = (Stage)zoomApplicantsButton.getScene().getWindow();
        Parent root = FXMLLoader.load(App.class.getResource("ZoomApplicants.fxml"));
        stage.setTitle("Zoom Applicants");
        stage.setScene(new Scene(root, 847, 512));
        stage.show();
    }

    public void handleLogOutAction (ActionEvent event) throws Exception{
        Stage stage = (Stage)logoutButton.getScene().getWindow();
        Parent root = FXMLLoader.load(App.class.getResource("Login.fxml"));
        stage.setTitle("Login");
        stage.setScene(new Scene(root, 350, 300));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clientNameColumn.setCellValueFactory(new PropertyValueFactory<ProgramApplyModel, String>("clientName"));
        exerciseNameColumn.setCellValueFactory(new PropertyValueFactory<ProgramApplyModel, String>("exerciseName"));
        trainingLevelColumn.setCellValueFactory(new PropertyValueFactory<ProgramApplyModel, String>("trainingLevel"));
        count = FitnessProgramService.getProgramNumber();
        for(int i=1; i<=count; i++){
            ProgramApplyModel programApply = new ProgramApplyModel();
            programApply = ProgramApplyService.returnProgramApply(i);
            list.add(programApply);
        }
        applicantsTable.setItems(list);
    }

}




































