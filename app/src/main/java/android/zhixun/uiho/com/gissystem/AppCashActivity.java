package android.zhixun.uiho.com.gissystem;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.yibogame.ui.activity.BaseActivity;

/**
 * Created by simpepeng on 2017/8/2.
 *
 * app bug 展示页面
 */

public class AppCashActivity extends AppCompatActivity {

    public static final String EXCEPTION_MSG = "exception_msg";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_cash);
        String bug = getIntent().getStringExtra(EXCEPTION_MSG);

        TextView tvBug = (TextView) findViewById(R.id.tv_bug);
        tvBug.setText(bug);
    }
}
