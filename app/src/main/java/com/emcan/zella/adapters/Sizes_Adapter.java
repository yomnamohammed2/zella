package com.emcan.zella.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.emcan.zella.Beans.Prices_Model;
import com.emcan.zella.ConnectionDetection;
import com.emcan.zella.GET_DATA;
import com.emcan.zella.R;
import com.emcan.zella.SharedPrefManager;

import java.util.ArrayList;

public class Sizes_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    private ArrayList<Prices_Model.Price> revies;
    private final Context mContext;
    ConnectionDetection connectionDetection;
    GET_DATA data;
    String lang;
    Typeface typeface;
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public View view;
        TextView size,price,price_before;
        ImageView add;



        public MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;

            size = (TextView) view.findViewById(R.id.size);
            price = (TextView) view.findViewById(R.id.price);
            add=  view.findViewById(R.id.add);
            price_before=view.findViewById(R.id.price_before);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(revies.get(getLayoutPosition()).getCheck()==0){

                        for(int i=0;i<revies.size();i++){
                            if(revies.get(i).getCheck()==1) {
                                revies.get(i).setCheck(0);
                                notifyItemChanged(i);
                            }

                        }
                        revies.get(getLayoutPosition()).setCheck(1);
                        data.getSize(revies.get(getLayoutPosition()));
                        notifyItemChanged(getLayoutPosition());

                    }

                }
            });

        }
    }


    public Sizes_Adapter (Context mContext, ArrayList<Prices_Model.Price> reviewList, GET_DATA data) {
        this.revies= reviewList;
        this.mContext=mContext;
        this.data=data;
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

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.size_item, viewGroup, false);
        return new Sizes_Adapter.MyViewHolder(view);

    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        connectionDetection=new ConnectionDetection(mContext);


        final Prices_Model.Price rate = revies.get(position);
        if (rate.getSub_category_size_name()!= null) {
            ((MyViewHolder) holder).size.setText(rate.getSub_category_size_name());
            ((MyViewHolder) holder).price.setText(rate.getSub_category_size_price()+" "+
                    mContext.getResources().getString(R.string.bhd));

            ((MyViewHolder) holder).size.setTypeface(typeface);
            ((MyViewHolder) holder).price.setTypeface(typeface);
//            ((MyViewHolder) holder).price.setTypeface(typeface);
        }

        ((MyViewHolder) holder).price_before.setTypeface(typeface);

        if(position==0){
            rate.setCheck(1);
        }
        if(rate.getCheck()==0){
            ( (MyViewHolder) holder).add.setImageDrawable(mContext.getResources().getDrawable(R.drawable.add));
        }
        else{
            ( (MyViewHolder) holder).add.setImageDrawable(mContext.getResources().getDrawable(R.drawable.check));
        }

        if(rate.getDiscount()!=null&&rate.getDiscount().equals("1")&&rate.getSub_category_size_price()!=null&&
        rate.getSub_category_size_price_after_disc()!=null){
            ( (MyViewHolder) holder).price_before.setVisibility(View.VISIBLE);
            ( (MyViewHolder) holder).price_before.setText(rate.getSub_category_size_price()+" "+
                    mContext.getResources().getString(R.string.bhd));

            ( (MyViewHolder) holder).price.setText(rate.getSub_category_size_price_after_disc()+" "+
                    mContext.getResources().getString(R.string.bhd));

            ( (MyViewHolder) holder).price_before.setPaintFlags(( (MyViewHolder) holder).price_before.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        }

    }



    @Override
    public int getItemCount() {
        return revies.size();
    }

}



