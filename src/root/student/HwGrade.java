package root.student;

public class HwGrade extends Grade {//used to differentiate weights and provide readability
    private static final double WEIGHT = 0.3;
    public HwGrade(String hwName, int earnedPts, int totalPts){
        super(hwName, earnedPts, totalPts);
    }
    public double getWeight(){
        return this.WEIGHT;
    }

}
