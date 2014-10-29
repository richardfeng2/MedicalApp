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
public class Referral {

    private int referralID;
    private int patientID;
    private String gpName;
    private String contact;
    private String purpose;
    private Date date;

    public Referral(int referralID, int patientID, String gpName, String contact, String purpose, Date date) {
        this.referralID = referralID;
        this.patientID = patientID;
        this.gpName = gpName;
        this.contact = contact;
        this.purpose = purpose;
        this.date = date;
    }

    public static void insertReferral(Referral referral) {
        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String query = "INSERT INTO Referral VALUES (?,?,?,?,?,?)";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, getNextID());
            stm.setInt(2, referral.getPatientID());
            stm.setString(3, referral.getGpName());
            stm.setString(4, referral.getContact());
            stm.setString(5, referral.getPurpose());
            Date date = Calendar.getInstance().getTime();
            stm.setTimestamp(6, convertJavaDateToSqlTimestamp(date));
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Referral.class.getName()).log(Level.SEVERE, "Error inserting referral", ex);
        }
    }

    public static Referral getReferral(int ID) {
        Connection conn = DBConnection.getInstance().getConnection();

        Referral referral = null;
        try {
            String query = "SELECT * FROM Referral WHERE ReferralID = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, ID);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int referralID = rs.getInt("referralID");
                int patientID = rs.getInt("patientID");
                String gpName = rs.getString("gpName");
                String contact = rs.getString("contact");
                String purpose = rs.getString("purpose");
                Date date = rs.getTimestamp("date");
                referral = new Referral(referralID, patientID, gpName, contact, purpose, date);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Referral.class.getName()).log(Level.SEVERE, "Error getting referral referralID=" + ID, ex);
        }
        return referral;
    }

    public static Referral getReferralByPatient(int appID) {
        Connection conn = DBConnection.getInstance().getConnection();

        Referral referral = null;
        try {
            String query = "SELECT * FROM Referral WHERE patientID = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, appID);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int referralID = rs.getInt("referralID");
                referral = getReferral(referralID);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Referral.class.getName()).log(Level.SEVERE, "Error getting referral appID=" + appID, ex);
        }
        return referral;
    }

    public static int getNextID() {
        int nextID = 0;
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            String query = "SELECT (MAX(referralID) + 1) AS nextID FROM Referral";
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(query);
            if (rs.next()) {
                nextID = rs.getInt("nextID");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Referral.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (nextID == 0) {
            nextID++;
        }
        return (nextID);
    }

    public int getReferralID() {
        return referralID;
    }

    public void setReferralID(int referralID) {
        this.referralID = referralID;
    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public String getGpName() {
        return gpName;
    }

    public void setGpName(String gpName) {
        this.gpName = gpName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
    
    

}
