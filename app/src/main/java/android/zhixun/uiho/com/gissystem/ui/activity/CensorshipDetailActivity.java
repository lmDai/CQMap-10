package android.zhixun.uiho.com.gissystem.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.app.MyBaseApplication;
import android.zhixun.uiho.com.gissystem.flux.models.CRCheckPersonModel;
import android.zhixun.uiho.com.gissystem.flux.models.CRImageModel;
import android.zhixun.uiho.com.gissystem.flux.models.CRResultTypeModel;
import android.zhixun.uiho.com.gissystem.flux.models.CheckGroupModel;
import android.zhixun.uiho.com.gissystem.flux.models.CheckListModel;
import android.zhixun.uiho.com.gissystem.flux.models.UnitDetailModel;
import android.zhixun.uiho.com.gissystem.flux.models.UserModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.CompanyDetailByCheckedModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.SecrecyInspectEntryCompanyListModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.SecrecyInspectFruitListModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.SecrecyInspectImgListModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.SecrecyPersonListModel;
import android.zhixun.uiho.com.gissystem.greendao_gen.DaoSession;
import android.zhixun.uiho.com.gissystem.interfaces.OnItemClickListener;
import android.zhixun.uiho.com.gissystem.rest.APIService;
import android.zhixun.uiho.com.gissystem.rest.SimpleSubscriber;
import android.zhixun.uiho.com.gissystem.ui.adapter.CensorshipDetailAdapter;
import android.zhixun.uiho.com.gissystem.ui.adapter.CheckSituationAdapter;
import android.zhixun.uiho.com.gissystem.ui.adapter.MyViewPagerAdapter;
import android.zhixun.uiho.com.gissystem.ui.widget.MyViewPager;

