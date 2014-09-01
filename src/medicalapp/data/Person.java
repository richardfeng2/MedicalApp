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

    public Person(int personID, String firstName, String lastName, boolean isPatient, boolean isStaff, String address, Date dateOfBirth, String contactNumber) {
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isPatient = isPatient;
        this.isStaff = isStaff;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.contactNumber = contactNumber;
    }

    public static void insertPerson(Person person) {
        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String query = "INSERT INTO Person VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, person.getPersonID());
            stm.setString(2, person.getFirstName());
            stm.setString(3, person.getLastName());
            stm.setString(4, person.getAddress());
            stm.setString(5, person.getContactNumber());
            stm.setDate(6, new java.sql.Date(person.getDateOfBirth().getTime()));
            stm.setBoolean(7, person.isIsPatient());
            stm.setBoolean(8, person.isIsStaff());
            
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
            stm.setBoolean(6, person.isIsPatient());
            stm.setBoolean(7, person.isIsStaff());
            stm.setInt(8, person.getPersonID());
            
            stm.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, "Error updating person", ex);
        }
        
    }

    public static void deletePerson(Person person) {
        deletePerson(person.getPersonID());
    }

    public static void deletePerson(int personID) {
        Connection conn = DBConnection.getInstance().getConnection();

        try {
            String query = "DELETE FROM Person WHERE PersonID = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, personID);

            stm.executeQuery();
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

            ResultSet personResults = stm.executeQuery();
            while (personResults.next()) {
                int personID = personResults.getInt("personID");
                String firstName = personResults.getString("firstName");
                String lastName = personResults.getString("lastName");
                boolean isPatient = personResults.getBoolean("isPatient");
                boolean isStaff = personResults.getBoolean("isStaff");
                String address = personResults.getString("address");
                Date dateOfBirth = personResults.getDate("dateOfBirth");
                String contactNumber = personResults.getString("contactNumber");

                person = new Person(personID, firstName, lastName, isPatient, isStaff, address, dateOfBirth, contactNumber);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, "Error getting person personID=" + ID, ex);
        }
        return person;
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

    public boolean isIsPatient() {
        return isPatient;
    }

    public void setIsPatient(boolean isPatient) {
        this.isPatient = isPatient;
    }

    public boolean isIsStaff() {
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

    public static java.sql.Date convertJavaDateToSqlDate(java.util.Date date) {
        return new java.sql.Date(date.getTime());
    }
}
