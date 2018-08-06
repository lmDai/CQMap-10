package android.zhixun.uiho.com.gissystem.ui.activity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.app.Config;
import android.zhixun.uiho.com.gissystem.app.MyBaseApplication;
import android.zhixun.uiho.com.gissystem.flux.models.CRImageModel;
import android.zhixun.uiho.com.gissystem.flux.models.CRModel;
import android.zhixun.uiho.com.gissystem.flux.models.RXProgress;
import android.zhixun.uiho.com.gissystem.flux.models.api.AchievementTypeAndCountModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.CheckModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.CheckUserModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.CompanyDetailByCheckedModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.CompanyDetailModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.ImageUploadModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.SecrecyInspectEntryCompanyListModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.SecrecyInspectFruitCheckModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.SecrecyInspectFruitListModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.SecrecyInspectImgListModel;
import android.zhixun.uiho.com.gissystem.flux.models.api.SecrecyPersonListModel;
import android.zhixun.uiho.com.gissystem.greendao_gen.CRImageModelDao;
import android.zhixun.uiho.com.gissystem.greendao_gen.DaoSession;
import android.zhixun.uiho.com.gissystem.interfaces.OnItemClickListener;
import android.zhixun.uiho.com.gissystem.rest.APIService;
import android.zhixun.uiho.com.gissystem.rest.SimpleSubscriber;
import android.zhixun.uiho.com.gissystem.ui.adapter.CensorshipRegisterAdapter;
import android.zhixun.uiho.com.gissystem.ui.adapter.RlCheckPersonAdapter;
import android.zhixun.uiho.com.gissystem.ui.adapter.ShowSeachAdapter;
import android.zhixun.uiho.com.gissystem.ui.widget.FullyGridLayoutManager;
import android.zhixun.uiho.com.gissystem.ui.widget.MyViewPager;
import android.zhixun.uiho.com.gissystem.ui.widget.SimpleAlertDialog;

import com.alibaba.fastjson.JSONObject;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.yibogame.app.DoOnSubscriber;
import com.yibogame.app.RxBus;
import com.yibogame.flux.stores.Store;
import com.yibogame.ui.widget.BottomSelectableDialog;
import com.yibogame.util.DateUtil;
import com.yibogame.util.LogUtil;
import com.yibogame.util.SPUtil;
import com.yibogame.util.ToastUtil;
import com.yibogame.util.ValidateUtil;

import org.greenrobot.greendao.query.Query;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

//import android.zhixun.uiho.com.gissystem.flux.models.UserModel;

/**
 * 保密检查登记
 */
