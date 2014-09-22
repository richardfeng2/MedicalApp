/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medicalapp.data;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Richard
 */
public class Patient extends Person {

    private int patientID;
    private String billingInfo;
    private ArrayList<String> conditions;

    public Patient(int patientID, String billingInfo, ArrayList<String> conditions, int personID, String firstName,
            String lastName, boolean isPatient, boolean isStaff, String address, Date dateOfBirth,
            String contactNumber, boolean expired) {
        super(personID, firstName, lastName, isPatient, isStaff, address, dateOfBirth, contactNumber, expired);
        this.patientID = patientID;
        this.billingInfo = billingInfo;
        this.conditions = conditions;
    }

    public static String arrayToString(ArrayList<String> conditions) {
        String string = "";

        if (!conditions.isEmpty()) {
            for (String condition : conditions) {
                //formating use of semi-colon
                if (string.equals("")) {
                    string += condition;
                } else {
                    string += "; " + condition;
                }
            }
        }
        return string;
    }

    //Diagnose a new condition, then update to database.
    public static void addCondition(int patientID, String condition) {
        Patient patient = getPatient(patientID);
        ArrayList<String> conditions = patient.getConditions();
        conditions.add(condition);
        Connection conn = DBConnection.getInstance().getConnection();
        try {
            String query = "UPDATE Patient SET conditions = ? WHERE patientID = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setString(1, arrayToString(conditions));
            stm.setInt(2, patientID);

            if (!(arrayToString(getPatient(patientID).getConditions())).contains(condition)) {

                stm.executeUpdate();

                System.out.println(getPatient(patientID).getFirstName() + " "
                        + getPatient(patientID).getLastName() + " " + "has successfully been"
                        + " diagnosed with " + condition + ".");
            } else {
                System.out.println(getPatient(patientID).getFirstName() + " "
                        + getPatient(patientID).getLastName() + " " + "is already"
                        + " diagnosed with " + condition + ".");
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Patient.class.getName()).log(Level.SEVERE, "Error adding condition", ex);
        }
    }

    //Remove diagnosis of a condition, then update to database.
    public static void removeCondition(int patientID, String condition) {

        Patient patient = getPatient(patientID);

        ArrayList<String> conditions = patient.getConditions();
        conditions.remove(condition);
        Connection conn = DBConnection.getInstance().getConnection();
        try {
            String query = "UPDATE Patient SET conditions = ? WHERE patientID = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setString(1, arrayToString(conditions));
            stm.setInt(2, patientID);

            stm.executeUpdate();

            System.out.println(getPatient(patientID).getFirstName() + " "
                    + getPatient(patientID).getLastName() + "'s " + "diagnosis has successfully been"
                    + " removed.");

            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Patient.class.getName()).log(Level.SEVERE, "Error removing condition", ex);
        }
    }

    public static void insertPatient(Patient patient) {
        insertPerson(patient);
        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String query = "INSERT INTO Patient VALUES (?,?,?,?)";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, getNextID());
            stm.setInt(2, Person.getNextID()-1);
            stm.setString(3, patient.getBillingInfo());
            stm.setString(4, "");

            stm.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Patient.class.getName()).log(Level.SEVERE, "Error updating patient", ex);
        }
    }

    public static void updatePatient(Patient patient) {
        updatePerson(patient);
        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String query = "UPDATE Patient SET billingInfo = ? WHERE patientID = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setString(1, patient.getBillingInfo());
            stm.setInt(2, patient.getPatientID());

            stm.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Patient.class.getName()).log(Level.SEVERE, "Error inserting patient", ex);
        }
    }

    public static void deletePatient(Patient patient) {
        deletePatient(patient.getPatientID());
        deletePerson(patient.getPersonID());
    }

    //If patient asks his/her patient file to be removed, set Person's isPatient = false.
    public static void deletePatient(int patientID) {
        Connection conn = DBConnection.getInstance().getConnection();

        try {
            String query = "UPDATE Pereson SET isPatient = false WHERE personID = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, getPatient(patientID).getPersonID());

            stm.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(Patient.class.getName()).log(Level.SEVERE, "Error deleting patient patientID = " + patientID, ex);
        }
    }

    public static Patient getPatient(int id) {
        Connection conn = DBConnection.getInstance().getConnection();

        Patient patient = null;
        try {

            String query = "SELECT * FROM Patient INNER JOIN Person "
                    + "ON Patient.personID = Person.personID WHERE patientID = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, id);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int patientID = rs.getInt("patientID");
                int personID = rs.getInt("personID");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                boolean isPatient = rs.getBoolean("isPatient");
                boolean isStaff = rs.getBoolean("isStaff");
                String address = rs.getString("address");
                Date dateOfBirth = rs.getDate("dateOfBirth");
                String contactNumber = rs.getString("contactNumber");
                String billingInfo = rs.getString("billingInfo");
                String conditions = rs.getString("conditions");
                boolean expired = rs.getBoolean("expired");

                //String from db to arrayList
                ArrayList<String> diagnosis = new ArrayList<>(Arrays.asList(conditions.split("; ")));

                patient = new Patient(patientID, billingInfo, diagnosis, personID, firstName, lastName, isPatient,
                        isStaff, address, dateOfBirth, contactNumber, expired);
            }
            //conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Person.class
                    .getName()).log(Level.SEVERE, "Error getting patient patientID = " + id, ex);
        }

        return patient;
    }

