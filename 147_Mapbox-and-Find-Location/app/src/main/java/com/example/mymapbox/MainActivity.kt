package com.example.mymapbox

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.animation.BounceInterpolator
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.location.LocationComponent
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions
import com.mapbox.mapboxsdk.location.LocationComponentOptions
import com.mapbox.mapboxsdk.location.modes.CameraMode
import com.mapbox.mapboxsdk.location.modes.RenderMode
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
    private lateinit var locationComponent:LocationComponent
    private lateinit var myLocation:LatLng
    private lateinit var permissionManager:PermissionsManager

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
                showMyLocation(style)
                addMarkerOnClick()
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


    @SuppressLint("MissingPermission")
    private fun showMyLocation(style: Style) {
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            val locationComponentOptions = LocationComponentOptions.builder(this)
                .pulseEnabled(true)
                .pulseColor(Color.BLUE)
                .pulseAlpha(.4f)
                .pulseInterpolator(BounceInterpolator())
                .build()
            val locationComponentActivationOptions = LocationComponentActivationOptions
                .builder(this, style)
                .locationComponentOptions(locationComponentOptions)
                .build()
            locationComponent = mapBoxMap.locationComponent
            locationComponent.activateLocationComponent(locationComponentActivationOptions)
            locationComponent.isLocationComponentEnabled = true
            locationComponent.cameraMode = CameraMode.TRACKING
            locationComponent.renderMode = RenderMode.COMPASS
            myLocation = LatLng(locationComponent.lastKnownLocation?.latitude as Double, locationComponent.lastKnownLocation?.longitude as Double)
            mapBoxMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 12.0))
        } else {
            permissionManager = PermissionsManager(object : PermissionsListener{
                override fun onExplanationNeeded(permissionsToExplain: MutableList<String>?) {
                    Toast.makeText(this@MainActivity, "Anda harus mengizinkan location permission untuk menggunakan aplikasi ini", Toast.LENGTH_SHORT).show()
                }
                override fun onPermissionResult(granted: Boolean) {
                    if (granted) {
                        mapBoxMap.getStyle { style ->
                            showMyLocation(style)
                        }
                    } else {
                        finish()
                    }
                }
            })
            permissionManager.requestLocationPermissions(this)
        }
    }

    private fun addMarkerOnClick(){
        mapBoxMap.addOnMapClickListener {
            point -> symbolManager.deleteAll()
            symbolManager.create(
                SymbolOptions()
                    .withLatLng(LatLng(point.latitude, point.longitude))
                    .withIconImage(ICON_ID)
                    .withDraggable(true)
            )
            true
        }
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