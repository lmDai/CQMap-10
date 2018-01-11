package android.zhixun.uiho.com.gissystem.flux.actions;


import com.esri.core.map.Feature;

import java.util.List;

/**
 * Created by parcool on 2016/11/29.
 */

public class MainActivityAction<T> {
    public static final String ANIM_TO_HALF = "anim_to_half",
            CLEAR_TEXT = "clear_text",
            SWITCH_TO_VECTOR = "switch_to_vector",
            SWITCH_TO_TOPOGRAPHIC = "switch_to_topographic",
            SWITCH_TO_IMAGE = "switch_to_image",
            ACTION_REFRESH_DATA = "action_refresh_data",
            ACTION_REFRESH_DATA_DISPATCH = "action_refresh_data_dispatch",
            ACTION_SELECT_ITEM = "action_select_item",
            ACTION_SELECT_ITEM_DISPATCH = "action_select_item_dispatch",
            ACTION_GET_UNIT_ID = "action_get_unit_id";

    private String action;

    private List<T> list;
    private Feature feature;
    private long unitId;

    public long getUnitId() {
        return unitId;
    }

    public void setUnitId(long unitId) {
        this.unitId = unitId;
    }

    public MainActivityAction(String action, List<T> list) {
        this.action = action;
        this.list = list;
    }

    public MainActivityAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Feature getFeature() {
        return feature;
    }

    public void setFeature(Feature feature) {
        this.feature = feature;
    }
}
