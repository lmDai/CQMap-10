package android.zhixun.uiho.com.gissystem.flux.models;

import com.bignerdranch.expandablerecyclerview.model.Parent;

import java.util.List;

/**
 * Created by zp on 2016/11/30.
 */

public class CheckGroupModel implements Parent<CheckListModel> {
    private String title;
    private List<CheckListModel> mMemberModels;


    public List<CheckListModel> getmMemberModels() {
        return mMemberModels;
    }


    public CheckGroupModel(String title, List<CheckListModel> mMemberModels) {
        this.title = title;
        this.mMemberModels = mMemberModels;

    }


    @Override
    public List<CheckListModel> getChildList() {
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

