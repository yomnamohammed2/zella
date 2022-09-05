package com.emcan.zella.adapters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.emcan.zella.ConnectionDetection;
import com.emcan.zella.R;
import com.emcan.zella.fragments.Suggestions;

import java.util.ArrayList;

public class Images_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    private ArrayList<String> dishes;
    private final Context mContext;
    ConnectionDetection connectionDetection;
    PopupWindow popupWindow;
    AppCompatActivity activity1;
    Suggestions fragment;



    public class MyViewHolder extends RecyclerView.ViewHolder {

        public View view;
        private  final ImageView image ,close;

        public MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;

            image = (ImageView)view.findViewById(R.id.image);
            close = (ImageView)view.findViewById(R.id.close);

            activity1=(AppCompatActivity) mContext;




        }
    }


    public Images_Adapter(Context mContext, ArrayList<String> reviewList, Suggestions fragment) {
        this.dishes= reviewList;
        this.mContext=mContext;
        this.fragment=fragment;


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.images_item, viewGroup, false);
        return new Images_Adapter.MyViewHolder(view);

    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        connectionDetection = new ConnectionDetection(mContext);



                Glide.with(mContext)
                        .load(dishes.get(position))
                        .error(R.drawable.mainicon)
                        .placeholder(R.drawable.mainicon)
                        .centerCrop()
                        .into(((MyViewHolder) holder).image);


        ((MyViewHolder) holder).close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dishes.get(position).equals("")){
                    fragment.getposition(-1);
                }
                else{
                    fragment.getposition(position);
                }


            }
        });

        connectionDetection=new ConnectionDetection(mContext);


    }

    @Override
    public int getItemCount() {
        return dishes.size();
    }

}


