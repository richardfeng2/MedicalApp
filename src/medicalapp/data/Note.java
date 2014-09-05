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

    public Note(int noteID, int appointmentID, String text) {
        this.noteID = noteID;
        this.appointmentID = appointmentID;
        this.text = text;
    }

    public static void insertNote(Note note) {
        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String query = "INSERT INTO Note VALUES (?,?, ?)";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, getNextID());
            stm.setInt(2, note.getAppointmentID());
            stm.setString(3, note.getText());

            stm.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Note.class.getName()).log(Level.SEVERE, "Error inserting note", ex);
        }
    }

    public static void updateNote(Note note) {
        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String query = "UPDATE Note SET Text = ? WHERE noteID = ?";

            PreparedStatement stm = conn.prepareStatement(query);

            stm.setString(1, note.getText());
            stm.setInt(2, note.getNoteID());

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
            String query = "DELETE FROM Note WHERE NoteID = ?";
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
                note = new Note(noteID, appointmentID, text);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Note.class.getName()).log(Level.SEVERE, "Error getting note noteID=" + ID, ex);
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
    
    
}
