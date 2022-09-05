package com.emcan.zella.fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.emcan.zella.R;
import com.emcan.zella.SharedPrefManager;
import com.emcan.zella.adapters.Meat_Chick_Tab_Adapter;
import com.emcan.zella.databinding.FragmentBranchesTabBinding;
import com.google.android.material.tabs.TabLayout;


public class BranchesTab extends Fragment {

 FragmentBranchesTabBinding binding;
    Toolbar toolbar;
    TextView title,num;
    ImageView cart;
    RelativeLayout no, yes;
    String lang;
    Typeface typeface;
    AppCompatActivity activity;
    int flag;
    Meat_Chick_Tab_Adapter meat_chick_tab_adapter;

    public BranchesTab() {
        // Required empty public constructor
    }


    public static BranchesTab newInstance(String param1, String param2) {
        BranchesTab fragment = new BranchesTab();
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
        binding= FragmentBranchesTabBinding.inflate(inflater, container, false);

            activity = (AppCompatActivity) getActivity();

//            ((MainActivity)activity).setBottomNavigation("home");

            //------------------------prepare tabs-----------------//


            lang= SharedPrefManager.getInstance(activity).getLang();
            if(lang.equals("en")){
                typeface = ResourcesCompat.getFont(activity, R.font.amaranth_bold);
            }
            if(lang.equals("ar")){
                typeface= ResourcesCompat.getFont(activity, R.font.bein_black);
            }


            setupViewPager(binding.pager);

            setToolbar();

            binding.tabDots.setupWithViewPager(binding.pager);
            binding.tabDots.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

//            set_Margins();
            changeTabsFont();



            binding.tabDots.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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


            return binding.getRoot();
        }

        private void setupViewPager(ViewPager viewPager) {

            meat_chick_tab_adapter = new Meat_Chick_Tab_Adapter(getChildFragmentManager());
            LayoutInflater inflator = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            int count=2;


            for (int i=count-1; i>-1; i--){

                if(i==0){
                    BranchesMap fView = new BranchesMap();
                    meat_chick_tab_adapter.addFrag(fView,getContext().getResources().getString(R.string.mapView));

                }else{
                    Branches_List fView = new Branches_List();
                    meat_chick_tab_adapter.addFrag(fView,getContext().getResources().getString(R.string.listView));

                }


            }

            viewPager.setAdapter(meat_chick_tab_adapter);

        }



        private void changeTabsFont() {
            ViewGroup vg = (ViewGroup) binding.tabDots.getChildAt(0);
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
            ViewGroup tabs = (ViewGroup) binding.tabDots.getChildAt(0);

            for(int i=0; i < binding.tabDots.getTabCount(); i++) {
                View tab = ((ViewGroup) binding.tabDots.getChildAt(0)).getChildAt(i);
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


            title.setText(getContext().getResources().getString(R.string.branches));
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
