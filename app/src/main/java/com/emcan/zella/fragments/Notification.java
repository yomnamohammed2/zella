package com.emcan.zella.fragments;

import android.content.Context;
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
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emcan.zella.Api_Service;
import com.emcan.zella.Beans.About_response;
import com.emcan.zella.Config;
import com.emcan.zella.ConnectionDetection;
import com.emcan.zella.R;
import com.emcan.zella.SharedPrefManager;
import com.emcan.zella.activities.MainActivity;
import com.emcan.zella.adapters.Notifications_adapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;


public class Notification extends Fragment {

    View view;

    ConnectionDetection connectionDetection;
    ProgressBar progressBar;
    ArrayList<About_response.Sofra> notifications;
    ImageView cart;
    Toolbar toolbar;
    TextView title, num;
    RelativeLayout no, yes;
    Context context;
    ImageView image;
    TextView message;
    RecyclerView recyclerView;
    String lang;
    Typeface typeface;
    AppCompatActivity activity;
    RelativeLayout pickup;


    public Notification() {
        // Required empty public constructor
    }


    public static Notification newInstance(String param1, String param2) {
        Notification fragment = new Notification();
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
        view= inflater.inflate(R.layout.fragment_notification, container, false);
        //----------------------tool bar and title--------------//

        activity = (AppCompatActivity) getActivity();

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        lang= SharedPrefManager.getInstance(activity).getLang();
        ImageButton back=toolbar.findViewById(R.id.back);
        if(lang.equals("en")){
            typeface = ResourcesCompat.getFont(activity, R.font.amaranth_bold);
            back.setRotation(180);
        }
        if(lang.equals("ar")){
            typeface= ResourcesCompat.getFont(activity, R.font.bein_black);
        }
        activity.setSupportActionBar(toolbar);
        no=toolbar.findViewById(R.id.no_cart);
        yes=toolbar.findViewById(R.id.yes_cart);

        num=toolbar.getRootView().findViewById(R.id.num);
        context=getContext();
        title=toolbar.getRootView().findViewById(R.id.toolbar_title);

        if(SharedPrefManager.getInstance(getContext()).get_Cart()>0){
            cart=toolbar.getRootView().findViewById(R.id.cart);

            no.setVisibility(View.INVISIBLE);
            yes.setVisibility(View.VISIBLE);
            num.setText(String.valueOf(SharedPrefManager.getInstance(getContext()).get_Cart()));

        }else{
            cart=toolbar.getRootView().findViewById(R.id.cart1);
            yes.setVisibility(View.INVISIBLE);
            no.setVisibility(View.VISIBLE);
        }
        title.setTypeface(typeface);
        title.setText(activity.getResources().getString(R.string.notifications));
        back.setVisibility(View.VISIBLE);



        cart.setVisibility(View.VISIBLE);
        ImageView menu=toolbar.findViewById(R.id.menu);
        menu.setVisibility(View.VISIBLE);

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
            }
        });

        RelativeLayout bar = activity.findViewById(R.id.bar);
        bar.setVisibility(View.VISIBLE);
        ((MainActivity) activity).select_icon("notification");

        connectionDetection=new ConnectionDetection(getContext());

         message =(TextView) view.findViewById(R.id.message);
         message.setTypeface(typeface);
        image = view.findViewById(R.id.image);
        recyclerView=(RecyclerView) view.findViewById(R.id.recycler_cart);
        progressBar=(ProgressBar) view.findViewById(R.id.progressBar);

        notifications=new ArrayList<>();

        if(SharedPrefManager.getInstance(context).isLoggedIn()) {
            get_notification();
        }else{
            message.setVisibility(View.VISIBLE);
            image.setVisibility(View.VISIBLE);
        }


        return view;

    }

    public void get_notification(){

        if(connectionDetection.isConnected()) {
            progressBar.setVisibility(View.VISIBLE);
            Api_Service requestInterface = Config.getClient().create(Api_Service.class);
//            Log.d("llll",SharedPrefManager.getInstance(context).getUser().getClient_id());

            Call<About_response> response1 = requestInterface.get_notifications(SharedPrefManager.getInstance(context).getUser().getClient_id());
            response1.enqueue(new Callback<About_response>() {

                @Override
                public void onResponse(Call<About_response> call, retrofit2.Response<About_response> response) {
                    About_response resp = response.body();
                    progressBar.setVisibility(View.INVISIBLE);
                    if (resp != null) {
                        if (resp.getSuccess() == 1) {
                            if (resp.getProduct().size()>0) {
                                message.setVisibility(View.INVISIBLE);
                                image.setVisibility(View.INVISIBLE);

                                Notifications_adapter mAdapter = new Notifications_adapter(activity, resp.getProduct());
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);
                                recyclerView.setLayoutManager(mLayoutManager);
                                recyclerView.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL));

                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                recyclerView.setAdapter(mAdapter);

                            }
                            else {
                                message.setVisibility(View.VISIBLE);
                                image.setVisibility(View.VISIBLE);
                            }
                            }else{
                                message.setVisibility(View.VISIBLE);
                                image.setVisibility(View.VISIBLE);
                            }
                        }else{
                        message.setVisibility(View.VISIBLE);
                        image.setVisibility(View.VISIBLE);
                    }

                }


                @Override
                public void onFailure(Call<About_response> call, Throwable t) {
                    Toast.makeText(getContext(), activity.getResources().getString(R.string.errortryagain), Toast.LENGTH_SHORT).show();
                    message.setVisibility(View.VISIBLE);
                    image.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });
        }else{
            Toast.makeText(getContext(),activity.getResources().getString(R.string.networkerror),Toast.LENGTH_SHORT).show();
            message.setVisibility(View.VISIBLE);
            image.setVisibility(View.VISIBLE);
        }




    }


}
