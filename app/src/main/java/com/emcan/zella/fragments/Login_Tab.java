package com.emcan.zella.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

import com.emcan.zella.Api_Service;
import com.emcan.zella.Beans.Cart_Response2;
import com.emcan.zella.Beans.Login_response;
import com.emcan.zella.Beans.User;
import com.emcan.zella.Config;
import com.emcan.zella.ConnectionDetection;
import com.emcan.zella.LoginListener;
import com.emcan.zella.R;
import com.emcan.zella.SharedPrefManager;
import com.emcan.zella.activities.Forget_Pass;
import com.emcan.zella.activities.Login_Activity;
import com.emcan.zella.activities.MainActivity;
import com.emcan.zella.databinding.FragmentLoginTabBinding;
import com.emcan.zella.databinding.FragmentSignupTabBinding;

import java.util.ArrayList;


public class Login_Tab extends Fragment {

    ConnectionDetection connectionDetection;
    String password_, phone_;
    PopupWindow popupWindow;
    Context context;
    String lang;
    AppCompatActivity activity;
    Typeface typeface;
    LoginListener loginListener;
    Spinner spinner;
    FragmentLoginTabBinding binding;
    public Login_Tab() {
        // Required empty public constructor
    }

    public static Login_Tab newInstance(String param1, String param2) {
        Login_Tab fragment = new Login_Tab();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentLoginTabBinding.inflate(inflater, container, false);
        context = getContext();

        lang = SharedPrefManager.getInstance(context).getLang();
        activity = (AppCompatActivity) getActivity();
        connectionDetection = new ConnectionDetection(context);

        if (lang.equals("en")) {
            typeface = ResourcesCompat.getFont(context, R.font.amaranth_bold);
            binding.phone.setGravity(Gravity.LEFT);
            binding.password.setGravity(Gravity.LEFT);

        }
        if (lang.equals("ar")) {
            typeface = ResourcesCompat.getFont(context, R.font.bein_black);
            binding.phone.setGravity(Gravity.RIGHT);
            binding.password.setGravity(Gravity.RIGHT);
        }

        binding.phone.setTypeface(typeface);
        binding.password.setTypeface(typeface);
        binding.forget.setTypeface(typeface);
        binding.signUp.setTypeface(typeface);
        binding.login.setTypeface(typeface);



        binding.signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginListener.selectTab(1);
            }
        });


        binding.forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Forget_Pass.class);
                startActivity(intent);
            }
        });

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                phone_ = binding.phone.getText().toString().trim();
                password_ = binding.password.getText().toString().trim();

                if (phone_.isEmpty() || phone_.length() == 0 || phone_.equals("") || phone_ == null) {
                    binding.phone.setError("");
                    binding.phone.requestFocus();
                } else {

                    binding.phone.setError(null);

                        if (password_.isEmpty() || password_.length() == 0 || password_.equals("") || password_ == null) {
                            binding.password.setError("");
                            binding.password.requestFocus();
                        } else {

                            binding.password.setError(null);


                            login();

                    }

                }
            }
        });


        return binding.getRoot();
    }

    public  void setListener(LoginListener loginListener){
        this.loginListener=loginListener;
    }

    private void login() {

        if (connectionDetection.isConnected()) {
          binding.  progressBar.setVisibility(View.VISIBLE);

            Api_Service requestInterface = Config.getClient().create(Api_Service.class);
            //   Toast.makeText(context,s,Toast.LENGTH_SHORT).show();

            User user=new User();
            user.setClient_phone(phone_);
            user.setClient_password(password_);
            user.setLang(lang);
            user.setType("android");
            user.setDevice_token(SharedPrefManager.getInstance(context).get_token());
            Call<Login_response> response1 = requestInterface.login2(user);
            response1.enqueue(new Callback<Login_response>() {

                @Override
                public void onResponse(Call<Login_response> call, retrofit2.Response<Login_response> response) {

                    Login_response resp = response.body();

                    if (resp != null) {

                        if (resp.getSuccess() == 1) {
                            ArrayList<User> product = resp.getProduct();
                            if (product.get(0) != null) {
                                //--------------------add user to shred prefrence----------------//
                                User client = product.get(0);
                                SharedPrefManager.getInstance(context).userLogin(client);

                                Log.d("lllll",product.get(0).getClient_id());

                                final Api_Service requestInterface = Config.getClient().create(Api_Service.class);
                                Call<Cart_Response2> response1 = requestInterface.getCart(SharedPrefManager.getInstance(context).getUser().getClient_id(),lang,"");
                                response1.enqueue(new Callback<Cart_Response2>() {
                                    @Override
                                    public void onResponse(Call<Cart_Response2> call, retrofit2.Response<Cart_Response2> response) {
                                       binding. progressBar.setVisibility(View.GONE);

                                        Cart_Response2 resp = response.body();
                                        if (resp != null) {
                                            if (resp.getSuccess() == 1) {
                                                ArrayList<Cart_Response2.CartModel2> cart1 = resp.getProduct();
                                                if (cart1.size() > 0) {
                                                    for (int i = 0; i < cart1.size() - 1; i++) {
                                                        SharedPrefManager.getInstance(context).add_Cart();
                                                    }
                                                }
                                            } else {
                                                //     Toast.makeText(context,resp.getMessage(),Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                        Intent intent = new Intent(context, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                        startActivity(intent);
                                       activity. finish();
                                    }

                                    @Override
                                    public void onFailure(Call<Cart_Response2> call, Throwable t) {
                                        binding. progressBar.setVisibility(View.INVISIBLE);
                                        //  Toast.makeText(getContext(),"خطأ في الاتصال بشبكة الانترنت",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        } else {
                            binding. progressBar.setVisibility(View.GONE);
                            Toast.makeText(context, resp.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        binding. progressBar.setVisibility(View.GONE);
                        Toast.makeText(context,getResources().getString(R.string.errortryagain), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Login_response> call, Throwable t) {
                    Toast.makeText(context, getResources().getString(R.string.errortryagain), Toast.LENGTH_SHORT).show();
                    binding.progressBar.setVisibility(View.GONE);
                    Log.d("lllllllllll", t.getMessage());

                }
            });

        } else {
            Toast.makeText(context, getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
        }

    }


}