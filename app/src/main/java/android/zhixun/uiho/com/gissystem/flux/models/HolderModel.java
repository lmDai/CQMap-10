package android.zhixun.uiho.com.gissystem.flux.models;

import com.bignerdranch.expandablerecyclerview.model.Parent;

import java.util.List;

/**
 * Created by zp on 2016/11/29.
 */

public class HolderModel implements Parent<HolderDetailModel> {
    private String mName;
    private String upLoading;
    private String state;
    private String date;



    public String getUpLoading() {
        return upLoading;
    }

    public String getState() {
        return state;
    }

    public String getDate() {
        return date;
    }

    public List<HolderDetailModel> getmMemberModels() {
        return mMemberModels;
    }

    private List<HolderDetailModel> mMemberModels;


    public HolderModel(String mName, String upLoading, String state, String date, List<HolderDetailModel> mMemberModels) {
        this.mName = mName;
        this.upLoading = upLoading;
        this.state = state;
        this.date = date;
        this.mMemberModels = mMemberModels;

    }

    public String getName() {
        return mName;
    }


    @Override
    public List<HolderDetailModel> getChildList() {
        return mMemberModels;
    }

//    public boolean isChecked() {
//        return isChecked;
//    }
//
//    public void setChecked(boolean checked) {
//        isChecked = checked;
//    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
//    public boolean isVegetarian() {
//        for (MemberModel memberModel : mMemberModels) {
//            if (!memberModel.isVegetarian()) {
//                return false;
//            }
//        }
//        return true;
//    }
}

