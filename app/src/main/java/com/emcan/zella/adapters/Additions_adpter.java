package com.emcan.zella.adapters;

import android.content.Context;
import android.graphics.Typeface;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.emcan.zella.Beans.Additions_Model;
import com.emcan.zella.ConnectionDetection;
import com.emcan.zella.GET_DATA;
import com.emcan.zella.R;
import com.emcan.zella.SharedPrefManager;

import java.util.ArrayList;

public class Additions_adpter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


        private ArrayList<Additions_Model.Addition> revies;
        private final Context mContext;
        ConnectionDetection connectionDetection;
        GET_DATA fragment;
        String lang;
        Typeface typeface;

        public class MyViewHolder extends RecyclerView.ViewHolder {

            public View view;
            TextView name,price;
            ImageView check;

            public MyViewHolder(View itemView) {
                super(itemView);
                view = itemView;

                name = (TextView) view.findViewById(R.id.name);
                check=  view.findViewById(R.id.check);
                price=  view.findViewById(R.id.price);

               itemView.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       if(revies.get(getLayoutPosition()).getCheck()==0){

                           revies.get(getLayoutPosition()).setCheck(1);
                           fragment.get_add(revies.get(getLayoutPosition()),1);
                           notifyItemChanged(getLayoutPosition());

                       }else{
                           revies.get(getLayoutPosition()).setCheck(0);
                           fragment.get_add(revies.get(getLayoutPosition()),0);
                           notifyItemChanged(getLayoutPosition());

                       }

                   }
               });



            }
        }


        public Additions_adpter (Context mContext, ArrayList<Additions_Model.Addition> reviewList, GET_DATA fragment) {
            this.revies= reviewList;
            this.mContext=mContext;
            this.fragment=fragment;
            lang= SharedPrefManager.getInstance(mContext).getLang();
            if(lang.equals("en")){
                typeface = ResourcesCompat.getFont(mContext, R.font.amaranth_regular);
            }
            if(lang.equals("ar")){
                typeface = ResourcesCompat.getFont(mContext, R.font.bein_black);
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.meal_addition_item, viewGroup, false);
            return new Additions_adpter.MyViewHolder(view);

        }


        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

            connectionDetection=new ConnectionDetection(mContext);

            final Additions_Model.Addition rate = revies.get(position);
            if (rate.getAddition_name()!= null) {
                ((MyViewHolder) holder).name.setText(rate.getAddition_name());
                ((MyViewHolder) holder).name.setTypeface(typeface);
            }

            if (rate.getAddition_price()!= null) {
                    ((MyViewHolder) holder).price.setText(rate.getAddition_price()+" "+mContext.getResources().getString(R.string.bhd));

            }

            if(rate.getCheck()==0){
                ( (MyViewHolder) holder).check.setImageDrawable(mContext.getResources().getDrawable(R.drawable.unselected));
            }
            else{
                ( (MyViewHolder) holder).check.setImageDrawable(mContext.getResources().getDrawable(R.drawable.selected));
            }

        }



        @Override
        public int getItemCount() {
            return revies.size();
        }

    }



