package android.zhixun.uiho.com.gissystem.flux.actions;

/**
 * Created by parcool on 2016/11/30.
 */

public class UnitFragmentActionTemp<T> {
    public static final String HIDE_SEARCH_BAR = "hide_search_bar", SHOW_SEARCH_BAR = "show_search_bar",ACTION_SYNC_ENVELOPE="action_sync_envelope",ACTION_CLEAR="action_clear";

    private String action;
    private T t;

    public UnitFragmentActionTemp(String action) {
        this.action = action;
    }

    public UnitFragmentActionTemp(String action, T t) {
        this.action = action;
        this.t = t;
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
}
