package android.zhixun.uiho.com.gissystem.ui.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.flux.models.CheckGroupModel;
import android.zhixun.uiho.com.gissystem.flux.models.CheckListModel;
import android.zhixun.uiho.com.gissystem.interfaces.OnExpandableItemCheckedListener;
import android.zhixun.uiho.com.gissystem.ui.holder.CheckGroupViewHolder;
import android.zhixun.uiho.com.gissystem.ui.holder.CheckListViewHolder;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;

import java.util.List;

/**
 * Created by zp on 2016/11/30.
 */

public class CheckSituationAdapter extends ExpandableRecyclerAdapter<CheckGroupModel, CheckListModel, CheckGroupViewHolder, CheckListViewHolder> {

    private static final int CHECK_LIST_NORMAL = 0;
    private static final int CHECK_LIST_EXTEND = 1;
    private LayoutInflater mInflater;
    private List<CheckGroupModel> mHolderModels;
    private OnExpandableItemCheckedListener mOnExpandableItemCheckedListener;

    public CheckSituationAdapter(Context context, @NonNull List<CheckGroupModel> HolderModels) {
        super(HolderModels);
        mHolderModels = HolderModels;
        mInflater = LayoutInflater.from(context);
    }

    public OnExpandableItemCheckedListener getOnItemCheckedListener() {
        return mOnExpandableItemCheckedListener;
    }

    public void setOnItemCheckedListener(OnExpandableItemCheckedListener mOnExpandableItemCheckedListener) {
        this.mOnExpandableItemCheckedListener = mOnExpandableItemCheckedListener;
    }

    @UiThread
    @NonNull
    @Override
    public CheckGroupViewHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        View recipeView;

        recipeView = mInflater.inflate(R.layout.item_expendlist_check_group, parentViewGroup, false);

        return new CheckGroupViewHolder(recipeView, mOnExpandableItemCheckedListener);
    }

//    @Override
//    public int getChildViewType(int parentPosition, int childPosition) {
//        int type;
//        switch (mHolderModels.get(parentPosition).getChildList().get(childPosition).getType()){
//            case 0:
//                type =CHECK_LIST_NORMAL;
//                break;
//            case 1:
//                type =CHECK_LIST_EXTEND;
//                break;
//        }
//        return type;
//    }

    @UiThread
    @NonNull
    @Override
    public CheckListViewHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        View ingredientView;
//        switch (viewType) {
//            default:
//            case CHECK_LIST_EXTEND:
//                ingredientView = mInflater.inflate(R.layout.item_expendlist_checklistone_item, childViewGroup, false);
//                break;
//            case CHECK_LIST_NORMAL:
//                ingredientView = mInflater.inflate(R.layout.item_expendlist_checklisttwo_item, childViewGroup, false);
//                break;
//        }

        ingredientView = mInflater.inflate(R.layout.item_expendlist_checklistone_item, childViewGroup, false);

        return new CheckListViewHolder(ingredientView, mOnExpandableItemCheckedListener);
    }

    @Override
    public void onBindParentViewHolder(@NonNull CheckGroupViewHolder parentViewHolder, int parentPosition, @NonNull CheckGroupModel parent) {
        parentViewHolder.bind(parent);
    }

    @Override
    public void onBindChildViewHolder(@NonNull CheckListViewHolder childViewHolder, int parentPosition, int childPosition, @NonNull CheckListModel child) {
        childViewHolder.bind(child);
    }


}
