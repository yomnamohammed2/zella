package com.emcan.zella.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.emcan.zella.Api_Service;
import com.emcan.zella.Beans.Branch_Model;
import com.emcan.zella.Config;
import com.emcan.zella.ConnectionDetection;
import com.emcan.zella.R;
import com.emcan.zella.SharedPrefManager;
import com.emcan.zella.databinding.FragmentBranchesMapBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class BranchesMap extends Fragment implements OnMapReadyCallback {

    private boolean isGPSEnabled,isNetworkEnabled;

    ImageView dropPinView;
    View  view;
    LocationManager locationManager;
    static final int REQUEST_LOCATION = 1;
    ConnectionDetection connectionDetection;
    public static final int LOCATION_UPDATE_MIN_DISTANCE = 10;
    public static final int LOCATION_UPDATE_MIN_TIME = 5000;
    GoogleMap googleMap;
    MapView mMapView;
    AppCompatActivity activity1;
    FragmentBranchesMapBinding binding;
    Context context;
    private LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {

//                locationManager.removeUpdates(mLocationListener);
//
//                if(googleMap!= null) {
//                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom((new LatLng(location.getLatitude(), location.getLongitude())), 15));
//                }
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
    public BranchesMap() {
        // Required empty public constructor
    }


    public static BranchesMap newInstance(String param1, String param2) {
        BranchesMap fragment = new BranchesMap();
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
        binding= FragmentBranchesMapBinding.inflate(inflater, container, false);

        activity1 = (AppCompatActivity) getActivity();
        view=binding.getRoot();

        locationManager = (LocationManager)getActivity()
                .getSystemService(Context.LOCATION_SERVICE);

        context=getContext();
        connectionDetection=new ConnectionDetection(context);

        mMapView = (MapView) view.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onCreate(null);
        mMapView.onResume();
        mMapView.getMapAsync(this);


        getBranches();

        return binding.getRoot();

    }

    public void getBranches() {
        if (connectionDetection.isConnected()) {
            Api_Service requestInterface = Config.getClient().create(Api_Service.class);
            Branch_Model model=new Branch_Model();
            model.setLang(SharedPrefManager.getInstance(activity1).getLang());
            model.setBranch_lat(SharedPrefManager.getInstance(activity1).getLatti());
            model.setBranch_long(SharedPrefManager.getInstance(activity1).getLongi());
            Call<Branch_Model> response1 = requestInterface.get_Branches(SharedPrefManager.getInstance(context).getLang());
            response1.enqueue(new Callback<Branch_Model>() {
                @Override
                public void onResponse(Call<Branch_Model> call, retrofit2.Response<Branch_Model> response) {
                    Branch_Model resp = response.body();
                    if (resp != null) {

                        if (resp.getSuccess() == 1) {

                            for(int i=0;i<resp.getProduct().size();i++){
                            Marker m=
                                    googleMap.addMarker(new MarkerOptions()
                                            .position(new LatLng(Double.parseDouble(resp.getProduct().get(i)
                                            .getBranch_lat()),
                                                    Double.parseDouble(resp.getProduct().get(i).getBranch_long())))
                                            .icon(bitmapDescriptorFromVector(activity1))

                                            .title(resp.getProduct().get(i).getName()));

                            m.setTag(i);

                            if(i==resp.getProduct().size()-1) {
                                googleMap.animateCamera
                                        (CameraUpdateFactory.newLatLngZoom((new LatLng(Double.parseDouble(resp.getProduct().get(i)
                                                .getBranch_lat()),
                                                        Double.parseDouble(resp.getProduct().get(i).getBranch_long()))
                                                ), 6
                                        ));
                            }
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<Branch_Model> call, Throwable t) {

                }
            });
        } else {
            Toast.makeText(getContext(), activity1.getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
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



//                    googleMap.setMyLocationEnabled(true);
//                    getLocation();
//
//                    Log.d("jjjjj","my location: "+latti+" "+longi);
//                    LatLng gps = new LatLng(latti, longi);
//
//                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(gps, 16));

            }else{
                Toast.makeText(getContext(),activity1.getResources().getString(R.string.networkerror), Toast.LENGTH_LONG).show();

            }
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
                }
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
    private BitmapDescriptor bitmapDescriptorFromVector(Context context) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, R.drawable.marker2);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

}