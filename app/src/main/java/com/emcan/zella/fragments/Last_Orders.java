package com.emcan.zella.fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;

import android.view.Gravity;
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
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emcan.zella.Api_Service;
import com.emcan.zella.Beans.Address;
import com.emcan.zella.Beans.Avaliable_Model;
import com.emcan.zella.Beans.Delivery_Response;
import com.emcan.zella.Beans.Last_order_pojo;
import com.emcan.zella.Config;
import com.emcan.zella.ConnectionDetection;
import com.emcan.zella.R;
import com.emcan.zella.SharedPrefManager;
import com.emcan.zella.adapters.Last_order_adapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


public class Last_Orders extends Fragment {

    TextView title,num;
    ImageView cart;
    Toolbar toolbar;
    ProgressBar progressBar;
    ConnectionDetection connectionDetection;
    TextView message;
    ImageView image;
    ArrayList<Last_order_pojo.last> orders;
    LinearLayout linear;
    ArrayList<Last_order_pojo.last.Last> order2;
    ArrayList<Address> address;
    String order_id;
    Double discount=0.0;
    PopupWindow popupWindow;
    String address_id;
    int flag=0;
    int view_id=-1;
    String charge;
    Context context;
    AppCompatActivity activity;
    RelativeLayout payment;
    Button online,cash;
    Last_order_pojo.last order;
    Button re_order;
    View view ;
    TextView txt1_;
    RelativeLayout incafe,delivery,takeaway;
    TextView txt,txt1,txt2;
    ArrayList <Delivery_Response.Delivery_Model>product;
    String deliver_id;
    String lang;
    Typeface typeface;

    public Last_Orders() {
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
        view=inflater.inflate(R.layout.fragment_last__orders, container, false);
        context=getContext();
        //--------------------tool bar----------------//

        activity = (AppCompatActivity) getActivity();
        lang= SharedPrefManager.getInstance(activity).getLang();
        if(lang.equals("en")){
            typeface = ResourcesCompat.getFont(activity, R.font.amaranth_bold);
        }
        if(lang.equals("ar")){
            typeface= ResourcesCompat.getFont(activity, R.font.bein_black);
        }
        progressBar=view.findViewById(R.id.progressBar);
        message = (TextView) view.findViewById(R.id.message);
        message.setTypeface(typeface);
        image =  view.findViewById(R.id.image);
        connectionDetection=new ConnectionDetection(context);


        if(SharedPrefManager.getInstance(context).isLoggedIn()) {
            if (connectionDetection.isConnected()) {
                progressBar.setVisibility(View.VISIBLE);
                final Api_Service requestInterface = Config.getClient().create(Api_Service.class);
                Call<Last_order_pojo> response1 = requestInterface.get_last_orders(SharedPrefManager.
                        getInstance(context).getUser().getClient_id(),lang);
                response1.enqueue(new Callback<Last_order_pojo>() {
                    @Override
                    public void onResponse(Call<Last_order_pojo> call, retrofit2.Response<Last_order_pojo> response) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Last_order_pojo resp = response.body();
                        if (resp != null) {
                            if (resp.getSuccess() == 1) {
                                orders = resp.getProduct();
                                if (orders.size() > 0) {

                                    image.setVisibility(View.INVISIBLE);
                                    for (int i = 0; i < orders.size(); i++) {

                                        linear = (LinearLayout) view.findViewById(R.id.container);

                                        LayoutInflater inflate = LayoutInflater.from(context);

                                        final    LinearLayout   layout = (LinearLayout) inflate.inflate(R.layout.last_order_layout2, null, false);

                                        layout.setId(Integer.parseInt(orders.get(i).getOrder_id()));
                                        order2 = orders.get(i).getItems();

                                        Last_order_adapter mAdapter = new Last_order_adapter(context, order2);
                                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
                                        final RecyclerView recyclerView = (RecyclerView) layout.findViewById(R.id.recycler_last_order);
                                        recyclerView.setVisibility(View.GONE);
                                        recyclerView.setLayoutManager(mLayoutManager);
                                        recyclerView.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL));
                                        layout.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                                if(flag!=0){
                                                    if (view_id == layout.getId()) {
                                                        recyclerView.setVisibility(View.GONE);
                                                        flag = 0;
                                                        view_id = -1;
                                                    }else {

                                                        LinearLayout linearLayout = linear.findViewById(view_id);
                                                        linearLayout.getChildAt(1).setVisibility(View.GONE);
                                                        view_id = layout.getId();
                                                        recyclerView.setVisibility(View.VISIBLE);
                                                    }

                                                }else{
                                                    flag=1;
                                                    view_id=layout.getId();
                                                    recyclerView.setVisibility(View.VISIBLE);
                                                }

                                            }
                                        });

                                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                                        recyclerView.setAdapter(mAdapter);

                                        TextView restaurant = layout.findViewById(R.id.name);
                                        restaurant.setText(context.getResources().getString(R.string.netprice)+" "+
                                                orders.get(i).getNet_price()+ " " + context.getResources().getString(R.string.bhd)   );
                                        restaurant.setTypeface(typeface);

                                        TextView date = layout.findViewById(R.id.date);
                                        TextView orderno = layout.findViewById(R.id.orderno);
                                        orderno.setTypeface(typeface);
                                        date.setTypeface(typeface);
                                        date.setText( orders.get(i).getOrder_date());
                                        orderno.setText( context.getResources().getString(R.string.order_num)+" "+
                                                orders.get(i).getOrder_id());


                                        linear.addView(layout); }
                                }
                                else {
                                    message.setVisibility(View.VISIBLE); }
                            }else {
                                message.setVisibility(View.VISIBLE); }
                        }else {
                            message.setVisibility(View.VISIBLE); }
                    }

                    @Override
                    public void onFailure(Call<Last_order_pojo> call, Throwable t) {
                        progressBar.setVisibility(View.INVISIBLE);
                        message.setVisibility(View.VISIBLE);
                    }
                });

            } else {
                Toast.makeText(context, activity.getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
            }
        }else{
            message.setVisibility(View.VISIBLE);
        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }


}
