package root.student;

import java.util.ArrayList;
import java.util.List;

public class Student {
    List<TestGrade> _testGrades = new ArrayList<TestGrade>();
    List<HwGrade> _hwGrades = new ArrayList<HwGrade>();
    private String _studentName;
    private int _totalhwpts;
    private int _earnedhwpts;
    private int _totaltestpoints;
    private int _earnedtestpoints;


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
            this._testGrades.remove(tempHwGrade);
        }
    }
    public void addGrade(TestGrade testGrade){
        this._testGrades.add(testGrade);
    }
    public void addGrade(HwGrade hwGrade){
        this._hwGrades.add(hwGrade);
    }
    public void editGrade(TestGrade testGrade, int adjustedPts){
        testGrade.setEarnedPts(adjustedPts);
    }
    public void editGrade(HwGrade hwGrade, int adjustedPts){
        hwGrade.setEarnedPts(adjustedPts);
    }
    public List<HwGrade> getHwGrades(){
        return this._hwGrades;
    }
    public List<TestGrade> getTestGrades(){
        return this._testGrades;
    }
    public String getStudentName(){
        return this._studentName;
    }
}