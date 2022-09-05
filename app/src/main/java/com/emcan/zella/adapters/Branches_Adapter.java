package com.emcan.zella.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;


import com.emcan.zella.Beans.Branch_Model;
import com.emcan.zella.ConnectionDetection;
import com.emcan.zella.GET_DATA;
import com.emcan.zella.R;
import com.emcan.zella.SharedPrefManager;

import java.util.ArrayList;

public class Branches_Adapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    private ArrayList<Branch_Model.Branch> revies;
    private final Context mContext;
    ConnectionDetection connectionDetection;
   GET_DATA data;
    String lang;
    Typeface typeface;
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public View view;
        TextView text,distance,address,phone;
        CheckBox ch;



        public MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;

            text = (TextView) view.findViewById(R.id.text);
            ch=  view.findViewById(R.id.cb);
            distance=view.findViewById(R.id.distance);
            address=view.findViewById(R.id.address);
            phone=view.findViewById(R.id.phone);

            distance.setTypeface(typeface);
            address.setTypeface(typeface);
            phone.setTypeface(typeface);
            text.setTypeface(typeface);

            ch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(revies.get(getLayoutPosition()).getCheck()==0){

                        for(int i=0;i<revies.size();i++){
                            revies.get(i).setCheck(0);
                        }
                        revies.get(getLayoutPosition()).setCheck(1);
                        data.get_ID(revies.get(getLayoutPosition()));
                        notifyDataSetChanged();

                    }else{
                        revies.get(getLayoutPosition()).setCheck(0);
                        data.remove_ID();
                        notifyItemChanged(getLayoutPosition());

                    }

                }
            });

        }
    }


    public Branches_Adapter (Context mContext, ArrayList<Branch_Model.Branch> reviewList, GET_DATA data) {
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

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.region_item, viewGroup, false);
        return new Branches_Adapter.MyViewHolder(view);

    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        connectionDetection=new ConnectionDetection(mContext);

        final Branch_Model.Branch rate = revies.get(position);
        if (rate.getName()!= null) {
            ((MyViewHolder) holder).text.setText(rate.getName());
            ((MyViewHolder) holder).text.setTypeface(typeface);
        }

        if(rate.getCheck()==0){
            ( (MyViewHolder) holder).ch.setChecked(false);
        }
else{
            ( (MyViewHolder) holder).ch.setChecked(true);
        }

         if(rate.getAddress()!=null&&rate.getAddress().length()>0){
             ((MyViewHolder) holder).address.setText(rate.getAddress());
         }

         if(rate.getDistance()!=null&&rate.getDistance().length()>0){
             ((MyViewHolder) holder).distance.setText(rate.getDistance()+"  "+mContext.getResources().getString(R.string.km));
         }

        if(rate.getPhone()!=null&&rate.getPhone().length()>0){
            ((MyViewHolder) holder).phone.setText(rate.getPhone());
        }
    }



    @Override
    public int getItemCount() {
        return revies.size();
    }

}



