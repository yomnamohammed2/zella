package com.emcan.zella.fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.emcan.zella.Api_Service;
import com.emcan.zella.Beans.About_response;
import com.emcan.zella.Config;
import com.emcan.zella.ConnectionDetection;
import com.emcan.zella.R;
import com.emcan.zella.SharedPrefManager;
import com.emcan.zella.activities.MainActivity;


import retrofit2.Call;
import retrofit2.Callback;


public class About_sofra extends Fragment {
    Toolbar toolbar;
    TextView title, num;
    ImageView cart;
    RelativeLayout no, yes;
    ConnectionDetection connectionDetection;
    Context mcontect;
    CardView card;
    String lang;
    Typeface typeface,typeface1;

    public About_sofra() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_sofra, container, false);
        mcontect = getContext();

        //----------------------tool bar and title--------------//
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ImageButton back = toolbar.findViewById(R.id.back);

        final AppCompatActivity activity = (AppCompatActivity) getActivity();
        lang= SharedPrefManager.getInstance(activity).getLang();
        if(lang.equals("en")){
            typeface = ResourcesCompat.getFont(activity, R.font.amaranth_bold);
            typeface1 = ResourcesCompat.getFont(activity, R.font.amaranth_regular);
            back.setRotation(180);
        }
        if(lang.equals("ar")){
            typeface= ResourcesCompat.getFont(activity, R.font.bein_black);
        }
        activity.setSupportActionBar(toolbar);
        no = toolbar.findViewById(R.id.no_cart);
        yes = toolbar.findViewById(R.id.yes_cart);


        RelativeLayout bar = activity.findViewById(R.id.bar);
        bar.setVisibility(View.VISIBLE);
        ((MainActivity) activity).select_icon("none");


        num = toolbar.getRootView().findViewById(R.id.num);
        title = toolbar.getRootView().findViewById(R.id.toolbar_title);

        if (SharedPrefManager.getInstance(mcontect).get_Cart() > 0) {
            cart = toolbar.getRootView().findViewById(R.id.cart);

            no.setVisibility(View.INVISIBLE);
            yes.setVisibility(View.VISIBLE);
            num.setText(String.valueOf(SharedPrefManager.getInstance(mcontect).get_Cart()));

        } else {
            cart = toolbar.getRootView().findViewById(R.id.cart1);
            yes.setVisibility(View.INVISIBLE);
            no.setVisibility(View.VISIBLE);
        }
        title.setTypeface(typeface);
        title.setVisibility(View.GONE);
        title.setVisibility(View.VISIBLE);
        title.setText(activity.getResources().getString(R.string.aboutrestaourant));

        cart.setVisibility(View.VISIBLE);
        ImageView menu = toolbar.findViewById(R.id.menu);
        menu.setVisibility(View.VISIBLE);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new MyCart();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment).addToBackStack("xyz")
                        .commit();
            }
        });
        back.setVisibility(View.VISIBLE);


        final TextView content = view.findViewById(R.id.content);
        content.setTypeface(typeface);
        final ImageView image = view.findViewById(R.id.image);
        final ProgressBar progressBar = view.findViewById(R.id.progressBar);
        connectionDetection = new ConnectionDetection(mcontect);

        if (connectionDetection.isConnected()) {
            progressBar.setVisibility(View.VISIBLE);

            Api_Service requestInterface = Config.getClient().create(Api_Service.class);
            Call<About_response> response1 = requestInterface.get_About(lang);
            response1.enqueue(new Callback<About_response>() {
                @Override
                public void onResponse(Call<About_response> call, retrofit2.Response<About_response> response) {
                    progressBar.setVisibility(View.INVISIBLE);
                    About_response resp = response.body();
                    if (resp != null) {
                        About_response.Sofra aboutSofra = resp.getProduct().get(0);
                        content.setText(aboutSofra.getContent());
                        content.setTypeface(typeface1);
                        if (aboutSofra.getImage() != null && !aboutSofra.getImage().equals("")) {

                            Glide.with(mcontect)
                                    .load(aboutSofra.getImage())
                                    .centerCrop()
//                                    .error(R.drawable.sp)
                                    .into(image);

                        }
                    }
                }

                @Override
                public void onFailure(Call<About_response> call, Throwable t) {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });

        } else {
            Toast.makeText(mcontect, activity.getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
        }


        return view;
    }


}
