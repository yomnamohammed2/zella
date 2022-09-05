package com.emcan.zella.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.Typeface;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emcan.zella.Api_Service;
import com.emcan.zella.Beans.Additions_Model;
import com.emcan.zella.Beans.Address;
import com.emcan.zella.Beans.Address_Response;
import com.emcan.zella.Beans.Avaliable_Model;
import com.emcan.zella.Beans.Branch_Model;
import com.emcan.zella.Beans.Cart_Response2;
import com.emcan.zella.Beans.Check;
import com.emcan.zella.Beans.Delivery_Response;
import com.emcan.zella.Beans.Prices_Model;
import com.emcan.zella.Beans.Region_Model;
import com.emcan.zella.Beans.Remove_Response;
import com.emcan.zella.Config;
import com.emcan.zella.ConnectionDetection;
import com.emcan.zella.GET_DATA;
import com.emcan.zella.R;
import com.emcan.zella.SharedPrefManager;
import com.emcan.zella.activities.MainActivity;
import com.emcan.zella.adapters.Address_adapter;
import com.emcan.zella.adapters.Branches_Adapter;
import com.emcan.zella.adapters.Cart_Adapter;
import com.emcan.zella.adapters.Region_Adapter;
import com.emcan.zella.databinding.FragmentMyCartBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Locale;
import java.util.StringTokenizer;

import retrofit2.Call;
import retrofit2.Callback;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


public class MyCart extends Fragment implements GET_DATA , OnMapReadyCallback ,Get_Address_Id{

    private GoogleMap googleMap;
    String block_txt,building_txt,apartment_txt,note_txt,phone_txt,street_txt,region_txt;
    ImageView  dropPinView;
    EditText phone,block,street1,building,apartment,note;
    MapView mMapView;
    ArrayList<Address> address;
    double latti,longi;
    ArrayList<Integer> check;

    int pos;
    String address_id="";

