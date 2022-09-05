package com.emcan.zella.fragments;

import android.graphics.Typeface;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.emcan.zella.Beans.Current_order_model;
import com.emcan.zella.R;
import com.emcan.zella.SharedPrefManager;


public class Track_order extends Fragment {


    Toolbar toolbar;
    TextView title,num;
    ImageView cart;
    String lang;
    Typeface typeface;
    TextView p,pp,t1,t2,t3;
    RelativeLayout pickup;

    Button track;

Current_order_model.Current_order order;
    public Track_order() {
        // Required empty public constructor
    }


    public static Track_order newInstance(Current_order_model.Current_order current_order) {
        Track_order fragment = new Track_order();
        Bundle args = new Bundle();
        args.putSerializable("order", current_order);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           order= (Current_order_model.Current_order) getArguments().getSerializable("order");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_track_order, container, false);

        final AppCompatActivity activity1 = (AppCompatActivity) getActivity();

        track=view.findViewById(R.id.track);
        p=view.findViewById(R.id.p);
        pp=view.findViewById(R.id.pp);
        t1=view.findViewById(R.id.t1);
        t2=view.findViewById(R.id.t2);
        t3=view.findViewById(R.id.t3);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        activity1.setSupportActionBar(toolbar);
        lang= SharedPrefManager.getInstance(activity1).getLang();
        ImageView back=(ImageView) toolbar.getRootView().findViewById(R.id.back);

        if(lang.equals("en")){
            typeface = ResourcesCompat.getFont(activity1, R.font.amaranth_bold);
            back.setRotation(180);
        }
        if(lang.equals("ar")){
            typeface = ResourcesCompat.getFont(activity1, R.font.bein_black);

        }
        p.setTypeface(typeface);
        pp.setTypeface(typeface);
        t1.setTypeface(typeface);
        t2.setTypeface(typeface);
        t3.setTypeface(typeface);
        track.setTypeface(typeface);
        TextView title =(TextView) toolbar.getRootView().findViewById(R.id.toolbar_title);
        title.setTypeface(typeface);

        ImageButton menu =(ImageButton) toolbar.getRootView().findViewById(R.id.menu);
     //   ((MainActivity) activity1).enableDisableDrawer(DrawerLayout.LOCK_MODE_UNLOCKED);


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

        cart.setVisibility(View.INVISIBLE);
        menu.setVisibility(View.INVISIBLE);

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
        num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new MyCart();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment).addToBackStack("xyz")
                        .commit();
            }
        });

        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity1.getSupportFragmentManager().popBackStack();
            }
        });

        num.setVisibility(View.INVISIBLE);


        title.setText(activity1.getResources().getString(R.string.follow));



        TextView num=view.findViewById(R.id.num);
        TextView date=view.findViewById(R.id.date);

        if(order.getOrder_date()!=null){
            date.setText(order.getOrder_date());
            date.setTypeface(typeface);
        }


        if(order.getOrder_id()!=null){
            num.setText(order.getOrder_id());
            num.setTypeface(typeface);
        }
        ImageView stat1_green=view.findViewById(R.id.stat1_green);
        ImageView stat2_green=view.findViewById(R.id.stat2_green);
        ImageView stat3_green=view.findViewById(R.id.stat3_green);

        ImageView delivery1=view.findViewById(R.id.deliver1);
        ImageView delivery2=view.findViewById(R.id.delivery2);
        if(order.getOrder_follow().equals("1")){
            stat1_green.setVisibility(View.VISIBLE);
            delivery1.setVisibility(View.VISIBLE);
        }
        if(order.getOrder_follow().equals("0")){
            stat1_green.setVisibility(View.VISIBLE);
            delivery1.setVisibility(View.VISIBLE);
        }
        if(order.getOrder_follow().equals("1")&&order.getDriver_data()!=null&&order.getDriver_data().size()>0){
           track.setVisibility(View.VISIBLE);
        }
        if(order.getOrder_follow().equals("2")){
            stat1_green.setVisibility(View.VISIBLE);
            stat2_green.setVisibility(View.VISIBLE);
            delivery2.setVisibility(View.VISIBLE);
            delivery1.setVisibility(View.INVISIBLE);
        }

        if(order.getOrder_follow().equals("3")){
            stat1_green.setVisibility(View.VISIBLE);
            stat2_green.setVisibility(View.VISIBLE);
            stat3_green.setVisibility(View.VISIBLE);
            delivery2.setVisibility(View.VISIBLE);
            delivery1.setVisibility(View.VISIBLE);
        }

        if(lang.equals("ar")){
           delivery1.setRotation(180);
           delivery2.setRotation(180);

        }


        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = TrackDriver.newInstance(order);
                FragmentManager fragmentManager;
                fragmentManager = activity1.getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment).addToBackStack(null)
                        .commit();
            }
        });
        return view;
    }


}
