package com.emcan.zella.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.emcan.zella.Api_Service;
import com.emcan.zella.Beans.Additions_Model;
import com.emcan.zella.Beans.Branch_Model;
import com.emcan.zella.Beans.Parent_Category;
import com.emcan.zella.Beans.Parent_Category_Model;
import com.emcan.zella.Beans.PopupModel;
import com.emcan.zella.Beans.Prices_Model;
import com.emcan.zella.Beans.Remove_Response;
import com.emcan.zella.Beans.SliderResponse;
import com.emcan.zella.Config;
import com.emcan.zella.ConnectionDetection;
import com.emcan.zella.GET_DATA;
import com.emcan.zella.R;
import com.emcan.zella.SharedPrefManager;
import com.emcan.zella.activities.MainActivity;
import com.emcan.zella.adapters.Branches_Adapter;
import com.emcan.zella.adapters.MainSliderAdapter;
import com.emcan.zella.adapters.Recycler_Home_Adapter;
import com.emcan.zella.adapters.Sections_Adapter;
import com.emcan.zella.adapters.SliderAdapterExample2;
import com.emcan.zella.databinding.FragmentSliderBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.yarolegovich.discretescrollview.DSVOrientation;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;

public class Slider extends Fragment implements GET_DATA,
        DiscreteScrollView.OnItemChangedListener<Recycler_Home_Adapter.MyViewHolder>,
        View.OnClickListener {
    SliderView slider;
    ConnectionDetection connectionDetection;
    AppCompatActivity activity;
    ImageView[] dots;
    Toolbar toolbar;
    PopupWindow popupWindow;
    FragmentManager fragmentManager;
    TextView title, num;
    ImageView cart;
    RecyclerView recyclerView_branches;
    ArrayList <Branch_Model.Branch> branchs;
//    private InfiniteScrollAdapter<?> infiniteAdapter1,infiniteAdapter2;
    public static int positionInDataSet;

    Context context;
    String lang;
    Typeface typeface;
    ImageButton back;
    FragmentSliderBinding binding;
    RelativeLayout yes,no;

    LocationManager locationManager;
    Location location;
    double latti,longi;
    Geocoder geocoder;
    List<Address> addresses;
    String city,country;
    public static final int LOCATION_UPDATE_MIN_DISTANCE = 10;
    public static final int LOCATION_UPDATE_MIN_TIME = 5000;
    private LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=FragmentSliderBinding.inflate(inflater,container,false);
        View v=binding.getRoot();

        activity= (AppCompatActivity) getActivity();
        fragmentManager =activity.getSupportFragmentManager();
        slider=v.findViewById(R.id.slider);
        context=getContext();
        connectionDetection=new ConnectionDetection(activity);

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        back=activity.findViewById(R.id.back);
        back.setVisibility(View.GONE);

         no = toolbar.getRootView().findViewById(R.id.no_cart);
         yes = toolbar.getRootView().findViewById(R.id.yes_cart);

        num = toolbar.getRootView().findViewById(R.id.num);
        lang= SharedPrefManager.getInstance(activity).getLang();
        if(lang.equals("en")){
            typeface = ResourcesCompat.getFont(activity, R.font.amaranth_bold);
        }
        if(lang.equals("ar")){
            typeface= ResourcesCompat.getFont(activity, R.font.bein_black);
        }

        updateToolbar();

        ImageView menu = toolbar.findViewById(R.id.menu);
        menu.setVisibility(View.VISIBLE);


        RelativeLayout bar = activity.findViewById(R.id.bar);
        bar.setVisibility(View.VISIBLE);
        ((MainActivity) activity).select_icon("home");

        context=getContext();

        getslider();

//        get_categories();


            locationManager = (LocationManager) getActivity()
                    .getSystemService(Context.LOCATION_SERVICE);

            if (ActivityCompat.checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getContext(),
                            Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION},
                        1);
            } else {

                if (ActivityCompat.checkSelfPermission(getContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(getContext(),
                                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ) {
                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                                LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, mLocationListener);
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                    }
                    //      Toast.makeText(getContext(), "Location is " + String.valueOf(latti) + ", " + String.valueOf(longi), Toast.LENGTH_LONG);

                    if (location != null) {
                        latti = location.getLatitude();
                        longi = location.getLongitude();
                        geocoder = new Geocoder(getContext(), Locale.getDefault());
                        try {
                            addresses = geocoder.getFromLocation(latti, longi, 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (addresses != null && addresses.size() > 0) {

                            city = addresses.get(0).getAddressLine(0);
                            country = addresses.get(0).getCountryName();
                        }

                        Log.e("Maps Fragment", "Location is: " + String.valueOf(latti) + ", " + String.valueOf(longi) + " " + city + " " + country);

                        SharedPrefManager.getInstance(getContext()).setLatti(String.valueOf(latti));
                        SharedPrefManager.getInstance(getContext()).setLongi(String.valueOf(longi));
                    } else {
                        Log.e("Maps fragment", "Unable to find current location.");
                    }
                }

            }


        return v;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 1:

                if (ActivityCompat.checkSelfPermission(getContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(getContext(),
                                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                            LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, mLocationListener);
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                    //      Toast.makeText(getContext(), "Location is " + String.valueOf(latti) + ", " + String.valueOf(longi), Toast.LENGTH_LONG);

                    if (location != null) {
                        latti = location.getLatitude();
                        longi = location.getLongitude();
                        geocoder = new Geocoder(getContext(), Locale.getDefault());
                        try {
                            addresses = geocoder.getFromLocation(latti, longi, 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (addresses != null && addresses.size() > 0) {
                            city = addresses.get(0).getLocality();
                            //  String state = addresses.get(0).getSubAdminArea();
                            country = addresses.get(0).getCountryName();
                        }

                        Log.e("Maps Fragment", "Location is: " + String.valueOf(latti) + ", " + String.valueOf(longi)+" "+city+" "+country);

                        SharedPrefManager.getInstance(getContext()).setLatti(String.valueOf(latti));
                        SharedPrefManager.getInstance(getContext()).setLongi(String.valueOf(longi));
                    } else {
                        Log.e("Maps fragment", "Unable to find current location.");
                    }

                }


        }
    }
    public void getslider() {

        final Api_Service requestInterface = Config.getClient().create(Api_Service.class);
        if (connectionDetection.isConnected()) {
            String id="";
            if(SharedPrefManager.getInstance(context).isLoggedIn()){

                id=SharedPrefManager.getInstance(context).getUser().getClient_id();
            }
            Call<SliderResponse> response = requestInterface.get_Slider(SharedPrefManager.getInstance(activity).getLang(),
          id);
            response.enqueue(new Callback<SliderResponse>() {
                @Override
                public void onResponse(Call<SliderResponse> call, retrofit2.Response<SliderResponse> response) {

                    SliderResponse resp = response.body();
                    if (resp != null) {
                        if (resp.getSuccess() == 1) {

//                            int cart_num=resp.getCart_count();
//                            SharedPrefManager.getInstance(context).reset_Cart();
//                            if (cart_num > 0) {
//                                for (int i = 0; i < cart_num; i++) {
//                                    SharedPrefManager.getInstance(context).add_Cart();
//                                }
//                            }
//
//                            updateToolbar();
                            if (resp.getSlider()!=null&&resp.getSlider().size() > 0) {
                                slider.setVisibility(View.VISIBLE);

                                MainSliderAdapter sliderAdapter1 = new MainSliderAdapter(activity,resp.getSlider());
                                slider.setSliderAdapter(sliderAdapter1);
                            }

                            ArrayList<Parent_Category> parent = resp.getCategories();
                            if (parent.size() > 0) {
                                binding.sectionsRel.setVisibility(View.VISIBLE);
//                                binding.txt1.setPaintFlags(binding.txt1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

                                Sections_Adapter sections_adapter=new Sections_Adapter(getContext(),parent);

                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context,
                                        LinearLayoutManager.HORIZONTAL, false);
                                binding.recyclerSections.setLayoutManager(mLayoutManager);
                                binding.recyclerSections.setItemAnimator(new DefaultItemAnimator());
                                binding.recyclerSections.setAdapter(sections_adapter);
                            }

                            if(resp.getMost_selling()!=null&&resp.getMost_selling().size()>0){

                                binding.mostSellingRel.setVisibility(View.VISIBLE);
                                binding.mostSellingTxt.setTypeface(typeface);
                                Recycler_Home_Adapter sections_adapter=new Recycler_Home_Adapter(context,resp.getMost_selling(),
                                        Slider.this);

                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context,
                                        LinearLayoutManager.HORIZONTAL, false);
                                binding.mostSelling.setLayoutManager(mLayoutManager);
                                binding.mostSelling.setItemAnimator(new DefaultItemAnimator());
                                binding.mostSelling.setAdapter(sections_adapter);

                            }

                            if(resp.getOffers()!=null&&resp.getOffers().size()>0){

                                binding.offersRel.setVisibility(View.VISIBLE);
                                binding.offersTxt.setTypeface(typeface);
                                Recycler_Home_Adapter sections_adapter=new Recycler_Home_Adapter(context,resp.getOffers(),
                                        Slider.this);

                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context,
                                        LinearLayoutManager.HORIZONTAL, false);
                                binding.offers.setLayoutManager(mLayoutManager);
                                binding.offers.setItemAnimator(new DefaultItemAnimator());
                                binding.offers.setAdapter(sections_adapter);

                            }

                            if(resp.getLatest()!=null&&resp.getLatest().size()>0){

                                binding.latestRel.setVisibility(View.VISIBLE);
                                binding.latestTxt.setTypeface(typeface);
                                Recycler_Home_Adapter sections_adapter=new Recycler_Home_Adapter(context,resp.getLatest(),
                                        Slider.this);

                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context,
                                        LinearLayoutManager.HORIZONTAL, false);
                                binding.latest.setLayoutManager(mLayoutManager);
                                binding.latest.setItemAnimator(new DefaultItemAnimator());
                                binding.latest.setAdapter(sections_adapter);

                            }

                            if(resp.getPopUp()!=null&&resp.getPopUp().size()>0) {
                                if (SharedPrefManager.getInstance(context).Adv_Check() == null) {
                                    showPopup(resp.getPopUp());
                                    SharedPrefManager.getInstance(context).Adv_Cancel("ok");
                                }
                            }

                        }else{

                        }
                    }
                    else{}
                }


                @Override
                public void onFailure(Call<SliderResponse> call, Throwable t) {
                    Log.d("respooooonse", t.toString());

                }
            });
        }
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public void onCurrentItemChanged(@Nullable Recycler_Home_Adapter.MyViewHolder viewHolder, int adapterPosition) {

//        positionInDataSet = infiniteAdapter1.getRealPosition(adapterPosition);

    }

    public void get_categories() {
        final Api_Service requestInterface = Config.getClient().create(Api_Service.class);
        if (connectionDetection.isConnected()) {
            Call<Parent_Category_Model> response = requestInterface.getParentCategories(lang);
            response.enqueue(new Callback<Parent_Category_Model>() {
                @Override
                public void onResponse(Call<Parent_Category_Model> call, retrofit2.Response<Parent_Category_Model> response) {

                    Parent_Category_Model resp = response.body();
                    if (resp != null) {
                        if (resp.getSuccess() == 1) {
                           ArrayList<Parent_Category> parent = resp.getProduct();
                            if (parent.size() > 0) {
                              binding.sectionsRel.setVisibility(View.VISIBLE);
//                                binding.txt1.setPaintFlags(binding.txt1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

                                Sections_Adapter sections_adapter=new Sections_Adapter(getContext(),parent);

                                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context,2,
                                        LinearLayoutManager.VERTICAL, false);
                                binding.recyclerSections.setLayoutManager(mLayoutManager);
                                binding.recyclerSections.setItemAnimator(new DefaultItemAnimator());
                                binding.recyclerSections.setAdapter(sections_adapter);
                            }

                        } else {
                            Toast.makeText(context, context.getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, context.getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
                    }
                }


                @Override
                public void onFailure(Call<Parent_Category_Model> call, Throwable t) {
                    Log.d("respooooonse", t.toString());

                }
            });
        } else {
            Toast.makeText(context, context.getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
        }

    }

    public void updateToolbar(){
        title = toolbar.getRootView().findViewById(R.id.toolbar_title);

        if (SharedPrefManager.getInstance(getContext()).get_Cart() > 0) {
            cart = toolbar.getRootView().findViewById(R.id.cart);

            no.setVisibility(View.INVISIBLE);
            yes.setVisibility(View.VISIBLE);
            num.setText(String.valueOf(SharedPrefManager.getInstance(getContext()).get_Cart()));

        } else {
            cart = toolbar.getRootView().findViewById(R.id.cart1);
            yes.setVisibility(View.INVISIBLE);
            no.setVisibility(View.VISIBLE);
        }
        title.setTypeface(typeface);
        title.setText(context.getResources().getString(R.string.main));
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new MyCart();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment).addToBackStack(null)
                        .commit();
            }
        });
    }


    @Override
    public void get_ID(Branch_Model.Branch branch) {


    }

    @Override
    public void remove_ID() {

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

    public void showPopup(ArrayList<PopupModel> popup_models){
        final View popupView = LayoutInflater.from(context).inflate(R.layout.popup, null);
        popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);


        TextView cancel = popupView.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();

            }
        });

        SliderView slider=popupView.findViewById(R.id.imageSlider);
        slider.setVisibility(View.VISIBLE);
        SliderAdapterExample2 adapter = new SliderAdapterExample2(context, popup_models,popupWindow);

        slider.setSliderAdapter(adapter);

        slider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        slider.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        slider.setScrollTimeInSec(4); //set scroll delay in seconds :
        slider.startAutoCycle();

        if(lang.equals("ar")){
            slider.setRotationY(180);
        }

        if(!activity.isFinishing()) {

            popupWindow.setAnimationStyle(R.style.popup_window_animation_phone);
            popupWindow.showAtLocation(binding.getRoot(), Gravity.CENTER, 0, 0);
        }

    }
}
