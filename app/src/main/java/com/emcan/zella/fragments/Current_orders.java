package com.emcan.zella.fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emcan.zella.Api_Service;
import com.emcan.zella.Beans.Check;
import com.emcan.zella.Beans.Current_order_model;
import com.emcan.zella.Beans.LoyaltyPointsResponse;
import com.emcan.zella.Config;
import com.emcan.zella.ConnectionDetection;
import com.emcan.zella.R;
import com.emcan.zella.SharedPrefManager;
import com.emcan.zella.adapters.Current_order_adapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Current_orders extends Fragment {
    // TODO: Rename parameter arguments, choose names that match

    ConnectionDetection connectionDetection;
    TextView message;
    ImageView image;
    ArrayList<Current_order_model.Current_order> orders;
    String id;
    ProgressBar progressBar;
    PopupWindow popupWindow;

    Context mcontext;
    int flag = 0;
    int view_id = -1;
    LayoutInflater inflate;
    LinearLayout linear;
    AppCompatActivity activity;
    FragmentManager fragmentManager;
    String trackk;
    Current_order_model.Current_order order;
    String lang;
    Typeface typeface;

    View view;
    ArrayList<Current_order_model.Order_item> order2;

    public Current_orders() {
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
        view = inflater.inflate(R.layout.fragment_current_orders, container, false);


      init();
        if(lang.equals("en")){
            typeface = ResourcesCompat.getFont(activity, R.font.amaranth_bold);
        }
        if(lang.equals("ar")){
            typeface= ResourcesCompat.getFont(activity, R.font.bein_black);
        }
        message = (TextView) view.findViewById(R.id.message);
        message.setTypeface(typeface);
        orders = new ArrayList<>();

        if(getArguments()!=null) {
            trackk = getArguments().getString("id");
        }


        if (SharedPrefManager.getInstance(mcontext).isLoggedIn()) {
            if (connectionDetection.isConnected()) {
                progressBar.setVisibility(View.VISIBLE);
                final Api_Service requestInterface = Config.getClient().create(Api_Service.class);
                Call<Current_order_model> response1 = requestInterface.get_current_orders(SharedPrefManager.
                        getInstance(mcontext).getUser().getClient_id(),lang);
                response1.enqueue(new Callback<Current_order_model>() {
                    @Override
                    public void onResponse(Call<Current_order_model> call, retrofit2.Response<Current_order_model> response) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Current_order_model resp = response.body();
                        if (resp != null) {
                            if (resp.getSuccess() == 1) {
                                orders = resp.getProduct();

                                if (orders.size() > 0) {
                                    image.setVisibility(View.INVISIBLE);

                                    for (int i = 0; i < orders.size(); i++) {

                                        linear = (LinearLayout) view.findViewById(R.id.container);

                                        inflate = LayoutInflater.from(mcontext);
                                        final LinearLayout layout = (LinearLayout) inflate.inflate(R.layout.last_order_layout3,
                                                null, false);

                                        layout.setId(i);
                                        order2 = orders.get(i).getItems();

                                        RelativeLayout track_order=layout.findViewById(R.id.track_rel);
                                        RelativeLayout track_rel=layout.findViewById(R.id.track);

                                        Button deliver_order=layout.findViewById(R.id.deliver_order);
                                        int finalI = i;
                                        if(orders.get(i).getOrder_status().equals("1")
                                        &&orders.get(i).getOrder_follow().equals("2")){
                                            deliver_order.setVisibility(View.VISIBLE);
                                        }else{
                                            deliver_order.setVisibility(View.GONE);

                                        }
                                        deliver_order.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                deliverOrder(orders.get(finalI).getOrder_id(),deliver_order);
                                            }
                                        });

                                        Current_order_adapter mAdapter = new Current_order_adapter(mcontext, order2, layout);
                                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mcontext);
                                        final RecyclerView recyclerView = (RecyclerView) layout.
                                                findViewById(R.id.recycler_last_order);
                                        recyclerView.setVisibility(View.GONE);
                                        recyclerView.setLayoutManager(mLayoutManager);
                                        recyclerView.addItemDecoration(new DividerItemDecoration(activity,
                                                DividerItemDecoration.VERTICAL));
                                        layout.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                                if (flag != 0) {
                                                    if (view_id == layout.getId()) {
                                                        recyclerView.setVisibility(View.GONE);
                                                        track_order.setVisibility(View.GONE);
                                                        track_rel.setVisibility(View.GONE);
                                                        flag = 0;
                                                        view_id = -1;
                                                    } else {

                                                        LinearLayout linearLayout = linear.findViewById(view_id);
                                                        linearLayout.getChildAt(1).setVisibility(View.GONE);
                                                        RelativeLayout trac=linearLayout.findViewById(R.id.track_rel);
                                                        RelativeLayout trac2=linearLayout.findViewById(R.id.track);
                                                        trac2.setVisibility(View.GONE);
                                                        trac.setVisibility(View.GONE);
                                                        view_id = layout.getId();
                                                        recyclerView.setVisibility(View.VISIBLE);
                                                        track_order.setVisibility(View.VISIBLE);
                                                        track_rel.setVisibility(View.VISIBLE);

                                                    }


                                                } else {
                                                    flag = 1;
                                                    view_id = layout.getId();
                                                    recyclerView.setVisibility(View.VISIBLE);
                                                    track_order.setVisibility(View.VISIBLE);
                                                    track_rel.setVisibility(View.VISIBLE);
                                                }


                                            }
                                        });

                                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                                        recyclerView.setAdapter(mAdapter);

                                        TextView restaurant = layout.findViewById(R.id.name);
                                        restaurant.setText(mcontext.getResources().getString(R.string.netprice)+" "+
                                                orders.get(i).getNet_price()+ " " + mcontext.getResources().getString(R.string.bhd)   );
                                        restaurant.setTypeface(typeface);

                                        TextView date = layout.findViewById(R.id.date);
                                        TextView orderno = layout.findViewById(R.id.orderno);
                                        orderno.setTypeface(typeface);
                                        date.setTypeface(typeface);
                                        date.setText( orders.get(i).getOrder_date());
                                        orderno.setText( mcontext.getResources().getString(R.string.order_num)+" "+
                                                orders.get(i).getOrder_id());



                                        TextView state_txt1=layout.findViewById(R.id.state1_txt);
                                        TextView state_txt2=layout.findViewById(R.id.state2_txt);
                                        TextView state_txt3=layout.findViewById(R.id.state3_txt);

                                        state_txt1.setTypeface(typeface);
                                        state_txt2.setTypeface(typeface);
                                        state_txt3.setTypeface(typeface);

                                        RelativeLayout rel1=layout.findViewById(R.id.rel1);
                                        RelativeLayout rel2=layout.findViewById(R.id.rel2);
                                        RelativeLayout rel3=layout.findViewById(R.id.rel3);

                                        RelativeLayout sep1=layout.findViewById(R.id.sep1);
                                        RelativeLayout sep2=layout.findViewById(R.id.sep2);

                                        ImageView preparing=layout.findViewById(R.id.preparing);
                                        ImageView delivery=layout.findViewById(R.id.delivery);
                                        ImageView pickup=layout.findViewById(R.id.pickup);

                                        ImageView state1=layout.findViewById(R.id.stat1_gray);
                                        ImageView state2=layout.findViewById(R.id.stat2_gray);
                                        ImageView state3=layout.findViewById(R.id.stat3_gray);

                                        ImageView backk=layout.findViewById(R.id.backk);
                                        TextView track_txt=layout.findViewById(R.id.track_txt);
                                        track_txt.setTypeface(typeface);

                                        track_order.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                                if(track_rel.getVisibility()==View.VISIBLE){
                                                    track_rel.setVisibility(View.GONE);

                                                    backk.setRotation(0);
                                                }else{
                                                    track_rel.setVisibility(View.VISIBLE);
                                                    if(lang.equals("en")) {
                                                        backk.setRotation(90);
                                                    }else{
                                                        backk.setRotation(-90);

                                                    }

                                                }

                                            }
                                        });


                                        if(orders.get(i).getDeliver_id()!=null&&orders.get(i).getDeliver_id().equals("1")) {
                                            if (orders.get(i).getOrder_status().equals("1")) {

                                                state1.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.check_green));

                                                sep1.setBackgroundColor(mcontext.getResources().getColor(R.color.colorAccent));
                                            }

                                            //-------------------order status---------------//
                                            if (orders.get(i).getOrder_status().equals("2")) {
                                                state1.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.check_green));

                                                sep1.setBackgroundColor(mcontext.getResources().getColor(R.color.colorAccent));
                                                state2.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.check_green));

                                                sep2.setBackgroundColor(mcontext.getResources().getColor(R.color.colorAccent));
                                            }
                                        }

                                     if(orders.get(i).getDeliver_id()!=null&&orders.get(i).getDeliver_id().equals("2")) {
                                         if (orders.get(i).getOrder_status().equals("1")) {

                                             state1.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.check_green));

                                         }
                                         if (orders.get(i).getOrder_status().equals("2")) {
                                             sep1.setBackgroundColor(mcontext.getResources().getColor(R.color.colorAccent));

                                             state1.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.check_green));

                                         }
                                         sep2.setVisibility(View.GONE);
                                         rel3.setVisibility(View.GONE);
                                         pickup.setVisibility(View.GONE);

                                         delivery.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.track3));
                                         state_txt2.setText(mcontext.getResources().getString(R.string.pickup));


                                     }
