package com.emcan.zella.fragments;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.emcan.zella.Api_Service;
import com.emcan.zella.Beans.Address;
import com.emcan.zella.Beans.Address_Response;
import com.emcan.zella.Beans.Last_order_pojo;
import com.emcan.zella.Beans.Region_Model;
import com.emcan.zella.Config;
import com.emcan.zella.ConnectionDetection;
import com.emcan.zella.R;
import com.emcan.zella.SharedPrefManager;
import com.emcan.zella.activities.MainActivity;
import com.emcan.zella.adapters.Region_Adapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;


public class Get_Address extends Fragment implements Get_Address_Id{


    Toolbar toolbar;
    TextView title,num;
    ImageView cart;
    ConnectionDetection connectionDetection;
    String street_txt,region_txt,latti,longi;
    String block_txt,building_txt,apartment_txt,note_txt,phone_txt;
    static PopupWindow popupWindow;
    ArrayList<Region_Model.Region_class> regions;
    static String region_id;

    static Region_Model.Region_class region_clas;

    EditText phone,block,street1,building,apartment,note;
    static RecyclerView recyclerView;
    static AutoCompleteTextView region1;
    static   ArrayList<Region_Model.Region_class> suggest;

    String lang;
    Typeface typeface;
   String flag=null;
   TextView t1,t2,t3,t4,t5,t6,t7;
    Last_order_pojo.last order;
    String order_id,reorder,charge;
    double discount;

    public Get_Address() {
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
        //--------------------tool bar----------------//

        View view=inflater.inflate(R.layout.fragment_get__address, container, false);
        t1=view.findViewById(R.id.t1);
        t2=view.findViewById(R.id.t2);
        t3=view.findViewById(R.id.t3);
        t4=view.findViewById(R.id.t4);
        t5=view.findViewById(R.id.t5);
        t6=view.findViewById(R.id.t6);
        t7=view.findViewById(R.id.t7);

        recyclerView=view.findViewById(R.id.region_recycler);

        final AppCompatActivity activity = (AppCompatActivity) getActivity();

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
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

        RelativeLayout bar = activity.findViewById(R.id.bar);
        bar.setVisibility(View.GONE);
        ((MainActivity) activity).select_icon("more");

        final RelativeLayout no=toolbar.findViewById(R.id.no_cart);
        RelativeLayout yes=toolbar.findViewById(R.id.yes_cart);


            title=toolbar.getRootView().findViewById(R.id.toolbar_title);
            cart=toolbar.getRootView().findViewById(R.id.cart1);
            yes.setVisibility(View.INVISIBLE);
            no.setVisibility(View.VISIBLE);


        cart.setVisibility(View.INVISIBLE);

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

        title.setTypeface(typeface);
        title.setText(activity.getResources().getString(R.string.detailadd));


        regions=new ArrayList<>();
        suggest=new ArrayList<>();

        back.setVisibility(View.VISIBLE);

        ImageView menu=toolbar.findViewById(R.id.menu);
        menu.setVisibility(View.INVISIBLE);


        connectionDetection=new ConnectionDetection(getContext());

        if(getArguments().getString("street")!=null){
             street_txt = getArguments().getString("street");
        }

       if(getArguments().getString("region")!=null){
            region_txt= getArguments().getString("region");
       }
        if(getArguments().getString("lat")!=null) {
             latti = getArguments().getString("lat");
        }
        if(getArguments().getString("long")!=null) {
             longi = getArguments().getString("long");
        }

        if(getArguments()!=null&&getArguments().getString("flag")!=null) {
            flag = getArguments().getString("flag");
        }
      //  Toast.makeText(getContext(),street+","+region+","+latti+","+longi,Toast.LENGTH_SHORT).show();

        if(getArguments()!=null&&getArguments().getString("reorder")!=null){
            reorder=getArguments().getString("reorder");
            order_id=getArguments().getString("order_id");
            charge=getArguments().getString("charge");
            discount=getArguments().getDouble("discount");
            order= (Last_order_pojo.last) getArguments().getSerializable("order");
        }

        phone=view.findViewById(R.id.phone);
        region1=view.findViewById(R.id.region);
        block=view.findViewById(R.id.block);
        street1=view.findViewById(R.id.road);
        building=view.findViewById(R.id.building);
        apartment=view.findViewById(R.id.apartment);
        note=view.findViewById(R.id.note);



        if(connectionDetection.isConnected()){
            Api_Service requestInterface = Config.getClient().create(Api_Service.class);
            Call<Region_Model> response1 = requestInterface.get_all_regions(lang,"");
            response1.enqueue(new Callback<Region_Model>() {
                @Override
                public void onResponse(Call<Region_Model> call, retrofit2.Response<Region_Model> response) {
                    if(response!=null){
                        if(response.body().getSuccess()==1) {
                            if (response.body().getProduct().size() > 0) {
                                regions = response.body().getProduct();
                            }
                        }
                    }


                }

                @Override
                public void onFailure(Call<Region_Model> call, Throwable t) {
                     Toast.makeText(activity, activity.getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
                }
            });



                }
        else{
            Toast.makeText(getContext(),activity.getResources().getString(R.string.networkerror),Toast.LENGTH_SHORT).show();
        }




        if(SharedPrefManager.getInstance(getContext()).getUser().getClient_phone()!=null){
            phone.setText(SharedPrefManager.getInstance(getContext()).getUser().getClient_phone());
        }
        street1.setText(street_txt);
      //  region1.setText(region_txt);


        region1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                region_id="";
                Region_Adapter mAdapter = new Region_Adapter(activity, regions,Get_Address.this);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(mAdapter);
                recyclerView.setVisibility(View.VISIBLE);
                region1.dismissDropDown();

                return false;

            }

        });

        region1.dismissDropDown();

        region1.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("jjjjjj", String.valueOf(count)+s);