public class CensorshipRegisterActivity extends BaseActivityWithTitle implements TakePhoto.TakeResultListener, InvokeListener, TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    //    private ArrayList<TImage> images = new ArrayList<>();
    private static final String TAG = TakePhotoActivity.class.getName();
    private RecyclerView mRecyclerView;
    private List<CRImageModel> listImageModel = new ArrayList<>();
    private EditText autoCompleteTextView;//被检单位
    private CensorshipRegisterAdapter censorshipRegisterAdapter;
    private AppCompatImageView arrowJCQK;//检查情况的三角

    //检查情况
    private RadioGroup RadioGroup_JC_one, RadioGroup_JC_three, RadioGroup_JC_four,
            RadioGroup_JC_five, RadioGroup_JC_six, RadioGroup_JC_seven, RadioGroup_JC_eight, RadioGroup_JC_nine,
            RadioGroup_JC_ten, RadioGroup_JC_elven, RadioGroup_JC_twelve, RadioGroup_JC_thirteen, RadioGroup_JC_fourteen, RadioGroup_JC_fifteen;
    //整改意见
    private RadioGroup RadioGroup_ZG_one, RadioGroup_ZG_two, RadioGroup_ZG_three, RadioGroup_ZG_four, RadioGroup_ZG_five;
    //领导意见
    private RadioGroup RadioGroup_LI_one, RadioGroup_LI_two, RadioGroup_LI_three;

    private AppCompatButton acbSave;

    //判断是新增或修改
    private int tid = -1;

    private List<CheckUserModel> mListCheckPersonUser = new ArrayList<>();//参检人员的list
    private RecyclerView mRVCheckPerson;//参检人员的recycle人view
    private RlCheckPersonAdapter rlCheckPersonAdapter;
    private List<Long> mListCheckedPerson = new ArrayList<>();
    private EditText etOtherProblems;//其他问题说明
    private EditText etLeadersOpinions;//领导人意见
    private EditText etTempCheckPerson;//临时参检人员
    private EditText etMatchPerson, etContact, etContactNumber;
    private TextView tvTime;
    private FrameLayout flCalendar;//日历控件
    private LinearLayout llAchievementContainer;
    private List<CRModel> mListCRModel = new ArrayList<>();
    private AppCompatButton acbSaveServer;
    private List<Integer> picturePosition = new ArrayList<>();//用来保存要删除的图片位置。
    private String BJDWName;//根据这个变量在填充数据时就将被检单位的字段关联上去
    private ImageButton flSearch;//公司搜素按钮
    private LinearLayout llArrowJCQK;//检查情况的缩放大布局
    private AppCompatImageView arrowZGYJ;//整改意见三角
    private LinearLayout llArrowZGYJ;//整改意见的缩放大布局
    private AppCompatImageView arrowLDYJ;//领导意见
    private LinearLayout llArrowLDYJ;//领导意见缩放大布局
    private MyViewPager myViewPager;
    private List<String> mListTPLL = new ArrayList<>();//图片浏览
    private RecyclerView searchRvTips;//将收索的内容展示在该recyclerView里面。
    private EditText etOtherZGIdea;
    private Button mBtnExport;

    @Override
    protected Store initActionsCreatorAndStore() {
        return null;
    }

    @Override
    protected void onStoreCall(Store.StoreChangeEvent storeChangeEvent) {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_censorship_register;
    }

    private Subscription rxBusProgressSubscription, uploadSubscription;

    private int secrecyInspectId;
    private CompanyDetailByCheckedModel dd;
    private String tName;

    @Override
    protected void onCreateActivity(@Nullable Bundle savedInstanceState) {
        //获取TakePhoto实例
        getTakePhoto().onCreate(savedInstanceState);
        setTitleText("保密检查登记");
        //初始化
        initView();
//        isAdd = getIntent().getBooleanExtra("isAdd", true);
        tid = getIntent().getIntExtra("tid", -1);
        tName = getIntent().getStringExtra("tName");
        secrecyInspectId = getIntent().getIntExtra("secrecyInspectId", -1);
        dd = getIntent().getParcelableExtra("CompanyDetailByCheckedModel");

        LogUtil.e("tid=" + tid);
        mDaoSession = ((MyBaseApplication) getApplication()).getDaoSession();

        fillData();

        setSaveListener();

        rxBusProgressSubscription = RxBus.getInstance().toObservable(RXProgress.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<RXProgress>() {
                    @Override
                    public void call(RXProgress rxProgress) {
                        if (rxProgress.getClassSimpleName().equals(CensorshipRegisterActivity.class.getSimpleName())) {
                            LogUtil.e("progress=" + rxProgress.getProgress() + "...");
//                            ToastUtil.showShort("rxProgress.getProgress()=" + rxProgress.getProgress());
                        }
                    }
                });
        if (dd != null) {
            fillDataFromEdit();
        }
    }

    private DaoSession mDaoSession;

    private void setSaveListener() {
        acbSaveServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData(true, false);
            }
        });
        //添加到本地
        acbSave.setOnClickListener(view -> saveData(false, false));

    }

    private void fillDataFromEdit() {
        arcgisid = tid + "";
        LogUtil.d("单位id=" + arcgisid);
        autoCompleteTextView.setText(tName);
        etTempCheckPerson.setText(dd.getSecrecyPersonsInterim());
        etMatchPerson.setText(dd.getCooperatePersons());
        tvTime.setText(DateUtil.longToString(DateUtil.yyyyMMddHHmmss, dd.getSecrecyTime()));
        if (dd.getIsAdoptLeader() == 0) {
            ((RadioButton) RadioGroup_LI_one.findViewById(R.id.XCheckBox_one_not)).setChecked(true);
        }
        if (dd.getIsAdoptLeader() == 1) {
            ((RadioButton) RadioGroup_LI_one.findViewById(R.id.XCheckBox_one_ok)).setChecked(true);
        }
        if (dd.getIsSignCompany() == 0) {
            ((RadioButton) RadioGroup_LI_three.findViewById(R.id.XCheckBox_three_not)).setChecked(true);
        }
        if (dd.getIsSignCompany() == 1) {
            ((RadioButton) RadioGroup_LI_three.findViewById(R.id.XCheckBox_three_ok)).setChecked(true);
        }
        //
        if (dd.getIsSignLeader() == 0) {
            ((RadioButton) RadioGroup_LI_two.findViewById(R.id.XCheckBox_two_not)).setChecked(true);
        }
        if (dd.getIsSignLeader() == 1) {
            ((RadioButton) RadioGroup_LI_two.findViewById(R.id.XCheckBox_two_ok)).setChecked(true);
        }
        etLeadersOpinions.setText(dd.getLeaderReply());
        etContact.setText(dd.getPersonName());
        etContactNumber.setText(dd.getPhone());
        etOtherProblems.setText(dd.getRectificationSuggestions());

        for (SecrecyInspectImgListModel secrecyInspectImgListModel : dd.getSecrecyInspectImgList()) {
            ImageUploadModel imageUploadModel = new ImageUploadModel();
            imageUploadModel.setUrl(secrecyInspectImgListModel.getUrl());
            listImages.add(imageUploadModel);
            CRImageModel crImageModel = new CRImageModel();
            crImageModel.setRemotePath(secrecyInspectImgListModel.getUrl());
            listImageModel.add(crImageModel);
        }
        censorshipRegisterAdapter.notifyDataSetChanged();

        List<SecrecyInspectEntryCompanyListModel> secrecyInspectEntryCompanyList = dd.getSecrecyInspectEntryCompanyList();
//        int i = 0;
        for (SecrecyInspectEntryCompanyListModel secrecyInspectEntryCompanyListModel : secrecyInspectEntryCompanyList) {
//            LogUtil.d("i=" + i + ",size=" + secrecyInspectEntryCompanyList.size() + ",getId=" + secrecyInspectEntryCompanyListModel.getSecrecyInspectEntryId() + ",isPass=" + secrecyInspectEntryCompanyListModel.getIsPass());
//            i++;
            switch (secrecyInspectEntryCompanyListModel.getSecrecyInspectEntryId()) {
                case 1:
                    if (secrecyInspectEntryCompanyListModel.getIsPass() == 0) {
                        ((RadioButton) RadioGroup_JC_one.findViewById(R.id.XCheckBox_one_not)).setChecked(true);
                    }
                    if (secrecyInspectEntryCompanyListModel.getIsPass() == 1) {
                        ((RadioButton) RadioGroup_JC_one.findViewById(R.id.XCheckBox_one_ok)).setChecked(true);
                    }
                    break;
                case 3:
                    if (secrecyInspectEntryCompanyListModel.getIsPass() == 0) {
                        ((RadioButton) RadioGroup_JC_three.findViewById(R.id.XCheckBox_three_not)).setChecked(true);
                    }
                    if (secrecyInspectEntryCompanyListModel.getIsPass() == 1) {
                        ((RadioButton) RadioGroup_JC_three.findViewById(R.id.XCheckBox_three_ok)).setChecked(true);
                    }
                    break;
                case 4:
                    if (secrecyInspectEntryCompanyListModel.getIsPass() == 0) {
                        ((RadioButton) RadioGroup_JC_four.findViewById(R.id.XCheckBox_four_bad)).setChecked(true);
                    }
                    if (secrecyInspectEntryCompanyListModel.getIsPass() == 1) {
                        ((RadioButton) RadioGroup_JC_four.findViewById(R.id.XCheckBox_four_range)).setChecked(true);
                    }
                    if (secrecyInspectEntryCompanyListModel.getIsPass() == 2) {
                        ((RadioButton) RadioGroup_JC_four.findViewById(R.id.XCheckBox_four_common)).setChecked(true);
                    }
                    if (secrecyInspectEntryCompanyListModel.getIsPass() == 3) {
                        ((RadioButton) RadioGroup_JC_four.findViewById(R.id.XCheckBox_four_better)).setChecked(true);
                    }
                    if (secrecyInspectEntryCompanyListModel.getIsPass() == 4) {
                        ((RadioButton) RadioGroup_JC_four.findViewById(R.id.XCheckBox_four_ok)).setChecked(true);
                    }
                    break;
                case 5:
                    if (secrecyInspectEntryCompanyListModel.getIsPass() == 0) {
                        ((RadioButton) RadioGroup_JC_five.findViewById(R.id.XCheckBox_five_not)).setChecked(true);
                    }
                    if (secrecyInspectEntryCompanyListModel.getIsPass() == 1) {
                        ((RadioButton) RadioGroup_JC_five.findViewById(R.id.XCheckBox_five_ok)).setChecked(true);
                    }
                    break;
                case 6:
                    if (secrecyInspectEntryCompanyListModel.getIsPass() == 0) {
                        ((RadioButton) RadioGroup_JC_six.findViewById(R.id.XCheckBox_six_not)).setChecked(true);
                    }
                    if (secrecyInspectEntryCompanyListModel.getIsPass() == 1) {
                        ((RadioButton) RadioGroup_JC_six.findViewById(R.id.XCheckBox_six_ok)).setChecked(true);
                    }
                    break;
                case 7:
                    if (secrecyInspectEntryCompanyListModel.getIsPass() == 0) {
                        ((RadioButton) RadioGroup_JC_seven.findViewById(R.id.XCheckBox_seven_not)).setChecked(true);
                    }
                    if (secrecyInspectEntryCompanyListModel.getIsPass() == 1) {
                        ((RadioButton) RadioGroup_JC_seven.findViewById(R.id.XCheckBox_seven_ok)).setChecked(true);
                    }
                    break;
                case 8:
                    if (secrecyInspectEntryCompanyListModel.getIsPass() == 0) {
                        ((RadioButton) RadioGroup_JC_eight.findViewById(R.id.XCheckBox_eight_not)).setChecked(true);
                    }
                    if (secrecyInspectEntryCompanyListModel.getIsPass() == 1) {
                        ((RadioButton) RadioGroup_JC_eight.findViewById(R.id.XCheckBox_eight_ok)).setChecked(true);
                    }
                    break;
                case 9:
                    if (secrecyInspectEntryCompanyListModel.getIsPass() == 0) {
                        ((RadioButton) RadioGroup_JC_nine.findViewById(R.id.XCheckBox_nine_not)).setChecked(true);
                    }
                    if (secrecyInspectEntryCompanyListModel.getIsPass() == 1) {
                        ((RadioButton) RadioGroup_JC_nine.findViewById(R.id.XCheckBox_nine_ok)).setChecked(true);
                    }
                    break;
                case 10:
                    if (secrecyInspectEntryCompanyListModel.getIsPass() == 0) {
                        ((RadioButton) RadioGroup_JC_ten.findViewById(R.id.XCheckBox_ten_not)).setChecked(true);
                    }
                    if (secrecyInspectEntryCompanyListModel.getIsPass() == 1) {
                        ((RadioButton) RadioGroup_JC_ten.findViewById(R.id.XCheckBox_ten_ok)).setChecked(true);
                    }
                    break;
                case 11:
                    if (secrecyInspectEntryCompanyListModel.getIsPass() == 0) {
                        ((RadioButton) RadioGroup_JC_elven.findViewById(R.id.XCheckBox_eleven_not)).setChecked(true);
                    }
                    if (secrecyInspectEntryCompanyListModel.getIsPass() == 1) {
                        ((RadioButton) RadioGroup_JC_elven.findViewById(R.id.XCheckBox_eleven_ok)).setChecked(true);
                    }
                    break;
                case 12:
                    if (secrecyInspectEntryCompanyListModel.getIsPass() == 0) {
                        ((RadioButton) RadioGroup_JC_twelve.findViewById(R.id.XCheckBox_twelve_not)).setChecked(true);
                    }
                    if (secrecyInspectEntryCompanyListModel.getIsPass() == 1) {
                        ((RadioButton) RadioGroup_JC_twelve.findViewById(R.id.XCheckBox_twelve_ok)).setChecked(true);
                    }
                    break;
                case 13:
                    if (secrecyInspectEntryCompanyListModel.getIsPass() == 0) {
                        ((RadioButton) RadioGroup_JC_thirteen.findViewById(R.id.XCheckBox_thirteen_not)).setChecked(true);
                    }
                    if (secrecyInspectEntryCompanyListModel.getIsPass() == 1) {
                        ((RadioButton) RadioGroup_JC_thirteen.findViewById(R.id.XCheckBox_thirteen_ok)).setChecked(true);
                    }
                    break;
                case 14:
                    if (secrecyInspectEntryCompanyListModel.getIsPass() == 0) {
                        ((RadioButton) RadioGroup_JC_fourteen.findViewById(R.id.XCheckBox_fourteen_not)).setChecked(true);
                    }
                    if (secrecyInspectEntryCompanyListModel.getIsPass() == 1) {
                        ((RadioButton) RadioGroup_JC_fourteen.findViewById(R.id.XCheckBox_fourteen_ok)).setChecked(true);
                    }
                    break;
                case 15:
                    if (secrecyInspectEntryCompanyListModel.getIsPass() == 0) {
                        ((RadioButton) RadioGroup_JC_fifteen.findViewById(R.id.XCheckBox_fifteen_not)).setChecked(true);
                    }
                    if (secrecyInspectEntryCompanyListModel.getIsPass() == 1) {
                        ((RadioButton) RadioGroup_JC_fifteen.findViewById(R.id.XCheckBox_fifteen_ok)).setChecked(true);
                    }
                    break;
                case 16:
                    if (secrecyInspectEntryCompanyListModel.getIsPass() == 0) {
                        ((RadioButton) RadioGroup_ZG_one.findViewById(R.id.XCheckBox_one_not)).setChecked(true);
                    }
                    if (secrecyInspectEntryCompanyListModel.getIsPass() == 1) {
                        ((RadioButton) RadioGroup_ZG_one.findViewById(R.id.XCheckBox_one_ok)).setChecked(true);
                    }
                    break;
                case 17:
                    if (secrecyInspectEntryCompanyListModel.getIsPass() == 0) {
                        ((RadioButton) RadioGroup_ZG_two.findViewById(R.id.XCheckBox_two_not)).setChecked(true);
                    }
                    if (secrecyInspectEntryCompanyListModel.getIsPass() == 1) {
                        ((RadioButton) RadioGroup_ZG_two.findViewById(R.id.XCheckBox_two_ok)).setChecked(true);
                    }
                    break;
                case 18:
                    if (secrecyInspectEntryCompanyListModel.getIsPass() == 0) {
                        ((RadioButton) RadioGroup_ZG_three.findViewById(R.id.XCheckBox_three_not)).setChecked(true);
                    }
                    if (secrecyInspectEntryCompanyListModel.getIsPass() == 1) {
                        ((RadioButton) RadioGroup_ZG_three.findViewById(R.id.XCheckBox_three_ok)).setChecked(true);
                    }
                    break;
                case 19:
                    if (secrecyInspectEntryCompanyListModel.getIsPass() == 0) {
                        ((RadioButton) RadioGroup_ZG_four.findViewById(R.id.XCheckBox_four_not)).setChecked(true);
                    }
                    if (secrecyInspectEntryCompanyListModel.getIsPass() == 1) {
                        ((RadioButton) RadioGroup_ZG_four.findViewById(R.id.XCheckBox_four_ok)).setChecked(true);
                    }
                    break;
                case 20:
                    if (secrecyInspectEntryCompanyListModel.getIsPass() == 0) {
                        ((RadioButton) RadioGroup_ZG_five.findViewById(R.id.XCheckBox_five_not)).setChecked(true);
                    }
                    if (secrecyInspectEntryCompanyListModel.getIsPass() == 1) {
                        ((RadioButton) RadioGroup_ZG_five.findViewById(R.id.XCheckBox_five_ok)).setChecked(true);
                    }
                    break;
            }
        }
        for (SecrecyInspectFruitListModel secrecyInspectFruitListModel : dd.getSecrecyInspectFruitList()) {
            etValues.setText(secrecyInspectFruitListModel.getDetailsValue());
        }
    }


    private void saveData(boolean isSaveToServer, boolean export) {
        if (RadioGroup_LI_one.getCheckedRadioButtonId() != R.id.XCheckBox_one_ok && RadioGroup_LI_one.getCheckedRadioButtonId() != R.id.XCheckBox_one_not) {
            ToastUtil.showShort("请选择是否检查通过！");
            return;
        }

        if (!("".equals(autoCompleteTextView.getText().toString()))) {
//            if (tid == -1) {
            //添加
            if (arcgisid.equals("")) {
                ToastUtil.showLong("请选择登记公司");
                return;
            }
            Map<Object, Object> map = new HashMap<>();
            if (secrecyInspectId != -1) {
                map.put("secrecyInspectId", secrecyInspectId);
            }
            map.put("companyId", arcgisid);
            map.put("secrecyPersonsInterim", etTempCheckPerson.getText().toString());
            map.put("cooperatePersons", etMatchPerson.getText().toString());//配合人员（id按逗号拼接，如：1,2）
            map.put("isAdoptLeader", RadioGroup_LI_one.getCheckedRadioButtonId() == R.id.XCheckBox_one_ok ? 1 : RadioGroup_LI_one.getCheckedRadioButtonId() == R.id.XCheckBox_one_not ? 0 : -1);//领导意见是否通过（0不通过1通过），
            map.put("isLocal", isSaveToServer ? 1 : 0);//是否本地（0本地1外地）
            map.put("registerStatus", 1);
            map.put("isSignCompany", RadioGroup_LI_three.getCheckedRadioButtonId() == R.id.XCheckBox_three_ok ? 1 : RadioGroup_LI_three.getCheckedRadioButtonId() == R.id.XCheckBox_three_not ? 0 : -1);//单位是否签字（0未签1已签），
            map.put("isSignLeader", RadioGroup_LI_two.getCheckedRadioButtonId() == R.id.XCheckBox_two_ok ? 1 : RadioGroup_LI_two.getCheckedRadioButtonId() == R.id.XCheckBox_two_not ? 0 : -1);//领导是否签字（0未签1已签）
            map.put("leaderReply", etLeadersOpinions.getText().toString());//领导批复内容，
            map.put("personName", etContact.getText().toString());//联系人，
            map.put("phone", etContactNumber.getText().toString());//联系电话，
            map.put("rectificationSuggestions", etOtherProblems.getText().toString());
            listImages.clear();
            for (int i = 1; i < listImageModel.size(); i++) {
                ImageUploadModel imageUploadModel = new ImageUploadModel();
                imageUploadModel.setUrl(listImageModel.get(i).getRemotePath());
                listImages.add(imageUploadModel);
            }
            map.put("secrecyInspectImgList", listImages);//[现场图片]
            map.put("secrecyCompanyId", 0);//参检单位，
            List<CheckModel> list = new ArrayList<>();
            CheckModel checkModel1 = new CheckModel();
            checkModel1.setIsPass(RadioGroup_JC_one.getCheckedRadioButtonId() == R.id.XCheckBox_one_ok ? 1 : RadioGroup_JC_one.getCheckedRadioButtonId() == R.id.XCheckBox_one_not ? 0 : -1);//是否存在涉密测绘成果失涉密情况
            checkModel1.setSecrecyInspectEntryId(1);
            list.add(checkModel1);

            CheckModel checkModel2 = new CheckModel();
            checkModel2.setIsPass(RadioGroup_JC_three.getCheckedRadioButtonId() == R.id.XCheckBox_three_ok ? 1 : RadioGroup_JC_three.getCheckedRadioButtonId() == R.id.XCheckBox_three_not ? 0 : -1);//保密机构是否健全
            checkModel2.setSecrecyInspectEntryId(3);
            list.add(checkModel2);

            int isPass = -1;
            if (RadioGroup_JC_four.getCheckedRadioButtonId() == R.id.XCheckBox_four_ok) {//4
                isPass = 4;
            } else if (RadioGroup_JC_four.getCheckedRadioButtonId() == R.id.XCheckBox_four_better) {
                isPass = 3;
            } else if (RadioGroup_JC_four.getCheckedRadioButtonId() == R.id.XCheckBox_four_common) {
                isPass = 2;
            } else if (RadioGroup_JC_four.getCheckedRadioButtonId() == R.id.XCheckBox_four_range) {
                isPass = 1;
            } else if (RadioGroup_JC_four.getCheckedRadioButtonId() == R.id.XCheckBox_four_bad) {
                isPass = 0;
            }
            CheckModel checkModel3 = new CheckModel();//保密规章制度是否健全
            checkModel3.setIsPass(isPass);
            checkModel3.setSecrecyInspectEntryId(4);
            list.add(checkModel3);

            CheckModel checkModel4 = new CheckModel();//是否配备“三铁一器”及监控设备
            checkModel4.setIsPass(RadioGroup_JC_five.getCheckedRadioButtonId() == R.id.XCheckBox_five_ok ? 1 : RadioGroup_JC_five.getCheckedRadioButtonId() == R.id.XCheckBox_five_not ? 0 : -1);
            checkModel4.setSecrecyInspectEntryId(5);
            list.add(checkModel4);

            CheckModel checkModel5 = new CheckModel();//是否建立涉密成果管理台账
            checkModel5.setIsPass(RadioGroup_JC_six.getCheckedRadioButtonId() == R.id.XCheckBox_six_ok ? 1 : RadioGroup_JC_six.getCheckedRadioButtonId() == R.id.XCheckBox_six_not ? 0 : -1);
            checkModel5.setSecrecyInspectEntryId(6);
            list.add(checkModel5);

            CheckModel checkModel6 = new CheckModel();//是否有专人管理涉密成果
            checkModel6.setIsPass(RadioGroup_JC_seven.getCheckedRadioButtonId() == R.id.XCheckBox_seven_ok ? 1 : RadioGroup_JC_seven.getCheckedRadioButtonId() == R.id.XCheckBox_seven_not ? 0 : -1);
            checkModel6.setSecrecyInspectEntryId(7);
            list.add(checkModel6);

            CheckModel checkModel7 = new CheckModel();//是否与涉密人员签订保密协议
            checkModel7.setIsPass(RadioGroup_JC_eight.getCheckedRadioButtonId() == R.id.XCheckBox_eight_ok ? 1 : RadioGroup_JC_eight.getCheckedRadioButtonId() == R.id.XCheckBox_eight_not ? 0 : -1);
            checkModel7.setSecrecyInspectEntryId(8);
            list.add(checkModel7);

            CheckModel checkModel8 = new CheckModel();//是否存在涉密测绘成果擅自转让情况
            checkModel8.setIsPass(RadioGroup_JC_nine.getCheckedRadioButtonId() == R.id.XCheckBox_nine_ok ? 1 : RadioGroup_JC_nine.getCheckedRadioButtonId() == R.id.XCheckBox_nine_not ? 0 : -1);
            checkModel8.setSecrecyInspectEntryId(9);
            list.add(checkModel8);

            CheckModel checkModel9 = new CheckModel();//移动存储介质管理是否符合要求
            checkModel9.setIsPass(RadioGroup_JC_ten.getCheckedRadioButtonId() == R.id.XCheckBox_ten_ok ? 1 : RadioGroup_JC_ten.getCheckedRadioButtonId() == R.id.XCheckBox_ten_not ? 0 : -1);
            checkModel9.setSecrecyInspectEntryId(10);
            list.add(checkModel9);

            CheckModel checkModel10 = new CheckModel();//涉密计算机是否连入互联网
            checkModel10.setIsPass(RadioGroup_JC_elven.getCheckedRadioButtonId() == R.id.XCheckBox_eleven_ok ? 1 : RadioGroup_JC_elven.getCheckedRadioButtonId() == R.id.XCheckBox_eleven_not ? 0 : -1);
            checkModel10.setSecrecyInspectEntryId(11);
            list.add(checkModel10);

            CheckModel checkModel11 = new CheckModel();//涉密计算机密码是否按要求设置动态密码
            checkModel11.setIsPass(RadioGroup_JC_twelve.getCheckedRadioButtonId() == R.id.XCheckBox_twelve_ok ? 1 : RadioGroup_JC_twelve.getCheckedRadioButtonId() == R.id.XCheckBox_twelve_not ? 0 : -1);
            checkModel11.setSecrecyInspectEntryId(12);
            list.add(checkModel11);

            CheckModel checkModel12 = new CheckModel();//涉密计算机端口、串口是否符合保密规定
            checkModel12.setIsPass(RadioGroup_JC_thirteen.getCheckedRadioButtonId() == R.id.XCheckBox_thirteen_ok ? 1 : RadioGroup_JC_thirteen.getCheckedRadioButtonId() == R.id.XCheckBox_thirteen_not ? 0 : -1);
            checkModel12.setSecrecyInspectEntryId(13);
            list.add(checkModel12);

            CheckModel checkModel13 = new CheckModel();//损毁测绘地理信息成果是否按规定程序报批市规划局备案及到指定位置销毁
            checkModel13.setIsPass(RadioGroup_JC_fourteen.getCheckedRadioButtonId() == R.id.XCheckBox_fourteen_ok ? 1 : RadioGroup_JC_fourteen.getCheckedRadioButtonId() == R.id.XCheckBox_fourteen_not ? 0 : -1);
            checkModel13.setSecrecyInspectEntryId(14);
            list.add(checkModel13);

            CheckModel checkModel14 = new CheckModel();//核心岗位涉密人员持证情况
            checkModel14.setIsPass(RadioGroup_JC_fifteen.getCheckedRadioButtonId() == R.id.XCheckBox_fifteen_ok ? 1 : RadioGroup_JC_fifteen.getCheckedRadioButtonId() == R.id.XCheckBox_fifteen_not ? 0 : -1);
            checkModel14.setSecrecyInspectEntryId(15);
            list.add(checkModel14);

            CheckModel checkModel15 = new CheckModel();//是否需要进一步健全保密制度
            checkModel15.setIsPass(RadioGroup_ZG_one.getCheckedRadioButtonId() == R.id.XCheckBox_one_ok ? 1 : RadioGroup_ZG_one.getCheckedRadioButtonId() == R.id.XCheckBox_one_not ? 0 : -1);
            checkModel15.setSecrecyInspectEntryId(16);
            list.add(checkModel15);

            CheckModel checkModel16 = new CheckModel();//是否需要进一步健全保密机构
            checkModel16.setIsPass(RadioGroup_ZG_two.getCheckedRadioButtonId() == R.id.XCheckBox_two_ok ? 1 : RadioGroup_ZG_two.getCheckedRadioButtonId() == R.id.XCheckBox_two_not ? 0 : -1);
            checkModel16.setSecrecyInspectEntryId(17);
            list.add(checkModel16);

            CheckModel checkModel17 = new CheckModel();//是否需要进一步落实保密措施
            checkModel17.setIsPass(RadioGroup_ZG_three.getCheckedRadioButtonId() == R.id.XCheckBox_three_ok ? 1 : RadioGroup_ZG_three.getCheckedRadioButtonId() == R.id.XCheckBox_three_not ? 0 : -1);
            checkModel17.setSecrecyInspectEntryId(18);
            list.add(checkModel17);

            CheckModel checkModel18 = new CheckModel();//是否需要进一步落实保密措施
            checkModel18.setIsPass(RadioGroup_ZG_four.getCheckedRadioButtonId() == R.id.XCheckBox_four_ok ? 1 : RadioGroup_ZG_four.getCheckedRadioButtonId() == R.id.XCheckBox_four_not ? 0 : -1);
            checkModel18.setSecrecyInspectEntryId(19);
            list.add(checkModel18);

            CheckModel checkModel19 = new CheckModel();//是否需要进一步全员开展培训
            checkModel19.setIsPass(RadioGroup_ZG_five.getCheckedRadioButtonId() == R.id.XCheckBox_five_ok ? 1 : RadioGroup_ZG_five.getCheckedRadioButtonId() == R.id.XCheckBox_five_not ? 0 : -1);
            checkModel19.setSecrecyInspectEntryId(20);
            list.add(checkModel19);

            map.put("secrecyInspectEntryCompanyList", list);//[检查项目/整改意见]

            List<SecrecyInspectFruitCheckModel> listSecrecyInspectFruitCheckModel = new ArrayList<>();
            if (!TextUtils.isEmpty(etValues.getText().toString())) {
                SecrecyInspectFruitCheckModel secrecyInspectFruitCheckModel = new SecrecyInspectFruitCheckModel();
                secrecyInspectFruitCheckModel.setDetailsValue(etValues.getText().toString());
                listSecrecyInspectFruitCheckModel.add(secrecyInspectFruitCheckModel);
            }
//                for (int i = 0; i < llAchievementContainer.getChildCount(); i++) {
//                    AppCompatCheckBox appCompatCheckBox = (AppCompatCheckBox) llAchievementContainer.getChildAt(i).findViewById(R.id.accb);
//                    EditText editText = (EditText) llAchievementContainer.getChildAt(i).findViewById(R.id.resultType_one);
//                    if (appCompatCheckBox.isChecked()) {
//                        SecrecyInspectFruitCheckModel secrecyInspectFruitCheckModel = new SecrecyInspectFruitCheckModel();
//                        secrecyInspectFruitCheckModel.setFruitCategoryId(Integer.parseInt(appCompatCheckBox.getTag().toString().split(",")[0]));
//                        secrecyInspectFruitCheckModel.setDetailsName(appCompatCheckBox.getTag().toString().split(",")[1]);
//                        secrecyInspectFruitCheckModel.setDetailsValue(editText.getText().toString());
//                        listSecrecyInspectFruitCheckModel.add(secrecyInspectFruitCheckModel);
//                    }
//                }
            map.put("secrecyInspectFruitList", listSecrecyInspectFruitCheckModel);//[成果检查]

            String secrecyPersonsStr = "";

            for (int i = 0; i < mListCheckedPerson.size(); i++) {
                if (i != mListCheckedPerson.size() - 1) {
                    secrecyPersonsStr += mListCheckedPerson.get(i) + ",";
                } else {
                    secrecyPersonsStr += mListCheckedPerson.get(i);
                }
            }
            map.put("secrecyPersons", secrecyPersonsStr);//参检人员（id按逗号拼接，如：1,2），
            if (!tvTime.getText().toString().equals("") && DateUtil.stringToLong(DateUtil.yyyyMMddHHmmss, tvTime.getText().toString()) != 0) {
                map.put("secrecyTimes", DateUtil.stringToLong(DateUtil.yyyyMMddHHmmss, tvTime.getText().toString()));//检查时间（暂时Date），
            }
            String userJson = SPUtil.getInstance().getString("UserModelOfJson");
            android.zhixun.uiho.com.gissystem.flux.models.api.UserModel userModel = JSONObject.parseObject(userJson, android.zhixun.uiho.com.gissystem.flux.models.api.UserModel.class);
            map.put("userId", userModel.getUserId());//提交人id，
            APIService.getInstance().commitCheck(map, new DoOnSubscriber<String>() {
                @Override
                public void doOnSubscriber() {
                    if (isSaveToServer) {
                        //这里要做一个菊花弹出框
                    }
                }

                @Override
                public void onCompleted() {
                    if (!export) {
                        ToastUtil.showShort("提交成功！");
                        finish();
                    }
//                    finish();
                }

                @Override
                public void onNext(String s) {
                    if (export) {
                        showExportDialog(s);
                    }
                }
            });


//            } else {
//
//            }
        } else {
            ToastUtil.showShort("被检查单位不能为空");
        }
    }

    private void showExportDialog(String secrecyInspectId) {
        new SimpleAlertDialog(mContext)
                .visibleEditText()
                .title("导出")
                .message("请输入一个您需要导出的邮箱地址")
                .setOkOnClickListener("确定", (dialog, view) -> {
                    String email = dialog.getEditText().getText().toString();
                    if (!ValidateUtil.getInstance().validateEmail(email)) {
                        ToastUtil.showShort("请输入一个正确的邮箱地址哦！");
                        return;
                    }
                    export(secrecyInspectId, email);
                }).setCancelOnClickListener("取消", null)
                .show();
    }

    private void export(String secrecyInspectId, String email) {
        showLoading();
        Map<Object, Object> map = new HashMap<>();
        map.put("secrecyInspectId", secrecyInspectId);
        map.put("toMail", email);
        APIService.getInstance()
                .export(map, new SimpleSubscriber<String>() {
                    @Override
                    public void onResponse(String response) {
                        hideLoading();
                        ToastUtil.showShort("导出成功！");
                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        hideLoading();
                        ToastUtil.showShort(e.getMessage());
                    }
                });
    }

    private String arcgisid = "";

    private void fillData() {
        mListCheckPersonUser.clear();
        Map<Object, Object> map = new HashMap<>();
        map.put("userType", "3");
        APIService.getInstance().getCheckUserList(map, new DoOnSubscriber<List<CheckUserModel>>() {
            @Override
            public void doOnSubscriber() {

            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onNext(List<CheckUserModel> checkUserModels) {
                mListCheckPersonUser.addAll(checkUserModels);
                if (dd != null) {
                    for (SecrecyPersonListModel secrecyPersonListModel : dd.getSecrecyPersonList()) {
                        for (CheckUserModel checkUserModel : mListCheckPersonUser) {
                            if (checkUserModel.getUserId() == secrecyPersonListModel.getUserId()) {
                                checkUserModel.setChecked(true);
                                break;
                            }
                        }
                    }
                }
                rlCheckPersonAdapter.notifyDataSetChanged();

            }
        });

        LogUtil.e("公司名称-------》" + BJDWName);
        if (tid == -1) {
            //被检单位
//            if(BJDWName!=null){
//                autoCompleteTextView.setText(BJDWName);
//            }   开始不显示公司的名称
            LogUtil.e("添加");
        } else {
            LogUtil.e("修改");
        }
    }

    private EditText etValues;

    private void initView() {
        searchRvTips = (RecyclerView) findViewById(R.id.search_lv_tips);//展示搜索结果的RecyclerView
        llAchievementContainer = (LinearLayout) findViewById(R.id.ll_achievement_container);
        etValues = (EditText) findViewById(R.id.et_values);
        flCalendar = (FrameLayout) findViewById(R.id.flCalendar);//日历控件
        flSearch = (ImageButton) findViewById(R.id.flllll);//公司搜索
        autoCompleteTextView = (EditText) findViewById(R.id.AutoCompleteTextView1);//被检查单位输入框
        arrowLDYJ = (AppCompatImageView) findViewById(R.id.arrow_LDYJ);//领导意见
        llArrowLDYJ = (LinearLayout) findViewById(R.id.ll_arrow_LDYJ);
        etTempCheckPerson = (EditText) findViewById(R.id.et_temp_check_person);//临时参检人员
        etOtherZGIdea = (EditText) findViewById(R.id.et_otherZGIdea);//其它整改意见

        arrowLDYJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (llArrowLDYJ.getVisibility() == View.VISIBLE) {
                    arrowLDYJ.setImageResource(R.mipmap.ic_arrow_drop_up_black_36dp);
                    llArrowLDYJ.setVisibility(View.GONE);

                } else {
                    arrowLDYJ.setImageResource(R.mipmap.arrow_bottom);
                    llArrowLDYJ.setVisibility(View.VISIBLE);

                }

            }
        });

        arrowZGYJ = (AppCompatImageView) findViewById(R.id.arrow_ZGYJ);
        llArrowZGYJ = (LinearLayout) findViewById(R.id.ll_arrow_ZGYJ);
        arrowZGYJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (llArrowZGYJ.getVisibility() == View.VISIBLE) {
                    arrowZGYJ.setImageResource(R.mipmap.ic_arrow_drop_up_black_36dp);
                    llArrowZGYJ.setVisibility(View.GONE);

                } else {
                    arrowZGYJ.setImageResource(R.mipmap.arrow_bottom);
                    llArrowZGYJ.setVisibility(View.VISIBLE);

                }

            }
        });
        arrowJCQK = (AppCompatImageView) findViewById(R.id.arrow_JCQK);
        llArrowJCQK = (LinearLayout) findViewById(R.id.ll_arrow_JCQK);
        arrowJCQK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (llArrowJCQK.getVisibility() == View.VISIBLE) {
                    arrowJCQK.setImageResource(R.mipmap.ic_arrow_drop_up_black_36dp);
                    llArrowJCQK.setVisibility(View.GONE);

                } else {
                    arrowJCQK.setImageResource(R.mipmap.arrow_bottom);
                    llArrowJCQK.setVisibility(View.VISIBLE);

                }

            }
        });
        //检查登记的公司搜索功能
        flSearch.setOnClickListener(view -> {
            String EditextContent = autoCompleteTextView.getText().toString();
            Map<Object, Object> map = new HashMap<>();
            map.put("key", EditextContent);
            APIService.getInstance().getCompanyList(map, new SimpleSubscriber<List<CompanyDetailModel>>() {
                @Override
                public void onResponse(List<CompanyDetailModel> companyDetailModels) {
                    if (companyDetailModels.size() > 0) {
                        searchRvTips.setVisibility(View.VISIBLE);
                    }
                    //构建Adapter
                    ShowSeachAdapter showSeachAdapter = new ShowSeachAdapter(companyDetailModels, CensorshipRegisterActivity.this);
                    searchRvTips.setLayoutManager(new LinearLayoutManager(CensorshipRegisterActivity.this));
                    searchRvTips.setAdapter(showSeachAdapter);
                    showSeachAdapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            autoCompleteTextView.setText(companyDetailModels.get(position).getCompanyName());
                            autoCompleteTextView.setSelection(autoCompleteTextView.getText().length());
                            searchRvTips.setVisibility(View.GONE);
                            arcgisid = companyDetailModels.get(position).getCompanyId() + "";
                            addAchievement();
                        }
                    });
                    InputMethodManager imm = (InputMethodManager) getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(autoCompleteTextView.getWindowToken(), 0);

                }

                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                    ToastUtil.showShort("未查询到信息，请重试");
                }
            });

        });
