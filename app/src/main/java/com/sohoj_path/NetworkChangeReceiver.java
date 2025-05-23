package com.sohoj_path;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class NetworkChangeReceiver extends BroadcastReceiver {
    private static boolean wasConnected = true;

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isConnected = isInternetConnected(context);

        if (isConnected && !wasConnected) {
            Toast.makeText(context, "Internet Connected", Toast.LENGTH_SHORT).show();
            wasConnected = true;
        } else if (!isConnected && wasConnected) {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
            wasConnected = false;
        }
    }

    private boolean isInternetConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnected();
        }
        return false;
    }
}
