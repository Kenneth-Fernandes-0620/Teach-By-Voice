package com.rosarycollege.utility;

import android.content.Context;
import android.net.ConnectivityManager;

public class NetworkStats {

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}
