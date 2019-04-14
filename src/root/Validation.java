package root;

import javafx.scene.control.TextField;
import root.controllers.StdntController;
import root.student.Student;



public class Validation {



    public static boolean checkName(String name) {//checks if name is taken
        StdntController studentController = StdntController.getStdntController();
        try {
            if(name.trim().isEmpty()) {
                return false;
            }else {
                //check every Student
                for(Student student:studentController.getStudents()) {
                    if(student.getStudentName().toLowerCase().equals(name.trim().toLowerCase())) {
                        return false;
                    }
                }

            }
        }catch(NullPointerException e) {
            System.out.println("Unexpect error has occured");
            return false;
        }
        return true;
    }


    public static boolean checkPtValues(TextField tferndPts, TextField tfttlPts){
        Integer i_earndPts = null;
        Integer i_ttlPts = null;
        try{
            i_earndPts = Integer.parseInt(tferndPts.getText());
            i_ttlPts = Integer.parseInt(tfttlPts.getText());

        }
        catch(Exception e){
            return false;
        }
        if (i_earndPts > i_ttlPts){
            return false;
        }
        else{
            return true;
        }
    }
    public static boolean isInteger(TextField tfuserInput){
        boolean isInteger = false;
        String s_userInput = tfuserInput.getText();
        try{
            Integer int_Pts = Integer.parseInt(s_userInput);
            isInteger = true;
            return isInteger;
        }
        catch(Exception e){
            return isInteger;
        }

    }

}
