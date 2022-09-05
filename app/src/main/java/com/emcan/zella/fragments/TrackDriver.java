package com.emcan.zella.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.emcan.zella.Beans.Current_order_model;
import com.emcan.zella.ConnectionDetection;
import com.emcan.zella.R;
import com.emcan.zella.SharedPrefManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class TrackDriver extends Fragment implements OnMapReadyCallback {

    View view;

    ConnectionDetection connectionDetection;
    ImageView cart;
    Toolbar toolbar;
    TextView title, num;
    RelativeLayout pickup;

    RelativeLayout no, yes;
    Context context;
    String lang;
    Typeface typeface;
    MapView mMapView;
    LocationManager locationManager;

    static final int REQUEST_LOCATION = 1;

    GoogleMap googleMap;
    AppCompatActivity activity;
    ImageButton delete;
    double latti,latti_, longi=0,longi_=0;
    double dest_longi,dest_latti;
    boolean isMarkerRotating=false;
    Marker marker,marker_destination;
    FragmentManager fragmentManager;
    Current_order_model.Current_order order;


    private LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {

                locationManager.removeUpdates(mLocationListener);

                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom((new LatLng(location.getLatitude(),
                        location.getLongitude())), 15));

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



    public static TrackDriver newInstance(Current_order_model.Current_order order) {
        TrackDriver fragment = new TrackDriver();
        Bundle args = new Bundle();
        args.putSerializable("order",order);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            order= (Current_order_model.Current_order) getArguments().getSerializable("order");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the view for this fragment
        view = inflater.inflate(R.layout.fragment_track_driver, container, false);
        //----------------------tool bar and title--------------//

        activity = (AppCompatActivity) getActivity();
        fragmentManager = activity.getSupportFragmentManager();

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        lang = SharedPrefManager.getInstance(activity).getLang();
        ImageButton back = toolbar.findViewById(R.id.back);

        context = getContext();
        lang = SharedPrefManager.getInstance(activity).getLang();

        if(lang.equals("en")){
            typeface = ResourcesCompat.getFont(activity, R.font.amaranth_bold);
            back.setRotation(180);
        }
        if(lang.equals("ar")){
            typeface = ResourcesCompat.getFont(activity, R.font.bein_black);

        }
        activity.setSupportActionBar(toolbar);
        no = toolbar.findViewById(R.id.no_cart);
        yes = toolbar.findViewById(R.id.yes_cart);

        num = toolbar.getRootView().findViewById(R.id.num);

        context = getContext();
        num=(TextView) toolbar.getRootView().findViewById(R.id.num);
        title=toolbar.getRootView().findViewById(R.id.toolbar_title);

        if(SharedPrefManager.getInstance(getContext()).get_Cart()>0){
            cart=(ImageView) toolbar.getRootView().findViewById(R.id.cart);

            no.setVisibility(View.INVISIBLE);
            yes.setVisibility(View.VISIBLE);
            num.setText(String.valueOf(SharedPrefManager.getInstance(getContext()).get_Cart()));

        }else{
            cart=(ImageView) toolbar.getRootView().findViewById(R.id.cart1);
            yes.setVisibility(View.INVISIBLE);
            no.setVisibility(View.VISIBLE);
        }
        title.setTypeface(typeface);
        title.setText(activity.getResources().getString(R.string.follow));

        cart.setVisibility(View.INVISIBLE);


        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.getSupportFragmentManager().popBackStack();
            }
        });

        num.setVisibility(View.INVISIBLE);


        cart.setVisibility(View.INVISIBLE);



        connectionDetection = new ConnectionDetection(context);
        locationManager = (LocationManager)activity
                .getSystemService(Context.LOCATION_SERVICE);

        mMapView = (MapView) view.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onCreate(null);
        mMapView.onResume();

        mMapView.getMapAsync(this);



        return view;

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
                this. googleMap= googleMap;
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                if(order.getDriver_data()!=null&&order.getDriver_data().size()>0)
                get_location();


            }else{
                Toast.makeText(getContext(),activity.getResources().getString(R.string.networkerror), Toast.LENGTH_LONG).show();

            }
        }
    }


    public   void get_location() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        Query query = database.getReference().child("driver_locations").child(order.getDriver_data().get(0).getId());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(latti_==0&&longi_==0) {
                    longi = (double) dataSnapshot.child("driver_lang").getValue();
                    latti = (double) dataSnapshot.child("driver_lat").getValue();
                    dest_longi =(double) dataSnapshot.child("destination_long").getValue();
                    dest_latti = (double) dataSnapshot.child("destination_lat").getValue();

                    googleMap.clear();
                    draw_route();

                    title.setVisibility(View.VISIBLE);
                    Log.d("viiiiii",title.getVisibility()+"  ");
                    marker=googleMap.addMarker(new MarkerOptions().position(new LatLng(latti,longi)).icon(BitmapDescriptorFactory.
                            fromResource(R.drawable.marker))
                            .title("Current Location"));
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latti,longi), 12));

                    marker_destination=googleMap.addMarker(new MarkerOptions().position(new LatLng(dest_latti,dest_longi))
                            .icon(BitmapDescriptorFactory.
                                    fromResource(R.drawable.marker2))
                            .title("Driver Location"));
                    longi_=longi;
                    latti_=latti;
                }else{
                    longi = (double) dataSnapshot.child("driver_lang").getValue();
                    latti = (double) dataSnapshot.child("driver_lat").getValue();
                    dest_longi =(double) dataSnapshot.child("destination_long").getValue();
                    dest_latti = (double) dataSnapshot.child("destination_lat").getValue();

                    googleMap.clear();
                    draw_route();

                    marker=googleMap.addMarker(new MarkerOptions().position(new LatLng(latti_,longi_)).icon(BitmapDescriptorFactory.
                            fromResource(R.drawable.marker))
                            .title("Current Location"));
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latti,longi), 12));

                    marker_destination=googleMap.addMarker(new MarkerOptions().position(new LatLng(dest_latti,dest_longi))
                            .icon(BitmapDescriptorFactory.
                                    fromResource(R.drawable.placeholder))
                            .title("Driver Location"));

                    //  double rotae = bearingBetweenLocations(marker.getPosition(), new LatLng(latti,longi));

                    //rotateMarker(marker,  (float) angle_);
                    movemarker( new LatLng(latti,longi));
                    marker_destination.setPosition(new LatLng(dest_latti,dest_longi));
                    longi_=longi;
                    latti_=latti;

                }



