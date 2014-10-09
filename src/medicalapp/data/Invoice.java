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
public class Invoice {

    private int invoiceID;
    private int appointmentID;
    private boolean isPaid;
    private Date dateIssued;
    private Date datePaid;
    private boolean expired;

    public Invoice(int invoiceID, int appointmentID, boolean isPaid,
            Date dateIssued, Date datePaid, boolean expired) {
        this.invoiceID = invoiceID;
        this.appointmentID = appointmentID;
        this.isPaid = isPaid;
        this.dateIssued = dateIssued;
        this.datePaid = datePaid;
        this.expired = expired;
    }

    public static void insertInvoice(Invoice invoice) {
        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String query = "INSERT INTO Invoice VALUES (?,?,?,?,?,?)";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, getNextID());
            stm.setInt(2, invoice.getAppointmentID());
            stm.setBoolean(3, invoice.isPaid());
            stm.setDate(4, convertJavaDateToSqlDate(invoice.getDateIssued()));

            if (invoice.getDatePaid()!=null) {
                stm.setDate(5, convertJavaDateToSqlDate(invoice.getDatePaid()));
            } else {
                stm.setDate(5, null);
            }
            stm.setBoolean(6, false);

            stm.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Invoice.class.getName()).log(Level.SEVERE, "Error inserting invoice", ex);
        }
    }

    //convert java and sql date compatability
    public static void updateInvoice(Invoice invoice) {
        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String query = "UPDATE Invoice"
                    + " SET appointmentID = ?, isPaid= ?, dateIssued = ?, datePaid = ?, expired = ?"
                    + " WHERE invoiceID = ?";

            PreparedStatement stm = conn.prepareStatement(query);

            stm.setInt(1, invoice.getAppointmentID());
            stm.setBoolean(2, invoice.isPaid());
            stm.setDate(3, convertJavaDateToSqlDate(invoice.getDateIssued()));
            stm.setDate(4, convertJavaDateToSqlDate(invoice.getDatePaid()));
            stm.setBoolean(5, invoice.isExpired());
            stm.setInt(6, invoice.getInvoiceID());

            stm.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Invoice.class.getName()).log(Level.SEVERE, "Error updating invoice", ex);
        }

    }

    public static void deleteInvoice(Invoice invoice) {
        deleteInvoice(invoice.getInvoiceID());
    }

    public static void deleteInvoice(int invoiceID) {
        Connection conn = DBConnection.getInstance().getConnection();

        try {
            String query = "UPDATE Invoice SET locked = true WHERE InvoiceID = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, invoiceID);

            stm.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(Invoice.class.getName()).log(Level.SEVERE, "Error deleting invoice invoiceID=" + invoiceID, ex);
        }
    }

    public static Invoice getInvoice(int ID) {
        Connection conn = DBConnection.getInstance().getConnection();

        Invoice invoice = null;
        try {
            String query = "SELECT * FROM Invoice WHERE InvoiceID = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, ID);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int invoiceID = rs.getInt("invoiceID");
                int appointmentID = rs.getInt("appointmentID");
                boolean isPaid = rs.getBoolean("isPaid");
                Date dateIssued = rs.getDate("dateIssued");
                Date datePaid = rs.getDate("datePaid");
                boolean expired = rs.getBoolean("expired");
                invoice = new Invoice(invoiceID, appointmentID, isPaid,
                        dateIssued, datePaid, expired);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Invoice.class.getName()).log(Level.SEVERE, "Error getting invoice invoiceID=" + ID, ex);
        }
        return invoice;
    }

    public static int getNextID() {
        int nextID = 0;
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            String query = "SELECT (MAX(invoiceID) + 1) AS nextID FROM Invoice";
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(query);
            if (rs.next()) {
                nextID = rs.getInt("nextID");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Invoice.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (nextID == 0) {
            nextID++;
        }
        return (nextID);
    }

    public static java.sql.Date convertJavaDateToSqlDate(java.util.Date date) {
        return new java.sql.Date(date.getTime());
    }

    public int getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(int invoiceID) {
        this.invoiceID = invoiceID;
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
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
}