    LocationManager locationManager;
    private boolean isGPSEnabled,isNetworkEnabled;
    ArrayList<Region_Model.Region_class> suggest;
    ArrayList<Region_Model.Region_class> regions;
    String region_id;
    Region_Model.Region_class region_clas;
    static final int REQUEST_LOCATION = 1;
    public static final int LOCATION_UPDATE_MIN_DISTANCE = 10;
    public static final int LOCATION_UPDATE_MIN_TIME = 5000;
    private LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {

                locationManager.removeUpdates(mLocationListener);

                if(googleMap!= null) {
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom((new LatLng(location.getLatitude(), location.getLongitude())), 15));
                }
            } else {
                // Logger.d("Location is null");
            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

ConnectionDetection connectionDetection;
ProgressBar progressBar;
ArrayList<Cart_Response2.CartModel2> cart1;
    ArrayList<Branch_Model.Branch> branchs;
    RecyclerView recyclerView,recyclerView_regions;
    AutoCompleteTextView region1;
    Branch_Model.Branch branch;
String deliver_id="";
ImageView cart;
Toolbar toolbar;
    TextView title, num;
    RelativeLayout no, yes;
    PopupWindow popupWindow;
    AppCompatActivity activity;

    FragmentMyCartBinding binding;
    public static RelativeLayout rel1,rel2,rel3,rel5;
    public static TextView txt,total_txt,vat,vat_value;
    public static TextView txt1,discount;

    public static TextView txt2,total_;
    public static ImageView image,menu,delete;
    String lang;
    Typeface typeface;
    Context context;
    View view;
    public static TextView message;
    public static Button continu,shop;
    RelativeLayout pickup;





    public MyCart() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= FragmentMyCartBinding.inflate(inflater, container, false);
        v=binding.getRoot();
        view=binding.getRoot();


        activity = (AppCompatActivity) getActivity();
        delete=activity.findViewById(R.id.delete);

        RelativeLayout bar = activity.findViewById(R.id.bar);
        bar.setVisibility(View.GONE);
        ((MainActivity) activity).select_icon("none");


        lang= SharedPrefManager.getInstance(activity).getLang();
        if(lang.equals("en")){
            typeface = ResourcesCompat.getFont(activity, R.font.amaranth_bold);
        }
        if(lang.equals("ar")){
            typeface= ResourcesCompat.getFont(activity, R.font.bein_black);
        }
        init();
        setToolbar();
        binding.message.setTypeface(typeface);
        binding.message2.setTypeface(typeface);
        binding.addItems.setTypeface(typeface);

        binding.addItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)activity).get_categories();

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete();
            }
        });

        continu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

             confirm();
            }
        });

        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Slider();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment).addToBackStack("xyz")
                        .commit();
            }
        });


        getCart();
        getBranches();
        get_client_address();


        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        delete.setVisibility(View.GONE);
    }

    private void init(){

        address=new ArrayList<>();
        progressBar=(ProgressBar) view.findViewById(R.id.progressBar);
        context=getContext();
        check=new ArrayList<>();

        message =(TextView) view.findViewById(R.id.message);
        message.setTypeface(typeface);
        image = view.findViewById(R.id.image);
        recyclerView=(RecyclerView) view.findViewById(R.id.recycler_cart);
        locationManager = (LocationManager)getActivity()
                .getSystemService(Context.LOCATION_SERVICE);
        this.isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        this.isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);


        rel1= view.findViewById(R.id.rel1);
        rel2= view.findViewById(R.id.rel2);
        rel3= view.findViewById(R.id.rel3);
        rel5=view.findViewById(R.id.rel5);
        txt=(TextView) view.findViewById(R.id.txt);
        txt1=(TextView) view.findViewById(R.id.txt1);
        txt2=(TextView) view.findViewById(R.id.txt2);
        total_txt=(TextView) view.findViewById(R.id.total_txt);
        total_=(TextView) view.findViewById(R.id.total_);
        discount=(TextView) view.findViewById(R.id.discount);
        vat=view.findViewById(R.id.vat);
        vat_value=view.findViewById(R.id.vat_value);
        txt.setTypeface(typeface);
        txt1.setTypeface(typeface);
        txt2.setTypeface(typeface);
        total_txt.setTypeface(typeface);
        total_.setTypeface(typeface);
        discount.setTypeface(typeface);
        vat_value.setTypeface(typeface);
        vat.setTypeface(typeface);

        continu=(Button) view.findViewById(R.id.continu);
        continu.setTypeface(typeface);
        shop=(Button) view.findViewById(R.id.shop);
        shop.setTypeface(typeface);
        connectionDetection=new ConnectionDetection(context);

    }

    private void setToolbar(){

        toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        no=toolbar.findViewById(R.id.no_cart);
        yes=toolbar.findViewById(R.id.yes_cart);

        num=toolbar.getRootView().findViewById(R.id.num);
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
        title.setText(activity.getResources().getString(R.string.cart));
        ImageButton back=toolbar.findViewById(R.id.back);
        back.setVisibility(View.VISIBLE);



        cart.setVisibility(View.VISIBLE);
        ImageView menu=toolbar.findViewById(R.id.menu);
        menu.setVisibility(View.VISIBLE);

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void confirm(){
        if (cart1.size() > 1){
            continu.setEnabled(false);
            if(connectionDetection.isConnected()) {
                Api_Service requestInterface = Config.getClient().create(Api_Service.class);

                Call<Avaliable_Model> response1 = requestInterface.aval();
                response1.enqueue(new Callback<Avaliable_Model>() {
                    @Override
                    public void onResponse(Call<Avaliable_Model> call, retrofit2.Response<Avaliable_Model> response) {
                        Avaliable_Model resp = response.body();
                        continu.setEnabled(true);
                        if (resp != null) {
                            if (resp.getSuccess() == 1) {
                                if (resp.getProduct().get(0).getAccept_orders().equals("1")) {


                                    if(!deliver_id.equals("")) {
                                        if(deliver_id.equals("2")) {

                                            if(branchs!=null &&branchs.size()>0) {
                                                selectBranches();
                                            }else {

                                            Fragment fragment = new Confirm_Order();
                                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                            fragmentManager.beginTransaction()
                                                    .replace(R.id.container, fragment).addToBackStack("xyz")
                                                    .commit();
                                            }
                                        }else{
                                            if(address!=null&&address.size()>0){
                                                selectAddress();

                                            }else {
                                                no_address_popup();
                                            }
                                        }

                                    }else{

                                        Toast.makeText(getContext(), context.getResources().getString(R.string.select_delivery_), Toast.LENGTH_SHORT).show();

                                    }

                                } else {
                                    LayoutInflater inflater = (LayoutInflater) getContext().
                                            getSystemService(LAYOUT_INFLATER_SERVICE);
                                    View customView = inflater.inflate(R.layout.message_popup, null);
                                    popupWindow = new PopupWindow(
                                            customView,
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            //   700,
                                            LinearLayout.LayoutParams.MATCH_PARENT, true
                                    );

                                    RelativeLayout out=customView.findViewById(R.id.out);
                                    out.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            popupWindow.dismiss();
                                        }
                                    });
                                    Button no1 = customView.findViewById(R.id.ok);
                                    TextView text = customView.findViewById(R.id.text);
                                    text.setText(context.getResources().getString(R.string.cantorder));
                                    no1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            popupWindow.dismiss();
                                        }
                                    });
                                    popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                                }
                            }
                        }
                    }


                    @Override
                    public void onFailure(Call<Avaliable_Model> call, Throwable t) {
                        Toast.makeText(getContext(), context.getResources().getString(R.string.errortryagain), Toast.LENGTH_SHORT).show();
                    }
                });
            }else{
                Toast.makeText(getContext(),context.getResources().getString(R.string.networkerror),Toast.LENGTH_SHORT).show();
            }



        }else{
            Toast.makeText(getContext(), context.getResources().getString(R.string.emptycart), Toast.LENGTH_SHORT).show();

        }

    }

    public  void getCart(){
        if(SharedPrefManager.getInstance(getContext()).isLoggedIn()) {
            if (connectionDetection.isConnected()) {
                progressBar.setVisibility(View.VISIBLE);
                Log.d("pppppppppppp",SharedPrefManager.getInstance(context).getUser().getClient_id());
                final Api_Service requestInterface = Config.getClient().create(Api_Service.class);
                Call<Cart_Response2> response1 = requestInterface.getCart(SharedPrefManager.getInstance(context).getUser().getClient_id(),lang,"");
                response1.enqueue(new Callback<Cart_Response2>() {
                    @Override
                    public void onResponse(Call<Cart_Response2> call, retrofit2.Response<Cart_Response2> response) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Cart_Response2 resp = response.body();
                        if (resp != null) {
                            if (resp.getSuccess() == 1) {
                                cart1 = resp.getProduct();
                                if (cart1.size() > 0) {
                                    get_delivery();

                                    continu.setVisibility(View.VISIBLE);
                                    shop.setVisibility(View.VISIBLE);
                                    message.setVisibility(View.INVISIBLE);
                                    image.setVisibility(View.INVISIBLE);
                                    delete.setVisibility(View.GONE);

                                    Cart_Adapter mAdapter = new Cart_Adapter(activity, cart1, view,null);
                                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);
                                    recyclerView.setLayoutManager(mLayoutManager);
                                    recyclerView.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL));

                                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                                    recyclerView.setAdapter(mAdapter);

                                    SharedPrefManager.getInstance(context).reset_Cart();
                                    for (int i = 0; i < cart1.size() - 1; i++) {
                                        SharedPrefManager.getInstance(context).add_Cart();

                                    }
                                    setToolbar();

                                    if(Float.parseFloat(cart1.get(cart1.size() - 1).getDiscount_percentage())>0){
                                        rel1.setVisibility(View.VISIBLE);
                                        rel2.setVisibility(View.VISIBLE);
                                        rel3.setVisibility(View.VISIBLE);
                                        rel5.setVisibility(View.VISIBLE);
//                                      total_txt.setText(cart1.get(cart1.size() - 1).getTotal_amount());

                                        total_txt.setText(cart1.get(cart1.size() - 1).getTotal_amount());
                                        discount.setText(cart1.get(cart1.size() - 1).getSummary());
                                        total_.setText(cart1.get(cart1.size() - 1).getTotal_amount_after_disc());
                                        txt1.setText(context.getResources().getString(R.string.discountt)+" ("+cart1.get(cart1.size() - 1).getDiscount_percentage()+"%) : ");
                                        vat.setText(context.getResources().getString(R.string.vat)+" ("+cart1.get(cart1.size() - 1).getVat()+"%) : ");
                                        vat_value.setText(cart1.get(cart1.size() - 1).getVat_value());
                                    }else {
                                        rel1.setVisibility(View.VISIBLE);
                                        rel2.setVisibility(View.GONE);
                                        rel3.setVisibility(View.VISIBLE);
                                        rel5.setVisibility(View.VISIBLE);
                                        total_txt.setText(cart1.get(cart1.size() - 1).getTotal_amount());
                                        total_.setText(cart1.get(cart1.size() - 1).getTotal_amount_after_disc());
                                        txt1.setText(context.getResources().getString(R.string.discountt)+" ("+cart1.get(cart1.size() - 1).getDiscount_percentage()+"%) : ");
                                        vat.setText(context.getResources().getString(R.string.vat)+" ("+cart1.get(cart1.size() - 1).getVat()+"%) : ");
                                        vat_value.setText(cart1.get(cart1.size() - 1).getVat_value());
                                        discount.setText(cart1.get(cart1.size() - 1).getSummary());
                                    }

                                } else {
                                    binding.nocart.setVisibility(View.VISIBLE);
                                    SharedPrefManager.getInstance(context).reset_Cart();

                                }

                            } else {
                                binding.nocart.setVisibility(View.VISIBLE);
                                SharedPrefManager.getInstance(context).reset_Cart();


                            }
                        } else {
                            binding.nocart.setVisibility(View.VISIBLE);
                            SharedPrefManager.getInstance(context).reset_Cart();

                        }
                    }


                    @Override
                    public void onFailure(Call<Cart_Response2> call, Throwable t) {
                        progressBar.setVisibility(View.INVISIBLE);
                        binding.nocart.setVisibility(View.VISIBLE);
                        SharedPrefManager.getInstance(context).reset_Cart();

                    }
                });

            } else {
                Toast.makeText(getContext(), context.getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
            }
        }else{

            binding.nocart.setVisibility(View.VISIBLE);
        }

    }

    private void get_delivery(){

        Api_Service requestInterface = Config.getClient().create(Api_Service.class);
        Call<Delivery_Response> response1 = requestInterface.get_Delivery(lang);
        response1.enqueue(new Callback<Delivery_Response>() {
            @Override
            public void onResponse(Call<Delivery_Response> call, retrofit2.Response<Delivery_Response> response) {
                Delivery_Response resp = response.body();
                if (resp != null) {
                    if (resp.getSuccess() == 1) {

                       ArrayList<Delivery_Response.Delivery_Model> product=resp.getProduct();

                        if(product.get(0).getDisplay().equals("1")){
                            binding.deliveryRel.setVisibility(View.VISIBLE);

                            binding.deliveryRel1.setVisibility(View.VISIBLE);
                            binding.deliveryTxt.setText(resp.getProduct().get(0).getName());
                            deliver_id=resp.getProduct().get(0).getDeliver_id();
                            binding.check1.setVisibility(View.VISIBLE);
                            binding.deliveryRel1.setBackground(context.getResources().getDrawable(R.drawable.rounded_3));

                            binding.deliveryRel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    deliver_id=resp.getProduct().get(0).getDeliver_id();

                                    binding.check1.setVisibility(View.VISIBLE);
                                    binding.deliveryRel1.setBackground(context.getResources().getDrawable(R.drawable.rounded_3));

                                    binding.check.setVisibility(View.GONE);
                                    binding.pickupRel1.setBackground(context.getResources().getDrawable(R.drawable.rounded_frame15));
                                }
                            });
                        }
//                        if(product.get(2).getDisplay().equals("1")){
//                            incafe.setVisibility(View.VISIBLE);
//                        }
                        if(product.size()>1&&product.get(1).getDisplay().equals("1")){
                            binding.pickupRel.setVisibility(View.VISIBLE);

                            binding.pickupRel1.setVisibility(View.VISIBLE);
                            binding.pickupTxt.setText(resp.getProduct().get(1).getName());

                            binding.pickupRel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    deliver_id=resp.getProduct().get(1).getDeliver_id();
                                    binding.check.setVisibility(View.VISIBLE);
                                    binding.pickupRel1.setBackground(context.getResources().getDrawable(R.drawable.rounded_3));

                                    binding.check1.setVisibility(View.GONE);
                                    binding.deliveryRel1.setBackground(context.getResources().getDrawable(R.drawable.rounded_frame15));
                                }
                            });                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Delivery_Response> call, Throwable t) {
                Toast.makeText(activity, activity.getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public  void delete(){
        if(SharedPrefManager.getInstance(getContext()).isLoggedIn()) {

            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(LAYOUT_INFLATER_SERVICE);
            View customView = inflater.inflate(R.layout.dialoge_alert, null);
            popupWindow = new PopupWindow(
                    customView,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    //   700,
                    LinearLayout.LayoutParams.MATCH_PARENT, true
            );
            RelativeLayout out=customView.findViewById(R.id.out);
            out.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupWindow.dismiss();
                }
            });
            popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
            Button no=customView.findViewById(R.id.no);
            Button yes=customView.findViewById(R.id.yes);
            TextView text=customView.findViewById(R.id.text);

            text.setText(activity.getResources().getString(R.string.surecartempty));
            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupWindow.dismiss();
                }
            });
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupWindow.dismiss();
                    if (connectionDetection.isConnected()) {
                        progressBar.setVisibility(View.VISIBLE);
                        Api_Service requestInterface = Config.getClient().create(Api_Service.class);
//            Log.d("llll", SharedPrefManager.getInstance(context).getUser().getClient_id());

                        Call<Check> response1 = requestInterface.emptyCart(SharedPrefManager.getInstance(context).
                                getUser().getClient_id());
                        response1.enqueue(new Callback<Check>() {

                            @Override
                            public void onResponse(Call<Check> call, retrofit2.Response<Check> response) {
                                Check resp = response.body();
                                progressBar.setVisibility(View.INVISIBLE);
                                if (resp != null) {
                                    if (resp.getSuccess() == 1) {
                                        recyclerView.setVisibility(View.GONE);
                                        message.setVisibility(View.VISIBLE);
                                        image.setVisibility(View.VISIBLE);
                                        total_txt.setVisibility(View.GONE);
                                        rel1.setVisibility(View.GONE);
                                        rel2.setVisibility(View.GONE);
                                        rel3.setVisibility(View.GONE);
                                        rel5.setVisibility(View.GONE);
                                        continu.setVisibility(View.GONE);
                                        shop.setVisibility(View.GONE);
                                        num.setText("");
                                        delete.setVisibility(View.GONE);
                                        SharedPrefManager.getInstance(getContext()).reset_Cart();
                                    }
                                } else {

                                }


                            }


                            @Override
                            public void onFailure(Call<Check> call, Throwable t) {
                                Toast.makeText(getContext(), activity.getResources().getString(R.string.errortryagain), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        });
                    } else {
                        Toast.makeText(getContext(), activity.getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();

                    }
                }
            });

        }}

    public void getBranches() {
        if (connectionDetection.isConnected()) {
            progressBar.setVisibility(View.VISIBLE);
            Api_Service requestInterface = Config.getClient().create(Api_Service.class);
            Branch_Model model=new Branch_Model();
            model.setLang(SharedPrefManager.getInstance(activity).getLang());
            model.setUser_lat(SharedPrefManager.getInstance(activity).getLatti());
            model.setUser_long(SharedPrefManager.getInstance(activity).getLongi());
            Call<Branch_Model> response1 = requestInterface.get_Branches(lang);

            Log.d("ppppppppppppppp",new Gson().toJson(model));


            response1.enqueue(new Callback<Branch_Model>() {
                @Override
                public void onResponse(Call<Branch_Model> call, retrofit2.Response<Branch_Model> response) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Branch_Model resp = response.body();
                    if (resp != null) {

                        if (resp.getSuccess() == 1) {
                            branchs=new ArrayList<>();
                            branchs = resp.getProduct();

                        }
                    }
                }

                @Override
                public void onFailure(Call<Branch_Model> call, Throwable t) {
                    progressBar.setVisibility(View.INVISIBLE);

                }
            });
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(getContext(), activity.getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
        }
    }
    private void selectBranches(){

        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        final View custom_view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_branches, null);

        popupWindow = new PopupWindow(custom_view, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);

        TextView  txt =custom_view.findViewById(R.id.txt);
        txt.setTypeface(typeface);

       RecyclerView  recyclerView_branches=custom_view.findViewById(R.id.recycler_branches);

        if (branchs.size() > 0) {

            Branches_Adapter mAdapter = new Branches_Adapter(context, branchs, MyCart.this);
            // mAdapter.notifyDataSetChanged();
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            recyclerView_branches.setLayoutManager(mLayoutManager);
            recyclerView_branches.addItemDecoration(new DividerItemDecoration(activity,
                    DividerItemDecoration.VERTICAL));

            recyclerView_branches.setItemAnimator(new DefaultItemAnimator());
            recyclerView_branches.setAdapter(mAdapter);

        }


        Button continu= custom_view.findViewById(R.id.continu);

        continu.setTypeface(typeface);



        continu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SharedPrefManager.getInstance(context).isLoggedIn()) {


                    if(branch!=null&&branch.getBranch_id()!="") {
                        popupWindow.dismiss();
                        Bundle bundle = new Bundle();
                        bundle.putString("deliver_type", deliver_id);
                        bundle.putString("branch_name", branch.getName());
                        bundle.putString("branch_id", branch.getBranch_id());



                        Fragment fragment = new Confirm_Order();
                        fragment.setArguments(bundle);
                        FragmentManager fragmentManager = activity.getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.container, fragment).addToBackStack(null)
                                .commit();
                    }else{
                        Toast.makeText(context,activity.getResources().getString(R.string.select_branch_),Toast.LENGTH_SHORT).show();

                    }
                }else{
                    Toast.makeText(context,activity.getResources().getString(R.string.signinfirst),Toast.LENGTH_SHORT).show();
                }
            }
        });


        progressBar=custom_view.findViewById(R.id.progressBar);

        getBranches();


        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);

        popupWindow.setAnimationStyle(R.style.popup_window_animation_phone);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

    }


    public  void selectAddress(){

            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            final View custom_view = LayoutInflater.from(getContext()).inflate(R.layout.select_address_popup, null);

            popupWindow = new PopupWindow(custom_view, WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT);

            TextView  txt =custom_view.findViewById(R.id.txt);
            txt.setTypeface(typeface);

            RecyclerView  recyclerView_address=custom_view.findViewById(R.id.recycler_address);

            if (address.size() > 0) {

                for(int i=0;i<address.size();i++){
                    check.add(0);
                }


                Address_adapter mAdapter = new Address_adapter(context, address,check,null, new Address_adapter.ListAllListeners() {

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
                recyclerView_address.setLayoutManager(mLayoutManager);
//                                recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

                recyclerView_address.setItemAnimator(new DefaultItemAnimator());
                recyclerView_address.setAdapter(mAdapter);

            }

        Button add= custom_view.findViewById(R.id.add_addres);

        add.setTypeface(typeface);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                add_Address();
            }
        });

        Button continu= custom_view.findViewById(R.id.continu);

            continu.setTypeface(typeface);

            continu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(SharedPrefManager.getInstance(context).isLoggedIn()) {
                        popupWindow.dismiss();

                        if(!address_id.equals("")){

                        Address add=address.get(pos);

//                        Log.d("addddd",add.getCharge()+"   ");

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



                        Fragment fragment=new Confirm_Order();
                        fragment.setArguments(bundle);
                        FragmentManager fragmentManager=activity.getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.container, fragment).addToBackStack(null)
                                .commit();
                        }else{
                            Toast.makeText(context,activity.getResources().getString(R.string.select_address),Toast.LENGTH_SHORT).show();

                        }
                    }else{
                        Toast.makeText(context,activity.getResources().getString(R.string.signinfirst),Toast.LENGTH_SHORT).show();
                    }
                }
            });


            progressBar=custom_view.findViewById(R.id.progressBar);

            getBranches();


            popupWindow.setOutsideTouchable(true);
            popupWindow.setFocusable(true);

            popupWindow.setAnimationStyle(R.style.popup_window_animation_phone);
            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);




    }
    @Override
    public void get_ID(Branch_Model.Branch branch) {

        this.branch=branch;
    }
    @Override
    public void remove_ID() {

        this.branch=null;
    }


    @Override
    public void get_add(Additions_Model.Addition addition, int i) {

    }

    @Override
    public void get_without(Remove_Response.Removes remove_id, int i) {

    }

    @Override
    public void getSize(Prices_Model.Price size) {

    }

    @Override
    public void get_Price(ArrayList<Prices_Model.Price> prices_models) {

    }

    public void no_address_popup(){

        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        final View custom_view = LayoutInflater.from(getContext()).inflate(R.layout.noaddress_popu, null);

        popupWindow = new PopupWindow(custom_view, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);

        TextView  txt =custom_view.findViewById(R.id.txt);
        txt.setTypeface(typeface);

        TextView  txt1 =custom_view.findViewById(R.id.txt1);
        txt1.setTypeface(typeface);

        Button continu= custom_view.findViewById(R.id.continu);
        continu.setTypeface(typeface);


        continu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popupWindow.dismiss();

                add_Address();
            }
        });


        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);

        popupWindow.setAnimationStyle(R.style.popup_window_animation_phone);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    private void add_Address(){

        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        final View custom_view = LayoutInflater.from(getContext()).inflate(R.layout.add_address_popup, null);

        popupWindow = new PopupWindow(custom_view, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);


        mMapView = (MapView) custom_view.findViewById(R.id.mapView);

        mMapView.onCreate(null);
        mMapView.onResume();


        mMapView.getMapAsync(MyCart.this);

        ScrollView scrollView=custom_view.findViewById(R.id.scrollview);

        mMapView.setOnTouchListener(new View.OnTouchListener() {

            public boolean dispatchTouchEvent(MotionEvent ev) {
                /**
                 * Request all parents to relinquish the touch events
                 */
                scrollView.requestDisallowInterceptTouchEvent(true);
                return true;
            }
            @Override
            public boolean onTouch(View view, MotionEvent ev) {
                int action = ev.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        scrollView.requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        scrollView.requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return true;

            }
        });



        // Handle MapView's touch events.


         dropPinView = new ImageView(getContext());
        dropPinView.setImageResource(R.drawable.marker);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(80, 80, Gravity.CENTER);
        float density = getResources().getDisplayMetrics().density;
        params.bottomMargin = (int) (12 * density);
        dropPinView.setLayoutParams(params);

        mMapView.addView(dropPinView);


        regions=new ArrayList<>();
        suggest=new ArrayList<>();

        recyclerView_regions=custom_view.findViewById(R.id.region_recycler);
        phone=custom_view.findViewById(R.id.phone);
        region1=custom_view.findViewById(R.id.region);
        block=custom_view.findViewById(R.id.block);
        street1=custom_view.findViewById(R.id.road);
        building=custom_view.findViewById(R.id.building);
        apartment=custom_view.findViewById(R.id.apartment);
        note=custom_view.findViewById(R.id.note);


        getRegions();


        if(SharedPrefManager.getInstance(getContext()).getUser().getClient_phone()!=null){
            phone.setText(SharedPrefManager.getInstance(getContext()).getUser().getClient_phone());
        }
        //  region1.setText(region_txt);


        region1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                region_id="";
                Region_Adapter mAdapter = new Region_Adapter(activity, regions,MyCart.this);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);
                recyclerView_regions.setLayoutManager(mLayoutManager);
                recyclerView_regions.setAdapter(mAdapter);
                recyclerView_regions.setVisibility(View.VISIBLE);
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

                        Region_Adapter mAdapter = new Region_Adapter(activity, suggest,MyCart.this);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);
                        recyclerView_regions.setLayoutManager(mLayoutManager);
