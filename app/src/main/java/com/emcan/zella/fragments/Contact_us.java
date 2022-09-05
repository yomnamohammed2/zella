package com.emcan.zella.fragments;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.emcan.zella.Api_Service;
import com.emcan.zella.Beans.Contact_Response;
import com.emcan.zella.Config;
import com.emcan.zella.ConnectionDetection;
import com.emcan.zella.R;
import com.emcan.zella.SharedPrefManager;
import com.emcan.zella.activities.MainActivity;
import com.emcan.zella.databinding.FragmentContactUsBinding;

import retrofit2.Call;
import retrofit2.Callback;


public class Contact_us extends Fragment {

    Toolbar toolbar;
    TextView title, num;
    ImageView cart;
    RelativeLayout no, yes;
    ConnectionDetection connectionDetection;
    public static String FACEBOOK_URL ;
    Contact_Response.Contact contact;
     AppCompatActivity activity;
     Context context;
     String lang;
    RelativeLayout pickup;

    RelativeLayout card4,card6,card5,card1,card3,card2,card7,card8,card9;
    FragmentContactUsBinding binding;
     Typeface typeface;
    FragmentManager fragmentManager;
    ImageView screenshot;

    public Contact_us() {
        // Required empty public constructor
    }
    ImageView snapchat,youtube;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_contact_us, container, false);
        context=getContext();
        binding= FragmentContactUsBinding.inflate(inflater, container, false);
        //----------------------tool bar and title--------------//

        activity = (AppCompatActivity) getActivity();

        RelativeLayout bar = activity.findViewById(R.id.bar);
        bar.setVisibility(View.VISIBLE);
        ((MainActivity) activity).select_icon("none");

        toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        ImageButton back=toolbar.findViewById(R.id.back);

        lang= SharedPrefManager.getInstance(activity).getLang();
        if(lang.equals("en")){
            typeface = ResourcesCompat.getFont(activity, R.font.amaranth_bold);
            back.setRotation(180);
        }
        if(lang.equals("ar")){
            typeface= ResourcesCompat.getFont(activity, R.font.bein_black);
        }
        no=toolbar.findViewById(R.id.no_cart);
        yes=toolbar.findViewById(R.id.yes_cart);

        num=toolbar.getRootView().findViewById(R.id.num);

        title=toolbar.getRootView().findViewById(R.id.toolbar_title);

        if(SharedPrefManager.getInstance(context).get_Cart()>0){
            cart=toolbar.getRootView().findViewById(R.id.cart);

            no.setVisibility(View.INVISIBLE);
            yes.setVisibility(View.VISIBLE);
            num.setText(String.valueOf(SharedPrefManager.getInstance(context).get_Cart()));

        }else{
            cart=toolbar.getRootView().findViewById(R.id.cart1);
            yes.setVisibility(View.INVISIBLE);
            no.setVisibility(View.VISIBLE);
        }
        title.setTypeface(typeface);
        title.setText(activity.getResources().getString(R.string.callus));
        back.setVisibility(View.VISIBLE);



        cart.setVisibility(View.VISIBLE);
        ImageView menu=toolbar.findViewById(R.id.menu);
        menu.setVisibility(View.VISIBLE);
        fragmentManager =activity.getSupportFragmentManager();
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new MyCart();

                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment).addToBackStack("xyz")
                        .commit();
            }
        });


        //-------------------------------------------------//
//        TextView title2=view.findViewById(R.id.title);
//        TextView subtitle=view.findViewById(R.id.subtitle);
//        title2.setTypeface(m_typeFace);
//        subtitle.setTypeface(m_typeFace);

        final TextView address=view.findViewById(R.id.address);
        final TextView phone2=view.findViewById(R.id.phone);
        final TextView phone1=view.findViewById(R.id.phone1);
        final TextView mail=view.findViewById(R.id.email);
        final TextView face=view.findViewById(R.id.facebook);
        final TextView twit=view.findViewById(R.id.twitter);
        final TextView insta=view.findViewById(R.id.instagram);
        final TextView web=view.findViewById(R.id.web);
        TextView email=view.findViewById(R.id.email);

        TextView snap=view.findViewById(R.id.snap);
        TextView youtube=view.findViewById(R.id.youtube);

        card4=view.findViewById(R.id.card4);
        card5=view.findViewById(R.id.card5);
        card6=view.findViewById(R.id.card6);
        card1=view.findViewById(R.id.card1);
        card2=view.findViewById(R.id.card2);
        card3=view.findViewById(R.id.card3);
        card7=view.findViewById(R.id.card7);
        card8=view.findViewById(R.id.card8);
        card9=view.findViewById(R.id.card9);

        address.setTypeface(typeface);
        phone2.setTypeface(typeface);
        phone1.setTypeface(typeface);
        face.setTypeface(typeface);
        twit.setTypeface(typeface);
        insta.setTypeface(typeface);
        web.setTypeface(typeface);
        email.setTypeface(typeface);
        snap.setTypeface(typeface);
        youtube.setTypeface(typeface);



