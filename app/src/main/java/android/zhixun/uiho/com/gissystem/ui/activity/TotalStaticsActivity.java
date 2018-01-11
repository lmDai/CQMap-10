package android.zhixun.uiho.com.gissystem.ui.activity;

import android.animation.ObjectAnimator;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.flux.models.TotalStaticsModel;
import android.zhixun.uiho.com.gissystem.ui.adapter.TotalStaticsAdapter;
import android.zhixun.uiho.com.gissystem.ui.widget.DialogUtil;
import android.zhixun.uiho.com.gissystem.ui.widget.DividerGridItemDecoration;
import android.zhixun.uiho.com.gissystem.ui.widget.DividerItemDecoration;

import com.yibogame.flux.stores.Store;
import com.yibogame.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.breadCrumbShortTitle;
import static android.R.attr.repeatCount;
import static android.R.attr.rotation;

public class TotalStaticsActivity extends BaseActivityWithTitle implements View.OnClickListener {
    private RecyclerView recyclerView;
    private List<TotalStaticsModel> lists = new ArrayList<>();
    private RelativeLayout relativeLayout1, relativeLayout2, relativeLayout3, relativeLayout4;
    private boolean isFirst = true;//动画的正三角和倒三角
    private ImageView image2;
    private LinearLayout llSift;

    @Override
    protected Store initActionsCreatorAndStore() {
        return null;
    }

    @Override
    protected void onStoreCall(Store.StoreChangeEvent storeChangeEvent) {

    }


    @Override
    protected int getLayoutRes() {
        return R.layout.activity_total_statics;
    }

    @Override
    protected void onCreateActivity(@Nullable Bundle savedInstanceState) {
        //设置Toolbar标题
        setTitleText("总体统计");
        addMenu(getResources().getDrawable(R.mipmap.chart), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showShort("点击了菜单！");
            }
        });
        //初始化
        initView();
        //设置监听
        setListener();

    }

    private void setListener() {
    }


    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        relativeLayout1 = (RelativeLayout) findViewById(R.id.relativeLayout1);
        relativeLayout2 = (RelativeLayout) findViewById(R.id.relativeLayout2);
        relativeLayout3 = (RelativeLayout) findViewById(R.id.relativeLayout3);
        relativeLayout4 = (RelativeLayout) findViewById(R.id.relativeLayout4);
        relativeLayout1.setOnClickListener(this);
        relativeLayout2.setOnClickListener(this);
        relativeLayout3.setOnClickListener(this);
        relativeLayout4.setOnClickListener(this);
        image2 = (ImageView) findViewById(R.id.image2);
        for (int i = 0; i <= 35; i++) {
            //String type, String content
            TotalStaticsModel totalStaticsModel = new TotalStaticsModel("0", "1125");
            lists.add(totalStaticsModel);
        }
        TotalStaticsAdapter totalStaticsAdapter = new TotalStaticsAdapter(this, lists);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 4);
        //添加分割线
        DividerGridItemDecoration dividerGridItemDecoration = new DividerGridItemDecoration(this);
        dividerGridItemDecoration.setShowFirstLine(true);
        recyclerView.addItemDecoration(dividerGridItemDecoration);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(totalStaticsAdapter);
        llSift = (LinearLayout) findViewById(R.id.ll_sift);
        llSift.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.relativeLayout1:
            case R.id.relativeLayout2:
            case R.id.relativeLayout3:
            case R.id.relativeLayout4:
                if (isFirst) {
                    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(image2, "rotation", 0F, 180F);//180度旋转
                    AccelerateDecelerateInterpolator accelerateDecelerateInterpolator = new AccelerateDecelerateInterpolator();
                    objectAnimator.setDuration(1000);
                    objectAnimator.setInterpolator(accelerateDecelerateInterpolator);
                    objectAnimator.start();
                    isFirst = false;
                } else if (!isFirst) {
                    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(image2, "rotation", 180F, 360F);//180度旋转

                    objectAnimator.setDuration(1000);

                    objectAnimator.start();
                    isFirst = true;
                }
                break;
            case R.id.ll_sift:
                View contentView = LayoutInflater.from(this).inflate(R.layout.layout_sift_statics, (ViewGroup) getWindow().getDecorView().getRootView(), false);
                DialogUtil.getInstance().showAnchorDialog(contentView, view);
                break;
        }
    }
}
