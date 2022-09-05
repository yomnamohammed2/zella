package com.emcan.zella.adapters;

import android.content.Context;
import android.graphics.Typeface;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.emcan.zella.Api_Service;
import com.emcan.zella.Beans.Sub_Category;
import com.emcan.zella.Beans.fav_Response;
import com.emcan.zella.Config;
import com.emcan.zella.ConnectionDetection;
import com.emcan.zella.R;
import com.emcan.zella.SharedPrefManager;
import com.emcan.zella.fragments.Dish_Fragments;
import com.emcan.zella.fragments.Meat_Chicken;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class Recycler_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


        private ArrayList<Sub_Category> dishes;
        private final Context mContext;
        ConnectionDetection connectionDetection;
        String parent_name;
        String love_status;
        PopupWindow popupWindow;
        String lang;
        Typeface typeface;

        public class MyViewHolder extends RecyclerView.ViewHolder {

            public View view;
            private final TextView dish_name,dish_details;
            private final ImageView dish_image,love;
            TextView price;


            public MyViewHolder(View itemView) {
                super(itemView);
                view = itemView;

                dish_name = (TextView) view.findViewById(R.id.dish_name);
                dish_details= (TextView) view.findViewById(R.id.dish_details);

                dish_image= (ImageView) view.findViewById(R.id.dish_image);
                love= (ImageView) view.findViewById(R.id.love);
                price=view.findViewById(R.id.price);
                price.setTypeface(typeface);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       /* if(dishes.get(getLayoutPosition()).getSub_category_id().equals("108")){
                            final AppCompatActivity activity = (AppCompatActivity) mContext;
                            FragmentManager fragmentManager = activity.getSupportFragmentManager();
                            Fragment fragment = Summer_Meal.newInstance(dishes.get(getLayoutPosition()), parent_name);
                            fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack("xyz")
                                    .commit();
                        }else {*/
//                       if(dishes.get(getLayoutPosition()).getType().equals("1")){
                           final AppCompatActivity activity = (AppCompatActivity) mContext;
                           FragmentManager fragmentManager = activity.getSupportFragmentManager();
                           Fragment fragment = Dish_Fragments.newInstance(dishes.get(getLayoutPosition()), parent_name);
                           fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack("xyz")
                                   .commit();
//                       }
//                       else{
//                           final AppCompatActivity activity = (AppCompatActivity) mContext;
//                           FragmentManager fragmentManager=activity.getSupportFragmentManager();
//                           Fragment fragment=new Meat_Chicken();
//                           Bundle bundle = new Bundle();
//                           bundle.putParcelableArrayList("cat", dishes);
//                           Log.d("dishesss",dishes.size()+"");
//                           bundle.putInt("pos",getLayoutPosition());
//                           bundle.putInt("flag",1);
//                           fragment.setArguments(bundle);
//                           fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack("xyz")
//                                   .commit();
//                       }

                      //  }
                    }
                });

            }
        }


        public Recycler_adapter(Context mContext,ArrayList<Sub_Category> reviewList,String parent) {
            this.dishes= reviewList;
            this.mContext=mContext;
            this.parent_name=parent;
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

            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item, viewGroup, false);
            return new Recycler_adapter.MyViewHolder(view);

        }


        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
            connectionDetection = new ConnectionDetection(mContext);

            setFadeAnimation(holder.itemView);

            final Sub_Category dish = dishes.get(position);
            love_status=dish.getSub_category_fav();


            if (dish.getSub_category_name() != null) {
                ((MyViewHolder) holder).dish_name.setText(dish.getSub_category_name());
                ((MyViewHolder) holder).dish_name.setTypeface(typeface);
            }
