package android.zhixun.uiho.com.gissystem.ui.activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.app.Config;
import android.zhixun.uiho.com.gissystem.app.MyBaseApplication;
import android.zhixun.uiho.com.gissystem.flux.models.CQPrefectureModel;
import android.zhixun.uiho.com.gissystem.flux.models.IndustryCategoryModel;
import android.zhixun.uiho.com.gissystem.greendao_gen.CQPrefectureModelDao;
import android.zhixun.uiho.com.gissystem.greendao_gen.DaoSession;
import android.zhixun.uiho.com.gissystem.greendao_gen.IndustryCategoryModelDao;
import android.zhixun.uiho.com.gissystem.rest.APIService;

import com.yibogame.app.DoOnSubscriber;
import com.yibogame.flux.stores.Store;
import com.yibogame.ui.activity.BaseActivity;
import com.yibogame.util.LogUtil;
import com.yibogame.util.SPUtil;
import com.yibogame.util.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

public class LoginActivity extends BaseActivity {

    private EditText userName, userPassword;
    private AppCompatButton btLogin;
    private ProgressDialog mProgressDialog;
    private DaoSession mDaoSession;
    private int mCQPrefectureStartCode = 0;
    private int mIndustryCategoryCode = 0;
    private ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化daoSession
        mDaoSession = ((MyBaseApplication) getApplication()).getDaoSession();
        setContentView(R.layout.activity_login);
        //初始化view
        initViews();
        //设置监听
        setListener();

        btLogin.performClick();
    }


    /***
     * 添加注册地
     */
    private void addCQPrefectureToDB() {
        //
        if (mDaoSession.loadAll(CQPrefectureModel.class).isEmpty()) {
            CQPrefectureModelDao cqPrefectureModelDao = mDaoSession.getCQPrefectureModelDao();
            Observable.from(Config.CQ_PREFECTURE).subscribe(new Subscriber<String>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    LogUtil.e("addCQPrefectureToDB()====>" + e.getMessage());
                }

                @Override
                public void onNext(String s) {
                    CQPrefectureModel cqPrefectureModel = new CQPrefectureModel();
                    LogUtil.d("--->" + mCQPrefectureStartCode);
                    cqPrefectureModel.setCode("" + (mCQPrefectureStartCode < 10 ? "0" + mCQPrefectureStartCode : mCQPrefectureStartCode));
                    mCQPrefectureStartCode++;
                    cqPrefectureModel.setName(s);
                    cqPrefectureModelDao.insert(cqPrefectureModel);
                }
            });
        } else {
            LogUtil.d("已存在[注册地表]，未添加...");
        }
    }


//    /***
//     * 添加公司信息
//     */
//
//    private void addUnitInformation(){
//        if(mDaoSession.loadAll(CompanyInforModel.class).isEmpty()) {
//            CompanyInforModelDao companyInforModelDao = mDaoSession.getCompanyInforModelDao();
//            int id = 0;
//            for (String unitNameAndNumber : Config.UNIT_NAME_AND_NUMBERS) {
//                CompanyInforModel companyInforModel  = new CompanyInforModel();
//                String[] info = unitNameAndNumber.split("_");
//                companyInforModel.setUnitNmae(info[0]);
//                companyInforModel.setBjCode(info[1]);
//                companyInforModel.setAddress(info[2]);
//                companyInforModel.setFFDate(info[3]+"_"+info[4]);
//                companyInforModel.setArgsID(id+"");
//                id++;
//                companyInforModelDao.insert(companyInforModel);
//            }
//        }
//    }

    /***
     * 添加行业数据
     */
    private void addIndustryCategory() {
        if (mDaoSession.loadAll(IndustryCategoryModel.class).isEmpty()) {
            IndustryCategoryModelDao industryCategoryModelDao = mDaoSession.getIndustryCategoryModelDao();
            Observable.from(Config.INDUSTRY_CATEGORY).subscribe(new Subscriber<String>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    LogUtil.e("addIndustryCategory()====>" + e.getMessage());
                }

                @Override
                public void onNext(String s) {
                    IndustryCategoryModel industryCategoryModel = new IndustryCategoryModel();
                    LogUtil.d("--->" + mIndustryCategoryCode);
                    industryCategoryModel.setCode("" + (mIndustryCategoryCode < 10 ? "0" + mIndustryCategoryCode : mIndustryCategoryCode));
                    mIndustryCategoryCode++;
                    industryCategoryModel.setName(s);
                    industryCategoryModelDao.insert(industryCategoryModel);
                }
            });
        } else {
            LogUtil.d("已存在[行业表]，未添加...");
        }