//                region_id="";
                suggest.clear();
                if (count > 0) {
                    for (int i = 0; i < regions.size(); i++) {

                        if (regions.get(i).getRegion_name().toLowerCase().contains(s)) {
                            suggest.add(regions.get(i));
                            Log.d("jjjjjj", String.valueOf(count)+s);
                        }

                    }
                    if(suggest.size()>0) {

                        Region_Adapter mAdapter = new Region_Adapter(activity, suggest,Get_Address.this);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);
                        recyclerView.setLayoutManager(mLayoutManager);
//                        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
//
//                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(mAdapter);
                        recyclerView.setVisibility(View.VISIBLE);
                        region1.dismissDropDown();

                    }else{
                        recyclerView.setVisibility(View.GONE);
                        region1.dismissDropDown();
                    }

                } else {

                    Region_Adapter mAdapter = new Region_Adapter(activity, regions,Get_Address.this);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);
                    recyclerView.setLayoutManager(mLayoutManager);
//                    recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
////
//                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(mAdapter);
                    recyclerView.setVisibility(View.VISIBLE);
                    region1.dismissDropDown();
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                suggest.clear();
                region1.dismissDropDown();
                for (int i = 0; i < regions.size(); i++) {

                    if (regions.get(i).getRegion_name().toLowerCase().contains(s)) {
                        suggest.add(regions.get(i));

                    }

                }
                if(suggest.size()>0) {

                    Region_Adapter mAdapter = new Region_Adapter(activity, suggest,Get_Address.this);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);
                    recyclerView.setLayoutManager(mLayoutManager);
