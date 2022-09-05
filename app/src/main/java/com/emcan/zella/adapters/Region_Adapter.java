package com.emcan.zella.adapters;

import android.content.Context;
import android.graphics.Typeface;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.emcan.zella.Beans.Region_Model;
import com.emcan.zella.ConnectionDetection;
import com.emcan.zella.R;
import com.emcan.zella.SharedPrefManager;
import com.emcan.zella.fragments.Get_Address;
import com.emcan.zella.fragments.Get_Address_Id;

import java.util.ArrayList;

public class Region_Adapter   extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    private ArrayList<Region_Model.Region_class> revies;
    private final Context mContext;
    ConnectionDetection connectionDetection;
    Get_Address_Id fragment;
    String lang;
    Typeface typeface;
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public View view;
         TextView text;

        public MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;

           text = (TextView) view.findViewById(R.id.text);

        }
    }


    public Region_Adapter (Context mContext, ArrayList<Region_Model.Region_class> reviewList, Get_Address_Id fragment) {
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

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.region_item1, viewGroup, false);
        return new Region_Adapter.MyViewHolder(view);

    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        connectionDetection=new ConnectionDetection(mContext);

        final Region_Model.Region_class rate = revies.get(position);
        if (rate.getRegion_name()!= null) {
            (( MyViewHolder) holder).text.setText(rate.getRegion_name());
            ((MyViewHolder) holder).text.setTypeface(typeface);

        }
        ( (MyViewHolder) holder).text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               fragment.get_regiov(revies.get(position));

            }
        });

    }



    @Override
    public int getItemCount() {
        return revies.size();
    }

}



