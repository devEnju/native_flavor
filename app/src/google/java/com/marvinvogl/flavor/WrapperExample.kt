package com.marvinvogl.flavor

import android.content.Context

import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability

class WrapperExample {
    companion object {
        private const val flavor = "Google Flavor"

        fun getFlavor(context: Context):String {
            val gms = GoogleApiAvailability.getInstance()

            if (gms.isGooglePlayServicesAvailable(context) == ConnectionResult.SUCCESS) {
                return "$flavor with GMS"
            }
            return flavor
        }
    }
}