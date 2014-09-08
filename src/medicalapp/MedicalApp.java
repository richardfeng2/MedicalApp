/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medicalapp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import medicalapp.data.Patient;
import static medicalapp.data.Patient.insertPatient;

/**
 *
 * @author Richard
 */
public class MedicalApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //TODO :
        //consider services table with set of costs; 
        //make a list of statements for demo purposes / add dummy data
        //Create SQL script generating tables + dummy data
        //Add  boolean "expired" columns and fields, indicating deleted data, and/or completed appointments

        //Sample date
        Calendar cal = Calendar.getInstance();
        Date date;
        cal.set(Calendar.YEAR, 1994);
        cal.set(Calendar.MONTH, 05);
        cal.set(Calendar.DATE, 02);
        date = cal.getTime();
        
        ArrayList<String> tonyConditions = new ArrayList<>();
//       Document d = new Document(1,1,"Lung X-ray",null,true);
//       updateDocument(d, "C:\\Users\\Richard\\Pictures\\lungCancer.jpg");
//       deleteDocument(1);

        Patient p = new Patient(1, "Medicare 1234567190", tonyConditions, 1, "Tony","Lu", true, false, 
                "1 Fake St, Hurstville", date, "0422 489 489", false);
        insertPatient(p);
        
    }
}
