package android.zhixun.uiho.com.gissystem.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.flux.models.api.CompanyDetailModel;
import android.zhixun.uiho.com.gissystem.greendao_gen.DaoSession;
import android.zhixun.uiho.com.gissystem.interfaces.OnItemClickListener;
import android.zhixun.uiho.com.gissystem.ui.adapter.ImageArrayAdapter;
import android.zhixun.uiho.com.gissystem.ui.adapter.MyViewPagerAdapter;
import android.zhixun.uiho.com.gissystem.ui.widget.MyViewPager;

import com.yibogame.flux.stores.Store;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class UnitDetailActivity extends BaseActivityWithTitle {
    private TextView tvNumber;//持证人数量
    private TextView tvState;//保密检查结果——通过
    private TextView tvEnterpriseCodeContent;//企业代码
    private TextView tvBusinessCategoryContent;//行业类别
    private TextView tvDomicileontent;//注册地
    private TextView tvContactsContacts;//联系人
    private TextView tvTelContacts;//座机电话
    private TextView tvPhoneContent;//联系电话
    private TextView tvCompany;//公司
    private TextView tvPositionContent;//公司地址
    private TextView tvOwnNumber;//几条报件
    private List<String> companyCertificatesLicenseImgList = new ArrayList<>();//法人证书
    private List<String> companyCertificatesQualificationsImgList = new ArrayList<>();//相关资质

    private RecyclerView recyclerView1, recyclerView2, recyclerView3;
    private List<String> companyCertificatesWayImgList = new ArrayList<>();
    private CompanyDetailModel mCompanyModel;
    private RelativeLayout rlHolder;//持证人
    private RelativeLayout rlCheckResult;//检查结果
    private RelativeLayout rlCHResult;//拥有的涉密测绘成果
    //    private String number;
    private DaoSession mDaoSession;
    private MyViewPager myViewPager;//图片滑动的ViewPager
    //    private List<String> mListImage = new ArrayList<>();//图片路径的集合
//    private List<String> mListImageP = new ArrayList<>();//图片路径的集合
//    private List<String> mListImageX = new ArrayList<>();//图片路径的集合
    private AppCompatImageView aivMethodLeft;//管理方法左滑动
    private AppCompatImageView aivMethodRight;//管理方法右滑动
    private AppCompatImageView aivLegalPersonLeft;//法人证书左滑动
    private AppCompatImageView aivLegalPersonRight;//法人证书右滑动
    private AppCompatImageView aivRelateLeft;//相关资质左滑动
    private AppCompatImageView aivRelateRight;//相关资质右滑动
    private ScaleInAnimationAdapter scaleInAnimationAdapter;

    //    private Query<AchievementModel> achievementModelQuery;
    @Override
    protected Store initActionsCreatorAndStore() {
        return null;
    }

    @Override
    protected void onStoreCall(Store.StoreChangeEvent storeChangeEvent) {

    }

    public static void navWith(Context context, CompanyDetailModel model) {
        Intent intent = new Intent(context, UnitDetailActivity.class);
        intent.putExtra("unitModel", model);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_unit_detail;
    }

    @Override
    protected void onCreateActivity(@Nullable Bundle savedInstanceState) {
        setTitleText("单位详情");
        mCompanyModel = (CompanyDetailModel) getIntent().getSerializableExtra("unitModel");
//        MyBaseApplication myBaseApplication = (MyBaseApplication) getApplication();
//        mDaoSession = ((MyBaseApplication) getApplication()).getDaoSession();
//        mCompanyModel = myBaseApplication.getUnitModel();
//        mCompanyModel = myBaseApplication.getCompanyDetailModel();
        //初始化
        initViews();
        //设置监听
        setListener();
//        initMap();
    }

    private void setListener() {
        rlHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UnitDetailActivity.this, HolderSizeMarkActivity.class);
                intent.putParcelableArrayListExtra("holders", mCompanyModel.getHolders());
                intent.putExtra("companyId", mCompanyModel.getCompanyId());
                intent.putExtra("companyName", mCompanyModel.getCompanyName());
                startActivity(intent);
            }
        });
        rlCheckResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                CRModelDao crModelDao = mDaoSession.getCRModelDao();
