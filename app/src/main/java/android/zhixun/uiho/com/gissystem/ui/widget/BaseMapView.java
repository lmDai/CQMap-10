package android.zhixun.uiho.com.gissystem.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
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
import com.esri.core.map.CallbackListener;
import com.esri.core.map.FeatureResult;
import com.esri.core.map.Graphic;
import com.esri.core.tasks.query.QueryParameters;
import com.esri.core.tasks.query.QueryTask;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


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

    @CurrentMapLayer
    private int currentMapLayer;
    @CurrentSpace
    private int currentDrawSpace = SPACE_NONE;

    public BaseMapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //天地图的layer
        vecTTDLayer = new TianDiTuLayer(TianDiTuLayerTypes.TIANDITU_VECTOR_2000);
        this.addLayer(vecTTDLayer);
        demTTDLayer = new TianDiTuLayer(TianDiTuLayerTypes.TIANDITU_TERRAIN_2000);
        imgTTDLayer = new TianDiTuLayer(TianDiTuLayerTypes.TIANDITU_IMAGE_2000);
        //天地图文字的layer
        vecTextTTDLayer = new TianDiTuLayer(TianDiTuLayerTypes.TIANDITU_VECTOR_ANNOTATION_CHINESE_2000);
        this.addLayer(vecTextTTDLayer);
        demTextTTDLayer =
                new TianDiTuLayer(TianDiTuLayerTypes.TIANDITU_TERRAIN_ANNOTATION_CHINESE_2000);
        imageTextTTDLayer =
                new TianDiTuLayer(TianDiTuLayerTypes.TIANDITU_IMAGE_ANNOTATION_CHINESE_2000);
        //重庆地图的layer
        vecLayer = new ArcGISTiledMapServiceLayer(context.getString(R.string.tdt_vec_base_map_url));
        this.addLayer(vecLayer);
        currentMapLayer = VEC_LAYER;

        demLayer = new ArcGISTiledMapServiceLayer(context.getString(R.string.tdt_dem_base_map_url));

        imgLayer =
                new ArcGISTiledMapServiceLayer(context.getString(R.string.tdt_img_base_map_url));

        this.setOnStatusChangedListener(onStatusChangedListener);
        //画图形的layer
        drawLayer = new GraphicsLayer();
        this.addLayer(drawLayer);
        drawTool = new DrawTool(this);
        drawTool.addEventListener(this);
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
            case DEM_LAYER:
                this.addLayer(demTTDLayer);
                this.addLayer(demTextTTDLayer);
                this.addLayer(demLayer);
                break;
            case VEC_LAYER:
                this.addLayer(vecTTDLayer);
                this.addLayer(vecTextTTDLayer);
                this.addLayer(vecLayer);
                break;
            case IMG_LAYER:
                this.addLayer(imgTTDLayer);
                this.addLayer(imageTextTTDLayer);
                this.addLayer(imgLayer);
                break;
        }
        //画图形的layer
        this.addLayer(drawLayer);
        drawTool = new DrawTool(this);
        drawTool.addEventListener(this);
        this.setOnStatusChangedListener(onStatusChangedListener);
//        this.addLayer(graphicsLayer);
    }

    public void location() {
        LocationDisplayManager locationDisplayManager = this.getLocationDisplayManager();
        locationDisplayManager.start();
        locationDisplayManager.setAutoPanMode(LocationDisplayManager.AutoPanMode.LOCATION);
//        locationDisplayManager.setLocationListener();
    }

//    public void zoomIn() {
//        this.zoomin();
//    }
//
//    public void zoomOut() {
//        this.zoomout();
//    }

    public static final int SPACE_NONE = 0;
    public static final int SPACE_RECT = 1;
    public static final int SPACE_POLYGON = 2;
    //    public static final int SPACE_POLYGON_SET_FINISH = 3;
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
            drawLayer.removeAll();
//            currentDrawGraphic = null;
        }
    }

    public void clearDrawLayerGraphics() {
        drawLayer.removeAll();
    }

//    public void clearGraphicLayerGraphics() {
//        graphicsLayer.removeAll();
//    }

    public void clearAll() {
        drawLayer.removeAll();
        currentDrawGraphic = null;
//        graphicsLayer.removeAll();
    }

//    public void addGraphic(Graphic graphic) {
//        drawLayer.addGraphic(graphic);
//    }

    public void setCurrentDrawGraphic(Graphic graphic) {
        this.currentDrawGraphic = graphic;
    }

    public void addDrawLayerGraphic(Graphic graphic) {
        this.currentDrawGraphic = graphic;
        drawLayer.addGraphic(graphic);
    }

    public DrawTool getDrawTool() {
        return drawTool;
    }

    public GraphicsLayer getDrawLayer() {
        return drawLayer;
    }

//    public GraphicsLayer getGraphicLayer() {
//        return graphicsLayer;
//    }


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

    public interface MainThreadCallback<T> {
        void onCallback(T result);

        void onError(Throwable e);
    }

    OnStatusChangedListener onStatusChangedListener = new OnStatusChangedListener() {
        @Override
        public void onStatusChanged(Object o, OnStatusChangedListener.STATUS status) {
            switch (status) {
                case INITIALIZED:
                    location();
                    break;
                case LAYER_LOADED:
                    centerAt(29.55, 106.55, true);
                    setScale(100000, true);
                    break;
            }
        }
    };
}
