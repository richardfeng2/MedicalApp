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
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import medicalapp.guimain.GuiMainController;

/**
 *
 * @author Richard Feng
 */
public class ChangeLog {

    private int ChangeLogID;
    private Date date;
    private String type;
    private String description;
    private int staffID;

    public ChangeLog(int ChangeLogID, Date date, String type, String description, int staffID) {
        this.ChangeLogID = ChangeLogID;
        this.date = date;
        this.type = type;
        this.description = description;
        this.staffID = staffID;
    }

    public static void insertChangeLog(ChangeLog changeLog) {
        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String query = "INSERT INTO ChangeLog VALUES (?,?,?,?,?)";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, getNextID());
            stm.setTimestamp(2, convertJavaDateToSqlTimestamp(changeLog.getDate()));
            stm.setString(3, changeLog.getType());
            stm.setString(4, changeLog.getDescription());
            stm.setInt(5, changeLog.getStaffID());

            stm.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, "Error inserting changeLog", ex);
        }
    }

    public static ChangeLog getChangeLog(int ID) {

        ChangeLog changeLog = null;
        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String query = "SELECT * FROM ChangeLog WHERE ChangeLogID = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, ID);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int changeLogID = rs.getInt("changeLogID");
                Date date = rs.getTimestamp("date");
                String description = rs.getString("description");
                String type = rs.getString("type");
                int staffID = rs.getInt("staffID");
                changeLog = new ChangeLog(changeLogID, date, type, description, staffID);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ChangeLog.class.getName()).log(Level.SEVERE, null, ex);
        }
        return changeLog;
    }

    public static java.sql.Date convertJavaDateToSqlDate(java.util.Date date) {
        return new java.sql.Date(date.getTime());
    }

    public static java.util.Date convertSqlDateToJavaDate(java.sql.Date date) {
        return new java.util.Date(date.getTime());
    }

    //Store java date as a timestamp on DB. The reason being sql date doesnt have time.
    public static java.sql.Timestamp convertJavaDateToSqlTimestamp(java.util.Date date) {
        //Date dt = new Date(Calendar.getInstance().getTimeInMillis()); // Your exising sql Date .
        return new Timestamp(date.getTime());
    }

    //generate next PK
    public static int getNextID() {
        int nextID = 0;
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            String query = "SELECT (MAX(changeLogID) + 1) AS nextID FROM ChangeLog";
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

    public static int getMaxID() {
        int maxID = 1;
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            String query = "SELECT (MAX(changeLogID)) AS maxID FROM ChangeLog";
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(query);
            if (rs.next()) {
                maxID = rs.getInt("maxID");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
        }
        return maxID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getChangeLogID() {
        return ChangeLogID;
    }

    public void setChangeLogID(int ChangeLogID) {
        this.ChangeLogID = ChangeLogID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

}
