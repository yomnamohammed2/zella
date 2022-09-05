package com.emcan.zella.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.emcan.zella.Api_Service;
import com.emcan.zella.Beans.Order_respose;
import com.emcan.zella.Beans.Payment_result;
import com.emcan.zella.Config;
import com.emcan.zella.R;
import com.emcan.zella.SharedPrefManager;

import java.util.Locale;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;

public class Payment2 extends AppCompatActivity {


    PopupWindow popupWindow;
    String cart_id,address_id;
    String total;
    String order_id,reorder;
    String deliver_id;
    String js,ju;
    String branch_id;
    String lang;
    String session_id,discount_code;
    ProgressBar progressBar;
    boolean use_points,applied;


    WebView webView;

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

        setContentView(R.layout.activity_payment2);

        cart_id=getIntent().getStringExtra("cart_id");
        address_id=getIntent().getStringExtra("address_id");
        deliver_id=getIntent().getStringExtra("deliver_id");
        total=getIntent().getStringExtra("total");

        order_id=getIntent().getStringExtra("order_id");
        reorder=getIntent().getStringExtra("reorder");
        discount_code=getIntent().getStringExtra("discount_code");
        applied=getIntent().getBooleanExtra("applied",false);

        use_points=getIntent().getBooleanExtra("use_points",false);

        webView=(WebView) findViewById(R.id.pdf);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        webView.getSettings().setJavaScriptEnabled(true);

        long min = 7000000000L;
        long max = 8000000000L;

        Random random = new Random();
        long n = random.nextLong() % (max - min) + max;
        webView.loadUrl("http://chicket.emcan-group.com/credit_payment/init.php?amount="+total+"&client_id="+
        SharedPrefManager.getInstance(getApplicationContext()).getUser().getClient_id()+"&orderID="+n);

//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
//
//
//        webView.getSettings().setDomStorageEnabled(true);
//        webView.getSettings().setDatabaseEnabled(true);

//        webView.getSettings().setMinimumFontSize(1);
//        webView.getSettings().setMinimumLogicalFontSize(1);
//        WebSettings mWebSettings = webView.getSettings();
//        mWebSettings.setSupportZoom(false);
//        mWebSettings.setAllowFileAccess(true);
//        mWebSettings.setAllowContentAccess(true);


