package com.emcan.zella.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import com.emcan.zella.Api_Service;
import com.emcan.zella.Beans.Check_Model;
import com.emcan.zella.Beans.LoyaltyPointsResponse;
import com.emcan.zella.Beans.Parent_Category;
import com.emcan.zella.Beans.Parent_Category_Model;
import com.emcan.zella.Beans.SettingsResponse;
import com.emcan.zella.Config;
import com.emcan.zella.ConnectionDetection;
import com.emcan.zella.R;
import com.emcan.zella.SharedPrefManager;
import com.emcan.zella.activities.Login_Activity;
import com.emcan.zella.activities.MainActivity;
import com.emcan.zella.adapters.Drawer_adapter;
import com.emcan.zella.databinding.FragmentMoreBinding;

import java.util.ArrayList;
import java.util.Locale;


public class More extends Fragment {

    FragmentMoreBinding binding;
    Toolbar toolbar;
    TextView title, num;
    ImageView cart;
    ImageButton menu;
    RelativeLayout no, yes;
    ConnectionDetection connectionDetection;
    Context mcontect;
    Context context;
    CardView card;
    String lang;
    final String FILE="pref1";
    ImageView back;

    FragmentManager fragmentManager;
    AppCompatActivity activity;
    Typeface typeface,typeface1;
    PopupWindow popupWindow;
    RelativeLayout login;
    String version;
    ArrayList<Parent_Category> parent;

    TextView name, email;
    ListView list1;
    TextView txtviewCopyname,txtviewUpdate,txtviewVersion;


    public More() {
        // Required empty public constructor
    }

