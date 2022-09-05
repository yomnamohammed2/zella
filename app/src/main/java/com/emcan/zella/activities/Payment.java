package com.emcan.zella.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.emcan.zella.Api_Service;
import com.emcan.zella.Beans.Last_order_pojo;
import com.emcan.zella.Beans.Order_respose;
import com.emcan.zella.Beans.Sub_Cat_Images_Model;
import com.emcan.zella.Config;
import com.emcan.zella.SharedPrefManager;
import com.emcan.zella.R;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;

public class Payment extends AppCompatActivity {


    String payment_id;
    PopupWindow popupWindow;
    String cart_id,address_id;
    String total;
    String order_id,reorder;
    String deliver_id;
    String js,ju;
    String branch_id;
    String lang;
    final String FILE="pref1";
    String answer;
    boolean use_points,applied;
    String discount_code;

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

        setContentView(R.layout.activity_payment);


        cart_id=getIntent().getStringExtra("cart_id");
        address_id=getIntent().getStringExtra("address_id");
        deliver_id=getIntent().getStringExtra("deliver_id");
        total=getIntent().getStringExtra("total");
        discount_code=getIntent().getStringExtra("discount_code");
        applied=getIntent().getBooleanExtra("applied",false);
        use_points=getIntent().getBooleanExtra("use_points",false);

        order_id=getIntent().getStringExtra("order_id");
        reorder=getIntent().getStringExtra("reorder");


        WebView webView=(WebView) findViewById(R.id.pdf);
        webView.getSettings().setJavaScriptEnabled(true);

//        webView.loadUrl("http://chicket.emcan-group.com/debit/init.php?amount="+total);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);


        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);

        webView.getSettings().setMinimumFontSize(1);
        webView.getSettings().setMinimumLogicalFontSize(1);
        WebSettings mWebSettings = webView.getSettings();
        mWebSettings.setSupportZoom(false);
        mWebSettings.setAllowFileAccess(true);
        mWebSettings.setAllowContentAccess(true);

        js = "document.getElementById('submit').click();";
        ju= "document.getElementById('PaymentID').value;";


        webView.setWebViewClient(new MyWebViewClient());
    }


    private class MyWebViewClient extends WebViewClient {


        @Override
        public void onPageFinished(WebView view, String url) {



            view.evaluateJavascript(ju, new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String s) {
                    if(s!=null&&!s.equals("null")){
                        payment_id=s;
                        Log.d("llll",s);

                    }
                }
            });
            view.evaluateJavascript(js, new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String s) {
//                    Log.d("llll",s);
                }
            });

            if(url.contains("PaymentID"))
            {
                //http://elmia.emcan-group.com/app/payment-debit/Success.php?paymentid=7562158482392190&transactionType=PaymentTran
                Uri urii = Uri.parse(url);
                String chapter = urii.getQueryParameter("PaymentID");
                SharedPreferences sharedPreferences=getSharedPreferences(FILE, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("paymentId",chapter);
                editor.apply();
                //Toast.makeText(PaymentActivity.this,"susse",Toast.LENGTH_SHORT).show();
              //  Log.d("success",chapter);

            }
            super.onPageFinished(view, url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d("kkkk",url);
            String str2="error";
            String str1="failed";
            String str3="success";


            Uri uri = Uri.parse(url);


            if(url.toLowerCase().contains(str2.toLowerCase()))
            {

                String chapter = uri.getQueryParameter("errortext");

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
                text.setText(getResources().getString(R.string.unsuccess)+"\n"+chapter);

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
            if(url.toLowerCase().contains(str1.toLowerCase()))
            {

                String chapter = uri.getQueryParameter("errortext");

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
                text.setText(getResources().getString(R.string.unsuccess)+"\n"+chapter);

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

            if(url.toLowerCase().contains(str3.toLowerCase()))
            {

                if(reorder!=null&&reorder.equals("1")){
                    re_order();
                }
                else {
                    confirm();
                }
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
        order.setPayment("debit");
        order.setClient_address_id(address_id);
        order.setDeliver_id(deliver_id);
        order.setLang(lang);
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
        order.setMobile_version(SharedPrefManager.getInstance(Payment.this).getMobileVersion());
        if(applied){
            order.setDiscount_code(discount_code);
        }
        else{
            order.setDiscount_code("");
        }
        Call<Order_respose> response1 = requestInterface.confirm_order2(order);
        response1.enqueue(new Callback<Order_respose>() {
            @Override
            public void onResponse(Call<Order_respose> call, retrofit2.Response<Order_respose> response) {
                final Order_respose resp = response.body();
                if (resp != null) {
                    if (resp.getSuccess() == 1) {

                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.success), Toast.LENGTH_SHORT).show();
                        SharedPrefManager.getInstance(getApplicationContext()).reset_Cart();

                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {

                                add_paymnet(resp.getProduct().get(0).getOrder_id());
                            }
                        });

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("type", "order");
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);


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

    private void add_paymnet(String order_id){
        Api_Service requestInterface = Config.getClient().create(Api_Service.class);
        Log.d("jjjjjjjj", total + "/" + order_id );
        SharedPreferences shared = getSharedPreferences(FILE, MODE_PRIVATE);
        answer=shared.getString("paymentId","");
        Call<Sub_Cat_Images_Model> response1 = requestInterface.add_payment(SharedPrefManager.getInstance(getApplicationContext()
                ).getUser().getClient_id(),
                order_id, total,"success",answer);
        response1.enqueue(new Callback<Sub_Cat_Images_Model>() {
            @Override
            public void onResponse(Call<Sub_Cat_Images_Model> call, retrofit2.Response<Sub_Cat_Images_Model> response) {
                final Sub_Cat_Images_Model resp = response.body();
                if (resp != null) {
                    if (resp.getSuccess() == 1) {
                    }
                }
            }

            @Override
            public void onFailure(Call<Sub_Cat_Images_Model> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private  void re_order() {

        Api_Service requestInterface = Config.getClient().create(Api_Service.class);
        Log.d("jjjjjjjj", address_id + "/" + order_id);

        Order_respose.Order_model order=new Order_respose.Order_model();
        order.setOrder_id(order_id);
        order.setClient_id(SharedPrefManager.
                getInstance(getApplicationContext()).getUser().getClient_id());
        order.setPayment("online");
        order.setClient_address_id(address_id);
        order.setDeliver_id(deliver_id);
        if(deliver_id.equals("1")){
            order.setBranch_id("");
        }else {
            branch_id=getIntent().getStringExtra("branch_id");
            order.setBranch_id(branch_id);

        }

        Call<Last_order_pojo> response1 = requestInterface.re_Order(order);
        response1.enqueue(new Callback<Last_order_pojo>() {
            @Override
            public void onResponse(Call<Last_order_pojo> call, retrofit2.Response<Last_order_pojo> response) {
                Last_order_pojo resp = response.body();
                if (resp != null) {
                    if (resp.getSuccess() == 1) {
                        final String order_iiii= resp.getProduct().get(0).getOrder_id();
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.resuccess), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("type", "order");
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);

                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {

                                add_paymnet(order_iiii);
                            }
                        });

                    }
                }
            }


            @Override
            public void onFailure(Call<Last_order_pojo> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.errortryagain), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
