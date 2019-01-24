package com.android.weathertask.presantation.main

import android.location.Location
import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.android.weathertask.domain.usecase.GetSavedLocationsUseCase
import com.android.weathertask.domain.usecase.SaveLocationUseCase
import com.android.weathertask.presantation.base.BaseViewModel
import com.android.weathertask.presantation.base.OnFusedLocationChangeListener
import com.android.weathertask.presantation.mapper.LocationMapper
import com.android.weathertask.presantation.model.LocationModel
import com.android.weathertask.utils.SimpleResourceSubscriber
import javax.inject.Inject

class LocationViewModel @Inject internal constructor(
    private val locationMapper: LocationMapper,
    private val saveLocationUseCase: SaveLocationUseCase,
    private val getSavedLocationsUseCase: GetSavedLocationsUseCase
) : BaseViewModel(), OnFusedLocationChangeListener {

    val locationSavedLiveData = MutableLiveData<Any>()

    val currentLocationLiveData = MutableLiveData<Location?>()

    val locationsLiveData = MediatorLiveData<List<LocationModel>>()

    val selectedLocationLiveData = MutableLiveData<LocationModel>()

    val savedLocationsLiveData = MutableLiveData<List<LocationModel>?>()

    fun getSavedLocations() {
        getSavedLocationsUseCase.executeFlowable(SimpleResourceSubscriber({ savedLocations ->
            savedLocationsLiveData.postValue(locationMapper.transformLocationItems(savedLocations))
        }, { error ->
            Log.e(TAG, error?.message)
        }))
    }

    init {
        locationsLiveData.addSource(currentLocationLiveData) {
            addCurrentLocationToSavedLocations(it, savedLocationsLiveData.value)
        }

        locationsLiveData.addSource(savedLocationsLiveData) {
            addCurrentLocationToSavedLocations(currentLocationLiveData.value, it)
        }
    }

    fun setSelectedLocation(location: LocationModel?) {
        selectedLocationLiveData.postValue(location)
    }

    private fun addCurrentLocationToSavedLocations(currentLocation: Location?, savedLocations: List<LocationModel>?) {
        val locations = mutableListOf<LocationModel>()
        currentLocation?.let {
            locations.add(
                LocationModel(
                    "Current Location",
                    currentLocation.latitude.toString(),
                    currentLocation.longitude.toString()
                )
            )
        }

        savedLocations?.let {
            locations.addAll(savedLocations)
        }
        locationsLiveData.postValue(locations.toList())
    }

    fun saveLocation(id: String, name: String, latitude: String, longitude: String) {
        saveLocationUseCase.executeFlowable(SimpleResourceSubscriber({ saved ->
            locationSavedLiveData.postValue(saved)
        }, { error ->
            Log.e(TAG, error?.message)
        }), SaveLocationUseCase.Param(id, name, latitude, longitude))
    }

    override fun getUseCases() = listOf(getSavedLocationsUseCase)

    override fun onFusedLocationChange(location: Location?) {
        currentLocationLiveData.postValue(location)
    }

    companion object {
        const val TAG = "LocationViewModel"
    }
}