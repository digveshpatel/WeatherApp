package com.android.weathertask.presantation.base

import android.Manifest
import android.content.Intent
import android.content.IntentSender
import android.net.Uri
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import com.android.weathertask.R
import com.android.weathertask.utils.Constant.REQUEST_CHECK_SETTINGS
import com.android.weathertask.utils.Constant.REQUEST_LOCATION_PERMISSION
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.tasks.OnSuccessListener
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

abstract class LocationBaseActivity : BaseActivity(), PermissionListener {



    fun checkLocationPermisison() {
        Dexter.withActivity(this)
            .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            .withListener(this).check()
    }

    override fun onPermissionGranted(response: PermissionGrantedResponse) {
        // permission is granted
        showLocationEnableDialog()
    }

    override fun onPermissionDenied(response: PermissionDeniedResponse) {
        // check for permanent denial of permission
        if (response.isPermanentlyDenied) {
            showSettingsDialog()
        } else {
            onLocationPermissionRejected()
        }
    }

    override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest, token: PermissionToken) {
        showPermissionExplainDialog(token)
    }

    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private fun showSettingsDialog() {
        AlertDialog.Builder(this@LocationBaseActivity)
            .setTitle(R.string.need_permissions)
            .setMessage(getString(R.string.msg_location_permission_explain, getString(R.string.app_name)))
            .setPositiveButton(R.string.goto_settings) { dialog, which ->
                dialog.dismiss()
                openSettings()
            }
            .setNegativeButton(R.string.cancel) { dialog, which ->
                dialog.cancel()
                onLocationPermissionRejected()
            }
            .setCancelable(false)
            .show()
    }

    /**
     * Showing Alert Dialog with permission explanation
     */
    private fun showPermissionExplainDialog(token: PermissionToken) {
        AlertDialog.Builder(this@LocationBaseActivity)
            .setTitle(R.string.need_permissions)
            .setMessage(getString(R.string.msg_location_permission_explain, getString(R.string.app_name)))
            .setPositiveButton(R.string.grant) { dialog, which ->
                dialog.dismiss()
                token.continuePermissionRequest()
            }
            .setNegativeButton(R.string.cancel) { dialog, which ->
                dialog.cancel()
                token.cancelPermissionRequest()
                onLocationPermissionRejected()
            }
            .setCancelable(false)
            .show()
    }
    // navigating user to app settings

    private fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        if (intent.resolveActivity(packageManager) != null)
            startActivityForResult(intent, REQUEST_LOCATION_PERMISSION)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_LOCATION_PERMISSION -> checkLocationPermisison()
        }
    }

    private fun showLocationEnableDialog() {
        val locationRequest = LocationRequest()
        locationRequest.interval = 40000
        locationRequest.fastestInterval = 20000
        locationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val client = LocationServices.getSettingsClient(this@LocationBaseActivity)
        val task = client.checkLocationSettings(builder.build())
        task.addOnSuccessListener(this, object : OnSuccessListener<LocationSettingsResponse> {
            override fun onSuccess(locationSettingsResponse: LocationSettingsResponse) {
                println("showLocationEnableDialog:onSuccess: $locationSettingsResponse")

            }
        })

        task.addOnFailureListener { e ->
            if (e is ResolvableApiException) {
                try {
                    e.startResolutionForResult(this@LocationBaseActivity, REQUEST_CHECK_SETTINGS)
                } catch (sendEx: IntentSender.SendIntentException) {
                    print("LocatioDialog SendIntentException : $sendEx")
                }

            }
        }

        startFusedLocationObserver()
    }

    protected abstract fun startFusedLocationObserver()

    /**
     * called after [.checkLocationPermisison] is completed
     * and user didn't grant location permission
     */
    protected abstract fun onLocationPermissionRejected()
}
