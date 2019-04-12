package root;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import root.controllers.StdntController;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import root.student.HwGrade;
import root.student.Student;
import root.student.TestGrade;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class GradeFormCtrler implements Initializable {

    enum e_GradeType
    {
        HW_GRADE,
        TEST_GRADE
    }
    private final String DFLT_STDNT = "Select Student"; //initial choicebox items
    private final String DFLT_ADD_EDT = "Edit/Add Grade";
    private final String EDIT_GRADE = "Edit Grade";
    private final String ADD_HWGRADE = "Add Homework Grade";
    private final String ADD_TESTGRADE = "Add Test Grade";
    private final String DFLT_GRDS = "Select Grade";


    StdntController studentController = StdntController.getStdntController();
    @FXML
    ChoiceBox chcbxStdntSlct;
    @FXML
    ChoiceBox chcbxAddEdtGrd;
    @FXML
    ChoiceBox chcbxStdntGrds;
    @FXML
    Button btnAddStdnt;
    @FXML
    TextField tfEntrStdntNm;
    @FXML
    TextField tfErndPts;
    @FXML
    TextField tfTtlPts;


    @Override
    public void initialize(URL location, ResourceBundle resources) {//generates choiceboxes and sets default values

        //TODO ADD STUDENTS/GRADES TO SELECTION BOXES AFTER LOADING A FILE
        chcbxStdntSlct.getItems().removeAll(chcbxStdntSlct.getItems());
        chcbxStdntSlct.getItems().add(DFLT_STDNT);
        chcbxStdntSlct.setValue(DFLT_STDNT);

        chcbxAddEdtGrd.getItems().removeAll(chcbxAddEdtGrd.getItems());
        chcbxAddEdtGrd.getItems().add(DFLT_ADD_EDT);
        chcbxAddEdtGrd.getItems().add(EDIT_GRADE);
        chcbxAddEdtGrd.getItems().add(ADD_HWGRADE);
        chcbxAddEdtGrd.getItems().add(ADD_TESTGRADE);
        chcbxAddEdtGrd.setValue(DFLT_ADD_EDT);//default value of choicebox



    }

    @FXML
    private void addStdntClckd(){
        String nameInput = tfEntrStdntNm.getText();

        if(Validation.checkName(nameInput)){
            studentController.addStudent(new Student(nameInput));
            tfEntrStdntNm.getStyleClass().remove("invalid-field");
            int lastindex = studentController.getStudents().size();
            String thisStudentName = studentController.getStudents().get(lastindex-1).getStudentName();
            chcbxStdntSlct.getItems().add(thisStudentName);
            tfEntrStdntNm.clear();

        }
        else{
            tfEntrStdntNm.getStyleClass().add("invalid-field");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setContentText("Student Name already in use");
            alert.showAndWait();
        }



    }
    @FXML
    private void stdntSlcted(){
        System.out.println("Student selection box changed");

    }
    @FXML
    private void addEditGrdClckd(){
        Object dfltOptnStdntSlct = chcbxStdntSlct.getItems().get(0);
        Object dfltOptnAddEdtGrd = chcbxAddEdtGrd.getItems().get(0);
        Object crntOptnStdntSlct = chcbxStdntSlct.getValue();
        Object crntOptnAddEdtGrd = chcbxAddEdtGrd.getValue();

        //chcbxAddEdtGrd
        if (crntOptnStdntSlct == dfltOptnStdntSlct||crntOptnAddEdtGrd == dfltOptnAddEdtGrd ){  //check if both student and add/edit have been selected with non-default values
            generateError();
            if(crntOptnStdntSlct == dfltOptnStdntSlct){
                chcbxStdntSlct.getStyleClass().add("invalid-field");
            }
            else{
                chcbxStdntSlct.getStyleClass().remove("invalid-field");
            }
            if(crntOptnAddEdtGrd == dfltOptnAddEdtGrd){
                chcbxAddEdtGrd.getStyleClass().add("invalid-field");
            }
            else{
                chcbxAddEdtGrd.getStyleClass().remove("invalid-field");
            }

        }

        else if(crntOptnAddEdtGrd.equals(ADD_HWGRADE)|| crntOptnAddEdtGrd.equals(ADD_TESTGRADE) ||crntOptnAddEdtGrd.equals(EDIT_GRADE)){//adding grade
            Student theStudent= null; //get the student selected
            try{
                theStudent = studentController.findStudent(crntOptnStdntSlct.toString());
                System.out.println("Student found");
            }
            catch(Exception e){
                e.printStackTrace();
                System.out.println("Memory integrity error, selection exists where student does not");
            }

            if(crntOptnAddEdtGrd.equals(EDIT_GRADE)){//generation of grades have taken place, now validate the choice box/integer grade inputs
                List<String> stdntGrds = generateStndtGrades(theStudent);
                chcbxStdntGrds.setDisable(false);//activate choicebox for selection
                chcbxStdntGrds.getItems().removeAll(chcbxStdntGrds.getItems());
                for (String grade: stdntGrds){
                    chcbxStdntGrds.getItems().add(DFLT_GRDS);
                    chcbxAddEdtGrd.getItems().add(EDIT_GRADE);
                    chcbxAddEdtGrd.getItems().add(ADD_HWGRADE);
                    chcbxAddEdtGrd.getItems().add(ADD_TESTGRADE);
                    chcbxAddEdtGrd.setValue(DFLT_GRDS);//default value of choicebox

                }


            }

            //User supplied point value validation
            if(!(Validation.isInteger(tfErndPts.getText())) || !(Validation.isInteger(tfTtlPts.getText()))){//are they integers
                generateError("Please supply integer values at highlighted fields");
                if(!(Validation.isInteger((tfErndPts.getText())))){
                    tfErndPts.getStyleClass().add("invalid-field");
                }
                else{
                    tfErndPts.getStyleClass().remove("invalid-field");
                }
                if(!(Validation.isInteger((tfTtlPts.getText())))){
                    tfTtlPts.getStyleClass().add("invalid-field");
                }
                else{
                    tfTtlPts.getStyleClass().remove("invalid-field");
                }

            }
            else if(!(Validation.checkPtValues(Integer.parseInt(tfErndPts.getText()), Integer.parseInt(tfTtlPts.getText())))){//are earned points greater than total points
                generateError("Earned points must be less than or equal to total points");
                tfErndPts.getStyleClass().add("invalid-field");
                tfTtlPts.getStyleClass().add("invalid-field");
            }
            else{//validation completed for point values, add/edit grade to Student depending on type
                String outputMsg = "";
                if (crntOptnAddEdtGrd.equals(ADD_HWGRADE)){
                    e_GradeType gradeType = e_GradeType.HW_GRADE;
                    outputMsg = addGrade(gradeType, theStudent);
                }
                else{
                    e_GradeType gradeType = e_GradeType.TEST_GRADE;
                    outputMsg = addGrade(gradeType, theStudent);


                }

                formCompletion(outputMsg);
            }




            //TODO clear all potential lingering error indicators on form
        }







            //tfEntrStdntNm.getStyleClass().remove("student-invalid-field");



    }

        /*else if(!(crntOptnAddEdtGrd.equals(EDIT_GRADE))){//editing grade

            //TODO generate and enable another selection box that contains all grades for the student
            Student theStudent= null;
            try{
               // theStudent = studentController.findStudent(crntOptnStdntSlct);
            }
            catch(Exception e){
                e.printStackTrace();
                System.out.println("Memory integrity error, selection exists where student does not");
            }


        }
        */

    private String addGrade(e_GradeType gradeType, Student theStudent){
        String outputMsg = "";
        if(gradeType == e_GradeType.HW_GRADE){
            Integer hwcount = 1;
            try{
                hwcount += theStudent.getHwGrades().size();//TODO not capturing increased size
            }
            catch(Exception e){

            }
            String hwname = "HW" + hwcount;//prepare hw name
            theStudent.addGrade(new HwGrade(hwname, Integer.parseInt(tfErndPts.getText()), Integer.parseInt(tfTtlPts.getText())));
            outputMsg = hwname +" added for student: "+ theStudent.getStudentName();

        }
        else if(gradeType == e_GradeType.TEST_GRADE){
            Integer testcount = 1;
            try{
                testcount += theStudent.getTestGrades().size();//TODO not capturing increased size
            }
            catch(Exception e){

            }
            String testName = "Test" + testcount;//prepare test name
            theStudent.addGrade(new TestGrade(testName, Integer.parseInt(tfErndPts.getText()), Integer.parseInt(tfTtlPts.getText())));
            outputMsg = testName +" added for student: "+ theStudent.getStudentName();

        }
        else{
            outputMsg = "An Error has occured";
        }


        return outputMsg;

    }



    private void formCompletion(String outputMsg){
        tfErndPts.getStyleClass().remove("invalid-field");//information confirmed, infrom the user and remove potential invalid indicator styles on gui elements
        tfTtlPts.getStyleClass().remove("invalid-field");
        chcbxAddEdtGrd.getStyleClass().remove("invalid-field");
        chcbxStdntSlct.getStyleClass().remove("invalid-field");
        tfErndPts.clear();
        tfTtlPts.clear();
        System.out.println((outputMsg));


    }
    private void generateError(String contentText){//sets context of error for user clarification
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Selection/Input Error");
        alert.setContentText(contentText);
        alert.showAndWait();
    }
    private void generateError(){//generic error
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Selection/Input Error");
        alert.setContentText("Please check choices/inputs at highlighted fields");
        alert.showAndWait();
    }
    private List<String> generateStndtGrades(Student theStudent){//returns the names of all the Students grades to populate the choicebox
        List<String> stdntGrds = new ArrayList<>();
        try{
            for(HwGrade hwGrade : theStudent.getHwGrades())
                stdntGrds.add(hwGrade.getName());
            for(TestGrade testGrade: theStudent.getTestGrades()){
                stdntGrds.add(testGrade.getName());
            }
        }
        catch(NullPointerException e){
            System.out.println("No grades exist");
        }
        return stdntGrds;
    }


}