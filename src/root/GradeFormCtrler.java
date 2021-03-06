package root;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import root.controllers.StdntController;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import root.student.Grade;
import root.student.HwGrade;
import root.student.Student;
import root.student.TestGrade;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class GradeFormCtrler implements Initializable {

    enum e_GradeType
    {
        HW_GRADE,
        TEST_GRADE
    }
    enum e_GradeFormat{
        LTR_GRADE,
        PCT_GRADE
    }
    private final String DFLT_STDNT = "Select Student"; //initial choicebox items
    private final String DFLT_ADD_EDT = "Edit/Add Grade";
    private final String EDIT_GRADE = "Edit Grade";
    private final String ADD_HWGRADE = "Add Homework Grade";
    private final String ADD_TESTGRADE = "Add Test Grade";
    private final String DFLT_GRDS = "Select Grade";


    StdntController studentController = StdntController.getStdntController();
    GradeToolFileIO gradeToolFileIO = GradeToolFileIO.getGradeToolFileIO();
    @FXML
    ChoiceBox chcbxStdntSlct;
    @FXML
    ChoiceBox chcbxAddEdtGrd;
    @FXML
    ChoiceBox chcbxStdntGrds;
    @FXML
    Button btnAddStdnt;
    @FXML
    Button btnClcLtrGrd;
    @FXML
    Button btnClcPrctgGrd;
    @FXML
    TextField tfEntrStdntNm;
    @FXML
    TextField tfErndPts;
    @FXML
    TextField tfTtlPts;
    @FXML
    TextArea taOutput;
    @FXML
    Button btnDltGrd;



    @Override
    public void initialize(URL location, ResourceBundle resources) {//generates choiceboxes and sets default values
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
    private void clcPctGrdClckd(){//TODO FIX ME

        Object dfltOptnStdntSlct = chcbxStdntSlct.getItems().get(0);
        Object crntOptnStdntSlct = chcbxStdntSlct.getValue();


        if(!(crntOptnStdntSlct == dfltOptnStdntSlct)){
            Student theStudent = studentController.getStudent(crntOptnStdntSlct);
            formCompletion(computeGrade(theStudent, e_GradeFormat.PCT_GRADE));
        }
        else{
            chcbxStdntSlct.getStyleClass().add("invalid-field");
            generateError("Select a student");
        }


    }

    @FXML
    private void clcLtrGrdClckd(){
        Object dfltOptnStdntSlct = chcbxStdntSlct.getItems().get(0);
        Object crntOptnStdntSlct = chcbxStdntSlct.getValue();


        if(!(crntOptnStdntSlct == dfltOptnStdntSlct)){
            Student theStudent = studentController.getStudent(crntOptnStdntSlct);
            formCompletion(computeGrade(theStudent, e_GradeFormat.LTR_GRADE));
        }
        else{
            chcbxStdntSlct.getStyleClass().add("invalid-field");
            generateError("Select a student");
        }

    }
    @FXML
    private void btnDltGrdClckd(){
        Object dfltOptnStdntSlct = chcbxStdntSlct.getItems().get(0);
        Object crntOptnStdntSlct = chcbxStdntSlct.getValue();
        Object dfltOptnAddEdtGrd = chcbxAddEdtGrd.getItems().get(0);
        Object crntOptnAddEdtGrd = chcbxAddEdtGrd.getValue();



        if(!(crntOptnStdntSlct == dfltOptnStdntSlct) && crntOptnAddEdtGrd.equals(EDIT_GRADE) && !(chcbxStdntGrds.isDisabled()) && !(chcbxStdntGrds.getValue().equals(DFLT_GRDS)) ){
            Object crntOptnGrdSlct = chcbxStdntGrds.getValue();
            Student theStudent = studentController.getStudent(crntOptnStdntSlct);
            HwGrade hwGrade = null;
            TestGrade testGrade = null;

            hwGrade = theStudent.findHWGrade(crntOptnGrdSlct.toString());
            testGrade = theStudent.findTestGrade(crntOptnGrdSlct.toString());

            if(hwGrade == null){
                theStudent.rmGrade(testGrade);
                chcbxStdntGrds.getItems().remove(crntOptnGrdSlct);
            }
            else{
                theStudent.rmGrade(hwGrade);
                chcbxStdntGrds.getItems().remove(crntOptnGrdSlct);
            }
            formCompletion("Grade removed");
        }
        else{
            if(crntOptnStdntSlct == dfltOptnStdntSlct){
                chcbxStdntSlct.getStyleClass().add("invalid-field");
                generateError("Select a student");
            }
            else if(!(crntOptnAddEdtGrd.equals(EDIT_GRADE))){
                chcbxAddEdtGrd.getStyleClass().add("invalid-field");
                generateError("Select 'Edit Grade' for deletions");
            }
            else if(chcbxStdntGrds.equals(DFLT_GRDS)){
                chcbxStdntGrds.getStyleClass().add("invalid-field");
                generateError("Select a grade for deletion");
            }

        }


    }
    @FXML
    private void chcbxStdntClicked(){//if student selected, checks if edit grade selected
        try {
            gradeGenerator();
        }
        catch(IndexOutOfBoundsException e){
            System.out.println("File loaded potentially causing trigger");
        }
    }

    @FXML
    private void chcbxEdtGrdClicked(){//if edit grade selected, checks if student is selected
        try {
            gradeGenerator();
        }
        catch(IndexOutOfBoundsException e){
            System.out.println("File loaded potentially causing trigger");
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



        else if(crntOptnAddEdtGrd.equals(ADD_HWGRADE)|| crntOptnAddEdtGrd.equals(ADD_TESTGRADE) || crntOptnAddEdtGrd.equals(EDIT_GRADE)) {//Validation and adding/editing grade
            Student theStudent = studentController.getStudent(crntOptnStdntSlct);
            HwGrade theHwGrade = null;//used for edit grade only
            TestGrade theTestGrade = null;//used for edit grade only
            boolean gradeSelected = false;
            if(crntOptnAddEdtGrd.equals(EDIT_GRADE) && !(chcbxStdntGrds.isDisabled())){//student selected, and edit selected, check if grade selected
                Object dfltOptnGrdSlct = chcbxStdntGrds.getItems().get(0);
                Object crntOptnGrdSlct = chcbxStdntGrds.getValue();
                if(crntOptnGrdSlct == dfltOptnGrdSlct){
                    generateError("Select a student's grade");//generate error
                    chcbxStdntGrds.getStyleClass().add("invalid-field");

                }
                else{
                    try{
                        theHwGrade = theStudent.findHWGrade(crntOptnGrdSlct.toString());
                    }
                    catch(Exception e){

                    }
                    try{
                        theTestGrade = theStudent.findTestGrade(crntOptnGrdSlct.toString());
                    }
                    catch(Exception e){

                    }

                    System.out.println("Grade Found");
                    gradeSelected = true;
                    chcbxStdntGrds.getStyleClass().remove("invalid-field");
                }
            }

            if (!(Validation.isInteger(tfErndPts)) || !(Validation.isInteger(tfTtlPts))) {
                if (!(Validation.isInteger(tfErndPts))) {
                    tfErndPts.getStyleClass().add("invalid-field");
                    generateError("Please supply integer values at highlighted fields");
                }
                else {
                    tfErndPts.getStyleClass().remove("invalid-field");
                }
                if (!(Validation.isInteger(tfTtlPts))) {
                    tfTtlPts.getStyleClass().add("invalid-field");
                    generateError("Please supply integer values at highlighted fields");
                }
                else {
                    tfTtlPts.getStyleClass().remove("invalid-field");
                }

            }
            else if(!(Validation.checkPtValues(tfErndPts, tfTtlPts))){
                    tfErndPts.getStyleClass().add("invalid-field");
                    tfTtlPts.getStyleClass().add("invalid-field");
                    generateError("Earned points must be less than or equal to total points");
            }


            else{//validation completed for point values, add/edit grade to Student depending on type
                String outputMsg = "";
                if (crntOptnAddEdtGrd.equals(EDIT_GRADE) && gradeSelected == true){
                    if(theHwGrade == null){//edit test grade
                        theStudent.editGrade(theTestGrade, Integer.parseInt(tfErndPts.getText()),Integer.parseInt(tfTtlPts.getText()));//points have been validated that they can be casted at this point
                        outputMsg += (theTestGrade.getName() +" for student: "+ theStudent.getStudentName() +" changed to: " + theTestGrade.getEarnedPts() + "/" + theTestGrade.getTotalPoints());
                    }
                    else{//edit hw grade
                        theStudent.editGrade(theHwGrade,Integer.parseInt(tfErndPts.getText()),Integer.parseInt(tfTtlPts.getText()) );//points have been validated that they can be casted at this point
                        outputMsg += (theHwGrade.getName() +" for student: "+ theStudent.getStudentName() +" changed to: " + theHwGrade.getEarnedPts() + "/" + theHwGrade.getTotalPoints() );
                    }


                }
                else if (crntOptnAddEdtGrd.equals(ADD_HWGRADE)){
                    e_GradeType gradeType = e_GradeType.HW_GRADE;
                    outputMsg = addGrade(gradeType, theStudent);
                }
                else{
                    e_GradeType gradeType = e_GradeType.TEST_GRADE;
                    outputMsg = addGrade(gradeType, theStudent);


                }

                formCompletion(outputMsg);
            }



        }








    }

    private String computeGrade(Student theStudent, e_GradeFormat type){//returns string to be used in output
        String output = "";
        double hwGradeEarned = 0;
        double hwGradeTotal = 0;
        for(HwGrade hwgrade: theStudent.getHwGrades()){
            hwGradeEarned += hwgrade.getEarnedPts();
            hwGradeTotal += hwgrade.getTotalPoints();

        }

        double testGradeEarned = 0;
        double testGradeTotal = 0;
        for(TestGrade testGrade: theStudent.getTestGrades()){
            testGradeEarned += testGrade.getEarnedPts();
            testGradeTotal += testGrade.getTotalPoints();
        }
        double hwWeightedAvg = (hwGradeEarned/hwGradeTotal)*HwGrade.getWeight();
        double testWeightedAvg = (testGradeEarned/testGradeTotal)*TestGrade.getWeight();
        switch (type) {

            case PCT_GRADE: {

                DecimalFormat df = new DecimalFormat("##.##%");//provide formatting for clarity

                //don't provide a grade if none exist
                if (theStudent.getHwGrades().size() == 0 && theStudent.getTestGrades().size() == 0) {
                    output += "No grades exist for the selected student";
                    return output;
                }
                //don't provide weight if there are only test grades or only homework grades
                else if (theStudent.getTestGrades().size() == 0) {
                    output += theStudent.getStudentName() + ":  percentage grade = " + df.format(hwWeightedAvg / HwGrade.getWeight());
                }
                else if (theStudent.getHwGrades().size() == 0) {
                    output += theStudent.getStudentName() + ": percentage grade = " + df.format(testWeightedAvg / TestGrade.getWeight());
                }

                //provide the weighted average
                else {
                    String formattedWeightedGrd = df.format(hwWeightedAvg + testWeightedAvg);
                    output += theStudent.getStudentName() + ": percentage grade = " + formattedWeightedGrd;

                }
                break;
            }

            case LTR_GRADE: {
                //calculate the letter grade
                double decimalGrade = 0.0;
                //don't provide a grade if none exist
                if (theStudent.getHwGrades().size() == 0 && theStudent.getTestGrades().size() == 0) {
                    output += "No grades exist for the selected student";
                    return output;
                }
                else if (theStudent.getTestGrades().size() == 0) {
                    decimalGrade = hwWeightedAvg / HwGrade.getWeight();
                }
                else if (theStudent.getHwGrades().size() == 0) {
                    decimalGrade = testWeightedAvg / TestGrade.getWeight();
                }
                //provide the weighted average
                else {
                    decimalGrade = hwWeightedAvg + testWeightedAvg;

                }
                //find the letter grade equivalent to the decimal
                String ltrGrd = "";
                if (decimalGrade < .65) {
                    ltrGrd = "F";

                } else if (decimalGrade <= .66) {
                    ltrGrd = "D";
                } else if (decimalGrade <= .69) {
                    ltrGrd = "D+";
                } else if (decimalGrade <= .72) {
                    ltrGrd = "C-";
                } else if (decimalGrade <= .76) {
                    ltrGrd = "C";
                } else if (decimalGrade <= .79) {
                    ltrGrd = "C+";
                } else if (decimalGrade <= .82) {
                    ltrGrd = "B-";
                } else if (decimalGrade <= .86) {
                    ltrGrd = "B";
                } else if (decimalGrade <= .89) {
                    ltrGrd = "B+";
                } else if (decimalGrade <= .92) {
                    ltrGrd = "A-";
                } else if (decimalGrade <= .96) {
                    ltrGrd = "A";
                } else if (decimalGrade <= 1.0) {
                    ltrGrd = "A+";
                }
                output += theStudent.getStudentName() + ": Letter Grade = " + ltrGrd;
                break;

            }
        }
        return output;

    }




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


    private void formCompletion(String outputMsg){//set application to defaults for control of user actions
        tfErndPts.getStyleClass().remove("invalid-field");//information confirmed, infrom the user and remove potential invalid indicator styles on gui elements
        tfTtlPts.getStyleClass().remove("invalid-field");
        chcbxAddEdtGrd.getStyleClass().remove("invalid-field");
        chcbxStdntSlct.getStyleClass().remove("invalid-field");
        chcbxStdntGrds.getStyleClass().remove("invalid-field");
        chcbxStdntGrds.setDisable(true);
        chcbxAddEdtGrd.setValue(DFLT_ADD_EDT);
        chcbxStdntSlct.setValue(DFLT_STDNT);
        tfErndPts.clear();
        tfTtlPts.clear();
        btnDltGrd.setDisable(true);
        taOutput.appendText(outputMsg + "\n");
        System.out.println((outputMsg));


        //UNIT TEST
        System.out.println("\n\n\n");
        for(Student student: studentController.getStudents()){
            for(HwGrade hwGrade: student.getHwGrades()){
                System.out.println("Student name: " + student.getStudentName() + ",  " +  hwGrade.getName() + ":" + hwGrade.getEarnedPts() + "//" +hwGrade.getTotalPoints());

            }
            for(TestGrade testGrade: student.getTestGrades()){
                System.out.println("Student name: " + student.getStudentName() + ",  " +  testGrade.getName() + ":" + testGrade.getEarnedPts() + "//" +testGrade.getTotalPoints());

            }
        }


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
    private void gradeGenerator(){//generates student grades in grade selection box and activates grade selection and deletion button
        Object dfltOptnStdntSlct = chcbxStdntSlct.getItems().get(0);
        Object crntOptnStdntSlct = chcbxStdntSlct.getValue();

        if(!(crntOptnStdntSlct == dfltOptnStdntSlct) && chcbxAddEdtGrd.getValue().equals(EDIT_GRADE)){
            Student theStudent = studentController.getStudent(crntOptnStdntSlct);
            List<String> stdntGrds = Student.generateStndtGrades(theStudent);
            chcbxStdntGrds.setDisable(false);//activate choicebox for selection
            chcbxStdntGrds.getItems().removeAll(chcbxStdntGrds.getItems());
            chcbxStdntGrds.getItems().add(DFLT_GRDS);
            for (String grade : stdntGrds) {
                chcbxStdntGrds.getItems().add(grade);
            }
            chcbxStdntGrds.setValue(DFLT_GRDS);
            btnDltGrd.setDisable(false);//activates deletion button
        }

    }

    //file saving/loading
    private void loadOperations(){//remove old data add file's data to update gui selection boxes
        chcbxStdntSlct.getItems().removeAll(chcbxStdntSlct.getItems());
        chcbxStdntSlct.getItems().add(DFLT_STDNT);
        chcbxStdntGrds.getItems().removeAll(chcbxStdntGrds.getItems());
        chcbxStdntGrds.getItems().add(DFLT_GRDS);
        chcbxStdntSlct.setValue(DFLT_STDNT);
        formCompletion("Loaded File successfully");
        for (Student student: studentController.getStudents()){
            chcbxStdntSlct.getItems().add(student.getStudentName());
        }
    }



    @FXML
    private void openConfigFile() {//Opens a file for the user to generate a fresh StdntController

        FileChooser fileChooser = new FileChooser();//Allows opening and saving files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("gt cfg file (*.gtcfg)", "*.gtcfg"); //sets extension filter
        fileChooser.getExtensionFilters().add(extFilter);
        String pathname = "/courses";
        Path requiredPath = Paths.get("/courses");
        if(!(Files.exists(requiredPath))){
            new File("/courses").mkdirs();
        }
        File savedFiles = new File(pathname);
        fileChooser.setInitialDirectory(savedFiles);
        try {
            Scene scene = btnAddStdnt.getScene();//grabs the scene from the window that initialized this event,  required for file selector
            if (scene != null) {
                Window window = scene.getWindow();
                if (window != null) {

                    File file = fileChooser.showOpenDialog(window);
                    if (file != null) {

                        String filePath = file.getAbsolutePath();
                        studentController.clear();//wipe current memory
                        studentController = gradeToolFileIO.openFile(filePath);

                        //UNIT TEST CHECK CONTROLLER STATUS
                        System.out.println("\n\n\n");
                        for(Student student: studentController.getStudents()){
                            for(HwGrade hwGrade: student.getHwGrades()){
                                System.out.println("Student name: " + student.getStudentName() + ",  " +  hwGrade.getName() + ":" + hwGrade.getEarnedPts() + "//" +hwGrade.getTotalPoints());

                            }
                            for(TestGrade testGrade: student.getTestGrades()){
                                System.out.println("Student name: " + student.getStudentName() + ",  " +  testGrade.getName() + ":" + testGrade.getEarnedPts() + "//" +testGrade.getTotalPoints());

                            }
                        }

                        loadOperations();//reset students for gui
                    }
                }

            }
        }
        catch(Exception e){
            System.out.println("File generation error");

        }

    }

    @FXML
    private void saveConfigFile() {//saves current students, and their grades to file
        FileChooser fileChooser = new FileChooser();//Allows opening and saving files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("gt cfg file (*.gtcfg)", "*.gtcfg"); //sets extension filter
        fileChooser.getExtensionFilters().add(extFilter);
        //check if folder exists, if not, make one
        String pathname = "/courses";
        Path requiredPath = Paths.get("/courses");
        if(!(Files.exists(requiredPath))){
            new File("/courses").mkdirs();
        }
        File savedFiles = new File("/courses");
        fileChooser.setInitialDirectory(savedFiles);
        Scene scene = btnAddStdnt.getScene();//grabs the scene from the window that initialized this event,  required for file selector
        if (scene != null) {
            Window window = scene.getWindow();
            if (window != null) {
                File file = fileChooser.showSaveDialog(window);
                if (file != null) {

                    String filePath = file.getAbsolutePath();
                    gradeToolFileIO.writeFile(studentController, filePath);

                }
            }

        }
    }




}