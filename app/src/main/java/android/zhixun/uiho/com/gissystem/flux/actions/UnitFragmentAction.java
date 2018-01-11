package android.zhixun.uiho.com.gissystem.flux.actions;

import com.yibogame.flux.actions.Action;

import java.util.Map;

/**
 * Created by parcool on 2016/12/16.
 */

public class UnitFragmentAction extends Action {

    public static final String GET_SIFT_ZCD_DATA = "get_sift_zcd_data",
            ZCD_ADAPTER_ON_ITEM_CLICK = "actionZCDAdapterOnItemClick",
            GET_SIFT_LB_DATA="get_sift_lb_data",
            LB_ADAPTER_ON_ITEM_CLICK="LB_ADAPTER_ON_ITEM_CLICK",
            BUILD_SQL="build_sql",
            ADD_UNIT_DATA="add_unit_data",
            NO_DATA="NO_DATA";

    public UnitFragmentAction(String type, Map<String, Object> data) {
        super(type, data);
    }

    public UnitFragmentAction(String type) {
        super(type);
    }
}
