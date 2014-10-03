/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package medicalapp.data;

import java.util.Date;

/**
 *
 * @author Richard
 */
public class VisitHistory {
    private int visitHistoryID;
    private int appointmentID;
    private String date;
    private String doctorName;

    public VisitHistory(int visitHistoryID, int appointmentID, String date, String doctorName) {
        this.visitHistoryID = visitHistoryID;
        this.appointmentID = appointmentID;
        this.date = date;
        this.doctorName = doctorName;
    }

    public int getVisitHistoryID() {
        return visitHistoryID;
    }

    public void setVisitHistoryID(int visitHistoryID) {
        this.visitHistoryID = visitHistoryID;
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(int appointmentID) {
        this.date = date;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }
    
}
