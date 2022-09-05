package com.emcan.zella.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;

import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.emcan.zella.Api_Service;
import com.emcan.zella.Beans.CartResponse;
import com.emcan.zella.Beans.Cart_Response2;
import com.emcan.zella.Beans.Check_client;
import com.emcan.zella.Beans.Sub_Category;
import com.emcan.zella.Config;
import com.emcan.zella.R;
import com.emcan.zella.SharedPrefManager;
import com.emcan.zella.activities.MainActivity;
import com.emcan.zella.fragments.Dish_Fragments;
import com.emcan.zella.fragments.Slider;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;


import java.util.ArrayList;

public class Recycler_Home_Adapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    private ArrayList<Sub_Category> dishes;
    private final Context mContext;
    String lang;
    AppCompatActivity activity;

    Slider fr;
    Typeface typeface;
    private static final int NUMBERS_OF_ITEM_TO_DISPLAY = 2;



    public class MyViewHolder extends RecyclerView.ViewHolder {

        public View view;
        private final TextView name,desc,coin;
        private final ShapeableImageView image;
        ImageView add;
        RelativeLayout layout;


        public MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;

            name = (TextView) view.findViewById(R.id.name);
            desc= (TextView) view.findViewById(R.id.price);
            coin= (TextView) view.findViewById(R.id.coin);
            add=view.findViewById(R.id.add);


            layout=view.findViewById(R.id.layout);

            name.setTypeface(typeface);
            coin.setTypeface(typeface);

            image=  view.findViewById(R.id.image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final AppCompatActivity activity = (AppCompatActivity) mContext;
                    FragmentManager fragmentManager=activity.getSupportFragmentManager();
                    Fragment fragment = Dish_Fragments.newInstance(dishes.get(getLayoutPosition()), "");
                    fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack("xyz")
                            .commit();

                }
            });


        }
    }

    public int getScreenWidth() {

        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        return size.x;
    }
    public Recycler_Home_Adapter(Context mContext,ArrayList<Sub_Category> reviewList ,Slider fragment) {
        this.dishes= reviewList;
        this.mContext=mContext;
        activity=(AppCompatActivity) mContext;
        this.fr=fragment;
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

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_item, viewGroup, false);
        view.getLayoutParams().width = (getScreenWidth() / NUMBERS_OF_ITEM_TO_DISPLAY);
        return new MyViewHolder(view);

    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {


        final Sub_Category dish = dishes.get(position);
        if (dish.getSub_category_name() != null) {
            ((MyViewHolder) holder).name.setText(dish.getSub_category_name());
            ((MyViewHolder) holder).name.setTypeface(typeface);
        }
        if (dish.getSizes() != null && dish.getSizes().size()>0) {
            ( (MyViewHolder) holder).desc.setText(dish.getSizes().get(0).getSub_category_size_price()+" "+
                    mContext.getResources().getString(R.string.bhd));
            ((MyViewHolder) holder).desc.setTypeface(typeface);
        }

        if(lang.equals("ar")){
            ((MyViewHolder) holder).layout.setBackground(mContext.getResources().getDrawable(R.drawable.card_view_background_ar));
        }

        if (dish.getSub_category_image() != null) {

            Glide.with(mContext)
                    .load(dish.getSub_category_image()).centerCrop()
                    .error(R.drawable.mainicon)
                    .into(((MyViewHolder) holder).image);

        }

        if(lang.equals("en")) {
            ((MyViewHolder) holder).image.setShapeAppearanceModel(((MyViewHolder) holder).image.getShapeAppearanceModel()
                    .toBuilder()
                    .setTopLeftCorner(CornerFamily.ROUNDED, 60)
                    .build());
        }else{
            ((MyViewHolder) holder).image.setShapeAppearanceModel(((MyViewHolder) holder).image.getShapeAppearanceModel()
                    .toBuilder()
                    .setTopRightCorner(CornerFamily.ROUNDED, 60)
                    .build());
        }


        ((MyViewHolder) holder).add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                        if (SharedPrefManager.getInstance(mContext).isLoggedIn()) {

                            if(dish.getSizes()!=null&&dish.getSizes().size()>0) {
                                check_user(position);
                            }
                        } else {
                            Toast.makeText(mContext, activity.getResources().getString(R.string.signinfirst), Toast.LENGTH_SHORT).show();
                        }
            }
        });

    }



    @Override
    public int getItemCount() {
        return dishes.size();
    }

    private void check_user(int position){

        Api_Service requestInterface = Config.getClient().create(Api_Service.class);

        Call<Check_client> response1 = requestInterface.check_client(
                SharedPrefManager.getInstance(mContext).getUser().getClient_id());
        response1.enqueue(new Callback<Check_client>() {
            @Override
            public void onResponse(Call<Check_client> call, retrofit2.Response<Check_client> response) {
                Check_client resp = response.body();
                if (resp != null) {

                    if(resp.getSuccess()==1){


                        if(resp.getProduct().get(0).getExist()==0){

                            SharedPrefManager.getInstance(mContext).logout();
                            Intent intent = new Intent(mContext, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            activity. startActivity(intent);

                        }else{
                            addtocart(position);
                        }
                    }else{
//                        Toast.makeText(getContext(),"error",Toast.LENGTH_SHORT).show();
                    }
                }
            }


            @Override
            public void onFailure(Call<Check_client> call, Throwable t) {
//                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });

    }
    private void addtocart(int position){

        Api_Service requestInterface = Config.getClient().create(Api_Service.class);

//        Log.d("jjjj", price_id + "/" + addition_id+" "+ SharedPrefManager.getInstance(context).getUser().getClient_id());

        CartResponse.CartModel mode = new CartResponse.CartModel();
        mode.setSub_category_id(dishes.get(position).getSub_category_id());
        mode.setClient_id(SharedPrefManager.getInstance(mContext).getUser().getClient_id());
        mode.setQuantity("1");
        mode.setAddition_id("");
        if(dishes.get(position).getSizes().get(0).getDiscount()!=null&&
               ! dishes.get(position).getSizes().get(0).getDiscount().equals("")
        && Double.parseDouble(dishes.get(position).getSizes().get(0).getDiscount())>1) {
            mode.setSize_id(dishes.get(position).getSizes().get(0).getSub_category_size_price_after_disc());
        }else{
            mode.setSize_id(dishes.get(position).getSizes().get(0).getSub_category_size_price_id());

        }
        mode.setRemove_id("");
        mode.setSummer_meal("");
        mode.setCountry_id("");

        Call<Cart_Response2> response1 = requestInterface.addTOCart(mode);
        response1.enqueue(new Callback<Cart_Response2>() {
            @Override
            public void onResponse(Call<Cart_Response2> call, retrofit2.Response<Cart_Response2> response) {
                Cart_Response2 resp = response.body();
                if (resp != null) {
                    if (resp.getSuccess() == 1) {


                        Toast.makeText(mContext, activity.getResources().getString(R.string.addedtocart), Toast.LENGTH_SHORT).show();
                        SharedPrefManager.getInstance(mContext).add_Cart();
                        fr.updateToolbar();
//                        fragmentManager.popBackStack();
//                        Fragment fragment = new MyCart();
//                        fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack("xyz")
//                                .commit();


                    } else {
                        Toast.makeText(mContext, resp.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mContext, resp.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<Cart_Response2> call, Throwable t) {
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

}



