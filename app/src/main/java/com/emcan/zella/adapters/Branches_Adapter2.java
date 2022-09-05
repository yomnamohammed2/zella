package com.emcan.zella.adapters;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.emcan.zella.Beans.Branch_Model;
import com.emcan.zella.ConnectionDetection;
import com.emcan.zella.R;
import com.emcan.zella.SharedPrefManager;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

public class Branches_Adapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    private ArrayList<Branch_Model.Branch> revies;
    private final Context mContext;
    ConnectionDetection connectionDetection;
    AppCompatActivity activity;
    String lang;
    Typeface typeface;
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public View view;
        TextView phone,address,distance,name;

        public MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;

            phone = (TextView) view.findViewById(R.id.phone);
            address=view.findViewById(R.id.address);
            name = (TextView) view.findViewById(R.id.name);
            distance=view.findViewById(R.id.distance);

            phone.setTypeface(typeface);
            address.setTypeface(typeface);

            name.setTypeface(typeface);
            distance.setTypeface(typeface);

        }
    }


    public Branches_Adapter2(Context mContext, ArrayList<Branch_Model.Branch> reviewList) {
        this.revies= reviewList;
        this.mContext=mContext;
        activity=(AppCompatActivity) mContext;
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

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.branch_item, viewGroup, false);
        return new Branches_Adapter2.MyViewHolder(view);

    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        connectionDetection=new ConnectionDetection(mContext);

        final Branch_Model.Branch rate = revies.get(position);

         if(rate.getAddress()!=null&&rate.getAddress().length()>0){
             ((MyViewHolder) holder).address.setText(rate.getAddress());
         }

         if(rate.getPhone()!=null&&rate.getPhone().length()>0){
             ((MyViewHolder) holder).phone.setText(rate.getPhone());
         }

        if(rate.getName()!=null&&rate.getName().length()>0){
            ((MyViewHolder) holder).name.setText(rate.getName());
        }

        if(rate.getDistance()!=null&&rate.getDistance().length()>0){
            ((MyViewHolder) holder).distance.setText(rate.getDistance()+" "+mContext.getResources().getString(R.string.km));
        }

        ((MyViewHolder) holder).phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPermissionGranted()) {
                    call_action(rate.getPhone());
                }
            }
        });
    }



    @Override
    public int getItemCount() {
        return revies.size();
    }

    public  boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG","Permission is granted");
                return true;
            } else {

                Log.v("TAG","Permission is revoked");

                activity.requestPermissions( new String[]{Manifest.permission.CALL_PHONE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG","Permission is granted");
            return true;
        }
    }



    void call_action(String number){
        Intent i=new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+number));
        mContext.startActivity(i);
    }
}



