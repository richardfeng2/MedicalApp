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
public class Service {
    int serviceID;
    String description;
    double price;

    public Service(int serviceID, String description, double price) {
        this.serviceID = serviceID;
        this.description = description;
        this.price = price;
    }

    public static Service getService(int ID) {
        Connection conn = DBConnection.getInstance().getConnection();
        Service service = null;
        try {
            String query = "SELECT * FROM Service WHERE ServiceID = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, ID);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int serviceID = rs.getInt("serviceID");
                String description = rs.getString("description");
                double price = rs.getDouble("price");
                service = new Service(serviceID, description, price);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Invoice.class.getName()).log(Level.SEVERE, "Error getting service ID=" + ID, ex);
        }
        return service;
    }
    public static Service getService(String description) {
        Connection conn = DBConnection.getInstance().getConnection();
        Service service = null;
        try {
            String query = "SELECT * FROM Service WHERE description = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setString(1, description);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int serviceID = rs.getInt("serviceID");
                service = getService(serviceID);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Invoice.class.getName()).log(Level.SEVERE, "Error getting service:" + description, ex);
        }
        return service;
    }
    
        public static int getNextID() {
        int nextID = 0;
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            String query = "SELECT (MAX(ServiceID) + 1) AS nextID FROM Service";
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

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
