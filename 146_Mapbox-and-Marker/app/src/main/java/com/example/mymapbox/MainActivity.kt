package com.example.mymapbox

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions

class MainActivity : AppCompatActivity() {

    private lateinit var mapView:MapView
    private lateinit var btnNavigation:Button
    private lateinit var mapBoxMap:MapboxMap
    private lateinit var symbolManager:SymbolManager

    companion object{
        private const val ICON_ID = "ICON_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mapView = findViewById(R.id.mapView)
        btnNavigation = findViewById(R.id.btnNavigation)

        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync {
            mapBoxMap -> this.mapBoxMap = mapBoxMap
            mapBoxMap.setStyle(Style.MAPBOX_STREETS){
                style ->
                // Get Marker
                symbolManager = SymbolManager(mapView, mapBoxMap, style)
                symbolManager.iconAllowOverlap = true

                style.addImage( ICON_ID,BitmapFactory.decodeResource(resources, R.drawable.mapbox_marker_icon_default) )

                showMarkerLocation()
            }
        }
    }

    private fun showMarkerLocation(){
        val showMarker = LatLng(-8.0459557,110.6441672)
        symbolManager.create(
                SymbolOptions()
                        .withLatLng(LatLng(showMarker.latitude, showMarker.longitude))
                        .withIconImage(ICON_ID)
                        .withIconSize(1.5f)
                        .withIconOffset(arrayOf(0f, -1.5f))
                        .withTextField("Goa Jombang")
                        .withTextHaloColor("rgba(255, 255, 255, 100)")
                        .withTextHaloWidth(5.0f)
                        .withTextAnchor("top")
                        .withTextOffset(arrayOf(0f,1.5f))
                        .withDraggable(true)
        )
        mapBoxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(showMarker, 10.0))
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}