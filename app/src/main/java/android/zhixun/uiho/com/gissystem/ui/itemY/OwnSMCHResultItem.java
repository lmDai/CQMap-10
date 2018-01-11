package android.zhixun.uiho.com.gissystem.ui.itemY;

import android.content.Context;
import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.app.MyBaseApplication;
import android.zhixun.uiho.com.gissystem.flux.models.api.AchievementModel;
import android.zhixun.uiho.com.gissystem.flux.models.CGSortOneModel;
import android.zhixun.uiho.com.gissystem.flux.models.CGSortTwoModel;
import android.zhixun.uiho.com.gissystem.flux.models.UnitModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.ForFruitTempModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.FruitCategoryAttrsModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.HandoutFruitListModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.OrderModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.TempSortItemModel;
import android.zhixun.uiho.com.gissystem.greendao_gen.CGSortOneModelDao;
import android.zhixun.uiho.com.gissystem.greendao_gen.CGSortTwoModelDao;
import android.zhixun.uiho.com.gissystem.greendao_gen.DaoSession;
import android.zhixun.uiho.com.gissystem.greendao_gen.UnitModelDao;
import android.zhixun.uiho.com.gissystem.rest.APIService;
import android.zhixun.uiho.com.gissystem.ui.item.CGSortOneItem;
import android.zhixun.uiho.com.gissystem.ui.item.CGSortOneItemTitle;
import android.zhixun.uiho.com.gissystem.ui.item.CGSortTwoItem;
import android.zhixun.uiho.com.gissystem.ui.item.CGSortTwoItemTitle;
import android.zhixun.uiho.com.gissystem.ui.widget.tree_recyclerview.viewholder.TreeAdapterItem;
import android.zhixun.uiho.com.gissystem.ui.widget.tree_recyclerview.viewholder.ViewHolder;

import com.yibogame.app.DoOnSubscriber;
import com.yibogame.util.DateUtil;
import com.yibogame.util.LogUtil;
import com.yibogame.util.ToastUtil;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zp on 2016/12/22.
 */

public class OwnSMCHResultItem extends TreeAdapterItem<OrderModel> {


    //    private UnitModel mUnitModel;
    public OwnSMCHResultItem(OrderModel data) {
        super(data);
    }

    @Override
    protected List<TreeAdapterItem> initChildrenList(OrderModel orderModel) {
        ArrayList<TreeAdapterItem> treeAdapterItems = new ArrayList<>();
        APIService.getInstance().getAchievementDetails(String.valueOf(orderModel.getHandoutId()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DoOnSubscriber<AchievementModel>() {
                    @Override
                    public void doOnSubscriber() {

                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(AchievementModel achievementModel) {
                        //装分类ID的list
                        List<ForFruitTempModel> listForFruitTempModel = new ArrayList<ForFruitTempModel>();
                        for (HandoutFruitListModel handoutFruitListModel : achievementModel.getHandoutFruitList()) {
                            //默认listFruitCategoryId里没有
                            ForFruitTempModel forFruitTempModelTemp = null;
                            for (ForFruitTempModel forFruitTempModel : listForFruitTempModel) {
                                if (forFruitTempModel.getFruitCategoryId() == handoutFruitListModel.getFruit().getFruitCategoryId()) {
                                    forFruitTempModelTemp = forFruitTempModel;
                                    break;
                                }
                            }
                            if (forFruitTempModelTemp == null) {
                                forFruitTempModelTemp = new ForFruitTempModel();
                                forFruitTempModelTemp.setFruitCategoryId(handoutFruitListModel.getFruit().getFruitCategory().getFruitCategoryId());
                                forFruitTempModelTemp.setFruitCategoryName(handoutFruitListModel.getFruit().getFruitCategory().getCategoryName());
                                forFruitTempModelTemp.setListFruitCategoryAttrsModel(new ArrayList<List<FruitCategoryAttrsModel>>());

                                listForFruitTempModel.add(forFruitTempModelTemp);
                            }
                            forFruitTempModelTemp.getListFruitCategoryAttrsModel().add(handoutFruitListModel.getFruit().getFruitCategory().getFruitCategoryAttrsList());
                        }

                        for (ForFruitTempModel forFruitTempModel : listForFruitTempModel) {
                            //一级分类名称。eg:水准点
                            CGSortOneItemTitle cgSortOneItemTitle = new CGSortOneItemTitle(forFruitTempModel.getFruitCategoryName());
                            treeAdapterItems.add(cgSortOneItemTitle);
                            //设置列名
                            List<FruitCategoryAttrsModel> fruitCategoryAttrsModelsColumn = forFruitTempModel.getListFruitCategoryAttrsModel().get(0);
                            TempSortItemModel tempSortItemModelTitleColumn = new TempSortItemModel();
                            List<Map<String, String>> listTitleColumn = new ArrayList<Map<String, String>>();
                            Map<String, String> mapTitleAndDataColumn = new HashMap<String, String>();
                            for (FruitCategoryAttrsModel fruitCategoryAttrsModel : fruitCategoryAttrsModelsColumn) {
                                mapTitleAndDataColumn.put(fruitCategoryAttrsModel.getAttrName(), fruitCategoryAttrsModel.getAttrValue());
                            }
                            listTitleColumn.add(mapTitleAndDataColumn);
                            tempSortItemModelTitleColumn.getMapList().addAll(listTitleColumn);
                            tempSortItemModelTitleColumn.setTitle(true);
                            CGSortOneItem cgSortOneItemColumn = new CGSortOneItem(tempSortItemModelTitleColumn);
                            treeAdapterItems.add(cgSortOneItemColumn);

                            //设置数据
                            for (List<FruitCategoryAttrsModel> fruitCategoryAttrsModels : forFruitTempModel.getListFruitCategoryAttrsModel()) {
                                TempSortItemModel tempSortItemModelTitle = new TempSortItemModel();
                                List<Map<String, String>> listTitle = new ArrayList<Map<String, String>>();
                                Map<String, String> mapTitleAndData = new HashMap<String, String>();
                                for (FruitCategoryAttrsModel fruitCategoryAttrsModel : fruitCategoryAttrsModels) {
                                    mapTitleAndData.put(fruitCategoryAttrsModel.getAttrName(), fruitCategoryAttrsModel.getAttrValue());
                                }
                                listTitle.add(mapTitleAndData);
                                tempSortItemModelTitle.getMapList().addAll(listTitle);
                                tempSortItemModelTitle.setTitle(false);
                                CGSortOneItem cgSortOneItem = new CGSortOneItem(tempSortItemModelTitle);
                                treeAdapterItems.add(cgSortOneItem);
                            }
                        }
                    }
                });
        return treeAdapterItems;
    }

    @Override
    protected int initLayoutId() {
        return R.layout.item_unit_and_detail;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder) {
        holder.setText(R.id.tv_unit_name, data.getCompanyName());
//        LogUtil.e("mUnitModel.getName()mUnitModel.getName()mUnitModel.getName()"+mUnitModel.getName());
        holder.setText(R.id.tv_code, "报件编号:" + data.getReportNo());
        holder.setText(R.id.tv_date, "时间:" + (DateUtil.longToString("yyyy-MM-dd", data.getCreateTime())));//+ (DateUtil.longToString("yyyy-MM-dd",data.getTime()))

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
