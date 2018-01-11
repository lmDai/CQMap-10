package android.zhixun.uiho.com.gissystem.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.flux.models.HolderDetailModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.HoldersModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.HoldersParentModel;
import android.zhixun.uiho.com.gissystem.interfaces.OnExpandableItemCheckedListener;
import android.zhixun.uiho.com.gissystem.ui.holder.HolderDetailViewHolder;
import android.zhixun.uiho.com.gissystem.ui.holder.HolderViewHolder;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zp on 2016/11/29.
 */

public class HolderSizeMarkAdapter extends ExpandableRecyclerAdapter<HoldersParentModel, HoldersModel, HolderViewHolder, HolderDetailViewHolder> {

    private static final int PARENT_VEGETARIAN = 0;
    private static final int PARENT_NORMAL = 1;
    private static final int CHILD_VEGETARIAN = 2;
    private static final int CHILD_NORMAL = 3;

    private LayoutInflater mInflater;
    private ArrayList<HoldersParentModel> mHolderModels;
    private OnExpandableItemCheckedListener mOnExpandableItemCheckedListener;
    private String companyName;

    public HolderSizeMarkAdapter(Context context, @NonNull ArrayList<HoldersParentModel> holderModels,String companyName) {
        super(holderModels);
        this.mHolderModels = holderModels;
        this.companyName = companyName;
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
    public HolderViewHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        View recipeView;

        recipeView = mInflater.inflate(R.layout.item_expendlist_group, parentViewGroup, false);


        return new HolderViewHolder(recipeView, mOnExpandableItemCheckedListener);
    }

    @UiThread
    @NonNull
    @Override
    public HolderDetailViewHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        View ingredientView;

        ingredientView = mInflater.inflate(R.layout.item_expendlist_item, childViewGroup, false);


        return new HolderDetailViewHolder(ingredientView, mOnExpandableItemCheckedListener,companyName);
    }

    @Override
    public void onBindParentViewHolder(@NonNull HolderViewHolder parentViewHolder, int parentPosition, @NonNull HoldersParentModel parent) {
        parentViewHolder.bind(parent);
    }

    @Override
    public void onBindChildViewHolder(@NonNull HolderDetailViewHolder childViewHolder, int parentPosition, int childPosition, @NonNull HoldersModel child) {
        childViewHolder.bind(child);

    }


}