    public static More newInstance(String param1, String param2) {
        More fragment = new More();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentMoreBinding.inflate(inflater, container, false);

        View view=binding.getRoot();
        mcontect=getContext();
        activity=(AppCompatActivity) getActivity();
        context=getContext();
        lang=SharedPrefManager.getInstance(mcontect).getLang();
        fragmentManager =activity.getSupportFragmentManager();
        parent = new ArrayList<>();
        connectionDetection=new ConnectionDetection(mcontect);

        txtviewCopyname=view.findViewById(R.id.txtview_copyname);
        txtviewUpdate=view.findViewById(R.id.txtview_update);
        txtviewVersion=view.findViewById(R.id.txtview_version);

        txtviewUpdate.setTypeface(typeface);
        txtviewCopyname.setTypeface(typeface);
        txtviewVersion.setTypeface(typeface);
        try {
            PackageInfo pInfo = mcontect.getPackageManager().getPackageInfo(mcontect.getPackageName(), 0);
            version = pInfo.versionName;
            txtviewVersion.setText("V " + version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        name = (TextView) view.findViewById(R.id.username);
        email = (TextView) view. findViewById(R.id.usermail);
        name.setTypeface(typeface);
        email.setTypeface(typeface);
        login=view.findViewById(R.id.login);


        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        title = toolbar.getRootView().findViewById(R.id.toolbar_title);

        menu = toolbar.getRootView().findViewById(R.id.menu);
        menu.setVisibility(View.VISIBLE);

        activity.setSupportActionBar(toolbar);
        back=activity.findViewById(R.id.back);
        back.setVisibility(View.VISIBLE);

        title.setTypeface(typeface);


        no = toolbar.getRootView().findViewById(R.id.no_cart);
        yes = toolbar.getRootView().findViewById(R.id.yes_cart);

        num = toolbar.getRootView().findViewById(R.id.num);

        RelativeLayout bar = activity.findViewById(R.id.bar);
        bar.setVisibility(View.VISIBLE);
        ((MainActivity) activity).select_icon("more");

        updateDrawer();
        getSettings();
        updateToolbar();

        if(lang.equals("ar")){
            binding.back1.setRotationY(180);
        }

        String[] list1_txt2 = {
                getResources().getString(R.string.main),
                getResources().getString(R.string.menu),
                getResources().getString(R.string.favourite),
                getResources().getString(R.string.cart),
                getResources().getString(R.string.myorders),
                getResources().getString(R.string.loyalty_points),
                getResources().getString(R.string.myacc),
                getResources().getString(R.string.complains),
                getResources().getString(R.string.aboutrestaourant),
                getResources().getString(R.string.notifications),
                getResources().getString(R.string.callus),
                getResources().getString(R.string.technical),
                getResources().getString(R.string.changelang),
                getResources().getString(R.string.logout)

        };

        int[] icons1 = new int[]{
                R.drawable.home,
                R.drawable.privacy,
                R.drawable.fav,
                R.drawable.cart,
                R.drawable.order,
                R.drawable.loyalty_points,
                R.drawable.profile,
                R.drawable.send_message,
                R.drawable.about,
                R.drawable.notifications,
                R.drawable.contact,
                R.drawable.supporticon,
                R.drawable.worlwide,
                R.drawable.sign_out

        };

        String[] list3_txt2 = {
                getResources().getString(R.string.main),
                getResources().getString(R.string.menu),
                getResources().getString(R.string.favourite),
                getResources().getString(R.string.cart),
                getResources().getString(R.string.myorders),
                getResources().getString(R.string.myacc),
                getResources().getString(R.string.complains),
                getResources().getString(R.string.aboutrestaourant),
                getResources().getString(R.string.notifications),
                getResources().getString(R.string.callus),
                getResources().getString(R.string.technical),
                getResources().getString(R.string.changelang),
                getResources().getString(R.string.logout)

        };

        int[] icons3 = new int[]{
                R.drawable.home,
                R.drawable.privacy,
                R.drawable.fav,
                R.drawable.cart,
                R.drawable.order,
                R.drawable.profile,
                R.drawable.send_message,
                R.drawable.about,
                R.drawable.notifications,
                R.drawable.contact,
                R.drawable.supporticon,
                R.drawable.worlwide,
                R.drawable.sign_out

        };
        String[] list1_txt = {
                getResources().getString(R.string.main),
                getResources().getString(R.string.menu),
                getResources().getString(R.string.aboutrestaourant),
                getResources().getString(R.string.notifications),

                getResources().getString(R.string.callus),
                getResources().getString(R.string.technical),
                getResources().getString(R.string.login),
                getResources().getString(R.string.signup),
                getResources().getString(R.string.changelang)

        };

        int[] icons2 = new int[]{
                R.drawable.home,
                R.drawable.privacy,
                R.drawable.about,
                R.drawable.notifications,
                R.drawable.contact,
                R.drawable.supporticon,
                R.drawable.sign_out,
                R.drawable.profile,
                R.drawable.worlwide

        };

        list1 = (ListView) view.findViewById(R.id.list1);
        if (SharedPrefManager.getInstance(mcontect).isLoggedIn()) {


            Drawer_adapter adapter = new Drawer_adapter(activity, list3_txt2, icons3);
            list1.setAdapter(adapter);


            list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Fragment fragment;

                    switch (i) {
                        case 0: //الرئيسية
                            fragment = new Slider();
                            title.setText(getResources().getString(R.string.app_name));
                            fragmentManager.beginTransaction()
                                    .replace(R.id.container, fragment).addToBackStack(null)
                                    .commit();
                            break;

                        case 1: //الرئيسية

                            get_categories();

                            break;


                        case 2: //المفضلة
                            fragment = new My_Favourites();
                            title.setText(getResources().getString(R.string.favourite));
                            fragmentManager.beginTransaction()
                                    .replace(R.id.container, fragment)
                                    .addToBackStack(null)
                                    .commit();
                            break;

                        case 3: //طلباتي
                            fragment = new MyCart();
                            title.setText(getResources().getString(R.string.cart));
                            fragmentManager.beginTransaction()
                                    .replace(R.id.container, fragment).addToBackStack(null)
                                    .commit();
                            break;

                        case 4:
                            fragment = new Orders();
                            title.setText(getResources().getString(R.string.app_name));
                            fragmentManager.beginTransaction()
                                    .replace(R.id.container, fragment).addToBackStack(null)
                                    .commit();
                            break;

                        case 5:
                            fragment = new My_Account();
                            title.setText(getResources().getString(R.string.app_name));
                            fragmentManager.beginTransaction()
                                    .replace(R.id.container, fragment).addToBackStack(null)
                                    .commit();
                            break;
                        case 6: //الطلبات السابقة
                            fragment = new Suggestions();
                            title.setText(getResources().getString(R.string.myorders));
                            fragmentManager.beginTransaction()
                                    .replace(R.id.container, fragment).addToBackStack(null)
                                    .commit();
                            break;

                        case 7: //عن المطعم
                            fragment = new About_sofra();
                            title.setText(getResources().getString(R.string.aboutrestaourant));
                            fragmentManager.beginTransaction()
                                    .replace(R.id.container, fragment).addToBackStack(null)
                                    .commit();
                            break;

                        case 8: //عن المطعم
                            fragment = new Notification();
                            title.setText(getResources().getString(R.string.aboutrestaourant));
                            fragmentManager.beginTransaction()
                                    .replace(R.id.container, fragment).addToBackStack(null)
                                    .commit();
                            break;

                        case 9: //تواصل ومساعدة
                            fragment = new Contact_us();
                            title.setText(getResources().getString(R.string.callus));
                            fragmentManager.beginTransaction()
                                    .replace(R.id.container, fragment).addToBackStack(null)
                                    .commit();
                            break;

                        case 10: //تواصل ومساعدة
                            fragment = new TechnicalSupportFragment();
                            title.setText(getResources().getString(R.string.callus));
                            fragmentManager.beginTransaction()
                                    .replace(R.id.container, fragment).addToBackStack(null)
                                    .commit();
                            break;

                        case 11:

                            final View popupView = LayoutInflater.from(mcontect).inflate(R.layout.language_popup, null);
                            popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT,
                                    WindowManager.LayoutParams.MATCH_PARENT);
                            final Button english = popupView.findViewById(R.id.english);
                            Button arabic = popupView.findViewById(R.id.arabic);

                            english.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    SharedPreferences sharedPreferences = mcontect.getSharedPreferences(FILE, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("LANG", "en");
                                    editor.apply();

                                    typeface = ResourcesCompat.getFont(mcontect, R.font.amaranth_bold);
                                    Locale myLocale = new Locale("en");
                                    Resources res = getResources();
                                    DisplayMetrics dm = res.getDisplayMetrics();
                                    Configuration conf = res.getConfiguration();
                                    conf.locale = myLocale;
                                    res.updateConfiguration(conf, dm);
                                    Intent refresh = new Intent(mcontect, MainActivity.class);
                                    refresh.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    refresh.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    refresh.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    startActivity(refresh);
                                    activity.finish();
                                    conf.setLayoutDirection(new Locale("en"));
                                    popupWindow.dismiss();

                                }
                            });
                            arabic.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    SharedPreferences sharedPreferences = mcontect.getSharedPreferences(FILE, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("LANG", "ar");
                                    editor.apply();
                                    typeface = ResourcesCompat.getFont(mcontect, R.font.bein_black);
                                    Locale myLocale = new Locale("ar");
                                    Resources res = getResources();
                                    DisplayMetrics dm = res.getDisplayMetrics();
                                    Configuration conf = res.getConfiguration();
                                    conf.locale = myLocale;
                                    res.updateConfiguration(conf, dm);
                                    Intent refresh = new Intent(mcontect, MainActivity.class);
                                    refresh.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    refresh.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    refresh.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    startActivity(refresh);
                                    activity.finish();
                                    conf.setLayoutDirection(new Locale("ar"));

                                    popupWindow.dismiss();
                                }
                            });


