//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package android.zhixun.uiho.com.gissystem;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.esri.android.map.Callout;
import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.GroupLayer;
import com.esri.android.map.Layer;
import com.esri.android.map.MapView;
import com.esri.android.map.TiledServiceLayer.TileInfo;
import com.esri.android.map.ags.ArcGISDynamicMapServiceLayer;
import com.esri.android.map.ags.ArcGISFeatureLayer;
import com.esri.android.map.ags.ArcGISLayerInfo;
import com.esri.android.map.ags.ArcGISPopupInfo;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
import com.esri.android.map.popup.PopupContainer;
import com.esri.android.toolkit.map.MapViewHelper;
import com.esri.android.toolkit.map.PopupCreateListener;
import com.esri.android.toolkit.util.TaskExecutor;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Latlon;
import com.esri.core.geometry.Polygon;
import com.esri.core.geometry.Polyline;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.map.FeatureSet;
import com.esri.core.map.Graphic;
import com.esri.core.map.popup.PopupInfo;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.esri.core.symbol.SimpleFillSymbol;
import com.esri.core.symbol.SimpleLineSymbol;
import com.esri.core.tasks.ags.query.Query;
import com.esri.core.tasks.ags.query.QueryTask;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;

public class SketchLayer extends GraphicsLayer {
    private static final String BITMAP = "bitmap";
    private static final String SNIPPET = "snippet";
    private static final String TITLE = "title";
    private static final String DRAGGABLE = "draggable";
    private static final int TOLERANCE = 20;
    private static final int MAXFEATURE = 10;
    private static final int MAXCALLOUTLENGTH = 200;
    private MapView map;
    private int[] selection;
    private boolean showGraphicCallout = true;
    private MapViewHelper mapHelper;

    public SketchLayer(MapViewHelper mapHelper) {
        if(mapHelper == null) {
            throw new IllegalArgumentException("mapHelper == null");
        } else {
            this.mapHelper = mapHelper;
            this.map = mapHelper.getMapView();
        }
    }

    int addPolygonGraphic(double[][] latlon, String title, String snippet, int resID, int fillColor, int strokeColor, float width, int zorder) {
        if(this.map.isLoaded() && latlon.length > 1) {
            HashMap<String, Object> attributes = this.setAttributes(title, snippet, Integer.valueOf(resID), false);
            Polygon polygon = Latlon.createPolygon(latlon);
            SimpleFillSymbol symbol = new SimpleFillSymbol(fillColor);
            symbol.setOutline(new SimpleLineSymbol(strokeColor, width));
            Graphic graphic = new Graphic(GeometryEngine.project(polygon, SpatialReference.create(SpatialReference.WKID_WGS84), this.getSpatialReference()), symbol, attributes, zorder);
            return this.addGraphic(graphic);
        } else {
            return -1;
        }
    }

    int addPolygonGraphic(double[][] latlon, String title, String snippet, String url, int fillColor, int strokeColor, float width, int zorder) {
        if(this.map.isLoaded() && latlon.length > 1) {
            HashMap<String, Object> attributes = this.setAttributes(title, snippet, url, false);
            Polygon polygon = Latlon.createPolygon(latlon);
            SimpleFillSymbol symbol = new SimpleFillSymbol(fillColor);
            symbol.setOutline(new SimpleLineSymbol(strokeColor, width));
            Graphic graphic = new Graphic(GeometryEngine.project(polygon, SpatialReference.create(SpatialReference.WKID_WGS84), this.getSpatialReference()), symbol, attributes, zorder);
            return this.addGraphic(graphic);
        } else {
            return -1;
        }
    }

    int addPolylineGraphic(double[][] latlon, String title, String snippet, int resID, int color, float width, int zorder) {
        if(this.map.isLoaded() && latlon.length > 1) {
            HashMap<String, Object> attributes = this.setAttributes(title, snippet, Integer.valueOf(resID), false);
            Polyline polyline = Latlon.createPolyline(latlon);
            Graphic graphic = new Graphic(GeometryEngine.project(polyline, SpatialReference.create(SpatialReference.WKID_WGS84), this.getSpatialReference()), new SimpleLineSymbol(color, width), attributes, zorder);
            return this.addGraphic(graphic);
        } else {
            return -1;
        }
    }