//        findViewById(R.id.aciv_plus).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (llAchievementContainer.getVisibility() == View.VISIBLE) {
//                    llAchievementContainer.setVisibility(View.GONE);
//                } else {
//                    llAchievementContainer.setVisibility(View.VISIBLE);
//                }
//            }
//        });
        etMatchPerson = (EditText) findViewById(R.id.et_match_person);
        etContact = (EditText) findViewById(R.id.et_contact);
        etContactNumber = (EditText) findViewById(R.id.et_contact_number);
        tvTime = (TextView) findViewById(R.id.tv_time);
        View situationCheck = findViewById(R.id.situation_check);//检查情况
        //其他问题说明
        etOtherProblems = (EditText) findViewById(R.id.et_otherProblems);

        //成果类型
        //RadioGroup
        RadioGroup_JC_three = (RadioGroup) situationCheck.findViewById(R.id.RadioGroup_JC_three);
        RadioGroup_JC_four = (RadioGroup) situationCheck.findViewById(R.id.RadioGroup_JC_four);
        RadioGroup_JC_five = (RadioGroup) situationCheck.findViewById(R.id.RadioGroup_JC_five);
        RadioGroup_JC_six = (RadioGroup) situationCheck.findViewById(R.id.RadioGroup_JC_six);
        RadioGroup_JC_seven = (RadioGroup) situationCheck.findViewById(R.id.RadioGroup_JC_seven);
        RadioGroup_JC_eight = (RadioGroup) situationCheck.findViewById(R.id.RadioGroup_JC_eight);
        RadioGroup_JC_nine = (RadioGroup) situationCheck.findViewById(R.id.RadioGroup_JC_nine);
        RadioGroup_JC_ten = (RadioGroup) situationCheck.findViewById(R.id.RadioGroup_JC_ten);
        RadioGroup_JC_elven = (RadioGroup) situationCheck.findViewById(R.id.RadioGroup_JC_eleven);
        RadioGroup_JC_twelve = (RadioGroup) situationCheck.findViewById(R.id.RadioGroup_JC_twelve);
        RadioGroup_JC_thirteen = (RadioGroup) situationCheck.findViewById(R.id.RadioGroup_JC_thirteen);
        RadioGroup_JC_fourteen = (RadioGroup) situationCheck.findViewById(R.id.RadioGroup_JC_fourteen);
        RadioGroup_JC_fifteen = (RadioGroup) situationCheck.findViewById(R.id.RadioGroup_JC_fifteen);


