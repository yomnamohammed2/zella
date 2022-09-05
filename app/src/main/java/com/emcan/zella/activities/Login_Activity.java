package com.emcan.zella.activities;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.emcan.zella.Api_Service;
import com.emcan.zella.Beans.Cart_Response2;
import com.emcan.zella.Beans.Login_response;
import com.emcan.zella.Beans.User;
import com.emcan.zella.Config;
import com.emcan.zella.ConnectionDetection;
import com.emcan.zella.R;
import com.emcan.zella.SharedPrefManager;
import com.emcan.zella.fragments.Login_Fragment;
import com.emcan.zella.fragments.Slider;
import com.facebook.login.LoginFragment;

import java.util.ArrayList;
import java.util.Locale;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import retrofit2.Call;
import retrofit2.Callback;

public class Login_Activity extends AppCompatActivity {

    ConnectionDetection connectionDetection;
    ProgressBar progressBar;
    String email_, password_;
    ImageView imageView;
    RelativeLayout slogan1;
    TextView title;
    String lang;
    Typeface typeface;
//    final String FILE="pref1";
    FragmentManager fragmentManager;
    CheckBox driver;

    int position=0;
    Context context;

    @Override
    protected void onResume() {
        super.onResume();

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

        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        fragmentManager=getSupportFragmentManager();

        position=getIntent().getIntExtra("pos",0);
//        title=findViewById(R.id.title);

//
//        TextView signup = (TextView) findViewById(R.id.sign_up);
//        TextView forget = (TextView) findViewById(R.id.forget);
//
//        final EditText email = (EditText) findViewById(R.id.phone);
//        final EditText password = (EditText) findViewById(R.id.password);
//        final CheckBox remember_me = (CheckBox) findViewById(R.id.remember);
//        Button action = (Button) findViewById(R.id.login);
//        final TextView slogan =  findViewById(R.id.slogan);
//        final TextView slogan2 =  findViewById(R.id.slogan2);
//        final TextView warning1 = (TextView) findViewById(R.id.warning1);
//        final TextView warning2 = (TextView) findViewById(R.id.warning2);
//        warning1.setVisibility(View.INVISIBLE);
//        warning2.setVisibility(View.INVISIBLE);
//
//        if(lang.equals("en")){
//            typeface = ResourcesCompat.getFont(Login_Activity.this, R.font.amaranth_bold);
//            email.setGravity(Gravity.LEFT);
//            password.setGravity(Gravity.LEFT);
//        }
//        if(lang.equals("ar")){
//            typeface= ResourcesCompat.getFont(Login_Activity.this, R.font.bein_black);
//
//            email.setGravity(Gravity.RIGHT);
//            password.setGravity(Gravity.RIGHT);
//        }
//
//        driver=findViewById(R.id.driver);
//        driver.setTypeface(typeface);
//
//        email.setTypeface(typeface);
//        password.setTypeface(typeface);
//        title.setTypeface(typeface);
//        remember_me.setTypeface(typeface);
//        action.setTypeface(typeface);
//        slogan.setTypeface(typeface);
//        slogan2.setTypeface(typeface);
//        warning1.setTypeface(typeface);
//        warning2.setTypeface(typeface);
//        signup.setTypeface(typeface);
//        forget.setTypeface(typeface);
//
//        context=getApplicationContext();
//        connectionDetection = new ConnectionDetection(context);
//        progressBar = (ProgressBar) findViewById(R.id.progressBar);
//
//        lang=SharedPrefManager.getInstance(context).getLang();
//         imageView = (ImageView) findViewById(R.id.progrss);
//       // GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imageView);
//      //  Glide.with(this).load(R.drawable.giphy).into(imageViewTarget);
//
//        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View arg0, boolean hasfocus) {
//                if (hasfocus) {
//                    email.setHint(" ");
//                } else {
//                    email.setHint(getResources().getString(R.string.emailaddress));
//                }
//            }
//        });
//
//        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View arg0, boolean hasfocus) {
//                if (hasfocus) {
//                    password.setHint(" ");
//                } else {
//                    password.setHint(getResources().getString(R.string.pas));
//                }
//            }
//        });
//
////        email_ = SharedPrefManager.getInstance(getApplicationContext()).get_Remember_email();
////        password_ = SharedPrefManager.getInstance(getApplicationContext()).get_Remember_pass();
////        if ((email_ != null)) {
////            email.setText(email_);
////        }
////
////        if ((password_ != null)) {
////            password.setText(password_);
////        }
//
//        slogan1=findViewById(R.id.slogan1);
//        slogan1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Uri uri = Uri.parse("https://instagram.com/emcansolutions?utm_source=ig_profile_share&igshid=1gzmdf75zr5p2");
//                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
//                likeIng.setPackage("com.instagram.android");
//                try {
//                    startActivity(likeIng);
//                } catch (ActivityNotFoundException e) {
//                    startActivity(new Intent(Intent.ACTION_VIEW,
//                            Uri.parse("https://instagram.com/emcansolutions?utm_source=ig_profile_share&igshid=1gzmdf75zr5p2")));
//                }
//            }
//        });
//        action.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                email_ = email.getText().toString().trim();
//                password_ = password.getText().toString().trim();
//
//                if (email_.isEmpty() || email_.length() == 0 || email_.equals("") || email_ == null) {
//                    email.requestFocus();
//                    warning1.setVisibility(View.VISIBLE);
//                } else {
//                    warning1.setVisibility(View.INVISIBLE);
//                    email.clearFocus();
//                    if (password_.isEmpty() || password_.length() == 0 || password_.equals("") || password_ == null) {
//                        warning2.setVisibility(View.VISIBLE);
//                        password.requestFocus();
//                    } else {
//                        warning2.setVisibility(View.INVISIBLE);
//                        if (remember_me.isChecked()) {
//                            SharedPrefManager.getInstance(getApplicationContext()).remember(email_, password_);
//                            login();
//                        } else {
//                            if(driver.isChecked()){
//                                driverLogin();
//                                SharedPrefManager.getInstance(Login_Activity.this).setDriver(true);
//                            }
//                            else{
//                                SharedPrefManager.getInstance(Login_Activity.this).setDriver(false);
//                                login();
//                            }
//                        }
//                    }
//                }
//
//            }
//        });
//
//        signup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Login_Activity.this, Signup_Activity.class);
//                startActivity(intent);
//            }
//        });
//
//        forget.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Login_Activity.this, Forget_Pass.class);
//                startActivity(intent);
//            }
//        });

        Login_Fragment fragment =  Login_Fragment.newInstance(position);
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment).addToBackStack("xyz")
                .commit();
    }

    @Override
    public void onBackPressed() {

        int fragments = getSupportFragmentManager().getBackStackEntryCount();
//            finish();

                finish();



    }


    private void login() {

        if (connectionDetection.isConnected()) {
            progressBar.setVisibility(View.VISIBLE);

                  Api_Service requestInterface = Config.getClient().create(Api_Service.class);
         //   Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();

            User user=new User();
            user.setClient_phone(email_);
            user.setClient_password(password_);
            user.setLang(lang);
            user.setType("android");
            user.setDevice_token(SharedPrefManager.getInstance(context).get_token());
            Call<Login_response> response1 = requestInterface.login2(user);
            response1.enqueue(new Callback<Login_response>() {

                @Override
                public void onResponse(Call<Login_response> call, retrofit2.Response<Login_response> response) {

                    Login_response resp = response.body();

                    if (resp != null) {

                        if (resp.getSuccess() == 1) {
                            ArrayList<User> product = resp.getProduct();
                            if (product.get(0) != null) {
                                //--------------------add user to shred prefrence----------------//
                                User client = product.get(0);
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(client);

                                Log.d("lllll",product.get(0).getClient_id());

                                final Api_Service requestInterface = Config.getClient().create(Api_Service.class);
                                Call<Cart_Response2> response1 = requestInterface.getCart(SharedPrefManager.getInstance(getApplicationContext()).getUser().getClient_id(),lang,"");
                                response1.enqueue(new Callback<Cart_Response2>() {
                                    @Override
                                    public void onResponse(Call<Cart_Response2> call, retrofit2.Response<Cart_Response2> response) {
                                        progressBar.setVisibility(View.GONE);

                                        Cart_Response2 resp = response.body();
                                        if (resp != null) {
                                            if (resp.getSuccess() == 1) {
                                                ArrayList<Cart_Response2.CartModel2> cart1 = resp.getProduct();
                                                if (cart1.size() > 0) {
                                                    for (int i = 0; i < cart1.size() - 1; i++) {
                                                        SharedPrefManager.getInstance(getApplicationContext()).add_Cart();
                                                    }
                                                }
                                            } else {
                                                //     Toast.makeText(getApplicationContext(),resp.getMessage(),Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                        Intent intent = new Intent(getApplication(), MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                        startActivity(intent);
                                        finish();
                                    }

                                    @Override
                                    public void onFailure(Call<Cart_Response2> call, Throwable t) {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        //  Toast.makeText(getContext(),"خطأ في الاتصال بشبكة الانترنت",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), resp.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),getResources().getString(R.string.errortryagain), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Login_response> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.errortryagain), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    Log.d("lllllllllll", t.getMessage());

                }
            });

        } else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
        }

    }


}
