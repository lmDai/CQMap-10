package android.zhixun.uiho.com.gissystem.util;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.ui.widget.BaseMapView;

import com.esri.android.map.event.OnZoomListener;
import com.esri.core.geometry.Point;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.esri.core.symbol.Symbol;
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
    private List<Graphic> mClusterItems;
    private BaseMapView mMapView;
    private List<Cluster> mClusters = new ArrayList<>();
    private List<Graphic> mAddMarkers = new ArrayList<>();

    public ClusterUtils(BaseMapView mapView, Graphic[] graphics) {

        int length = graphics.length;
        LogUtil.d("length == " + length);
        mClusterSize = 100;
        mMapView = mapView;
        mScale = mapView.getScale();
        mClusterDistance = (long) (mScale / mClusterSize);
        mClusterItems = Arrays.asList(graphics);
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
        mClusters.clear();
        for (Graphic item : mClusterItems) {
            Point point = (Point) item.getGeometry();
            Cluster cluster = getCluster(point);
            if (cluster != null) {
                cluster.addClusterItem(item);
            } else {
                cluster = new Cluster(point);
                mClusters.add(cluster);
                cluster.addClusterItem(item);
            }
        }
        //复制一份数据，规避同步
        List<Cluster> clusters = new ArrayList<Cluster>();
        clusters.addAll(mClusters);
        addClusterToMap(clusters);
    }

    /**
     * 计算两个点是否需要聚合
     *
     * @param point_i
     * @param point_j
     * @return
     */
    private boolean calcPoint(Point point_i, Point point_j) {
        int x = (int) Math.abs(point_i.getX() - point_j.getX());
        int y = (int) Math.abs(point_i.getY() - point_j.getY());
        int z = (int) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        LogUtil.d("z == " + z);
        return z < 100;
    }

    /**
     * 根据一个点获取是否可以依附的聚合点，没有则返回null
     */
    private Cluster getCluster(Point point) {
        for (Cluster cluster : mClusters) {
            if (calcPoint(point, cluster.centerPoint)) {
                return cluster;
            }
        }
        return null;
    }

    private void addClusterToMap(List<Cluster> clusters) {
        //先删除已有marker
        mMapView.clearAll();
        //再添加marker
        for (Cluster cluster : clusters) {
            addSingleClusterToMap(cluster);
        }
    }

    /**
     * 将单个聚合元素添加至地图显示
     *
     * @param cluster
     */
    private void addSingleClusterToMap(Cluster cluster) {
        Point centerPoint = cluster.centerPoint;
        Graphic graphic = getGraphic(cluster.getCount(), centerPoint);
        mMapView.addGraphic(graphic);

        mAddMarkers.add(graphic);
    }

    private Graphic getGraphic(int count, Point centerPoint) {
        Symbol symbol = null;
        if (count > 1) {
            symbol = new TextSymbol(20, String.valueOf(count), Color.RED);
        } else {
            symbol =
                    new PictureMarkerSymbol(mMapView.getContext(),
                            ContextCompat.getDrawable(mMapView.getContext(),
                                    R.drawable.ic_location_green));
        }
        Graphic graphic = new Graphic(centerPoint, symbol);
        return graphic;
    }

    public class Cluster {

        private Point centerPoint;
        private List<Graphic> mGraphics;
        private Graphic marker;

        public Cluster(Point point) {
            this.centerPoint = point;
            mGraphics = new ArrayList<>();
        }

        public void addClusterItem(Graphic graphic) {
//            if (mGraphics.contains(graphic)) return;
            mGraphics.add(graphic);
        }

        public int getCount() {
            return mGraphics.size();
        }

        public List<Graphic> getClusterItems() {
            return mGraphics;
        }

        public Graphic getMarker() {
            return marker;
        }

        public void setMarker(Graphic marker) {
            this.marker = marker;
        }
    }
}
