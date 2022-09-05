package com.emcan.zella.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.emcan.zella.Beans.Parent_Category;
import com.emcan.zella.Beans.Sub_Category;
import com.emcan.zella.R;
import com.emcan.zella.SharedPrefManager;
import com.emcan.zella.activities.MainActivity;
import com.emcan.zella.adapters.Meat_Chick_Tab_Adapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class Meat_Chicken extends Fragment {

    Toolbar toolbar;
    TextView title,num;
    ImageView cart;
    ArrayList<Parent_Category> parent_categories,parent_categories2;
    ArrayList<Sub_Category> sub_categories,sub_categories1;
    int position=-1;
    RelativeLayout no, yes;
    TabLayout tabLayout;
    String lang;
    Typeface typeface;
    AppCompatActivity activity;
    int flag;
    RelativeLayout pickup;


    Meat_Chick_Tab_Adapter meat_chick_tab_adapter;
    public Meat_Chicken() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View view=inflater.inflate(R.layout.fragment_meat__chicken, container, false);

         activity = (AppCompatActivity) getActivity();

        //------------------get data------------------//
        if(getArguments()!=null) {
            flag=getArguments().getInt("flag");
            if(flag==1){
                sub_categories=getArguments().getParcelableArrayList("cat");
                position = getArguments().getInt("pos");
            }
            parent_categories = getArguments().getParcelableArrayList("cat");
            position = getArguments().getInt("pos");
        }

//        ((MainActivity)activity).setBottomNavigation("home");

        //------------------------prepare tabs-----------------//

        RelativeLayout bar = activity.findViewById(R.id.bar);
        bar.setVisibility(View.VISIBLE);
        ((MainActivity) activity).select_icon("home");


        parent_categories2=new ArrayList<>();
        sub_categories1=new ArrayList<>();
        lang= SharedPrefManager.getInstance(activity).getLang();
        if(lang.equals("en")){
            typeface = ResourcesCompat.getFont(activity, R.font.amaranth_bold);
        }
        if(lang.equals("ar")){
            typeface= ResourcesCompat.getFont(activity, R.font.bein_black);
        }
        if(lang.equals("en")){
            if(flag==1){
                Log.d("outfor","outfor");
                for(int i=sub_categories.size()-1;i>-1;i--){
                    sub_categories1.add(sub_categories.get(i));
                    Log.e("infor",sub_categories.get(i).getSub_category_name());
                }
            }
            else{
                for(int i=parent_categories.size()-1;i>-1;i--){
                    parent_categories2.add(parent_categories.get(i));
                }
            }

        }else{
            if(flag==1){
                for(int i=0;i<sub_categories.size();i++){
                    sub_categories1.add(sub_categories.get(i));
                }
            }
            else{
                for(int i=0;i<parent_categories.size();i++){
                    parent_categories2.add(parent_categories.get(i));
                }
            }

        }
        ViewPager vpPager = (ViewPager) view.findViewById(R.id.pager);
        tabLayout=(TabLayout) view.findViewById(R.id.tabDots);
        setupViewPager(vpPager);


        setToolbar();

        tabLayout.setupWithViewPager(vpPager);
        tabLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

        set_Margins();
        changeTabsFont();

            if (lang.equals("ar")) {
                if(flag==1){
                    TabLayout.Tab tab = tabLayout.getTabAt(sub_categories.size()-1-position);
                    tab.select();

                }
                else{
                    TabLayout.Tab tab = tabLayout.getTabAt(parent_categories.size()-1-position);
                    tab.select();
                }
            } else {
                TabLayout.Tab tab = tabLayout.getTabAt(position);
                tab.select();
            }

            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab1)
            { tab1.select(); }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

            if(flag==1){
                tabLayout.setVisibility(View.GONE);
            }
        return view;
    }

 private void setupViewPager(ViewPager viewPager) {

         meat_chick_tab_adapter = new Meat_Chick_Tab_Adapter(getChildFragmentManager());
        LayoutInflater inflator = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        int count=0;
        if(flag==1){
            count = sub_categories.size();
        }
        else{
          count = parent_categories.size();
        }
        if(count<4){
          tabLayout.setTabMode(TabLayout.MODE_FIXED);
        }else{
          tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
         }

        for (int i=count-1; i>-1; i--){

            Sandwitches fView = new Sandwitches();
            Bundle bundle = new Bundle();
            if(flag==1){
                SharedPreferences sharedPreferences=activity.getSharedPreferences("pref1",Context.MODE_PRIVATE);
                bundle.putString("parent_id", sub_categories1.get(i).getParent_category_id());
                bundle.putString("parent_name",sharedPreferences.getString("parent_name",""));
                bundle.putString("type",sharedPreferences.getString("type",""));
                bundle.putString("parent_type",sharedPreferences.getString("parent_type",""));
                bundle.putString("sub_cat_id",sub_categories1.get(i).getSub_category_id());
//                Log.d("name",sharedPreferences.getString("parent_name","xfxd"));
                bundle.putInt("flag",1);
            }
            else{
                bundle.putString("parent_id", parent_categories2.get(i).getParent_categorey_id());
                bundle.putString("parent_name",parent_categories2.get(i).getParent_categorey_name());
                bundle.putString("type",parent_categories2.get(i).getType());
                bundle.putString("parent_type",parent_categories2.get(i).getParent_type());
                SharedPreferences sharedPreferences=activity.getSharedPreferences("pref1",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("parent_type",parent_categories2.get(i).getParent_type());
                editor.putString("type",parent_categories2.get(i).getType());
//                Log.e("jkkjkj",parent_categories2.get(i).getParent_categorey_name());
                editor.putString("parent_name",parent_categories2.get(i).getParent_categorey_name());
                editor.apply();
            }

            fView.setArguments(bundle);

          if(flag==1){
              SharedPreferences sharedPreferences=activity.getSharedPreferences("pref1",Context.MODE_PRIVATE);
              meat_chick_tab_adapter.addFrag(fView,sharedPreferences.getString("parent_name",""));

          }
          else{
              meat_chick_tab_adapter.addFrag(fView,parent_categories2.get(i).getParent_categorey_name());

          }
        }

        viewPager.setAdapter(meat_chick_tab_adapter);

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
            p.setMargins(0, 0, 50, 0);
            tab.requestLayout();

        }
    }
    private void  setToolbar(){
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

        activity.setSupportActionBar(toolbar);
        no=toolbar.findViewById(R.id.no_cart);
        yes=toolbar.findViewById(R.id.yes_cart);

        num=toolbar.getRootView().findViewById(R.id.num);
        title=toolbar.getRootView().findViewById(R.id.toolbar_title);

        if(SharedPrefManager.getInstance(getContext()).get_Cart()>0){
            title.setTypeface(typeface);
            cart=toolbar.getRootView().findViewById(R.id.cart);
            no.setVisibility(View.INVISIBLE);
            yes.setVisibility(View.VISIBLE);
            num.setText(String.valueOf(SharedPrefManager.getInstance(getContext()).get_Cart()));

        }else{
            title.setTypeface(typeface);
            cart=toolbar.getRootView().findViewById(R.id.cart1);
            yes.setVisibility(View.INVISIBLE);
            no.setVisibility(View.VISIBLE);
        }

        ImageButton back=toolbar.findViewById(R.id.back);
        back.setVisibility(View.VISIBLE);
        num.setVisibility(View.VISIBLE);

        cart.setVisibility(View.VISIBLE);
        ImageView menu=toolbar.findViewById(R.id.menu);
        menu.setVisibility(View.VISIBLE);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new MyCart();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment).addToBackStack("xyz")
                        .commit();
            }
        });
    }

}
