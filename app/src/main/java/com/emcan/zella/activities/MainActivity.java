package com.emcan.zella.activities;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.emcan.zella.fragments.BranchesTab;
import com.emcan.zella.fragments.Contact;
import com.emcan.zella.fragments.More;
import com.emcan.zella.fragments.Search;
import com.google.android.material.navigation.NavigationBarView;
import android.graphics.drawable.ColorDrawable;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.emcan.zella.Api_Service;
import com.emcan.zella.Beans.Additions_Model;
import com.emcan.zella.Beans.Branch_Model;
import com.emcan.zella.Beans.LoginResponse;
import com.emcan.zella.Beans.LoyaltyPointsResponse;
import com.emcan.zella.Beans.Parent_Category;
import com.emcan.zella.Beans.Parent_Category_Model;
import com.emcan.zella.Beans.Prices_Model;
import com.emcan.zella.Beans.Remove_Response;
import com.emcan.zella.Beans.SettingsResponse;
import com.emcan.zella.Beans.Sub_Category_Model;
import com.emcan.zella.Beans.User;
import com.emcan.zella.Config;
import com.emcan.zella.ConnectionDetection;
import com.emcan.zella.GET_DATA;
import com.emcan.zella.R;
import com.emcan.zella.SharedPrefManager;
import com.emcan.zella.adapters.Drawer_adapter;
import com.emcan.zella.databinding.ActivityMainBinding;
import com.emcan.zella.fragments.About_sofra;
import com.emcan.zella.fragments.Dish_Fragments;
import com.emcan.zella.fragments.LoyaltyPointsFragment;
import com.emcan.zella.fragments.Meat_Chicken;
import com.emcan.zella.fragments.Messages;
import com.emcan.zella.fragments.MyCart;
import com.emcan.zella.fragments.My_Account;
import com.emcan.zella.fragments.My_Favourites;
import com.emcan.zella.fragments.Notification;
import com.emcan.zella.fragments.Orders;
import com.emcan.zella.fragments.Slider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements GET_DATA  {


    FragmentManager fragmentManager;
    TextView title;
    int fragments;
    RelativeLayout icon1, icon2, icon3,icon4;

    ImageView home, notification, messages, menu;
    ArrayList<Parent_Category> parent;

    ConnectionDetection connectionDetection;
    ActivityMainBinding binding;
    Context context;
    PopupWindow popupWindow;
    String currentVersion;
    final String FILE="pref1";
    Typeface typeface;
    String lang;
    Toolbar mToolbar;
    ImageButton back;


    @Override
    protected void onResume() {
        super.onResume();

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >20) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        if (Build.VERSION.SDK_INT >22) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN );
        }
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

        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);



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

//--------------------tool bar setting------------//
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        title = (TextView) mToolbar.getRootView().findViewById(R.id.toolbar_title);
        title.setTypeface(typeface);


        //-------------------back arrow toolbar ------------------//
        context=getApplicationContext();
        FirebaseApp.initializeApp(context);
        connectionDetection=new ConnectionDetection(context);


        if(connectionDetection.isConnected()) {

            FirebaseMessaging.getInstance().getToken()
                    .addOnCompleteListener(new OnCompleteListener<String>() {
                        @Override
                        public void onComplete(@NonNull Task<String> task) {
                            if (!task.isSuccessful()) {
                                Log.w("pppppppp", "Fetching FCM registration token failed", task.getException());
                                return;
                            }

//                            Log.d("ppppppppp",task.getResult());

                            SharedPrefManager.getInstance(getApplicationContext()).set_token(task.getResult());

                            if (SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()) {

                                send_token();
                            }
                        }

                    });

        }
        init();
        fragments = getSupportFragmentManager().getBackStackEntryCount();
        if (getFragmentManager().getBackStackEntryCount() > 1) {
            back.setVisibility(View.VISIBLE);
        } else {
            back.setVisibility(View.INVISIBLE);
        }
        if(lang.equals("en")){
            typeface = ResourcesCompat.getFont(MainActivity.this, R.font.amaranth_bold);
            back.setRotation(180);
        }
        if(lang.equals("ar")){
            typeface= ResourcesCompat.getFont(MainActivity.this, R.font.bein_black);
        }


        title.setTypeface(typeface);

        ImageButton search=mToolbar.findViewById(R.id.menu);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new Search();
                fragmentManager.beginTransaction().replace(R.id.container,
                        fragment).addToBackStack("xyz")
                        .commit();
            }
        });


        fragments = getSupportFragmentManager().getBackStackEntryCount();
        if(fragments==1){
         back.setVisibility(View.INVISIBLE);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (fragments == 1) {
                    finish();
                } else {
                    if (getFragmentManager().getBackStackEntryCount() > 1) {
                        getFragmentManager().popBackStack();
                    } else {
                        MainActivity.super.onBackPressed();
                    }
                }
            }
        });



       setFragment();