//    public static Patient getPatient(String firstName, String lastName) {
//        Connection conn = DBConnection.getInstance().getConnection();
//        Patient patient = null;
//        try {
//            String query = "SELECT * FROM Patient INNER JOIN Person "
//                    + "ON Patient.personID = Person.personID "
//                    + "WHERE firstName = ? AND lastName = ?";
//            PreparedStatement stm = conn.prepareStatement(query);
//            stm.setString(1, firstName);
//            stm.setString(2, lastName);
//
//            ResultSet rs = stm.executeQuery();
//            while (rs.next()) {
//                int patientID = rs.getInt("patientID");
//                int personID = rs.getInt("personID");
//                boolean isPatient = rs.getBoolean("isPatient");
//                boolean isStaff = rs.getBoolean("isStaff");
//                Date dateOfBirth = rs.getDate("dateOfBirth");
//                String contactNumber = rs.getString("contactNumber");
//                String billingInfo = rs.getString("billingInfo");
//                String conditions = rs.getString("conditions");
//                boolean expired = rs.getBoolean("expired");
//                String address = rs.getString("address");
//
//                //String from db to arrayList
//                ArrayList<String> diagnosis = new ArrayList<>(Arrays.asList(conditions.split("; ")));
//
//                if (!expired) {
//                    patient = getPatient(patientID);
//                    System.out.println(firstName + "\t" + lastName + "\t" + address);
//                }
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, "Error getting patient = "
//                    + firstName + " " + lastName, ex);
//        }
//    }
    public static ArrayList<Patient> searchPatients(String[] searchTerm) {
        Connection conn = DBConnection.getInstance().getConnection();

        ArrayList<Patient> patients = new ArrayList<>();

        for (String word : searchTerm) {
            try {
                String query = "SELECT * FROM Patient INNER JOIN Person "
                        + "ON Patient.personID = Person.personID "
                        + "WHERE LCASE(firstName) LIKE ? OR LCASE(lastName) LIKE ? "
                        + " OR LCASE(address) LIKE ?"
                        + " AND expired <> true";
                PreparedStatement stm = conn.prepareStatement(query);
                stm.setString(1, "%" + word + "%");
                stm.setString(2, "%" + word + "%");
                stm.setString(3, "%" + word + "%");

                ResultSet rs = stm.executeQuery();
                while (rs.next()) {
                    int patientID = rs.getInt("patientID");

                    if (patients.contains(getPatient(patientID))) { //Check if array already contains the patient
                    } else {
                        patients.add(getPatient(patientID));
                        System.out.println("Added " + getPatient(patientID).getFirstName());
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(Person.class.getName()).log(Level.SEVERE, "Error getting patient = "
                        + searchTerm, ex);
            }
        }
        
        //removes duplicated results due to multiple search
        if (patients != null || !patients.isEmpty()) {
            int n = patients.size()/Array.getLength(searchTerm);
            for (int i = patients.size()/Array.getLength(searchTerm); i < patients.size(); i++) {
                patients.remove(i);
            }
            
            return patients;
        } else {
            System.out.println("No patients added");
            return null;
        }
    }

    public static ArrayList<Patient> searchPatients(String searchTerm) {
        Connection conn = DBConnection.getInstance().getConnection();

        ArrayList<Patient> patients = new ArrayList<>();

        try {
            String query = "SELECT * FROM Patient INNER JOIN Person "
                    + "ON Patient.personID = Person.personID "
                    + "WHERE LCASE(firstName) LIKE ? OR LCASE(lastName) LIKE ? "
                    + " OR LCASE(address) LIKE ?"
                    + " AND expired <> true";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setString(1, "%" + searchTerm + "%");
            stm.setString(2, "%" + searchTerm + "%");
            stm.setString(3, "%" + searchTerm + "%");

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int patientID = rs.getInt("patientID");

                patients.add(getPatient(patientID));
                System.out.println(patients.get(patients.size() - 1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, "Error getting patient = "
                    + searchTerm, ex);
        }

        if (patients != null || !patients.isEmpty()) {
//
//            //Delete unique elements from patiens, as they dont not share common split search terms
//            Multiset<Patient> patientMultiset = HashMultiset.create();
//            patientMultiset.addAll(patients);
//            for (Multiset.Entry<Patient> p : patientMultiset.entrySet()) {
//                for (int i = 0; i < Array.getLength(splitTerm); i++) {
//                    if(p.getElement().getPersonID() != p.)
//                }
//                
//                if (p.getCount() != Array.getLength(splitTerm)) { //# of patient instances doesnt match # of search terms
//                    patients.remove(p); //remove non duplicates, coz not what search terms looking for
//                }
//                // remove duplicates
//                HashSet hs = new HashSet();
//                hs.addAll(patients);
//                patients.clear();
//                patients.addAll(hs);
//            }

            return patients;
        } else {
            System.out.println("No patients added");
            return null;
        }
    }

    public static Patient getPatient(String firstName, String lastName, String address) {
        Connection conn = DBConnection.getInstance().getConnection();
        Patient patient = null;

        try {
            String query = "SELECT * FROM Patient INNER JOIN Person "
                    + "ON Patient.personID = Person.personID "
                    + "WHERE firstName = ? AND lastName = ? AND address = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setString(1, firstName);
            stm.setString(2, lastName);
            stm.setString(3, address);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int patientID = rs.getInt("patientID");
                boolean expired = rs.getBoolean("expired");

                patient = getPatient(patientID);
                if (!expired) {
                    System.out.println(firstName + "\t" + lastName + "\t" + address);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, "Error getting patient = "
                    + firstName + " " + lastName + " " + address, ex);
        }

        return patient;
    }

    //When inserting new records, increment the maximum ID by 1.
    public static int getNextID() {
        int nextID = 0;
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            String query = "SELECT (MAX(patientID) + 1) AS nextID FROM Patient";
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

    public ArrayList<String> getConditions() {
        return conditions;
    }

    public void setConditions(ArrayList<String> conditions) {
        this.conditions = conditions;
    }

    public String getBillingInfo() {
        return billingInfo;
    }

    public void setBillingInfo(String billingInfo) {
        this.billingInfo = billingInfo;
    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }
}
