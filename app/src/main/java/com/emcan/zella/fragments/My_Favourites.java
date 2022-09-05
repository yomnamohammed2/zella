package com.emcan.zella.fragments;

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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emcan.zella.Api_Service;
import com.emcan.zella.Beans.Sub_Category;
import com.emcan.zella.Beans.fav_Response;
import com.emcan.zella.Config;
import com.emcan.zella.ConnectionDetection;
import com.emcan.zella.R;
import com.emcan.zella.SharedPrefManager;
import com.emcan.zella.activities.MainActivity;
import com.emcan.zella.adapters.Fav_adapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;


public class My_Favourites extends Fragment {

    ConnectionDetection connectionDetection;
    ProgressBar progressBar;
    Toolbar toolbar;
    TextView title,num;
    ImageView cart;
ArrayList<Sub_Category> favourites;
ImageView image;
TextView message;
String lang;
Typeface typeface;
    RelativeLayout pickup;


    public My_Favourites() {

    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_my__favourites, container, false);

        final AppCompatActivity activity = (AppCompatActivity) getActivity();

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
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
        //   title=(TextView)toolbar.getRootView().findViewById(R.id.toolbar_title);
        RelativeLayout no=toolbar.findViewById(R.id.no_cart);
        RelativeLayout yes=toolbar.findViewById(R.id.yes_cart);


        RelativeLayout bar = activity.findViewById(R.id.bar);
        bar.setVisibility(View.VISIBLE);
        ((MainActivity) activity).select_icon("none");

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
        title.setText(activity.getResources().getString(R.string.favourite));
        cart.setVisibility(View.VISIBLE);
        ImageView menu=toolbar.findViewById(R.id.menu);
        menu.setVisibility(View.VISIBLE);

        back.setVisibility(View.VISIBLE);


        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new MyCart();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment).addToBackStack("xyz")
                        .commit();
            }
        });



        connectionDetection=new ConnectionDetection(getContext());

        //--------------get favourites from server------------//
 message=view.findViewById(R.id.message);
 message.setTypeface(typeface);
        image=view.findViewById(R.id.image);
final RecyclerView recyclerView=view.findViewById(R.id.recycler_fav);
        progressBar=view.findViewById(R.id.progressBar);

if(SharedPrefManager.getInstance(getContext()).isLoggedIn()) {
    if (connectionDetection.isConnected()) {
        progressBar.setVisibility(View.VISIBLE);
        Api_Service requestInterface = Config.getClient().create(Api_Service.class);
        Call<fav_Response> response1 = requestInterface.getFavourites(SharedPrefManager.getInstance(getContext()).getUser()
                .getClient_id(),lang);
        response1.enqueue(new Callback<fav_Response>() {
            @Override
            public void onResponse(Call<fav_Response> call, retrofit2.Response<fav_Response> response) {
                progressBar.setVisibility(View.INVISIBLE);
                fav_Response resp = response.body();
                if (resp != null) {

                    message.setVisibility(View.INVISIBLE);
                    if (resp.getSuccess() == 1) {
                        favourites = resp.getProduct();
                        if (favourites.size() > 0) {

                            Fav_adapter mAdapter = new Fav_adapter(getContext(), favourites);
                            // mAdapter.notifyDataSetChanged();
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(mAdapter);
                            image.setVisibility(View.INVISIBLE);
                            message.setVisibility(View.INVISIBLE);

                        } else {
                            message.setVisibility(View.VISIBLE);
                        }
                    }
                } else {
                    message.setVisibility(View.VISIBLE);
                    //  Toast.makeText(getContext(), "خطأ حاول مجددا", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<fav_Response> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                message.setVisibility(View.VISIBLE);
                //   Toast.makeText(getContext(), "خطأ حاول مجددا", Toast.LENGTH_SHORT).show();
            }
        });
    } else {
        progressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(getContext(), activity.getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
    }
}else{
    message.setVisibility(View.VISIBLE);
}

        return view;
    }


}
