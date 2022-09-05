package com.emcan.zella.fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.emcan.zella.Beans.Contact_Response;
import com.emcan.zella.ConnectionDetection;
import com.emcan.zella.R;
import com.emcan.zella.SharedPrefManager;
import com.emcan.zella.databinding.FragmentContactBinding;


public class Contact extends Fragment {

  FragmentContactBinding binding;
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

    Typeface typeface;
    FragmentManager fragmentManager;

    public Contact() {
        // Required empty public constructor
    }


    public static Contact newInstance(String param1, String param2) {
        Contact fragment = new Contact();
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
       binding=  FragmentContactBinding.inflate(inflater, container, false);


        context=getContext();
        //----------------------tool bar and title--------------//

        activity = (AppCompatActivity) getActivity();

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


        binding.txt1.setTypeface(typeface);
        binding.txt2.setTypeface(typeface);
        binding.txt3.setTypeface(typeface);

        if(lang.equals("ar")){
            binding.back1.setRotationY(180);
            binding.back2.setRotationY(180);
            binding.back3.setRotationY(180);
        }

        binding.card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Fragment fragment = new Contact_us();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment).addToBackStack(null)
                        .commit();
            }
        });


        binding.card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new TechnicalSupportFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment).addToBackStack(null)
                        .commit();
            }
        });

        binding.card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new Suggestions();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment).addToBackStack(null)
                        .commit();
            }
        });
        return binding.getRoot();
    }
}