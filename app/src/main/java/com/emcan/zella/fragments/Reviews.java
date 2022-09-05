package com.emcan.zella.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.emcan.zella.Api_Service;
import com.emcan.zella.Beans.Review;
import com.emcan.zella.Beans.Reviews_Model;
import com.emcan.zella.Beans.Sub_Cat_Images_Model;
import com.emcan.zella.Config;
import com.emcan.zella.ConnectionDetection;
import com.emcan.zella.R;
import com.emcan.zella.SharedPrefManager;
import com.emcan.zella.activities.MainActivity;
import com.emcan.zella.adapters.Reviews_adapter;
import com.emcan.zella.databinding.FragmentReviewsBinding;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class Reviews extends Fragment {


    Context context;
    ConnectionDetection connectionDetection;
    AppCompatActivity activity;
    FragmentReviewsBinding binding;
    Typeface typeface;
    String lang;
    Toolbar toolbar;
    TextView title, num;
    ImageView cart;
    RelativeLayout no, yes;
    String id;
    FragmentManager fragmentManager;
    ArrayList<Review> reviews;
    ImageButton back;


    public Reviews() {
        // Required empty public constructor
    }


    public static Reviews newInstance(String param1, String param2) {
        Reviews fragment = new Reviews();
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
        binding= FragmentReviewsBinding.inflate(inflater, container, false);

        init();

        lang= SharedPrefManager.getInstance(activity).getLang();
        if(lang.equals("en")){
            typeface = ResourcesCompat.getFont(activity, R.font.amaranth_bold);
        }
        if(lang.equals("ar")){
            typeface= ResourcesCompat.getFont(activity, R.font.bein_black);
        }
        fragmentManager=activity.getSupportFragmentManager();

        setUp_Toolbar();


        RelativeLayout bar = activity.findViewById(R.id.bar);
        bar.setVisibility(View.GONE);
        ((MainActivity) activity).select_icon("home");

        binding.addComment.setTypeface(typeface);
        binding.rate.setTypeface(typeface);
        binding.title1.setTypeface(typeface);
        binding.title2.setTypeface(typeface);
        LayerDrawable stars = (LayerDrawable)  binding.addRate.getProgressDrawable();
        DrawableCompat.setTint(DrawableCompat.wrap(stars.getDrawable(0)), Color.parseColor("#d3d3d3"));


        id=getArguments().getString("sub_cat_id");

        get_Reviews();



        binding. rate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View view) {

                if(SharedPrefManager.getInstance(context).isLoggedIn()){

                    String comm = binding.addComment.getText().toString();
                    int rate = (int) binding.addRate.getRating();
                    if (comm.isEmpty() || comm.length() == 0 || comm.equals("") || comm == null) {
                        binding.addComment.requestFocus();
                        Toast.makeText(context, activity.getResources().getString(R.string.addre), Toast.LENGTH_SHORT).show();
                    } else {
                        binding.addComment.clearFocus();
                        if (rate == 0) {
                            binding.addRate.requestFocus();
                            Toast.makeText(context, activity.getResources().getString(R.string.addr), Toast.LENGTH_SHORT).show();
                        } else {
                            connectionDetection = new ConnectionDetection(context);

                            if (connectionDetection.isConnected()) {
                                binding.progressBar.setVisibility(View.VISIBLE);

                                Api_Service requestInterface = Config.getClient().create(Api_Service.class);
                                JsonObject h = new JsonObject();
                                h.addProperty("client_id", SharedPrefManager.
                                        getInstance(context).getUser().getClient_id());
                                h.addProperty("comment", comm);
                                h.addProperty("rate", String.valueOf(rate));
                                h.addProperty("sub_category_id", id);
                                h.addProperty("lang", lang);

                                Call<Sub_Cat_Images_Model> response1 = requestInterface.addComment(h);
                                response1.enqueue(new Callback<Sub_Cat_Images_Model>() {
                                    @Override
                                    public void onResponse(Call<Sub_Cat_Images_Model> call, retrofit2.Response<Sub_Cat_Images_Model> response) {
                                        binding.progressBar.setVisibility(View.INVISIBLE);
                                        Sub_Cat_Images_Model resp = response.body();
                                        if (resp != null) {
                                            if (resp.getSuccess() == 1) {
                                                Toast.makeText(context, activity.getResources().getString(R.string.commadded), Toast.LENGTH_SHORT).show();
                                                InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
                                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                                                   get_Reviews();
                                            } else {
                                                Toast.makeText(context, resp.getMessage(), Toast.LENGTH_SHORT).show();

                                            }
                                        } else {
                                            Toast.makeText(context, resp.getMessage(), Toast.LENGTH_SHORT).show();

                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Sub_Cat_Images_Model> call, Throwable t) {

                                        binding.progressBar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        }
                    }
                }else{
                    Toast.makeText(context, context.getResources().getString(R.string.signfirst), Toast.LENGTH_SHORT).show();


                }

            }
        });



        return binding.getRoot();
    }


    public void get_Reviews(){
        if(connectionDetection.isConnected()) {
            binding.progressBar.setVisibility(View.VISIBLE);
            Api_Service requestInterface = Config.getClient().create(Api_Service.class);
            Call<Reviews_Model> response2 = requestInterface.get_Sub_Cat_Comments(id, lang, SharedPrefManager.
                    getInstance(context).getUser().getClient_id());
            response2.enqueue(new Callback<Reviews_Model>() {
                @Override
                public void onResponse(Call<Reviews_Model> call, retrofit2.Response<Reviews_Model> response) {
                    Reviews_Model resp = response.body();
                    binding.progressBar.setVisibility(View.GONE);

                    if (resp != null) {
                        if (resp.getSuccess() == 1) {

                            reviews = resp.getProduct();
                            if (reviews.size() > 0) {

                                binding.recyclerReviews.setVisibility(View.VISIBLE);

                                Reviews_adapter mAdapter = new Reviews_adapter(context, reviews,
                                        binding.getRoot(),Reviews.this);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                                binding.recyclerReviews.setLayoutManager(mLayoutManager);
                                binding.recyclerReviews.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL));

                                binding.recyclerReviews.setItemAnimator(new DefaultItemAnimator());
                                binding.recyclerReviews.setAdapter(mAdapter);
                            }else{
                                binding.recyclerReviews.setVisibility(View.GONE);
                            }

                        }
                    }
                }

                @Override
                public void onFailure(Call<Reviews_Model> call, Throwable t) {

                    binding.progressBar.setVisibility(View.GONE);
                    binding.recyclerReviews.setVisibility(View.GONE);

                    Toast.makeText(getContext(), context.getResources().getString(
                            R.string.networkerror), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void init(){
        context=getContext();
        activity = (AppCompatActivity) getActivity();
        connectionDetection=new ConnectionDetection(context);

        fragmentManager=activity.getSupportFragmentManager();
    }


//    public void edit_comment(){
//
//            Api_Service requestInterface = Config.getClient().create(Api_Service.class);
//            JsonObject h=new JsonObject();
//            h.addProperty("client_id",SharedPrefManager.
//                    getInstance(context).getUser().getClient_id());
//            h.addProperty("comment",comm);
//            h.addProperty("comment_id",myReview.getComment_id());
//            h.addProperty("rate", String.valueOf(rate));
//
//            Call<Sub_Cat_Images_Model> response1 = requestInterface.edit_Comment(h);
//            response1.enqueue(new Callback<Sub_Cat_Images_Model>() {
//                @Override
//                public void onResponse(Call<Sub_Cat_Images_Model> call, retrofit2.Response<Sub_Cat_Images_Model> response) {
//                    progressBar.setVisibility(View.INVISIBLE);
//                    Sub_Cat_Images_Model resp = response.body();
//                    if (resp != null) {
//                        if (resp.getSuccess() == 1) {
//                            Toast.makeText(context, activity.getResources().getString(R.string.commedited), Toast.LENGTH_SHORT).show();
//                            fragmentManager.popBackStack();
//                            fragmentManager.popBackStack();
//                            Fragment fragment=new Reviews();
//
//                            Bundle bundle = new Bundle();
//                            bundle.putString("sub_cat_id", myReview.getSub_category_id());
//                            fragment.setArguments(bundle);
//                            fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack("xyz")
//                                    .commit();
//
//
//                        } else {
//                            Toast.makeText(context, activity.getResources().getString(R.string.errortryagain), Toast.LENGTH_SHORT).show();
//
//                        }
//                    } else {
//                        Toast.makeText(context,  activity.getResources().getString(R.string.errortryagain), Toast.LENGTH_SHORT).show();
//
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<Sub_Cat_Images_Model> call, Throwable t) {
//                    progressBar.setVisibility(View.INVISIBLE);
//                    Toast.makeText(context, activity.getResources().getString(R.string.commedited), Toast.LENGTH_SHORT).show();
//                    fragmentManager.popBackStack();
//                    fragmentManager.popBackStack();
//                    Fragment fragment=new Reviews();
//
//                    Bundle bundle = new Bundle();
//                    bundle.putString("sub_cat_id", myReview.getSub_category_id());
//                    fragment.setArguments(bundle);
//                    fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack("xyz")
//                            .commit();
//                }
//            });
//
//    }
private void setUp_Toolbar(){
    toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
    activity.setSupportActionBar(toolbar);
    back=toolbar.findViewById(R.id.back);
    no=toolbar.findViewById(R.id.no_cart);
    yes=toolbar.findViewById(R.id.yes_cart);

    num=toolbar.getRootView().findViewById(R.id.num);
    fragmentManager=activity.getSupportFragmentManager();
//        comments=toolbar.findViewById(R.id.comment);
//        comments.setVisibility(View.VISIBLE);
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
    title.setText(getResources().getString(R.string.app_name));
    back.setVisibility(View.INVISIBLE);

    cart.setVisibility(View.VISIBLE);
    ImageView menu=toolbar.findViewById(R.id.menu);
    menu.setVisibility(View.VISIBLE);

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


}


}
