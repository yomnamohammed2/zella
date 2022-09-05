package com.emcan.zella.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;


import com.emcan.zella.Api_Service;
import com.emcan.zella.Beans.User;
import com.emcan.zella.Config;
import com.emcan.zella.ConnectionDetection;
import com.emcan.zella.R;
import com.emcan.zella.SharedPrefManager;
import com.emcan.zella.Beans.Check;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;

public class Forget_Pass extends AppCompatActivity {

    ConnectionDetection connectionDetection;
    ProgressBar progressBar;
    String email_,password_;
    String lang,phone;
    Typeface typeface;
    EditText email;
    String country_code;
    Spinner spinner;

    @Override
    protected void onResume() {
        super.onResume();

        ////// for arabic view ///////////
       /* Resources res = getBaseContext().getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale("ar".toLowerCase())); // API 17+ only.
        res.updateConfiguration(conf, dm);
        /////////////////////////////////////////*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lang= SharedPrefManager.getInstance(this).getLang();
        if(lang.equals("ar")){
            Locale myLocale = new Locale("ar");
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
            conf.setLayoutDirection(new Locale("ar"));

        }else{
            Locale myLocale = new Locale("en");
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
            conf.setLayoutDirection(new Locale("en"));

        }

        setContentView(R.layout.activity_forget__pass);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

//        spinner=findViewById(R.id.country_spn);
//        ArrayList<String> countries=new ArrayList<>();
//
//        countries.add("+973");
//        countries.add("+966");

//        final CountryAdapter adapter=new CountryAdapter(Forget_Pass.this,countries);
//        spinner.setAdapter(adapter);


        email = (EditText) findViewById(R.id.phone);
        if(lang.equals("en")){
            typeface = ResourcesCompat.getFont(Forget_Pass.this, R.font.amaranth_bold);
            email.setGravity(Gravity.LEFT);
        }
        if(lang.equals("ar")){
            typeface= ResourcesCompat.getFont(Forget_Pass.this, R.font.bein_black);
            email.setGravity(Gravity.RIGHT);
        }


        Button action = (Button) findViewById(R.id.login);
        email.setTypeface(typeface);
        action.setTypeface(typeface);

        connectionDetection = new ConnectionDetection(getApplicationContext());
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email_ = email.getText().toString().trim();

                if (email_.isEmpty() || email_.length() == 0 || email_.equals("") || email_ == null) {
                    email.requestFocus();

                } else {
                    login();
                        }


            }
        });
    }

    public void login(){

        if(connectionDetection.isConnected()){
            progressBar.setVisibility(View.VISIBLE);
            Api_Service requestInterface= Config.getClient().create(Api_Service.class);
            User user=new User();
            user.setPhone(email_);
            user.setLang(lang);
            Call<Check> response1 = requestInterface.checkExist(email_,lang);
            response1.enqueue(new Callback<Check>() {

                @Override
                public void onResponse(Call<Check> call, retrofit2.Response<Check> response) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Check resp = response.body();
                    if (resp != null) {
                        if (resp.getSuccess() == 1) {

                            if(resp.getExist()==1) {

                                Intent i = new Intent(Forget_Pass.this, Phone_verfication.class);
                                i.putExtra("fl", 1);
                                i.putExtra("client_id", resp.getClient_id());
                                i.putExtra("phone", email_);
                                i.putExtra("phone_code", country_code);
                                startActivity(i);
                            }else {


                                Toast.makeText(getApplicationContext(),resp.getMessage(),Toast.LENGTH_LONG).show();

                            }

                            }
                            else{
                            Toast.makeText(getApplicationContext(),resp.getMessage(),Toast.LENGTH_LONG).show();

                        }
                        }else {

                    }

                }

                @Override
                public void onFailure(Call<Check> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.errortryagain), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    Log.d("respooooonse", t.toString());

                }
            });

        }else{
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.networkerror),Toast.LENGTH_SHORT).show();
        }

    }
}
