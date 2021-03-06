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
public class TestResult {

    private int testResultID;
    private double weight; //kg's
    private String bloodPressure; // "x/y"
    private int heartRate; //bpm (beats per min)
    private double oxygenLevel; // mmHg
    private double lungCapacity; //%
    private double oxygenUptake; //mL/min
    private int appointmentID;
    private boolean expired;
    private boolean locked;

    public TestResult(int testResultID, double weight, String bloodPressure, int heartRate,
            double oxygenLevel, double lungCapacity, double oxygenUptake, int appointmentID,
            boolean expired, boolean locked) {
        this.testResultID = testResultID;
        this.weight = weight;
        this.bloodPressure = bloodPressure;
        this.heartRate = heartRate;
        this.oxygenLevel = oxygenLevel;
        this.lungCapacity = lungCapacity;
        this.oxygenUptake = oxygenUptake;
        this.appointmentID = appointmentID;
        this.expired = expired;
        this.locked = locked;
    }

    public static void insertTestResult(int appointmentID, double weight, String bloodPressure,
            int heartRate, double oxygenLevel, double lungCapacity, double oxygenUptake) {
        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String query = "INSERT INTO TestResult VALUES (?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement stm = conn.prepareStatement(query);
            
            int nextID = getNextID();
            stm.setInt(1, nextID);
            stm.setDouble(2, weight);
            stm.setString(3, bloodPressure);
            stm.setInt(4, heartRate);
            stm.setDouble(5, oxygenLevel);
            stm.setDouble(6, lungCapacity);
            stm.setDouble(7, oxygenUptake);
            stm.setInt(8, appointmentID);
            stm.setBoolean(9, false);
            stm.setBoolean(10, false);

            stm.executeUpdate();

            System.out.println("TestResultID: " + nextID + " inserted successfully.");
        } catch (SQLException ex) {
            Logger.getLogger(TestResult.class.getName()).log(Level.SEVERE, "Error inserting test result", ex);
        }
    }

    public static void insertTestResult(TestResult testResult) {
        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String query = "INSERT INTO TestResult VALUES (?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, getNextID());
            stm.setDouble(2, testResult.getWeight());
            stm.setString(3, testResult.getBloodPressure());
            stm.setInt(4, testResult.getHeartRate());
            stm.setDouble(5, testResult.getOxygenLevel());
            stm.setDouble(6, testResult.getLungCapacity());
            stm.setDouble(7, testResult.getOxygenUptake());
            stm.setInt(8, testResult.getAppointmentID());
            stm.setBoolean(9, false);
            stm.setBoolean(10, false);

            stm.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(TestResult.class.getName()).log(Level.SEVERE, "Error inserting testResult", ex);
        }
    }

    public static void updateTestResult(TestResult testResult) {
        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String query = "UPDATE TestResult "
                    + "SET weight = ?, bloodPressure = ?, heartRate= ?, oxygenLevel = ?,"
                    + "lungCapacity = ?, oxygenUptake = ?, appointmentID = ?, locked = ?"
                    + "WHERE testResultID = ?";

            PreparedStatement stm = conn.prepareStatement(query);

            stm.setDouble(1, testResult.getWeight());
            stm.setString(2, testResult.getBloodPressure());
            stm.setInt(3, testResult.getHeartRate());
            stm.setDouble(4, testResult.getOxygenLevel());
            stm.setDouble(5, testResult.getLungCapacity());
            stm.setDouble(6, testResult.getOxygenUptake());
            stm.setInt(7, testResult.getAppointmentID());
            stm.setBoolean(8, testResult.isLocked());
            stm.setInt(9, testResult.getTestResultID());

            stm.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, "Error updating testResult", ex);
        }
    }

    public static void deleteTestResult(TestResult testResult) {
        deleteTestResult(testResult.getTestResultID());
    }

    public static void deleteTestResult(int testResultID) {
        Connection conn = DBConnection.getInstance().getConnection();

        try {
            String query = "UPDATE TestResult SET expired = true WHERE testResultID = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, testResultID);

            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TestResult.class.getName()).log(Level.SEVERE, "Error deleting testResult testResultID="
                    + testResultID, ex);
        }
    }

    public static TestResult getTestResult(int id) {
        Connection conn = DBConnection.getInstance().getConnection();

        TestResult testResult = null;
        try {
            String query = "SELECT * FROM TestResult WHERE testResultID = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, id);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int testResultID = rs.getInt("testResultID");
                double weight = rs.getDouble("weight");
                String bloodPressure = rs.getString("bloodPressure");
                int heartRate = rs.getInt("heartRate");
                double oxygenLevel = rs.getDouble("oxygenLevel");
                double lungCapacity = rs.getDouble("lungCapacity");
                double oxygenUptake = rs.getDouble("oxygenUptake");
                int appointmentID = rs.getInt("appointmentID");
                Boolean expired = rs.getBoolean("expired");
                Boolean locked = rs.getBoolean("locked");

                testResult = new TestResult(testResultID, weight, bloodPressure, heartRate,
                        oxygenLevel, lungCapacity, oxygenUptake, appointmentID, expired, locked);

                System.out.println("TestResultID\tAppointmentID\tWeight\tBlood Pressure\tHeart Rate\tOxygen Level"
                        + "\tLung Capacity\tOxygen Uptake\tTaken on");
                System.out.println(testResultID + "\t\t" + appointmentID + "\t\t" + weight + "kg\t" + 
                        bloodPressure + " mmHg\t" + heartRate + " pbm\t\t" + oxygenLevel + " mmHg\t" + lungCapacity + "%\t\t"
                        + oxygenUptake + " mL/min\t" + Appointment.getAppointment(appointmentID).getDate());
            }
        } catch (SQLException ex) {
            Logger.getLogger(TestResult.class.getName()).log(Level.SEVERE, "Error getting testResult ID = " + id, ex);
        }
        return testResult;
    }

    public static int getNextID() {
        int nextID = 0;
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            String query = "SELECT (MAX(testResultID) + 1) AS nextID FROM TestResult";
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(query);
            if (rs.next()) {
                nextID = rs.getInt("nextID");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (nextID == 0) {
            nextID++;
        }
        return (nextID);
    }

    public int getTestResultID() {
        return testResultID;
    }

    public void setTestResultID(int testResultID) {
        this.testResultID = testResultID;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    public double getOxygenLevel() {
        return oxygenLevel;
    }

    public void setOxygenLevel(double oxygenLevel) {
        this.oxygenLevel = oxygenLevel;
    }

    public double getLungCapacity() {
        return lungCapacity;
    }

    public void setLungCapacity(double lungCapacity) {
        this.lungCapacity = lungCapacity;
    }

    public double getOxygenUptake() {
        return oxygenUptake;
    }

    public void setOxygenUptake(double oxygenUptake) {
        this.oxygenUptake = oxygenUptake;
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
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
