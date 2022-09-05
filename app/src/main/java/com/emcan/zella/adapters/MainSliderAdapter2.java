package com.emcan.zella.adapters;

import android.content.Context;
import android.graphics.Outline;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.emcan.zella.Beans.Image_Model;
import com.emcan.zella.Beans.Sub_Category;
import com.emcan.zella.R;
import com.emcan.zella.fragments.Dish_Fragments;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainSliderAdapter2 extends SliderViewAdapter<MainSliderAdapter2.MyViewHolder> {

    private Context context;
    private List<Image_Model> ads;

    public MainSliderAdapter2(Context context, List<Image_Model> adList) {
        this.context = context;
        this.ads = adList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.ad_item, null);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, final int position) {
        if (ads != null && ads.size() > 0) {
//            Picasso.get().
//                    load(itemAd.getImage()).
////                    placeholder(context.getResources().getDrawable(R.drawable.ic_logo)).
////                    error(context.getResources().getDrawable(R.drawable.ic_logo)).
//                    into(viewHolder.sliderImgView);


            if(ads.get(position).getImage()!=null&&ads.get(position).getImage().length()>0){
                Glide.with(context).load(ads.get(position).getImage()).into(viewHolder.sliderImgView);
            }


            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                ViewOutlineProvider provider = new ViewOutlineProvider() {
                    @Override
                    public void getOutline(View view, Outline outline) {
                        Float corner = 200f;

                        outline.setRoundRect(0, -corner.intValue(), view.getWidth(), view.getHeight(),corner);
                    }
                };

                viewHolder.sliderImgView .setOutlineProvider(provider);
                viewHolder.sliderImgView.setClipToOutline(true);
            }
        }
    }

    @Override
    public int getCount() {
        return ads != null ? ads.size() : 0;
    }

    public class MyViewHolder extends SliderViewAdapter.ViewHolder{

        ImageView sliderImgView, adItemPlaceholderImgView;

        public MyViewHolder(View itemView) {
            super(itemView);
            sliderImgView = itemView.findViewById(R.id.imgview_slider_item);
            adItemPlaceholderImgView = itemView.findViewById(R.id.imgview_ad_item_placeholder);


        }
    }
}