//                User user=SharedPrefManager.getInstance(context).getUser();
//
//                user.setPhone_number( (String) dataSnapshot.child("phone_number").getValue());
////                user.setWallet( (Long) dataSnapshot.child("Wallet").getValue());
//                user.setFirst_name( (String) dataSnapshot.child("first_name").getValue());
//                user.setLast_name( (String) dataSnapshot.child("last_name").getValue());
//                user.setGender( (long) dataSnapshot.child("gender").getValue());
//                SharedPrefManager.getInstance(context).userLogin(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_LOCATION:
                if (ActivityCompat.checkSelfPermission(context,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(context,
                                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(
                            new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                    Manifest.permission.ACCESS_FINE_LOCATION},
                            1);
                } else {
                    mMapView.getMapAsync(this);
                }
                break;
        }
    }





    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
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

    private void movemarker(LatLng disti) {

        final LatLng startPosition = marker.getPosition();
        LatLng finalPosition=disti;


        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        final Interpolator interpolator = new AccelerateDecelerateInterpolator();
        final float durationInMs = 500;
        final boolean hideMarker = false;

        final LatLng finalPosition1 = finalPosition;
        handler.post(new Runnable() {
            long elapsed;
            float t;
            float v;

            @Override
            public void run() {
                // Calculate progress using interpolator
                elapsed = SystemClock.uptimeMillis() - start;
                t = elapsed / durationInMs;

                LatLng currentPosition = new LatLng(
                        startPosition.latitude * (1 - t) + finalPosition1.latitude * t,
                        startPosition.longitude * (1 - t) + finalPosition1.longitude * t);

                marker.setPosition(currentPosition);



                // Repeat till progress is complete.
                if (t < 1) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                } else {
                    if (hideMarker) {
                        marker.setVisible(false);
                    } else {
                        marker.setVisible(true);
                    }
                }
            }
        });
    }


