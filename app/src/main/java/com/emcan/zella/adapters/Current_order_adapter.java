package com.emcan.zella.adapters;

import android.content.Context;
import android.graphics.Typeface;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.emcan.zella.Beans.Address;
import com.emcan.zella.Beans.Current_order_model;
import com.emcan.zella.ConnectionDetection;
import com.emcan.zella.R;
import com.emcan.zella.SharedPrefManager;


import java.util.ArrayList;

public class Current_order_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    private ArrayList<Current_order_model.Order_item> orders;
    private final Context mContext;
    PopupWindow mPopupWindow;
    View view;
    ConnectionDetection connectionDetection;
    ProgressBar progressBar;
    ArrayList<Address> address;
    String addition_,without;
    String lang;
    Typeface typeface;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public View view;
        private final TextView name,price,addition,addition1,quantity,unit;
        private final ImageView image;
        private final LinearLayout addition_rel,without_rel;
        private final TextView without,without1;

        public MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;

            name = (TextView) view.findViewById(R.id.order_name);
            price= (TextView) view.findViewById(R.id.price);
            addition= (TextView) view.findViewById(R.id.additions);
            addition1= (TextView) view.findViewById(R.id.addition1);
            quantity= (TextView) view.findViewById(R.id.count);
            unit= (TextView) view.findViewById(R.id.unit);


            image= (ImageView) view.findViewById(R.id.order_image);


            addition_rel =  view.findViewById(R.id.addition_rel);
            without_rel =  view.findViewById(R.id.without_rel);


            without =  view.findViewById(R.id.without);
            without1 =  view.findViewById(R.id.without1);


        }
    }


    public Current_order_adapter(Context mContext, ArrayList<Current_order_model.Order_item> reviewList, View view) {
        this.orders= reviewList;
        this.mContext=mContext;
        this.view=view;

        lang= SharedPrefManager.getInstance(mContext).getLang();
        if(lang.equals("en")){
            typeface = ResourcesCompat.getFont(mContext, R.font.amaranth_bold);
        }
        if(lang.equals("ar")){
            typeface = ResourcesCompat.getFont(mContext, R.font.bein_black);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.last_order_item, viewGroup, false);
        return new Current_order_adapter.MyViewHolder(view);

    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {


        final Current_order_model.Order_item dish = orders.get(position);


        if (dish.getSub_category_name() != null) {
            ( (MyViewHolder) holder).name.setText(dish.getSub_category_name());
            ((MyViewHolder) holder).name.setTypeface(typeface);
        }

        if (dish.getAddition().size() > 0) {
            ((MyViewHolder) holder).addition_rel.setVisibility(View.VISIBLE);


            if (dish.getAddition().size() > 0) {
                for (int i = 0; i < dish.getAddition().size(); i++) {
                    if (i == 0) {
                        addition_ = dish.getAddition().get(0).getAddition_name();
                    } else {
                        addition_ = addition_ + " ,"+" " + dish.getAddition().get(i).getAddition_name();
                    }
                }
            }


            ((MyViewHolder) holder).addition1.setText(addition_);
        } else {
            ((MyViewHolder) holder).addition_rel.setVisibility(View.GONE);

        }





        ((MyViewHolder) holder).name.setTypeface(typeface);
        ((MyViewHolder) holder).addition.setTypeface(typeface);
        ((MyViewHolder) holder).addition1.setTypeface(typeface);
        ((MyViewHolder) holder).without.setTypeface(typeface);
        ((MyViewHolder) holder).without1.setTypeface(typeface);


        if (dish.getSub_category_image() != null) {

            Glide.with(mContext)
                    .load(dish.getSub_category_image())
                    .error(R.drawable.mainicon)
                    .placeholder(R.drawable.mainicon)
                    .into(((Current_order_adapter.MyViewHolder) holder).image);

        }else {

        }



        if(dish.getRemove().size()>0)
        {
            ((MyViewHolder) holder).without_rel.setVisibility(View.VISIBLE);

            if (dish.getRemove().size() > 0) {
                for (int i = 0; i < dish.getRemove().size(); i++) {
                    if (i == 0) {
                        without = dish.getRemove().get(0).getRemove_name();
                    } else {
                        without = without + " ," +" "+ dish.getRemove().get(i).getRemove_name();
                    }
                }
            }

            ((MyViewHolder) holder).without1.setText(without);
        }else{
            ((MyViewHolder) holder).without_rel.setVisibility(View.GONE);

        }


        if (dish.getPrice() != null) {
            String s =dish.getPrice();
                ( (MyViewHolder) holder).price.setText(s+" "+mContext.getResources().getString(R.string.bhd));


            ( (MyViewHolder) holder).price.setTypeface(typeface);
        }

        if (dish.getSize_price() != null) {
            String s = dish.getSize_price();
                ( (MyViewHolder) holder).unit.setText(s+" "+mContext.getResources().getString(R.string.bhd));
           
            ( (MyViewHolder) holder).unit.setTypeface(typeface);
        }

        if (dish.getQuantity() != null) {
            ( (MyViewHolder) holder).quantity.setText(dish.getQuantity());
            ( (MyViewHolder) holder).quantity.setTypeface(typeface);
        }

    }



    @Override
    public int getItemCount() {
        return orders.size();
    }

}


