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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Richard
 */
public class Note {     
    private int noteID;
    private int appointmentID;
    private String text;
    private boolean expired;
    private boolean locked;

    public Note(int noteID, int appointmentID, String text, boolean expired, boolean locked) {
        this.noteID = noteID;
        this.appointmentID = appointmentID;
        this.text = text;
        this.expired = expired;
        this.locked = locked;
    }

    public static void insertNote(Note note) {
        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String query = "INSERT INTO Note VALUES (?,?,?,?,?)";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, getNextID());
            stm.setInt(2, note.getAppointmentID());
            stm.setString(3, note.getText());
            stm.setBoolean(4, false);
            stm.setBoolean(5, false);

            stm.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Note.class.getName()).log(Level.SEVERE, "Error inserting note", ex);
        }
    }

    public static void updateNote(Note note) {
        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String query = "UPDATE Note SET text = ?, locked = ? WHERE noteID = ?";

            PreparedStatement stm = conn.prepareStatement(query);

            stm.setString(1, note.getText());
            stm.setBoolean(2, note.isLocked());
            stm.setInt(3, note.getNoteID());

            stm.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Note.class.getName()).log(Level.SEVERE, "Error updating note", ex);
        }
    }

    public static void deleteNote(Note note) {
        deleteNote(note.getNoteID());
    }

    public static void deleteNote(int noteID) {
        Connection conn = DBConnection.getInstance().getConnection();

        try {
            String query = "UPDATE Note SET expired = true WHERE NoteID = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, noteID);

            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Note.class.getName()).log(Level.SEVERE, "Error deleting note noteID=" + noteID, ex);
        }
    }

    public static Note getNote(int ID) {
        Connection conn = DBConnection.getInstance().getConnection();

        Note note = null;
        try {
            String query = "SELECT * FROM Note WHERE NoteID = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, ID);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int noteID = rs.getInt("noteID");
                int appointmentID = rs.getInt("appointmentID");
                String text = rs.getString("text");
                Boolean expired = rs.getBoolean("expired");
                Boolean locked = rs.getBoolean("locked");

                note = new Note(noteID, appointmentID, text, expired, locked);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(Note.class.getName()).log(Level.SEVERE, "Error getting note noteID=" + ID, ex);
        }
        return note;
    }
    public static Note getNoteByAppointment(int appID) {
        Connection conn = DBConnection.getInstance().getConnection();

        Note note = null;
        try {
            String query = "SELECT * FROM Note WHERE appointmentID = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, appID);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int noteID = rs.getInt("noteID");
                note = getNote(noteID);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Note.class.getName()).log(Level.SEVERE, "Error getting note appID=" + appID, ex);
        }
        return note;
    }

    //When inserting new records, increment the maximum ID by 1.
    public static int getNextID() {
        int nextID = 0;
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            String query = "SELECT (MAX(noteID) + 1) AS nextID FROM Note";
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(query);
            if (rs.next()) {
                nextID = rs.getInt("nextID");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Note.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (nextID == 0) {
            nextID++;
        }
        return (nextID);
    }
    
    public int getNoteID() {
        return noteID;
    }

    public void setNoteID(int noteID) {
        this.noteID = noteID;
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }
    
    
}