//                        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
//
//                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView_regions.setAdapter(mAdapter);
                        recyclerView_regions.setVisibility(View.VISIBLE);
                        region1.dismissDropDown();

                    }else{
                        recyclerView_regions.setVisibility(View.GONE);
                        region1.dismissDropDown();
                    }

                } else {

                    Region_Adapter mAdapter = new Region_Adapter(activity, regions,MyCart.this);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);
                    recyclerView_regions.setLayoutManager(mLayoutManager);
//                    recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
////
//                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView_regions.setAdapter(mAdapter);
                    recyclerView_regions.setVisibility(View.VISIBLE);
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

                    Region_Adapter mAdapter = new Region_Adapter(activity, suggest,MyCart.this);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);
                    recyclerView_regions.setLayoutManager(mLayoutManager);
//                        recyclerView_regions.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
//
//                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView_regions.setAdapter(mAdapter);
                    recyclerView_regions.setVisibility(View.VISIBLE);
                    region1.dismissDropDown();
                }else{
                    recyclerView_regions.setVisibility(View.GONE);
                    region1.dismissDropDown();

                }
            }
        });


        Button confirm_address=custom_view.findViewById(R.id.confirm_address);
        confirm_address.setTypeface(typeface);
        street1.setTypeface(typeface);
        region1.setTypeface(typeface);
        phone.setTypeface(typeface);
        apartment.setTypeface(typeface);
        note.setTypeface(typeface);
        block.setTypeface(typeface);
        building.setTypeface(typeface);


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
                                    Log.d("pppppppppppppp","ppppppppppppppppp");
                                  getAddress();
                                  popupWindow.dismiss();
                                }
                            }


                        }

                    }


                }




            }
        });


        progressBar=custom_view.findViewById(R.id.progressBar);


        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);

        popupWindow.setAnimationStyle(R.style.popup_window_animation_phone);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this. googleMap= googleMap;
      if(  googleMap!=null){
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //Initialize Google PLay Services
        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        } else {

            if (connectionDetection.isConnected()) {
                MapsInitializer.initialize(getContext());


                googleMap.setMyLocationEnabled(true);
                    getLocation();

                Log.d("jjjjj", "my location: " + latti + " " + longi);
                LatLng gps = new LatLng(latti, longi);

                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(gps, 16));


            } else {
                Toast.makeText(getContext(), activity
                        .getResources().getString(R.string.networkerror), Toast.LENGTH_LONG).show();

            }
        }
        }

    }
    void getLocation(){

        if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

        }else {

            Location location;

            if(isNetworkEnabled==true) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, mLocationListener);

                try {

                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                    if (location != null) {
                        latti = location.getLatitude();
                        longi = location.getLongitude();

                        Log.e("Maps Fragment", "Location is: " + String.valueOf(latti) + ", " + String.valueOf(longi));
                    } else {
                        Log.e("Maps fragment", "Unable to find current location.");
                    }

                } catch (Exception e) {
                    Log.d("kk", e.getMessage());
                }
            }else {
                if (isGPSEnabled == true) {

                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                            LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, mLocationListener);

                    try {

                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                        if (location != null) {
                            latti = location.getLatitude();
                            longi = location.getLongitude();

                            Log.e("Maps Fragment", "Location is: " + String.valueOf(latti) + ", " + String.valueOf(longi));
                        } else {
                            Log.e("Maps fragment", "Unable to find current location.");
                        }

                    } catch (Exception e) {
                        Log.d("kk", e.getMessage());
                    }
                }

            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_LOCATION:
                if (grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED) {
                    if(mMapView!=null) {
                        mMapView.getMapAsync(this);
                    }

                } else {
                }
                break;
        }
    }


