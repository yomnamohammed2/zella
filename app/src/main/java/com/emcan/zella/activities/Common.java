package com.emcan.zella.activities;

import android.content.Context;
import android.location.Location;
import android.preference.PreferenceManager;

public class Common {
    private static final String KEY_REQUESTING_LOCATION_UPDATES = "LocationUpdatesEnabled";

    public static String getLocationText(Location mLocation) {
        return mLocation==null?"Unkown Location": new StringBuilder().append(mLocation.getLatitude())
                .append(", ")
                .append(mLocation.getLongitude()).toString();
    }

    public static void setRequestingLocationUpdates(Context context, boolean b) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putBoolean(KEY_REQUESTING_LOCATION_UPDATES,b).apply();
    }

    public static boolean requestingLocationUpdates(Context context) {
       return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(KEY_REQUESTING_LOCATION_UPDATES,false);
    }
}
