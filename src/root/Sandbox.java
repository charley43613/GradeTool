package root;
import root.controllers.StdntController;
import root.student.HwGrade;
import root.student.Student;
import root.student.TestGrade;
import root.student.HwGrade;

public class Sandbox {

        public static void main(String[] args) {
            StdntController stdntController = StdntController.getStdntController();
            StdntController.getStdntController().addStudent(new Student("Charles"));
            Student astudent = stdntController.getStudents().get(0);
            astudent.addGrade(new TestGrade("Test1", 98, 100));
            astudent.addGrade(new HwGrade("hw1", 10, 12));

            System.out.println("Hw grade earned/total: " + astudent.getHwGrades().get(0).getEarnedPts() + "/" + astudent.getHwGrades().get(0).getTotalPoints());
        }


    }