//-------------adv pop up-------------//
        final View popupView = LayoutInflater.from(MainActivity.this).inflate(R.layout.pop_adv, null);
        popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);


        TextView cancel = popupView.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                SharedPrefManager.getInstance(getApplicationContext()).Adv_Cancel("1");

            }
        });

        RelativeLayout out = popupView.findViewById(R.id.out);
        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                SharedPrefManager.getInstance(getApplicationContext()).Adv_Cancel("1");

            }
        });


        ConnectionDetection connectionDetection = new ConnectionDetection(getApplicationContext());
        if (SharedPrefManager.getInstance(getApplicationContext()).Adv_Check() == null) {
            if (connectionDetection.isConnected()) {
                Api_Service requestInterface = Config.getClient().create(Api_Service.class);
                Call<Sub_Category_Model> response1 = requestInterface.get_dish_of_the_day();
                response1.enqueue(new Callback<Sub_Category_Model>() {
                    @Override
                    public void onResponse(Call<Sub_Category_Model> call, retrofit2.Response<Sub_Category_Model> response) {

                        final Sub_Category_Model resp = response.body();
                        if (resp != null) {
                            if (resp.getSuccess() == 1) {
                                //        Toast.makeText(getApplicationContext(),String.valueOf(resp.getProduct().size()),Toast.LENGTH_SHORT).show();
                                if (resp.getProduct().size() > 0) {
                                    if (response.body().getProduct().get(0).getImage() != null
                                            && !response.body().getProduct().get(0).getImage().equals(" ")) {
                                        popupWindow.setOutsideTouchable(true);
                                        popupWindow.setFocusable(true);

                                        ImageView image = popupView.findViewById(R.id.image);

                                        if (response.body().getProduct().get(0).getImage() != null &&
                                                !response.body().getProduct().get(0).getImage().equals("")) {
                                            Glide.with(getApplicationContext())
                                                    .load(response.body().getProduct().get(0).getImage())
                                                    .into(image);
                                            image.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    popupWindow.dismiss();
                                                    Fragment fragment = Dish_Fragments.newInstance(resp.getProduct().get(0),
                                                            resp.getProduct().get(0).getParent_category_name());
                                                    fragmentManager.beginTransaction().replace(R.id.container,
                                                            fragment).addToBackStack("xyz")
                                                            .commit();
                                                }
                                            });
                                            findViewById(R.id.container).post(new Runnable() {
                                                public void run() {
                                                    popupWindow.setAnimationStyle(R.style.popup_window_animation_phone);

                                                    popupWindow.showAtLocation(findViewById(R.id.container), Gravity.CENTER, 0, 0);

                                          //////
                                                }
                                            });

                                        }

                                    }
                                }
                            }
                        }
                    }


                    @Override
                    public void onFailure(Call<Sub_Category_Model> call, Throwable t) {
                        //    Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();


                    }
                });

            } else {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();

            }
        }




        icon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_icon("home");
                Fragment fragment = new Slider();
                title.setText(getResources().getString(R.string.app_name));
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment).addToBackStack("xyz")
                        .commit();
            }
        });

        icon2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        select_icon("notification");
                        Fragment fragment = new Notification();
                        fragmentManager.beginTransaction()
                                .replace(R.id.container, fragment).addToBackStack("xyz")
                                .commit();


                    }
                }
        );


        icon3.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        select_icon("chat");
                        Fragment fragment = new Messages();
                        fragmentManager.beginTransaction()
                                .replace(R.id.container, fragment).addToBackStack("xyz")
                                .commit();
                    }
                }
        );

        icon4.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        select_icon("more");
                        Fragment fragment = new More();
                        fragmentManager.beginTransaction()
                                .replace(R.id.container, fragment).addToBackStack("xyz")
                                .commit();
                    }
                }
        );


        try {
            currentVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            SharedPrefManager.getInstance(MainActivity.this).setMobileVersion(currentVersion);

//            Log.d("jjjjjj",currentVersion);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (SharedPrefManager.getInstance(getApplicationContext()).update_check() == null) {
           // new GetVersionCode().execute();
        }

    }





    private void init(){


        fragmentManager = getSupportFragmentManager();

        connectionDetection = new ConnectionDetection(getApplicationContext());

        home = findViewById(R.id.home_icon);
        notification = findViewById(R.id.notification_icon);
        messages = findViewById(R.id.message_icon);
        menu = findViewById(R.id.more);

        back = (ImageButton) mToolbar.getRootView().findViewById(R.id.back);

        parent=new ArrayList<>();

        icon1 = findViewById(R.id.icon1);
        icon2 = findViewById(R.id.icon2);
        icon3 = findViewById(R.id.icon3);
        icon4 = findViewById(R.id.icon4);

//
//        binding.bottomNavigation.setItemIconTintList(null);
//        binding.bottomNavigation.setOnItemSelectedListener(this);
    }




    @Override
    public void onBackPressed() {

        int fragments = getSupportFragmentManager().getBackStackEntryCount();
        if (fragments == 1) {
//            finish();
            final AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(this,
                        android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(this);
            }
            builder.setTitle(getResources().getString(R.string.closeapp))
                    .setMessage(getResources().getString(R.string.wantcloseapp))
                    .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();
        } else {
            if (getFragmentManager().getBackStackEntryCount() > 1) {
                getFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }
        }

    }



    private void send_token(){
        Api_Service requestInterface = Config.getClient().create(Api_Service.class);
        final User user = new User();
        user.setType("android");
        user.setLang(lang);
        user.setDevice_token(SharedPrefManager.getInstance(context).get_token());
        user.setClient_id(SharedPrefManager.getInstance(context).getUser().getClient_id());

        Call<LoginResponse> response1 = requestInterface.send_Token(user);
        response1.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, retrofit2.Response<LoginResponse> response) {

                LoginResponse resp = response.body();
                if (resp != null) {

                    if (resp.getSuccess() == 1) {
                        Log.d("gggggggggg",resp.getMessage()+"");

                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {


            }
        });
    }


   /* private class GetVersionCode extends AsyncTask<Void, String, String> {
        @Override
        protected String doInBackground(Void... voids) {
            String newVersion = null;
            try {
                newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + MainActivity.this.getPackageName() + "&hl=it")
                        .timeout(30000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get()
                        .select(".hAyfc .htlgb")
                        .get(7)
                        .ownText();
                return newVersion;
            } catch (Exception e) {
                return newVersion;
            }

        }

        @Override
        protected void onPostExecute(String onlineVersion) {
            super.onPostExecute(onlineVersion);
            if (onlineVersion != null && !onlineVersion.isEmpty()) {
                Log.d("jjjjjjj",onlineVersion);
                if (Float.valueOf(currentVersion) < Float.valueOf(onlineVersion)) {

                    final View popupView = LayoutInflater.from(MainActivity.this).inflate(R.layout.update_popup, null);
                    popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT,
                            WindowManager.LayoutParams.MATCH_PARENT);


                    TextView cancel = popupView.findViewById(R.id.cancel);
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            popupWindow.dismiss();
                            SharedPrefManager.getInstance(getApplicationContext()).update_cancel("1");

                        }
                    });


                    TextView update = popupView.findViewById(R.id.update);
                    update.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            popupWindow.dismiss();
                            SharedPrefManager.getInstance(getApplicationContext()).update_cancel("1");

                           final String appPackageName = getPackageName();
//                            Intent sendIntent = new Intent();
//                            sendIntent.setAction(Intent.ACTION_SEND);
//                            sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            sendIntent.putExtra(android.content.Intent.EXTRA_TEXT,
//                                    " https://play.google.com/store/apps/details?id="+appPackageName);
//
//                            sendIntent.setType("text/plain");
//                            startActivity(sendIntent);

                            String url = "https://play.google.com/store/apps/details?id="+appPackageName;
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            i.setPackage("com.android.vending");/* Play store package name */
                           /* startActivity(i);

                        }
                    });

                    findViewById(R.id.container).post(new Runnable() {
                        public void run() {
                            popupWindow.showAtLocation(findViewById(R.id.container), Gravity.CENTER, 0, 0);
                        }
                    });

                }
            }
            Log.d("update", "Current version " + currentVersion + "playstore version " + onlineVersion);
        }
    }*/

    private void setFragment(){
        if (getIntent().getStringExtra("type") != null) {

            if (getIntent().getStringExtra("type").equals("order_")) {
                Fragment fragment = new Slider();
                title.setText(getResources().getString(R.string.app_name));
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment).addToBackStack("xyz")
                        .commit();
                fragment = new Orders();
                title.setText(getResources().getString(R.string.notifications));
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment).addToBackStack("xyz")
                        .commit();
            } else {
                Fragment fragment = new Slider();
                title.setText(getResources().getString(R.string.app_name));
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment).addToBackStack("xyz")
                        .commit();
                fragment = new Notification();
                title.setText(getResources().getString(R.string.notifications));
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment).addToBackStack("xyz")
                        .commit();
            }

        }

        else {
            Fragment fragment = new Slider();
            title.setText(getResources().getString(R.string.app_name));
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment).addToBackStack("xyz")
                    .commit();
        }
    }




    @Override
    public void get_ID(Branch_Model.Branch branch) {

    }

    @Override
    public void remove_ID() {

    }



    @Override
    public void get_add(Additions_Model.Addition addition, int i) {

    }

    @Override
    public void get_without(Remove_Response.Removes remove_id, int i) {

    }


    @Override
    public void getSize(Prices_Model.Price size) {

    }

    @Override
    public void get_Price(ArrayList<Prices_Model.Price> prices_models) {

    }

    public void replaceFragment(Fragment fragment) {
        if (fragment != null) {
            fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack("xyz")
                    .commit();
//            this.title.setText(title);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // action when permission granted
                } else {
                    // action when permission denied
                }
                return;
            }
        }
    }

    public void select_home() {
        icon1.setBackground(getResources().getDrawable(R.drawable.home_fill));
        home.setVisibility(View.GONE);


    }

    public void unselect_home() {
        home.setImageDrawable(getResources().getDrawable(R.drawable.home_empty));
        home.setVisibility(View.VISIBLE);
        icon1.setBackgroundColor(Color.parseColor("#00FFFFFF"));
    }

    public void select_notifications() {
        icon2.setBackground(getResources().getDrawable(R.drawable.notifications_fill));
        notification.setVisibility(View.GONE);


    }

    public void unselect_notifications() {
        notification.setImageDrawable(getResources().getDrawable(R.drawable.notifications_empty));
        notification.setVisibility(View.VISIBLE);
        icon2.setBackgroundColor(Color.parseColor("#00FFFFFF"));


    }

    public void select_orders() {
        icon3.setBackground(getResources().getDrawable(R.drawable.messages_fill));
        messages.setVisibility(View.GONE);


    }

    public void unselect_orders() {
        messages.setImageDrawable(getResources().getDrawable(R.drawable.messages_empty));
        messages.setVisibility(View.VISIBLE);
        icon3.setBackgroundColor(Color.parseColor("#00FFFFFF"));



    }

    public void select_mnue() {
        icon4.setBackground(getResources().getDrawable(R.drawable.more_fill));
        menu.setVisibility(View.GONE);

    }

    public void unselect_mnue() {
        menu.setImageDrawable(getResources().getDrawable(R.drawable.more_empty));
        menu.setVisibility(View.VISIBLE);
        icon4.setBackgroundColor(Color.parseColor("#00FFFFFF"));
    }

    public void select_icon(String type) {

        if (type.equals("none")) {
            unselect_home();
            unselect_orders();
            unselect_mnue();
            unselect_notifications();
        }


        if (type.equals("home")) {
            select_home();
            unselect_orders();
            unselect_notifications();
            unselect_mnue();

        }
        if (type.equals("notification")) {
            unselect_home();
            unselect_orders();
            select_notifications();
            unselect_mnue();

        }
        if (type.equals("chat")) {
            unselect_home();
            select_orders();
            unselect_notifications();
            unselect_mnue();
        }


        if (type.equals("more")) {
            unselect_home();
            select_mnue();
            unselect_orders();
            unselect_notifications();
        }


    }
    public void get_categories() {
        final Api_Service requestInterface = Config.getClient().create(Api_Service.class);
        if (connectionDetection.isConnected()) {
            Call<Parent_Category_Model> response = requestInterface.getParentCategories(lang);
            response.enqueue(new Callback<Parent_Category_Model>() {
                @Override
                public void onResponse(Call<Parent_Category_Model> call, retrofit2.Response<Parent_Category_Model> response) {

                    Parent_Category_Model resp = response.body();
                    if (resp != null) {
                        if (resp.getSuccess() == 1) {
                            parent = resp.getProduct();
                            if (parent.size() > 0) {
                                Fragment fragment = new Meat_Chicken();
                                title.setText(getResources().getString(R.string.app_name));
                                title.setTypeface(typeface);
                                Bundle bundle = new Bundle();
                                bundle.putParcelableArrayList("cat", parent);
                                bundle.putInt("pos", 0);
                                fragment.setArguments(bundle);
                                fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack("xyz")
                                        .commit();
                            }

                        } else {
                            Toast.makeText(context, getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
                    }
                }


                @Override
                public void onFailure(Call<Parent_Category_Model> call, Throwable t) {
                    Log.d("respooooonse", t.toString());

                }
            });
        } else {
            Toast.makeText(context, getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
        }

    }
}