//                List<CRModel> crModelList = crModelDao.queryBuilder().where(CRModelDao.Properties.UnitId.eq(mCompanyModel.getCompanyId()), CRModelDao.Properties.IsLocal.eq(false)).build().list();
//                if (crModelList.size() == 0) {
//                    ToastUtil.showShort("请先提交保密检查");
//                } else {

                Intent intent = new Intent(UnitDetailActivity.this, UnitCensorShipActivity.class);
                intent.putExtra("company", mCompanyModel.getCompanyId());
                startActivity(intent);
//                }
            }
        });
        rlCHResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UnitDetailActivity.this, OwnSecretResultActivity.class);
                intent.putExtra("company", (long) mCompanyModel.getCompanyId());//这个数据是个long
                startActivity(intent);

            }
        });
    }

    private void initViews() {
        //图片滑动的ViewPager
        rlHolder = (RelativeLayout) findViewById(R.id.rlHolder);//持证人
        rlCheckResult = (RelativeLayout) findViewById(R.id.rlCheckResult);//检查结果
        rlCHResult = (RelativeLayout) findViewById(R.id.rlCHResult);//拥有的涉密测绘成果

        tvNumber = (TextView) findViewById(R.id.tv_number);
//        tvNumber.setText(mCompanyModel.getHolders().size() + "人");
        tvState = (TextView) findViewById(R.id.tv_state);

        tvState.setText(mCompanyModel.getSecrecyIsPass() == 0 ? "不通过" : "通过");
        tvEnterpriseCodeContent = (TextView) findViewById(R.id.tv_enterprise_code_content);
        tvEnterpriseCodeContent.setText(mCompanyModel.getOrganizationCode());//企业代码
        tvBusinessCategoryContent = (TextView) findViewById(R.id.tv_business_category_content);
        tvBusinessCategoryContent.setText(mCompanyModel.getIndustryCategoryName());//行业类别
        tvDomicileontent = (TextView) findViewById(R.id.tv_domicile_content);
        tvDomicileontent.setText(mCompanyModel.getAreaName());//注册地
        tvContactsContacts = (TextView) findViewById(R.id.tv_contacts_contacts);
        tvContactsContacts.setText(mCompanyModel.getCompanyName());//联系人
        tvTelContacts = (TextView) findViewById(R.id.tv_tel_contacts);
        tvTelContacts.setText(mCompanyModel.getCompanyTelephone());//座机电话
        tvPhoneContent = (TextView) findViewById(R.id.tv_phone_content);
        tvPhoneContent.setText(mCompanyModel.getSecrecyPersonPhone());//联系电话
        tvCompany = (TextView) findViewById(R.id.tv_company);
        tvCompany.setText(mCompanyModel.getCompanyName());//重庆宝威有限科技公司
        tvPositionContent = (TextView) findViewById(R.id.tv_position_content);//公司地址
        tvPositionContent.setText(mCompanyModel.getCompanyAddre());
        tvOwnNumber = (TextView) findViewById(R.id.tv_own_number);
        //拥有的测绘成果几条
        tvOwnNumber.setText(String.valueOf(mCompanyModel.getFruitNum()) + "个报件");

        aivMethodLeft = (AppCompatImageView) findViewById(R.id.aiv_method_left);//管理方法左滑动
        aivMethodRight = (AppCompatImageView) findViewById(R.id.aiv_method_right);//管理方法右滑动

        aivLegalPersonLeft = (AppCompatImageView) findViewById(R.id.aiv_legal_person_left);//法人证书左滑动
        aivLegalPersonRight = (AppCompatImageView) findViewById(R.id.aiv_legal_person_right);//法人证书右滑动

        aivRelateLeft = (AppCompatImageView) findViewById(R.id.aiv_relate_left);//相关资质左滑动
        aivRelateRight = (AppCompatImageView) findViewById(R.id.aiv_relate_right);////相关资质右滑动

        recyclerView1 = (RecyclerView) findViewById(R.id.recyclerView1);
        recyclerView2 = (RecyclerView) findViewById(R.id.recyclerView2);
        recyclerView3 = (RecyclerView) findViewById(R.id.recyclerView3);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        for (CompanyCertificatesWayModel companyCertificatesWayModel : mCompanyModel.getCompanyCertificatesWay()) {
//            companyCertificatesWayImgList.add(companyCertificatesWayModel.getCertificatesUrl());
//        }
        ImageArrayAdapter imageArrayAdapter = new ImageArrayAdapter(this, companyCertificatesWayImgList);
        //添加adapter动画
        AlphaInAnimationAdapter alphaInAnimationAdapter = new AlphaInAnimationAdapter(imageArrayAdapter);
        //改变持续时长
        alphaInAnimationAdapter.setDuration(1000);
        //改变插值器
        alphaInAnimationAdapter.setInterpolator(new OvershootInterpolator());
        //禁用只有第一次有动画
        alphaInAnimationAdapter.setFirstOnly(false);
        //多个动画adapter组合
        scaleInAnimationAdapter = new ScaleInAnimationAdapter(alphaInAnimationAdapter);
        scaleInAnimationAdapter.setFirstOnly(false);
        recyclerView1.setLayoutManager(linearLayoutManager);
        recyclerView1.setAdapter(scaleInAnimationAdapter);

        //管理方法左滑动监听
        aivMethodLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int firstItem = linearLayoutManager.findFirstVisibleItemPosition();
//                int lastItem = linearLayoutManager.findLastVisibleItemPosition();

                int n = firstItem - 1 == -1 ? 0 : firstItem - 1;
                linearLayoutManager.scrollToPositionWithOffset(n, 0);
                aivMethodRight.setVisibility(View.VISIBLE);
//                if(n==0){
//                    aivMethodLeft.setVisibility(View.GONE);
//                }else{
//                    aivMethodLeft.setVisibility(View.VISIBLE);
//                }

            }
        });
        //管理方法右滑动监听
        aivMethodRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int firstItem = linearLayoutManager.findFirstVisibleItemPosition();
                int lastItem = linearLayoutManager.findLastVisibleItemPosition();
                //
                int n = lastItem + 1 == 6 ? 5 : firstItem + 1;
                linearLayoutManager.scrollToPositionWithOffset(n, 0);
            }
        });
