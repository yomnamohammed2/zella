package com.emcan.zella.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.emcan.zella.Beans.Sub_Category;
import com.emcan.zella.R;
import com.emcan.zella.fragments.Dish_Fragments;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class MainSliderAdapter extends SliderViewAdapter<MainSliderAdapter.MyViewHolder> {

    private Context context;
    private List<Sub_Category> ads;

    public MainSliderAdapter(Context context, List<Sub_Category> adList) {
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
            Sub_Category itemAd = ads.get(position);
//            Picasso.get().
//                    load(itemAd.getImage()).
////                    placeholder(context.getResources().getDrawable(R.drawable.ic_logo)).
////                    error(context.getResources().getDrawable(R.drawable.ic_logo)).
//                    into(viewHolder.sliderImgView);


            if(ads.get(position).getSlider_image()!=null&&ads.get(position).getSlider_image().length()>0){
                Glide.with(context).load(ads.get(position).getSlider_image()).into(viewHolder.sliderImgView);
            }

            viewHolder.sliderImgView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(ads.get(position).getSlider_type().equals("1")) {
                        final AppCompatActivity activity = (AppCompatActivity) context;
                        FragmentManager fragmentManager = activity.getSupportFragmentManager();
                        Fragment fragment = Dish_Fragments.newInstance(ads.get(position), ads.get(position).getSub_category_name());
                        fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack("xyz")
                                .commit();
                    }
                }
            });


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
