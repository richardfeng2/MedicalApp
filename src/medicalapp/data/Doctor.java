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
            boolean isPatient, boolean isStaff, String address, Date dateOfBirth, String contactNumber) {

        super(staffID, isAdmin, isNurse, isDoctor, username, password, personID, firstName, lastName,
                isPatient, isStaff, address, dateOfBirth, contactNumber);
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
//        deletePerson(doctor.getStaff(getDoctor(doctorID).getStaffID()).getPersonID());
//        deleteStaff(getDoctor(doctorID).getStaffID());
    }

    public static void deleteDoctor(int doctorID) {
        Connection conn = DBConnection.getInstance().getConnection();

        try {
           // String query = "DELETE s, d, p FROM Staff AS s, Doctor AS d, Person AS p WHERE StaffID = ?";

            String query1 = "DELETE FROM Doctor WHERE doctorID = ?";
            String query2 = "DELETE FROM Staff WHERE staffID = ?";
            String query3 = "DELETE FROM Person WHERE personID = ?";

            PreparedStatement stm1 = conn.prepareStatement(query1);
//            PreparedStatement stm2 = conn.prepareStatement(query2);
//            PreparedStatement stm3 = conn.prepareStatement(query3);
//            
            stm1.setInt(1, doctorID);
//            stm2.setInt(1, getDoctor(doctorID).getStaffID());
//            stm3.setInt(1, getStaff(getDoctor(doctorID).getStaffID()).getPersonID());
//            

            stm1.executeUpdate();
//            stm2.executeUpdate();
//            stm3.executeUpdate();
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

            ResultSet doctorResults = stm.executeQuery();
            while (doctorResults.next()) {
                int doctorID = doctorResults.getInt("doctorID");
                int staffID = doctorResults.getInt("staffID");
                int personID = doctorResults.getInt("personID");
                String firstName = doctorResults.getString("firstName");
                String lastName = doctorResults.getString("lastName");
                boolean isPatient = doctorResults.getBoolean("isPatient");
                boolean isStaff = doctorResults.getBoolean("isStaff");
                boolean isAdmin = doctorResults.getBoolean("isAdmin");
                boolean isNurse = doctorResults.getBoolean("isNurse");
                boolean isDoctor = doctorResults.getBoolean("isDoctor");
                String address = doctorResults.getString("address");
                Date dateOfBirth = doctorResults.getDate("dateOfBirth");
                String contactNumber = doctorResults.getString("contactNumber");
                String username = doctorResults.getString("username");
                String password = doctorResults.getString("password");
                doctor = new Doctor(doctorID, staffID, isAdmin, isNurse, isDoctor, username, password,
                        personID, firstName, lastName, isPatient, isStaff, address, dateOfBirth, contactNumber);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Person.class
                    .getName()).log(Level.SEVERE, "Error getting doctor doctorID = " + id, ex);
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
