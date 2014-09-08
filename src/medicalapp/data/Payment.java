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
public class Payment {

    private int paymentID;
    private int appointmentID;
    private String service;
    private double price;
    private boolean isPaid;
    private Date dateIssued;
    private Date datePaid;
    private boolean expired;
    private boolean locked;

    public Payment(int paymentID, int appointmentID, String service, double price,
            boolean isPaid, Date dateIssued, Date datePaid, boolean expired, boolean locked) {
        this.paymentID = paymentID;
        this.appointmentID = appointmentID;
        this.service = service;
        this.price = price;
        this.isPaid = isPaid;
        this.dateIssued = dateIssued;
        this.datePaid = datePaid;
        this.expired = expired;
        this.locked = locked;
    }

    public static void insertPayment(Payment payment) {
        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String query = "INSERT INTO Payment VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, getNextID());
            stm.setInt(2, payment.getAppointmentID());
            stm.setString(3, payment.getService());
            stm.setDouble(4, payment.getPrice());
            stm.setBoolean(5, payment.isPaid());
            stm.setDate(6, convertJavaDateToSqlDate(payment.getDateIssued()));
            stm.setDate(7, convertJavaDateToSqlDate(payment.getDatePaid()));
            stm.setBoolean(8, false);
            stm.setBoolean(9, false);

            stm.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Payment.class.getName()).log(Level.SEVERE, "Error inserting payment", ex);
        }
    }

    //convert java and sql date compatability
    public static void updatePayment(Payment payment) {
        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String query = "UPDATE Payment"
                    + " SET appointmentID = ?, service = ?, price = ?, isPaid= ?, "
                    + " dateIssued = ?, datePaid = ?, locked = ?"
                    + " WHERE paymentID = ?";

            PreparedStatement stm = conn.prepareStatement(query);

            stm.setInt(1, payment.getAppointmentID());
            stm.setString(2, payment.getService());
            stm.setDouble(3, payment.getPrice());
            stm.setBoolean(4, payment.isPaid());
            stm.setDate(5, convertJavaDateToSqlDate(payment.getDateIssued()));
            stm.setDate(6, convertJavaDateToSqlDate(payment.getDatePaid()));
            stm.setInt(8, payment.getPaymentID());
            stm.setBoolean(7, payment.isLocked());

            stm.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Payment.class.getName()).log(Level.SEVERE, "Error updating payment", ex);
        }

    }

    public static void deletePayment(Payment payment) {
        deletePayment(payment.getPaymentID());
    }

    public static void deletePayment(int paymentID) {
        Connection conn = DBConnection.getInstance().getConnection();

        try {
            String query = "UPDATE Payment SET locked = true WHERE PaymentID = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, paymentID);

            stm.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(Payment.class.getName()).log(Level.SEVERE, "Error deleting payment paymentID=" + paymentID, ex);
        }
    }

    public static Payment getPayment(int ID) {
        Connection conn = DBConnection.getInstance().getConnection();

        Payment payment = null;
        try {
            String query = "SELECT * FROM Payment WHERE PaymentID = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, ID);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int paymentID = rs.getInt("paymentID");
                int appointmentID = rs.getInt("appointmentID");
                String service = rs.getString("service");
                double price = rs.getDouble("price");
                boolean isPaid = rs.getBoolean("isPaid");
                Date dateIssued = rs.getDate("dateIssued");
                Date datePaid = rs.getDate("datePaid");
                boolean expired = rs.getBoolean("expired");
                boolean locked = rs.getBoolean("locked");
                payment = new Payment(paymentID, appointmentID, service, price, isPaid, 
                        dateIssued, datePaid, expired, locked);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Payment.class.getName()).log(Level.SEVERE, "Error getting payment paymentID=" + ID, ex);
        }
        return payment;
    }

    public static int getNextID() {
        int nextID = 0;
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            String query = "SELECT (MAX(paymentID) + 1) AS nextID FROM Payment";
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(query);
            if (rs.next()) {
                nextID = rs.getInt("nextID");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Payment.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (nextID == 0) {
            nextID++;
        }
        return (nextID);
    }

    public static java.sql.Date convertJavaDateToSqlDate(java.util.Date date) {
        return new java.sql.Date(date.getTime());
    }

    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setIsPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    public Date getDateIssued() {
        return dateIssued;
    }

    public void setDateIssued(Date dateIssued) {
        this.dateIssued = dateIssued;
    }

    public Date getDatePaid() {
        return datePaid;
    }

    public void setDatePaid(Date datePaid) {
        this.datePaid = datePaid;
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
