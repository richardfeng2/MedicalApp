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

/**
 *
 * @author Richard Feng
 */
public class Appointment {

    private int appointmentID;
    private Date date;
    private int patientID;
    private int doctorID;
    private String purpose;
    private Duration duration; //assume appointment duration is 15 minutes
    private String referringGP;
    private boolean expired; //back-end boolean to keep track of cancelled appointments
    private boolean finished; //back-end variable to check if appointment is finished
    private String doctorName;

    public Appointment(int appointmentID, Date date, int patientID, int doctorID,
            String purpose, Duration duration, String referringGP, boolean expired, boolean finished) {
        this.appointmentID = appointmentID;
        this.date = date;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.purpose = purpose;
        this.duration = duration;
        this.referringGP = referringGP;
        this.expired = expired;
    }

    public static void insertAppointment(Appointment appointment) {
        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String query = "INSERT INTO Appointment VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, getNextID());
            stm.setTimestamp(2, convertJavaDateToSqlTimestamp(appointment.getDate()));
            stm.setInt(3, appointment.getPatientID());
            stm.setInt(4, appointment.getDoctorID());
            stm.setString(5, appointment.getPurpose());
            stm.setDouble(6, durationToDouble(appointment.getDuration()));
            stm.setString(7, appointment.getReferringGP());
            stm.setBoolean(8, false);
            stm.setBoolean(9, false);

            stm.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, "Error inserting appointment", ex);
        }
    }

    public static void insertAppointment(Patient patient, Doctor doctor, Date date, int duration, String purpose) {

        Appointment appointment = new Appointment(1, date, patient.getPatientID(),
                doctor.getDoctorID(), purpose, Duration.ofMinutes(duration), "", false, false); 

        insertAppointment(appointment);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        System.out.println("Appointment Created");
        System.out.println("Patient\t\tDoctor\t\tDate");
        System.out.println(patient.getFirstName() + " " + patient.getLastName() + " "
                + "\tDr. " + doctor.getLastName() + "\t" + dateFormat.format(date));
    }

    public static void updateAppointment(Appointment appointment) {
        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String query = "UPDATE Appointment "
                    + "SET Date = ?, purpose = ?, doctorID = ?, duration = ?, referringGP = ?"
                    + " finished = ? "
                    + "WHERE appointmentID = ?";

            PreparedStatement stm = conn.prepareStatement(query);

            stm.setDate(1, convertJavaDateToSqlDate(appointment.getDate()));
            stm.setString(2, appointment.getPurpose());
            stm.setInt(3, appointment.getDoctorID());
            stm.setDouble(4, durationToDouble(appointment.getDuration()));
            stm.setString(5, appointment.getReferringGP());
            stm.setBoolean(6, appointment.isFinished());
            stm.setInt(7, appointment.getAppointmentID());
            stm.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, "Error updating appointment", ex);
        }
    }

    public static void deleteAppointment(Appointment appointment) {
        deleteAppointment(appointment.getAppointmentID());
    }

    //Expired boolean true reflects deletion.
    public static void deleteAppointment(int appointmentID) {
        Connection conn = DBConnection.getInstance().getConnection();

        try {
            String query = "UDPATE Appointment SET expired = false WHERE appointemntID = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, appointmentID);

            stm.executeUpdate();
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

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int appointmentID = rs.getInt("appointmentID");
                Date date = rs.getTimestamp("date");
                int patientID = rs.getInt("patientID");
                int doctorID = rs.getInt("doctorID");
                String purpose = rs.getString("purpose");
                Duration duration = doubleToDuration(rs.getDouble("duration")); //DB stores duration as a double. Convert double -> long -> Duration (minutes)
                String referringGP = rs.getString("referringGP");
                Boolean expired = rs.getBoolean("expired");
                Boolean finished = rs.getBoolean("finished");
                appointment = new Appointment(appointmentID, date, patientID, doctorID, purpose,
                        duration, referringGP, expired, finished);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Appointment.class.getName()).log(Level.SEVERE, null, ex);
        }
        return appointment;
    }

    //Get a patient's appointment upon checking in.
    public static Appointment getAppointment(String firstName, String lastName, String address) {
        Appointment appointment = null;
        Connection conn = DBConnection.getInstance().getConnection();
        try {
            String query = "SELECT * FROM Patient "
                    + "INNER JOIN Appointment "
                    + "ON Patient.patientID = Appointment.patientID "
                    + "INNER JOIN Doctor "
                    + "ON Appointment.doctorID = Doctor.doctorID "
                    + "WHERE Appointment.patientID = ? AND expired <> true "
                    + "AND Appointment.finished <> true";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, Patient.getPatient(firstName, lastName, address).getPatientID());

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int appointmentID = rs.getInt("appointmentID");
                appointment = getAppointment(appointmentID);

                DateFormat timeFormat = new SimpleDateFormat("HH:mm");
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
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

    //Converts doubles to duration type. Use for appointment scheduling.
    public static Duration doubleToDuration(double d) {
        return Duration.ofMinutes((long) d);
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
            String query = "SELECT (MAX(appointmentID) + 1) AS nextID FROM Appointment";
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
            String query = "SELECT (MAX(appointmentID)) AS maxID FROM Appointment";
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

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
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

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

}