//                                        else {
////
//                                                track.setVisibility(View.VISIBLE);
//                                                track_img.setVisibility(View.VISIBLE);
//                                            }


                                        linear.addView(layout);

                                    }
                                } else {
                                    message.setVisibility(View.VISIBLE);
                                }
                            } else {
                                message.setVisibility(View.VISIBLE);
                            }
                        } else {
                            message.setVisibility(View.VISIBLE);
                        }
                    }


                    @Override
                    public void onFailure(Call<Current_order_model> call, Throwable t) {
                        progressBar.setVisibility(View.INVISIBLE);
                        message.setVisibility(View.VISIBLE);
                        image.setVisibility(View.VISIBLE);
                    }
                });

            } else {
                Toast.makeText(mcontext, activity.getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
            }
        } else {
            message.setVisibility(View.VISIBLE);
        }

//        if(trackk!=null){
//            for(int i=0;i<orders.size();i++){
//                if(trackk.equals(orders.get(i).getOrder_id())){
//
//                    if(orders.get(i).getDeliver_id().equals("1")) {
//                        Fragment fragment = Track_order.newInstance(orders.get(i));
//                        fragmentManager.beginTransaction()
//                                .replace(R.id.container, fragment).addToBackStack(null)
//                                .commit();
//                    }else{
//                        Fragment fragment = Track_Order_DineIn.newInstance(orders.get(i));
//                        fragmentManager.beginTransaction()
//                                .replace(R.id.container, fragment).addToBackStack(null)
//                                .commit();
//                    }
//                }
//            }
//        }

        return view;
    }

    private void init(){
        mcontext = getContext();
        activity = (AppCompatActivity) getActivity();
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        image =  view.findViewById(R.id.image);
        connectionDetection = new ConnectionDetection(mcontext);

        lang= SharedPrefManager.getInstance(activity).getLang();
        fragmentManager = activity.getSupportFragmentManager();
    }

    public void deliverOrder(String id ,Button check){

        if (connectionDetection.isConnected()) {
            Log.d("pppppppppppp",id);
            Api_Service requestInterface = Config.getClient().create(Api_Service.class);
            Call<Check> response1 = requestInterface.deliverOrder(id ,lang);
            response1.enqueue(new Callback<Check>() {
                @Override
                public void onResponse(Call<Check> call, Response<Check> response) {
                    Check resp = response.body();
                    if (resp != null) {
                        if (resp.getSuccess() == 1) {
                              Toast.makeText(getContext(),resp.getMessage(), Toast.LENGTH_SHORT).show();
                              check.setVisibility(View.GONE);

                        }
                    } else {
                        //  Toast.makeText(getContext(), "خطأ حاول مجددا", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Check> call, Throwable t) {

                    //   Toast.makeText(getContext(), "خطأ حاول مجددا", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getContext(), activity.getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
        }
    }
}