//        for (CompanyCertificatesLicenseModel companyCertificatesLicenseModel : mCompanyModel.getCompanyCertificatesLicense()) {
//            companyCertificatesLicenseImgList.add(companyCertificatesLicenseModel.getCertificatesUrl());
//        }
        ImageArrayAdapter imageArrayAdapterPerson = new ImageArrayAdapter(this, companyCertificatesLicenseImgList);
        AlphaInAnimationAdapter alphaInAnimationAdapterL = new AlphaInAnimationAdapter(imageArrayAdapterPerson);
        //改变持续时长
        alphaInAnimationAdapterL.setDuration(1000);
        //改变插值器
        alphaInAnimationAdapterL.setInterpolator(new OvershootInterpolator());
        //禁用只有第一次有动画
        alphaInAnimationAdapterL.setFirstOnly(false);
        //多个动画adapter组合
        ScaleInAnimationAdapter scaleInAnimationAdapterL = new ScaleInAnimationAdapter(alphaInAnimationAdapterL);
        scaleInAnimationAdapterL.setFirstOnly(false);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView2.setLayoutManager(linearLayoutManager2);
        recyclerView2.setAdapter(scaleInAnimationAdapterL);


        aivLegalPersonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int firstItem = linearLayoutManager2.findFirstVisibleItemPosition();
                int n = firstItem - 1 == -1 ? 0 : firstItem - 1;
                linearLayoutManager2.scrollToPositionWithOffset(n, 0);
            }
        });

        aivLegalPersonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int firstItem = linearLayoutManager2.findFirstVisibleItemPosition();
                int lastItem = linearLayoutManager2.findLastVisibleItemPosition();
                //
                int n = lastItem + 1 == 6 ? 5 : firstItem + 1;
                linearLayoutManager2.scrollToPositionWithOffset(n, 0);

            }
        });