    int addPolylineGraphic(double[][] latlon, String title, String snippet, String url, int color, float width, int zorder) {
        if(this.map.isLoaded() && latlon.length > 1) {
            HashMap<String, Object> attributes = this.setAttributes(title, snippet, url, false);
            Polyline polyline = Latlon.createPolyline(latlon);
            Graphic graphic = new Graphic(GeometryEngine.project(polyline, SpatialReference.create(SpatialReference.WKID_WGS84), this.getSpatialReference()), new SimpleLineSymbol(color, width), attributes, zorder);
            return this.addGraphic(graphic);
        } else {
            return -1;
        }
    }

    int addMarkerGraphic(double lat, double lon, String title, String snippet, int resID, Drawable icon, boolean draggable, int zorder) {
        if(!this.map.isLoaded()) {
            return -1;
        } else {
            HashMap<String, Object> attributes = this.setAttributes(title, snippet, Integer.valueOf(resID), draggable);
            Drawable icn = icon != null?icon:this.map.getResources().getDrawable(17301516);
            Graphic graphic = new Graphic(GeometryEngine.project(lon, lat, this.getSpatialReference()), new PictureMarkerSymbol(this.map.getContext(), icn), attributes, zorder);
            return this.addGraphic(graphic);
        }
    }

    int addMarkerGraphic(double lat, double lon, String title, String snippet, String url, Drawable icon, boolean draggable, int zorder) {
        if(!this.map.isLoaded()) {
            return -1;
        } else {
            HashMap<String, Object> attributes = this.setAttributes(title, snippet, url, draggable);
            Drawable icn = icon != null?icon:this.map.getResources().getDrawable(R.mipmap.ic_launcher);
            Graphic graphic = new Graphic(GeometryEngine.project(lon, lat, this.getSpatialReference()), new PictureMarkerSymbol(this.map.getContext(), icn), attributes, zorder);
            return this.addGraphic(graphic);
        }
    }

    private HashMap<String, Object> setAttributes(String title, String snippet, Object bitmap, boolean draggable) {
        HashMap<String, Object> attributes = new HashMap();
        attributes.put("bitmap", bitmap);
        attributes.put("title", title);
        attributes.put("snippet", snippet);
        attributes.put("draggable", Boolean.valueOf(draggable));
        return attributes;
    }