//
        View rectificationOpinions = findViewById(R.id.rectification_opinions);//整改意见
        //RadioGroup
        RadioGroup_ZG_one = (RadioGroup) rectificationOpinions.findViewById(R.id.RadioGroup_ZG_one);
        RadioGroup_ZG_two = (RadioGroup) rectificationOpinions.findViewById(R.id.RadioGroup_ZG_two);
        RadioGroup_ZG_three = (RadioGroup) rectificationOpinions.findViewById(R.id.RadioGroup_ZG_three);
        RadioGroup_ZG_four = (RadioGroup) rectificationOpinions.findViewById(R.id.RadioGroup_ZG_four);
        RadioGroup_ZG_five = (RadioGroup) rectificationOpinions.findViewById(R.id.RadioGroup_ZG_five);


        View leaderIdea = findViewById(R.id.leader_idea);//领导意见

        //领导人意见
        etLeadersOpinions = (EditText) leaderIdea.findViewById(R.id.et_leaders_opinions);
        //RadioGroup
        RadioGroup_LI_one = (RadioGroup) leaderIdea.findViewById(R.id.RadioGroup_LI_one);
        RadioGroup_LI_two = (RadioGroup) leaderIdea.findViewById(R.id.RadioGroup_LI_two);
        RadioGroup_LI_three = (RadioGroup) leaderIdea.findViewById(R.id.RadioGroup_LI_three);

        //提交至本地服务器按钮
        acbSaveServer = (AppCompatButton) findViewById(R.id.acb_save_server);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        Resources r = getResources();
        String plus = ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                + r.getResourcePackageName(R.mipmap.ic_plus) + "/"
                + r.getResourceTypeName(R.mipmap.ic_plus) + "/"
                + r.getResourceEntryName(R.mipmap.ic_plus);
        CRImageModel crImageMode = new CRImageModel(888L, 8888888, plus);
        listImageModel.add(crImageMode);
