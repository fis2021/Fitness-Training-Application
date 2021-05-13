package org.fta.Models;

public class ChooseModel {

    private String trainer;
    private String exercise;

    public ChooseModel(String trainer, String exercise) {
        this.trainer = trainer;
        this.exercise = exercise;
    }

    public ChooseModel() {

    }

    public String gettrainer() {
        return trainer;
    }

    public void settrainer(String trainer) {
        this.trainer = trainer;
    }

    public String getexercise() {
        return exercise;
    }

    public void setexercise(String exercise) {
        this.exercise = exercise;
    }

//    @Override
//    public int hashCode() {
//        final int prime = 31;
//        int result = 1;
//        result = prime * result + exercise;
//        result = prime * result + ((trainer == null) ? 0 : trainer.hashCode());
//        return result;
//    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ChooseModel other = (ChooseModel) obj;
        if (exercise != other.exercise)
            return false;
        if (trainer == null) {
            if (other.trainer != null)
                return false;
        } else if (!trainer.equals(other.trainer))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "CartShirt [trainer=" + trainer + ", exercise=" + exercise + "]";
    }

}
