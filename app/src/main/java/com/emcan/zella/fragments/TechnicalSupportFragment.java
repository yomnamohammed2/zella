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


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.emcan.zella.Api_Service;
import com.emcan.zella.Beans.Contact_Response;
import com.emcan.zella.Beans.TechnicalResponse;
import com.emcan.zella.Config;
import com.emcan.zella.ConnectionDetection;
import com.emcan.zella.R;
import com.emcan.zella.SharedPrefManager;
import com.emcan.zella.activities.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by HP on 2019-09-24.
 */

public class TechnicalSupportFragment extends Fragment {
    TextView txt1,txt2;
    ProgressBar progressBar;
    Toolbar toolbar;
    TextView title, num;
    RelativeLayout pickup;

    ImageView cart;
    RelativeLayout no, yes;
    ConnectionDetection connectionDetection;
    public static String FACEBOOK_URL ;
    Contact_Response.Contact contact;
    AppCompatActivity activity;
    Context context;
    String lang;
    Typeface typeface;
    FragmentManager fragmentManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.technical_support,container,false);
        activity= (AppCompatActivity) getActivity();
        connectionDetection=new ConnectionDetection(activity);
        toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        lang= SharedPrefManager.getInstance(activity).getLang();

        ImageButton back=toolbar.findViewById(R.id.back);
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
        title.setText(activity.getResources().getString(R.string.technical));
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

        RelativeLayout bar = activity.findViewById(R.id.bar);
        bar.setVisibility(View.VISIBLE);
        ((MainActivity) activity).select_icon("none");

        txt1=v.findViewById(R.id.txt1);
        txt2=v.findViewById(R.id.txt2);
        progressBar=v.findViewById(R.id.progressBar);
        getTechnical();
        return v;
    }

    public void getTechnical(){
        if (connectionDetection.isConnected()) {
            progressBar.setVisibility(View.VISIBLE);
            Api_Service requestInterface = Config.getClient().create(Api_Service.class);
            Call<TechnicalResponse> response1 = requestInterface.get_Technical(lang);
            response1.enqueue(new Callback<TechnicalResponse>() {
                @Override
                public void onResponse(Call<TechnicalResponse> call, retrofit2.Response<TechnicalResponse> response) {
                    progressBar.setVisibility(View.GONE);
                    TechnicalResponse resp = response.body();
                    if (resp != null) {

                        if (resp.getSuccess() == 1) {
                            if (resp.getProduct().size()>0) {
                               if(resp.getProduct().get(0).getTitle()!=null){
                                   txt1.setText(resp.getProduct().get(0).getTitle());
                                   txt1.setTypeface(typeface);
                               }
                               if(resp.getProduct().get(0).getNumber()!=null){
                                   txt2.setText(resp.getProduct().get(0).getNumber());
                                   txt2.setTypeface(typeface);
                               }


                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<TechnicalResponse> call, Throwable t) {
                    //   Toast.makeText(getContext(), "خطأ حاول مجددا", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(context, activity.getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
        }
    }
}