//        for (int i = 0; i < 10; i++) {
//            CRImageModel CRImageModel = new CRImageModel(R.mipmap.bg_login);
//            listImageModel.add(CRImageModel);
//        }

        censorshipRegisterAdapter = new CensorshipRegisterAdapter(CensorshipRegisterActivity.this, listImageModel);
        mRecyclerView.setLayoutManager(new FullyGridLayoutManager(CensorshipRegisterActivity.this, 4));
        mRecyclerView.setAdapter(censorshipRegisterAdapter);

        acbSave = (AppCompatButton) findViewById(R.id.acb_save);
        RadioGroup_JC_one = (RadioGroup) findViewById(R.id.RadioGroup_JC_one);
        mRVCheckPerson = (RecyclerView) findViewById(R.id.rl_checkPerson);
        FullyGridLayoutManager fullyGridLayoutManager = new FullyGridLayoutManager(this, 3);
        mRVCheckPerson.setLayoutManager(fullyGridLayoutManager);
//        mRVCheckPerson.addItemDecoration(new DividerGridItemDecoration(this));
        rlCheckPersonAdapter = new RlCheckPersonAdapter(mListCheckPersonUser);
        rlCheckPersonAdapter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Long userId = Long.parseLong(compoundButton.getTag().toString());
//                UserModel userModelTemp = mListCheckPersonUser.get(position);
                if (compoundButton.isChecked()) {
                    //bug fixed!
                    //因为这个事件在初始化的时候会导致多次执行，所以会添加多个相同的用户ID进去。
                    //解决方案：如果之前有了的用户ID就不要再添加了
                    if (!mListCheckedPerson.contains(userId)) {
                        mListCheckedPerson.add(userId);
                    }
                } else {
                    mListCheckedPerson.remove(userId);
                }
            }
        });
        mRVCheckPerson.setAdapter(rlCheckPersonAdapter);
        //设置删除的监听
        censorshipRegisterAdapter.setOnItemDeleteListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position != 0) {
                    if (tid == -1) {
                        AlertDialog myDialog = new AlertDialog.Builder(CensorshipRegisterActivity.this).create();
                        myDialog.show();

                        myDialog.getWindow().setContentView(R.layout.layout_mydialog_view);
                        myDialog.getWindow()
                                .findViewById(R.id.dialog_button_ok)
                                .setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        listImageModel.remove(position);
                                        censorshipRegisterAdapter.notifyDataSetChanged();
                                        myDialog.dismiss();
                                    }
                                });
                        myDialog.getWindow()
                                .findViewById(R.id.dialog_button_cancel)
                                .setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        myDialog.dismiss();
                                    }
                                });


                    } else {
                        AlertDialog myDialog = new AlertDialog.Builder(CensorshipRegisterActivity.this).create();
                        myDialog.show();
                        myDialog.getWindow().setContentView(R.layout.layout_mydialog_view);
                        myDialog.getWindow()
                                .findViewById(R.id.dialog_button_ok)
                                .setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (!TextUtils.isEmpty(listImageModel.get(position).getPath())) {
                                            //把图片查询出来
                                            CRImageModelDao crImageModelDao = mDaoSession.getCRImageModelDao();
                                            Query<CRImageModel> crImageModelQuery = crImageModelDao.queryBuilder().where(CRImageModelDao.Properties.Id.eq(listImageModel.get(position).getId())).build();
                                            CRImageModel crImageModel = crImageModelQuery.unique();
                                            crImageModelDao.delete(crImageModel);
                                            listImageModel.remove(crImageModel);
                                        }
                                        for (CRImageModel crImageModel : listImageModel) {
                                            if (!TextUtils.isEmpty(listImageModel.get(position).getRemotePath()) && listImageModel.get(position).getRemotePath().equals(crImageModel.getRemotePath())) {
                                                listImageModel.remove(crImageModel);
                                                break;
                                            }
                                        }

//                                        long crImage = crImageModelQuery.list().get(position).getId();
//                                        crImageModelDao.deleteByKey(crImage);
//                                        listImageModel.clear();

//                                        listImageModel.addAll(crImageModelQuery.list());
                                        censorshipRegisterAdapter.notifyDataSetChanged();
                                        myDialog.dismiss();
                                    }
                                });
                        myDialog.getWindow()
                                .findViewById(R.id.dialog_button_cancel)
                                .setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        myDialog.dismiss();
                                    }
                                });
                    }
                }
            }
        });
        censorshipRegisterAdapter.setOnItemClickListener(new OnItemClickListener() {
            BottomSelectableDialog bottomSelectableDialog;

            @Override
            public void onItemClick(View view, int position) {
                if (position == 0) {
//                    startActivity(new Intent(AddEventRecordActivity.this,SimpleActivity.class));
                    //-选择照片
                    //--如果是多选
//                    takePhoto.onPickMultipleWithCrop(Config.SELECT_IMAGE_LIMIT_MAX, getCropOptions());//启用裁剪
//                    takePhoto.onPickMultiple(Config.SELECT_IMAGE_LIMIT_MAX);//不启用裁剪
//                    //--如果是单选
//                    //---从文件
//                    takePhoto.onPickFromDocumentsWithCrop(getTempImageUri(), getCropOptions());//启用裁剪
//                    takePhoto.onPickFromDocuments();//不启用裁剪
//                    //---从相册
//                    takePhoto.onPickFromGalleryWithCrop(getTempImageUri(), getCropOptions());//启用裁剪
//                    takePhoto.onPickFromGallery();//不启用裁剪
//                    //-拍照
//                    takePhoto.onPickFromCaptureWithCrop(getTempImageUri(), getCropOptions());//启用裁剪
//                    takePhoto.onPickFromCapture(getTempImageUri());//不启用裁剪

                    BottomSelectableDialog.Builder builder = new BottomSelectableDialog.Builder();
                    bottomSelectableDialog = builder.setColor(getResources().getColor(R.color.colorPrimaryDark))
                            .addItem("拍照", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
                                    if (!file.getParentFile().exists()) {
                                        file.getParentFile().mkdirs();
                                    }
                                    Uri imageUri = Uri.fromFile(file);
                                    takePhoto.onPickFromCaptureWithCrop(imageUri, getCropOptions());//启用裁剪
                                    bottomSelectableDialog.dismiss();

                                }
                            }).addItem("从相册选择", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    takePhoto.onPickMultipleWithCrop(Config.SELECT_IMAGE_LIMIT_MAX, getCropOptions());//启用裁剪
                                    bottomSelectableDialog.dismiss();
                                }
                            }).build(CensorshipRegisterActivity.this);
                    bottomSelectableDialog.show();
                }
