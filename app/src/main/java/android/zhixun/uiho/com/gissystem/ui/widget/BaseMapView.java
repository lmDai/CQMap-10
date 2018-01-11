package android.zhixun.uiho.com.gissystem.ui.widget;

import android.content.Context;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.zhixun.uiho.com.gissystem.R;
import android.zhixun.uiho.com.gissystem.tdt.TianDiTuLayer;
import android.zhixun.uiho.com.gissystem.tdt.TianDiTuLayerTypes;

import com.esri.android.map.LocationDisplayManager;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
import com.esri.android.map.event.OnStatusChangedListener;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public class BaseMapView extends MapView {

    private ArcGISTiledMapServiceLayer vecLayer;
    private ArcGISTiledMapServiceLayer demLayer;
    private ArcGISTiledMapServiceLayer imgLayer;
    @CurrentLayer
    private int currentLayer;

    public BaseMapView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TianDiTuLayer tianDiTuLayer = new TianDiTuLayer(TianDiTuLayerTypes.TIANDITU_VECTOR_2000);
        this.addLayer(tianDiTuLayer, 0);

        vecLayer = new ArcGISTiledMapServiceLayer(context.getString(R.string.tdt_vec_base_map_url));
        this.addLayer(vecLayer, 1);
        currentLayer = VEC_LAYER;

        demLayer = new ArcGISTiledMapServiceLayer(context.getString(R.string.tdt_dem_base_map_url));

        imgLayer =
                new ArcGISTiledMapServiceLayer(context.getString(R.string.tdt_img_base_map_url));

        this.setOnStatusChangedListener((OnStatusChangedListener) (o, status) -> {
            this.centerAt(29.55, 106.55, true);
            this.setScale(100000, true);
        });
    }

    public static final int DEM_LAYER = 0;
    public static final int VEC_LAYER = 1;
    public static final int IMG_LAYER = 2;

    @IntDef({DEM_LAYER, VEC_LAYER, IMG_LAYER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface CurrentLayer {
    }

    public int getCurrentLayer() {
        return currentLayer;
    }

    public void setCurrentLayer(@CurrentLayer int currentLayer) {
        this.currentLayer = currentLayer;
        switch (currentLayer) {
            case DEM_LAYER:
                removeLayer(1);
                this.addLayer(demLayer, 1);
                break;
            case VEC_LAYER:
                removeLayer(1);
                this.addLayer(vecLayer, 1);
                break;
            case IMG_LAYER:
                removeLayer(1);
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
}
