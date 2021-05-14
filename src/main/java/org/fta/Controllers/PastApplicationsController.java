package org.fta.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.fta.App;
import org.fta.Models.PastApplicationsModel;
import org.fta.Services.PastApplicationsService;

import java.net.URL;
import java.util.ResourceBundle;

public class PastApplicationsController implements Initializable {
    @FXML
    private Button logoutbutton;
    @FXML
    private TableView<PastApplicationsModel> applicationsTable;
    @FXML
    private TableColumn<PastApplicationsModel, String> customerNameColumn;
    @FXML
    private TableColumn<PastApplicationsModel, String> exerciseNameColumn;
    @FXML
    private TableColumn<PastApplicationsModel, String> zoomLinkColumn;
    private int count;
    ObservableList<PastApplicationsModel> list = FXCollections.observableArrayList();

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<PastApplicationsModel, String>("customerName"));
        exerciseNameColumn.setCellValueFactory(new PropertyValueFactory<PastApplicationsModel, String>("exerciseName"));
        zoomLinkColumn.setCellValueFactory(new PropertyValueFactory<PastApplicationsModel, String>("zoomLink"));
        count = PastApplicationsService.getPastApplicationNumber();
        for(int i=1; i<=count; i++){
            PastApplicationsModel pastApplication = new PastApplicationsModel();
            pastApplication = PastApplicationsService.returnPastApplication(i);
            list.add(pastApplication);
        }
        applicationsTable.setItems(list);
    }
}

