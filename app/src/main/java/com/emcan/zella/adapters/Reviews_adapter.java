package com.emcan.zella.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.emcan.zella.Api_Service;
import com.emcan.zella.Beans.Review;
import com.emcan.zella.Beans.Sub_Cat_Images_Model;
import com.emcan.zella.Config;
import com.emcan.zella.ConnectionDetection;
import com.emcan.zella.R;
import com.emcan.zella.SharedPrefManager;
import com.emcan.zella.fragments.Add_Rate;
import com.emcan.zella.fragments.Reviews;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class Reviews_adapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    private ArrayList<Review> revies;
    private final Context mContext;
    ConnectionDetection connectionDetection;

    PopupWindow popupWindow;
    View view1;
    Reviews reviews;
    String lang;
    Typeface typeface;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public View view;
        private final TextView username,usercomment;
        private final RatingBar userrate;
        private final  ImageView edit,delete;
        private final LinearLayout editt;



        public MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;

            username = (TextView) view.findViewById(R.id.username);
            usercomment= (TextView) view.findViewById(R.id.comment);

            edit=view.findViewById(R.id.edit);
            delete=view.findViewById(R.id.delete);
            editt=view.findViewById(R.id.editt);


            userrate= (RatingBar) view.findViewById(R.id.rate);
            LayerDrawable stars1 = (LayerDrawable) userrate.getProgressDrawable();
            stars1.setColorFilter(Color.parseColor("#ffc501"), PorterDuff.Mode.SRC_ATOP);

        }
    }


    public Reviews_adapter (Context mContext, ArrayList<Review> reviewList, View view, Reviews reviews) {
        this.revies= reviewList;
        this.mContext=mContext;
        this.view1=view;
        this.reviews=reviews;
        lang= SharedPrefManager.getInstance(mContext).getLang();
        if(lang.equals("en")){
            typeface = ResourcesCompat.getFont(mContext, R.font.amaranth_bold);
        }
        if(lang.equals("ar")){
            typeface= ResourcesCompat.getFont(mContext, R.font.bein_black);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.review_recycler_item, viewGroup, false);
        return new Reviews_adapter.MyViewHolder(view);

    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {


connectionDetection=new ConnectionDetection(mContext);

        final Review rate = revies.get(position);
        if (rate.getClient_name()!= null) {
            ((MyViewHolder) holder).username.setText(rate.getClient_name());
            ((MyViewHolder) holder).username.setTypeface(typeface);
        }
        if (rate.getComment() != null) {
            ( (MyViewHolder) holder).usercomment.setText(rate.getComment());
            ((MyViewHolder) holder).usercomment.setTypeface(typeface);
        }

        if (rate.getRate() != 0) {
            ( (MyViewHolder) holder) .userrate.setRating(rate.getRate());

        }

        Log.d("clienttttttt",rate.getClient_id()+"   "+SharedPrefManager.getInstance(mContext).getUser().getClient_id());

        if (rate.getClient_id()
                .equals(SharedPrefManager.getInstance(mContext).getUser().getClient_id())) {
            ( (MyViewHolder) holder) .editt.setVisibility(View.VISIBLE);

        }
        else{
            ( (MyViewHolder) holder) .editt.setVisibility(View.GONE);
        }

        ( (MyViewHolder) holder). edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyDataSetChanged();
                final AppCompatActivity activity = (AppCompatActivity) mContext;
                FragmentManager fragmentManager=activity.getSupportFragmentManager();
                Fragment fragment=new Add_Rate();
                Bundle bundle = new Bundle();
                bundle.putParcelable("rate",rate);
                fragment.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack("xyz")
                        .commit();

            }
        });

        ( (MyViewHolder) holder).delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
                View customView = inflater.inflate(R.layout.dialoge_alert, null);
                popupWindow = new PopupWindow(
                        customView,
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        //   700,
                        LinearLayout.LayoutParams.MATCH_PARENT, true
                );

                RelativeLayout out=customView.findViewById(R.id.out);
                out.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
                Button no = customView.findViewById(R.id.no);
                Button yes = customView.findViewById(R.id.yes);
                TextView text = customView.findViewById(R.id.text);

                text.setText(mContext.getResources().getString(R.string.deletecomment));
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();


                        if (connectionDetection.isConnected()) {
                            Api_Service requestInterface = Config.getClient().create(Api_Service.class);
                            Call<Sub_Cat_Images_Model> response1 = requestInterface.delte_Comment(rate.getComment_id());
                            response1.enqueue(new Callback<Sub_Cat_Images_Model>() {
                                @Override
                                public void onResponse(Call<Sub_Cat_Images_Model> call, retrofit2.Response<Sub_Cat_Images_Model> response) {
                                    Sub_Cat_Images_Model resp = response.body();
                                    if (resp != null) {
                                        if (resp.getSuccess() == 1) {
                                            Toast.makeText(mContext, mContext.getResources().getString(R.string.commdeleted), Toast.LENGTH_SHORT).show();

                                            reviews.get_Reviews();
                                        }
                                    } else {
                                        Toast.makeText(mContext, mContext.getResources().getString(R.string.commdeleted), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Sub_Cat_Images_Model> call, Throwable t) {
                                    Toast.makeText(mContext, mContext.getResources().getString(R.string.commdeleted), Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else {

                            Toast.makeText(mContext, mContext.getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
            }
            });
            }




    @Override
    public int getItemCount() {
        return revies.size();
    }

}