import com.alibaba.fastjson.JSONObject;
import com.yibogame.flux.stores.Store;
import com.yibogame.util.DateUtil;
import com.yibogame.util.LogUtil;
import com.yibogame.util.SPUtil;
import com.yibogame.util.ToastUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class CensorshipDetailActivity extends BaseActivityWithTitle {
    private RecyclerView recyclerView1, recyclerView2;
    private List<UnitDetailModel> lists = new ArrayList<>();
    private TextView tv_check_unit_name;//重庆市规划局
    private TextView tv_check_person_name;//张三，李四，王伟，张三，李四，王伟，张三，李四，王伟
    private TextView tv_beChecked_unit_name;//重庆市宝威有限公司
    private TextView tv_contact_name;//欧阳娜娜
    private TextView tv_contact_tel_content;//12345678901
    private TextView tv_check_time_content;//2016-11-28
    private TextView tv_match_person_name;//配合人员
    private TextView tv_submitter;//提交人
    private TextView tv_submit_date;//提交日期
    private TextView tvOtherZGIdeaContent;//其它整改意见内容
    private DaoSession mDaoSession;
    private List<CRCheckPersonModel> mListCRCheckPersonModel = new ArrayList<>();//对应tid的勾选了得参选人员。
    private List<UserModel> mListUserModel = new ArrayList<>();//对应tid 的所有参选人员。
    private List<String> CJname = new ArrayList<>();//参检人员名字。
    private String CJNameList = "";
    private List<CRResultTypeModel> mListCRResultTypeModel = new ArrayList<>();//成果类型
    private List<CRImageModel> mListCRImageModel = new ArrayList<>();//查询图片
    private LinearLayout llResultType;//装成果种类的
    private ImageView arrowJCQK;//检查情况
    private LinearLayout llJCQK;//检查情况的外包装
    private ImageView arrowResultType;//测绘成果种类
    private MyViewPager myViewPager;
    private List<String> mListViewPager = new ArrayList<>();
    private CompanyDetailByCheckedModel mCompanyDetailByCheckedModel;
    private android.zhixun.uiho.com.gissystem.flux.models.api.UserModel userModel;

    @Override
    protected Store initActionsCreatorAndStore() {
        return null;
    }

    @Override
    protected void onStoreCall(Store.StoreChangeEvent storeChangeEvent) {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_censorship_detail;
    }

    @Override
    protected void onCreateActivity(@Nullable Bundle savedInstanceState) {
        setTitleText("保密检查详情");
        //初始化
        mCompanyDetailByCheckedModel = getIntent().getParcelableExtra("CompanyDetailByCheckedModel");
        String params = String.valueOf(mCompanyDetailByCheckedModel.getSecrecyInspectId());
        getData(params);
        String userJson = SPUtil.getInstance().getString("UserModelOfJson");
        userModel = JSONObject.parseObject(userJson, android.zhixun.uiho.com.gissystem.flux.models.api.UserModel.class);
        mDaoSession = ((MyBaseApplication) getApplication()).getDaoSession();



    }

    private void getData(String params){
        showLoading();
        APIService.getInstance()
                .getSecrecyInspect(params, new SimpleSubscriber<CompanyDetailByCheckedModel>() {
                    @Override
                    public void onResponse(CompanyDetailByCheckedModel response) {
                        hideLoading();
                        mCompanyDetailByCheckedModel = response;
                        initViews();
                        addAchievement();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        hideLoading();
                        ToastUtil.showShort(e.getMessage());
                    }
                });
    }

    private void initViews() {
        llResultType = (LinearLayout) findViewById(R.id.ll_result_type);
        arrowResultType = (ImageView) findViewById(R.id.arrow_result_type);
        tvOtherZGIdeaContent = (TextView) findViewById(R.id.tv_otherZGIdea_content);//其它整改意见内容
        arrowResultType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (llResultType.getVisibility() == View.VISIBLE) {
                    llResultType.setVisibility(View.GONE);
                    arrowResultType.setImageResource(R.mipmap.ic_arrow_drop_up_black_36dp);
                } else {
                    llResultType.setVisibility(View.VISIBLE);
                    arrowResultType.setImageResource(R.mipmap.arrow_bottom);
                }
            }
        });

        arrowJCQK = (ImageView) findViewById(R.id.arrow_JCQK);
        llJCQK = (LinearLayout) findViewById(R.id.ll_JCQK);
        arrowJCQK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (llJCQK.getVisibility() == View.VISIBLE) {
                    llJCQK.setVisibility(View.GONE);
                    arrowJCQK.setImageResource(R.drawable.ic_arrow_up);
                } else {
                    llJCQK.setVisibility(View.VISIBLE);
                    arrowJCQK.setImageResource(R.mipmap.arrow_down);
                }
            }
        });
        if (mCompanyDetailByCheckedModel.getSecrecyPersonList() != null) {
            for (SecrecyPersonListModel secrecyPersonListModel : mCompanyDetailByCheckedModel.getSecrecyPersonList()) {
                CJname.add(secrecyPersonListModel.getName());
            }
        }
        if (mCompanyDetailByCheckedModel.getSecrecyInspectImgList() != null) {
            for (SecrecyInspectImgListModel secrecyInspectImgListModel : mCompanyDetailByCheckedModel.getSecrecyInspectImgList()) {
                mListViewPager.add(secrecyInspectImgListModel.getUrl());
            }
        }
        if (mListViewPager.size() <= 0) {
            findViewById(R.id.tv_no_image).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.tv_no_image).setVisibility(View.GONE);
        }
        //这里填充图片数据
//        for (int i = 0; i < mListCRImageModel.size(); i++) {
//            mListViewPager.add(mListCRImageModel.get(i).getPath());
//        }
        tv_submitter = (TextView) findViewById(R.id.tv_submitter);
//        tv_submitter.setText(crActiveUser.getNickname());
        tv_submitter.setText(userModel.getName());

        //提交日期 以提交到服务器的时间为准
        tv_submit_date = (TextView) findViewById(R.id.tv_submit_date);
