/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package medicalapp;

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
        
        Person p = Person.getPerson(1);
        
        System.out.println(p.getAddress());
    }
    
}
