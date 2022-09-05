package com.emcan.zella.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.emcan.zella.Api_Service;
import com.emcan.zella.Beans.Check_Model;
import com.emcan.zella.Beans.Login_response;
import com.emcan.zella.Beans.User;
import com.emcan.zella.Config;
import com.emcan.zella.ConnectionDetection;
import com.emcan.zella.R;
import com.emcan.zella.SharedPrefManager;
import com.emcan.zella.activities.MainActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


public class Edit_Account extends Fragment {

    Toolbar toolbar;
    TextView title,num,e1,e2,e3;
    ImageView cart;
    RelativeLayout no,yes;
    EditText name,password,confirm_password,phone;
    ConnectionDetection connectionDetection;
    ArrayList<User> users;
    String lang;
    Context context;
    Typeface typeface;
    PopupWindow popupWindow;

    RelativeLayout pickup;

    public Edit_Account() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_edit__account, container, false);


        final AppCompatActivity activity = (AppCompatActivity) getActivity();
        context=getContext();

        RelativeLayout bar = activity.findViewById(R.id.bar);
        bar.setVisibility(View.VISIBLE);
        ((MainActivity) activity).select_icon("none");

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        ImageButton back=toolbar.findViewById(R.id.back);

        lang= SharedPrefManager.getInstance(activity).getLang();
        if(lang.equals("en")){
            typeface = ResourcesCompat.getFont(activity, R.font.amaranth_bold);
            back.setRotation(180);
        }
        if(lang.equals("ar")){

            typeface = ResourcesCompat.getFont(activity, R.font.bein_black);
        }
        no=toolbar.findViewById(R.id.no_cart);
        yes=toolbar.findViewById(R.id.yes_cart);

        num=toolbar.getRootView().findViewById(R.id.num);
        title=toolbar.getRootView().findViewById(R.id.toolbar_title);

        if(SharedPrefManager.getInstance(getContext()).get_Cart()>0){
            cart=toolbar.getRootView().findViewById(R.id.cart);

            no.setVisibility(View.INVISIBLE);
            yes.setVisibility(View.VISIBLE);
            num.setText(String.valueOf(SharedPrefManager.getInstance(getContext()).get_Cart()));

        }else{
            cart=toolbar.getRootView().findViewById(R.id.cart1);
            yes.setVisibility(View.INVISIBLE);
            no.setVisibility(View.VISIBLE);
        }
        title.setTypeface(typeface);
        title.setText(activity.getResources().getString(R.string.editacc));

        back.setVisibility(View.VISIBLE);

        cart.setVisibility(View.VISIBLE);
        ImageView menu=toolbar.findViewById(R.id.menu);
        menu.setVisibility(View.VISIBLE);

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new MyCart();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment).addToBackStack("xyz")
                        .commit();
            }
        });
        //------------------------------------------------//
        connectionDetection=new ConnectionDetection(getContext());

        name=view.findViewById(R.id.name);
        name.setTypeface(typeface);
        password=view.findViewById(R.id.password);
        e2=view.findViewById(R.id.e2);
        e1=view.findViewById(R.id.e1);
        e3=view.findViewById(R.id.e3);

        e1.setTypeface(typeface);
        e2.setTypeface(typeface);
        e3.setTypeface(typeface);

        password.setTypeface(typeface);
        confirm_password=view.findViewById(R.id.confirm_password);
        confirm_password.setTypeface(typeface);
        phone=view.findViewById(R.id.phone);
        phone.setTypeface(typeface);
        if(lang.equals("ar")){
            name.setGravity(Gravity.RIGHT);
            password.setGravity(Gravity.RIGHT);
            confirm_password.setGravity(Gravity.RIGHT);
            phone.setGravity(Gravity.RIGHT);
        }else{
            name.setGravity(Gravity.LEFT);
            password.setGravity(Gravity.LEFT);
            confirm_password.setGravity(Gravity.LEFT);
            phone.setGravity(Gravity.LEFT);
        }

        User client=new User();
        client=SharedPrefManager.getInstance(getContext()).getUser();

        name.setText(client.getClient_name());
        password.setText(client.getClient_password());
        phone.setText(client.getClient_phone());

        Button delete=view.findViewById(R.id.delete);
        delete.setTypeface(typeface);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
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

                text.setText(context.getResources().getString(R.string.delete_account_));
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (connectionDetection.isConnected()) {
                            Api_Service requestInterface = Config.getClient().create(Api_Service.class);
                            Call<Check_Model> response1 = requestInterface.deleteAccount(SharedPrefManager.getInstance(context).getUser().getClient_id()
                                    ,lang);
                            response1.enqueue(new Callback<Check_Model>() {
                                @Override
                                public void onResponse(Call<Check_Model> call, retrofit2.Response<Check_Model> response) {
                                    Check_Model resp = response.body();
                                    if (resp != null) {
                                        if (resp.getSuccess() == 1) {
                                            Toast.makeText(context, resp.getMessage(), Toast.LENGTH_SHORT).show();

                                            SharedPrefManager.getInstance(context).logout();
                                            Intent intent = new Intent(context, MainActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                            startActivity(intent);

//
                                        }
                                    } else {
                                        Toast.makeText(context, resp.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Check_Model> call, Throwable t) {
//                                        Toast.makeText(context, context.getResources().getString(R.string.deletedfromfav), Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else {

                            Toast.makeText(context, context.getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

            }
        });


        Button edit=view.findViewById(R.id.edit);
        edit.setTypeface(typeface);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name_txt=name.getText().toString().trim();
                String pass_txt=password.getText().toString().trim();
                String pass2_txt=confirm_password.getText().toString().trim();
                String phone_txt=phone.getText().toString().trim();

                if (name_txt==null||name_txt.equals("")){
                    name.requestFocus();

                }else{
                    name.clearFocus();
                    if(pass_txt==null||pass_txt.equals("")){
                        password.requestFocus();
                    }else{
                        password.clearFocus();
                        if(pass2_txt==null||pass2_txt.equals(""))
                        {
                            confirm_password.requestFocus();
                        }else{
                            confirm_password.clearFocus();
                                    phone.clearFocus();
                                    if(pass_txt.equals(pass2_txt)){
                                        if(connectionDetection.isConnected()){

                                            Api_Service requestInterface = Config.getClient().create(Api_Service.class);
                                            User user=new User();
                                            user.setClient_phone(phone_txt);
                                            user.setClient_password(pass_txt);
                                           user.setClient_name(name_txt);
                                            user.setLang(lang);
                                            user.setClient_id(SharedPrefManager.getInstance(getContext()).getUser().getClient_id());
                                            Call<Login_response> response1 = requestInterface.edit_account
                                                    (user);
                                            response1.enqueue(new Callback<Login_response>() {
                                                @Override
                                                public void onResponse(Call<Login_response> call, retrofit2.Response<Login_response> response) {
                                                    Login_response resp = response.body();
                                                    if (resp != null) {
                                                        if (resp.getSuccess() == 1) {

                                                            users=resp.getProduct();
                                                            if (users.size()>0) {
                                                                User user = users.get(0);
                                                                Log.d("phoneeee",user.getClient_phone()+"   bbb");

                                                                SharedPrefManager.getInstance(getContext()).userLogin(user);
                                                                Toast.makeText(getContext(),activity.getResources().getString(R.string.dataedit),Toast.LENGTH_SHORT).show();
                                                                activity.getSupportFragmentManager().popBackStack();
                                                            }
                                                        }}}


                                                @Override
                                                public void onFailure(Call<Login_response> call, Throwable t) {

                                                }
                                            });


                                        }else{
                                            Toast.makeText(getContext(),activity.getResources().getString(R.string.networkerror),Toast.LENGTH_SHORT).show();
                                        }

                                    }else{
                                        Toast.makeText(getContext(),activity.getResources().getString(R.string.samepass),Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                    }



            }
        });

        return view;
    }




}