//        for (CompanyCertificatesQualificationsModel companyCertificatesQualificationsModel : mCompanyModel.getCompanyCertificatesQualifications()) {
//            companyCertificatesQualificationsImgList.add(companyCertificatesQualificationsModel.getCertificatesUrl());
//        }
        ImageArrayAdapter imageArrayAdapterZZ = new ImageArrayAdapter(this, companyCertificatesQualificationsImgList);
        AlphaInAnimationAdapter alphaInAnimationAdapterZ = new AlphaInAnimationAdapter(imageArrayAdapterZZ);
        //改变持续时长
        alphaInAnimationAdapterZ.setDuration(1000);
        //改变插值器
        alphaInAnimationAdapterZ.setInterpolator(new OvershootInterpolator());
        //禁用只有第一次有动画
        alphaInAnimationAdapterZ.setFirstOnly(false);
        //多个动画adapter组合
        ScaleInAnimationAdapter scaleInAnimationAdapterZ = new ScaleInAnimationAdapter(alphaInAnimationAdapterZ);
        scaleInAnimationAdapterZ.setFirstOnly(false);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(this);
        linearLayoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView3.setLayoutManager(linearLayoutManager3);
        recyclerView3.setAdapter(scaleInAnimationAdapterZ);

        aivRelateLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int firstItem = linearLayoutManager3.findFirstVisibleItemPosition();
                int n = firstItem - 1 == -1 ? 0 : firstItem - 1;
                linearLayoutManager3.scrollToPositionWithOffset(n, 0);
            }
        });
        aivRelateRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int firstItem = linearLayoutManager3.findFirstVisibleItemPosition();
                int lastItem = linearLayoutManager3.findLastVisibleItemPosition();
                //
                int n = lastItem + 1 == 6 ? 5 : firstItem + 1;
                linearLayoutManager3.scrollToPositionWithOffset(n, 0);
            }
        });


        //给Adapter设置监听
        imageArrayAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                AlertDialog myDialog = new AlertDialog.Builder(UnitDetailActivity.this).create();
                myDialog.show();
                myDialog.getWindow().setContentView(R.layout.layout_browse_viewpager);
                myViewPager = (MyViewPager) myDialog.getWindow().findViewById(R.id.my_viewpager);
                MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(UnitDetailActivity.this, companyCertificatesWayImgList);
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
        //法人证书图片放大
        imageArrayAdapterPerson.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                AlertDialog myDialog = new AlertDialog.Builder(UnitDetailActivity.this).create();
                myDialog.show();
                myDialog.getWindow().setContentView(R.layout.layout_browse_viewpager);
                myViewPager = (MyViewPager) myDialog.getWindow().findViewById(R.id.my_viewpager);
                MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(UnitDetailActivity.this, companyCertificatesLicenseImgList);
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
        //相关资质图片放大
        imageArrayAdapterZZ.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                AlertDialog myDialog = new AlertDialog.Builder(UnitDetailActivity.this).create();
                myDialog.show();
                myDialog.getWindow().setContentView(R.layout.layout_browse_viewpager);
                myViewPager = (MyViewPager) myDialog.getWindow().findViewById(R.id.my_viewpager);
                MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(UnitDetailActivity.this, companyCertificatesQualificationsImgList);
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

}
