package android.zhixun.uiho.com.gissystem.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.zhixun.uiho.com.gissystem.ui.fragment.CheckFragment;
import android.zhixun.uiho.com.gissystem.ui.fragment.DirectoryFragment;
import android.zhixun.uiho.com.gissystem.ui.fragment.DispatchFragment;
import android.zhixun.uiho.com.gissystem.ui.fragment.UnitFragment;

public class MainViewPagerAdapter extends FragmentPagerAdapter {

    private UnitFragment unitFragment;
    private DispatchFragment dispatchFragment;
    private DirectoryFragment directoryFragment;
    private CheckFragment checkFragment;

    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if (unitFragment == null) {
                    unitFragment = new UnitFragment();
                }
                return unitFragment;
            case 1:
                if (dispatchFragment == null) {
                    dispatchFragment = new DispatchFragment();
                }
                return dispatchFragment;
            case 2:
                if (directoryFragment == null) {
                    directoryFragment = new DirectoryFragment();
                }
                return directoryFragment;
            case 3:
                if (checkFragment == null) {
                    checkFragment = new CheckFragment();
                }
                return checkFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
