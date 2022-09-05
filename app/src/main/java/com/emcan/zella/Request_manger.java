package com.emcan.zella;

import android.content.Context;

import android.view.View;
import android.widget.Toast;


import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emcan.zella.Beans.Cart_Response2;
import com.emcan.zella.adapters.Cart_Adapter;
import com.emcan.zella.fragments.Confirm_Order;

import retrofit2.Call;
import retrofit2.Callback;

public class Request_manger {
    public static void getcart(final Context context, final Confirm_Order confirm_order, final View view) {

        confirm_order.progressBar.setVisibility(View.VISIBLE);
        final Api_Service requestInterface = Config.getClient().create(Api_Service.class);
        Call<Cart_Response2> response1 = requestInterface.getCart(
                SharedPrefManager.getInstance(context).getUser().getClient_id(), confirm_order.lang,"");
        response1.enqueue(new Callback<Cart_Response2>() {
            @Override
            public void onResponse(Call<Cart_Response2> call, retrofit2.Response<Cart_Response2> response) {
                Cart_Response2 resp = response.body();
                confirm_order.progressBar.setVisibility(View.INVISIBLE);

                if (resp != null) {
                    if (resp.getSuccess() == 1) {
                        confirm_order.cart1 = resp.getProduct();
                        if (confirm_order.cart1.size() > 0) {


                            Cart_Adapter mAdapter = new Cart_Adapter(context, confirm_order.cart1, view, "1");
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
                            confirm_order.recyclerView.setLayoutManager(mLayoutManager);
                            confirm_order.recyclerView.setItemAnimator(new DefaultItemAnimator());
                            confirm_order.recyclerView.setAdapter(mAdapter);

                            if (confirm_order.cart1.size() > 0) {
                                for (int i = 0; i < confirm_order.cart1.size() - 1; i++) {
                                    if (i == 0) {
                                        confirm_order.id_cart = confirm_order.cart1.get(0).getCart_id();
                                    } else {
                                        confirm_order.id_cart = confirm_order.id_cart + "," + confirm_order.cart1.get(i).getCart_id();
                                    }
                                }
                            }


                        }

                    }
                }
            }


            @Override
            public void onFailure(Call<Cart_Response2> call, Throwable t) {
                Toast.makeText(context, context.getResources().getString(R.string.errortryagain), Toast.LENGTH_SHORT).show();
            }
        });

    }


}
