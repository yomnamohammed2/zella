package com.emcan.zella.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.emcan.zella.R;
import com.emcan.zella.SharedPrefManager;



public class Splash2 extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;
    final String FILE="pref1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        SharedPrefManager.getInstance(getApplicationContext()).Adv_Cancel(null);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Crashlytics.getInstance().crash(); // Force a crash


                    Intent intent = new Intent(Splash2.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);

            }
        }, SPLASH_TIME_OUT);

    }
}
