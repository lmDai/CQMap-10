package android.zhixun.uiho.com.gissystem.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.zhixun.uiho.com.gissystem.R;

import com.bumptech.glide.Glide;
import com.yibogame.util.LogUtil;

import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Administrator on 2016/12/15.
 */

public class MyViewPagerAdapter extends PagerAdapter {

    private List<String> imgs;

    private Context mContext;

    public MyViewPagerAdapter(Context context, List<String> imgs) {

        this.mContext = context;
        this.imgs = imgs;
        for (String img : imgs) {
            LogUtil.d("img--------->"+img);
        }
    }

    @Override
    public int getCount() { // 获得size
        return imgs.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        String imgUrl = imgs.get(position);

        Uri uri = Uri.parse(imgUrl);
        LinearLayout view = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.layotu_browse_picture, null);
        PhotoView img = (PhotoView) view.findViewById(R.id.my_photoView);
//        img.setImageURI(uri);
        LogUtil.d("imgUrl---->"+imgUrl);
        Glide.with(mContext)
                .load(imgUrl)
                .into(img);

        PhotoViewAttacher mAttacher = new PhotoViewAttacher(img);
        mAttacher.update();
//        ImageLoaderUtil.getInstance().displayListItemImage(imgs.get(position), img);

        ((ViewPager) container).addView(view);

        return view;

    }  }
