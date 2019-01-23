package com.android.weathertask.presantation.locations

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.android.weathertask.R
import com.android.weathertask.di.Injectable
import com.android.weathertask.presantation.base.BaseFragment
import com.android.weathertask.presantation.main.LocationViewModel
import com.android.weathertask.presantation.model.LocationModel
import kotlinx.android.synthetic.main.fragment_locations.*
import javax.inject.Inject

class LocationsFragment : BaseFragment(), Injectable, OnLocationItemSelectedListener {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private val locationViewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(requireActivity(), factory).get(LocationViewModel::class.java)
    }

    private val selectedLocationLiveData by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(requireActivity()).get(LocationViewModel::class.java)
    }

    val locationAdapter by lazy(LazyThreadSafetyMode.NONE) {
        LocationAdapter(locationViewModel.locationsLiveData.value, this)
    }

    override fun viewModelSetup() {
        locationViewModel.locationsLiveData.observe(this, Observer {
            locationAdapter.locations = it
        })
    }

    override fun viewSetUp(view: View) {
        recyclerView.adapter = locationAdapter
    }

    override fun onLocationItemSelected(locationModal: LocationModel?) {
        selectedLocationLiveData.setSelectedLocation(locationModal)
    }

    override fun getContentView(): Int {
        return R.layout.fragment_locations
    }
}
