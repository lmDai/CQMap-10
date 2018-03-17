package android.zhixun.uiho.com.gissystem.util;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;
import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.ui.widget.BaseMapView;

import com.esri.android.map.event.OnZoomListener;
import com.esri.core.geometry.Point;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.esri.core.symbol.Symbol;
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
    private HandlerThread mMarkerHandlerThread = new HandlerThread("addMarker");
    private HandlerThread mSignClusterThread = new HandlerThread("calculateCluster");
    private Handler mMarkerhandler;
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

        initThreadHandler();
        assignClusters();
    }


    private boolean mapContainsPoint(Point point) {
        Rect rect = new Rect();
        mMapView.getWindowVisibleDisplayFrame(rect);
        Point screenPoint = mMapView.toScreenPoint(point);
        return rect.contains((int) screenPoint.getX(), (int) screenPoint.getY());
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
     * 添加一个聚合点
     *
     * @param item
     */
    public void addClusterItem(Graphic item) {
        Message message = Message.obtain();
        message.what = SignClusterHandler.CALCULATE_SINGLE_CLUSTER;
        message.obj = item;
        mSignClusterHandler.sendMessage(message);
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
                    Graphic graphic = (Graphic) message.obj;
                    mClusterItems.add(graphic);
                    calculateSingleCluster(graphic);
                    break;
            }
        }
    }

    private void calculateClusters() {
        mIsCanceled = false;
        mClusters.clear();

        for (Graphic item : mClusterItems) {
            if (mIsCanceled) {
                return;
            }
            Point point = (Point) item.getGeometry();
            if (!mapContainsPoint(point)) {
                continue;
            }
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
        Message message = Message.obtain();
        message.what = MarkerHandler.ADD_CLUSTER_LIST;
        message.obj = clusters;
        if (mIsCanceled) {
            return;
        }
        mMarkerhandler.sendMessage(message);
    }

    /**
     * 计算两个点是否需要聚合
     *
     * @param point_i
     * @param point_j
     * @return
     */
    private boolean calcPoint(Point point_i, Point point_j) {
        Point screenPoint_i = mMapView.toScreenPoint(point_i);
        Point screenPoint_j = mMapView.toScreenPoint(point_j);
        int x = (int) Math.abs(screenPoint_i.getX() - screenPoint_j.getX());
        int y = (int) Math.abs(screenPoint_i.getY() - screenPoint_j.getY());
        int z = (int) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        LogUtil.d("z == " + z);
        return z < 100;
    }

    /**
     * 在已有的聚合基础上，对添加的单个元素进行聚合
     */
    private void calculateSingleCluster(Graphic clusterItem) {
        Point point = (Point) clusterItem.getGeometry();

        if (!mapContainsPoint(point)) {
            return;
        }
        Cluster cluster = getCluster(point);
        if (cluster != null) {
            cluster.addClusterItem(clusterItem);
            Message message = Message.obtain();
            message.what = MarkerHandler.UPDATE_SINGLE_CLUSTER;

            message.obj = cluster;
            mMarkerhandler.removeMessages(MarkerHandler.UPDATE_SINGLE_CLUSTER);
            mMarkerhandler.sendMessageDelayed(message, 5);


        } else {

            cluster = new Cluster(point);
            mClusters.add(cluster);
            cluster.addClusterItem(clusterItem);
            Message message = Message.obtain();
            message.what = MarkerHandler.ADD_SINGLE_CLUSTER;
            message.obj = cluster;
            mMarkerhandler.sendMessage(message);

        }
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

    //初始化Handler
    private void initThreadHandler() {
        mMarkerHandlerThread.start();
        mSignClusterThread.start();
        mMarkerhandler = new MarkerHandler(mMarkerHandlerThread.getLooper());
        mSignClusterHandler = new SignClusterHandler(mSignClusterHandler.getLooper());
    }

    /**
     * 处理market添加，更新等操作
     */
    class MarkerHandler extends Handler {

        static final int ADD_CLUSTER_LIST = 0;

        static final int ADD_SINGLE_CLUSTER = 1;

        static final int UPDATE_SINGLE_CLUSTER = 2;

        MarkerHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {

            switch (message.what) {
                case ADD_CLUSTER_LIST:
                    List<Cluster> clusters = (List<Cluster>) message.obj;
                    addClusterToMap(clusters);
                    break;
                case ADD_SINGLE_CLUSTER:
                    Cluster cluster = (Cluster) message.obj;
                    addSingleClusterToMap(cluster);
                    break;
                case UPDATE_SINGLE_CLUSTER:
                    Cluster updateCluster = (Cluster) message.obj;
                    updateCluster(updateCluster);
                    break;
            }
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

    /**
     * 更新已加入地图聚合点的样式
     */
    private void updateCluster(Cluster cluster) {

        Graphic marker = cluster.getMarker();
//        marker.setIcon(getBitmapDes(cluster.getClusterCount()));

    }

    private Graphic getGraphic(int count, Point centerPoint) {
        Symbol symbol = null;
        if (count > 1) {
            View markerView = View.inflate(
                    mMapView.getContext(), R.layout.marker_cluster, null);
            TextView tvCount = markerView.findViewById(R.id.tv_count);
            markerView.measure(0, 0);
            markerView.layout(0, 0, markerView.getMeasuredWidth(),
                    markerView.getMeasuredHeight());
            tvCount.setText(String.valueOf(count));
            markerView.setDrawingCacheEnabled(true);
            markerView.buildDrawingCache();
            Bitmap bitmap = markerView.getDrawingCache();
            BitmapDrawable drawable = new BitmapDrawable(mMapView.getContext().getResources(),
                    bitmap);
            symbol = new PictureMarkerSymbol(mMapView.getContext(),drawable);
        } else {
            symbol =
                    new PictureMarkerSymbol(mMapView.getContext(),
                            ContextCompat.getDrawable(mMapView.getContext(),
                                    R.drawable.ic_location_green));
        }
        return new Graphic(centerPoint, symbol);
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
