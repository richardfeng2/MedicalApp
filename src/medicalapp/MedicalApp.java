/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medicalapp;

import java.util.Calendar;
import java.util.Date;
import medicalapp.data.Document;
import static medicalapp.data.Document.deleteDocument;
import static medicalapp.data.Document.updateDocument;

/**
 *
 * @author Richard
 */
public class MedicalApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //TODO : Documents table storing attachments; Notes table storing timestamped notes
        //consider services table with set of costs.

        //Sample date
        Calendar cal = Calendar.getInstance();
        Date date;
        cal.set(Calendar.YEAR, 1974);
        cal.set(Calendar.MONTH, 05);
        cal.set(Calendar.DATE, 02);
        date = cal.getTime();
        
       Document d = new Document(1,1,"Lung X-ray",null,true);
       updateDocument(d, "C:\\Users\\Richard\\Pictures\\lungCancer.jpg");
       deleteDocument(1);
        
    }


}
