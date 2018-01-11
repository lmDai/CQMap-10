package android.zhixun.uiho.com.gissystem.flux.models;

/**
 * Created by zp on 2016/11/29.
 */

public class UnitCensorShipModel {
    private  String examiner;
    private String checkTime;
    private int state;
    private String tempCheckPerson;//临时参检人员

    public UnitCensorShipModel() {

    }

    public UnitCensorShipModel(String examiner, String checkTime, int state, String tempCheckPerson) {
        this.examiner = examiner;
        this.checkTime = checkTime;
        this.state = state;
        this.tempCheckPerson = tempCheckPerson;
    }

    public String getTempCheckPerson() {
        return tempCheckPerson;
    }

    public void setTempCheckPerson(String tempCheckPerson) {
        this.tempCheckPerson = tempCheckPerson;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getExaminer() {
        return examiner;
    }

    public void setExaminer(String examiner) {
        this.examiner = examiner;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }
}
