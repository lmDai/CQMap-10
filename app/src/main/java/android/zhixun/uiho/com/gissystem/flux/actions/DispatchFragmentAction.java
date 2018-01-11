package android.zhixun.uiho.com.gissystem.flux.actions;

import com.yibogame.flux.actions.Action;

import java.util.Map;

/**
 * Created by parcool on 2016/12/16.
 */

public class DispatchFragmentAction extends Action {

    public static final String GET_SIFT_ZCD_DATA = "get_sift_zcd_data",
            ZCD_ADAPTER_ON_ITEM_CLICK = "actionZCDAdapterOnItemClick",
            GET_SIFT_LB_DATA = "get_sift_lb_data",
            LB_ADAPTER_ON_ITEM_CLICK = "LB_ADAPTER_ON_ITEM_CLICK",
            BUILD_SQL = "build_sql",
            NO_DATA = "no_data";

    public DispatchFragmentAction(String type, Map<String, Object> data) {
        super(type, data);
    }

    public DispatchFragmentAction(String type) {
        super(type);
    }
}
