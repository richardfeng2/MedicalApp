/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package medicalapp;

import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import medicalapp.data.Appointment;
import medicalapp.data.Person;

/**
 *
 * @author Richard
 */
public class MedicalApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        //Sample date
        Calendar cal = Calendar.getInstance();
        Date date;
        cal.set(Calendar.YEAR, 2014);
        cal.set(Calendar.MONTH, 05);
        cal.set(Calendar.DATE, 01);
        date = cal.getTime();
//        
          //Test update person  
//        Person p =  new Person(1, "Taylor", "Swift", false, false, "123 Smith Street", date, "0484084877");
//        Person.updatePerson(p);
//        
       // Appointment a = Appointment.getAppointment(1);
       // System.out.println(a.getDate());
        
        //Test update appointment
        Appointment a = new Appointment(1, date,1,1,"Emergency Checkup",Duration.ofMinutes(15), "Dr. Tan");
        Appointment.updateAppointment(a);
        System.out.println(a.getDate());
    }
    
}
