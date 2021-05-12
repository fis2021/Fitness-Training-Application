package org.fta.Models;

import org.dizitart.no2.objects.Id;

import java.util.Objects;

public class ProgramApplyModel {
    @Id
    public String customerName;
    public String customerTrainingLevel;
    public String exerciseName;

    public ProgramApplyModel(String customerName, String customerTrainingLevel, String exerciseName) {
        this.customerName = customerName;
        this.customerTrainingLevel = customerTrainingLevel;
        this.exerciseName = exerciseName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerTrainingLevel() {
        return customerTrainingLevel;
    }

    public void setCustomerTrainingLevel(String customerTrainingLevel) {
        this.customerTrainingLevel = customerTrainingLevel;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProgramApplyModel that = (ProgramApplyModel) o;
        if (customerName== null) {
            if (that.customerName != null)
                return false;
        } else if (!customerName.equals(that.customerName))
                     return false;
        if (customerTrainingLevel == null) {
            if (that.customerTrainingLevel != null)
                return false;
        } else if (!customerTrainingLevel.equals(that.customerTrainingLevel))
                    return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((customerName == null) ? 0 : customerName.hashCode());
        result = prime * result + ((customerTrainingLevel == null) ? 0 : customerTrainingLevel.hashCode());
        result = prime * result + ((exerciseName == null) ? 0 : exerciseName.hashCode());
        return result;
    }
}