    public boolean onSingleTap(MotionEvent point) {
        if(!this.showGraphicCallout && this.mapHelper.getOnGraphicClickListener() == null) {
            return false;
        } else {
            int[] sel = this.getGraphicIDs(point.getX(), point.getY(), 20, 1);
            if(sel != null && sel.length > 0) {
                final Graphic graphic = this.getGraphic(sel[0]);
                if(this.showGraphicCallout) {
                    final LinearLayout calloutView = new LinearLayout(this.map.getContext());
                    calloutView.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {
                            if(SketchLayer.this.mapHelper.getOnCalloutClickListener() != null) {
                                SketchLayer.this.mapHelper.getOnCalloutClickListener().onCalloutClick(graphic);
                            }

                        }
                    });
                    calloutView.setPadding(3, 3, 3, 3);
                    calloutView.setGravity(17);
                    Object bitmap = graphic.getAttributeValue("bitmap");
                    if(bitmap != null) {
                        final ImageView imageView;
                        if(bitmap instanceof Integer) {
                            Drawable drawable = this.map.getResources().getDrawable(((Integer)bitmap).intValue());
                            imageView = new ImageView(this.map.getContext());
                            imageView.setImageDrawable(drawable);
                            calloutView.addView(imageView, -2, -2);
                        } else {
                            final String url = (String)bitmap;
                            if(!TextUtils.isEmpty(url)) {
                                imageView = new ImageView(this.map.getContext());
                                calloutView.addView(imageView, -2, -2);
                                TaskExecutor.pool.submit(new Runnable() {
                                    public void run() {
                                        try {
                                            InputStream is = (InputStream)(new URL(url)).getContent();
                                            final Bitmap bitmap = BitmapFactory.decodeStream(is);
                                            if(bitmap != null) {
                                                SketchLayer.this.map.post(new Runnable() {
                                                    public void run() {
                                                        imageView.setImageBitmap(bitmap);
                                                        imageView.setPadding(0, 0, 5, 0);
                                                        calloutView.invalidate();
                                                    }
                                                });
                                            }
                                        } catch (Exception var3) {
                                            Log.e(SketchLayer.this.map.getClass().getSimpleName(), "", var3);
                                        }

                                    }
                                });
                            }
                        }
                    }

                    LinearLayout titleView = new LinearLayout(this.map.getContext());
                    titleView.setOrientation(LinearLayout.VERTICAL);
                    titleView.setGravity(17);
                    TextView title = new TextView(this.map.getContext());
                    title.setSingleLine();
                    title.setEllipsize(TruncateAt.MARQUEE);
                    title.setSelected(true);
                    title.setMarqueeRepeatLimit(-1);
                    title.setMaxWidth(200);
                    title.setText((CharSequence)graphic.getAttributeValue("title"));
                    title.setTypeface((Typeface)null, 1);
                    title.setGravity(1);
                    titleView.addView(title, -2, -2);
                    TextView snippet = new TextView(this.map.getContext());
                    snippet.setText((CharSequence)graphic.getAttributeValue("snippet"));
                    snippet.setSingleLine();
                    snippet.setEllipsize(TruncateAt.MARQUEE);
                    snippet.setSelected(true);
                    snippet.setMarqueeRepeatLimit(-1);
                    snippet.setMaxWidth(200);
                    titleView.addView(snippet, -2, -2);
                    Callout callout = this.map.getCallout();
                    callout.setOffset(0, 50);
                    calloutView.addView(titleView, -2, -2);
                    if(this.mapHelper.getOnCalloutClickListener() != null) {
                        calloutView.setOnClickListener(new OnClickListener() {
                            public void onClick(View v) {
                                if(SketchLayer.this.mapHelper.getOnCalloutClickListener() != null) {
                                    SketchLayer.this.mapHelper.getOnCalloutClickListener().onCalloutClick(graphic);
                                }

                            }
                        });
                    }

                    callout.show(this.map.toMapPoint(point.getX(), point.getY()), calloutView);
                }

                if(this.mapHelper.getOnGraphicClickListener() != null) {
                    this.mapHelper.getOnGraphicClickListener().onGraphicClick(graphic);
                }
            } else {
                if(!this.map.getCallout().isShowing()) {
                    return false;
                }

                this.map.getCallout().hide();
            }

