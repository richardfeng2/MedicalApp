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
    private String date;
    private String doctorName;

    public VisitHistory(String date, String doctorName) {
        this.date = date;
        this.doctorName = doctorName;
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
