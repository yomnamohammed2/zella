package com.emcan.zella.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.emcan.zella.Api_Service;
import com.emcan.zella.Beans.Review;
import com.emcan.zella.Beans.Sub_Cat_Images_Model;
import com.emcan.zella.Beans.Sub_Category;
import com.emcan.zella.Config;
import com.emcan.zella.ConnectionDetection;
import com.emcan.zella.R;
import com.emcan.zella.SharedPrefManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;


public class Add_Rate extends Fragment {

    Sub_Category dish;
    Toolbar toolbar;
    TextView title,num;
    ImageView cart;
    String lang;
    Typeface typeface;
    ConnectionDetection connectionDetection;
    Review myReview;
    ArrayList<Review> reviews;
     AppCompatActivity activity;
     Context context;
    FragmentManager fragmentManager;

    public Add_Rate() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    View view=inflater.inflate(R.layout.fragment_add__rate, container, false);
    //--------------------tool bar----------------//
context=getContext();

         activity = (AppCompatActivity) getActivity();

        toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        ImageButton back=toolbar.findViewById(R.id.back);

        activity.setSupportActionBar(toolbar);

        lang= SharedPrefManager.getInstance(activity).getLang();
        if(lang.equals("en")){
            typeface = ResourcesCompat.getFont(activity, R.font.amaranth_bold);
            back.setRotation(180);
        }
        if(lang.equals("ar")){
            typeface= ResourcesCompat.getFont(activity, R.font.bein_black);
        }
        //////////////////////////////////////////////////