//
            }
        });
        flCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd1 = DatePickerDialog.newInstance(
                        CensorshipRegisterActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd1.setVersion(DatePickerDialog.Version.VERSION_2);
                dpd1.show(getSupportFragmentManager(), "Datepickerdialog");
//
            }
        });
        //导出
        mBtnExport = findViewById(R.id.btn_export);
        mBtnExport.setOnClickListener(v -> saveData(false, true));
    }


    private void addAchievement() {
        //通过return的方式注释之前的
        if (etValues != null) {
            return;
        }
        if (arcgisid.equals("")) {
            ToastUtil.showShort("单位ID为空!");
            return;
        }
        Map<Object, Object> map = new HashMap<>();
        map.put("companyId", arcgisid);
        APIService.getInstance().getAchievementTypeAndCountList(map, new DoOnSubscriber<List<AchievementTypeAndCountModel>>() {
            @Override
            public void doOnSubscriber() {

            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onNext(List<AchievementTypeAndCountModel> achievementTypeAndCountModels) {
                llAchievementContainer.removeAllViews();//移除所有View再加撒
                for (AchievementTypeAndCountModel achievementTypeAndCountModel : achievementTypeAndCountModels) {
                    View viewEdit = getLayoutInflater().inflate(R.layout.layout_edit, llAchievementContainer, false);
                    viewEdit.setTag(achievementTypeAndCountModel.getCategoryId() + "," + achievementTypeAndCountModel.getCategoryName());
                    AppCompatCheckBox appCompatCheckBox = (AppCompatCheckBox) viewEdit.findViewById(R.id.accb);
                    appCompatCheckBox.setTag(achievementTypeAndCountModel.getCategoryId() + "," + achievementTypeAndCountModel.getCategoryName());
                    EditText editText = (EditText) viewEdit.findViewById(R.id.resultType_one);
                    editText.setEnabled(false);
                    appCompatCheckBox.setText(achievementTypeAndCountModel.getCategoryName());
                    appCompatCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            editText.setEnabled(compoundButton.isChecked());
                        }
                    });
                    llAchievementContainer.addView(viewEdit);
                }
            }
        });

    }

    /**
     * 获取TakePhoto实例getTakePhoto
     *
     * @return
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //把上传的subscription取消以防内存泄漏
        if (rxBusProgressSubscription != null && rxBusProgressSubscription.isUnsubscribed()) {
            rxBusProgressSubscription.unsubscribe();
        }
        if (uploadSubscription != null && uploadSubscription.isUnsubscribed()) {
            uploadSubscription.unsubscribe();
        }
    }


    private ProgressDialog progressDialog;
    private List<ImageUploadModel> listImages = new ArrayList<>();

    @Override
    public void takeSuccess(TResult result) {
        Log.i(TAG, "takeSuccess：" + result.getImage().getPath());
        for (TImage tImage : result.getImages()) {
            CRImageModel crImageModel1 = new CRImageModel();
            crImageModel1.setPath(tImage.getPath());
            listImageModel.add(crImageModel1);
        }
        uploadSubscription = Observable.from(listImageModel)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if (progressDialog == null) {
                            progressDialog = new ProgressDialog(CensorshipRegisterActivity.this);
                            progressDialog.setMessage("保存图片中……");
                            progressDialog.setCanceledOnTouchOutside(false);
                            progressDialog.setCancelable(false);
                        }
                        progressDialog.show();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .filter(new Func1<CRImageModel, Boolean>() {
                    @Override
                    public Boolean call(CRImageModel crImageModel) {
                        return crImageModel != null && !TextUtils.isEmpty(crImageModel.getPath()) && !crImageModel.getPath().startsWith(ContentResolver.SCHEME_ANDROID_RESOURCE);
                    }
                })
                .map(new Func1<CRImageModel, String>() {
                    @Override
                    public String call(CRImageModel crImageModel) {
                        return crImageModel.getPath();
                    }
                })
                .flatMap(new Func1<String, Observable<String>>() {
                    @Override
                    public Observable<String> call(String s) {
                        return APIService.getInstance().upload(s, CensorshipRegisterActivity.class.getSimpleName()).map(new Func1<ResponseBody, String>() {
                            @Override
                            public String call(ResponseBody responseBody) {
                                String resultUrl = "";
                                try {
                                    for (CRImageModel crImageModel : listImageModel) {
                                        if (!TextUtils.isEmpty(crImageModel.getPath()) && crImageModel.getPath().equals(s)) {
                                            resultUrl = responseBody.string().replace("\"", "");
                                            crImageModel.setRemotePath(resultUrl);
                                            break;
                                        }
                                    }
                                    return resultUrl;
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    return e.toString();
                                }
                            }
                        });
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DoOnSubscriber<String>() {
                    @Override
                    public void doOnSubscriber() {

                    }

                    @Override
                    public void onCompleted() {
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onNext(String responseBody) {

//                        try {
                        LogUtil.i("上传成功后返回的路径：" + responseBody);
//
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        ToastUtil.showLong(e.getMessage());
                    }
                });
        censorshipRegisterAdapter.notifyDataSetChanged();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        ToastUtil.showShort(msg);
        Log.i(TAG, "takeFail:" + msg);
    }


    @Override
    public void takeCancel() {
        Log.i(TAG, getResources().getString(com.jph.takephoto.R.string.msg_operation_canceled));
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }

    private CropOptions getCropOptions() {
        CropOptions.Builder builder = new CropOptions.Builder();
        if (Config.ASPECT) {
            builder.setAspectX(Config.CROP_WIDTH).setAspectY(Config.CROP_HEIGHT);
        } else {
            builder.setOutputX(Config.CROP_WIDTH).setOutputY(Config.CROP_HEIGHT);
        }
        builder.setWithOwnCrop(Config.CROP_TOOL_DEFAULT);
        return builder.create();
    }

    private String date;
    private String time;
    private String finalltime = date + " " + time;

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        int month = monthOfYear + 1;
        date = year + "-" + month + "-" + dayOfMonth;
        tvTime.setText(date + " " + time);
        Calendar now = Calendar.getInstance();
        TimePickerDialog dpd2 = TimePickerDialog.newInstance(CensorshipRegisterActivity.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                now.get(Calendar.SECOND),
                true);
        dpd2.setVersion(TimePickerDialog.Version.VERSION_2);
        dpd2.show(getSupportFragmentManager(), "TimePickerDialog");
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
        String minuteString = minute < 10 ? "0" + minute : "" + minute;
        String secondString = second < 10 ? "0" + second : "" + second;
        time = hourString + ":" + minuteString + ":" + secondString;
        tvTime.setText(date + " " + time);
    }
}