connectionDetection=new ConnectionDetection(context);



        if (connectionDetection.isConnected()) {
            Api_Service requestInterface = Config.getClient().create(Api_Service.class);
            Call<Contact_Response> response1 = requestInterface.get_Contact(SharedPrefManager.getInstance(activity).getLang(),
              "");
                    response1.enqueue(new Callback<Contact_Response>() {
                @Override
                public void onResponse(Call<Contact_Response> call, retrofit2.Response<Contact_Response> response) {

                    Contact_Response resp = response.body();
                    if (resp != null) {

                        if (resp.getSuccess() == 1) {
                            if (resp.getProduct().size()>0) {

                                contact=resp.getProduct().get(0);
                                if(contact.getImage()!=null&&contact.getImage().length()>0){
//                                    Glide.with(activity).load(contact.getImage()).error(R.drawable.screenshot).into(screenshot);
                                }
                                else{
//                                    Glide.with(activity).load(R.drawable.screenshot).into(screenshot);

                                }
                                if(contact.getAddress()!=null)
                                {
                                    card3.setVisibility(View.VISIBLE);
                                    address.setText(contact.getAddress());
                                }
                                if(contact.getPhone()!=null)
                                {
                                    card1.setVisibility(View.VISIBLE);
                                    phone1.setText("- "+contact.getPhone());
                                }
                                if(contact.getMobile()!=null)
                                {
                                    phone2.setText(contact.getMobile());
                                }

                                if(contact.getWebsite()!=null)
                                {
                                    card2.setVisibility(View.VISIBLE);
                                    web.setText(contact.getWebsite());

                                }

                                if(contact.getEmail()!=null)
                                {
                                    card7.setVisibility(View.VISIBLE);
                                    email.setText(contact.getEmail());

                                }


                                if(contact.getFacebook()!=null)
                                {
                                   // face.setText(contact.getFacebook());
                                    FACEBOOK_URL=contact.getFacebook();
                                    face.setText(contact.getFacebook());
                                }
                                if(contact.getTwitter()!=null)
                                {
                                    twit.setText(contact.getTwitter());
                                 //   twit.setText(contact.getTwitter());
                                }
                                if(contact.getInstagram()!=null)
                                {
                                    insta.setText(contact.getInstagram());
                                  //  insta.setText(contact.getInstagram());
                                }
                                if(contact.getSnapchat()!=null)
                                {
                                    card8.setVisibility(View.VISIBLE);
                                    snap.setText(contact.getSnapchat());
                                }
                                if(contact.getYoutube()!=null)
                                {
                                    card9.setVisibility(View.VISIBLE);
                                    youtube.setText(contact.getYoutube());
                                }
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<Contact_Response> call, Throwable t) {
                    //   Toast.makeText(getContext(), "خطأ حاول مجددا", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(context, activity.getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
        }

        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent faceBookIntent = new Intent(Intent.ACTION_VIEW);
                String faceBookUrl = getFacebookPageURL(activity);
                faceBookIntent.setData(Uri.parse(faceBookUrl));
                startActivity(faceBookIntent);
            }
        });

        card9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent faceBookIntent = new Intent(Intent.ACTION_VIEW);
                faceBookIntent.setData(Uri.parse(contact.getYoutube()));
                if(faceBookIntent.resolveActivity(activity.getPackageManager())!=null) {
                    startActivity(faceBookIntent);
                }
            }
        });
//
        card8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent faceBookIntent = new Intent(Intent.ACTION_VIEW);
                faceBookIntent.setData(Uri.parse(contact.getSnapchat()));
                if(faceBookIntent.resolveActivity(activity.getPackageManager())!=null) {
                    startActivity(faceBookIntent);
                }
            }
        });

        card5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(contact.getTwitter())));
                }   catch (Exception e) {
                    Toast.makeText(getActivity(), "couldn't be opened", Toast.LENGTH_SHORT).show();
                }

            }
        });


        card6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(contact.getInstagram());
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
                likeIng.setPackage("com.instagram.android");
                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse(contact.getInstagram())));
                }
            }
        });



//        phone.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (isPermissionGranted()) {
//                    call_action(contact.getPhone());
//                }
//            }
//        });

        phone2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPermissionGranted()) {
                    call_action(contact.getMobile());
                }
            }
        });

        phone1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPermissionGranted()) {
                    call_action(contact.getPhone());
                }
            }
        });
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i=new Intent(Intent.ACTION_VIEW,Uri.parse("https://"+contact.getWebsite()));
//                if(i.resolveActivity(activity.getPackageManager())!=null) {
//                    startActivity(i);
//                }

                try {
                    Uri uri = Uri.parse("https://"+contact.getWebsite());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Log.e("", e.getMessage());
                    Toast.makeText(getActivity(), "couldn't be opened", Toast.LENGTH_SHORT).show();
                }
            }
        });



//
//        web_img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i=new Intent(Intent.ACTION_VIEW,Uri.parse(contact.getWebsite()));
//                if(i.resolveActivity(activity.getPackageManager())!=null) {
//                    startActivity(i);
//                }
//            }
//        });

        card7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(contact!=null) {
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto", contact.getEmail(), null));
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                    activity.startActivity(Intent.createChooser(emailIntent, null));

                }
            }
        });
//        mail_img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
//                        "mailto", contact.getEmail(), null));
//                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
//               activity.startActivity(Intent.createChooser(emailIntent, null));
//
//            }
//        });
        return view;
    }




    public String getFacebookPageURL(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            }
            else { //older versions of fb app
                return "fb://profile/" + FACEBOOK_URL;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return FACEBOOK_URL; //normal web url
        }
    }


    public  boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG","Permission is granted");
                return true;
            } else {

                Log.v("TAG","Permission is revoked");
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG","Permission is granted");
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {

            case 1: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(context, "Permission granted", Toast.LENGTH_SHORT).show();
                    //call_action();
                } else {
                    Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    void call_action(String number){
        Intent i=new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+number));
        startActivity(i);
    }

}
