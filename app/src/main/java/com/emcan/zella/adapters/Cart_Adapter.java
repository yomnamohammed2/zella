package com.emcan.zella.adapters;

import android.content.Context;
import android.graphics.Typeface;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.emcan.zella.Beans.Cart_Response2;
import com.emcan.zella.Beans.Check;
import com.emcan.zella.Config;
import com.emcan.zella.ConnectionDetection;
import com.emcan.zella.R;
import com.emcan.zella.SharedPrefManager;
import com.emcan.zella.fragments.MyCart;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class Cart_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    ConnectionDetection connectionDetection;
    ArrayList<Cart_Response2.CartModel2> dishes;
    Context mContext;
    TextView title;
    ImageView cart;
     int quantity ;
     PopupWindow popupWindow;
     View view1;
     String addition_,without;

     Double d;
     String flag;
    String lang;
    Typeface typeface;



    public class MyViewHolder extends RecyclerView.ViewHolder {

        public View view;
        private final TextView dish_name, price, count;
        private final ImageView dish_image, delete;
        private final TextView addition,addition1,quantity,unit,cart_price;
        private final ImageView plus,minus;
        private final LinearLayout addition_rel,without_rel;
        private final TextView without,without1;
        LinearLayout total_rel;

        RelativeLayout quan_rel;
        TextView txt_quantity;
        public MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;

//            rel1=view.findViewById(R.id.rel1);

            txt_quantity=view.findViewById(R.id.quantity);

            dish_name = (TextView) view.findViewById(R.id.dish_name);
            price = (TextView) view.findViewById(R.id.price);
            count = (TextView) view.findViewById(R.id.count);
            delete = (ImageView) view.findViewById(R.id.delete);
            dish_image = (ImageView) view.findViewById(R.id.dish_image);
            unit =  view.findViewById(R.id.unit);
            cart_price =  view.findViewById(R.id.cart_price);


            total_rel =  view.findViewById(R.id.total);

            addition_rel =  view.findViewById(R.id.addition_rel);
            without_rel =  view.findViewById(R.id.without_rel);

            plus =  view.findViewById(R.id.plus);
            minus =  view.findViewById(R.id.minus);

            without =  view.findViewById(R.id.without);
            without1 =  view.findViewById(R.id.without1);

            addition=view.findViewById(R.id.additions);
            addition1=view.findViewById(R.id.addition1);

            quantity=view.findViewById(R.id.quantity);
//            quantity_txt=view.findViewById(R.id.quantity_txt);
//            quantity_txt.setTypeface(typeface);
            quantity.setTypeface(typeface);

            quan_rel=view.findViewById(R.id.quan_rel);

        }
    }


    public Cart_Adapter(Context mContext, ArrayList<Cart_Response2.CartModel2> reviewList, View view, String flag) {
        this.dishes = reviewList;
        this.mContext = mContext;
        this.view1=view;
        this.flag=flag;
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

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_recyler_item, viewGroup, false);
        return new Cart_Adapter.MyViewHolder(view);

    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        connectionDetection = new ConnectionDetection(mContext);

        final Cart_Response2.CartModel2 dish = dishes.get(position);
if(dish.getQuantity()!=null&&dish.getQuantity()!="") {
    quantity = Integer.parseInt(dish.getQuantity());
    ((MyViewHolder) holder).quantity.setText(String.valueOf(quantity));
//    ((MyViewHolder) holder).quantity_txt .setText(String.valueOf(quantity));
    ((MyViewHolder) holder).quantity.setTypeface(typeface);
//    ((MyViewHolder) holder).quantity_txt.setTypeface(typeface);

}

if(dish.getPrice()!=null){
        ((MyViewHolder) holder).price.setText(dish.getPrice()+" "+mContext.getResources().getString(R.string.bhd));
    ((MyViewHolder) holder).cart_price.setText(dish.getPrice()+" "+mContext.getResources().getString(R.string.bhd));

    ((MyViewHolder) holder).price.setTypeface(typeface);
    ((MyViewHolder) holder).cart_price.setTypeface(typeface);
}

        if(dish.getSize_price()!=null){
                ((MyViewHolder) holder).unit.setText(dish.getSize_price()+" "+mContext.getResources().getString(R.string.bhd));
            ((MyViewHolder) holder).unit.setTypeface(typeface);
}
        ((MyViewHolder) holder).addition_rel.setVisibility(View.VISIBLE);
        ((MyViewHolder) holder).without_rel.setVisibility(View.VISIBLE);

