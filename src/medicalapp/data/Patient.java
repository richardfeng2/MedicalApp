/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package medicalapp.data;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Richard
 */
public class Patient extends Person {

   private int patientID;
   private ArrayList<String> conditions;
    
    public Patient(int patientID, int personID, String firstName, String lastName, boolean isPatient, boolean isStaff, String address, Date dateOfBirth, String contactNumber) {
        super(personID, firstName, lastName, isPatient, isStaff, address, dateOfBirth, contactNumber);
        this.patientID = patientID;
    }

    public void addCondition(String condition){
        conditions.add(condition);
    }

    public void removeCondition(String condition){
        conditions.remove(condition);
    }
    
    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public ArrayList<String> getConditions() {
        return conditions;
    }

    public void setConditions(ArrayList<String> conditions) {
        this.conditions = conditions;
    }
    
}
