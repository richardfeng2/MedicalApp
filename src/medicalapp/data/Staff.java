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
public class Staff extends Person {

    private int staffID;
    //Below booleans are used to specify data access privileges.
    private boolean isAdmin;
    private boolean isNurse;
    private boolean isDoctor;
    private String username;
    private String password;

    public Staff(int staffID, boolean isAdmin, boolean isNurse, boolean isDoctor, String username,
            String password, int personID, String firstName, String lastName,
            boolean isPatient, boolean isStaff, String address, Date dateOfBirth, String contactNumber) {
        super(personID, firstName, lastName, isPatient, isStaff, address, dateOfBirth, contactNumber);
        this.staffID = staffID;
        this.isAdmin = isAdmin;
        this.isNurse = isNurse;
        this.isDoctor = isDoctor;
        this.username = username;
        this.password = password;
    }

    public static void insertStaff(Staff staff) {
        insertPerson(staff);
        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String query = "INSERT INTO Staff VALUES (?,?,?,?,?,?,?)";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, getNextID());
            stm.setInt(2, staff.getPersonID());
            stm.setString(3, staff.getUsername());
            stm.setString(4, staff.getPassword());
            stm.setBoolean(5, staff.isAdmin());
            stm.setBoolean(6, staff.isNurse());
            stm.setBoolean(7, staff.isDoctor());

            stm.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Staff.class.getName()).log(Level.SEVERE, "Error inserting staff", ex);
        }
    }

    public static void updateStaff(Staff staff) {
        updatePerson(staff);
        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String query = "UPDATE Staff SET isAdmin= ?, isNurse=?, isDoctor? WHERE staffID = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setBoolean(1, staff.isAdmin());
            stm.setBoolean(2, staff.isNurse());
            stm.setBoolean(3, staff.isDoctor());
            stm.setInt(3, staff.getStaffID());

            stm.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Staff.class.getName()).log(Level.SEVERE, "Error updating staff", ex);
        }
    }

    public static void deleteStaff(Staff staff) {
        deleteStaff(staff.getStaffID());
    }

    public static void deleteStaff(int staffID) {
        Connection conn = DBConnection.getInstance().getConnection();

        try {
            //Deleting Staff record does not delete Person record.
            String query = "DELETE FROM Staff WHERE StaffID = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, staffID);

            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Staff.class.getName()).log(Level.SEVERE, "Error deleting staff staffID=" + staffID, ex);
        }
    }

    public static Staff getStaff(int id) {
        Connection conn = DBConnection.getInstance().getConnection();

        Staff staff = null;
        try {
            String query = "SELECT * FROM Staff INNER JOIN Person "
                    + " ON Staff.personID = Person.personID"
                    + " WHERE staffID = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, id);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int staffID = rs.getInt("staffID");
                boolean isAdmin = rs.getBoolean("isAdmin");
                boolean isNurse = rs.getBoolean("isNurse");
                boolean isDoctor = rs.getBoolean("isDoctor");
                String username = rs.getString("username");
                String password = rs.getString("password");
                int personID = rs.getInt("personID");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                boolean isPatient = rs.getBoolean("isPatient");
                boolean isStaff = rs.getBoolean("isStaff");
                String address = rs.getString("address");
                Date dateOfBirth = rs.getDate("dateOfBirth");
                String contactNumber = rs.getString("contactNumber");

                staff = new Staff(staffID, isAdmin, isNurse, isDoctor, username,
                        password, personID, firstName, lastName, isPatient, isStaff, address, dateOfBirth, contactNumber);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, "Error getting staff staffID = " + id, ex);
        }
        return staff;
    }

    public static int getNextID() {
        int nextID = 0;
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            String query = "SELECT (MAX(staffID) + 1) AS nextID FROM Staff";
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(query);
            if (rs.next()) {
                nextID = rs.getInt("nextID");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (nextID == 0 ){
            nextID++;
        }
        return (nextID);
    }

    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public boolean isNurse() {
        return isNurse;
    }

    public void setIsNurse(boolean isNurse) {
        this.isNurse = isNurse;
    }

    public boolean isDoctor() {
        return isDoctor;
    }

    public void setIsDoctor(boolean isDoctor) {
        this.isDoctor = isDoctor;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
