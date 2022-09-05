package com.emcan.zella.adapters;

import android.content.Context;
import android.graphics.Typeface;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;


import com.emcan.zella.Beans.About_response;
import com.emcan.zella.ConnectionDetection;
import com.emcan.zella.R;
import com.emcan.zella.SharedPrefManager;

import java.util.ArrayList;

public class Messages_Adapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    private ArrayList<About_response.Sofra> revies;
    private final Context mContext;
    ConnectionDetection connectionDetection;

    String lang;
    Typeface typeface;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public View view;
        TextView text,date;



        public MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;

            text = (TextView) view.findViewById(R.id.content);
            date=view.findViewById(R.id.date);


        }
    }


    public Messages_Adapter (Context mContext, ArrayList<About_response.Sofra> reviewList) {
        this.revies= reviewList;
        this.mContext=mContext;
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

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.messages_item, viewGroup, false);
        return new Messages_Adapter.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        setFadeAnimation(holder.itemView);

        connectionDetection=new ConnectionDetection(mContext);

        final About_response.Sofra rate = revies.get(position);
        if (rate.getContent()!= null) {
            ((MyViewHolder) holder).text.setText(rate.getContent());
            ((MyViewHolder) holder).text.setTypeface(typeface);
        }
        if (rate.getDate()!= null) {
            ((MyViewHolder) holder).date.setText(rate.getDate());
            ((MyViewHolder) holder).date.setTypeface(typeface);
        }

//        if (rate.getContent()!= null) {
//            ((MyViewHolder) holder).date.setText(rate.getContent());
//
//        }

        ((MyViewHolder) holder).text.setTypeface(typeface);
//       ((MyViewHolder) holder).date.setTypeface(m_typeFace);


    }



    @Override
    public int getItemCount() {
        return revies.size();
    }



    public void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(500);
        view.startAnimation(anim);
    }
}