//                        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
//
//                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(mAdapter);
                    recyclerView.setVisibility(View.VISIBLE);
                    region1.dismissDropDown();
                }else{
                    recyclerView.setVisibility(View.GONE);
                    region1.dismissDropDown();

                }
            }
        });


        Button confirm_address=view.findViewById(R.id.confirm_address);
        confirm_address.setTypeface(typeface);
        street1.setTypeface(typeface);
        region1.setTypeface(typeface);
        phone.setTypeface(typeface);
        apartment.setTypeface(typeface);
        note.setTypeface(typeface);
        block.setTypeface(typeface);
        building.setTypeface(typeface);
        t1.setTypeface(typeface);
        t2.setTypeface(typeface);
        t3.setTypeface(typeface);
        t4.setTypeface(typeface);
        t5.setTypeface(typeface);
        t6.setTypeface(typeface);
        t7.setTypeface(typeface);




        confirm_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                street_txt=street1.getText().toString().trim();
                phone_txt=phone.getText().toString().trim();
                region_txt=region1.getText().toString().trim();
                block_txt=block.getText().toString().trim();
                building_txt=building.getText().toString().trim();
                apartment_txt=apartment.getText().toString().trim();
                note_txt=note.getText().toString().trim();

                if(phone_txt==null||phone_txt.equals("")) {
                    phone.requestFocus();
                }else{
                       phone.clearFocus();
                       if(region_id==null||region_id.equals(""))
                       {
                         Toast.makeText(getContext(),activity.getResources().getString(R.string.enterregion),Toast.LENGTH_SHORT).show();
                       }else{

                           region1.clearFocus();
                           if(block_txt==null||block_txt.equals("")){
                               block.requestFocus();
                           }
                           else{
                               block.clearFocus();

                               if(street_txt==null||street_txt.equals("")){
                                   street1.requestFocus();
                               }
                            else{
                             street1.clearFocus();
                           if(building_txt==null||building_txt.equals("")) {
                              building.requestFocus(); }
                                   else{
                              building.clearFocus();
                             if(connectionDetection.isConnected()){

                                 Api_Service requestInterface = Config.getClient().create(Api_Service.class);


                                     if(getArguments().getString("id")!=null&&!getArguments().getString("id").equals(""))
                                     {

                                         Address address=new Address();
                                         address.setBuilding(building_txt);
                                         address.setFlat_number(apartment_txt);
                                         address.setLang(longi);
                                         address.setLat(latti);
                                         address.setRoad(street_txt);
                                         address.setRegion_id(region_id);
                                         address.setBlock(block_txt);
                                         address.setClient_phone(phone_txt);
                                         address.setNote(note_txt);
                                         address.setClient_id( SharedPrefManager.getInstance(getContext()).getUser().getClient_id());
                                         address.setClient_address_id(getArguments().getString("id"));


                                         Call<Address_Response> response1 = requestInterface.edit_Address(address);
                                         response1.enqueue(new Callback<Address_Response>() {
                                             @Override
                                             public void onResponse(Call<Address_Response> call, retrofit2.Response<Address_Response> response) {

                                                 Address_Response resp = response.body();
                                                 if (resp != null) {
                                                     if (resp.getSuccess() == 1) {

                                                         FragmentManager fragmentManager = activity.getSupportFragmentManager();
                                                         fragmentManager.popBackStack();
                                                         fragmentManager.popBackStack();
                                                         fragmentManager.popBackStack();
                                                         Fragment fragment = new Client_address();
                                                         Bundle bundle = new Bundle();
                                                         if(flag!=null) {
                                                             bundle.putString("flag", "1");
                                                         }

                                                         fragment.setArguments(bundle);
                                                         fragmentManager.beginTransaction()
                                                                 .replace(R.id.container, fragment).addToBackStack(null)
                                                                 .commit();
                                                     }else{
                                                         Toast.makeText(getContext(),resp.getMessage(),Toast.LENGTH_SHORT).show();

                                                     }
                                                 }
                                             }

                                             @Override
                                             public void onFailure(Call<Address_Response> call, Throwable t) {
                                                // Toast.makeText(getContext(), "خطأ حاول مجددا", Toast.LENGTH_SHORT).show();
                                                 FragmentManager fragmentManager = activity.getSupportFragmentManager();
                                                 fragmentManager.popBackStack();
                                                 fragmentManager.popBackStack();
                                                 fragmentManager.popBackStack();
                                                 Fragment fragment = new Client_address();
                                                 if(flag!=null) {
                                                     Bundle bundle = new Bundle();

                                                     bundle.putString("flag", "1");
                                                     fragment.setArguments(bundle);
                                                 }

                                                 fragmentManager.beginTransaction()
                                                         .replace(R.id.container, fragment).addToBackStack(null)
                                                         .commit();
                                             }
                                         });

                                 }else {
                                         final Address address=new Address();
                                         address.setBuilding(building_txt);
                                         address.setFlat_number(apartment_txt);
                                         address.setLang(longi);
                                         address.setLat(latti);
                                         address.setRoad(street_txt);
                                         address.setRegion_id(region_id);
                                         address.setBlock(block_txt);
                                         address.setClient_phone(phone_txt);
                                         address.setNote(note_txt);
                                         address.setClient_id( SharedPrefManager.getInstance(getContext()).getUser().getClient_id());

                                     Call<Address_Response> response1 = requestInterface.add_address(address);

                                     response1.enqueue(new Callback<Address_Response>() {
                                         @Override
                                         public void onResponse(Call<Address_Response> call, retrofit2.Response<Address_Response> response) {

                                             Address_Response resp = response.body();
                                             if (resp != null) {
                                                 if (resp.getSuccess() == 1) {
                                                     Address addresss = resp.getProduct().get(0);
                                                     Bundle bundle = new Bundle();
                                                     bundle.putString("lat", latti);
                                                     bundle.putString("lang", longi);
                                                     bundle.putString("region", region_txt);
                                                     bundle.putString("block", block_txt);
                                                     bundle.putString("road", street_txt);
                                                     bundle.putString("building", building_txt);
                                                     bundle.putString("flat", apartment_txt);
                                                     bundle.putString("note", note_txt);
                                                     bundle.putString("charge", address.getCharge());
                                                     bundle.putString("address_id", addresss.getClient_address_id());
                                                     bundle.putString("charge",addresss.getCharge() );
                                                     bundle.putString("mini",addresss.getMin_order() );
                                                     bundle.putString("deliver_type","1");
                                                     bundle.putString("postal_code",address.getPostal_code());
                                                     bundle.putString("additional_code",address.getAdditional_code());

                                                     if(reorder!=null){
                                                         bundle.putSerializable("order",order);
                                                         bundle.putString("order_id",order_id);
                                                         bundle.putDouble("discount",discount);
                                                         bundle.putString("reorder",reorder);
                                                     }
                                                     if(flag==null){
                                                         Fragment fragment = new Confirm_Order();
                                                         fragment.setArguments(bundle);
                                                         FragmentManager fragmentManager = activity.getSupportFragmentManager();
                                                         fragmentManager.beginTransaction()
                                                                 .replace(R.id.container, fragment).addToBackStack(null)
                                                                 .commit();
                                                     }else {
                                                         Fragment fragment = new Client_address();
                                                         Bundle bundle1=new Bundle();

                                                         bundle1.putString("flag","1");
                                                         fragment.setArguments(bundle1);
                                                         FragmentManager fragmentManager = activity.getSupportFragmentManager();
                                                         fragmentManager.beginTransaction()
                                                                 .replace(R.id.container, fragment).addToBackStack(null)
                                                                 .commit();
                                                     }
                                                 }else{
                                                     Toast.makeText(getContext(),resp.getMessage(),Toast.LENGTH_SHORT).show();

                                                 }
                                             }
                                         }

                                         @Override
                                         public void onFailure(Call<Address_Response> call, Throwable t) {
                                             Toast.makeText(getContext(), activity.getResources().getString(R.string.errortryagain), Toast.LENGTH_SHORT).show();
                                         }
                                     });
                                 }
                               }else{

                         Toast.makeText(getContext(),activity.getResources().getString(R.string.networkerror),Toast.LENGTH_SHORT).show();
                                       }
                                   }
                               }


                           }

                       }


                    }




            }
        });

        return view;
    }


    @Override
    public void get_regiov(Region_Model.Region_class regionClass){
        region_clas=regionClass;
        region_id=regionClass.getRegion_id();
        recyclerView.setVisibility(View.GONE);
        region1.setText(regionClass.getRegion_name());
        region1.clearFocus();
        suggest.clear();
        region1.dismissDropDown();

    }
}
