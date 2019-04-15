package root.student;

import java.io.Serializable;

public class HwGrade extends Grade implements Serializable {//used to differentiate weights and provide readability
    private static final double WEIGHT = 0.3;
    public HwGrade(String hwName, int earnedPts, int totalPts){
        super(hwName, earnedPts, totalPts);
    }
    public static double getWeight(){
        return WEIGHT;
    }

}
