package com.marvinvogl.flavor

import android.content.Context

import com.google.android.gms.common.ConnectionResult as google
import com.google.android.gms.common.GoogleApiAvailability

import com.huawei.hms.api.ConnectionResult as huawei
import com.huawei.hms.api.HuaweiApiAvailability

class WrapperExample {
    companion object {
        private const val flavor = "Huawei Flavor"

        fun getFlavor(context: Context):String {
            val gms = GoogleApiAvailability.getInstance()
            val hms = HuaweiApiAvailability.getInstance()

            if (gms.isGooglePlayServicesAvailable(context) == google.SUCCESS) {
                return "$flavor with GMS"
            }
            if (hms.isHuaweiMobileServicesAvailable(context) == huawei.SUCCESS) {
                return "$flavor with HMS"
            }
            return flavor
        }
    }
}