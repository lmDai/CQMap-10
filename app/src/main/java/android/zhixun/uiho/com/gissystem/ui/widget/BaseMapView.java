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

    private ArcGISTiledMapServiceLayer vecLayer;
    private ArcGISTiledMapServiceLayer demLayer;
    private ArcGISTiledMapServiceLayer imgLayer;
    //
    private GraphicsLayer drawLayer = new GraphicsLayer();
    private DrawTool drawTool;
    //
    GraphicsLayer graphicsLayer = new GraphicsLayer();
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
        TianDiTuLayer tianDiTuLayer = new TianDiTuLayer(TianDiTuLayerTypes.TIANDITU_VECTOR_2000);
        this.addLayer(tianDiTuLayer, 0);
        //重庆地图的layer
        vecLayer = new ArcGISTiledMapServiceLayer(context.getString(R.string.tdt_vec_base_map_url));
        this.addLayer(vecLayer, 1);
        currentMapLayer = VEC_LAYER;

        demLayer = new ArcGISTiledMapServiceLayer(context.getString(R.string.tdt_dem_base_map_url));

        imgLayer =
                new ArcGISTiledMapServiceLayer(context.getString(R.string.tdt_img_base_map_url));

        this.setOnStatusChangedListener((OnStatusChangedListener) (o, status) -> {
            this.centerAt(29.55, 106.55, true);
            this.setScale(100000, true);
        });
        //画图形的layer
        this.addLayer(drawLayer, 2);
        drawTool = new DrawTool(this);
        drawTool.addEventListener(this);
        //添加标注的layer
        this.addLayer(graphicsLayer, 3);
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

        removeLayer(1);
        switch (currentMapLayer) {
            case DEM_LAYER:
                this.addLayer(demLayer, 1);
                break;
            case VEC_LAYER:
                this.addLayer(vecLayer, 1);
                break;
            case IMG_LAYER:
                this.addLayer(imgLayer, 1);
                break;
        }
    }

    public void location() {
        LocationDisplayManager locationDisplayManager = this.getLocationDisplayManager();
        locationDisplayManager.start();
        locationDisplayManager.setAutoPanMode(LocationDisplayManager.AutoPanMode.LOCATION);
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
    public static final int SPACE_POLYGON_SET_FINISH = 3;
    public static final int SPACE_BUFFER = 4;
    public static final int SPACE_BUFFER_SET_FINISH = 5;

    @IntDef({SPACE_NONE, SPACE_RECT, SPACE_POLYGON, SPACE_POLYGON_SET_FINISH,
            SPACE_BUFFER, SPACE_BUFFER_SET_FINISH})
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

    public void clearGraphicLayerGraphics() {
        graphicsLayer.removeAll();
    }

    public void clearAll() {
        drawLayer.removeAll();
        graphicsLayer.removeAll();
//        graphicsIds.clear();
    }

    public void addGraphic(Graphic graphic) {
        graphicsLayer.addGraphic(graphic);
    }

    public void setCurrentDrawGraphic(Graphic graphic){
        this.currentDrawGraphic = graphic;
    }

    public void addDrawLayerGraphic(Graphic graphic) {
        currentDrawGraphic = graphic;
        drawLayer.addGraphic(graphic);
    }

    public DrawTool getDrawTool() {
        return drawTool;
    }

    public GraphicsLayer getDrawLayer() {
        return drawLayer;
    }

    public GraphicsLayer getGraphicLayer() {
        return graphicsLayer;
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
}