//        String tvSubmitDate = crModel.getCheck_time().split(" ")[0];
//        long tvSubmitDate = crModel.getSavaServerTime();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mCompanyDetailByCheckedModel.getSecrecyTime());
        tv_submit_date.setText(formatter.format(calendar.getTime()));

        String JCone = "未选择", JCThree = "未选择", JCFour = "未选择", JCFive = "未选择", JCSix = "未选择", JCSeven = "未选择",
                JCEight = "未选择", JCNine = "未选择", JCTen = "未选择", JCelevn = "未选择",
                JCtwelve = "", JCthirteen = "未选择", JCfourteen = "未选择", JCfifteen = "未选择";
        String ZGone = "未选择", ZGtwo = "未选择", ZGthree = "未选择", ZGfour = "未选择", ZGfive = "未选择";
        String LDone = "未选择", LDtwo = "未选择", LDthree = "未选择";
        if (mCompanyDetailByCheckedModel.getSecrecyInspectEntryCompanyList() != null) {
            for (SecrecyInspectEntryCompanyListModel secrecyInspectEntryListModel : mCompanyDetailByCheckedModel.getSecrecyInspectEntryCompanyList()) {
                if (secrecyInspectEntryListModel.getSecrecyInspectEntryId() == 1) {
                    if (secrecyInspectEntryListModel.getIsPass() == 1) {
                        JCone = "是";
                    } else if (secrecyInspectEntryListModel.getIsPass() == 0) {
                        JCone = "否";
                    } else {
                        JCone = "未选择";
                    }
                }
                LogUtil.e("------->isPass=" + secrecyInspectEntryListModel.getIsPass() + ",ID=" + secrecyInspectEntryListModel.getSecrecyInspectId());
                if (secrecyInspectEntryListModel.getSecrecyInspectEntryId() == 3) {
                    if (secrecyInspectEntryListModel.getIsPass() == 1) {
                        JCThree = "是";
                    } else if (secrecyInspectEntryListModel.getIsPass() == 0) {
                        JCThree = "否";
                    } else {
                        JCThree = "未选择";
                    }
                }
                if (secrecyInspectEntryListModel.getSecrecyInspectEntryId() == 4) {
                    if (secrecyInspectEntryListModel.getIsPass() == 0) {
                        JCFour = "差";
                    } else if (secrecyInspectEntryListModel.getIsPass() == 1) {
                        JCFour = "较差";
                    } else if (secrecyInspectEntryListModel.getIsPass() == 2) {
                        JCFour = "一般";
                    } else if (secrecyInspectEntryListModel.getIsPass() == 3) {
                        JCFour = "较好";
                    } else if (secrecyInspectEntryListModel.getIsPass() == 4) {
                        JCFour = "好";
                    } else {
                        JCFour = "未选择";
                    }
                }


                if (secrecyInspectEntryListModel.getSecrecyInspectEntryId() == 5) {
                    if (secrecyInspectEntryListModel.getIsPass() == 1) {
                        JCFive = "是";
                    } else if (secrecyInspectEntryListModel.getIsPass() == 0) {
                        JCFive = "否";
                    } else {
                        JCFive = "未选择";
                    }
                }
                if (secrecyInspectEntryListModel.getSecrecyInspectEntryId() == 6) {
                    if (secrecyInspectEntryListModel.getIsPass() == 1) {
                        JCSix = "是";
                    } else if (secrecyInspectEntryListModel.getIsPass() == 0) {
                        JCSix = "否";
                    } else {
                        JCSix = "未选择";
                    }
                }
                if (secrecyInspectEntryListModel.getSecrecyInspectEntryId() == 7) {
                    if (secrecyInspectEntryListModel.getIsPass() == 1) {
                        JCSeven = "是";
                    } else if (secrecyInspectEntryListModel.getIsPass() == 0) {
                        JCSeven = "否";
                    } else {
                        JCSeven = "未选择";
                    }
                }
                if (secrecyInspectEntryListModel.getSecrecyInspectEntryId() == 8) {
                    if (secrecyInspectEntryListModel.getIsPass() == 1) {
                        JCEight = "是";
                    } else if (secrecyInspectEntryListModel.getIsPass() == 0) {
                        JCEight = "否";
                    } else {
                        JCEight = "未选择";
                    }
                }
                if (secrecyInspectEntryListModel.getSecrecyInspectEntryId() == 9) {
                    if (secrecyInspectEntryListModel.getIsPass() == 1) {
                        JCNine = "是";
                    } else if (secrecyInspectEntryListModel.getIsPass() == 0) {
                        JCNine = "否";
                    } else {
                        JCNine = "未选择";
                    }
                }
                if (secrecyInspectEntryListModel.getSecrecyInspectEntryId() == 10) {
                    if (secrecyInspectEntryListModel.getIsPass() == 1) {
                        JCTen = "是";
                    } else if (secrecyInspectEntryListModel.getIsPass() == 0) {
                        JCTen = "否";
                    } else {
                        JCTen = "未选择";
                    }
                }
                if (secrecyInspectEntryListModel.getSecrecyInspectEntryId() == 11) {
                    if (secrecyInspectEntryListModel.getIsPass() == 1) {
                        JCelevn = "是";
                    } else if (secrecyInspectEntryListModel.getIsPass() == 0) {
                        JCelevn = "否";
                    } else {
                        JCelevn = "未选择";
                    }
                }
                if (secrecyInspectEntryListModel.getSecrecyInspectEntryId() == 12) {
                    if (secrecyInspectEntryListModel.getIsPass() == 1) {
                        JCtwelve = "是";
                    } else if (secrecyInspectEntryListModel.getIsPass() == 0) {
                        JCtwelve = "否";
                    } else {
                        JCtwelve = "未选择";
                    }
                }
                if (secrecyInspectEntryListModel.getSecrecyInspectEntryId() == 13) {
                    if (secrecyInspectEntryListModel.getIsPass() == 1) {
                        JCthirteen = "是";
                    } else if (secrecyInspectEntryListModel.getIsPass() == 0) {
                        JCthirteen = "否";
                    } else {
                        JCthirteen = "未选择";
                    }
                }
                if (secrecyInspectEntryListModel.getSecrecyInspectEntryId() == 14) {
                    if (secrecyInspectEntryListModel.getIsPass() == 1) {
                        JCfourteen = "是";
                    } else if (secrecyInspectEntryListModel.getIsPass() == 0) {
                        JCfourteen = "否";
                    } else {
                        JCfourteen = "未选择";
                    }
                }
                if (secrecyInspectEntryListModel.getSecrecyInspectEntryId() == 15) {
                    if (secrecyInspectEntryListModel.getIsPass() == 1) {
                        JCfifteen = "是";
                    } else if (secrecyInspectEntryListModel.getIsPass() == 0) {
                        JCfifteen = "否";
                    } else {
                        JCfifteen = "未选择";
                    }
                }
                if (secrecyInspectEntryListModel.getSecrecyInspectEntryId() == 16) {
                    if (secrecyInspectEntryListModel.getIsPass() == 1) {
                        ZGone = "是";
                    } else if (secrecyInspectEntryListModel.getIsPass() == 0) {
                        ZGone = "否";
                    } else {
                        ZGone = "未选择";
                    }
                }
                if (secrecyInspectEntryListModel.getSecrecyInspectEntryId() == 17) {
                    if (secrecyInspectEntryListModel.getIsPass() == 1) {
                        ZGtwo = "是";
                    } else if (secrecyInspectEntryListModel.getIsPass() == 0) {
                        ZGtwo = "否";
                    } else {
                        ZGtwo = "未选择";
                    }
                }
                if (secrecyInspectEntryListModel.getSecrecyInspectEntryId() == 18) {
                    if (secrecyInspectEntryListModel.getIsPass() == 1) {
                        ZGthree = "是";
                    } else if (secrecyInspectEntryListModel.getIsPass() == 0) {
                        ZGthree = "否";
                    } else {
                        ZGthree = "未选择";
                    }
                }
                if (secrecyInspectEntryListModel.getSecrecyInspectEntryId() == 19) {
                    if (secrecyInspectEntryListModel.getIsPass() == 1) {
                        ZGfour = "是";
                    } else if (secrecyInspectEntryListModel.getIsPass() == 0) {
                        ZGfour = "否";
                    } else {
                        ZGfour = "未选择";
                    }
                }
                if (secrecyInspectEntryListModel.getSecrecyInspectEntryId() == 20) {
                    if (secrecyInspectEntryListModel.getIsPass() == 1) {
                        ZGfive = "是";
                    } else if (secrecyInspectEntryListModel.getIsPass() == 0) {
                        ZGfive = "否";
                    } else {
                        ZGfive = "未选择";
                    }
                }
                if (secrecyInspectEntryListModel.getSecrecyInspectEntryId() == 21) {
                    if (secrecyInspectEntryListModel.getIsPass() == 1) {
                        JCone = "是";
                    } else if (secrecyInspectEntryListModel.getIsPass() == 0) {
                        JCone = "否";
                    } else {
                        JCone = "未选择";
                    }
                }
            }
        }
        //十六 其他问题 直接在下面写
        if (mCompanyDetailByCheckedModel.getIsAdoptLeader() == 1) {
            //是否检查通过
            LDone = "是";
        } else if (mCompanyDetailByCheckedModel.getIsAdoptLeader() == 0) {
            //是否检查通过
            LDone = "否";
        } else {
            LDone = "未选择";
        }
        if (mCompanyDetailByCheckedModel.getIsSignLeader() == 1) {
            //领导签字
            LDtwo = "是";
        } else if (mCompanyDetailByCheckedModel.getIsSignLeader() == 0) {
            //领导签字
            LDtwo = "否";
        } else {
            LDtwo = "未选择";
        }

        if (mCompanyDetailByCheckedModel.getIsSignCompany() == 1) {
            //单位签字
            LDthree = "是";
        } else if (mCompanyDetailByCheckedModel.getIsSignCompany() == 0) {
            LDthree = "否";
        } else {
            LDthree = "未选择";
        }

