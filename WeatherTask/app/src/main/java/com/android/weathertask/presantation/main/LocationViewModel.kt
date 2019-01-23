package com.android.weathertask.presantation.main

import android.location.Location
import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.android.weathertask.domain.model.LocationItem
import com.android.weathertask.domain.usecase.GetSavedLocationsUseCase
import com.android.weathertask.domain.usecase.SaveLocationUseCase
import com.android.weathertask.presantation.base.BaseViewModel
import com.android.weathertask.presantation.base.OnFusedLocationChangeListener
import com.android.weathertask.presantation.mapper.LocationMapper
import com.android.weathertask.presantation.model.LocationModel
import io.reactivex.subscribers.ResourceSubscriber
import javax.inject.Inject

class LocationViewModel @Inject internal constructor(
    private val locationMapper: LocationMapper,
    val saveLocationUseCase: SaveLocationUseCase,
    private val getSavedLocationsUseCase: GetSavedLocationsUseCase
) :
    BaseViewModel(), OnFusedLocationChangeListener {

    val locationSavedLiveData = MutableLiveData<Any>()

    val currentLocationLiveData = MutableLiveData<Location?>()

    val locationsLiveData = MediatorLiveData<List<LocationModel>>()

    val selectedLocationLiveData = MutableLiveData<LocationModel>()

    val savedLocationsLiveData = MutableLiveData<List<LocationModel>?>()

    fun getSavedLocations() {
        getSavedLocationsUseCase.executeFlowable(object : ResourceSubscriber<List<LocationItem>>() {
            override fun onComplete() {
                //no-op
            }

            override fun onNext(savedLocations: List<LocationItem>?) {
                savedLocationsLiveData.postValue(locationMapper.transformLocationItems(savedLocations))
            }

            override fun onError(t: Throwable?) {
                Log.e(TAG, t?.message)
            }

        })
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
        if (currentLocation != null) {
            locations.add(
                LocationModel(
                    "Current Location",
                    currentLocation.latitude.toString(),
                    currentLocation.longitude.toString()
                )
            )
        }

        if (savedLocations != null) {
            locations.addAll(savedLocations)
        }

        locationsLiveData.postValue(locations.toList())
    }

    fun saveLocation(id: String, name: String, latitude: String, longitude: String) {
        saveLocationUseCase.executeFlowable(object : ResourceSubscriber<Any>() {
            override fun onNext(saved: Any) {
                locationSavedLiveData.postValue(saved)
                getSavedLocations()
            }

            override fun onError(t: Throwable) {
                Log.e(TAG, t.message)
            }

            override fun onComplete() {
                //no-op
            }
        }, SaveLocationUseCase.Param(id, name, latitude, longitude))
    }

    override fun onFusedLocationChange(location: Location?) {
        currentLocationLiveData.postValue(location)
    }

    override fun clearSubscriptions() {
        saveLocationUseCase.clearAllSubscription()
    }

    companion object {
        const val TAG = "LocationViewModel"
    }
}