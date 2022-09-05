package com.emcan.zella.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.bumptech.glide.Glide;
import com.emcan.zella.Beans.PopupModel;
import com.emcan.zella.R;
import com.emcan.zella.fragments.Dish_Fragments;
import com.smarteist.autoimageslider.SliderView;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class SliderAdapterExample2 extends
        SliderViewAdapter<SliderAdapterExample2.SliderAdapterjj> {

    private Context context;
    FragmentManager fragmentManager;
    AppCompatActivity activity;
    private ArrayList<PopupModel> mSliderItems = new ArrayList<>();
    PopupWindow popupWindow;

    public SliderAdapterExample2(Context context, ArrayList<PopupModel> images,PopupWindow popupWindow) {
        this.context = context;
        this.mSliderItems = images;
        activity=(AppCompatActivity) context;
        fragmentManager=activity.getSupportFragmentManager();
        this.popupWindow=popupWindow;
        notifyDataSetChanged();
    }

    @Override
    public SliderAdapterjj onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.popitem, null);
        return new SliderAdapterjj(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterjj viewHolder, final int position) {

        final PopupModel sliderItem = mSliderItems.get(position);

        Glide.with(context)
                .load(sliderItem.getImage()).centerCrop().
                        error(context.getResources().getDrawable(R.drawable.logo))

                .into(viewHolder.imageViewBackground);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sliderItem.getType().equals("1")) {

                    sliderItem.getItem_details().setSub_category_image(sliderItem.getSub_category_image());

                    Fragment fragment = Dish_Fragments.newInstance(sliderItem.getItem_details(),"");
                    fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack("xyz")
                            .commit();

                    if(popupWindow!=null)
                    {
                        popupWindow.dismiss();
                    }

                } else {
                    if (sliderItem.getType().equals("2")) {

//
                    }
                }
            }
        });



    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return mSliderItems.size();
    }


    public class SliderAdapterjj extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;

        public SliderAdapterjj(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.image);

            this.itemView = itemView;
        }
    }

}