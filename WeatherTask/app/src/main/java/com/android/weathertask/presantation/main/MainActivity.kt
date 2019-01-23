package com.android.weathertask.presantation.main

import android.app.Activity
import android.content.Intent
import android.location.Location
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.android.weathertask.R
import com.android.weathertask.presantation.base.LocationBaseActivity
import com.android.weathertask.presantation.locations.LocationsFragment
import com.android.weathertask.presantation.weather.WeatherFragment
import com.android.weathertask.utils.Constant
import com.android.weathertask.utils.FusedLocation
import com.google.android.gms.location.places.ui.PlacePicker
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : LocationBaseActivity(), HasSupportFragmentInjector, OnCompleteListener<Location> {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    @Inject
    lateinit var diapacheAndroidInjector: DispatchingAndroidInjector<Fragment>

    private var fusedLocation: FusedLocation? = null

    private val locationViewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this, factory).get(LocationViewModel::class.java)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return diapacheAndroidInjector
    }

    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun viewModelSetUp() {
        locationViewModel.selectedLocationLiveData.observe(this, Observer {
            switchFragment(WeatherFragment(), true, Constant.FRAGMENT_WEATHER)
        })
    }

    override fun viewSetUp() {
        checkLocationPermisison()

        switchFragment(LocationsFragment(), false, Constant.FRAGMENT_SPLASH)
        btn_select_location.setOnClickListener { openPlacePicker() }

        locationViewModel.getSavedLocations()
    }

    private fun openPlacePicker() {
        val builder = PlacePicker.IntentBuilder()
        startActivityForResult(builder.build(this), Constant.REQUEST_PLACE_PICKER)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == Constant.REQUEST_PLACE_PICKER) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                val place = PlacePicker.getPlace(this, data)
                locationViewModel.saveLocation(
                    place.id,
                    place.name.toString(),
                    place.latLng.latitude.toString(),
                    place.latLng.longitude.toString()
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        fusedLocation?.setOnFusedLocationChangeListener(this.locationViewModel)
    }

    override fun onPause() {
        super.onPause()
        fusedLocation?.removeLocationUpdate()
    }

    override fun startFusedLocationObserver() {
        fusedLocation = FusedLocation(this)
        fusedLocation?.apply {
            setOnFusedLocationChangeListener(this@MainActivity.locationViewModel)
            if (hasLocationPermission()) {
                fusedLocationClient.getLastLocation()
                    ?.addOnCompleteListener(this@MainActivity, this@MainActivity)
            }
        }
    }

    override fun onComplete(task: Task<Location>) {
        if (task.isSuccessful) {
            locationViewModel.currentLocationLiveData.postValue(task.result)
        }
    }

    override fun onLocationPermissionRejected() {
        //no-op
    }
}
