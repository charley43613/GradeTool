package root.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import root.student.HwGrade;
import root.student.Student;
import root.student.TestGrade;

public class StdntController implements Serializable {
    private List<Student> _students = new ArrayList<Student>();
    private static StdntController _stdntController = new StdntController();
    private StdntController(){};



    public static StdntController getStdntController(){
        return _stdntController;
    }
    public void setStdntController(StdntController stdntController){
        _stdntController = stdntController;
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

    public Student getStudent(Object crntOptnStdntSlct){
        Student theStudent= null; //get the student selected
        try{
            theStudent = findStudent(crntOptnStdntSlct.toString());
            System.out.println("Student found");
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Memory integrity error, selection exists where student does not");
        }
        return theStudent;

    }
    private Student findStudent(String studentName){
        Student theStudent = null;
        for(Student student:_students){
            if(student.getStudentName().equals(studentName)){
                theStudent = student;
                return theStudent;
            }

        }
        return theStudent;
    }
    public void clear(){//remove all memory
        _students.clear();
    }




}
