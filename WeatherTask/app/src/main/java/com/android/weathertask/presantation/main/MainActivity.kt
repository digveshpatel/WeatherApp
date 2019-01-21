package com.android.weathertask.presantation.main

import android.location.Location
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.android.weathertask.R
import com.android.weathertask.presantation.base.LocationBaseActivity
import com.android.weathertask.presantation.splash.SplashFragment
import com.android.weathertask.utils.FusedLocation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MainActivity : LocationBaseActivity(), HasSupportFragmentInjector, OnCompleteListener<Location> {

    @Inject
    lateinit var diapacheAndroidInjector: DispatchingAndroidInjector<Fragment>

    private var fusedLocation: FusedLocation? = null
    private var locationViewModel: LocationViewModel? = null


    override fun getLayout(): Int {
        return R.layout.activity_main;
    }

    override fun viewModelSetUp() {
        locationViewModel = ViewModelProviders.of(this).get(LocationViewModel::class.java)
    }

    override fun viewSetUp() {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, SplashFragment()).commit()
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return diapacheAndroidInjector
    }


    override fun onResume() {
        super.onResume()
        if (fusedLocation != null)
            fusedLocation!!.setOnFusedLocationChangeListener(this.locationViewModel!!)
    }

    override fun onPause() {
        super.onPause()
        if (fusedLocation != null)
            fusedLocation?.removeLocationUpdate()
    }

    override fun startFusedLocationTimer() {

        fusedLocation = FusedLocation(this)
        fusedLocation!!.setOnFusedLocationChangeListener(this.locationViewModel!!)
        if (fusedLocation!!.checkPermission()) {
            fusedLocation!!.fusedLocationClient.getLastLocation().addOnCompleteListener(this, this)

        }
    }

    override fun onLocationPermissionRejected() {
        locationViewModel?.location?.postValue(null)
    }

    override fun onComplete(task: Task<Location>) {
        if (task.isSuccessful())
            locationViewModel?.location?.postValue(task.getResult())
        else
            locationViewModel?.location?.postValue(null)
    }

}