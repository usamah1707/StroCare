package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.util

import android.Manifest

object Constants {
    const val ACTION_SET_REPETITIVE_ALARM = "ACTION_SET_REPETITIVE_ALARM"
    const val EXTRA_EXACT_ALARM_TIME = "EXTRA_EXACT_ALARM_TIME"
    const val READ_CONTACT_PERMISSION = Manifest.permission.READ_CONTACTS
    const val READ_CONTACT_PERMISSION_CODE = 111
    const val SMS_PERMISSION: String = Manifest.permission.SEND_SMS
    const val SMS_REQUEST_CODE = 112

    init {
        System.loadLibrary("cpp_lib")
    }
    external fun CallStringMethod(): String

    var APP_NAME = CallStringMethod()


}