                            popupWindow.setAnimationStyle(R.style.popup_window_animation_phone);
                            popupWindow.setFocusable(true);
                            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                            break;

                        case 12: //تواصل ومساعدة

                           logout();
                            break;
                    }

                }
            });




//                            }
//                        }
//                    } else {
//                        //  Toast.makeText(getContext(), "خطأ حاول مجددا", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<LoyaltyPointsResponse> call, Throwable t) {
//
//                    //   Toast.makeText(getContext(), "خطأ حاول مجددا", Toast.LENGTH_SHORT).show();
//                }
//            });

        } else {
            Drawer_adapter adapter = new Drawer_adapter(activity, list1_txt, icons2);
            list1.setAdapter(adapter);

            list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Fragment fragment;

                    switch (i) {
                        case 0: //الرئيسية
                            fragment = new Slider();
                            title.setText(getResources().getString(R.string.app_name));
                            // fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            fragmentManager.beginTransaction()
                                    .replace(R.id.container, fragment).addToBackStack(null)
                                    .commit();
                            break;

                        case 1: //الرئيسية

                            get_categories();

                            break;

                        case 2: //عن المطعم
                            fragment = new About_sofra();
                            title.setText(getResources().getString(R.string.aboutrestaourant));
                            fragmentManager.beginTransaction()
                                    .replace(R.id.container, fragment).addToBackStack(null)
                                    .commit();
                            break;

                        case 3: //عن المطعم
                            fragment = new Notification();
                            title.setText(getResources().getString(R.string.aboutrestaourant));
                            fragmentManager.beginTransaction()
                                    .replace(R.id.container, fragment).addToBackStack(null)
                                    .commit();
                            break;


                        case 4: //تواصل ومساعدة
                            fragment = new Contact();
                            title.setText(getResources().getString(R.string.callus));
                            fragmentManager.beginTransaction()
                                    .replace(R.id.container, fragment).addToBackStack(null)
                                    .commit();
                            break;

                        case 5:
                            fragment = new TechnicalSupportFragment();
                            title.setText(getResources().getString(R.string.app_name));
                            fragmentManager.beginTransaction()
                                    .replace(R.id.container, fragment).addToBackStack(null)
                                    .commit();
                            break;


                        case 6: //تسجيل دخول //تسجيل خروج

                            Intent intent = new Intent(mcontect, Login_Activity.class);
                            intent.putExtra("pos",0);
                            startActivity(intent);
                            break;

                        case 7: //مستخدم جديد

                            Intent intent1 = new Intent(mcontect, Login_Activity.class);
                            intent1.putExtra("pos",1);

                            startActivity(intent1);
                            break;

                        case 8:
                            final View popupView = LayoutInflater.from(mcontect).inflate(R.layout.language_popup, null);
                            popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT,
                                    WindowManager.LayoutParams.MATCH_PARENT);
                            final Button english=popupView.findViewById(R.id.english);
                            Button arabic=popupView.findViewById(R.id.arabic);

                            english.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    SharedPreferences sharedPreferences = mcontect.getSharedPreferences(FILE, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("LANG", "en");
                                    editor.apply();

                                    typeface = ResourcesCompat.getFont(mcontect, R.font.amaranth_bold);
                                    Locale myLocale = new Locale("en");
                                    Resources res = getResources();
                                    DisplayMetrics dm = res.getDisplayMetrics();
                                    Configuration conf = res.getConfiguration();
                                    conf.locale = myLocale;
                                    res.updateConfiguration(conf, dm);
                                    Intent refresh = new Intent(mcontect, MainActivity.class);
                                    refresh.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    refresh.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    refresh.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    startActivity(refresh);
                                    activity.finish();
                                    conf.setLayoutDirection(new Locale("en"));
                                    popupWindow.dismiss();

                                }
                            });
                            arabic.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    SharedPreferences sharedPreferences = mcontect.getSharedPreferences(FILE, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("LANG", "ar");
                                    editor.apply();
                                    typeface= ResourcesCompat.getFont(mcontect, R.font.bein_black);
                                    Locale myLocale = new Locale("ar");
                                    Resources res = getResources();
                                    DisplayMetrics dm = res.getDisplayMetrics();
                                    Configuration conf = res.getConfiguration();
                                    conf.locale = myLocale;
                                    res.updateConfiguration(conf, dm);
                                    Intent refresh = new Intent(mcontect, MainActivity.class);
                                    refresh.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    refresh.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    refresh.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    startActivity(refresh);
                                   activity. finish();
                                    conf.setLayoutDirection(new Locale("ar"));

                                    popupWindow.dismiss();
                                }
                            });


                            popupWindow.setAnimationStyle(R.style.popup_window_animation_phone);
                            popupWindow.setFocusable(true);
                            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                    }
                }
            });

        }

        if (SharedPrefManager.getInstance(mcontect).isLoggedIn()) {


            Api_Service requestInterface = Config.getClient().create(Api_Service.class);
                Call<LoyaltyPointsResponse> response1 = requestInterface.getPoints(SharedPrefManager.getInstance(getContext()).getUser()
                        .getClient_id(),lang,"1","","");
                response1.enqueue(new Callback<LoyaltyPointsResponse>() {
                    @Override
                    public void onResponse(Call<LoyaltyPointsResponse> call, Response<LoyaltyPointsResponse> response) {
                        LoyaltyPointsResponse resp = response.body();
                        if (resp != null) {
                            if (resp.getSuccess() == 1) {
                               if(resp.getPoints_status()!=null&&resp.getPoints_status().equals("1"))

                               {

                                   Drawer_adapter adapter = new Drawer_adapter(activity, list1_txt2, icons1);
                                   list1.setAdapter(adapter);


                                   list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                       @Override
                                       public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                           Fragment fragment;

                                           switch (i) {
                                               case 0: //الرئيسية
                                                   fragment = new Slider();
                                                   title.setText(getResources().getString(R.string.app_name));
                                                   fragmentManager.beginTransaction()
                                                           .replace(R.id.container, fragment).addToBackStack(null)
                                                           .commit();
                                                   break;

                                               case 1: //الرئيسية

                                                   get_categories();

                                                   break;


                                               case 2: //المفضلة
                                                   fragment = new My_Favourites();
                                                   title.setText(getResources().getString(R.string.favourite));
                                                   fragmentManager.beginTransaction()
                                                           .replace(R.id.container, fragment)
                                                           .addToBackStack(null)
                                                           .commit();
                                                   break;

                                               case 3: //طلباتي
                                                   fragment = new MyCart();
                                                   title.setText(getResources().getString(R.string.cart));
                                                   fragmentManager.beginTransaction()
                                                           .replace(R.id.container, fragment).addToBackStack(null)
                                                           .commit();
                                                   break;

                                               case 4:
                                                   fragment = new Orders();
                                                   title.setText(getResources().getString(R.string.app_name));
                                                   fragmentManager.beginTransaction()
                                                           .replace(R.id.container, fragment).addToBackStack(null)
                                                           .commit();
                                                   break;


                                               case 5:
                                                   fragment = new LoyaltyPointsFragment();
                                                   title.setText(getResources().getString(R.string.app_name));
                                                   fragmentManager.beginTransaction()
                                                           .replace(R.id.container, fragment).addToBackStack(null)
                                                           .commit();
                                                   break;

                                               case 6:
                                                   fragment = new My_Account();
                                                   title.setText(getResources().getString(R.string.app_name));
                                                   fragmentManager.beginTransaction()
                                                           .replace(R.id.container, fragment).addToBackStack(null)
                                                           .commit();
                                                   break;
                                               case 7: //الطلبات السابقة
                                                   fragment = new Suggestions();
                                                   title.setText(getResources().getString(R.string.myorders));
                                                   fragmentManager.beginTransaction()
                                                           .replace(R.id.container, fragment).addToBackStack(null)
                                                           .commit();
                                                   break;

                                               case 8: //عن المطعم
                                                   fragment = new About_sofra();
                                                   title.setText(getResources().getString(R.string.aboutrestaourant));
                                                   fragmentManager.beginTransaction()
                                                           .replace(R.id.container, fragment).addToBackStack(null)
                                                           .commit();
                                                   break;

                                               case 9: //عن المطعم
                                                   fragment = new Notification();
                                                   title.setText(getResources().getString(R.string.aboutrestaourant));
                                                   fragmentManager.beginTransaction()
                                                           .replace(R.id.container, fragment).addToBackStack(null)
                                                           .commit();
                                                   break;

                                               case 10: //تواصل ومساعدة
                                                   fragment = new Contact_us();
                                                   title.setText(getResources().getString(R.string.callus));
                                                   fragmentManager.beginTransaction()
                                                           .replace(R.id.container, fragment).addToBackStack(null)
                                                           .commit();
                                                   break;

                                               case 11: //تواصل ومساعدة
                                                   fragment = new TechnicalSupportFragment();
                                                   title.setText(getResources().getString(R.string.callus));
                                                   fragmentManager.beginTransaction()
                                                           .replace(R.id.container, fragment).addToBackStack(null)
                                                           .commit();
                                                   break;

                                               case 12:

                                                   final View popupView = LayoutInflater.from(mcontect).inflate(R.layout.language_popup, null);
                                                   popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT,
                                                           WindowManager.LayoutParams.MATCH_PARENT);
                                                   final Button english = popupView.findViewById(R.id.english);
                                                   Button arabic = popupView.findViewById(R.id.arabic);

                                                   english.setOnClickListener(new View.OnClickListener() {
                                                       @Override
                                                       public void onClick(View view) {
                                                           SharedPreferences sharedPreferences = mcontect.getSharedPreferences(FILE, Context.MODE_PRIVATE);
                                                           SharedPreferences.Editor editor = sharedPreferences.edit();
                                                           editor.putString("LANG", "en");
                                                           editor.apply();

                                                           typeface = ResourcesCompat.getFont(mcontect, R.font.amaranth_bold);
                                                           Locale myLocale = new Locale("en");
                                                           Resources res = getResources();
                                                           DisplayMetrics dm = res.getDisplayMetrics();
                                                           Configuration conf = res.getConfiguration();
                                                           conf.locale = myLocale;
                                                           res.updateConfiguration(conf, dm);
                                                           Intent refresh = new Intent(mcontect, MainActivity.class);
                                                           refresh.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                           refresh.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                           refresh.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                                           startActivity(refresh);
                                                           activity.finish();
                                                           conf.setLayoutDirection(new Locale("en"));
                                                           popupWindow.dismiss();

                                                       }
                                                   });
                                                   arabic.setOnClickListener(new View.OnClickListener() {
                                                       @Override
                                                       public void onClick(View view) {
                                                           SharedPreferences sharedPreferences = mcontect.getSharedPreferences(FILE, Context.MODE_PRIVATE);
                                                           SharedPreferences.Editor editor = sharedPreferences.edit();
                                                           editor.putString("LANG", "ar");
                                                           editor.apply();
                                                           typeface = ResourcesCompat.getFont(mcontect, R.font.bein_black);
                                                           Locale myLocale = new Locale("ar");
                                                           Resources res = getResources();
                                                           DisplayMetrics dm = res.getDisplayMetrics();
                                                           Configuration conf = res.getConfiguration();
                                                           conf.locale = myLocale;
                                                           res.updateConfiguration(conf, dm);
                                                           Intent refresh = new Intent(mcontect, MainActivity.class);
                                                           refresh.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                           refresh.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                           refresh.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                                           startActivity(refresh);
                                                           activity.finish();
                                                           conf.setLayoutDirection(new Locale("ar"));

                                                           popupWindow.dismiss();
                                                       }
                                                   });


                                                   popupWindow.setAnimationStyle(R.style.popup_window_animation_phone);
                                                   popupWindow.setFocusable(true);
                                                   popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                                                   break;

                                               case 13 : //تواصل ومساعدة

                                                   logout();
                                                   break;
                                           }

                                       }
                                   });


                               }
                            }
                        } else {
                            //  Toast.makeText(getContext(), "خطأ حاول مجددا", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoyaltyPointsResponse> call, Throwable t) {

                        //   Toast.makeText(getContext(), "خطأ حاول مجددا", Toast.LENGTH_SHORT).show();
                    }
                });
        }
        return   binding.getRoot();
    }

    public void updateDrawer() {
        //  Log.d("pppppppppppppp","pppppppppppppppppppp");

        if (SharedPrefManager.getInstance(mcontect).isLoggedIn()) {
            login.setVisibility(View.VISIBLE);
            name.setText(SharedPrefManager.getInstance(mcontect).getUser().getClient_name());
            email.setText(SharedPrefManager.getInstance(mcontect).getUser().getClient_phone());
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = new My_Account();
                    ((MainActivity)activity). replaceFragment(fragment);
                }
            });
            name.setTypeface(typeface);

        } else {

            login.setVisibility(View.GONE);

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
                            Toast.makeText(mcontect, getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(mcontect, getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
                    }
                }


                @Override
                public void onFailure(Call<Parent_Category_Model> call, Throwable t) {
                    Log.d("respooooonse", t.toString());

                }
            });
        } else {
            Toast.makeText(mcontect, getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
        }

    }

    void getSettings() {
        if (connectionDetection.isConnected()) {
            final Api_Service requestInterface = Config.getClient().create(Api_Service.class);
            Call<SettingsResponse> response1 = requestInterface.getSettings();
            response1.enqueue(new Callback<SettingsResponse>() {
                @Override
                public void onResponse(Call<SettingsResponse> call, Response<SettingsResponse> response) {
                    final SettingsResponse resp = response.body();
                    if (resp != null) {
                        if (resp.getSuccess() == 1) {

                            if (resp.getProduct() != null && resp.getProduct().size() > 0) {
                                if (resp.getProduct().get(0).getCopyright_name() != null && resp.getProduct().get(0).getCopyright_name().length() > 0) {
                                    txtviewCopyname.setText(resp.getProduct().get(0).getCopyright_name());
                                    if (resp.getProduct().get(0).getCopyright_link() != null) {
                                        txtviewCopyname.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(resp.getProduct().get(0).getCopyright_link()));
                                                if (i.resolveActivity(mcontect.getPackageManager()) != null) {
                                                    startActivity(i);
                                                }
                                            }
                                        });
                                    }
                                }

                                if(resp.getProduct().get(0).getAndroid_version()!=null&&resp.getProduct().get(0).getAndroid_version().length()>0&&version!=null){
                                    if(Double.parseDouble(resp.getProduct().get(0).getAndroid_version())>Double.parseDouble(version)){
                                        if(lang.equals("en")){
                                            txtviewUpdate.setText("(Update Available)");
                                        }
                                        else{
                                            txtviewUpdate.setText("(يوجد تحديث جديد)");
                                        }
                                    }
                                    else{
                                        txtviewUpdate.setVisibility(View.GONE);
                                    }
                                }
                            }

                        }
                    }
                }


                @Override
                public void onFailure(Call<SettingsResponse> call, Throwable t) {

                }
            });

        } else {
        }
    }
    public void updateToolbar(){
        title = toolbar.getRootView().findViewById(R.id.toolbar_title);

        if (SharedPrefManager.getInstance(getContext()).get_Cart() > 0) {
            cart = toolbar.getRootView().findViewById(R.id.cart);

            no.setVisibility(View.INVISIBLE);
            yes.setVisibility(View.VISIBLE);
            num.setText(String.valueOf(SharedPrefManager.getInstance(getContext()).get_Cart()));

        } else {
            cart = toolbar.getRootView().findViewById(R.id.cart1);
            yes.setVisibility(View.INVISIBLE);
            no.setVisibility(View.VISIBLE);
        }
        title.setTypeface(typeface);
        title.setText(mcontect.getResources().getString(R.string.more));

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new MyCart();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment).addToBackStack(null)
                        .commit();
            }
        });
    }


    public void logout(){
        if (connectionDetection.isConnected()) {
            Api_Service requestInterface = Config.getClient().create(Api_Service.class);
            Call<Check_Model> response1 = requestInterface.logout(SharedPrefManager.getInstance(context).getUser().getClient_id()
                    ,lang);
            response1.enqueue(new Callback<Check_Model>() {
                @Override
                public void onResponse(Call<Check_Model> call, retrofit2.Response<Check_Model> response) {
                    Check_Model resp = response.body();
                    if (resp != null) {
                        if (resp.getSuccess() == 1) {

                            SharedPrefManager.getInstance(context).logout();
                            Intent intent = new Intent(context, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent);

//
                        }
                    } else {
                        Toast.makeText(context, resp.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Check_Model> call, Throwable t) {
//                                        Toast.makeText(context, context.getResources().getString(R.string.deletedfromfav), Toast.LENGTH_SHORT).show();
                }
            });

        } else {

            Toast.makeText(context, context.getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
        }
    }

}