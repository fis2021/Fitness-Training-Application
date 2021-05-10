package org.fta.Models;

import org.dizitart.no2.objects.Id;

public class FitnessProgramModel {
    @Id
    private String exerciseName;
    private int reps;
    private int sets;
    private String zoomLink;
    private String trainerName;

    public FitnessProgramModel(String exerciseName, int reps, int sets, String zoomLink, String trainerName) {
        this.exerciseName = exerciseName;
        this.reps = reps;
        this.sets = sets;
        this.zoomLink = zoomLink;
        this.trainerName = trainerName;
    }

    public FitnessProgramModel() {

    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) { this.exerciseName = exerciseName; }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public String getZoomLink() {
        return zoomLink;
    }

    public void setZoomLink(String zoomLink) {
        this.zoomLink = zoomLink;
    }

    public String getTrainerName() { return trainerName; }

    public void setTrainerName(String trainerName) { this.trainerName = trainerName; }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((exerciseName == null) ? 0 : exerciseName.hashCode());
        result = prime * result + reps;
        result = prime * result + sets;
        result = prime * result + ((zoomLink == null) ? 0 : zoomLink.hashCode());
        result = prime * result + ((trainerName == null) ? 0 : trainerName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FitnessProgramModel other = (FitnessProgramModel) obj;
        if (trainerName == null) {
            if (other.trainerName != null)
                return false;
        } else if (!trainerName.equals(other.trainerName))
            return false;
        if (exerciseName == null) {
            if (other.exerciseName != null)
                return false;
        } else if (!exerciseName.equals(other.exerciseName))
            return false;
        if (reps != other.reps)
            return false;
        if (sets != other.sets)
            return false;
        if (zoomLink == null) {
            if (other.zoomLink != null)
                return false;
        } else if (!zoomLink.equals(other.zoomLink))
            return false;
        return true;
    }


}