        webView.setWebViewClient(new Payment2.MyWebViewClient());
    }


    private class MyWebViewClient extends WebViewClient {


        @Override
        public void onPageFinished(WebView view, String url) {

            super.onPageFinished(view, url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d("kkkk",url);
            String str2="sessionVersion";

            String str1="checkout";
            String str3="entry";

            Uri uri = Uri.parse(url);
            if(url.toLowerCase().contains(str3.toLowerCase())) {
                webView.loadUrl(url);
            }

            if(url.toLowerCase().contains(str1.toLowerCase()))
            {
                progressBar.setVisibility(View.GONE);

                webView.loadUrl(url);
                String[] cuts=url.split("/");
                session_id=url.substring((url.indexOf("S")),url.length());

                Log.d("lll",session_id);
                SharedPreferences sharedPreferences=getSharedPreferences("pref1", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("session",session_id);
                editor.apply();
            }

            if(url.toLowerCase().contains(str2.toLowerCase())) {

                check_result();
                }



            return true;
        }
    }

    public void confirm(){

        Api_Service requestInterface = Config.getClient().create(Api_Service.class);
        Log.d("jjjjjjjj", address_id + "/" + cart_id );

        Order_respose.Order_model order=new Order_respose.Order_model();
        order.setCart_id(cart_id);
        order.setClient_id(SharedPrefManager.
                getInstance(getApplicationContext()).getUser().getClient_id());
        order.setPayment("credit");
        order.setClient_address_id(address_id);
        order.setDeliver_id(deliver_id);
        order.setLang(lang);
        SharedPreferences shared = getSharedPreferences("pref1", MODE_PRIVATE);
        session_id=shared.getString("session","");
        order.setSession_id(session_id);
        order.setDevice_type("android");

        if(deliver_id.equals("1")){
            order.setBranch_id("");
        }else{
//            branch_name=getIntent().getStringExtra("branch_name");
            branch_id=getIntent().getStringExtra("branch_id");
            order.setBranch_id(branch_id);
        }
        if(use_points){
            order.setUse_points("1");
        }
        else{
            order.setUse_points("0");
        }
        if(applied){
            order.setDiscount_code(discount_code);
        }
        else{
            order.setDiscount_code("");
        }
        order.setMobile_version(SharedPrefManager.getInstance(Payment2.this).getMobileVersion());


        Call<Order_respose> response1 = requestInterface.confirm_order2(order);
        response1.enqueue(new Callback<Order_respose>() {
            @Override
            public void onResponse(Call<Order_respose> call, retrofit2.Response<Order_respose> response) {
                final Order_respose resp = response.body();
                if (resp != null) {
                    if (resp.getSuccess() == 1) {

                      //  Toast.makeText(getApplicationContext(), getResources().getString(R.string.success), Toast.LENGTH_SHORT).show();
                        SharedPrefManager.getInstance(getApplicationContext()).reset_Cart();
                        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View customView = inflater.inflate(R.layout.message_popup, null);
                popupWindow = new PopupWindow(
                        customView,
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        //   700,
                        LinearLayout.LayoutParams.MATCH_PARENT, true
                );

                Button ok = customView.findViewById(R.id.ok);
                TextView text = customView.findViewById(R.id.text);
                text.setText(getResources().getString(R.string.success));

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("type", "order");
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                    }
                });
                findViewById(R.id.container).post(new Runnable() {
                    public void run() {
                        popupWindow.showAtLocation(findViewById(R.id.container), Gravity.CENTER, 0, 0);
                    }
                });




                    } else {

                        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                        View customView = inflater.inflate(R.layout.message_popup, null);
                        popupWindow = new PopupWindow(
                                customView,
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                //   700,
                                LinearLayout.LayoutParams.MATCH_PARENT, true
                        );

                        Button no1 = customView.findViewById(R.id.ok);
                        TextView text = customView.findViewById(R.id.text);
                        text.setText(resp.getMessage());
                        no1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                popupWindow.dismiss();
                            }
                        });
                        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
                    }
                }
            }


            @Override
            public void onFailure(Call<Order_respose> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void check_result(){
        Api_Service requestInterface = Config.getClient_payment().create(Api_Service.class);
        SharedPreferences shared = getSharedPreferences("pref1", MODE_PRIVATE);
        session_id=shared.getString("session","");
        Log.d("sssssss",session_id+"          "+SharedPrefManager.
                getInstance(getApplicationContext()
                ).getUser().getClient_id());
        Call<Payment_result> response1 = requestInterface.check_payment_result(SharedPrefManager.
                        getInstance(getApplicationContext()
                ).getUser().getClient_id(),
             session_id);
        response1.enqueue(new Callback<Payment_result>() {
            @Override
            public void onResponse(Call<Payment_result> call, retrofit2.Response<Payment_result> response) {
                final Payment_result resp = response.body();
                if (resp != null) {
                    if (resp.getSuccess() == 1) {
                            confirm();
                        }else{
                            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                            View customView = inflater.inflate(R.layout.message_popup, null);
                            popupWindow = new PopupWindow(
                                    customView,
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    //   700,
                                    LinearLayout.LayoutParams.MATCH_PARENT, true
                            );

                            Button ok = customView.findViewById(R.id.ok);
                            TextView text = customView.findViewById(R.id.text);
                            text.setText(getResources().getString(R.string.unsuccess));

                            ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(final View view) {
                                    popupWindow.dismiss();
                                    finish();
                                }
                            });
                            findViewById(R.id.container).post(new Runnable() {
                                public void run() {
                                    popupWindow.showAtLocation(findViewById(R.id.container), Gravity.CENTER, 0, 0);
                                }
                            });

                        }

                }
            }

            @Override
            public void onFailure(Call<Payment_result> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
