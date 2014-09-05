/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medicalapp.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Blob;
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
public class Document {

    private int documentID;
    private int appointmentID;
    private String title;
    private Blob file;
    private boolean isClinical;

    public Document(int documentID, int appointmentID, String title, Blob file, boolean isClinical) {
        this.documentID = documentID;
        this.appointmentID = appointmentID;
        this.title = title;
        this.file = file;
        this.isClinical = isClinical;
    }

    public static void insertDocument(Document document, String filePath) {
        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String query = "INSERT INTO Document VALUES (?,?,?,?,?)";

            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, getNextID());
            stm.setInt(2, document.getAppointmentID());
            stm.setString(3, document.getTitle());

            InputStream inputStream = null;
            try {
                inputStream = new FileInputStream(new File(filePath));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Document.class.getName()).log(Level.SEVERE, "File not found at " + filePath, ex);
            }
            stm.setBlob(4, inputStream);
            stm.setBoolean(5, document.isClinical());

            stm.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Document.class.getName()).log(Level.SEVERE, "Error inserting document", ex);
        }
    }

    public static void updateDocument(Document document, String filePath) {

        Connection conn = DBConnection.getInstance().getConnection();

        //Checks if user is updating a file. If not, only updates other columns.
        if (filePath != null || !filePath.isEmpty()) {
            try {
                String query = "UPDATE Document "
                        + "SET appointmentID = ?, title = ?, "
                        + "isClinical = ?, file = ? "
                        + "WHERE documentID = ?";
                
                InputStream inputStream = null;
                try {
                    inputStream = new FileInputStream(new File(filePath));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Document.class.getName()).log(Level.SEVERE, "File not found at " + filePath, ex);
                }
                
                PreparedStatement stm = conn.prepareStatement(query);
                
                stm.setInt(1, document.getAppointmentID());
                stm.setString(2, document.getTitle());
                stm.setBoolean(3, document.isClinical());
                stm.setBlob(4, inputStream);
                stm.setInt(5, document.getDocumentID());
                
                stm.executeUpdate();
                
            } catch (SQLException ex) {
                Logger.getLogger(Document.class.getName()).log(Level.SEVERE, null,ex);
            }
        } else {
            try {
                String query = "UPDATE Document "
                        + "SET appointmentID = ?, title = ?, isClinical = ? "
                        + "WHERE documentID = ?";
                
                PreparedStatement stm = conn.prepareStatement(query);
                
                stm.setInt(1, document.getAppointmentID());
                stm.setString(2, document.getTitle());
                stm.setBoolean(3, document.isClinical());
                stm.setInt(4, document.getDocumentID());
                
                stm.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(Document.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void deleteDocument(Document document) {
        deleteDocument(document.getDocumentID());
    }

    public static void deleteDocument(int documentID) {
        Connection conn = DBConnection.getInstance().getConnection();

        try {
            String query = "DELETE FROM Document WHERE DocumentID = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, documentID);

            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Document.class.getName()).log(Level.SEVERE, "Error deleting document documentID=" + documentID, ex);
        }
    }

    public static Document getDocument(int id) {

        Connection conn = DBConnection.getInstance().getConnection();

        Document document = null;
        try {
            String query = "SELECT * FROM Document WHERE DocumentID = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, id);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int documentID = rs.getInt("documentID");
                int appointmentID = rs.getInt("appointmentID");
                String title = rs.getString("title");
                Blob file = rs.getBlob("file");
                Boolean isClinical = rs.getBoolean("file");

                document = new Document(documentID, appointmentID, title, file, isClinical);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Document.class.getName()).log(Level.SEVERE, "Error getting document documentID=" + id, ex);
        }
        return document;
    }

    public static int getNextID() {
        int nextID = 0;
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            String query = "SELECT (MAX(documentID) + 1) AS nextID FROM Document";
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(query);
            if (rs.next()) {
                nextID = rs.getInt("nextID");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Document.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (nextID == 0) {
            nextID++;
        }
        return (nextID);
    }

    public boolean isClinical() {
        return isClinical;
    }

    public void setIsClinical(boolean isClinical) {
        this.isClinical = isClinical;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDocumentID() {
        return documentID;
    }

    public void setDocumentID(int documentID) {
        this.documentID = documentID;
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public Blob getFile() {
        return file;
    }

    public void setFile(Blob file) {
        this.file = file;
    }

}