//        String LDthree;
//        if (crModel.getLeaderIdea_three() == 1) {
//            LDthree = "是";
//        } else if (crModel.getLeaderIdea_three() == -1) {
//            LDthree = "否";
//        } else {
//            LDthree = "未选择";
//        }

        String LDsign = mCompanyDetailByCheckedModel.getLeaderReply();
        recyclerView1 = (RecyclerView) findViewById(R.id.recyclerView1);

        recyclerView2 = (RecyclerView) findViewById(R.id.recyclerView2);
        tv_check_unit_name = (TextView) findViewById(R.id.tv_check_unit_name);
        tv_check_person_name = (TextView) findViewById(R.id.tv_check_person_name);
        tv_beChecked_unit_name = (TextView) findViewById(R.id.tv_beChecked_unit_name);
        tv_contact_name = (TextView) findViewById(R.id.tv_contact_name);
        tv_contact_tel_content = (TextView) findViewById(R.id.tv_contact_tel_content);
        tv_check_time_content = (TextView) findViewById(R.id.tv_check_time_content);
        tv_match_person_name = (TextView) findViewById(R.id.tv_match_person_name);


        String checked_unit = mCompanyDetailByCheckedModel.getCompanyName();//被检单位。
        String Match_person = mCompanyDetailByCheckedModel.getCooperatePersons();//配合人员。
        String contacts = mCompanyDetailByCheckedModel.getPersonName();//联系人
        String contacts_number = mCompanyDetailByCheckedModel.getPhone();//联系电话
        String date = DateUtil.longToString(DateUtil.yyyyMMDD, mCompanyDetailByCheckedModel.getSecrecyTime());//联系日期
        String tempCheckPerson = mCompanyDetailByCheckedModel.getSecrecyPersonsInterim();//临时参检人员

        tv_check_unit_name.setText("重庆市规划局");
        for (int i = 0; i < CJname.size(); i++) {
            CJNameList += CJname.get(i) + " ";
        }
