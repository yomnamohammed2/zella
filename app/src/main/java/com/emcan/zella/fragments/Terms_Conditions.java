package com.emcan.zella.fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.emcan.zella.Api_Service;
import com.emcan.zella.Beans.Terms_Respose;
import com.emcan.zella.Config;
import com.emcan.zella.ConnectionDetection;
import com.emcan.zella.R;
import com.emcan.zella.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;


public class Terms_Conditions extends Fragment {

    String type;
    View view;
    ConnectionDetection connectionDetection;
    Context context;
    TextView txt;
    ProgressBar progressBar;
    AppCompatActivity activity1;
    RelativeLayout pickup;


    Toolbar toolbar;
    TextView title, num;
    ImageView cart;
    Typeface typeface;
    String lang;


    public Terms_Conditions() {
        // Required empty public constructor
    }


    public static Terms_Conditions newInstance(String type) {
        Terms_Conditions fragment = new Terms_Conditions();
        Bundle args = new Bundle();
        args.putString("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getString("type");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_terms__conditions, container, false);

        init();

        lang = SharedPrefManager.getInstance(activity1).getLang();
        if (lang.equals("en")) {
            typeface = ResourcesCompat.getFont(activity1, R.font.amaranth_bold);
        }
        if (lang.equals("ar")) {
            typeface= ResourcesCompat.getFont(activity1, R.font.bein_black);
        }

        setToolbar();


        get_terms();

        return view;

    }

    private void init() {
        context = getContext();
        activity1 = (AppCompatActivity) getActivity();
        connectionDetection = new ConnectionDetection(context);
        txt = view.findViewById(R.id.txt);
        progressBar = view.findViewById(R.id.progressBar);
    }

    private void get_terms() {
        if (connectionDetection.isConnected()) {
            progressBar.setVisibility(View.VISIBLE);

            Api_Service requestInterface = Config.getClient().create(Api_Service.class);
            Call<Terms_Respose> response1 = requestInterface.get_Terms(type,lang);
            response1.enqueue(new Callback<Terms_Respose>() {
                @Override
                public void onResponse(Call<Terms_Respose> call, retrofit2.Response<Terms_Respose> response) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Terms_Respose resp = response.body();
                    if (resp != null) {
                        Terms_Respose.Terms_Model aboutSofra = resp.getProduct().get(0);
                        txt.setText(aboutSofra.getName());
                    }
                }

                @Override
                public void onFailure(Call<Terms_Respose> call, Throwable t) {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });

        } else {
            Toast.makeText(context, "خطأ في الاتصال بشبكة الانترنت", Toast.LENGTH_SHORT).show();
        }


    }

    private void setToolbar() {
        toolbar = (Toolbar) activity1.findViewById(R.id.toolbar);
        activity1.setSupportActionBar(toolbar);

        RelativeLayout no = toolbar.findViewById(R.id.no_cart);
        RelativeLayout yes = toolbar.findViewById(R.id.yes_cart);

        num = toolbar.getRootView().findViewById(R.id.num);
        title=toolbar.getRootView().findViewById(R.id.toolbar_title);

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
        title.setText(activity1.getResources().getString(R.string.terms));
        ImageButton back = toolbar.findViewById(R.id.back);
        back.setVisibility(View.VISIBLE);

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

    }



}
