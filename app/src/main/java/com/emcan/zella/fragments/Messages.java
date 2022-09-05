package com.emcan.zella.fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.emcan.zella.Api_Service;
import com.emcan.zella.Beans.About_response;
import com.emcan.zella.Config;
import com.emcan.zella.ConnectionDetection;
import com.emcan.zella.R;
import com.emcan.zella.SharedPrefManager;
import com.emcan.zella.activities.MainActivity;
import com.emcan.zella.adapters.Meat_Chick_Tab_Adapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;


public class Messages extends Fragment {

    View view;
    Toolbar toolbar;
    TextView title, num;
    ImageView cart;
    RelativeLayout no, yes;
    ConnectionDetection connectionDetection;
    Context mcontect;
    ArrayList<About_response.Sofra> types;
    RelativeLayout pickup;
    TabLayout tabLayout;
    FragmentManager fragmentmanfer2;
    String lang;
    Typeface typeface;
    AppCompatActivity activity;

    public Messages() {
        // Required empty public constructor
    }

    public static Messages newInstance(String param1, String param2) {
        Messages fragment = new Messages();
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
        view = inflater.inflate(R.layout.fragment_messages, container, false);

        mcontect = getContext();
        //----------------------tool bar and title--------------//

        activity = (AppCompatActivity) getActivity();

        toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        ImageButton back = toolbar.findViewById(R.id.back);
        lang= SharedPrefManager.getInstance(activity).getLang();
        if(lang.equals("en")){
            typeface = ResourcesCompat.getFont(activity, R.font.amaranth_bold);
            back.setRotation(180);
        }
        if(lang.equals("ar")){
            typeface= ResourcesCompat.getFont(activity, R.font.bein_black);
        }
        no = toolbar.findViewById(R.id.no_cart);
        yes = toolbar.findViewById(R.id.yes_cart);


        num = toolbar.getRootView().findViewById(R.id.num);
        title=toolbar.getRootView().findViewById(R.id.toolbar_title);

        if (SharedPrefManager.getInstance(mcontect).get_Cart() > 0) {
            cart = toolbar.getRootView().findViewById(R.id.cart);

            no.setVisibility(View.INVISIBLE);
            yes.setVisibility(View.VISIBLE);
            num.setText(String.valueOf(SharedPrefManager.getInstance(mcontect).get_Cart()));

        } else {
            cart = toolbar.getRootView().findViewById(R.id.cart1);
            yes.setVisibility(View.INVISIBLE);
            no.setVisibility(View.VISIBLE);
        }
        title.setTypeface(typeface);

        title.setVisibility(View.GONE);
        title.setVisibility(View.VISIBLE);
        //    title.setText("");
        title.setText(activity.getResources().getString(R.string.inbox));

        RelativeLayout bar = activity.findViewById(R.id.bar);
        bar.setVisibility(View.VISIBLE);
        ((MainActivity) activity).select_icon("chat");


        fragmentmanfer2=getChildFragmentManager();
        cart.setVisibility(View.VISIBLE);
        ImageView menu = toolbar.findViewById(R.id.menu);
        menu.setVisibility(View.VISIBLE);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new MyCart();
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment).addToBackStack("xyz")
                        .commit();
            }
        });
        back.setVisibility(View.VISIBLE);


        final ViewPager vpPager = (ViewPager) view.findViewById(R.id.pager);
        final ProgressBar progressBar=view.findViewById(R.id.progressBar);
        tabLayout=(TabLayout) view.findViewById(R.id.tabDots);


        connectionDetection = new ConnectionDetection(mcontect);

        if (connectionDetection.isConnected()) {
            progressBar.setVisibility(View.VISIBLE);

            Api_Service requestInterface = Config.getClient().create(Api_Service.class);
            Call<About_response> response1 = requestInterface.get_messages_type(lang);
            response1.enqueue(new Callback<About_response>() {
                @Override
                public void onResponse(Call<About_response> call, retrofit2.Response<About_response> response) {
                    progressBar.setVisibility(View.INVISIBLE);
                    About_response resp = response.body();
                    if (resp != null) {
                      if(resp.getProduct().size()>0){
                          types=resp.getProduct();
                          for(int i=0;i<resp.getProduct().size();i++){
                              Log.d("languageeeee",resp.getProduct().get(i).getTitle());
                          }

                          setupViewPager(vpPager,resp.getProduct().size());
                          tabLayout.setupWithViewPager(vpPager);
                          if(lang.equals("ar")){
                              tabLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

                          }
                          else{
                              tabLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

                          }
                          TabLayout.Tab tab = tabLayout.getTabAt(resp.getProduct().size()-1);

                          tab.select();
                          tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                              @Override
                              public void onTabSelected(TabLayout.Tab tab1)

                              {
                                  tab1.select();
                              }

                              @Override
                              public void onTabUnselected(TabLayout.Tab tab) {

                              }

                              @Override
                              public void onTabReselected(TabLayout.Tab tab) {

                              }
                          });


                      }

                    }
                }

                @Override
                public void onFailure(Call<About_response> call, Throwable t) {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });

        } else {
            Toast.makeText(mcontect, activity.getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
        }


        return view;
    }

    private void setupViewPager(ViewPager viewPager, int count_) {

       Meat_Chick_Tab_Adapter meat_chick_tab_adapter = new Meat_Chick_Tab_Adapter(fragmentmanfer2);
        LayoutInflater inflator = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        int count = count_;
        for (int i=count-1; i>-1; i--){
            if(types.get(i).getMessage_type_id().equals("1")){
                Complains fView = new Complains();
                Bundle bundle = new Bundle();
                bundle.putString("id", types.get(i).getMessage_type_id() );
                bundle.putString("name",types.get(i).getTitle());
                fView.setArguments(bundle);

                meat_chick_tab_adapter.addFrag(fView,types.get(i).getTitle());
            }else{
                Message_tab fView = new Message_tab();
                Bundle bundle = new Bundle();
                bundle.putString("id", types.get(i).getMessage_type_id() );
                bundle.putString("name",types.get(i).getTitle());
                fView.setArguments(bundle);

                meat_chick_tab_adapter.addFrag(fView,types.get(i).getTitle());
            }


        }

        viewPager.setAdapter(meat_chick_tab_adapter);
        set_Margins();
        changeTabsFont();
    }

    private void changeTabsFont() {
        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);

            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(typeface);
                }
            }
        }
    }

    private void set_Margins(){
        ViewGroup tabs = (ViewGroup) tabLayout.getChildAt(0);

        for(int i=0; i < tabLayout.getTabCount(); i++) {
            View tab = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
            p.setMargins(20, 0, 20, 0);
            tab.requestLayout();

        }
    }


}
