package com.example.submission_intermediete_dicoding.ui.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.submission_intermediete_dicoding.R
import com.example.submission_intermediete_dicoding.data.response.ListStoryItem
import com.example.submission_intermediete_dicoding.data.retrofit.Injection
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.submission_intermediete_dicoding.databinding.ActivityMapsBinding
import com.example.submission_intermediete_dicoding.ui.viewmodel.MapsViewModel
import com.example.submission_intermediete_dicoding.ui.viewmodel.StoryViewModelFactory
import com.google.android.gms.maps.model.LatLngBounds

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var viewModel: MapsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        viewModel = ViewModelProvider(this, StoryViewModelFactory(Injection.provideStoryRepository(this)))
            .get(MapsViewModel::class.java)

        viewModel.stories.observe(this) { stories ->
            if (stories != null) {
                addManyMarkers(stories)
            }
        }

        viewModel.getStoryWithLoc()
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        setupMapUI()

    }

    private fun setupMapUI() {
        mMap.uiSettings.apply {
            isZoomControlsEnabled = true
            isIndoorLevelPickerEnabled = true
            isCompassEnabled = true
            isMapToolbarEnabled = true
        }
    }

    private val boundsBuilder = LatLngBounds.Builder()

    private fun addManyMarkers(stories: List<ListStoryItem>) {
        stories.forEach { story ->
            val latLng = LatLng(story.lat!!, story.lon!!)
            mMap.addMarker(MarkerOptions().position(latLng).title(story.name).snippet(story.description))
            boundsBuilder.include(latLng)
        }

        val bounds: LatLngBounds = boundsBuilder.build()
        mMap.animateCamera(
            CameraUpdateFactory.newLatLngBounds(
                bounds,
                resources.displayMetrics.widthPixels,
                resources.displayMetrics.heightPixels,
                30
            )
        )
    }
}