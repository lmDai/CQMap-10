package android.zhixun.uiho.com.gissystem.ui.widget;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.util.Log;
import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.drawtool.DrawEvent;
import android.zhixun.uiho.com.gissystem.drawtool.DrawEventListener;
import android.zhixun.uiho.com.gissystem.drawtool.DrawTool;
import android.zhixun.uiho.com.gissystem.tdt.TianDiTuLayer;
import android.zhixun.uiho.com.gissystem.tdt.TianDiTuLayerTypes;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.LocationDisplayManager;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
import com.esri.android.map.event.OnStatusChangedListener;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.Polygon;
import com.esri.core.map.CallbackListener;
import com.esri.core.map.FeatureResult;
import com.esri.core.map.Graphic;
import com.esri.core.tasks.query.QueryParameters;
import com.esri.core.tasks.query.QueryTask;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;


public class BaseMapView extends MapView implements DrawEventListener {

    private ArcGISTiledMapServiceLayer vecLayer, demLayer, imgLayer;
    private TianDiTuLayer vecTTDLayer, demTTDLayer, imgTTDLayer;
    private TianDiTuLayer vecTextTTDLayer, demTextTTDLayer, imageTextTTDLayer;
    //
    private GraphicsLayer drawLayer;
    private DrawTool drawTool;
    //
//    GraphicsLayer graphicsLayer;
    //
//    private List<Integer> graphicsIds = new ArrayList<>();

    private Graphic currentDrawGraphic;

    public static final double mScale = 100000;

    @CurrentMapLayer
    private int currentMapLayer;
    @CurrentSpace
    private int currentDrawSpace = SPACE_NONE;
    String path;

    public BaseMapView(Context context) {
        super(context);
        init(context);
    }

    public BaseMapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        path = context.getCacheDir().getAbsolutePath();
        //天地图的layer
        vecTTDLayer = new TianDiTuLayer(TianDiTuLayerTypes.TIANDITU_VECTOR_2000, path);
        this.addLayer(vecTTDLayer);

        //天地图文字的layer
        vecTextTTDLayer = new TianDiTuLayer(TianDiTuLayerTypes.TIANDITU_VECTOR_ANNOTATION_CHINESE_2000, path);
        this.addLayer(vecTextTTDLayer);

        //重庆地图的layer
        vecLayer = new ArcGISTiledMapServiceLayer(context.getString(R.string.tdt_vec_base_map_url));
        this.addLayer(vecLayer);
        currentMapLayer = VEC_LAYER;

