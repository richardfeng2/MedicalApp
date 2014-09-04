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
    private String service;
    private double price;
    private boolean isPaid;

    //date from appointment
    public Invoice(int invoiceID, int appointmentID, String service, double price, boolean isPaid) {
        this.invoiceID = invoiceID;
        this.appointmentID = appointmentID;
        this.service = service;
        this.price = price;
        this.isPaid = isPaid;
    }

        public static void insertInvoice(Invoice invoice) {
        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String query = "INSERT INTO Invoice VALUES (?,?,?,?,?)";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, getNextID());
            stm.setInt(2, invoice.getAppointmentID());
            stm.setString(3, invoice.getService());
            stm.setDouble(4, invoice.getPrice());
            stm.setBoolean(5, invoice.isPaid());

            stm.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Invoice.class.getName()).log(Level.SEVERE, "Error inserting invoice", ex);
        }
    }

    public static void updateInvoice(Invoice invoice) {
        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String query = "UPDATE Invoice"
                    + " SET appointmentID = ?, service = ?, price = ?, isPaid= ?"
                    + " WHERE invoiceID = ?";

            PreparedStatement stm = conn.prepareStatement(query);

            stm.setInt(1, invoice.getAppointmentID());
            stm.setString(2, invoice.getService());
            stm.setDouble(3, invoice.getPrice());
            stm.setBoolean(4, invoice.isPaid());
            stm.setInt(5, invoice.getInvoiceID());

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
            String query = "DELETE FROM Invoice WHERE InvoiceID = ?";
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
                String service = rs.getString("service");
                double price = rs.getDouble("price");
                boolean isPaid = rs.getBoolean("isPaid");

                invoice = new Invoice(invoiceID, appointmentID, service, price, isPaid);
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
    
    
    
}
