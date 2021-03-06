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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.fta.App;
import org.fta.Models.ProgramApplyModel;
import org.fta.Models.FitnessProgramModel;
import org.fta.Services.FitnessProgramService;
import org.fta.Services.PastApplicationsService;
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
    private Button refreshButton;
    @FXML
    private Button acceptButton;
    @FXML
    private Button denyButton;
    @FXML
    private TextField customerNameField;
    @FXML
    private TextField exerciseNameField;
    @FXML
    private TextField zoomLinkField;
    @FXML
    private Text applicationMessage;
    @FXML
    private TableView<ProgramApplyModel> applicantsTable;
    @FXML
    private TableColumn<ProgramApplyModel, String> customerNameColumn;
    @FXML
    private TableColumn<ProgramApplyModel, String> exerciseNameColumn;
    @FXML
    private TableColumn<ProgramApplyModel, String> customerTrainingLevelColumn;
    private int count;
    ObservableList<ProgramApplyModel> list = FXCollections.observableArrayList();


    public void handleMyProfileAction (ActionEvent event) throws Exception {
        Stage stage = (Stage)myProfileButton.getScene().getWindow();
        Parent root = FXMLLoader.load(App.class.getResource("TrainerMyProfile.fxml"));
        stage.setTitle("Trainer Profile");
        stage.setScene(new Scene(root, 847, 512));
        stage.show();
    }

    public void handleZoomApplicantsAction (ActionEvent event) throws Exception {
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
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<ProgramApplyModel, String>("customerName"));
        exerciseNameColumn.setCellValueFactory(new PropertyValueFactory<ProgramApplyModel, String>("exerciseName"));
        customerTrainingLevelColumn.setCellValueFactory(new PropertyValueFactory<ProgramApplyModel, String>("customerTrainingLevel"));
        count = ProgramApplyService.getProgramApplyNumber();
        for(int i=1; i<=count; i++){
            ProgramApplyModel programApply = new ProgramApplyModel();
            programApply = ProgramApplyService.returnProgramApply(i);
            list.add(programApply);
        }
        applicantsTable.setItems(list);
    }

    @FXML
    public void handleAcceptAction(ActionEvent event) {
        PastApplicationsService.addPastApplicationToDatabase(customerNameField.getText(), exerciseNameField.getText(), zoomLinkField.getText());
        ProgramApplyService.acceptApplication(customerNameField.getText(),exerciseNameField.getText());
        applicationMessage.setText("Application accepted");

    }

    @FXML
    public void handleDenyAction(ActionEvent event) {
        ProgramApplyService.denyApplication(customerNameField.getText(), exerciseNameField.getText());
        applicationMessage.setText("Application denied");
    }

    @FXML
    public void handleRefreshAction(ActionEvent event) {
        applicantsTable.getItems().clear();
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<ProgramApplyModel, String>("customerName"));
        exerciseNameColumn.setCellValueFactory(new PropertyValueFactory<ProgramApplyModel, String>("exerciseName"));
        customerTrainingLevelColumn.setCellValueFactory(new PropertyValueFactory<ProgramApplyModel, String>("customerTrainingLevel"));
        count = ProgramApplyService.getProgramApplyNumber();
        for(int i=1; i<=count; i++){
            ProgramApplyModel programApply = new ProgramApplyModel();
            programApply = ProgramApplyService.returnProgramApply(i);
            list.add(programApply);
        }
        applicantsTable.setItems(list);
    }

    @FXML
    public void clickApplication(MouseEvent event){
        if(event.getClickCount() == 1){
            int idx = 0;
            idx = applicantsTable.getSelectionModel().getSelectedIndex();
            customerNameField.setText(customerNameColumn.getCellData(idx));
            exerciseNameField.setText(exerciseNameColumn.getCellData(idx));
        }
    }

}