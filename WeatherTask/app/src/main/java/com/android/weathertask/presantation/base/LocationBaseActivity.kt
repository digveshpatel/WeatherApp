package com.android.weathertask.presantation.base

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import com.android.weathertask.R
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

abstract class LocationBaseActivity : BaseActivity(), PermissionListener {

    val REQUEST_LOCATION_PERMISSION = 6

    fun checkLocationPermisison() {
        Dexter.withActivity(this)
            .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            .withListener(this).check()
    }

    override fun onPermissionGranted(response: PermissionGrantedResponse) {
        // permission is granted
        //        showLocationEnableDialog();
        startFusedLocationTimer()
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
            .setPositiveButton(R.string.goto_settings, DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
                openSettings()
            })
            .setNegativeButton(R.string.cancel, DialogInterface.OnClickListener { dialog, which ->
                dialog.cancel()
                onLocationPermissionRejected()
            })
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
            .setPositiveButton(R.string.grant, DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
                token.continuePermissionRequest()
            })
            .setNegativeButton(R.string.cancel, DialogInterface.OnClickListener { dialog, which ->
                dialog.cancel()
                token.cancelPermissionRequest()
                onLocationPermissionRejected()
            })
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

     protected override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_LOCATION_PERMISSION -> checkLocationPermisison()
        }
    }

    /* public void showLocationEnableDialog() {
         LocationRequest locationRequest = Utility.createLocationRequest();
         LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
         SettingsClient client = LocationServices.getSettingsClient(LocationBaseActivity.this);
        *//* Task<LocationSettingsResponse> task =*//* *//*client.checkLocationSettings(builder.build());*//*
     *//*task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                Utility.log("showLocationEnableDialog", "onSuccess : " + locationSettingsResponse);
            }
        });*//*

     *//*  task.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e instanceof ResolvableApiException) {
                            try {
                                ResolvableApiException resolvable = (ResolvableApiException) e;
                                resolvable.startResolutionForResult(LocationBaseActivity.this, KeyClass.REQUEST_CHECK_SETTINGS);
                            } catch (IntentSender.SendIntentException sendEx) {
                                Utility.log("LocatioDialog", "SendIntentException : " + sendEx);
                            }
                        }

                    }
                });*//*

        startFusedLocationTimer();
    }
*/
    protected abstract fun startFusedLocationTimer()

    /**
     * called after [.checkLocationPermisison] is completed
     * and user didn't grant location permission
     */
    protected abstract fun onLocationPermissionRejected()
}
