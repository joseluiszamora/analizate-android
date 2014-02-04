package com.analizate.main;
import java.util.ArrayList;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.overlays.ExtendedOverlayItem;
import org.osmdroid.bonuspack.overlays.ItemizedOverlayWithBubble;
import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.PathOverlay;

import com.analizate.webservice.GPSTracker;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class AboutMision extends Activity {
	// GPS latitude longitude
	double latitude = 0.0;
    double longitude = 0.0;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aboutus_fragment_mision);
		

		
		// ****** GPS
		final GPSTracker gps = new GPSTracker(this);
		
		MapView map = (MapView) findViewById(R.id.openmapview);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setClickable(true);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);
        map.setUseDataConnection(true);
        map.getController().setZoom(11);
        map.getController().setCenter(new GeoPoint(-16.511066 , -68.157085));
        
        
        
        // start point
        GeoPoint startPoint = new GeoPoint(-16.511066 , -68.157085);
        
        IMapController mapController = map.getController();
        mapController.setZoom(15);
        mapController.setCenter(startPoint);
        
        RoadManager roadManager = new OSRMRoadManager();
        ArrayList<GeoPoint> waypoints = new ArrayList<GeoPoint>();
        waypoints.add(startPoint);
        
        if(gps.canGetLocation()){    		
        	latitude = gps.getLatitude();
	        longitude = gps.getLongitude();
	        
	        // Finish Points
	        waypoints.add(new GeoPoint(latitude, longitude));
        }else{
            Toast.makeText(this, "Debes activar tu GPS ", Toast.LENGTH_SHORT).show();
        }
        
        Road road = roadManager.getRoad(waypoints);
        Log.d("CordovaLog", ">>**>>>>>> " + road.mNodes.size());
        PathOverlay roadOverlay = RoadManager.buildRoadOverlay(road, map.getContext());
        map.getOverlays().add(roadOverlay);
        map.invalidate();
        
        //* ADD CUSTOMIZE POINTS AND BUBBLES
        final ArrayList<ExtendedOverlayItem> roadItems = new ArrayList<ExtendedOverlayItem>();
		ItemizedOverlayWithBubble<ExtendedOverlayItem> roadNodes = new ItemizedOverlayWithBubble<ExtendedOverlayItem>(this, roadItems, map);
		Drawable marker = getResources().getDrawable(R.drawable.marker_kml_point);
		Drawable marker2 = getResources().getDrawable(R.drawable.anhosp);
		
		for (int i = 0; i < waypoints.size(); i++) {
			if (i == 0){
				ExtendedOverlayItem nodeMarker = new ExtendedOverlayItem("Laboratorios Analizate", "Centro Médico 'Adolfo Kolping‎' Constantino D. Medina El Alto, Bolivia +591 2 2811744", waypoints.get(i), this);
	            nodeMarker.setMarkerHotspot(OverlayItem.HotspotPlace.CENTER);
	            nodeMarker.setMarker(marker2);
	            roadNodes.addItem(nodeMarker);
			}else{
				ExtendedOverlayItem nodeMarker = new ExtendedOverlayItem("Ubicacion Actual", "Tú te encuentras aqui", waypoints.get(i), this);
	            nodeMarker.setMarkerHotspot(OverlayItem.HotspotPlace.CENTER);
	            nodeMarker.setMarker(marker);
	            roadNodes.addItem(nodeMarker);
			}
		}
        
        map.getOverlays().add(roadNodes);
        map.invalidate();
	}
}