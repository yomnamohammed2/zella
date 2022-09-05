package com.emcan.zella.adapters;

import android.content.Context;
import android.graphics.Typeface;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
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

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class Fav_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


        private ArrayList<Sub_Category> dishes;
        private final Context mContext;
        ConnectionDetection connectionDetection;
        PopupWindow popupWindow;
        AppCompatActivity activity1;
        String lang;
        Typeface typeface;


        public class MyViewHolder extends RecyclerView.ViewHolder {

            public View view;
            private final TextView dish_name,dish_details;
            private final ImageView dish_image,love;


            public MyViewHolder(View itemView) {
                super(itemView);
                view = itemView;

                dish_name = (TextView) view.findViewById(R.id.dish_name);
                dish_details= (TextView) view.findViewById(R.id.dish_details);

                dish_image= (ImageView) view.findViewById(R.id.dish_image);
                love= (ImageView) view.findViewById(R.id.love);


                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                      Sub_Category dish=new Sub_Category();
                      dish.setSub_category_desc(dishes.get(getLayoutPosition()).getSub_category_desc());
                      dish.setSub_category_id(dishes.get(getLayoutPosition()).getSub_category_id());
                      dish.setSub_category_name(dishes.get(getLayoutPosition()).getSub_category_name());
                      dish.setSub_category_image(dishes.get(getLayoutPosition()).getSub_category_image());
                      dish.setSpicy_show(dishes.get(getLayoutPosition()).getSpicy_show());
                      dish.setType(dishes.get(getLayoutPosition()).getType());


                        final AppCompatActivity activity = (AppCompatActivity) mContext;
                        FragmentManager fragmentManager=activity.getSupportFragmentManager();
                        Fragment fragment= Dish_Fragments.newInstance(dishes.get(getLayoutPosition()),mContext.getResources().getString(R.string.app_nam));
                        fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack("xyz")
                                .commit();



                    }
                });

            }
        }


        public Fav_adapter(Context mContext,ArrayList<Sub_Category> reviewList) {
            this.dishes= reviewList;
            this.mContext=mContext;
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

            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fav_recycler_item, viewGroup, false);
            return new Fav_adapter.MyViewHolder(view);

        }


        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
            connectionDetection = new ConnectionDetection(mContext);

            final Sub_Category dish = dishes.get(position);

           activity1=(AppCompatActivity)mContext;
            if (dish.getSub_category_name() != null) {
                ((MyViewHolder) holder).dish_name.setText(dish.getSub_category_name());
            }
            if (dish.getSub_category_desc() != null) {
                ((MyViewHolder) holder).dish_details.setText(dish.getSub_category_desc());
            }
            if (dish.getSub_category_image()!= null) {

                Glide.with(mContext)
                        .load(dish.getSub_category_image())
                        .centerCrop()
                        .error(R.drawable.mainicon)
                        .placeholder(R.drawable.mainicon)
                        .into(((MyViewHolder) holder).dish_image);

            }

                    ((MyViewHolder) holder).love.setImageResource(R.drawable.favo);
            ((MyViewHolder) holder).dish_details.setTypeface(typeface);
            ((MyViewHolder) holder).dish_name.setTypeface(typeface);

            ((MyViewHolder) holder).love.setOnClickListener(new View.OnClickListener() {
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
                    Button no=customView.findViewById(R.id.no);
                    Button yes=customView.findViewById(R.id.yes);
                    TextView text=customView.findViewById(R.id.text);

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

                            dishes.remove(position);
                            popupWindow.dismiss();
                            notifyDataSetChanged();
                            if (connectionDetection.isConnected()) {
                                Api_Service requestInterface = Config.getClient().create(Api_Service.class);
                                Call<fav_Response> response1 = requestInterface.deleteFromFavo(SharedPrefManager.getInstance(mContext).getUser().getClient_id(), dish.getSub_category_id());
                                response1.enqueue(new Callback<fav_Response>() {
                                    @Override
                                    public void onResponse(Call<fav_Response> call, retrofit2.Response<fav_Response> response) {
                                        fav_Response resp = response.body();
                                        if (resp != null) {
                                            if (resp.getSuccess() == 1) {
                                                Toast.makeText(mContext, mContext.getResources().getString(R.string.deletedfromfav), Toast.LENGTH_SHORT).show();

//
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
                    RelativeLayout relativeLayout= (RelativeLayout) ((AppCompatActivity) mContext).findViewById(R.id.view_fav);

                    popupWindow.showAtLocation(relativeLayout, Gravity.CENTER, 0, 0);



                }
            });

        }

        @Override
        public int getItemCount() {
            return dishes.size();
        }

    }