//        CJNameList += tempCheckPerson;

        tv_check_person_name.setText(mCompanyDetailByCheckedModel.secrecyPersonNames);
        tv_beChecked_unit_name.setText(checked_unit);
        tv_contact_name.setText(contacts);
        tv_contact_tel_content.setText(contacts_number);
        tv_check_time_content.setText(date);
        tv_match_person_name.setText(Match_person);
        //整改意见

        ((TextView) findViewById(R.id.JCone)).setText(JCone);
        ((TextView) findViewById(R.id.JCthree)).setText(JCThree);
        ((TextView) findViewById(R.id.JCfour)).setText(JCFour);
        ((TextView) findViewById(R.id.JCfive)).setText(JCFive);
        ((TextView) findViewById(R.id.JCsix)).setText(JCSix);
        ((TextView) findViewById(R.id.JCseven)).setText(JCSeven);
        ((TextView) findViewById(R.id.JCeight)).setText(JCEight);
        ((TextView) findViewById(R.id.JCnine)).setText(JCNine);
        ((TextView) findViewById(R.id.JCten)).setText(JCTen);
        ((TextView) findViewById(R.id.JCeleven)).setText(JCelevn);
        ((TextView) findViewById(R.id.JCtwelve)).setText(JCtwelve);
        ((TextView) findViewById(R.id.JCthirteen)).setText(JCthirteen);
        ((TextView) findViewById(R.id.JCfourteen)).setText(JCfourteen);
        ((TextView) findViewById(R.id.JCfifteen)).setText(JCfifteen);
        ((TextView) findViewById(R.id.JCsixteen)).setText("");//暂未设置//其他问题说明

        // 整改意见
        ((TextView) findViewById(R.id.tv_suggest1_state)).setText(ZGone);
        ((TextView) findViewById(R.id.tv_suggest2_state)).setText(ZGtwo);
        ((TextView) findViewById(R.id.tv_suggest3_state)).setText(ZGthree);
        ((TextView) findViewById(R.id.tv_suggest4_state)).setText(ZGfour);
        ((TextView) findViewById(R.id.tv_suggest5_state)).setText(ZGfive);
        tvOtherZGIdeaContent = (TextView) findViewById(R.id.tv_otherZGIdea_content);//其它整改意见内容
        tvOtherZGIdeaContent.setText(mCompanyDetailByCheckedModel.getRectificationSuggestions());

        //领导意见
        ((TextView) findViewById(R.id.tv_suggest6_state)).setText(LDone);
        ((TextView) findViewById(R.id.tv_suggest7_state)).setText(LDtwo);
        ((TextView) findViewById(R.id.tv_suggest8)).setText(LDsign);//领导意见
        ((TextView) findViewById(R.id.tv_suggest9_state)).setText(LDthree);
        //String title, List<CheckListModel> mMemberModels
        final List<CheckGroupModel> CheckGroupModelList = new ArrayList<>();

        //String title, int type, String state
        CheckListModel one = new CheckListModel("（一）是否存在涉密测绘成果涉密情况", 1, JCone);
        CheckListModel two = new CheckListModel("（二）被检单位拥有的涉密测绘结果种类", 0, "是");
        CheckListModel three = new CheckListModel("（三）保密机构是否健全", 1, JCThree);
        CheckListModel four = new CheckListModel("（四）保密规章制度是否健全", 1, JCFour);
        CheckListModel five = new CheckListModel("（五）是否配备“三铁一器”及监控设备", 1, JCFive);
        CheckListModel six = new CheckListModel("（六）是否建立涉密成果管理平台", 1, "是");
        CheckListModel seven = new CheckListModel("（七）是否有专人管理涉密成果", 1, "是");
        CheckListModel eight = new CheckListModel("（八）是否与涉密人员签订保密协议", 1, "是");
        CheckListModel nine = new CheckListModel("（九）是否存在涉密测绘成果擅自转让", 1, "是");
        CheckListModel ten = new CheckListModel("（十）移动存储介质管理是否符合要求", 1, "是");
        CheckListModel eleven = new CheckListModel("（十一）涉密计算机是否连入互联网", 1, "是");
        CheckListModel twelve = new CheckListModel("（十二）涉密计算机密码是否按要求设置为动态密码", 1, "是");
        CheckListModel thirteen = new CheckListModel("（十三）涉密计算机端口，串口是否符合保密规定", 1, "是");
        CheckListModel fourteen = new CheckListModel("（十四）销毁测绘地理信息成果是否按规定程序报批市规划局备案及到指定位置销毁", 1, "是");
        CheckListModel fiveteen = new CheckListModel("（十五）核心岗位涉密成员", 1, "是");
        CheckListModel sixteen = new CheckListModel("（十六）其他问题：销毁测绘地理信息成果是否按规定程序报批市规划局备案及到指定位置销毁", 1, "是");
        CheckGroupModel tacok = new CheckGroupModel("小队", Arrays.asList(one, two,
                three, four, five, six, seven, eight, nine, ten, eleven, twelve, thirteen, fourteen, fiveteen, sixteen));

        CheckGroupModelList.add(tacok);

        CheckSituationAdapter checkSituationAdapter = new CheckSituationAdapter(this, CheckGroupModelList);
