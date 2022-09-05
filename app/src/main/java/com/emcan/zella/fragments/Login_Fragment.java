package com.emcan.zella.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.emcan.zella.ConnectionDetection;
import com.emcan.zella.LoginListener;
import com.emcan.zella.R;
import com.emcan.zella.SharedPrefManager;
import com.emcan.zella.adapters.Meat_Chick_Tab_Adapter;
import com.google.android.material.tabs.TabLayout;


public class Login_Fragment extends Fragment implements LoginListener {


    View view;
    String lang;
    ConnectionDetection connectionDetection;
    Context context;
    AppCompatActivity activity;
    FragmentManager fragmentmanfer2;
    int position=0;
    TabLayout tabLayout;


    public Login_Fragment() {
        // Required empty public constructor
    }


    public static Login_Fragment newInstance(int position) {
        Login_Fragment fragment = new Login_Fragment();
        Bundle args = new Bundle();
        args.putInt("pos",position);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            position=getArguments().getInt("pos");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login_, container, false);

        final ViewPager vpPager = (ViewPager) view.findViewById(R.id.pager);
         tabLayout = (TabLayout) view.findViewById(R.id.tabDots);

        context = getContext();
        activity = (AppCompatActivity) getActivity();
        lang = SharedPrefManager.getInstance(context).getLang();

        fragmentmanfer2 = getChildFragmentManager();




        setupViewPager(vpPager, 2);
        tabLayout.setupWithViewPager(vpPager);
        if (lang.equals("ar")) {
            tabLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

        } else {
            tabLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        }

        TabLayout.Tab tab = tabLayout.getTabAt(position);

        tab.select();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab1) {
                tab1.select();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        return view;
    }

    private void setupViewPager(ViewPager viewPager, int count_) {

        Meat_Chick_Tab_Adapter meat_chick_tab_adapter = new Meat_Chick_Tab_Adapter(fragmentmanfer2);

        int count = 2;
        for (int i = 0; i < 2; i++) {
            if (i == 0) {
                Login_Tab fView = new Login_Tab();
                fView.setListener(this);


                meat_chick_tab_adapter.addFrag(fView, context.getResources().getString(R.string.login));
            } else {
                Signup_Tab fView = new Signup_Tab();
                fView.setListener(this);

                meat_chick_tab_adapter.addFrag(fView, context.getResources().getString(R.string.signup));
            }

        }
            viewPager.setAdapter(meat_chick_tab_adapter);



    }

    @Override
    public  void selectTab(int position){
        TabLayout.Tab tab = tabLayout.getTabAt(position);

        tab.select();
    }
}