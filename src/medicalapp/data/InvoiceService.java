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
public class InvoiceService {

    int invoiceID;
    int serviceID;

    public InvoiceService(int invoiceID, int serviceID) {
        this.invoiceID = invoiceID;
        this.serviceID = serviceID;
    }

    public static void insertInvoiceService(InvoiceService invoiceService) {
        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String query = "INSERT INTO Invoice_Service VALUES (?,?)";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, invoiceService.getInvoiceID());
            stm.setInt(2, invoiceService.getServiceID());

            stm.executeUpdate();
        } catch (SQLException ex) {
        }
    }
    
    public static void deleteInvoiceService(InvoiceService invoiceService) {
        Connection conn = DBConnection.getInstance().getConnection();

        try {
            String query = "DELETE FROM Invoice_Service WHERE InvoiceID = ? AND serviceID = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, invoiceService.getInvoiceID());
            stm.setInt(2, invoiceService.getServiceID());

            stm.executeQuery();
        } catch (SQLException ex) {
        }
    }

    public static InvoiceService getInvoiceService(int ID) {
        Connection conn = DBConnection.getInstance().getConnection();

        InvoiceService invoiceService = null;
        try {
            String query = "SELECT * FROM Invoice_Service WHERE InvoiceID = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, ID);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int invoiceID = rs.getInt("invoiceID");
                int serviceID = rs.getInt("serviceID");
                invoiceService = new InvoiceService(invoiceID,serviceID);
            }
        } catch (SQLException ex) {
        }
        return invoiceService;
    }

    public int getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(int invoiceID) {
        this.invoiceID = invoiceID;
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

}
