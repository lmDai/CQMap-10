package android.zhixun.uiho.com.gissystem.flux.models.api;

import java.util.List;

/**
 * Created by parcool on 2016/9/5.
 */

public class BaseListResultModel<T> {
    private int count;//记录条数
    private String responseCode;
    private String responseMsg;
    private long datetime;
    private List<T> object;


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public List<T> getObject() {
        return object;
    }

    public void setObject(List<T> object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return "count=" + count + ",responseCode=" + responseCode + ",responseMsg=" + responseMsg + ",T.size()=" + object.size() + ",T=" + object.toString();
    }
}