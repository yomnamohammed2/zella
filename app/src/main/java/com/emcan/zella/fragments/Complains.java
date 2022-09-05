package com.emcan.zella.fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;

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
import com.emcan.zella.Beans.Complain_response;
import com.emcan.zella.Config;
import com.emcan.zella.ConnectionDetection;
import com.emcan.zella.R;
import com.emcan.zella.SharedPrefManager;
import com.emcan.zella.adapters.Complains_adapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;


public class Complains extends Fragment {


    View view;
    ConnectionDetection connectionDetection;
    ProgressBar progressBar;
    ArrayList<About_response.Sofra> notifications;
    ImageView cart;
    Toolbar toolbar;
    TextView title, num;
    RelativeLayout no, yes;
    Typeface m_typeFace;
    Context context;
    ImageView image;
    TextView message;
    RecyclerView recyclerView;
    String message_type_id;
    String lang;
    Typeface typeface;
    AppCompatActivity activity;

    public Complains() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Complains newInstance(String param1, String param2) {
        Complains fragment = new Complains();
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
        view= inflater.inflate(R.layout.fragment_complains, container, false);
        activity= (AppCompatActivity) getActivity();
        lang= SharedPrefManager.getInstance(activity).getLang();
        if(lang.equals("en")){
            typeface = ResourcesCompat.getFont(activity, R.font.amaranth_bold);
        }
        if(lang.equals("ar")){
            typeface= ResourcesCompat.getFont(activity, R.font.bein_black);
        }
        connectionDetection=new ConnectionDetection(getContext());

        message =(TextView) view.findViewById(R.id.message);
        message.setTypeface(typeface);
        image = view.findViewById(R.id.image);
        recyclerView=(RecyclerView) view.findViewById(R.id.recycler_cart);
        progressBar=(ProgressBar) view.findViewById(R.id.progressBar);
        context=getContext();

        notifications=new ArrayList<>();

        if(SharedPrefManager.getInstance(context).isLoggedIn()) {
            get_notification();
        }else{
            message.setVisibility(View.VISIBLE);
            image.setVisibility(View.VISIBLE);
        }


        return view;
    }

    public void get_notification() {

        if (connectionDetection.isConnected()) {
            progressBar.setVisibility(View.VISIBLE);
            Api_Service requestInterface = Config.getClient().create(Api_Service.class);
//            Log.d("client_id", SharedPrefManager.getInstance(context).getUser().getClient_id());

            Call<Complain_response> response1 = requestInterface.get_complains(SharedPrefManager.getInstance(context).
                    getUser().getClient_id());
            response1.enqueue(new Callback<Complain_response>() {

                @Override
                public void onResponse(Call<Complain_response> call, retrofit2.Response<Complain_response> response) {
                    Complain_response resp = response.body();
                    progressBar.setVisibility(View.INVISIBLE);
                    if (resp != null) {
                        if (resp.getSuccess() == 1) {
                            if (resp.getProduct().size() > 0) {
                                message.setVisibility(View.INVISIBLE);
                                image.setVisibility(View.INVISIBLE);

                                Complains_adapter mAdapter = new Complains_adapter(context,resp.getProduct());
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                                recyclerView.setLayoutManager(mLayoutManager);
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
                public void onFailure(Call<Complain_response> call, Throwable t) {
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
