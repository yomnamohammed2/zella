package com.emcan.zella.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.emcan.zella.Beans.Parent_Category;
import com.emcan.zella.ConnectionDetection;
import com.emcan.zella.R;
import com.emcan.zella.SharedPrefManager;
import com.emcan.zella.fragments.Meat_Chicken;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

public class Sections_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


        private ArrayList<Parent_Category> dishes;
        private final Context mContext;
        ConnectionDetection connectionDetection;
        String parent_name;
        String love_status;
        PopupWindow popupWindow;
        String lang,type;
        Typeface typeface;

        public class MyViewHolder extends RecyclerView.ViewHolder {

            public View view;
            private final TextView dish_name;
            private final ImageView dish_image;


            public MyViewHolder(View itemView) {
                super(itemView);
                view = itemView;

                dish_name = (TextView) view.findViewById(R.id.name);

                dish_image= (ImageView) view.findViewById(R.id.img_section);
                dish_name.setTypeface(typeface);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                           final AppCompatActivity activity = (AppCompatActivity) mContext;
                           FragmentManager fragmentManager = activity.getSupportFragmentManager();
                        Fragment fragment = new Meat_Chicken();

                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList("cat", dishes);
                        bundle.putInt("pos", getLayoutPosition());
                        fragment.setArguments(bundle);
                        fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack("xyz")
                                .commit();


                    }
                });

            }
        }


        public Sections_Adapter(Context mContext, ArrayList<Parent_Category> reviewList) {
            this.dishes= reviewList;
            this.mContext=mContext;
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

            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.section_layout, viewGroup, false);
            return new Sections_Adapter.MyViewHolder(view);

        }


        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
            connectionDetection = new ConnectionDetection(mContext);

            setFadeAnimation(holder.itemView);

            final Parent_Category dish = dishes.get(position);


            if (dish.getParent_categorey_name() != null) {
                ((MyViewHolder) holder).dish_name.setText(dish.getParent_categorey_name());
            }

            if (dish.getParent_categorey_image()!= null&&dish.getParent_categorey_image().length()>0) {
                Glide.with(mContext)
                        .load(dish.getParent_categorey_image()).error(R.drawable.mainicon).into(((MyViewHolder) holder).dish_image);
            }
            else{
                Glide.with(mContext)
                        .load(R.drawable.mainicon).into(((MyViewHolder) holder).dish_image);
            }


        }

        @Override
        public int getItemCount() {
            return dishes.size();
        }

    public void setFadeAnimation(View view) {
        android.view.animation.AlphaAnimation anim = new android.view.animation.AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(500);
        view.startAnimation(anim);
    }

}


