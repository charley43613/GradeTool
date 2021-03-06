package root.student;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Student implements Serializable {
    List<TestGrade> _testGrades = new ArrayList<TestGrade>();
    List<HwGrade> _hwGrades = new ArrayList<HwGrade>();
    private String _studentName;



    public Student(String studnetName){
        this._studentName = studnetName;
    }


    public void rmGrade(TestGrade testGrade){
        TestGrade tempTestGrade = null;
        for(TestGrade tg: this._testGrades){
            if (testGrade.getName().equals(tg.getName())){
                tempTestGrade = tg;
            }
        }
        if (testGrade != null){
            this._testGrades.remove(testGrade);
        }
    }
    public void rmGrade(HwGrade hwGrade){
        HwGrade tempHwGrade = null;
        for(HwGrade hg: this._hwGrades){
            if (hg.getName().equals(hwGrade.getName())){
                tempHwGrade = hwGrade;
            }
        }
        if (tempHwGrade != null){
            this._hwGrades.remove(tempHwGrade);
        }
    }
    public void addGrade(TestGrade testGrade){
        this._testGrades.add(testGrade);
    }
    public void addGrade(HwGrade hwGrade){
        this._hwGrades.add(hwGrade);
    }
    public void editGrade(TestGrade testGrade, int earnedPts, int ttlPts){

        for(TestGrade stdnTestGrd: _testGrades){
            if (testGrade.equals(stdnTestGrd)){
                testGrade.setEarnedPts(earnedPts);
                testGrade.setTotalPts(ttlPts);
                System.out.println("Test grade updated");
            }
        }

    }
    public void editGrade(HwGrade hwGrade, int earnedPts, int ttlPts){
        for(HwGrade stdnHwGrd: _hwGrades){
            if (hwGrade.equals(stdnHwGrd)){
                hwGrade.setEarnedPts(earnedPts);
                hwGrade.setTotalPts(ttlPts);
                System.out.println("HW grade updated");
            }
        }
    }
    public List<HwGrade> getHwGrades(){
        return this._hwGrades;
    }
    public List<TestGrade> getTestGrades(){
        return this._testGrades;
    }
    public static List<String> generateStndtGrades(Student theStudent){//returns the names of all the Students grades to populate the choicebox
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
    public String getStudentName(){
        return this._studentName;
    }
    public HwGrade findHWGrade(String gradeName){
        HwGrade thehwGrade = null;
        for(HwGrade hwGrade : _hwGrades){
            if(gradeName.equals(hwGrade.getName())){
                thehwGrade = hwGrade;
            }
        }
        return thehwGrade;
    }
    public TestGrade findTestGrade(String testName){
        TestGrade thetestGrade = null;
        for(TestGrade testGrade : _testGrades){
            if(testName.equals(testGrade.getName())){
                thetestGrade = testGrade;
            }
        }
        return thetestGrade;
    }



}
