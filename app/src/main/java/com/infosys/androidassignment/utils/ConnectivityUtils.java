package com.infosys.androidassignment.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectivityUtils {

    /**
     * Check if internet connection is available or not
     *
     * @param context Context
     * @return true/false
     */
    public boolean isNetworkAvailable(Context context) {
        try {
            // retrieve a ConnectivityManager for handling management of network connections.
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            // check if connectivity manager is null
            if (connectivityManager != null) {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                return activeNetworkInfo != null && activeNetworkInfo.isConnected();
            } else {
                // connectivity manager is null return false
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // no internet connectivity
        return false;
    }
}
