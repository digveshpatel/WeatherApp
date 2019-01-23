package com.android.weathertask.presantation.weather

import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.android.weathertask.R
import com.android.weathertask.di.Injectable
import com.android.weathertask.presantation.base.BaseActivity
import com.android.weathertask.presantation.base.BaseFragment
import com.android.weathertask.presantation.main.LocationViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.frgament_weather.*
import javax.inject.Inject


class WeatherFragment : BaseFragment(), Injectable {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private val locationViewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(requireActivity(), factory).get(LocationViewModel::class.java)
    }
    private val weatherViewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this, factory).get(WeatherViewModel::class.java)
    }

    override fun getContentView(): Int {
        return R.layout.frgament_weather
    }

    override fun viewModelSetup() {
        weatherViewModel.failedLiveData.observe(this, Observer {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
            (activity as BaseActivity).popBackStack()
        })
        val locationModel = locationViewModel.selectedLocationLiveData.value
        if (locationModel != null) {
            weatherViewModel.getWeatherOfLocation(locationModel.lat, locationModel.long)
        }
        weatherViewModel.weatherLiveData.observe(this, Observer { weatherModel ->
            Glide.with(requireActivity())
                .load(weatherModel?.iconUrl)
                .into(iv_weather)
            tv_weather_description.text = weatherModel?.description
            tv_temperature.text = weatherModel?.temperature
            tv_location.text = weatherModel?.location
            tv_humidity.text = weatherModel?.humidity
        })
    }

    override fun viewSetUp(view: View) {
        //no-op
    }
}



