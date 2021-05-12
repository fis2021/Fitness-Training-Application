package org.fta.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import org.fta.App;
import org.fta.Models.FitnessProgramModel;
import org.fta.Models.ProgramListener;

public class ProgramController {
    @FXML
    private Label exerciseNameLabel;

    @FXML
    private Label trainerNameLabel;

    @FXML
    private Label repsLabel;

    @FXML
    private Label setsLabel;

    private FitnessProgramModel program;

    private ProgramListener listener;

    @FXML
    private void click(MouseEvent mouseEvent)
    {
        listener.onClickListener(program);
    }

    public void setInfo(FitnessProgramModel program, ProgramListener listener) {
        this.program = program;
        this.listener = listener;
        exerciseNameLabel.setText(program.getExerciseName());
        trainerNameLabel.setText(program.getTrainerName());
        repsLabel.setText(String.valueOf(program.getReps()));
        setsLabel.setText(String.valueOf(program.getSets()));
    }
}


