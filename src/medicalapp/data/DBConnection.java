/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medicalapp.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Richard
 */
public class DBConnection {

    private static final String dbURL = "jdbc:derby://localhost:1527/medicalDB;user=sa;password=sa";

    private static DBConnection instance;

    private final Connection connection;

    private DBConnection(Connection conn) {
        this.connection = conn;
    }

    public static DBConnection getInstance() {
        if (instance == null) {
            Connection conn = null;
            try {
                Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
                conn = DriverManager.getConnection(dbURL);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, "Failed to get connection", ex);
            }

            if (conn == null) {
                System.out.println("Unable to connect. Connect to server!.");
            }

            instance = new DBConnection(conn);
        }
        return instance;
    }

    public Connection getConnection() {
        return this.connection;
    }
}
