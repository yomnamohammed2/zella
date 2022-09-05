package com.emcan.zella;

import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class ConnectionDetection {
    Context context;
    public ConnectionDetection(Context context) {
this.context=context;
    }
    public boolean isConnected(){
        ConnectivityManager connectivityManager=(ConnectivityManager) context.getSystemService(Service.CONNECTIVITY_SERVICE);

    if(connectivityManager !=null){

        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if(networkInfo !=null){
            if(networkInfo.getState()== NetworkInfo.State.CONNECTED){
return true;
            }
        }
    }
return  false;
    }
}
