/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medicalapp;

import java.util.Calendar;
import java.util.Date;
import static medicalapp.data.Patient.getPatient;

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
        //consider services table with set of costs; changeLog table
                
        //Sample date
        Calendar cal = Calendar.getInstance();
        Date date;
        cal.set(Calendar.YEAR, 2014);
        cal.set(Calendar.MONTH, 9);
        cal.set(Calendar.DATE, 10);
        date = cal.getTime();
        
        

        /*
            Schedule an appointment with John Smith, and Dr. Jacky Chan
        */
        //insertAppointment("John", "Smith", "123 Fake St, Fakeville", "Jacky Chan", date, "General Checkup");
        
        /*
            Checkin   
        */
       // getPatient("John","Smith");
        //getAppointment("John","Smith","123 Fake St, Fakeville");

        /*
            Test results
        */
        //insertTestResult(4, 70, "140/120", 100, 40, 75, 230);
        //getTestResult(3);
        
        /*
            Diagnosis
        */
        //addCondition(getAppointment(4).getPatientID(), "Asthma");
        //removeCondition(getAppointment(4).getPatientID(), "Asthma");
       
        
        //Attach document - lung cancer
        //End appointment
        //Invoicing
        //Paid
        
        
    }
}



//       Document d = new Document(1,1,"Lung X-ray",null,true);
//       updateDocument(d, "C:\\Users\\Richard\\Pictures\\lungCancer.jpg");
//       deleteDocument(1);

//        ArrayList<String> tonyConditions = new ArrayList<>();
//        
//        Patient p = new Patient(1, "Medicare 1234567190", tonyConditions, 1, "Tony","Lu", true, false, 
//                "1 Fake St, Hurstville", date, "0422 489 489", false);
//        insertPatient(p);
        