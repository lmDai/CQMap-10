package android.zhixun.uiho.com.gissystem.flux.models;

import com.bignerdranch.expandablerecyclerview.model.Parent;

import java.util.List;

/**
 * Created by zp on 2016/11/30.
 */

public class CheckGroupTwoModel implements Parent<CheckListTwoModel> {
    private String title;
    private List<CheckListTwoModel> mMemberModels;


    public List<CheckListTwoModel> getmMemberModels() {
        return mMemberModels;
    }


    public CheckGroupTwoModel(String title, List<CheckListTwoModel> mMemberModels) {
        this.title = title;
        this.mMemberModels = mMemberModels;

    }


    @Override
    public List<CheckListTwoModel> getChildList() {
        return mMemberModels;
    }

//

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

