package root.student;

public abstract class Grade {

    String _name;
    int _earnedPts;
    int _totalPoints;


    Grade(String name, int earnedPts, int _totalPoints){
        this._name = name;
        this._earnedPts = earnedPts;
        this._totalPoints = _totalPoints;
    }
    public void setEarnedPts(int _earnedPts){
        this._earnedPts = _earnedPts;
    }
    public void setTotalPts(int _totalPoints){
        this._totalPoints = _totalPoints;
    }
    public int getEarnedPts(){
        return this._earnedPts;
    }
    public int getTotalPoints(){
        return this._totalPoints;
    }
    public String getName(){ return this._name;}

}
