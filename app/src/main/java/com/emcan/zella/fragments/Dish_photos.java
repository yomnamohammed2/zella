package com.emcan.zella.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.emcan.zella.R;


public class Dish_photos extends Fragment {


    public Dish_photos() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_dish_photos, container, false);

        String image =getArguments().getString("image_link");

        ImageView dish_image=(ImageView)view
                .findViewById(R.id.dish_photo);
        if (image!= null) {
          //  Toast.makeText(getContext(),image,Toast.LENGTH_SHORT).show();

            Glide.with(getContext())
                    .load(image)
                    .error(R.drawable.mainicon)
                    .into(dish_image);

        }
        return view;
    }



}
