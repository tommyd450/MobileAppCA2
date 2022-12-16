package org.com.animaltracker.activities

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso
import org.com.animaltracker.R

import org.com.animaltracker.databinding.ActivityAnimalMapBinding
import org.com.animaltracker.databinding.ContentAnimalMapBinding
import org.com.animaltracker.main.MainApp
import org.com.animaltracker.model.AnimalModel

class AnimalMapActivity : AppCompatActivity(), GoogleMap.OnMarkerClickListener {

    private lateinit var binding: ActivityAnimalMapBinding
    private lateinit var contentBinding: ContentAnimalMapBinding
    lateinit var map: GoogleMap
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = application as MainApp
        binding = ActivityAnimalMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        contentBinding = ContentAnimalMapBinding.bind(binding.root)
        contentBinding.mapView.onCreate(savedInstanceState)

        contentBinding.mapView.getMapAsync {
            map = it
            configureMap()
        }
    }
    private fun configureMap() {
        map.uiSettings.isZoomControlsEnabled = true
        app.animals.findAll().forEach {
            val loc = LatLng(it.location.lat, it.location.lng)
            val options = MarkerOptions().title(it.title).position(loc)
            val genus = MarkerOptions().title(it.genus).position(loc)
            map.addMarker(options)?.tag = it

            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.location.zoom))
            map.setOnMarkerClickListener(this)
        }
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        var anim: AnimalModel = marker.tag as AnimalModel
        contentBinding.aSpecies.text = marker.title
        contentBinding.aTitle.text = anim.genus
        Picasso.get()
            .load(anim.image)
            .into(contentBinding.imageView)
        return false
    }
    override fun onDestroy() {
        super.onDestroy()
        contentBinding.mapView.onDestroy()
    }
    override fun onLowMemory() {
        super.onLowMemory()
        contentBinding.mapView.onLowMemory()
    }
    override fun onPause() {
        super.onPause()
        contentBinding.mapView.onPause()
    }
    override fun onResume() {
        super.onResume()
        contentBinding.mapView.onResume()
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        contentBinding.mapView.onSaveInstanceState(outState)
    }

}