//        for (int i = 0; i < 10; i++) {
//            //String examiner, String checkTime
//            UnitDetailModel UnitDetailModel = new UnitDetailModel(R.mipmap.bg_login);
//            lists.add(UnitDetailModel);
//        }
        CensorshipDetailAdapter censorshipDetailAdapter = new CensorshipDetailAdapter(this, mListViewPager);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        recyclerView1.setLayoutManager(linearLayoutManager);
        recyclerView1.setAdapter(checkSituationAdapter);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        recyclerView2.setAdapter(censorshipDetailAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView2.setLayoutManager(linearLayoutManager);
        censorshipDetailAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                AlertDialog myDialog = new AlertDialog.Builder(CensorshipDetailActivity.this).create();
                myDialog.show();
                myDialog.getWindow().setContentView(R.layout.layout_browse_viewpager);
                myViewPager = (MyViewPager) myDialog.getWindow().findViewById(R.id.my_viewpager);
                MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(CensorshipDetailActivity.this, mListViewPager);
                myViewPager.setAdapter(myViewPagerAdapter);
                myViewPager.setCurrentItem(position);
                myDialog.getWindow().findViewById(R.id.img_browse_back).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myDialog.dismiss();
                    }
                });
            }
        });
    }

    private void addAchievement() {
        if (mCompanyDetailByCheckedModel.getSecrecyInspectFruitList() != null) {
            for (SecrecyInspectFruitListModel secrecyInspectFruitListModel : mCompanyDetailByCheckedModel.getSecrecyInspectFruitList()) {
                View viewEdit = getLayoutInflater().inflate(R.layout.layout_jcxq_item, llResultType, false);

//            viewEdit.setId(88888888 + i);
                TextView resultTypeContent = (TextView) viewEdit.findViewById(R.id.result_type_content);
//            String typeContent = Config.RESULT_TYPE[mListCRResultTypeModel.get(i).getIndex()] + "：" + mListCRResultTypeModel.get(i).getRTypeContent();
//                resultTypeContent.setText(secrecyInspectFruitListModel.getDetailsName() + ":" + secrecyInspectFruitListModel.getDetailsValue());
                resultTypeContent.setText(secrecyInspectFruitListModel.getDetailsValue());
                llResultType.addView(viewEdit);
//            if(i!=mListCRResultTypeModel.size()-1){
//
//            }
            }
            View viewDivider = getLayoutInflater().inflate(R.layout.layout_divider_view, llResultType, false);
            llResultType.addView(viewDivider);
        }
    }
}
