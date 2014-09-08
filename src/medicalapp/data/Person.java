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
public class Person {

    private int personID;
    private String firstName;
    private String lastName;
    private boolean isPatient;
    private boolean isStaff;
    private String address;
    private Date dateOfBirth;
    private String contactNumber;
    private boolean expired;

    public Person(int personID, String firstName, String lastName, boolean isPatient, 
            boolean isStaff, String address, Date dateOfBirth, String contactNumber, boolean expired) {
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isPatient = isPatient;
        this.isStaff = isStaff;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.contactNumber = contactNumber;
        this.expired = expired;
    }

    public static void insertPerson(Person person) {
        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String query = "INSERT INTO Person VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, getNextID());
            stm.setString(2, person.getFirstName());
            stm.setString(3, person.getLastName());
            stm.setString(4, person.getAddress());
            stm.setString(5, person.getContactNumber());
            stm.setDate(6, new java.sql.Date(person.getDateOfBirth().getTime()));
            stm.setBoolean(7, person.isPatient());
            stm.setBoolean(8, person.isStaff());
            stm.setBoolean(9, false);

            stm.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, "Error inserting person", ex);
        }
    }

    public static void updatePerson(Person person) {
        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String query = "UPDATE Person "
                    + "SET FirstName = ?, LastName = ?, Address = ?, ContactNumber = ?,"
                    + "dateOfBirth = ?, isPatient = ?, isStaff = ?"
                    + "WHERE personID = ?";

            PreparedStatement stm = conn.prepareStatement(query);

            stm.setString(1, person.getFirstName());
            stm.setString(2, person.getLastName());
            stm.setString(3, person.getAddress());
            stm.setString(4, person.getContactNumber());
            stm.setDate(5, convertJavaDateToSqlDate(person.getDateOfBirth()));
            stm.setBoolean(6, person.isPatient());
            stm.setBoolean(7, person.isStaff());
            stm.setInt(8, person.getPersonID());

            stm.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, "Error updating person", ex);
        }
    }

    public static void deletePerson(Person person) {
        deletePerson(person.getPersonID());
    }

    //If someone asks to be removed from system, set expired = true.
    public static void deletePerson(int personID) {
        Connection conn = DBConnection.getInstance().getConnection();

        try {
            String query = "UPDATE Person SET expired = true WHERE PersonID = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, personID);

            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, "Error deleting person personID=" + personID, ex);
        }
    }

    public static Person getPerson(int ID) {
        Connection conn = DBConnection.getInstance().getConnection();

        Person person = null;
        try {
            String query = "SELECT * FROM Person WHERE PersonID = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, ID);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int personID = rs.getInt("personID");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                boolean isPatient = rs.getBoolean("isPatient");
                boolean isStaff = rs.getBoolean("isStaff");
                String address = rs.getString("address");
                Date dateOfBirth = rs.getDate("dateOfBirth");
                String contactNumber = rs.getString("contactNumber");
                Boolean expired = rs.getBoolean("expired");
                
                person = new Person(personID, firstName, lastName, isPatient, isStaff, address, 
                        dateOfBirth, contactNumber, expired);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, "Error getting person personID=" + ID, ex);
        }
        return person;
    }

    //When inserting new records, increment the maximum ID by 1.
    public static int getNextID() {
        int nextID = 0;
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            String query = "SELECT (MAX(personID) + 1) AS nextID FROM Person";
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

    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isPatient() {
        return isPatient;
    }

    public void setIsPatient(boolean isPatient) {
        this.isPatient = isPatient;
    }

    public boolean isStaff() {
        return isStaff;
    }

    public void setIsStaff(boolean isStaff) {
        this.isStaff = isStaff;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    //convert java and sql date compatability
    public static java.sql.Date convertJavaDateToSqlDate(java.util.Date date) {
        return new java.sql.Date(date.getTime());
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }
    
}
