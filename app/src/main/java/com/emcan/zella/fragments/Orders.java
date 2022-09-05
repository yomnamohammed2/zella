package com.emcan.zella.fragments;

import android.graphics.Typeface;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.emcan.zella.ConnectionDetection;
import com.emcan.zella.R;
import com.emcan.zella.SharedPrefManager;
import com.emcan.zella.activities.MainActivity;
import com.emcan.zella.adapters.last_order_tabs_adapter;
import com.google.android.material.tabs.TabLayout;


public class Orders extends Fragment {
    TextView title,num;
    ImageView cart;
    Toolbar toolbar;
    ProgressBar progressBar;
    ConnectionDetection connectionDetection;
    String lang;
    RelativeLayout pickup;
    TabLayout tabLayout;

    Typeface typeface;

    public Orders() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =inflater.inflate(R.layout.fragment_orders, container, false);


        final AppCompatActivity activity1 = (AppCompatActivity) getActivity();

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        activity1.setSupportActionBar(toolbar);
        lang= SharedPrefManager.getInstance(activity1).getLang();
        if(lang.equals("en")){
            typeface = ResourcesCompat.getFont(activity1, R.font.amaranth_bold);
        }
        if(lang.equals("ar")){
            typeface= ResourcesCompat.getFont(activity1, R.font.bein_black);
        }
        ImageButton menu =(ImageButton) toolbar.getRootView().findViewById(R.id.menu);

       // ((MainActivity) activity1).enableDisableDrawer(DrawerLayout.LOCK_MODE_UNLOCKED);

        RelativeLayout bar = activity1.findViewById(R.id.bar);
        bar.setVisibility(View.VISIBLE);
        ((MainActivity) activity1).select_icon("none");


        RelativeLayout no=(RelativeLayout) toolbar.getRootView().findViewById(R.id.no_cart);
        RelativeLayout yes=(RelativeLayout) toolbar.getRootView().findViewById(R.id.yes_cart);

        num=(TextView) toolbar.getRootView().findViewById(R.id.num);
        title=toolbar.getRootView().findViewById(R.id.toolbar_title);


        if(SharedPrefManager.getInstance(getContext()).get_Cart()>0){
            cart=(ImageView) toolbar.getRootView().findViewById(R.id.cart);
            no.setVisibility(View.INVISIBLE);
            yes.setVisibility(View.VISIBLE);
            num.setText(String.valueOf(SharedPrefManager.getInstance(getContext()).get_Cart()));

        }else{
            cart=(ImageView) toolbar.getRootView().findViewById(R.id.cart1);
            yes.setVisibility(View.INVISIBLE);
            no.setVisibility(View.VISIBLE);
        }
        title.setTypeface(typeface);
        title.setText(activity1.getResources().getString(R.string.myorders));
        cart.setVisibility(View.VISIBLE);
        menu.setVisibility(View.VISIBLE);

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new MyCart();
                FragmentManager fragmentManager = activity1.getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment).addToBackStack("xyz")
                        .commit();
            }
        });
        num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new MyCart();
                FragmentManager fragmentManager = activity1.getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment).addToBackStack("xyz")
                        .commit();
            }
        });

        ImageView back=(ImageView) toolbar.getRootView().findViewById(R.id.back);
        back.setVisibility(View.VISIBLE);

        num.setVisibility(View.VISIBLE);

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.pager);

        // Create an adapter that knows which fragment should be shown on each page
        last_order_tabs_adapter adapter = new last_order_tabs_adapter(getContext(),getChildFragmentManager());
        viewPager.setAdapter(adapter);
         tabLayout=(TabLayout) view.findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(viewPager);
        if(lang.equals("ar")){
            viewPager.setCurrentItem(1);
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.select();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        changeTabsFont();
        set_Margins();

        return view;
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
