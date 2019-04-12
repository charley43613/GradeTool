package root;

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
    public static boolean checkPtValues(Integer erndPts, Integer ttlPts){

        if (erndPts > ttlPts){
            return false;
        }
        else{
            return true;
        }
    }
    public static boolean isInteger(String userInput){
        boolean isInteger = false;
        try{
            Integer int_Pts = Integer.parseInt(userInput);
            isInteger = true;
            return isInteger;
        }
        catch(Exception e){
            return isInteger;
        }

    }

}
