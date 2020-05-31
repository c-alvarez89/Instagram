package com.meazza.instagram.common.permission

import android.Manifest
import android.app.Activity
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

class PermissionRequest(private val activity: Activity) {

    private val readExternalStorage = Manifest.permission.READ_EXTERNAL_STORAGE
    private val camera = Manifest.permission.CAMERA

    fun allPermissions(): PermissionState? {

        var result: PermissionState? = null

        Dexter.withContext(activity)
            .withPermissions(readExternalStorage, camera)
            .withListener(object : MultiplePermissionsListener {

                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    report?.let {

                        if (it.areAllPermissionsGranted()) result =
                            PermissionState.ALL_PERMISSIONS_GRANTED

                        for (permission in report.deniedPermissionResponses) {
                            when (permission.permissionName) {
                                readExternalStorage -> result = permissionDenied(permission)
                                camera -> result = permissionDenied(permission)
                            }
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?, token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }
            }).check()

        return result
    }

    private fun permissionDenied(permission: PermissionDeniedResponse): PermissionState? {
        return if (permission.isPermanentlyDenied) PermissionState.PERMANENTLY_DENIED
        else PermissionState.DENIED
    }
}
