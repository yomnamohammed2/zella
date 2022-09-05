package com.emcan.zella.adapters;

import android.content.Context;
import android.graphics.Typeface;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.emcan.zella.Api_Service;
import com.emcan.zella.Beans.Address;
import com.emcan.zella.Beans.Address_Response;
import com.emcan.zella.Config;
import com.emcan.zella.ConnectionDetection;
import com.emcan.zella.R;
import com.emcan.zella.SharedPrefManager;
import com.emcan.zella.fragments.Map;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class Address_adapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


        private ArrayList<Address> Addresss;
        private ArrayList<Integer> check;
        private final Context mContext;
        public ListAllListeners listAllListeners;
        ConnectionDetection connectionDetection;
        PopupWindow popupWindow;
        String region;
        View view;
   AppCompatActivity activity;
   String flag=null;
   String lang;
   Typeface typeface;



        public class MyViewHolder extends RecyclerView.ViewHolder {
            private final TextView address;
            private final ImageView delete,edit;
            private final CheckBox cb;



            public MyViewHolder(View itemView) {
                super(itemView);
                view = itemView;

                address = (TextView) view.findViewById(R.id.address);

                edit= (ImageView) view.findViewById(R.id.edit);
                delete= (ImageView) view.findViewById(R.id.delete);
                cb=(CheckBox) view.findViewById(R.id.cb);


            }
        }


        public Address_adapter(Context mContext, ArrayList<Address> reviewList, ArrayList<Integer> check, String flag, ListAllListeners listAllListeners) {
            this.Addresss= reviewList;
            this.listAllListeners=listAllListeners;
            this.mContext=mContext;
            this.check=check;
            activity = (AppCompatActivity) mContext;
            this. flag=flag;
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

            View view = LayoutInflater.from(mContext).inflate(R.layout.address_recycler_item, viewGroup, false);
            return new Address_adapter.MyViewHolder(view);

        }


        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
            connectionDetection = new ConnectionDetection(mContext);

            final Address address = Addresss.get(position);

            if (address.getRegion_name() != null&&!address.getRegion_name().equals("")) {
             region=address.getRegion_name();
            }

            if (address.getBlock() != null&&!address.getBlock().equals("")) {
                region=region+","+address.getBlock();
            }
            if (address.getRoad() != null&&!address.getRoad().equals("")) {
                region=region+","+address.getRoad();
            }


            if (address.getBuilding() != null&&!address.getBuilding().equals("")) {
                region=region+","+address.getBuilding();
            }

            if (address.getFlat_number() != null&&!address.getFlat_number().equals("")) {
                region=region+","+address.getFlat_number();
            }

            ((MyViewHolder) holder).address.setText(region);
            ((MyViewHolder) holder).address.setTypeface(typeface);
            //-------------------check box-------------//


            ((MyViewHolder) holder).cb .setOnCheckedChangeListener(null);
            if(check.get(position)==1){
                ((MyViewHolder) holder).cb.setChecked(true);
            }else{
                ((MyViewHolder) holder).cb.setChecked(false);
            }

            ((MyViewHolder) holder).cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                      //  Addresss.get(holder.getAdapterPosition()).setChecked(true);
                        listAllListeners.onItemCheck(address.getClient_address_id(), position);
                        for(int i=0;i<check.size();i++){
                            check.set(i,0);
                        }
                        check.set(position,1);
                        notifyDataSetChanged();
                    }
                    else {
                      //  Addresss.get(holder.getAdapterPosition()).setChecked(false);
                        listAllListeners.onItemUncheck(null, position);
                    }
                }
            });


            ((MyViewHolder) holder).edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                    Log.d("ppppppppppp",address.getLat()+"//////");
//                    Fragment fragment=new Map();
//                    Bundle bundle=new Bundle();
//                    bundle.putString("lat",address.getLat());
//                    bundle.putString("long",address.getLang());
//                    bundle.putString("id",address.getClient_address_id());
//                    bundle.putString("flag",flag);
//                    fragment.setArguments(bundle);
//                    FragmentManager fragmentManager=activity.getSupportFragmentManager();
//                    fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack("xyz")
//                            .commit();
                }
            });

            if(flag==null){
                ((MyViewHolder) holder).edit.setVisibility(View.INVISIBLE);
                ((MyViewHolder) holder).delete.setVisibility(View.INVISIBLE);
            }

            ((MyViewHolder) holder).delete.setOnClickListener(new View.OnClickListener() {
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

                    text.setText(mContext.getResources().getString(R.string.deleteadd));
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
                            Addresss.remove(position);
                            notifyDataSetChanged();
                            if (connectionDetection.isConnected()) {

                                Api_Service requestInterface = Config.getClient().create(Api_Service.class);
                                Call<Address_Response> response1 = requestInterface.deleteAddress(SharedPrefManager.getInstance(mContext).getUser().getClient_id(),
                                        address.getClient_address_id());
                                response1.enqueue(new Callback<Address_Response>() {
                                    @Override
                                    public void onResponse(Call<Address_Response> call, retrofit2.Response<Address_Response> response) {
                                        Address_Response resp = response.body();
                                        if (resp != null) {

                                            Toast.makeText(mContext, mContext.getResources().getString(R.string.deletedadd), Toast.LENGTH_SHORT).show();


                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Address_Response> call, Throwable t) {
                                        // Toast.makeText(mContext, "خطأ حاول مجددا", Toast.LENGTH_SHORT).show();
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

    public interface ListAllListeners {

        void onItemCheck(String checkBoxName, int position);

        void onItemUncheck(String checkBoxName, int position);
    }

        @Override
        public int getItemCount() {
            return Addresss.size();
        }

    }


