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

}
