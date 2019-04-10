package root;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import root.controllers.StdntController;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import root.student.Student;

import java.net.URL;
import java.util.ResourceBundle;


public class GradeFormCtrler implements Initializable {
    StdntController studentController = StdntController.getStdntController();
    @FXML
    ChoiceBox chcbxStdntSlct;
    @FXML
    ChoiceBox chcbxAddEdtGrd;
    @FXML
    Button btnAddStdnt;
    @FXML
    TextField tfEntrStdntNm;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //TODO ADD STUDENTS/GRADES TO SELECTION BOXES AFTER LOADING A FILE
        chcbxStdntSlct.getItems().removeAll(chcbxStdntSlct.getItems());
        ObservableList<String> dfltVal1 = FXCollections.observableArrayList("Select Student");
        chcbxStdntSlct.setItems(dfltVal1);
        chcbxStdntSlct.setValue("Select Student");

        chcbxAddEdtGrd.getItems().removeAll(chcbxAddEdtGrd.getItems());
        ObservableList<String> dfltVal2 = FXCollections.observableArrayList("Edit/Add Grade");
        chcbxAddEdtGrd.setItems(dfltVal2);
        chcbxAddEdtGrd.setValue("Edit/Add Grade");
    }

    @FXML
    private void addStdntClckd(){
        String nameInput = tfEntrStdntNm.getText();

        if(Validation.checkName(nameInput)){
            studentController.addStudent(new Student(nameInput));
            tfEntrStdntNm.getStyleClass().remove("student-invalid-field");
            int lastindex = studentController.getStudents().size();
            String thisStudentName = studentController.getStudents().get(lastindex-1).getStudentName();
            chcbxStdntSlct.getItems().add(thisStudentName);
            tfEntrStdntNm.clear();

        }
        else{
            tfEntrStdntNm.getStyleClass().add("student-invalid-field");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setContentText("Student Name already in use");
            alert.showAndWait();
        }



    }
    @FXML
    private void addEditGrdClckd(){
        //check if student is selected
        String defaultOption = chcbxStdntSlct.getItems().get(0).toString();
        String currentOption = chcbxStdntSlct.getValue().toString();
        if (currentOption == defaultOption){
            System.out.println("Debugging");
        }

    }

}