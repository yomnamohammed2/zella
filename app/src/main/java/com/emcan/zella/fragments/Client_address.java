package com.emcan.zella.fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.emcan.zella.Api_Service;
import com.emcan.zella.Beans.Address;
import com.emcan.zella.Beans.Address_Response;
import com.emcan.zella.Beans.Last_order_pojo;
import com.emcan.zella.Config;
import com.emcan.zella.ConnectionDetection;
import com.emcan.zella.R;
import com.emcan.zella.SharedPrefManager;
import com.emcan.zella.activities.MainActivity;
import com.emcan.zella.adapters.Address_adapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;


public class Client_address extends Fragment {

    ConnectionDetection connectionDetection;
    ProgressBar progressBar;
    ArrayList<Address> address;
    TextView title;
    ImageView cart;
    ArrayList<Integer> check;
    String address_id;
    int pos;
    Context context;
    String flag=null;
    String lang;
    Last_order_pojo.last order;
    String order_id,reorder,charge;
    Typeface typeface;

    double discount;


    public static Client_address newInstance(String order_id, String reorder, Last_order_pojo.last order,double discount,String charge) {
        Client_address fragment = new Client_address();
        Bundle args = new Bundle();
        args.putSerializable("order",order);
        args.putString("order_id",order_id);
        args.putString("reorder",reorder);
        args.putString("charge",charge);
        args.putDouble("discount",discount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            order= (Last_order_pojo.last) getArguments().getSerializable("order");
            order_id=  getArguments().getString("order_id");
            reorder=  getArguments().getString("reorder");
            charge=  getArguments().getString("charge");
            discount=  getArguments().getDouble("discount");
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View view=inflater.inflate(R.layout.fragment_client_address, container, false);

      context=getContext();

        final AppCompatActivity activity = (AppCompatActivity) getActivity();

        RelativeLayout bar = activity.findViewById(R.id.bar);
        bar.setVisibility(View.GONE);
        ((MainActivity) activity).select_icon("none");

       Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        ImageButton back=toolbar.findViewById(R.id.back);

        lang= SharedPrefManager.getInstance(activity).getLang();
        if(lang.equals("en")){
            typeface = ResourcesCompat.getFont(activity, R.font.amaranth_bold);
            back.setRotation(180);
        }
        if(lang.equals("ar")){
            typeface = ResourcesCompat.getFont(activity, R.font.bein_black);
        }

        //////////////////////////////////////////////////

        RelativeLayout no=toolbar.findViewById(R.id.no_cart);
        RelativeLayout yes=toolbar.findViewById(R.id.yes_cart);


        title=toolbar.getRootView().findViewById(R.id.toolbar_title);
        cart=toolbar.getRootView().findViewById(R.id.cart1);
        yes.setVisibility(View.INVISIBLE);
        no.setVisibility(View.VISIBLE);


        cart.setVisibility(View.INVISIBLE);


            title.setText(activity.getResources().getString(R.string.chooseadd));

        title.setTypeface(typeface);

        back.setVisibility(View.VISIBLE);
        ImageView menu=toolbar.findViewById(R.id.menu);
        menu.setVisibility(View.INVISIBLE);

        check=new ArrayList<>();


       final TextView message =view.findViewById(R.id.message);
        final ImageView no_address=view.findViewById(R.id.no_address);
        final RecyclerView recyclerView=view.findViewById(R.id.recycler_cart);
        progressBar=view.findViewById(R.id.progressBar);
//------------------------------add address from map -------------//
        Button add=view.findViewById(R.id.add);
        add.setTypeface(typeface);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new Map();
                Bundle bundle=new Bundle();
                bundle.putString("flag",flag);
                if(reorder!=null){
                    bundle.putSerializable("order",order);
                    bundle.putString("order_id",order_id);
                    bundle.putString("reorder",reorder);
                    bundle.putString("charge",charge);
                    bundle.putDouble("discount",discount);

                }
                fragment.setArguments(bundle);
                FragmentManager fragmentManager=activity.getSupportFragmentManager();
                // FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment).addToBackStack(null)
                        .commit();
            }
        });
        //-----------------confirm order---------------//
        Button confirm=view.findViewById(R.id.continu);
        confirm.setTypeface(typeface);

        if(getArguments()!=null&&getArguments().getString("flag")!=null) {

                confirm.setVisibility(View.GONE);
                flag=getArguments().getString("flag");
            }


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(address.size()>0){
                    if(address_id==null||address_id.equals("")){
                        Toast.makeText(context,activity.getResources().getString(R.string.choose),Toast.LENGTH_SHORT).show();
                    }else{


                        Address add=address.get(pos);

                        Log.d("addddd",add.getCharge()+"   ");

                        Bundle bundle=new Bundle();
                        bundle.putString("region",add.getRegion_name());
                        bundle.putString("block",add.getBlock());
                        bundle.putString("road",add.getRoad());
                        bundle.putString("building",add.getBuilding());
                        bundle.putString("flat",add.getFlat_number());
                        bundle.putString("note",add.getNote());
                        bundle.putString("address_id",address_id);
                        bundle.putString("charge",add.getCharge());
                        bundle.putString("mini",add.getMin_order());
                        bundle.putString("deliver_type","1");

                        if(reorder!=null){

                            bundle.putString("order_id",order_id);
                            bundle.putString("reorder",reorder);
                            bundle.putSerializable("order",order);
                            bundle.putDouble("discount",discount);

                        }

                        Fragment fragment=new Confirm_Order();
                        fragment.setArguments(bundle);
                        FragmentManager fragmentManager=activity.getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.container, fragment).addToBackStack(null)
                                .commit();

                    }

                }
                else{
                    Toast.makeText(context,activity.getResources().getString(R.string.addaddress),Toast.LENGTH_SHORT).show();
                }
            }
        });

        connectionDetection=new ConnectionDetection(context);


        if (connectionDetection.isConnected()) {
            progressBar.setVisibility(View.VISIBLE);
            message.setTypeface(typeface);
            Api_Service requestInterface = Config.getClient().create(Api_Service.class);
            Call<Address_Response> response1 = requestInterface.getAddress
                    (SharedPrefManager.getInstance(context).getUser().getClient_id(),lang,"");
            response1.enqueue(new Callback<Address_Response>() {
                @Override
                public void onResponse(Call<Address_Response> call, retrofit2.Response<Address_Response> response) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Address_Response resp = response.body();
                    if (resp != null) {
                        if (resp.getSuccess() == 1) {
                            address = resp.getProduct();
                            if (address.size() > 0) {
                                message.setVisibility(View.INVISIBLE);
                                no_address.setVisibility(View.INVISIBLE);
                                message.setTypeface(typeface);

                                for(int i=0;i<address.size();i++){
                                  check.add(0);
                                }


                                Address_adapter mAdapter = new Address_adapter(context, address,check,flag, new Address_adapter.ListAllListeners() {

                                    @Override
                                    public void onItemCheck(String checkBoxName, int position) {
                                      //  Toast.makeText(getActivity(), checkBoxName, Toast.LENGTH_SHORT).show();
                                        address_id=checkBoxName;
                                        pos=position;

                                    }

                                    @Override
                                    public void onItemUncheck(String checkBoxName, int position) {
                                        address_id=checkBoxName;
                                    }
                                });

                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
                                recyclerView.setLayoutManager(mLayoutManager);
//                                recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                recyclerView.setAdapter(mAdapter);


                            } else {
                                message.setVisibility(View.VISIBLE);
                                no_address.setVisibility(View.VISIBLE);
                            }

                        } else {
                            message.setVisibility(View.VISIBLE);
                            no_address.setVisibility(View.VISIBLE);
                        }
                    } else {
                        message.setVisibility(View.VISIBLE);
                        no_address.setVisibility(View.VISIBLE);
                    }
                }


                @Override
                public void onFailure(Call<Address_Response> call, Throwable t) {
                    progressBar.setVisibility(View.INVISIBLE);
                    message.setVisibility(View.VISIBLE);
                    no_address.setVisibility(View.VISIBLE);
                }
            });

        } else {
            Toast.makeText(context, activity.getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
            message.setVisibility(View.VISIBLE);
            no_address.setVisibility(View.VISIBLE);
        }



        return view;
    }



}
