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
public class Doctor extends Staff {

    private int doctorID;

    public Doctor(int doctorID, int staffID, boolean isAdmin, boolean isNurse, boolean isDoctor,
            String username, String password, int personID, String firstName, String lastName,
            boolean isPatient, boolean isStaff, String address, Date dateOfBirth, String contactNumber, boolean expired) {

        super(staffID, isAdmin, isNurse, isDoctor, username, password, personID, firstName, lastName,
                isPatient, isStaff, address, dateOfBirth, contactNumber, expired);
        this.doctorID = doctorID;
    }

    public static void insertDoctor(Doctor doctor) {
        insertStaff(doctor);
        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String query = "INSERT INTO Doctor VALUES (?,?)";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, getNextID());
            stm.setInt(2, Staff.getNextID());

            stm.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Doctor.class.getName()).log(Level.SEVERE, "Error inserting doctor", ex);
        }
    }

    public static void deleteDoctor(Doctor doctor) {
        deleteDoctor(doctor.getDoctorID());
    }

    //Deletes records from Doctor, Staff, Person table.
    public static void deleteDoctor(int doctorID) {
        Connection conn = DBConnection.getInstance().getConnection();

        try {
            String query1 = "DELETE FROM Doctor WHERE doctorID = ?";
            String query2 = "DELETE FROM Staff WHERE staffID = ?";
            String query3 = "DELETE FROM Person WHERE personID = ?";

            int staffID = getDoctor(doctorID).getStaffID();
            int personID = getDoctor(doctorID).getPersonID();
            
            PreparedStatement stm = conn.prepareStatement(query1);
            stm.setInt(1, doctorID);
            stm.executeUpdate();
            
            stm = conn.prepareStatement(query2);
            stm.setInt(1, staffID);
            stm.executeUpdate();
            
            stm = conn.prepareStatement(query3);
            stm.setInt(1, personID);
            stm.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(Doctor.class.getName()).log(Level.SEVERE, "Error deleting doctor doctorID=" + doctorID, ex);
        }
    }

    public static Doctor getDoctor(int id) {
        Doctor doctor = null;
        Connection conn = DBConnection.getInstance().getConnection();
        try {
            String query = "SELECT * "
                    + " FROM Doctor AS d "
                    + " INNER JOIN Staff AS s"
                    + "     ON d.staffID = s.staffID "
                    + " INNER JOIN Person As p"
                    + "     ON s.personID = p.personID "
                    + " WHERE doctorID = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, id);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int doctorID = rs.getInt("doctorID");
                int staffID = rs.getInt("staffID");
                int personID = rs.getInt("personID");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                boolean isPatient = rs.getBoolean("isPatient");
                boolean isStaff = rs.getBoolean("isStaff");
                boolean isAdmin = rs.getBoolean("isAdmin");
                boolean isNurse = rs.getBoolean("isNurse");
                boolean isDoctor = rs.getBoolean("isDoctor");
                String address = rs.getString("address");
                Date dateOfBirth = rs.getDate("dateOfBirth");
                String contactNumber = rs.getString("contactNumber");
                String username = rs.getString("username");
                String password = rs.getString("password");
                Boolean expired = rs.getBoolean("expired");
                
                doctor = new Doctor(doctorID, staffID, isAdmin, isNurse, isDoctor, username, password,
                        personID, firstName, lastName, isPatient, isStaff, address, dateOfBirth, contactNumber, expired);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Person.class
                    .getName()).log(Level.SEVERE, "Error getting doctor doctorID = " + id, ex);
        }
        return doctor;
    }
    
    public static Doctor getDoctor(String firstName, String lastName) {
        Doctor doctor = null;
        Connection conn = DBConnection.getInstance().getConnection();
        try {
            String query = "SELECT * "
                    + " FROM Doctor AS d "
                    + " INNER JOIN Staff AS s"
                    + "     ON d.staffID = s.staffID "
                    + " INNER JOIN Person As p"
                    + "     ON s.personID = p.personID "
                    + " WHERE firstName = ?"
                    + " AND lastName = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setString(1, firstName);
            stm.setString(2, lastName);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int doctorID = rs.getInt("doctorID");
                int staffID = rs.getInt("staffID");
                int personID = rs.getInt("personID");
                boolean isPatient = rs.getBoolean("isPatient");
                boolean isStaff = rs.getBoolean("isStaff");
                boolean isAdmin = rs.getBoolean("isAdmin");
                boolean isNurse = rs.getBoolean("isNurse");
                boolean isDoctor = rs.getBoolean("isDoctor");
                String address = rs.getString("address");
                Date dateOfBirth = rs.getDate("dateOfBirth");
                String contactNumber = rs.getString("contactNumber");
                String username = rs.getString("username");
                String password = rs.getString("password");
                Boolean expired = rs.getBoolean("expired");
                
                doctor = new Doctor(doctorID, staffID, isAdmin, isNurse, isDoctor, username, password,
                        personID, firstName, lastName, isPatient, isStaff, address, dateOfBirth, contactNumber, expired);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Person.class
                    .getName()).log(Level.SEVERE, "Error getting doctor: " + firstName + " " + lastName, ex);
        }
        return doctor;
    }

    public static int getNextID() {
        int nextID = 1;
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            String query = "SELECT (MAX(doctorID) + 1) AS nextID FROM Doctor";
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(query);
            if (rs.next()) {
                nextID = rs.getInt("nextID");
                if (nextID == 0) {
                    nextID++;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (nextID);
    }

    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }
}
