package com.emcan.zella;

import android.content.Context;


import com.emcan.zella.Beans.Additions_Model;
import com.emcan.zella.Beans.Meal_Additions;
import com.emcan.zella.Beans.Prices_Model;
import com.emcan.zella.Beans.Remove_Response;
import com.emcan.zella.Beans.Reviews_Model;
import com.emcan.zella.Beans.Sub_Cat_Images_Model;

import retrofit2.Call;
import retrofit2.Callback;

public class Data_Manger {

//
    public static  void getPhtos(final Get_Photos fragment, final Context context,String id) {

        final Api_Service requestInterface = Config.getClient().create(Api_Service.class);
        Call<Sub_Cat_Images_Model> response1 = requestInterface.get_Sub_Cat_Images(id);
        response1.enqueue(new Callback<Sub_Cat_Images_Model>() {
            @Override
            public void onResponse(Call<Sub_Cat_Images_Model> call, retrofit2.Response<Sub_Cat_Images_Model> response) {
                Sub_Cat_Images_Model resp = response.body();
                if (resp != null) {
                    if (resp.getSuccess() == 1) {

//                        fragment.get_Photos(resp.getProduct());

                    }
                }
            }

            @Override
            public void onFailure(Call<Sub_Cat_Images_Model> call, Throwable t) {



            }
        });

    }

    public static void get_Additions(final Get_Photos fragment, final  Context context,String parent_id){

       String lang= SharedPrefManager.getInstance(context).getLang();
        Api_Service requestInterface= Config.getClient().create(Api_Service.class);
        Call<Additions_Model> response1 = requestInterface.get_Additions(lang,"",
                parent_id);
        response1.enqueue(new Callback<Additions_Model>() {
            @Override
            public void onResponse(Call<Additions_Model> call, retrofit2.Response<Additions_Model> response) {
                Additions_Model resp = response.body();
                if (resp != null) {
                    if (resp.getSuccess() == 1) {
                        fragment.get_Additions(resp.getProduct());


                    } else { }
                } else { }
            }


            @Override
            public void onFailure(Call<Additions_Model> call, Throwable t) {

            }
        });
    }

    public static void get_Removes(final Get_Photos fragment,final Context context,String parent_id) {


        String lang= SharedPrefManager.getInstance(context).getLang();
        Api_Service requestInterface = Config.getClient().create(Api_Service.class);
        Call<Remove_Response> response1 = requestInterface.get_removes(lang,parent_id);
        response1.enqueue(new Callback<Remove_Response>() {
            @Override
            public void onResponse(Call<Remove_Response> call, retrofit2.Response<Remove_Response> response) {
                Remove_Response resp = response.body();
                if (resp != null) {
                    if (resp.getSuccess() == 1) {
                        fragment.get_Removes(resp.getProduct());

                    } else {
                    }
                } else {
                }
            }


            @Override
            public void onFailure(Call<Remove_Response> call, Throwable t) {

            }
        });
    }



    public static void get_Meal_additions(final GET_DATA fragment, Context context) {


        String lang= SharedPrefManager.getInstance(context).getLang();
        Api_Service requestInterface = Config.getClient().create(Api_Service.class);
        Call<Meal_Additions> response1 = requestInterface.getMeal_Additions(lang,"");
        response1.enqueue(new Callback<Meal_Additions>() {
            @Override
            public void onResponse(Call<Meal_Additions> call, retrofit2.Response<Meal_Additions> response) {
                Meal_Additions resp = response.body();
                if (resp != null) {
                    if (resp.getSuccess() == 1) {
//                        fragment.get_Meal_additiona(resp.getProduct().get(0));


                    } else {
                    }
                } else {
                }
            }


            @Override
            public void onFailure(Call<Meal_Additions> call, Throwable t) {

            }
        });
    }

    public static void get_Prices(final Context context, final GET_DATA fragment,String id){

       final String lang= SharedPrefManager.getInstance(context).getLang();
        Api_Service requestInterface = Config.getClient().create(Api_Service.class);
        Call<Prices_Model> response1 = requestInterface.getSub_Cat_Prices(id, lang,"");
        response1.enqueue(new Callback<Prices_Model>() {
            @Override
            public void onResponse(Call<Prices_Model> call, retrofit2.Response<Prices_Model> response) {
                Prices_Model resp = response.body();
                if (resp != null) {
                    if (resp.getSuccess() == 1) {
                       fragment.get_Price(resp.getProduct());



                    } else {
                    }
                }
            }

            @Override
            public void onFailure(Call<Prices_Model> call, Throwable t) {

            }
        });
    }

}
