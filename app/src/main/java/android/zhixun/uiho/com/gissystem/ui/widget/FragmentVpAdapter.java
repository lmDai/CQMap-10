package android.zhixun.uiho.com.gissystem.ui.widget;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FragmentVpAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragments = new ArrayList<>();

    public FragmentVpAdapter(FragmentManager fm, Fragment... fragments) {
        super(fm);
        mFragments.addAll(Arrays.asList(fragments));
    }

    public FragmentVpAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mFragments.addAll(fragments);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