        RelativeLayout no=toolbar.findViewById(R.id.no_cart);
        RelativeLayout yes=toolbar.findViewById(R.id.yes_cart);

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
        cart.setVisibility(View.VISIBLE);
        ImageView menu=toolbar.findViewById(R.id.menu);
        menu.setVisibility(View.VISIBLE);
        fragmentManager = activity.getSupportFragmentManager();
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new MyCart();

                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment).addToBackStack("xyz")
                        .commit();
            }
        });

        back.setVisibility(View.VISIBLE);

        final ProgressBar progressBar=view.findViewById(R.id.progressBar);

    //------------get dish data---------------//

        if(this.getArguments().getSerializable("dish")!=null){
            dish=(Sub_Category) this.getArguments().getSerializable("dish");
            title.setText(activity.getResources().getString(R.string.addrate));
          //  title.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
           // Toast.makeText(getContext(),dish.getSub_category_name(),Toast.LENGTH_SHORT).show();
        }



        TextView title1=view.findViewById(R.id.title1);
        title1.setTypeface(typeface);
        TextView title2=view.findViewById(R.id.title2);
        title2.setTypeface(typeface);


        final RatingBar ratingBar=view.findViewById(R.id.add_rate);
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.setColorFilter(Color.parseColor("#ffc501"), PorterDuff.Mode.SRC_ATOP);
        final EditText comment=view.findViewById(R.id.add_comment);

        ///////////////edit rate//////////////////
        if(this.getArguments().getParcelable("rate")!=null){
            myReview=this.getArguments().getParcelable("rate");
            reviews = getArguments().getParcelableArrayList("reviews");
            title.setText(activity.getResources().getString(R.string.editrate));

            ratingBar.setRating(myReview.getRate());
            comment.setText(myReview.getComment());
           // Toast.makeText(getContext(),myReview.getComment(),Toast.LENGTH_SHORT).show();
        }



        Button do_rate=view.findViewById(R.id.rate);
        do_rate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String comm=comment.getText().toString();
                int rate=(int) ratingBar.getRating();
                if (comm.isEmpty() || comm.length() == 0 || comm.equals("") || comm == null) {
                comment.requestFocus();
                    Toast.makeText(context,activity.getResources().getString(R.string.addre),Toast.LENGTH_SHORT).show();
                }else{
                    comment.clearFocus();
                    if(rate==0){
                        ratingBar.requestFocus();
                        Toast.makeText(context,activity.getResources().getString(R.string.addr),Toast.LENGTH_SHORT).show();
                    }
                    else {
                        connectionDetection = new ConnectionDetection(context);

                        if (connectionDetection.isConnected()) {
                            progressBar.setVisibility(View.VISIBLE);
                            if(myReview!=null){
                                Api_Service requestInterface = Config.getClient().create(Api_Service.class);
                                Call<Sub_Cat_Images_Model> response1 = requestInterface.edit_Comment(myReview.getComment_id(),comm, String.valueOf(rate));
                                response1.enqueue(new Callback<Sub_Cat_Images_Model>() {
                                    @Override
                                    public void onResponse(Call<Sub_Cat_Images_Model> call, retrofit2.Response<Sub_Cat_Images_Model> response) {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        Sub_Cat_Images_Model resp = response.body();
                                        if (resp != null) {
                                            if (resp.getSuccess() == 1) {
                                                Toast.makeText(context, activity.getResources().getString(R.string.commedited), Toast.LENGTH_SHORT).show();
                                               fragmentManager.popBackStack();
                                               fragmentManager.popBackStack();
                                                Fragment fragment=new Reviews();

                                                Bundle bundle = new Bundle();
                                                bundle.putString("sub_cat_id", myReview.getSub_category_id());
                                                fragment.setArguments(bundle);
                                                fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack("xyz")
                                                        .commit();


                                            } else {
                                                Toast.makeText(context, activity.getResources().getString(R.string.errortryagain), Toast.LENGTH_SHORT).show();

                                            }
                                        } else {
                                            Toast.makeText(context,  activity.getResources().getString(R.string.errortryagain), Toast.LENGTH_SHORT).show();

                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Sub_Cat_Images_Model> call, Throwable t) {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(context, activity.getResources().getString(R.string.commedited), Toast.LENGTH_SHORT).show();
                                       fragmentManager.popBackStack();
                                      fragmentManager.popBackStack();
                                        Fragment fragment=new Reviews();

                                        Bundle bundle = new Bundle();
                                        bundle.putString("sub_cat_id", myReview.getSub_category_id());
                                        fragment.setArguments(bundle);
                                        fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack("xyz")
                                                .commit();
                                    }
                                });
                            }else {
                                Api_Service requestInterface = Config.getClient().create(Api_Service.class);

//                                Call<Sub_Cat_Images_Model> response1 = requestInterface.addComment(SharedPrefManager.getInstance(context).getUser().getClient_id(),
//                                        dish.getSub_category_id(), comm, String.valueOf(rate));
//                                response1.enqueue(new Callback<Sub_Cat_Images_Model>() {
//                                    @Override
//                                    public void onResponse(Call<Sub_Cat_Images_Model> call, retrofit2.Response<Sub_Cat_Images_Model> response) {
//                                        progressBar.setVisibility(View.INVISIBLE);
//                                        Sub_Cat_Images_Model resp = response.body();
//                                        if (resp != null) {
//                                            if (resp.getSuccess() == 1) {
//                                                Toast.makeText(context, activity.getResources().getString(R.string.commadded), Toast.LENGTH_SHORT).show();
//                                          fragmentManager.popBackStack();
//                                            } else {
//                                                Toast.makeText(context, activity.getResources().getString(R.string.errortryagain), Toast.LENGTH_SHORT).show();
//
//                                            }
//                                        } else {
//                                            Toast.makeText(context, activity.getResources().getString(R.string.errortryagain), Toast.LENGTH_SHORT).show();
//
//                                        }
//                                    }
//                                    @Override
//                                    public void onFailure(Call<Sub_Cat_Images_Model> call, Throwable t) {
//                                        progressBar.setVisibility(View.INVISIBLE);
//                                        Toast.makeText(context, activity.getResources().getString(R.string.errortryagain), Toast.LENGTH_SHORT).show();
//                                    }
//                                });
                            }

                        } else {
                            Toast.makeText(context, activity.getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
                        }

                    }
                }

            }
        });


        return view;
    }




}
