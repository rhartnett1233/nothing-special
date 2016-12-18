import java.util.*;

public class Driver{
    public static void main(String[] args){
        Client r = new Client();

        r.deleteAccount("Testaccount");
        r.createAccount("Testaccount", "Full Name", "Test@umass.edu", "UMASS","10:00pm", "10:00am");

        r.login("Testaccount", "UMASS");

        r.addAssignment("Hw1","Circuits", "12/14/16", "09:00pm", "5", "2", "");
        r.addAssignment("Hw2","Comp Sys", "12/13/16", "07:00pm", "4", "3", "");
        r.addAssignment("Hw3","Hardware", "12/15/16", "11:00pm", "1", "1", "");
        r.addAssignment("Hw4","Har", "12/18/16", "06:00am", "3", "3", "");
        r.addAssignment("Hw5","math", "12/24/16", "06:00am", "5", "1", "");
        r.addAssignment("Hw6","english", "12/22/16", "10:00pm", "10", "1", "");
        r.addAssignment("Hw7","dsds", "12/30/16", "12:00pm", "40", "3", "");

        
        r.addEvent("Electronics","MoWe","12/05/16", "10:00am", "12/05/16", "11:00am","Elab 202");

        r.addEvent("Circuits","MoWeFr","12/05/16", "04:00pm", "12/05/16", "06:00pm","LRC 301");
        r.addEvent("EnginWrit","TuTh","12/06/16", "10:00am","12/06/16", "11:00am","Marcus 110");
        r.addEvent("Seminar", "", "12/16/16", "02:00pm", "12/16/16", "04:00pm", "Marston 5");

        r.schedule();

        System.out.println("Account \"" + r.getUserName()+ "\" made and initiated");

    }

}