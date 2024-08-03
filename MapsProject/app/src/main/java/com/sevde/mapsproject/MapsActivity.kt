package com.sevde.mapsproject

import android.Manifest
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.sevde.mapsproject.databinding.ActivityMapsBinding
import java.util.Locale

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: LocationListener
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private lateinit var sharedPreferences: SharedPreferences

    var takipBoolean: Boolean? = null

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        registerLauncher()

        sharedPreferences = getSharedPreferences("com.sevde.mapsproject", MODE_PRIVATE)
        takipBoolean = sharedPreferences.getBoolean("takipBoolean", false)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMapLongClickListener(this)

        locationManager = this.getSystemService(LOCATION_SERVICE) as LocationManager
        locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                takipBoolean = sharedPreferences.getBoolean("takipBoolean", false)
                if (!takipBoolean!!) {
                    mMap.clear()
                    val kullaniciKonumu = LatLng(location.latitude, location.longitude)
                    mMap.addMarker(MarkerOptions().position(kullaniciKonumu).title("Konumunuz"))
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(kullaniciKonumu, 14f))
                    sharedPreferences.edit().putBoolean("takipBoolean", true).apply()
                }
            }

            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {}
            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                Snackbar.make(binding.root, "Konumunu almak için izin gerekli", Snackbar.LENGTH_INDEFINITE)
                    .setAction("İzin Ver") {
                        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                    }.show()
            } else {
                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, locationListener)
            val sonBilinenKonum = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (sonBilinenKonum != null) {
                val sonBilinenLatLng = LatLng(sonBilinenKonum.latitude, sonBilinenKonum.longitude)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sonBilinenLatLng, 14f))
            }
        }
    }

    private fun registerLauncher() {
        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
            if (result) {
                if (ContextCompat.checkSelfPermission(this@MapsActivity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, locationListener)
                    val sonBilinenKonum = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    if (sonBilinenKonum != null) {
                        val sonBilinenLatLng = LatLng(sonBilinenKonum.latitude, sonBilinenKonum.longitude)
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sonBilinenLatLng, 14f))
                    }
                }
            } else {
                Toast.makeText(this@MapsActivity, "İzne ihtiyacımız var!", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onMapLongClick(p0: LatLng) {
        mMap.clear()
        println(p0.latitude)
        println(p0.longitude)

        val geocoder = Geocoder(this, Locale.getDefault())
        var adres = ""

        try {
            val liste = geocoder.getFromLocation(p0.latitude, p0.longitude, 1)
            if (liste != null && liste.isNotEmpty()) {
                val ilkAdres = liste.first()
                val ulkeAdi = ilkAdres.countryName
                val cadde = ilkAdres.thoroughfare ?: ""
                val sokak = ilkAdres.subThoroughfare ?: ""
                adres += "$cadde $sokak"
                println(adres)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        mMap.addMarker(MarkerOptions().position(p0).title(adres))
    }
}






