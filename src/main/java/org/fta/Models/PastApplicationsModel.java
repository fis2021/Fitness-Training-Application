package org.fta.Models;

import org.dizitart.no2.objects.Id;

public class PastApplicationsModel {

    @Id
    public String customerName;
    public String zoomLink;
    public String exerciseName;

    public PastApplicationsModel(String exerciseName, String customerName, String zoomLink) {
        this.exerciseName = exerciseName;
        this.customerName = customerName;
        this.zoomLink = zoomLink;
    }

    public PastApplicationsModel(){

    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getZoomLink() {
        return zoomLink;
    }

    public void setZoomLink(String zoomLink) { this.zoomLink = zoomLink; }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    @Override
    public String toString() {
        return "Program{" +
                "customerName='" + customerName + '\'' +
                ", zoomLink='" + zoomLink + '\'' +
                ", exerciseName='" + exerciseName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PastApplicationsModel that = (PastApplicationsModel) o;
        if (customerName== null) {
            if (that.customerName != null)
                return false;
        } else if (!customerName.equals(that.customerName))
            return false;
        if (zoomLink == null) {
            if (that.zoomLink != null)
                return false;
        } else if (!zoomLink.equals(that.zoomLink))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((customerName == null) ? 0 : customerName.hashCode());
        result = prime * result + ((zoomLink == null) ? 0 : zoomLink.hashCode());
        result = prime * result + ((exerciseName == null) ? 0 : exerciseName.hashCode());
        return result;
    }

}