public  void getRegions(){
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




}

    @Override
    public void get_regiov(Region_Model.Region_class regionClass){
        region_clas=regionClass;
        region_id=regionClass.getRegion_id();
        if(recyclerView_regions!=null) {
            recyclerView_regions.setVisibility(View.GONE);
        }
        if(region1!=null) {
            region1.setText(regionClass.getRegion_name());
            region1.clearFocus();
            region1.dismissDropDown();

        }
        suggest.clear();

    }

    public  void add_address_api(
    ){
        if(connectionDetection.isConnected()){

            Api_Service requestInterface = Config.getClient().create(Api_Service.class);


            final Address address=new Address();
            address.setBuilding(building_txt);
            address.setFlat_number(apartment_txt);
            address.setLang(String.valueOf(longi));
            address.setLat(String.valueOf(latti));
            address.setRoad(street_txt);
            address.setRegion_id(region_id);
            address.setBlock(block_txt);
            address.setClient_phone(phone_txt);
            address.setNote(note_txt);
            address.setClient_id( SharedPrefManager.getInstance(getContext()).getUser().getClient_id());

            Log.d("ppppppppppppppp",new Gson().toJson(address));


            Call<Address_Response> response1 = requestInterface.add_address(address);

            response1.enqueue(new Callback<Address_Response>() {
                @Override
                public void onResponse(Call<Address_Response> call, retrofit2.Response<Address_Response> response) {

                    Address_Response resp = response.body();
                    if (resp != null) {
                        if (resp.getSuccess() == 1) {
                            Address addresss = resp.getProduct().get(0);
                            Bundle bundle = new Bundle();
                            bundle.putString("lat", String.valueOf(latti));
                            bundle.putString("lang", String.valueOf(longi));
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



                            Fragment fragment = new Confirm_Order();
                            fragment.setArguments(bundle);
                            FragmentManager fragmentManager = activity.getSupportFragmentManager();
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
                    Toast.makeText(getContext(), activity.getResources().getString(R.string.errortryagain), Toast.LENGTH_SHORT).show();
                }
            });

        }else{

            Toast.makeText(getContext(),activity.getResources().getString(R.string.networkerror),Toast.LENGTH_SHORT).show();
        }
    }


        public void getAddress(){
            try {

                LatLng position = googleMap.getProjection().fromScreenLocation(new Point(dropPinView.getLeft() +
                        (dropPinView.getWidth() / 2), dropPinView.getBottom()));
                latti=position.latitude;
                longi=position.longitude;

                Log.d("ooooooooooooooo","oooooooooooooooooooo");

                add_address_api();

            } catch (Exception e) {
                e.printStackTrace();

            }

    }

    public void get_client_address(){
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

                        }

                    }
                }

                @Override
                public void onFailure(Call<Address_Response> call, Throwable t) {

                }
            });

        } else {
            Toast.makeText(context, activity.getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();

        }

    }
}
