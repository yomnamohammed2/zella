package com.emcan.zella.fragments;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.emcan.zella.Beans.Last_order_pojo;
import com.emcan.zella.ConnectionDetection;
import com.emcan.zella.R;
import com.emcan.zella.SharedPrefManager;
import com.emcan.zella.activities.MainActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


public class Map extends Fragment implements OnMapReadyCallback {

    private GoogleMap googleMap;
    MapView mMapView;
    double latti,longi;
    TextView title,num;
    ImageView cart;
    Toolbar toolbar;
    RelativeLayout no,yes;
    RelativeLayout pickup;

    Context context;
    Geocoder geocoder;
    List<Address> addresses;
    PopupWindow popupWindow;
    LocationManager locationManager;
    static final int REQUEST_LOCATION = 1;
    ConnectionDetection connectionDetection;
    public static final int LOCATION_UPDATE_MIN_DISTANCE = 10;
    public static final int LOCATION_UPDATE_MIN_TIME = 5000;
    String lang;
    Typeface typeface;
    AppCompatActivity activity1;
    String id;
    Last_order_pojo.last order;
    String order_id,reorder,charge;
    double discount;
    String flag=null;

    private boolean isGPSEnabled,isNetworkEnabled;

    ImageView dropPinView;
    View  view;

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
    public Map() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view=inflater.inflate(R.layout.fragment_map, container, false);

        locationManager = (LocationManager)getActivity()
                .getSystemService(Context.LOCATION_SERVICE);

        activity1 = (AppCompatActivity) getActivity();

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        activity1.setSupportActionBar(toolbar);
        lang= SharedPrefManager.getInstance(activity1).getLang();
        if(lang.equals("en")){
            typeface = ResourcesCompat.getFont(activity1, R.font.amaranth_bold);
        }
        if(lang.equals("ar")){
            typeface = ResourcesCompat.getFont(activity1, R.font.bein_black);
        }
       setToolbar();
        RelativeLayout bar = activity1.findViewById(R.id.bar);
        bar.setVisibility(View.GONE);
        ((MainActivity) activity1).select_icon("more");

        ///////////////////////////////////////////


        Button get_address=(Button) view.findViewById(R.id.get_address);
        get_address.setTypeface(typeface);

        context=getContext();
        connectionDetection=new ConnectionDetection(context);

        mMapView = (MapView) view.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);


        mMapView.onCreate(null);
        mMapView.onResume();



        mMapView.getMapAsync(this);

        dropPinView = new ImageView(getContext());
        dropPinView.setImageResource(R.drawable.marker);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(80, 80, Gravity.CENTER);
        float density = getResources().getDisplayMetrics().density;
        params.bottomMargin = (int) (12 * density);
        dropPinView.setLayoutParams(params);


        get_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getAddress();
            }
        });



        mMapView.addView(dropPinView);

if(getArguments()!=null&&getArguments().getString("flag")!=null){
    flag=getArguments().getString("flag");
}

        if(getArguments()!=null&&getArguments().getString("reorder")!=null){
            reorder=getArguments().getString("reorder");
            order_id=getArguments().getString("order_id");
            charge=getArguments().getString("charge");
            discount=getArguments().getDouble("discount");
            order= (Last_order_pojo.last) getArguments().getSerializable("order");
        }

        return view;
    }
    public void getAddress(){
        try {

            LatLng position = googleMap.getProjection().fromScreenLocation(new Point(dropPinView.getLeft() + (dropPinView.getWidth() / 2), dropPinView.getBottom()));
            latti=position.latitude;
            longi=position.longitude;


            geocoder = new Geocoder(getContext(), Locale.getDefault());
            addresses = geocoder.getFromLocation(latti, longi, 1);
            if (addresses != null && addresses.size() > 0) {
                String address = addresses.get(0).getAddressLine(0);
                String city = addresses.get(0).getLocality();
                //  String state = addresses.get(0).getSubAdminArea();
                String country = addresses.get(0).getCountryName();

                StringTokenizer stringTokenizer = new StringTokenizer(address, ",");
                String street="";
                if(stringTokenizer.hasMoreTokens()) {
                    street =stringTokenizer.nextToken().trim();
                }
                AppCompatActivity activity = (AppCompatActivity) getActivity();

                Log.d("address","Your address is: " + country + " " + city );
                Fragment fragment=new Get_Address();


                Bundle bundle = new Bundle();
                bundle.putString("street", street);
                bundle.putString("region", city);
                bundle.putString("lat", String.valueOf(latti));
                bundle.putString("long", String.valueOf(longi));
                bundle.putString("id",id);
                bundle.putString("flag",flag);
                if(reorder!=null){
                    bundle.putSerializable("order",order);
                    bundle.putString("order_id",order_id);
                    bundle.putString("charge",charge);
                    bundle.putString("reorder",reorder);
                    bundle.putDouble("discount",discount);
                }

                fragment.setArguments(bundle);
                FragmentManager fragmentManager=activity.getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment).addToBackStack(null)
                        .commit();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this. googleMap= googleMap;
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

                if(getArguments()!=null&&getArguments().getString("lat")!=null){

                        latti = Double.parseDouble(getArguments().getString("lat"));
                        longi = Double.parseDouble(getArguments().getString("long"));
                        id=getArguments().getString("id");
                        LatLng gps = new LatLng(latti, longi);

                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(gps, 16));

                }else {

                    googleMap.setMyLocationEnabled(true);
                    getLocation();

                    Log.d("jjjjj","my location: "+latti+" "+longi);
                    LatLng gps = new LatLng(latti, longi);

                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(gps, 16));
                }

            }else{
                Toast.makeText(getContext(),activity1.getResources().getString(R.string.networkerror), Toast.LENGTH_LONG).show();

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
                    mMapView.getMapAsync(this);

                } else {
                }
                break;
        }
    }


    @Override
    public void onResume() {

        mMapView.onResume();
        this.isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        this.isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        mMapView.getMapAsync(this);
//        getLocation();
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

        }else {
            if(isNetworkEnabled==true) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, mLocationListener);
            }else{
                if (isGPSEnabled == true) {

                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                            LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, mLocationListener);
                }else{
                    show_Alerts();}
            }
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
        locationManager.removeUpdates(mLocationListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        locationManager.removeUpdates(mLocationListener);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

private void setToolbar(){
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

    title.setVisibility(View.GONE);
    title.setVisibility(View.VISIBLE);
    cart.setVisibility(View.VISIBLE);
    ImageView menu=toolbar.findViewById(R.id.menu);
    menu.setVisibility(View.VISIBLE);
    cart.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Fragment fragment=new MyCart();
            FragmentManager fragmentManager = activity1.getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment).addToBackStack("xyz")
                    .commit();
        }
    });
    ImageButton back=toolbar.findViewById(R.id.back);
    back.setVisibility(View.VISIBLE);



    title.setText(activity1.getResources().getString(R.string.chooseyou));
}



    public  void show_Alerts(){


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.dialoge_alert, null);
       final PopupWindow popupWindow = new PopupWindow(
                customView,
                LinearLayout.LayoutParams.MATCH_PARENT,
                //   700,
                LinearLayout.LayoutParams.MATCH_PARENT, true
        );
//        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);

        final RelativeLayout out=customView.findViewById(R.id.out);
        Button no=customView.findViewById(R.id.no);
        Button yes=customView.findViewById(R.id.yes);
        TextView text=customView.findViewById(R.id.text);

        text.setText(context.getResources().getString(R.string.allow_location));


        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                out.setVisibility(View.GONE);
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                out.setVisibility(View.GONE);
                startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                popupWindow.dismiss();

            }
        });

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

    }
}
