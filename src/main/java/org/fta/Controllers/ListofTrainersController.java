package org.fta.Controllers;

import org.fta.App;
import org.fta.Services.ChooseService;
import org.fta.Services.FitnessProgramService;
import org.fta.Models.ProgramListener;
import org.fta.Models.FitnessProgramModel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.fta.Services.ProgramApplyService;


public class ListofTrainersController implements Initializable{
    @FXML
    private Button logoutbutton;
    @FXML
    private ProgramListener listener;
    @FXML
    private ScrollPane scroll;
    @FXML
    private VBox selectedProgram;

    @FXML
    private Label selectedProgramExerciseName;
    @FXML
    private Label selectedProgramReps;
    @FXML
    private Label selectedProgramSets;
    @FXML
    private Label selectedProgramTrainerName;
    @FXML
    private GridPane grid;

    public void handleLogoutAction(ActionEvent event) throws Exception{
        Stage stage=(Stage)logoutbutton.getScene().getWindow();
        Parent root = FXMLLoader.load(App.class.getResource("Login.fxml"));
        stage.setTitle("Login");
        stage.setScene(new Scene(root, 350, 300));
        stage.show();
    }

    @FXML
    private Button pastbutton;

    private int k;

    @FXML
    private TextField enteredName;

    @FXML
    private TextField enteredTrainingLevel;

    @FXML
    public void handlePastApplications(ActionEvent event) throws Exception{
        Stage primary=(Stage)pastbutton.getScene().getWindow();
        Parent root = FXMLLoader.load(App.class.getResource("PastApplications.fxml"));
        primary.setTitle("Past Applications");
        primary.setScene(new Scene(root, 800, 800));
        primary.show();
    }

    private void setSelectedProgram(FitnessProgramModel program) {
        selectedProgramExerciseName.setText(program.getExerciseName());
        selectedProgramReps.setText(String.valueOf(program.getReps()));
        selectedProgramTrainerName.setText(program.getTrainerName());
        selectedProgramSets.setText(String.valueOf(program.getSets()));
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle){
        k = FitnessProgramService.getProgramNumber();
        if(k>0){
            listener=new ProgramListener() {
                @Override
                public void onClickListener(FitnessProgramModel program) {
                    setSelectedProgram(program);
                }
            };
        }
        int col=0;
        int lin=0;
        try{
            for(int i=1;i<=k;i++){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(App.class.getResource("program.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                ProgramController programController = fxmlLoader.getController();
                FitnessProgramModel program = new FitnessProgramModel();
                program = FitnessProgramService.returnProgram(i);
                programController.setInfo(program, listener);
                if(col==2){
                    col=0;
                    lin++;
                }
                col++;
                grid.add(anchorPane, col, lin);
                GridPane.setMargin(anchorPane, new Insets(15,5,15,5));
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Text applymessage;

    @FXML
    public void handleApplyAction(ActionEvent event) {
        ProgramApplyService.addApplicationToDatabase(enteredName.getText(),enteredTrainingLevel.getText(),selectedProgramExerciseName.getText());
        ChooseService.removeChosen();
        applymessage.setText("Applied succesfully!");
    }

    @FXML
    public void handleChooseAction(ActionEvent event) {
        ChooseService.addChosenProgram(selectedProgramTrainerName.getText(), selectedProgramExerciseName.getText());
    }
}