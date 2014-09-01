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
import java.sql.Timestamp;
import java.time.Duration;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Richard Feng
 */
public class Appointment {

    private int appointmentID;
    private Date date;
    private int patientID;
    private int doctorID;
    private String description;
    private Duration duration; //TO-DO: assume appointment duration is 15 minutes
    private String referringGP;

    public Appointment(int appointmentID, Date date, int patientID, int doctorID,
            String description, Duration duration, String referringGP) {
        this.appointmentID = appointmentID;
        this.date = date;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.description = description;
        this.duration = duration;
        this.referringGP = referringGP;
    }

    public static void insertAppointment(Appointment appointment) {
        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String query = "INSERT INTO Appointment VALUES (?,?,?,?,?,?,?)";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, appointment.getAppointmentID());
            stm.setTimestamp(2, convertJavaDateToSqlTimestamp(appointment.getDate()));
            stm.setInt(3, appointment.getPatientID());
            stm.setInt(4, appointment.getDoctorID());
            stm.setString(5, appointment.getDescription());
            stm.setDouble(6, durationToDouble(appointment.getDuration()));
            stm.setString(7, appointment.getReferringGP());

            stm.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, "Error inserting appointment", ex);
        }
    }

    public static void updateAppointment(Appointment appointment) {
        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String query = "UPDATE Appointment "
                    + "SET Date = ?, description = ?, doctorID = ?, duration = ?, referringGP = ?"
                    + "WHERE appointmentID = ?";

            PreparedStatement stm = conn.prepareStatement(query);

            stm.setDate(1, convertJavaDateToSqlDate(appointment.getDate()));
            stm.setString(2, appointment.getDescription());
            stm.setInt(3, appointment.getDoctorID());
            stm.setDouble(4, durationToDouble(appointment.getDuration()));
            stm.setString(5, appointment.getReferringGP());
            stm.setInt(6, appointment.getAppointmentID());
            stm.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, "Error updating appointment", ex);
        }
    }

    public static void deleteAppointment(Appointment appointment) {
        deleteAppointment(appointment.getAppointmentID());
    }

    public static void deleteAppointment(int appointmentID) {
        Connection conn = DBConnection.getInstance().getConnection();

        try {
            String query = "DELETE FROM Appointment WHERE appointemntID = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, appointmentID);

            stm.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, "Error deleting appointment, ID=" + appointmentID, ex);
        }
    }

    public static Appointment getAppointment(int ID) {

        Appointment appointment = null;
        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String query = "SELECT * FROM Appointment WHERE AppointmentID = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, ID);

            ResultSet appointmentResults = stm.executeQuery();
            while (appointmentResults.next()) {
                int appointmentID = appointmentResults.getInt("appointmentID");
                Date date = appointmentResults.getDate("date");
                int patientID = appointmentResults.getInt("patientID");
                int doctorID = appointmentResults.getInt("doctorID");
                String description = appointmentResults.getString("description");
                //DB stores duration as a double. Convert double -> long -> Duration (minutes)
                Duration duration = doubleToDuration(appointmentResults.getDouble("duration"));
                String referringGP = appointmentResults.getString("referringGP");
                appointment = new Appointment(appointmentID, date, patientID, doctorID, description, duration, referringGP);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Appointment.class.getName()).log(Level.SEVERE, null, ex);
        }
        return appointment;
    }

    //Converts duration type to double type. Use when you want to input data in the DB.
    public static double durationToDouble(Duration duration) {
        return (double) duration.toMinutes();
    }

    //Converts doubles to duration type. Use when you want to display output to user.
    public static Duration doubleToDuration(double d) {
        return Duration.ofMinutes((long) d);
    }

    public static java.sql.Date convertJavaDateToSqlDate(java.util.Date date) {
        return new java.sql.Date(date.getTime());
    }

    public static java.sql.Timestamp convertJavaDateToSqlTimestamp(java.util.Date date) {
        //Date dt = new Date(Calendar.getInstance().getTimeInMillis()); // Your exising sql Date .
        return new Timestamp(date.getTime());
    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
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

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public String getReferringGP() {
        return referringGP;
    }

    public void setReferringGP(String referringGP) {
        this.referringGP = referringGP;
    }

}
