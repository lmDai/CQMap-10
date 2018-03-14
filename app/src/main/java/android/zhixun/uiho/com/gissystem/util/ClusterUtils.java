package android.zhixun.uiho.com.gissystem.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.zhixun.uiho.com.gissystem.ui.widget.BaseMapView;

import com.esri.android.map.event.OnZoomListener;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.LinearUnit;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.map.Graphic;
import com.yibogame.util.LogUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by simple on 27/02/2018.
 */

public class ClusterUtils {

    private double mScale;
    private double mClusterDistance;
    private int mClusterSize;
    private Handler mSignClusterHandler;
    private boolean mIsCanceled;
    private List<Graphic> mGraphics;

    public ClusterUtils(BaseMapView mapView, Graphic[] graphics) {

        int length = graphics.length;
        LogUtil.d("length == " + length);
        mClusterSize = dp2px(mapView.getContext(), 100);
        mScale = mapView.getScale();
        mClusterDistance = mScale * mClusterSize;
        mGraphics = Arrays.asList(graphics);
        mSignClusterHandler = new SignClusterHandler(Looper.getMainLooper());
        assignClusters();

        mapView.setOnZoomListener(new OnZoomListener() {
            @Override
            public void preAction(float v, float v1, double v2) {

            }

            @Override
            public void postAction(float v, float v1, double v2) {

                mScale = mapView.getScale();
                mClusterDistance = mScale * mClusterSize;
                assignClusters();
            }
        });

    }

    /**
     * 对点进行聚合
     */
    private void assignClusters() {
        mIsCanceled = true;
        mSignClusterHandler.removeMessages(SignClusterHandler.CALCULATE_CLUSTER);
        mSignClusterHandler.sendEmptyMessage(SignClusterHandler.CALCULATE_CLUSTER);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private void addClusters() {

    }

    /**
     * 处理聚合点算法线程
     */
    class SignClusterHandler extends Handler {
        static final int CALCULATE_CLUSTER = 0;
        static final int CALCULATE_SINGLE_CLUSTER = 1;

        SignClusterHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                case CALCULATE_CLUSTER:
                    calculateClusters();
                    break;
                case CALCULATE_SINGLE_CLUSTER:

                    break;
            }
        }
    }

    private void calculateClusters() {

        int length = mGraphics.size();
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {

                Graphic g_i = mGraphics.get(i);
                Graphic g_j = mGraphics.get(j);

                double distance = GeometryEngine.geodesicDistance(((Point) g_i.getGeometry())
                        , ((Point) g_j.getGeometry()),
                        SpatialReference.create(SpatialReference.WKID_WGS84),
                        new LinearUnit(LinearUnit.Code.KILOMETER));
                LogUtil.d("distance == " + distance);
                LogUtil.d("mClusterDistance == " + mClusterDistance);
                if (distance < mClusterDistance) {

                }

            }
        }
    }

    public class Cluster {

        private Point mPoint;
        private List<Graphic> mGraphics;

        public Cluster(Point point) {
            this.mPoint = point;
            mGraphics = new ArrayList<>();
        }

        public void addGraphics(Graphic graphic) {
            mGraphics.add(graphic);
        }

        public List<Graphic> getGraphics() {
            return mGraphics;
        }
    }
}
