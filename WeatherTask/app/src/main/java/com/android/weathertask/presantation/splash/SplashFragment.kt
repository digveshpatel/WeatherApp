package com.android.weathertask.presantation.splash

import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.android.weathertask.R
import com.android.weathertask.di.Injectable
import com.android.weathertask.presantation.base.BaseFragment
import com.android.weathertask.presantation.main.LocationViewModel

class SplashFragment() : BaseFragment(), Injectable {

    private var locationViewModel: LocationViewModel? = null

    override fun getContentView(): Int {
        return R.layout.fragment_splash
    }

    override fun viewSetUp() {
        locationViewModel = ViewModelProviders.of(this).get(LocationViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
        if (activity != null)
            activity!!.getWindow().decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                    // Set the content to appear under the system bars so that the
                    // content doesn't resize when the system bars hide and show.
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    // Hide the nav bar and status bar
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    override fun onPause() {
        super.onPause()
        if (activity != null) {
            activity!!.getWindow().decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        }
    }

}