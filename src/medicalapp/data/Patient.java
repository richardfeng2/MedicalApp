/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medicalapp.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Richard
 */
public class Patient extends Person {

    private int patientID;
    private String billingInfo;

    public Patient(int patientID, String billingInfo, int personID, String firstName,
            String lastName, boolean isPatient, boolean isStaff, String address, Date dateOfBirth,
            String contactNumber) {
        super(personID, firstName, lastName, isPatient, isStaff, address, dateOfBirth, contactNumber);
        this.patientID = patientID;
        this.billingInfo = billingInfo;
    }

    public static void insertPatient(Patient patient) {
        insertPerson(patient);
        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String query = "INSERT INTO Patient VALUES (?,?,?)";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, getNextID());
            stm.setInt(2, Person.getNextID());
            stm.setString(3, patient.getBillingInfo());

            stm.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Patient.class.getName()).log(Level.SEVERE, "Error updating patient", ex);
        }
    }

    public static void updatePatient(Patient patient) {
        updatePerson(patient);
        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String query = "UPDATE Patient SET billingInfo = ? WHERE patientID = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setString(1, patient.getBillingInfo());
            stm.setInt(2, patient.getPatientID());

            stm.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Patient.class.getName()).log(Level.SEVERE, "Error inserting patient", ex);
        }
    }

    public static void deletePatient(Patient patient) {
        deletePatient(patient.getPatientID());
        deletePerson(patient.getPersonID());
    }

    public static void deletePatient(int patientID) {
        Connection conn = DBConnection.getInstance().getConnection();

        try {
            String query = "DELETE FROM Patient WHERE PatientID = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, patientID);

            stm.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(Patient.class.getName()).log(Level.SEVERE, "Error deleting patient patientID = " + patientID, ex);
        }
    }

    public static Patient getPatient(int id) {
        Connection conn = DBConnection.getInstance().getConnection();

        Patient patient = null;
        try {
            String query = "SELECT * FROM Patient INNER JOIN Person "
                    + "ON Patient.personID = Person.personID WHERE patientID = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, id);

            ResultSet patientResults = stm.executeQuery();
            while (patientResults.next()) {
                int patientID = patientResults.getInt("patientID");
                int personID = patientResults.getInt("personID");
                String firstName = patientResults.getString("firstName");
                String lastName = patientResults.getString("lastName");
                boolean isPatient = patientResults.getBoolean("isPatient");
                boolean isStaff = patientResults.getBoolean("isStaff");
                String address = patientResults.getString("address");
                Date dateOfBirth = patientResults.getDate("dateOfBirth");
                String contactNumber = patientResults.getString("contactNumber");
                String billingInfo = patientResults.getString("billingInfo");

                patient = new Patient(patientID, billingInfo, personID, firstName, lastName, isPatient,
                        isStaff, address, dateOfBirth, contactNumber);

            }
        } catch (SQLException ex) {
            Logger.getLogger(Person.class
                    .getName()).log(Level.SEVERE, "Error getting patient patientID = " + id, ex);
        }
        return patient;
    }

    //When inserting new records, increment the maximum ID by 1.
    public static int getNextID() {
        int nextID = 0;
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            String query = "SELECT (MAX(patientID) + 1) AS nextID FROM Patient";
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(query);
            if (rs.next()) {
                nextID = rs.getInt("nextID");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Patient.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (nextID == 0) {
            nextID++;
        }
        return (nextID);
    }

    public String getBillingInfo() {
        return billingInfo;
    }

    public void setBillingInfo(String billingInfo) {
        this.billingInfo = billingInfo;
    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }
}