//            if (dish.getSub_category_desc() != null) {
//                ((MyViewHolder) holder).dish_details.setText(dish.getSub_category_desc());
//                ((MyViewHolder) holder).dish_details.setTypeface(typeface);
//            }

            if (dish.getSub_category_image_name()!= null&&dish.getSub_category_image_name().length()>0) {
                Glide.with(mContext)
                        .load(dish.getSub_category_image_name()).error(R.drawable.mainicon).into(((MyViewHolder) holder).dish_image);
            }
            else{
                Glide.with(mContext)
                        .load(R.drawable.mainicon).into(((MyViewHolder) holder).dish_image);
            }
            if(dish.getSizes()!=null&&dish.getSizes().size()>0){

                        ((MyViewHolder) holder).price.setText(dish.getSizes().get(0).getSub_category_size_price()+" "+
                                mContext.getResources().getString(R.string.bhd));


            }



            if(dish.getSub_category_fav()!=null) {
                if (dish.getSub_category_fav().equals("0")) {
                    ((MyViewHolder) holder).love.setImageResource(R.drawable.heart);
                    } else if (dish.getSub_category_fav().equals("1")) {
                    ((MyViewHolder) holder).love.setImageResource(R.drawable.favo);
                    }
                      }
            ((MyViewHolder) holder).love.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (SharedPrefManager.getInstance(mContext).isLoggedIn()) {
                     if (dish.getSub_category_fav() != null) {
                      if (dish.getSub_category_fav().equals("0")) {
                      dish.setSub_category_fav("1");

                    ((MyViewHolder) holder).love.setImageResource(R.drawable.favo);
                      if (connectionDetection.isConnected()) {
                        Api_Service requestInterface = Config.getClient().create(Api_Service.class);
                         Call<fav_Response> response1 = requestInterface.addToFavo(SharedPrefManager.getInstance(mContext).
                                 getUser().getClient_id(), dish.getSub_category_id(),lang);
                           response1.enqueue(new Callback<fav_Response>() {
                             @Override
                             public void onResponse(Call<fav_Response> call, retrofit2.Response<fav_Response> response) {
                              fav_Response resp = response.body();
                              if (resp != null) {
                                 if (resp.getSuccess() == 1) {
                                     Toast.makeText(mContext, resp.getMessage(), Toast.LENGTH_SHORT).show();
                                     }else{
                                     Toast.makeText(mContext, resp.getMessage(), Toast.LENGTH_SHORT).show();
                                 }
                            } else {
                   Toast.makeText(mContext, mContext.getResources().getString(R.string.errortryagain), Toast.LENGTH_SHORT).show();
                                  }
                             }
                      @Override
                     public void onFailure(Call<fav_Response> call, Throwable t) {
                     Toast.makeText(mContext, mContext.getResources().getString(R.string.errortryagain), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } else {

                                    Toast.makeText(mContext, mContext.getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
                                }
                            } else if (dish.getSub_category_fav().equals("1")) {

                                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
                                View customView = inflater.inflate(R.layout.dialoge_alert, null);
                                popupWindow = new PopupWindow(
                                        customView,
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        //   700,
                                        LinearLayout.LayoutParams.MATCH_PARENT, true
                                );

                                Button no = customView.findViewById(R.id.no);
                                Button yes = customView.findViewById(R.id.yes);
                                TextView text = customView.findViewById(R.id.text);

                                text.setText(mContext.getResources().getString(R.string.deletefromfav));
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
                     dish.setSub_category_fav("0");
                 ((MyViewHolder) holder).love.setImageResource(R.drawable.heart);
                      popupWindow.dismiss();
           if (connectionDetection.isConnected()) {
                 Api_Service requestInterface = Config.getClient().create(Api_Service.class);
                       Call<fav_Response> response1 = requestInterface.deleteFromFavo(SharedPrefManager.getInstance(mContext).
                               getUser().getClient_id(), dish.getSub_category_id());
                        response1.enqueue(new Callback<fav_Response>() {
                               @Override
                        public void onResponse(Call<fav_Response> call, retrofit2.Response<fav_Response> response) {
                          fav_Response resp = response.body();
                          if (resp != null) {
                           if (resp.getSuccess() == 1) {
                              Toast.makeText(mContext, mContext.getResources().getString(R.string.deletedfromfav), Toast.LENGTH_SHORT).show();
                                                        }
                            } else {
                         Toast.makeText(mContext, mContext.getResources().getString(R.string.deletedfromfav), Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                           @Override
                         public void onFailure(Call<fav_Response> call, Throwable t) {
                         Toast.makeText(mContext, mContext.getResources().getString(R.string.deletedfromfav), Toast.LENGTH_SHORT).show();
                                                }

                                            });
                                     } else {
               Toast.makeText(mContext, mContext.getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();

                                        }
                                    }

                                });
                                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);


                            }
                        }

                    } else {
                        Toast.makeText(mContext, mContext.getResources().getString(R.string.signfirst), Toast.LENGTH_SHORT).show();

                    }
                }

            });

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