//    private void rotateMarker(final Marker marker, final float toRotation) {
//        if(!isMarkerRotating) {
//            final Handler handler = new Handler();
//            final long start = SystemClock.uptimeMillis();
//            final float startRotation = marker.getRotation();
//            final long duration = 500;
//
//            final Interpolator interpolator = new LinearInterpolator();
//
//            handler.post(new Runnable() {
//                @Override
//                public void run() {
//                    isMarkerRotating = true;
//
//                    long elapsed = SystemClock.uptimeMillis() - start;
//                    float t = interpolator.getInterpolation((float) elapsed / duration);
//
//                    float rot = t * toRotation + (1 - t) * startRotation;
//
//                    marker.setRotation(-rot > 180 ? rot / 2 : rot);
//                    marker.setAnchor(0.5f,0.5f);
//
//                    if (t <1.0) {
//                        // Post again 16ms later.0
//                        handler.postDelayed(this, 10);
//                    } else {
//                        isMarkerRotating = false;
//                    }
//                }
//            });
//        }
//    }

    private void draw_route(){

//        List<LatLng> path = new ArrayList();
//
//        GeoApiContext context = new GeoApiContext.Builder()
//                .apiKey("AIzaSyATrsZELdvuNpmLY043alCTT4YKa691S4I")
//                .build();
//        DirectionsApiRequest req = DirectionsApi.getDirections(context, latti+","+
//                longi,dest_latti+","+dest_longi);
//        try {
//            DirectionsResult res = req.await();
//
//            //Loop through legs and steps to get encoded polylines of each step
//            if (res.routes != null && res.routes.length > 0) {
//                DirectionsRoute route = res.routes[0];
//
//                if (route.legs !=null) {
//                    for(int i=0; i<route.legs.length; i++) {
//                        DirectionsLeg leg = route.legs[i];
//                        if (leg.steps != null) {
//                            for (int j=0; j<leg.steps.length;j++){
//                                DirectionsStep step = leg.steps[j];
//                                if (step.steps != null && step.steps.length >0) {
//                                    for (int k=0; k<step.steps.length;k++){
//                                        DirectionsStep step1 = step.steps[k];
//                                        EncodedPolyline points1 = step1.polyline;
//                                        if (points1 != null) {
//                                            //Decode polyline and add points to list of route coordinates
//                                            List<com.google.maps.model.LatLng> coords1 = points1.decodePath();
//                                            for (com.google.maps.model.LatLng coord1 : coords1) {
//                                                path.add(new LatLng(coord1.lat, coord1.lng));
//                                            }
//                                        }
//                                    }
//                                } else {
//                                    EncodedPolyline points = step.polyline;
//                                    if (points != null) {
//                                        //Decode polyline and add points to list of route coordinates
//                                        List<com.google.maps.model.LatLng> coords = points.decodePath();
//                                        for (com.google.maps.model.LatLng coord : coords) {
//                                            path.add(new LatLng(coord.lat, coord.lng));
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        } catch(Exception ex) {
//            Log.e("lllll", ex.getLocalizedMessage());
//        }
//
//        //Draw the polyline
//        if (path.size() > 0) {
//            PolylineOptions opts = new PolylineOptions().addAll(path).color(Color.RED).width(10);
//            googleMap.addPolyline(opts);
//        }
//
//        googleMap.getUiSettings().setZoomControlsEnabled(true);
////        LatLng latLng=new LatLng(pic_lat.latitude+(pic_lat.latitude-dest_lat.latitude)
////                ,pic_lat.longitude+(pic_lat.longitude-dest_lat.longitude));
////
////        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pic_lat, 12));
    }
}
