package root.controllers;

import java.util.ArrayList;
import java.util.List;
import root.student.Student;

public class StdntController {
    private List<Student> _students = new ArrayList<Student>();
    private static StdntController _stdntController = new StdntController();
    private StdntController(){};



    public static StdntController getStdntController(){
        return _stdntController;
    }
    public List<Student> getStudents(){
        return _students;
    }
    public void addStudent(Student student){
        _students.add(student);
    }
    public void removeStudent(String studentName){
        Student student = null;
        for (Student astudent: _students){
            if (astudent.getStudentName().equals(studentName)){
                student = astudent;
            }
        }
        if(student != null){
            _students.remove(student);
        }
    }
    public String getStudentName(int index){
        return _students.get(index).getStudentName();
    } //retrieves student name at a particular index
    public Student findStudent(String studentName){
        Student theStudent = null;
        for(Student student:_students){
            if(student.getStudentName().equals(studentName)){
                theStudent = student;
                return theStudent;
            }

        }
        return theStudent;
    }

}
