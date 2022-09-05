package com.emcan.zella.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emcan.zella.Api_Service;
import com.emcan.zella.Beans.Sub_Category;
import com.emcan.zella.Beans.Sub_Category_Model;
import com.emcan.zella.Config;
import com.emcan.zella.ConnectionDetection;
import com.emcan.zella.R;
import com.emcan.zella.SharedPrefManager;
import com.emcan.zella.adapters.Recycler_adapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;


public class Sandwitches extends Fragment {
RecyclerView recyclerView;
Recycler_adapter mAdapter;
ConnectionDetection connectionDetection;
Toolbar toolbar;
TextView title,num;
ImageView cart;
AppCompatActivity activity;
Context context;
    FragmentManager fragmentManager;
    String lang;
    String type,sub_cat_id;
    Typeface typeface;
    BroadcastReceiver broad_castReceiver;
    RelativeLayout pickup;
    ArrayList<Sub_Category> sub_categories;
    ArrayList<Sub_Category> sub_categories1;
    String id,parent_name,parent_type;
    int flag;

    public Sandwitches() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {

        activity.registerReceiver( broad_castReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));

        super.onResume();

    }

    @Override
    public void onPause() {
        activity.unregisterReceiver(broad_castReceiver);

        super.onPause();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_sandwitches, container, false);
context=getContext();

        activity = (AppCompatActivity) getActivity();

        toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        lang= SharedPrefManager.getInstance(activity).getLang();
        if(lang.equals("en")){
            typeface = ResourcesCompat.getFont(activity, R.font.amaranth_bold);
        }
        if(lang.equals("ar")){
            typeface= ResourcesCompat.getFont(activity, R.font.bein_black);
        }
       // title=(TextView)toolbar.getRootView().findViewById(R.id.toolbar_title);
        RelativeLayout no=toolbar.findViewById(R.id.no_cart);
        RelativeLayout yes=toolbar.findViewById(R.id.yes_cart);

        num=toolbar.getRootView().findViewById(R.id.num);
        title=toolbar.getRootView().findViewById(R.id.toolbar_title);

        if(SharedPrefManager.getInstance(context).get_Cart()>0){
            cart=toolbar.getRootView().findViewById(R.id.cart);

            no.setVisibility(View.INVISIBLE);
            yes.setVisibility(View.VISIBLE);
            num.setText(String.valueOf(SharedPrefManager.getInstance(context).get_Cart()));

        }else{
            cart=toolbar.getRootView().findViewById(R.id.cart1);
            yes.setVisibility(View.INVISIBLE);
            no.setVisibility(View.VISIBLE);
        }

        title.setTypeface(typeface);
        title.setText(getResources().getString(R.string.menu));
        ImageButton back=toolbar.findViewById(R.id.back);
        back.setVisibility(View.VISIBLE);


        cart.setVisibility(View.VISIBLE);
        ImageView menu=toolbar.findViewById(R.id.menu);
        menu.setVisibility(View.VISIBLE);

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new MyCart();
                 fragmentManager = activity.getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment).addToBackStack("xyz")
                        .commit();
            }
        });

        //--------------gat parent category id--------------------//
        Bundle bundle = this.getArguments();
        id = bundle.getString("parent_id");
        parent_name=bundle.getString("parent_name");
        type=bundle.getString("type");
        parent_type=bundle.getString("parent_type");
        flag=bundle.getInt("flag");
        if(flag==1){
            sub_cat_id=bundle.getString("sub_cat_id");
        }
//        Log.e("parent_typeeeee",parent_type+" "+type+""+parent_name);

        recyclerView = view.findViewById(R.id.recycler_view);

        connectionDetection=new ConnectionDetection(context);
        broad_castReceiver=new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                if (connectionDetection.isConnected()) {

                    if(flag==1){
                        update2();
                    }
                    else{
                        update();
                    }
                }
            }
        };
        super.onResume();



        return view;
    }

    public void update() {


        final Api_Service requestInterface = Config.getClient().create(Api_Service.class);
        if (connectionDetection.isConnected()) {
            Call<Sub_Category_Model> response = requestInterface.getSubCategories(id, SharedPrefManager.getInstance(context).getUser().getClient_id(),lang,parent_type,
                  "");
            response.enqueue(new Callback<Sub_Category_Model>() {
                @Override
           public void onResponse(Call<Sub_Category_Model> call, retrofit2.Response<Sub_Category_Model> response) {

                    Sub_Category_Model resp = response.body();
             if (resp != null) {
                if (resp.getSuccess() == 1) {
                    sub_categories1=new ArrayList<>();
                sub_categories = resp.getProduct();
                 if (sub_categories.size() > 0) {
                     for(int i=sub_categories.size()-1;i>=0;i--){
                         sub_categories.get(i).setType(type);
//                         sub_categories1.add(sub_categories.get(i));
                     }

                                mAdapter = new Recycler_adapter(context, sub_categories,parent_name);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
                                recyclerView.setLayoutManager(mLayoutManager);
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                recyclerView.setAdapter(mAdapter);

                            }
                        }else{}

                    }else{}
                }


                @Override
                public void onFailure(Call<Sub_Category_Model> call, Throwable t) {
                    Log.d("respooooonse", t.toString());
                 //   Toast.makeText(getContext(),"An Error Occurred",Toast.LENGTH_SHORT).show();

                }
            });
        }
       

    }

    public void update2() {


        final Api_Service requestInterface = Config.getClient().create(Api_Service.class);
        if (connectionDetection.isConnected()) {
            Call<Sub_Category_Model> response = requestInterface.getSubSubCategories(id, SharedPrefManager.getInstance(context).getUser().getClient_id(),lang,sub_cat_id);
            response.enqueue(new Callback<Sub_Category_Model>() {
                @Override
                public void onResponse(Call<Sub_Category_Model> call, retrofit2.Response<Sub_Category_Model> response) {

                    Sub_Category_Model resp = response.body();
                    if (resp != null) {
                        if (resp.getSuccess() == 1) {
                            sub_categories1=new ArrayList<>();
                            sub_categories = resp.getProduct();
                            if (sub_categories.size() > 0) {
                                for(int i=sub_categories.size()-1;i>=0;i--){
                                    sub_categories.get(i).setType(type);
//                         sub_categories1.add(sub_categories.get(i));
                                }

                                mAdapter = new Recycler_adapter(context, sub_categories,parent_name);
                                Log.e("adapter",parent_name);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
                                recyclerView.setLayoutManager(mLayoutManager);
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                recyclerView.setAdapter(mAdapter);

                            }
                        }else{}

                    }else{}
                }


                @Override
                public void onFailure(Call<Sub_Category_Model> call, Throwable t) {
                    Log.d("respooooonse", t.toString());
                    //   Toast.makeText(getContext(),"An Error Occurred",Toast.LENGTH_SHORT).show();

                }
            });
        }


    }


}
