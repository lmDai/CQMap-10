package android.zhixun.uiho.com.gissystem.flux.actions;

/**
 * Created by parcool on 2016/11/30.
 */

public class DispatchFragmentActionTemp<T> {
    public static final String HIDE_SEARCH_BAR = "hide_search_bar", SHOW_SEARCH_BAR = "show_search_bar",ACTION_SYNC_ENVELOPE="action_sync_envelope",ACTION_CLEAR="action_clear",
    ACTION_SHOW_ONE="action_show_one",ACTION_SHOW_TWO="action_show_two",ACTION_HIDE_DETAIL="action_hide_detail",ACTION_GET_ITEM_DATA_BY_POSITION="action_get_item_data_by_position"
            ,ACTION_SHOW="action_show";

    private String action;
    private T t;
    private int what;

    public DispatchFragmentActionTemp(String action) {
        this.action = action;
    }

    public DispatchFragmentActionTemp(String action, T t) {
        this.action = action;
        this.t = t;
    }

    public DispatchFragmentActionTemp(String action, T t,int what) {
        this.action = action;
        this.t = t;
        this.what = what;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public int getWhat() {
        return what;
    }

    public void setWhat(int what) {
        this.what = what;
    }
}
