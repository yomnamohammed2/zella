package com.emcan.zella.fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emcan.zella.Api_Service;
import com.emcan.zella.Beans.About_response;
import com.emcan.zella.Config;
import com.emcan.zella.ConnectionDetection;
import com.emcan.zella.R;
import com.emcan.zella.SharedPrefManager;
import com.emcan.zella.adapters.Messages_Adapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;


public class Message_tab extends Fragment {
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
   String message_type_id;
   AppCompatActivity activity;
   String lang;
   Typeface typeface;


    public Message_tab() {
        // Required empty public constructor
    }


    public static Message_tab newInstance(String message_type_id) {
        Message_tab fragment = new Message_tab();
        Bundle args = new Bundle();
        args.putString("id", message_type_id);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            message_type_id = getArguments().getString("id");
            Log.d("message_type_id",message_type_id+"");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_message_tab, container, false);

        connectionDetection=new ConnectionDetection(getContext());
        activity= (AppCompatActivity) getActivity();
        lang= SharedPrefManager.getInstance(activity).getLang();
        if(lang.equals("en")){
            typeface = ResourcesCompat.getFont(activity, R.font.amaranth_bold);
        }
        if(lang.equals("ar")){
            typeface = ResourcesCompat.getFont(activity, R.font.bein_black);
        }
        message =(TextView) view.findViewById(R.id.message);
        message.setTypeface(typeface);
        image = view.findViewById(R.id.image);
        recyclerView=(RecyclerView) view.findViewById(R.id.recycler_cart);
        progressBar=(ProgressBar) view.findViewById(R.id.progressBar);
        message.setTypeface(typeface);

        if(message_type_id!=null&&message_type_id.equals("2")){
            message.setText(getResources().getString(R.string.nooffers));
        }

        notifications=new ArrayList<>();

        get_notification();

        //  message.setVisibility(View.VISIBLE);
        return view;
    }

    public void get_notification() {

        if (connectionDetection.isConnected()) {
            progressBar.setVisibility(View.VISIBLE);
            Api_Service requestInterface = Config.getClient().create(Api_Service.class);
//            Log.d("llll", SharedPrefManager.getInstance(context).getUser().getClient_id());

            Call<About_response> response1 = requestInterface.get_messages_by_type(SharedPrefManager.getInstance(context).
                    getUser().getClient_id(),message_type_id);
            response1.enqueue(new Callback<About_response>() {

                @Override
                public void onResponse(Call<About_response> call, retrofit2.Response<About_response> response) {
                    About_response resp = response.body();
                    progressBar.setVisibility(View.INVISIBLE);
                    if (resp != null) {
                        if (resp.getSuccess() == 1) {
                            if (resp.getProduct().size() > 0) {
                                message.setVisibility(View.INVISIBLE);
                                image.setVisibility(View.INVISIBLE);

                                Messages_Adapter mAdapter = new Messages_Adapter(activity, resp.getProduct());
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);
                                recyclerView.setLayoutManager(mLayoutManager);
//                                recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
//
//                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                recyclerView.setAdapter(mAdapter);

                            } else {
                                message.setVisibility(View.VISIBLE);
                                image.setVisibility(View.VISIBLE);
                            }
                        } else {
                            message.setVisibility(View.VISIBLE);
                            image.setVisibility(View.VISIBLE);
                        }
                    } else {
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
        } else {
            Toast.makeText(getContext(), activity.getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
            message.setVisibility(View.VISIBLE);
            image.setVisibility(View.VISIBLE);
        }
    }



}
