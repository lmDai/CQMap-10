package android.zhixun.uiho.com.gissystem.ui.item;

import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.flux.models.AchievementModel;
import android.zhixun.uiho.com.gissystem.flux.models.CGSortOneModel;
import android.zhixun.uiho.com.gissystem.flux.models.CGSortTwoModel;
import android.zhixun.uiho.com.gissystem.flux.models.UnitModel;
import android.zhixun.uiho.com.gissystem.greendao_gen.CGSortOneModelDao;
import android.zhixun.uiho.com.gissystem.greendao_gen.CGSortTwoModelDao;
import android.zhixun.uiho.com.gissystem.greendao_gen.DaoSession;
import android.zhixun.uiho.com.gissystem.greendao_gen.UnitModelDao;
import android.zhixun.uiho.com.gissystem.ui.widget.tree_recyclerview.viewholder.TreeAdapterItem;
import android.zhixun.uiho.com.gissystem.ui.widget.tree_recyclerview.viewholder.ViewHolder;

import com.yibogame.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by parcool on 2016/12/20.
 */

public class UnitItem extends TreeAdapterItem<AchievementModel> {

    private UnitModel mUnitModel;
    public UnitItem(AchievementModel data) {
        super(data);
    }

    public UnitModel getmUnitModel() {
        return mUnitModel;
    }

    @Override
    protected List<TreeAdapterItem> initChildrenList(AchievementModel data) {

//        UnitModelDao unitModelDao = daoSession.getUnitModelDao();
//        mUnitModel = unitModelDao.queryBuilder().where(UnitModelDao.Properties.Id.eq(data.getUnitKey())).unique();
        ArrayList<TreeAdapterItem> treeAdapterItems = new ArrayList<>();
//        CGSortOneModelDao cgSortOneModelDao = daoSession.getCGSortOneModelDao();
//        CGSortTwoModelDao cgSortTwoModelDao = daoSession.getCGSortTwoModelDao();
//        List<CGSortOneModel> cgSortOneModelList = cgSortOneModelDao.queryBuilder().where(CGSortOneModelDao.Properties.CRKey.eq(data.getUnitKey())).list();
//        List<CGSortTwoModel> cgSortTwoModelList = cgSortTwoModelDao.queryBuilder().where(CGSortTwoModelDao.Properties.CRKey.eq(data.getUnitKey())).list();
//        if (cgSortOneModelList != null) {
//            CGSortOneItemTitle cgSortOneItemTitle = new CGSortOneItemTitle("");
//            treeAdapterItems.add(cgSortOneItemTitle);
//            for (int i = 0; i < cgSortOneModelList.size(); i++) {
//                CGSortOneItem cgSortOneItem = new CGSortOneItem(cgSortOneModelList.get(i));
//                treeAdapterItems.add(cgSortOneItem);
//            }
//        }
//        if (cgSortTwoModelList != null) {
//            CGSortTwoItemTitle cgSortTwoItemTitle = new CGSortTwoItemTitle("");
//            treeAdapterItems.add(cgSortTwoItemTitle);
//            for (CGSortTwoModel cgSortTwoModel : cgSortTwoModelList) {
//                CGSortTwoItem cgSortTwoItem = new CGSortTwoItem(cgSortTwoModel);
//                treeAdapterItems.add(cgSortTwoItem);
//            }
//        }
        return treeAdapterItems;
    }

    @Override
    protected int initLayoutId() {
        return R.layout.item_unit;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder) {
        holder.setText(R.id.tv_unit_name, mUnitModel.getName());
        holder.setText(R.id.tv_code, "报件编号:" + data.getBjCode());
        holder.setText(R.id.tv_date, "时间:" + DateUtil.longToString("yyyy-MM-dd",data.getTime()));
    }

    @Override
    public int initSpansize() {
        return 0;
    }

    @Override
    public void onExpand() {
        super.onExpand();
    }

    @Override
    public void onCollapse() {
        super.onCollapse();
    }

}
