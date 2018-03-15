package android.zhixun.uiho.com.gissystem.util;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.zhixun.uiho.com.gissystem.ui.widget.BaseMapView;

import com.esri.android.map.event.OnZoomListener;
import com.esri.core.geometry.Point;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.TextSymbol;
import com.yibogame.util.LogUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by simple on 27/02/2018.
 */

public class ClusterUtils {

    private double mScale;
    private long mClusterDistance;
    private int mClusterSize;
    private Handler mSignClusterHandler;
    private boolean mIsCanceled;
    private List<Graphic> mGraphics;
    private BaseMapView mMapView;
    private List<Cluster> mClusterList = new ArrayList<>();

    public ClusterUtils(BaseMapView mapView, Graphic[] graphics) {

        int length = graphics.length;
//        LogUtil.d("length == " + length);
        mClusterSize = 100;
        mMapView = mapView;
        mScale = mapView.getScale();
        mClusterDistance = (long) (mScale / mClusterSize);
        mGraphics = Arrays.asList(graphics);
        mSignClusterHandler = new SignClusterHandler(Looper.getMainLooper());
        assignClusters();

        mapView.setOnZoomListener(new OnZoomListener() {
            @Override
            public void preAction(float v, float v1, double v2) {

            }

            @Override
            public void postAction(float pivotX, float pivotY, double factor) {
//                LogUtil.d("factor == " + factor);
                mScale = mapView.getScale();
//                LogUtil.d("mScale == " + mScale);
                mClusterDistance = (long) (mScale / mClusterSize);
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
        mClusterList.clear();
        int length = mGraphics.size();
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {

                Graphic g_i = mGraphics.get(i);
                Graphic g_j = mGraphics.get(j);

                Point p_i = mMapView.toScreenPoint(((Point) g_i.getGeometry()));
                Point p_j = mMapView.toScreenPoint(((Point) g_j.getGeometry()));
                Cluster cluster = new Cluster(((Point) g_i.getGeometry()));
                if (calcPoint(p_i, p_j)) {//加入聚合
//                    Cluster cluster = getCluster(p_i);
//                    if (cluster == null) {
//                        cluster = new Cluster(((Point) g_i.getGeometry()));
//                    }
                    cluster.addGraphics(g_i);
                    cluster.addGraphics(g_j);
                } else {
                    cluster.addGraphics(g_j);
                }
                mClusterList.add(cluster);
            }
        }
        addGraphicToMap();
    }

    private boolean calcPoint(Point point_i, Point point_j) {
        int x = (int) Math.abs(point_i.getX() - point_j.getX());
        int y = (int) Math.abs(point_i.getY() - point_j.getY());
        int z = (int) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        LogUtil.d("z == " + z);
        return z < 100;
    }

    private Cluster getCluster(Point point) {
        for (Cluster cluster : mClusterList) {
            if (cluster.mPoint == point) {
                return cluster;
            }
        }
        return null;
    }

    private void addGraphicToMap() {
        for (Cluster cluster : mClusterList) {
            int size = cluster.mGraphics.size();
            if (size > 1) {
                TextSymbol symbol = new TextSymbol(20, cluster.getGraphics().size() + "", Color.RED);
                Graphic graphic = new Graphic(cluster.mPoint, symbol);
                mMapView.addGraphic(graphic);
            } else if (size == 1) {
                mMapView.addGraphic(cluster.getGraphics().get(0));
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
            if (mGraphics.contains(graphic)) return;
            mGraphics.add(graphic);
        }

        public List<Graphic> getGraphics() {
            return mGraphics;
        }
    }
}