            return true;
        }
    }

    public boolean onLongPress(MotionEvent point) {
        this.map.getCallout().hide();
        int[] sel = this.getGraphicIDs(point.getX(), point.getY(), 20, 1);
        if(sel != null && sel.length > 0) {
            Graphic graphic = this.getGraphic(sel[0]);
            if(!((Boolean)graphic.getAttributeValue("draggable")).booleanValue()) {
                return false;
            } else {
                this.selection = new int[]{sel[0]};
                this.updateGraphic(this.selection[0], this.map.toMapPoint(point.getX(), point.getY() - 100.0F));
                return true;
            }
        } else {
            return false;
        }
    }

    public boolean onDragPointerMove(MotionEvent from, MotionEvent to) {
        if(this.selection != null && this.selection.length > 0) {
            this.updateGraphic(this.selection[0], this.map.toMapPoint(to.getX(), to.getY() - 100.0F));
            return true;
        } else {
            return false;
        }
    }

    public boolean onDragPointerUp(MotionEvent from, MotionEvent to) {
        if(this.selection != null && this.selection.length > 0) {
            this.selection = null;
            return true;
        } else {
            return false;
        }
    }

    public void showGraphicCallout(boolean show) {
        this.showGraphicCallout = show;
        if(!show) {
            this.map.getCallout().hide();
        }

    }

    public void createPopup(float screenX, float screenY, PopupCreateListener listener) {
        PopupContainer container = new PopupContainer(this.map);
        Layer[] var5 = this.map.getLayers();
        int var6 = var5.length;

        for(int var7 = 0; var7 < var6; ++var7) {
            Layer layer = var5[var7];
            this.query(layer, screenX, screenY, listener, container);
        }

    }

    void query(Layer layer, float screenX, float screenY, PopupCreateListener listener, PopupContainer container) {
        if(layer instanceof ArcGISFeatureLayer) {
            this.query((ArcGISFeatureLayer)layer, screenX, screenY, listener, container);
        } else if(layer instanceof ArcGISDynamicMapServiceLayer) {
            this.query((ArcGISDynamicMapServiceLayer)layer, screenX, screenY, listener, container);
        } else if(layer instanceof ArcGISTiledMapServiceLayer) {
            this.query((ArcGISTiledMapServiceLayer)layer, screenX, screenY, listener, container);
        } else if(layer instanceof GroupLayer) {
            this.query((GroupLayer)layer, screenX, screenY, listener, container);
        }

    }

    private void query(GroupLayer layer, float screenX, float screenY, PopupCreateListener listener, PopupContainer container) {
        Layer[] subLayers = layer.getLayers();
        if(subLayers != null && subLayers.length != 0) {
            Layer[] var7 = subLayers;
            int var8 = subLayers.length;

            for(int var9 = 0; var9 < var8; ++var9) {
                Layer subLayer = var7[var9];
                this.query(subLayer, screenX, screenY, listener, container);
            }

        }
    }

    private void query(final ArcGISFeatureLayer layer, final float screenX, final float screenY, final PopupCreateListener listener, final PopupContainer container) {
        TaskExecutor.pool.submit(new Runnable() {
            public void run() {
                SketchLayer.this.queryLayer(layer, screenX, screenY, listener, container);
            }
        });
    }

    private void query(final ArcGISDynamicMapServiceLayer layer, final float screenX, final float screenY, final PopupCreateListener listener, final PopupContainer container) {
        TaskExecutor.pool.submit(new Runnable() {
            public void run() {
                try {
                    ArcGISLayerInfo[] layerInfos = layer.getAllLayers();
                    if(layerInfos == null) {
                        return;
                    }

                    ArcGISLayerInfo[] var2 = layerInfos;
                    int var3 = layerInfos.length;

                    for(int var4 = 0; var4 < var3; ++var4) {
                        ArcGISLayerInfo layerInfo = var2[var4];
                        ArcGISPopupInfo popupInfo = layer.getPopupInfo(layerInfo.getId());
                        if(popupInfo != null) {
                            ArcGISLayerInfo linfo;
                            for(linfo = layerInfo; linfo != null && linfo.isVisible(); linfo = linfo.getParentLayer()) {
                                ;
                            }

                            if(linfo == null || linfo.isVisible()) {
                                double maxScale = layerInfo.getMaxScale() != 0.0D?layerInfo.getMaxScale():popupInfo.getMaxScale();
                                double minScale = layerInfo.getMinScale() != 0.0D?layerInfo.getMinScale():popupInfo.getMinScale();
                                boolean matchesMaxScale = maxScale == 0.0D || SketchLayer.this.map.getScale() > maxScale;
                                boolean matchesMinScale = minScale == 0.0D || SketchLayer.this.map.getScale() < minScale;
                                if(matchesMaxScale && matchesMinScale) {
                                    SketchLayer.this.queryLayer(layer, layerInfo.getId(), layer.getUrl() + "/" + layerInfo.getId(), popupInfo, screenX, screenY, layer.getSpatialReference(), listener, container);
                                }
                            }
                        }
                    }
                } catch (Exception var14) {
                    Log.e(SketchLayer.this.map.getClass().getSimpleName(), "", var14);
                }

            }
        });
    }

    private void query(final ArcGISTiledMapServiceLayer layer, final float screenX, final float screenY, final PopupCreateListener listener, final PopupContainer container) {
        TaskExecutor.pool.submit(new Runnable() {
            public void run() {
                try {
                    ArcGISTiledMapServiceLayer tiledLayer = layer;
                    ArcGISLayerInfo[] layerinfos = tiledLayer.getAllLayers();
                    if(layerinfos == null) {
                        return;
                    }

                    ArcGISLayerInfo[] var3 = layerinfos;
                    int var4 = layerinfos.length;

                    for(int var5 = 0; var5 < var4; ++var5) {
                        ArcGISLayerInfo layerInfo = var3[var5];
                        int layerID = layerInfo.getId();
                        String layerUrl = tiledLayer.getQueryUrl(layerID);
                        if(layerUrl == null) {
                            layerUrl = tiledLayer.getUrl() + "/" + layerID;
                        }

                        PopupInfo popupInfo = tiledLayer.getPopupInfo(layerID);
                        if(layerInfo.getLayers() != null && layerInfo.getLayers().length <= 0 && popupInfo != null) {
                            ArcGISLayerInfo linfo;
                            for(linfo = layerInfo; linfo != null && linfo.isVisible(); linfo = linfo.getParentLayer()) {
                                ;
                            }

                            if(linfo == null || linfo.isVisible()) {
                                double maxScale = layerInfo.getMaxScale() != 0.0D?layerInfo.getMaxScale():popupInfo.getMaxScale();
                                double minScale = layerInfo.getMinScale() != 0.0D?layerInfo.getMinScale():popupInfo.getMinScale();
                                int currentLevel = tiledLayer.getCurrentLevel();
                                TileInfo tileInfo = tiledLayer.getTileInfo();
                                double lodscale = tileInfo.getScales()[currentLevel];
                                boolean matchesMaxScale = maxScale == 0.0D || lodscale > maxScale;
                                boolean matchesMinScale = minScale == 0.0D || lodscale < minScale;
                                if(matchesMaxScale && matchesMinScale) {
                                    SketchLayer.this.queryLayer(layer, layerID, layerUrl, popupInfo, screenX, screenY, layer.getSpatialReference(), listener, container);
                                }
                            }
                        }
                    }
                } catch (Exception var21) {
                    Log.e(SketchLayer.this.map.getClass().getSimpleName(), "", var21);
                }

            }
        });
    }

    private void queryLayer(Layer layer, int layerID, String url, PopupInfo popupInfo, float screenX, float screenY, SpatialReference sr, PopupCreateListener listener, PopupContainer container) {
        try {
            Query query = new Query();
            query.setInSpatialReference(sr);
            query.setOutSpatialReference(sr);
            query.setGeometry(this.getEnvelope(screenX, screenY));
            query.setMaxFeatures(10);
            query.setOutFields(new String[]{"*"});
            QueryTask queryTask = new QueryTask(url, layer.getCredentials());
            FeatureSet results = queryTask.execute(query);
            if(results != null && results.getGraphics() != null && results.getGraphics().length > 0) {
                Graphic[] graphics = results.getGraphics();
                Graphic[] var14 = graphics;
                int var15 = graphics.length;

                for(int var16 = 0; var16 < var15; ++var16) {
                    Graphic graphic = var14[var16];
                    container.addPopup(layer.createPopup(this.map, layerID, graphic));
                }

                listener.onResult(container);
            }
        } catch (Exception var18) {
            Log.e(this.map.getClass().getSimpleName(), "", var18);
        }

    }

    private void queryLayer(ArcGISFeatureLayer featureLayer, float screenX, float screenY, PopupCreateListener listener, PopupContainer container) {
        try {
            featureLayer.clearSelection();
            int[] ids = featureLayer.getGraphicIDs(screenX, screenY, 20);
            if(ids != null && ids.length > 0) {
                int[] var7 = ids;
                int var8 = ids.length;

                for(int var9 = 0; var9 < var8; ++var9) {
                    int id = var7[var9];
                    Graphic g = featureLayer.getGraphic(id);
                    if(g != null) {
                        container.addPopup(featureLayer.createPopup(this.map, 0, g));
                    }
                }

                listener.onResult(container);
            }
        } catch (Exception var12) {
            Log.e(this.map.getClass().getSimpleName(), "", var12);
        }

    }

    private Envelope getEnvelope(float screenX, float screenY) {
        double res = this.map.getResolution();
        return new Envelope(this.map.toMapPoint(screenX, screenY), 20.0D * res, 20.0D * res);
    }
}
