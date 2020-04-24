package com.project.info.util

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.LiveData

/*
* Creating a broadcast receiver to listen for any state change.
*
* Register and unregister it with LiveData lifecycle.
* we’ll be notified about any state change, if we’ll observe this LiveData
 */

class ConnectionLiveData(private val context: Activity?) : LiveData<Boolean>() {

    override fun onActive() {
        super.onActive()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        context?.registerReceiver(networkReceiver, filter)
    }

    override fun onInactive() {
        super.onInactive()
        context?.unregisterReceiver(networkReceiver)
    }

    private val networkReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.extras != null) {
                var result = false
                postValue(result)
                val connectivityManager =
                    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

                val networkCapabilities = connectivityManager.activeNetwork ?: return
                val activeNetwork =
                    connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return
                result = when {
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
                postValue(result)
            }
        }
    }
}