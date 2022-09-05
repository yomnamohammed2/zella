package com.emcan.zella.adapters;

import android.content.Context;
import android.graphics.Typeface;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;


import com.emcan.zella.Beans.Complain_response;
import com.emcan.zella.ConnectionDetection;
import com.emcan.zella.R;
import com.emcan.zella.SharedPrefManager;
import com.emcan.zella.fragments.Complains_Detaills;

import java.util.ArrayList;

public class Complains_adapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    private ArrayList<Complain_response.Complain> revies;
    private final Context mContext;
    ConnectionDetection connectionDetection;
    FragmentManager fragmentManager;
    String lang;
    Typeface typeface;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public View view;
        TextView text,date;



        public MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;

            text = (TextView) view.findViewById(R.id.title);
            date=  view.findViewById(R.id.date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  Fragment fragment = Complains_Detaills.newInstance(revies.get(getLayoutPosition()));

                    fragmentManager.beginTransaction()
                            .replace(R.id.container, fragment).addToBackStack(null)
                            .commit();
                }
            });

        }
    }


    public Complains_adapter (Context mContext, ArrayList<Complain_response.Complain> reviewList) {
        this.revies= reviewList;
        this.mContext=mContext;
        AppCompatActivity activity=(AppCompatActivity)mContext;
        fragmentManager=activity.getSupportFragmentManager();
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

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.complain_item, viewGroup, false);
        return new Complains_adapter.MyViewHolder(view);

    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        setFadeAnimation(holder.itemView);

        connectionDetection=new ConnectionDetection(mContext);

        final Complain_response.Complain rate = revies.get(position);
        if (rate.getTitle()!= null) {
            ((MyViewHolder) holder).text.setText(rate.getTitle());

        }

        if (rate.getDate()!= null) {
            ((MyViewHolder) holder).date.setText(rate.getDate());

        }

        ((MyViewHolder) holder).text.setTypeface(typeface);
        ((MyViewHolder) holder).date.setTypeface(typeface);


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