if(dish.getAddition().size()>0)
{
    ((MyViewHolder) holder).addition_rel.setVisibility(View.VISIBLE);


    if (dish.getAddition().size() > 0) {
        for (int i = 0; i < dish.getAddition().size(); i++) {
            if (i == 0) {
                addition_ = dish.getAddition().get(0).getAddition_name();
            } else {
                addition_ = addition_ + ","+" " + dish.getAddition().get(i).getAddition_name();
            }
        }
    }

    ((MyViewHolder) holder).addition1.setText(addition_);
}else{
    ((MyViewHolder) holder).addition_rel.setVisibility(View.GONE);

}


        if(dish.getRemove().size()>0)
        {
            ((MyViewHolder) holder).without_rel.setVisibility(View.VISIBLE);

            if (dish.getRemove().size() > 0) {
                for (int i = 0; i < dish.getRemove().size(); i++) {
                    if (i == 0) {
                        without = dish.getRemove().get(0).getRemove_name();
                    } else {
                        without = without + " ," +" " + dish.getRemove().get(i).getRemove_name();
                    }
                }
            }

            ((MyViewHolder) holder).without1.setText(without);
        }else{
            ((MyViewHolder) holder).without_rel.setVisibility(View.GONE);

        }



        if (dish.getSub_category_name() != null) {
            ((MyViewHolder) holder).dish_name.setText(dish.getSub_category_name());
        }
        ((MyViewHolder) holder).dish_name.setTypeface(typeface);
        ((MyViewHolder) holder).addition.setTypeface(typeface);
        ((MyViewHolder) holder).addition1.setTypeface(typeface);
        ((MyViewHolder) holder).without.setTypeface(typeface);
        ((MyViewHolder) holder).without1.setTypeface(typeface);


        if (dish.getQuantity() != null) {
            ((MyViewHolder) holder).count.setText(dish.getQuantity());
            ((MyViewHolder) holder).txt_quantity.setText(dish.getQuantity());
        }


        if (dish.getSub_category_image() != null) {

            Glide.with(mContext)
                    .load(dish.getSub_category_image())
                    .error(R.drawable.mainicon)
                    .placeholder(R.drawable.mainicon)
                    .into(((MyViewHolder) holder).dish_image);

        }
        ((MyViewHolder) holder).delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete(position);
            }
        });

        if(flag!=null)
        {
            ((MyViewHolder) holder).delete.setVisibility(View.GONE);
            ((MyViewHolder) holder).quan_rel.setVisibility(View.GONE);
            ((MyViewHolder) holder).count.setVisibility(View.VISIBLE);
            ((MyViewHolder) holder).total_rel.setVisibility(View.VISIBLE);

//            ((MyViewHolder) holder).rel1.setVisibility(View.GONE);
        }


        if(lang.equals("ar")){
            ((MyViewHolder) holder).txt_quantity.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
        }
        ((MyViewHolder) holder).txt_quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(((MyViewHolder) holder).txt_quantity.getText().toString().trim().length()==0){
                    ((MyViewHolder) holder).plus.setClickable(false);
                    ((MyViewHolder) holder).minus.setClickable(false);
                }
                else{
                    ((MyViewHolder) holder).plus.setClickable(true);
                    ((MyViewHolder) holder).minus.setClickable(true);
                }
                if(((MyViewHolder) holder).txt_quantity.getText().toString().equals("0")||((MyViewHolder) holder).txt_quantity.getText().toString().equals("00")){
                    ((MyViewHolder) holder).txt_quantity.setText("1");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        ((MyViewHolder) holder).plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((MyViewHolder) holder).txt_quantity.setText((Integer.parseInt(((MyViewHolder) holder).txt_quantity.getText().toString().trim()) + 1)+"");

                Api_Service requestInterface = Config.getClient().create(Api_Service.class);
                Call<Check> response = requestInterface.editCart(dish.getCart_id(),lang,((MyViewHolder) holder).txt_quantity.getText().toString().trim());
                response.enqueue(new Callback<Check>() {
                    @Override
                    public void onResponse(Call<Check> call, retrofit2.Response<Check> response) {
                        Log.d("succcesss",response.body().getMessage());

                        Check resp = response.body();
                        if (resp != null) {
                            if (resp.getSuccess() == 1) {
                                Log.d("succcesss","subbbb");
                                getCart();
                                ((MyViewHolder) holder).count.setText(((MyViewHolder) holder).txt_quantity.getText().toString().trim());

                            }
                            else{

                            }
                        }
                    }


                    @Override
                    public void onFailure(Call<Check> call, Throwable t) {
                        Log.d("succcesss","subbbb2"+t.getMessage());

                        //  Toast.makeText(mContext,"خطأ في الاتصال بشبكة الانترنت",Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
        ((MyViewHolder) holder).minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.parseInt(((MyViewHolder) holder).txt_quantity.getText().toString())>1) {
                    ((MyViewHolder) holder).txt_quantity.setText((Integer.parseInt(((MyViewHolder) holder).txt_quantity.getText().toString()) - 1 )+"");

                    Api_Service requestInterface = Config.getClient().create(Api_Service.class);
                    Call<Check> response = requestInterface.editCart(dish.getCart_id(),lang,((MyViewHolder) holder).txt_quantity.getText().toString().trim());
                    response.enqueue(new Callback<Check>() {
                        @Override
                        public void onResponse(Call<Check> call, retrofit2.Response<Check> response) {
                            Log.d("succcesss",response.body().getMessage());

                            Check resp = response.body();
                            if (resp != null) {
                                if (resp.getSuccess() == 1) {
                                    Log.d("succcesss","subbbb");
                                    getCart();
                                    ((MyViewHolder) holder).count.setText(((MyViewHolder) holder).txt_quantity.getText().toString().trim());

                                }
                                else{

                                }
                            }
                        }


                        @Override
                        public void onFailure(Call<Check> call, Throwable t) {
                            Log.d("succcesss","subbbb2"+t.getMessage());

                            //  Toast.makeText(mContext,"خطأ في الاتصال بشبكة الانترنت",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return dishes.size()-1;
    }

    private void delete(final int pos) {
        if (connectionDetection.isConnected()) {
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

        text.setText(mContext.getResources().getString(R.string.surecartdelete));
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
                SharedPrefManager.getInstance(mContext).delete_Cart();
                AppCompatActivity activity = (AppCompatActivity) mContext;
                activity.getSupportActionBar();
                Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
                activity.setSupportActionBar(toolbar);
                RelativeLayout no = toolbar.findViewById(R.id.no_cart);
                RelativeLayout yes = toolbar.findViewById(R.id.yes_cart);

                TextView num = toolbar.getRootView().findViewById(R.id.num);
                title = toolbar.getRootView().findViewById(R.id.toolbar_title);

                if (SharedPrefManager.getInstance(mContext).get_Cart() > 0) {
                    cart = toolbar.getRootView().findViewById(R.id.cart);

                    no.setVisibility(View.INVISIBLE);
                    yes.setVisibility(View.VISIBLE);
                    num.setText(String.valueOf(SharedPrefManager.getInstance(mContext).get_Cart()));

                } else {
                    cart = toolbar.getRootView().findViewById(R.id.cart1);
                    yes.setVisibility(View.INVISIBLE);
                    no.setVisibility(View.VISIBLE);
                }
                title.setText(mContext.getResources().getString(R.string.cart));
                cart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Fragment fragment = new MyCart();
                        AppCompatActivity activity = (AppCompatActivity) mContext;

                        FragmentManager fragmentManager = activity.getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.container, fragment).addToBackStack("xyz")
                                .commit();
                    }
                });


                deleteCart(pos);

            }
            }


            );

            popupWindow.showAtLocation(view1, Gravity.CENTER, 0, 0);


        } else {
            Toast.makeText(mContext, mContext.getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
        }




    }

    private void deleteCart(final int pos){
        Log.d("succcesss","subbbb1");

        Api_Service requestInterface = Config.getClient().create(Api_Service.class);
        Call<Check> response = requestInterface.deleteCart(SharedPrefManager.getInstance(mContext).getUser().getClient_id()
                , dishes.get(pos).getCart_id());
        response.enqueue(new Callback<Check>() {
            @Override
            public void onResponse(Call<Check> call, retrofit2.Response<Check> response) {
                Log.d("succcesss",response.body().getMessage());

                Check resp = response.body();
                if (resp != null) {
                    if (resp.getSuccess() == 1) {
                        Log.d("succcesss","subbbb");
                        Toast.makeText(mContext, mContext.getResources().getString(R.string.deletedfromcart), Toast.LENGTH_SHORT).show();
                        dishes.remove(pos);
                        getCart();
                        notifyDataSetChanged();

                    }
                    else{

                    }
                }
            }


            @Override
            public void onFailure(Call<Check> call, Throwable t) {
                Log.d("succcesss","subbbb2"+t.getMessage());

                //  Toast.makeText(mContext,"خطأ في الاتصال بشبكة الانترنت",Toast.LENGTH_SHORT).show();
            }
        });

    }



    public  void getCart(){
        Log.d("cartttttttt","cart1");
        if (connectionDetection.isConnected()) {
            final Api_Service requestInterface = Config.getClient().create(Api_Service.class);
            Call<Cart_Response2> response1 = requestInterface.getCart(SharedPrefManager.getInstance(mContext).getUser().getClient_id(),
                    lang,"");
            response1.enqueue(new Callback<Cart_Response2>() {
                @Override
                public void onResponse(Call<Cart_Response2> call, retrofit2.Response<Cart_Response2> response) {
                    Cart_Response2 resp = response.body();
                    Log.d("cartttttttt","cart3");

                    if (resp != null) {
                        if (resp.getSuccess() == 1) {
                            ArrayList<Cart_Response2.CartModel2> cart1 = resp.getProduct();
                            if (cart1.size() > 0) {
                                Log.d("cartttttttt","cart2");
                                dishes=cart1;
                                notifyDataSetChanged();
                                MyCart.continu.setVisibility(View.VISIBLE);
                                MyCart.shop.setVisibility(View.VISIBLE);
                                MyCart.message.setVisibility(View.INVISIBLE);
                                MyCart.image.setVisibility(View.INVISIBLE);
//                                MyCart.delete.setVisibility(View.VISIBLE);


                                if(Float.parseFloat(cart1.get(cart1.size() - 1).getDiscount_percentage())>0){
                                    MyCart.rel1.setVisibility(View.VISIBLE);
                                    MyCart.rel2.setVisibility(View.VISIBLE);
                                    MyCart.rel3.setVisibility(View.VISIBLE);
                                    MyCart.rel5.setVisibility(View.VISIBLE);
//                                      total_txt.setText(cart1.get(cart1.size() - 1).getTotal_amount());

                                    MyCart.total_txt.setText(cart1.get(cart1.size() - 1).getTotal_amount());
                                    MyCart.discount.setText(cart1.get(cart1.size() - 1).getSummary());
                                    MyCart.total_.setText(cart1.get(cart1.size() - 1).getTotal_amount_after_disc());
                                    MyCart.txt1.setText(mContext.getResources().getString(R.string.discountt)+" ("+cart1.get(cart1.size() - 1).getDiscount_percentage()+"%) : ");
                                    MyCart.vat.setText(mContext.getResources().getString(R.string.vat)+" ("+cart1.get(cart1.size() - 1).getVat()+"%) : ");
                                    MyCart.vat_value.setText(cart1.get(cart1.size() - 1).getVat_value());
                                }else {
                                    MyCart.rel1.setVisibility(View.VISIBLE);
                                    MyCart.rel2.setVisibility(View.GONE);
                                    MyCart.rel3.setVisibility(View.VISIBLE);
                                    MyCart.rel5.setVisibility(View.VISIBLE);
                                    MyCart.total_txt.setText(cart1.get(cart1.size() - 1).getTotal_amount());
                                    MyCart.total_.setText(cart1.get(cart1.size() - 1).getTotal_amount_after_disc());
                                    MyCart.txt1.setText(mContext.getResources().getString(R.string.discountt)+" ("+cart1.get(cart1.size() - 1).getDiscount_percentage()+"%) : ");
                                    MyCart.vat.setText(mContext.getResources().getString(R.string.vat)+" ("+cart1.get(cart1.size() - 1).getVat()+"%) : ");
                                    MyCart.vat_value.setText(cart1.get(cart1.size() - 1).getVat_value());
                                    MyCart.discount.setText(cart1.get(cart1.size() - 1).getSummary());
                                }

                            } else {
                                Log.d("cartttttttt","cart4");

                                MyCart.continu.setVisibility(View.INVISIBLE);
                                MyCart.shop.setVisibility(View.INVISIBLE);
                                MyCart.message.setVisibility(View.VISIBLE);
                                MyCart.image.setVisibility(View.VISIBLE);
//                                MyCart.delete.setVisibility(View.INVISIBLE);
                                MyCart.rel1.setVisibility(View.GONE);
                                MyCart.rel2.setVisibility(View.GONE);
                                MyCart.rel3.setVisibility(View.GONE);
                                MyCart.rel5.setVisibility(View.GONE);
                            }

                        } else {
                            MyCart.message.setVisibility(View.VISIBLE);
                            MyCart.image.setVisibility(View.VISIBLE);

                        }
                    } else {
                        MyCart.message.setVisibility(View.VISIBLE);
                        MyCart.image.setVisibility(View.VISIBLE);
                    }
                }


                @Override
                public void onFailure(Call<Cart_Response2> call, Throwable t) {
                    MyCart.message.setVisibility(View.VISIBLE);
                    MyCart.image.setVisibility(View.VISIBLE);
                }
            });

        } else {
            Toast.makeText(mContext, mContext.getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
        }
    }


}


