package root.student;

public class TestGrade extends Grade {//used to differentiate weights and provide readability
    private static final double WEIGHT = 0.7;
    public TestGrade(String testName, int testGrade, int totalPts) {
        super(testName, testGrade, totalPts);
    }
    public double getWeight(){
        return this.WEIGHT;
    }
}
