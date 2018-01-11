package android.zhixun.uiho.com.gissystem.flux.actions;

import com.yibogame.flux.actions.ActionsCreator;
import com.yibogame.flux.dispatcher.Dispatcher;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by parcool on 2016/12/16.
 */

public class UnitFragmentActionsCreator extends ActionsCreator {

    public UnitFragmentActionsCreator(Dispatcher dispatcher) {
        super(dispatcher);
    }

    public void actionGetSiftZCDData() {
        getDispatcher().dispatch(new UnitFragmentAction(UnitFragmentAction.GET_SIFT_ZCD_DATA));
    }

    public void actionZCDAdapterOnItemClick(int position){
        Map<String,Object> map = new HashMap<>();
        map.put("position",position);
        getDispatcher().dispatch(new UnitFragmentAction(UnitFragmentAction.ZCD_ADAPTER_ON_ITEM_CLICK,map));
    }

    public void actionGetSiftLBData() {
        getDispatcher().dispatch(new UnitFragmentAction(UnitFragmentAction.GET_SIFT_LB_DATA));
    }

    public void actionLBAdapterOnItemClick(int position){
        Map<String,Object> map = new HashMap<>();
        map.put("position",position);
        getDispatcher().dispatch(new UnitFragmentAction(UnitFragmentAction.LB_ADAPTER_ON_ITEM_CLICK,map));
    }

    public void buildSql(String searchStr,int industryCategoryId,int areaId){
        Map<String,Object> map = new HashMap<>();
        map.put("searchStr",searchStr);
        map.put("industryCategoryId",industryCategoryId);
        map.put("areaId",areaId);
        getDispatcher().dispatch(new UnitFragmentAction(UnitFragmentAction.BUILD_SQL,map));
    }

    public void addUnitData(){
        getDispatcher().dispatch(new UnitFragmentAction(UnitFragmentAction.ADD_UNIT_DATA));
    }
}
