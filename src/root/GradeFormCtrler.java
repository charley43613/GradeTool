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
import java.util.ResourceBundle;


public class GradeFormCtrler implements Initializable {

    private static final String DFLT_STDNT = "Select Student"; //initial choicebox items
    private static final String DFLT_ADD_EDT = "Edit/Add Grade";
    private static final String EDIT_GRADE = "Edit Grade";
    private static final String ADD_HWGRADE = "Add Homework Grade";
    private static final String ADD_TESTGRADE = "Add Test Grade";


    StdntController studentController = StdntController.getStdntController();
    @FXML
    ChoiceBox chcbxStdntSlct;
    @FXML
    ChoiceBox chcbxAddEdtGrd;
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
            if(crntOptnAddEdtGrd == dfltOptnAddEdtGrd){
                chcbxAddEdtGrd.getStyleClass().add("invalid-field");
            }

        }

        else if(crntOptnAddEdtGrd.equals(ADD_HWGRADE)|| crntOptnAddEdtGrd.equals(ADD_TESTGRADE)){//adding grade
            Student theStudent= null; //get the student selected
            try{
                theStudent = studentController.findStudent(crntOptnStdntSlct.getClass().toString());
                System.out.println("Student found");
            }
            catch(Exception e){
                e.printStackTrace();
                System.out.println("Memory integrity error, selection exists where student does not");
            }

            //User supplied point value validation
            if(!(Validation.isInteger(tfErndPts.getText()) || !(Validation.isInteger(tfTtlPts.getText())))){//are they integers
                generateError("Please supply integer values at highlighted fields");
                if(!(Validation.isInteger((tfErndPts.getText())))){
                    tfErndPts.getStyleClass().add("invalid-field");
                }
                if(!(Validation.isInteger((tfTtlPts.getText())))){
                    tfTtlPts.getStyleClass().add("invalid-field");
                }

            }
            else if(!(Validation.checkPtValues(Integer.parseInt(tfErndPts.getText()), Integer.parseInt(tfTtlPts.getText())))){//are earned points greater than total points
                generateError("Earned points must be less than or equal to total points");
                tfErndPts.getStyleClass().add("invalid-field");
                tfTtlPts.getStyleClass().add("invalid-field");
            }
            else{//validation completed for point values
                if (crntOptnAddEdtGrd.equals(ADD_HWGRADE)){//add hw grade
                    String hwname = "HW" + theStudent.getHwGrades().size();//prepare hw name
                    theStudent.addGrade(new HwGrade(hwname, Integer.parseInt(tfErndPts.getText()), Integer.parseInt(tfTtlPts.getText())));

                }
                else{                                      //add test grade
                    String testname = "TEST" + theStudent.getTestGrades().size();//prepare hw name
                    theStudent.addGrade(new TestGrade(testname, Integer.parseInt(tfErndPts.getText()), Integer.parseInt(tfTtlPts.getText())));
                }

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

}