//        IndustryCategoryModelDao industryCategoryModelDao = mDaoSession.getIndustryCategoryModelDao();
//        for (IndustryCategoryModel industryCategoryModel : industryCategoryModelDao.queryBuilder().build().list()) {
//            LogUtil.d("industryCategoryModel.getName="+industryCategoryModel.getName());
//        }

    }

    private void addCensorshipRegister() {
//        if (mDaoSession.loadAll(CRModel.class).isEmpty()){
//            DAOCensorshipRegisterModelDao daoCensorshipRegisterModelDao  = mDaoSession.getDAOCensorshipRegisterModelDao();
//
//
//        }
    }

    @Override
    protected Store initActionsCreatorAndStore() {
        return null;
    }

    @Override
    protected void onStoreCall(Store.StoreChangeEvent storeChangeEvent) {

    }

    private void setListener() {
        btLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(userName.getText().toString())) {
                    ToastUtil.showShort("请输入用户名");
                    return;
                }
                if (TextUtils.isEmpty(userPassword.getText().toString())) {
                    ToastUtil.showShort("请输入密码");
                    return;
                }
                Map<Object, Object> map = new HashMap<>();
                map.put("userName", userName.getText().toString());
                map.put("pwd", userPassword.getText().toString());
                Subscription subscriptionLogin = APIService.getInstance().login(map, new DoOnSubscriber<android.zhixun.uiho.com.gissystem.flux.models.api.UserModel>() {


                    @Override
                    public void doOnSubscriber() {
                        progressDialog = new ProgressDialog(LoginActivity.this);
                        progressDialog.setMessage("登录中");
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                    }

                    @Override
                    public void onCompleted() {
                        LogUtil.i("onCompleted!");
                        if (progressDialog != null && progressDialog.isShowing()) {
                            try {
                                progressDialog.dismiss();
                            } catch (Exception e) {
                                e.printStackTrace();
                                LogUtil.e("这里奇怪的报了个错误，Caused by: java.lang.IllegalArgumentException: View=DecorView@4dc065e[] not attached to window manager");
                            }

                        }
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onNext(android.zhixun.uiho.com.gissystem.flux.models.api.UserModel userModel) {
                        SPUtil.getInstance().put("UserModelOfJson", userModel.toJsonString());
                        LogUtil.d("userModel.toJsonString()=" + userModel.toJsonString());
                        MyBaseApplication.getInstance().setUserModel(userModel);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        ToastUtil.showShort(e.getMessage());
                    }

                });

//                String uName = userName.getText().toString();
//                String uPassword = userPassword.getText().toString();
//
//                UserModelDao userModelDao = mDaoSession.getUserModelDao();
//                Query<UserModel> userModelQuery = userModelDao.queryBuilder().where(UserModelDao.Properties.Username.eq(uName), UserModelDao.Properties.Password.eq(uPassword)).build();
//                UserModel userModel = userModelQuery.unique();
//                if (userModel != null) {
//                    //将当前用户保存起来
//                    CRActiveUserModelDao crActiveUserModelDao = mDaoSession.getCRActiveUserModelDao();
//                    crActiveUserModelDao.deleteAll();
//                    CRActiveUserModel crActiveUser = new CRActiveUserModel();
////                    crActiveUser.setNickname(userModel.getNickname());
//                    crActiveUser.setNickname("钱进");
////                    crActiveUser.setUserUnitId(userModel.getMyUnitId());
//                    crActiveUser.setUserId(userModel.getId());
//                    crActiveUserModelDao.insert(crActiveUser);
//                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                } else {
//                    ToastUtil.showShort("用户名或密码不正确");
//                }
//                if ((uName.equals("123456")) && (uPassword.equals("123456"))) {
//
//
//                } else {
//                    ToastUtil.showShort("用户名或密码不正确");
            }
        });
    }

    private void initViews() {
        userName = (EditText) findViewById(R.id.user_name);
        userPassword = (EditText) findViewById(R.id.user_password);
        btLogin = (AppCompatButton) findViewById(R.id.btLogin);

    }


}
