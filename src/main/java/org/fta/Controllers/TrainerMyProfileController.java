package org.fta.Controllers;

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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.fta.App;

import java.net.URL;
import java.util.ResourceBundle;

import org.fta.Services.ClientService;
import org.fta.Services.FitnessProgramService;
import org.fta.Models.FitnessProgramModel;
import org.fta.Exceptions.BlankFieldException;
import org.fta.Exceptions.InvalidFieldException;


public class TrainerMyProfileController implements Initializable {

    @FXML
    private Text successMessage;
    @FXML
    private TextField exerciseNameField;
    @FXML
    private TextField repsField;
    @FXML
    private TextField setsField;
    @FXML
    private TextField zoomField;
    @FXML
    private TextField trainerNameField;
    @FXML
    private TableView<FitnessProgramModel> trainerTable;
    @FXML
    private TableColumn<FitnessProgramModel, String> exerciseNameColumn;
    @FXML
    private TableColumn<FitnessProgramModel, Integer> repsColumn;
    @FXML
    private TableColumn<FitnessProgramModel, Integer> setsColumn;
    @FXML
    private TableColumn<FitnessProgramModel, String> zoomColumn;
    @FXML
    private TableColumn<FitnessProgramModel, String> trainerNameColumn;
    @FXML
    private Button addButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button logoutButton;
    @FXML
    private Button refreshButton;
    @FXML
    private Button myProfileButton;
    @FXML
    private Button zoomApplicantsButton;

    ObservableList<FitnessProgramModel> list=  FXCollections.observableArrayList();

    private int count;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        exerciseNameColumn.setCellValueFactory(new PropertyValueFactory<FitnessProgramModel,String>("exerciseName"));
        repsColumn.setCellValueFactory(new PropertyValueFactory<FitnessProgramModel,Integer>("reps"));
        setsColumn.setCellValueFactory(new PropertyValueFactory<FitnessProgramModel,Integer>("sets"));
        zoomColumn.setCellValueFactory(new PropertyValueFactory<FitnessProgramModel,String>("zoomLink"));
        trainerNameColumn.setCellValueFactory(new PropertyValueFactory<FitnessProgramModel,String>("trainerName"));
        count=FitnessProgramService.getProgramNumber();
        for(int i=1;i<=count;i++) {
            FitnessProgramModel program = new FitnessProgramModel();
            program = FitnessProgramService.returnProgram(i);
            list.add(program);
        }

        trainerTable.setItems(list);
    }

    public void handleMyProfileAction (ActionEvent event) throws Exception {
        Stage stage=(Stage)myProfileButton.getScene().getWindow();
        Parent root = FXMLLoader.load(App.class.getResource("TrainerMyProfile.fxml"));
        stage.setTitle("Trainer Profile");
        stage.setScene(new Scene(root, 512, 847));
        stage.show();
    }

    public void handleZoomApplicantsAction (ActionEvent event) throws Exception {
        Stage stage=(Stage)zoomApplicantsButton.getScene().getWindow();
        Parent root = FXMLLoader.load(App.class.getResource("ZoomApplicants.fxml"));
        stage.setTitle("Zoom Applicants");
        stage.setScene(new Scene(root, 350, 300));
        stage.show();
    }

    public void handleLogOutAction (ActionEvent event) throws Exception {
        Stage stage=(Stage)logoutButton.getScene().getWindow();
        Parent root = FXMLLoader.load(App.class.getResource("Login.fxml"));
        stage.setTitle("Login");
        stage.setScene(new Scene(root, 350, 300));
        stage.show();
    }

    @FXML
    public void clickProgram(MouseEvent event) {
        if(event.getClickCount()==1) {
            int idx;
            idx=trainerTable.getSelectionModel().getSelectedIndex();
            exerciseNameField.setText(exerciseNameColumn.getCellData(idx));
            repsField.setText(String.valueOf(repsColumn.getCellData(idx)));
            setsField.setText(String.valueOf(setsColumn.getCellData(idx)));
            zoomField.setText(zoomColumn.getCellData(idx));
            trainerNameField.setText(trainerNameColumn.getCellData(idx));
        }
    }

    @FXML
    public void handleAddAction(ActionEvent event) throws Exception {
        try {
            FitnessProgramService.verifyBlankFields(exerciseNameField.getText(), repsField.getText(), setsField.getText(), zoomField.getText(), trainerNameField.getText());
            FitnessProgramService.addProgram(exerciseNameField.getText(), repsField.getText(), setsField.getText(), zoomField.getText(), trainerNameField.getText());
            successMessage.setText("Added program");
        }
        catch(BlankFieldException e) {
            successMessage.setText(e.getMessage());
        }
    }

    @FXML
    public void handleEditAction(ActionEvent event) throws Exception {
        try {
            FitnessProgramService.verifyBlankFields(exerciseNameField.getText(), repsField.getText(), setsField.getText(), zoomField.getText(), trainerNameField.getText());
            FitnessProgramService.verifyInvalidFields(exerciseNameField.getText(), repsField.getText(), setsField.getText(), zoomField.getText(), trainerNameField.getText());
            FitnessProgramService.editProgram(exerciseNameField.getText(), repsField.getText(), setsField.getText(), zoomField.getText(), trainerNameField.getText());
            successMessage.setText("Edited program");
        }
        catch(BlankFieldException e) {
            successMessage.setText(e.getMessage());
        }
        catch(InvalidFieldException e) {
            successMessage.setText(e.getMessage());
        }
    }

    @FXML
    public void handleDeleteAction(ActionEvent event) throws Exception {
        try {
            FitnessProgramService.verifyBlankFields(exerciseNameField.getText(), repsField.getText(), setsField.getText(), zoomField.getText(), trainerNameField.getText());
            FitnessProgramService.verifyInvalidFields(exerciseNameField.getText(), repsField.getText(), setsField.getText(), zoomField.getText(), trainerNameField.getText());
            FitnessProgramService.deleteProgram(exerciseNameField.getText(), repsField.getText(), setsField.getText(), zoomField.getText(), trainerNameField.getText());
            successMessage.setText("Deleted program");
        }
        catch(BlankFieldException e) {
            successMessage.setText(e.getMessage());
        }
        catch(InvalidFieldException e) {
            successMessage.setText(e.getMessage());
        }
    }

    @FXML
    public void handleRefreshAction(ActionEvent event) {
        trainerTable.getItems().clear();
        exerciseNameColumn.setCellValueFactory(new PropertyValueFactory<FitnessProgramModel,String>("exerciseName"));
        repsColumn.setCellValueFactory(new PropertyValueFactory<FitnessProgramModel,Integer>("reps"));
        setsColumn.setCellValueFactory(new PropertyValueFactory<FitnessProgramModel,Integer>("sets"));
        zoomColumn.setCellValueFactory(new PropertyValueFactory<FitnessProgramModel,String>("zoomLink"));
        trainerNameColumn.setCellValueFactory(new PropertyValueFactory<FitnessProgramModel,String>("trainerName"));
        count=FitnessProgramService.getProgramNumber();
        for(int i=1;i<=count;i++) {
            FitnessProgramModel program = new FitnessProgramModel();
            program = FitnessProgramService.returnProgram(i);
            list.add(program);
        }

        trainerTable.setItems(list);
    }

}



























