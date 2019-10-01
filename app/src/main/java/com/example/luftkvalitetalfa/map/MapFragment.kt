package com.example.luftkvalitetalfa.map

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.luftkvalitetalfa.MainActivity
import com.example.luftkvalitetalfa.R
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.maps.*
import com.google.android.gms.maps.GoogleMap
import com.example.luftkvalitetalfa.model.Station
import com.example.luftkvalitetalfa.model.Stations
import com.example.luftkvalitetalfa.service.Measures
import com.example.luftkvalitetalfa.service.Stasjonsliste
import com.google.android.gms.maps.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import android.graphics.Color.parseColor
import android.graphics.Color.colorToHSV
import com.google.android.gms.maps.model.BitmapDescriptor
import kotlinx.android.synthetic.main.fragment_googlemap.*


class MapFragment: Fragment(), OnMapReadyCallback {

    private lateinit var googlemap: GoogleMap
    private lateinit var mview: View
    private lateinit var mapFragment : SupportMapFragment
    private var stationList = Stasjonsliste.shared.stasjonsliste

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mview = inflater.inflate(R.layout.fragment_googlemap, container, false)
        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return mview
    }

    override fun onMapReady(map: GoogleMap) {

        try {
            MapsInitializer.initialize(context)
        } catch (e: GooglePlayServicesNotAvailableException) {
            e.printStackTrace()
        }
        googlemap = map

        googlemap.mapType = GoogleMap.MAP_TYPE_NORMAL

        googlemap.uiSettings.isMyLocationButtonEnabled = true
        googlemap.uiSettings.isZoomControlsEnabled = true
        googlemap.uiSettings.isCompassEnabled = true
        googlemap.uiSettings.isScrollGesturesEnabled = true
        googlemap.uiSettings.isZoomGesturesEnabled = true
        googlemap.uiSettings.isTiltGesturesEnabled = true
        googlemap.uiSettings.isRotateGesturesEnabled = true

        if (checkPermission()){
            googlemap.isMyLocationEnabled = true
        }

        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(LatLng(59.92, 10.83), 10f)
        map.moveCamera(cameraUpdate)


       Handler().postDelayed(
            {
             putMarkers()
            },
            2000
        )

        googlemap.setOnInfoWindowClickListener { marker ->
            for(i in stationList){
                if(marker.title == i.name){

                    var mainActivity: MainActivity = activity as MainActivity
                    mainActivity.changeEOIFragment(i.eoi)
                }
            }
        }
    }

    private fun putMarkers() {

        var marker: MarkerOptions
        val index = Stasjonsliste.shared.getTime()

        for (s in stationList) {
            marker = MarkerOptions().position(
                LatLng(
                    s.latitude.toDouble(),
                    s.longitude.toDouble()
                )
            ).title(s.name).snippet("Klikk her for mer info")

            var aqi = blockingDispatcher(s, index)

            var calculatedAQI = Measures.shared.calculateAQI(aqi)
            println("calculatedAQI: $calculatedAQI")

            when {
                calculatedAQI < 2.0 -> marker.icon(getMarkerColor("#9EFDBF"))
                calculatedAQI < 3.0 -> marker.icon(getMarkerColor("#FDF29E"))
                calculatedAQI < 4.0 -> marker.icon(getMarkerColor("#FF6969"))
                else -> marker.icon(getMarkerColor("#58004E"))
            }

            googlemap.addMarker(marker)
        }
        marker = MarkerOptions().position(
            LatLng(
                59.9433046,
                10.7156985
            )
        ).title("IFI").snippet("Klikk for mer info").icon(getMarkerColor("#FF6969"))
        googlemap.addMarker(marker)

        marker = MarkerOptions().position(
            LatLng(
                59.9614638,
                10.6281671
            )
        ).title("Holmenkollen").snippet("Klikk for mer info").icon(getMarkerColor("#58004E"))
        googlemap.addMarker(marker)

        //legger til mockpin
        //putMockMarker(Stasjonsliste.shared.luftkvalitetsliste["NO0015R"]!!)
    }
    private fun putMockMarker(s: Station){
        val time = Stasjonsliste.shared.getTime()
        val marker = MarkerOptions().position(LatLng(59.9433046,10.7156985)).title("IFI").snippet("Her er ifi")
        s.meta.location.name = "IFI"
        s.data.time[time].variables.pm10_concentration.value = (8.90).toFloat()
        s.data.time[time].variables.pm25_concentration.value = (27.90).toFloat()
        s.data.time[time].variables.no2_concentration.value = (210.80).toFloat()
        s.data.time[time].variables.o3_concentration.value = (260.00).toFloat()

        googlemap.addMarker(marker)

    }
    //denne metoden er tatt ifra denne linken: https://stackoverflow.com/questions/19076124/android-map-marker-color
    fun getMarkerColor(color: String): BitmapDescriptor {
        val hsv = FloatArray(3)
        colorToHSV(parseColor(color), hsv)
        return BitmapDescriptorFactory.defaultMarker(hsv[0])
    }

    private fun blockingDispatcher(s: Stations, index: Int): Double {
        lateinit var maalestasjon: Station
        runBlocking(Dispatchers.IO) {
            maalestasjon = setLuftkvalitetInfo(s)
        }
       return setAQI(maalestasjon, index)
    }

    private fun setLuftkvalitetInfo(s: Stations): Station {
        return Stasjonsliste.shared.luftkvalitetsliste[s.eoi]!!
    }

    private fun setAQI(s: Station?, index: Int): Double {
        return s!!.data.time[index].variables.AQI.value.toDouble()
    }

    private fun checkPermission(): Boolean {
        val finelocation = ContextCompat.checkSelfPermission(activity!!, Manifest.permission.ACCESS_FINE_LOCATION)
        val coarseloaction = ContextCompat.checkSelfPermission(activity!!, Manifest.permission.ACCESS_COARSE_LOCATION)

        if(finelocation == PackageManager.PERMISSION_GRANTED && coarseloaction == PackageManager.PERMISSION_GRANTED){
            return true
        }
        return false
    }
}