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
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import static medicalapp.data.Appointment.convertJavaDateToSqlTimestamp;

/**
 *
 * @author Richard
 */
public class Addendum {

    private int addendumID;
    private int noteID;
    private String text;
    private Date date;
    private int staffID;

    public Addendum(int addendumID, int noteID, String text, Date date, int staffID) {
        this.addendumID = addendumID;
        this.noteID = noteID;
        this.text = text;
        this.date = date;
        this.staffID = staffID;
    }

    public static void insertAddendum(Addendum addendum) {
        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String query = "INSERT INTO Addendum VALUES (?,?,?,?,?)";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, getNextID());
            stm.setInt(2, addendum.getNoteID());
            stm.setString(3, addendum.getText());
            Date date = Calendar.getInstance().getTime();
            stm.setTimestamp(4, convertJavaDateToSqlTimestamp(date));
            stm.setInt(5, addendum.getStaffID());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Addendum.class.getName()).log(Level.SEVERE, "Error inserting addendum", ex);
        }
    }

    public static Addendum getAddendum(int ID) {
        Connection conn = DBConnection.getInstance().getConnection();

        Addendum addendum = null;
        try {
            String query = "SELECT * FROM Addendum WHERE AddendumID = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, ID);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int addendumID = rs.getInt("addendumID");
                int noteID = rs.getInt("noteID");
                String text = rs.getString("text");
                Date date = rs.getTimestamp("date");
                int staffID = rs.getInt("staffID");
                addendum = new Addendum(addendumID, noteID, text, date, staffID);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Addendum.class.getName()).log(Level.SEVERE, "Error getting addendum addendumID=" + ID, ex);
        }
        return addendum;
    }

    public static Addendum getAddendumByNote(int appID) {
        Connection conn = DBConnection.getInstance().getConnection();

        Addendum addendum = null;
        try {
            String query = "SELECT * FROM Addendum WHERE noteID = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, appID);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int addendumID = rs.getInt("addendumID");
                addendum = getAddendum(addendumID);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Addendum.class.getName()).log(Level.SEVERE, "Error getting addendum appID=" + appID, ex);
        }
        return addendum;
    }

    public static int getNextID() {
        int nextID = 0;
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            String query = "SELECT (MAX(addendumID) + 1) AS nextID FROM Addendum";
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(query);
            if (rs.next()) {
                nextID = rs.getInt("nextID");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Addendum.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (nextID == 0) {
            nextID++;
        }
        return (nextID);
    }

    public int getAddendumID() {
        return addendumID;
    }

    public void setAddendumID(int addendumID) {
        this.addendumID = addendumID;
    }

    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    public int getNoteID() {
        return noteID;
    }

    public void setNoteID(int noteID) {
        this.noteID = noteID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