        //画图形的layer
        drawLayer = new GraphicsLayer();
        this.addLayer(drawLayer);
        drawTool = new DrawTool(this);
        drawTool.addEventListener(this);
        this.setOnStatusChangedListener(onStatusChangedListener);
        //添加标注的layer
//        graphicsLayer = new GraphicsLayer();
//        this.addLayer(graphicsLayer);
    }

    public static final int DEM_LAYER = 0;
    public static final int VEC_LAYER = 1;
    public static final int IMG_LAYER = 2;

    @Override
    public void handleDrawEvent(DrawEvent event) {
        currentDrawGraphic = event.getDrawGraphic();
        drawLayer.addGraphic(currentDrawGraphic);
    }

    public Graphic getCurrentDrawGraphic() {
        return currentDrawGraphic;
    }

    public void setBasicScale() {
        this.setScale(100000, true);
    }


    @IntDef({DEM_LAYER, VEC_LAYER, IMG_LAYER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface CurrentMapLayer {
    }

    public int getCurrentMapLayer() {
        return currentMapLayer;
    }

    public void setCurrentMapLayer(@CurrentMapLayer int currentMapLayer) {
        this.currentMapLayer = currentMapLayer;

        removeAll();
        switch (currentMapLayer) {
            case DEM_LAYER://地形
                demTTDLayer = new TianDiTuLayer(TianDiTuLayerTypes.TIANDITU_TERRAIN_2000, path);
                this.addLayer(demTTDLayer);

                demTextTTDLayer =
                        new TianDiTuLayer(TianDiTuLayerTypes.TIANDITU_TERRAIN_ANNOTATION_CHINESE_2000, path);
                this.addLayer(demTextTTDLayer);

                demLayer = new ArcGISTiledMapServiceLayer(getContext().getString(R.string.tdt_dem_base_map_url));
                this.addLayer(demLayer);
                break;
            case VEC_LAYER://矢量
                this.addLayer(vecTTDLayer);
                this.addLayer(vecTextTTDLayer);
                this.addLayer(vecLayer);
                break;
            case IMG_LAYER://图形
                imgTTDLayer = new TianDiTuLayer(TianDiTuLayerTypes.TIANDITU_IMAGE_2000, path);
                this.addLayer(imgTTDLayer);

                imageTextTTDLayer =
                        new TianDiTuLayer(TianDiTuLayerTypes.TIANDITU_IMAGE_ANNOTATION_CHINESE_2000, path);
                this.addLayer(imageTextTTDLayer);

                imgLayer =
                        new ArcGISTiledMapServiceLayer(getContext().getString(R.string.tdt_img_base_map_url));
                this.addLayer(imgLayer);
                break;
        }
        //画图形的layer
        this.addLayer(drawLayer);
        drawTool = new DrawTool(this);
        drawTool.addEventListener(this);
//        this.addLayer(graphicsLayer);
    }

    public void location() {
        LocationDisplayManager locationDisplayManager = this.getLocationDisplayManager();
        locationDisplayManager.start();
        locationDisplayManager.setAutoPanMode(LocationDisplayManager.AutoPanMode.LOCATION);
//        locationDisplayManager.setLocationListener();
    }

    public static final int SPACE_NONE = 0;
    public static final int SPACE_RECT = 1;
    public static final int SPACE_POLYGON = 2;
    public static final int SPACE_BUFFER = 4;
    public static final int SPACE_BUFFER_SET_FINISH = 5;
    public static final int SPACE_MAP_NUMBER = 6;// 图符号
    public static final int SPACE_ADMIN_REGION = 7;// 行政区域

    @IntDef({SPACE_NONE, SPACE_RECT, SPACE_POLYGON,
            SPACE_BUFFER, SPACE_BUFFER_SET_FINISH, SPACE_MAP_NUMBER,
            SPACE_ADMIN_REGION})
    @Retention(RetentionPolicy.SOURCE)
    public @interface CurrentSpace {
    }

    public int getCurrentDrawSpace() {
        return currentDrawSpace;
    }

    public void setCurrentDrawSpace(@CurrentSpace int currentDrawSpace) {
        this.currentDrawSpace = currentDrawSpace;
        if (currentDrawSpace == SPACE_NONE) {
            drawTool.deactivate();
//            drawLayer.removeAll();
//            currentDrawGraphic = null;
        }
    }

    public void clearAll() {
        drawLayer.removeAll();
        currentDrawGraphic = null;
    }

    public void setCurrentDrawGraphic(Graphic graphic) {
        this.currentDrawGraphic = graphic;
        drawLayer.addGraphic(graphic);
    }

    public void addGraphic(Graphic graphic) {
        drawLayer.addGraphic(graphic);
    }

    public void addGraphics(Graphic[] graphic) {
        drawLayer.addGraphics(graphic);
    }

    public DrawTool getDrawTool() {
        return drawTool;
    }

    public GraphicsLayer getDrawLayer() {
        return drawLayer;
    }


    public void queryGeometry(Activity act, String url,
                              MainThreadCallback<FeatureResult> callback) {
        QueryParameters query = new QueryParameters();
        query.setGeometry(currentDrawGraphic.getGeometry());
        query.setOutSpatialReference(this.getSpatialReference());
        query.setOutFields(new String[]{"*"});
        query.setReturnGeometry(true);
        QueryTask queryTask = new QueryTask(url);
        queryTask.execute(query, new CallbackListener<FeatureResult>() {
            @Override
            public void onCallback(FeatureResult objects) {
                act.runOnUiThread(() -> callback.onCallback(objects));
            }

            @Override
            public void onError(Throwable throwable) {
                act.runOnUiThread(() -> callback.onError(throwable));
            }
        });
    }

    public void querySQL(Activity act,
                         String url, String where,
                         MainThreadCallback<FeatureResult> callback) {
        QueryParameters query = new QueryParameters();
        query.setOutSpatialReference(this.getSpatialReference());
        query.setOutFields(new String[]{"*"});
        query.setReturnGeometry(true);
        query.setWhere(where);
        query.setReturnIdsOnly(false);
        query.setReturnZ(false);
        query.setReturnM(false);
        QueryTask qTask = new QueryTask(url);
        qTask.execute(query, new CallbackListener<FeatureResult>() {
            @Override
            public void onCallback(FeatureResult objects) {
                act.runOnUiThread(() -> {
                    callback.onCallback(objects);
                });
            }

            @Override
            public void onError(Throwable throwable) {
                act.runOnUiThread(() -> {
                    callback.onError(throwable);
                });
            }
        });

    }

    public void queryGeometryAndSql(Activity act, String url, String where,
                                    MainThreadCallback<FeatureResult> callback) {
        QueryParameters query = new QueryParameters();
        query.setGeometry(currentDrawGraphic.getGeometry());
        query.setWhere(where);
        query.setOutSpatialReference(this.getSpatialReference());
        query.setOutFields(new String[]{"*"});
        query.setReturnGeometry(true);
        QueryTask queryTask = new QueryTask(url);
        queryTask.execute(query, new CallbackListener<FeatureResult>() {
            @Override
            public void onCallback(FeatureResult objects) {
                act.runOnUiThread(() -> callback.onCallback(objects));
            }

            @Override
            public void onError(Throwable throwable) {
                act.runOnUiThread(() -> callback.onError(throwable));
            }
        });
    }

    public interface MainThreadCallback<T> {
        void onCallback(T result);

        void onError(Throwable e);
    }

    private void requestLocationPermission() {
        AndPermission.with(getContext())
                .permission(Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                .callback(listener)
                .start();

    }

    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            setScale(mScale, true);
            location();
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {

        }
    };

    public interface OnLayerLoaderListener {
        void onLayerLoaded();
    }

    public OnLayerLoaderListener mOnLayerLoaderListener;

    public void setOnLayerLoaderListener(OnLayerLoaderListener layerLoaderListener) {
        this.mOnLayerLoaderListener = layerLoaderListener;
    }

    OnStatusChangedListener onStatusChangedListener = (OnStatusChangedListener) (o, status) -> {
        switch (status) {
            case INITIALIZED:
                requestLocationPermission();
                Log.d("simple", "INITIALIZED");
                break;
            case LAYER_LOADED:
                Log.d("simple", "LAYER_LOADED");
                if (mOnLayerLoaderListener != null) {
                    mOnLayerLoaderListener.onLayerLoaded();
                }
                break;
        }
    };

    public void centerAtGraphic(Graphic graphic) {
        if (graphic.getGeometry() == null)
            return;
        switch (graphic.getGeometry().getType()) {
            case ENVELOPE:
                Envelope envelope = (Envelope) graphic.getGeometry();
                this.centerAt(envelope.getCenter(), true);
                break;
            case POLYGON:
                Polygon polygon = (Polygon) graphic.getGeometry();
                Point point = polygon.getPoint(1);
                this.centerAt(point, true);
                break;
            case POINT:
                Point point1 = (Point) graphic.getGeometry();
                this.centerAt(point1, true);
                break;
        }
    }
}
