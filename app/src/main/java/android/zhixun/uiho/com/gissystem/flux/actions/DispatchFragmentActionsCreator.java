package android.zhixun.uiho.com.gissystem.flux.actions;

import com.yibogame.flux.actions.ActionsCreator;
import com.yibogame.flux.dispatcher.Dispatcher;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by parcool on 2016/12/16.
 */

public class DispatchFragmentActionsCreator extends ActionsCreator {

    public DispatchFragmentActionsCreator(Dispatcher dispatcher) {
        super(dispatcher);
    }

    public void actionGetSiftZCDData() {
        getDispatcher().dispatch(new DispatchFragmentAction(DispatchFragmentAction.GET_SIFT_ZCD_DATA));
    }

    public void actionZCDAdapterOnItemClick(int position){
        Map<String,Object> map = new HashMap<>();
        map.put("position",position);
        getDispatcher().dispatch(new DispatchFragmentAction(DispatchFragmentAction.ZCD_ADAPTER_ON_ITEM_CLICK,map));
    }

    public void actionGetSiftLBData() {
        getDispatcher().dispatch(new DispatchFragmentAction(DispatchFragmentAction.GET_SIFT_LB_DATA));
    }

    public void actionLBAdapterOnItemClick(int position){
        Map<String,Object> map = new HashMap<>();
        map.put("position",position);
        getDispatcher().dispatch(new DispatchFragmentAction(DispatchFragmentAction.LB_ADAPTER_ON_ITEM_CLICK,map));
    }

    public void buildSql(String searchStr,String caseCode,String dataCode,long startTime,long endTime){
        Map<String,Object> map = new HashMap<>();
        map.put("searchStr",searchStr);
        map.put("caseCode",caseCode);
        map.put("dataCode",dataCode);
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        getDispatcher().dispatch(new DispatchFragmentAction(DispatchFragmentAction.BUILD_SQL,map));
    }
}
