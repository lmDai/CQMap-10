package android.zhixun.uiho.com.gissystem.flux.models.api;


/**
 * Created by parcool on 2016/9/5.
 */

public class BaseResultModel<T> {
    private int count;//记录条数
    private String responseCode;
    private String responseMsg;
    private long datetime;
    private T object;
//    private Class<T> entityClass;
//
//    public BaseResultModel() {
//        Type genType = getClass().getGenericSuperclass();
//        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
//        entityClass = (Class) params[0];
//    }
//
//    public T get(){
//        try {
//            return entityClass.newInstance();
//        } catch (InstantiationException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return null;
//    }


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

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return "count=" + count + ",responseCode=" + responseCode + ",responseMsg=" + responseMsg + ",T=" + object.toString();
    }
}