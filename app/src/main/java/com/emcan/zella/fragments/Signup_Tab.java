package com.emcan.zella.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.emcan.zella.Api_Service;
import com.emcan.zella.Beans.Check;
import com.emcan.zella.Beans.Login_response;
import com.emcan.zella.Beans.User;
import com.emcan.zella.Config;
import com.emcan.zella.ConnectionDetection;
import com.emcan.zella.LoginListener;
import com.emcan.zella.R;
import com.emcan.zella.SharedPrefManager;
import com.emcan.zella.activities.Forget_Pass;
import com.emcan.zella.activities.MainActivity;
import com.emcan.zella.activities.Phone_verfication;
import com.emcan.zella.databinding.FragmentSignupTabBinding;

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


public class Signup_Tab extends Fragment {

    ConnectionDetection connectionDetection;
    String password_, name_, phone_, email_;
    PopupWindow popupWindow;
    Context context;
    String lang;
    AppCompatActivity activity;
    Typeface typeface;
    LoginListener loginListener;
    Spinner spinner;
    FragmentSignupTabBinding binding;


    public Signup_Tab() {
        // Required empty public constructor
    }


    public static Signup_Tab newInstance(String param1, String param2) {
        Signup_Tab fragment = new Signup_Tab();
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
        binding = FragmentSignupTabBinding.inflate(inflater, container, false);

        context = getContext();

        lang = SharedPrefManager.getInstance(context).getLang();
        activity = (AppCompatActivity) getActivity();
        connectionDetection = new ConnectionDetection(context);

        if (lang.equals("en")) {
            typeface = ResourcesCompat.getFont(context, R.font.amaranth_bold);
            binding.phone.setGravity(Gravity.LEFT);
            binding.password.setGravity(Gravity.LEFT);
            binding.name.setGravity(Gravity.LEFT);
            binding.email.setGravity(Gravity.LEFT);
        }
        if (lang.equals("ar")) {
            typeface = ResourcesCompat.getFont(context, R.font.bein_black);
            binding.phone.setGravity(Gravity.RIGHT);
            binding.name.setGravity(Gravity.RIGHT);
            binding.email.setGravity(Gravity.RIGHT);
            binding.password.setGravity(Gravity.RIGHT);
        }

        binding.phone.setTypeface(typeface);
        binding.name.setTypeface(typeface);
        binding.email.setTypeface(typeface);
        binding.password.setTypeface(typeface);
        binding.txt1.setTypeface(typeface);
        binding.loginTxt.setTypeface(typeface);
        binding.signup.setTypeface(typeface);

        binding.loginTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginListener.selectTab(0);

            }
        });

        binding.txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


              loginListener.selectTab(0);
            }
        });

        binding.signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                phone_ = binding.phone.getText().toString().trim();
                name_ = binding.name.getText().toString().trim();
                password_ = binding.password.getText().toString().trim();
                email_ = binding.email.getText().toString().trim();

                if (phone_.isEmpty() || phone_.length() == 0 || phone_.equals("") || phone_ == null) {
                    binding.phone.setError("");
                    binding.phone.requestFocus();
                } else {

                    binding.phone.setError(null);

                    if (name_.isEmpty() || name_.length() == 0 || name_.equals("") || name_ == null) {
                        binding.name.setError("");
                        binding.name.requestFocus();
                    } else {

                        binding.name.setError(null);

                        if (password_.isEmpty() || password_.length() == 0 || password_.equals("") || password_ == null) {
                            binding.password.setError("");
                            binding.password.requestFocus();
                        } else {

                            binding.password.setError(null);

                            if (email_.isEmpty() || email_.length() == 0 || email_.equals("") || email_ == null) {
                                binding.email.setError("");
                                binding.email.requestFocus();
                            } else {

                                binding.email.setError(null);

                                check();

                            }

                        }

                    }

                }
            }
        });
        return binding.getRoot();

    }


    private void check() {
        binding.progressBar.setVisibility(View.VISIBLE);
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);

        if (connectionDetection.isConnected()) {
            Api_Service requestInterface = Config.getClient().create(Api_Service.class);

            User user = new User();
            user.setPhone(phone_);
            user.setLang(lang);
            Call<Check> response2 = requestInterface.checkExist(phone_,lang);

            response2.enqueue(new Callback<Check>() {
                @Override
                public void onResponse(Call<Check> call, Response<Check> response) {

                    binding.progressBar.setVisibility(View.INVISIBLE);
                    if (response != null) {

                        if (response.body().getSuccess() == 1) {

                            if (response.body().getExist() == 1) {

                                LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                                View customView = inflater.inflate(R.layout.message_popup, null);
                                popupWindow = new PopupWindow(
                                        customView,
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        //   700,
                                        LinearLayout.LayoutParams.MATCH_PARENT, true
                                );

                                TextView text = customView.findViewById(R.id.text);
                                text.setTypeface(typeface);
                                text.setText(response.body().getMessage());

                                Button okay = customView.findViewById(R.id.ok);
                                okay.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        popupWindow.dismiss();
                                    }
                                });

                                popupWindow.showAtLocation(binding.getRoot(), Gravity.CENTER, 0, 0);


                            } else {

                                Intent i = new Intent(activity, Phone_verfication.class);
                                i.putExtra("phone", phone_);
                                i.putExtra("name", name_);
                                i.putExtra("email", email_);
                                i.putExtra("password", password_);
                                startActivity(i);
                            }


                        }
                    }
                }

                @Override
                public void onFailure(Call<Check> call, Throwable t) {
                    Toast.makeText(context, context.getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
                    binding.progressBar.setVisibility(View.INVISIBLE);
                }
            });
        } else {
            Toast.makeText(context, context.getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
            binding.progressBar.setVisibility(View.INVISIBLE);
        }


    }

    private void signup() {

        if (connectionDetection.isConnected()) {
           binding. progressBar.setVisibility(View.VISIBLE);
            Api_Service requestInterface = Config.getClient().create(Api_Service.class);

            User user = new User();
            user.setClient_phone(phone_);
            user.setClient_name(name_);
            user.setClient_password(password_);

            user.setClient_email(email_);
            user.setType("android");
            user.setLang(lang);
            user.setDevice_token(SharedPrefManager.getInstance(context).get_token());

            Call<Login_response> response1 = requestInterface.signup(user);
            response1.enqueue(new Callback<Login_response>() {
                @Override
                public void onResponse(Call<Login_response> call, Response<Login_response> response) {
                    binding.progressBar.setVisibility(View.GONE);
                    Login_response resp = response.body();
                    if (resp != null) {
                        if (resp.getSuccess() == 1) {
                            ArrayList<User> product = resp.getProduct();
                            if (product.get(0) != null) {
                                //--------------------add user to shred prefrence----------------//
                                User client = product.get(0);
                                SharedPrefManager.getInstance(context).userLogin(client);
                                SharedPrefManager.getInstance(context).set_data(null);
                                SharedPrefManager.getInstance(context).set_CONTACT(null);

                                Intent intent = new Intent(context, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(intent);
                                activity.finish();
                                Toast.makeText(context, resp.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                           binding. progressBar.setVisibility(View.GONE);
                            Toast.makeText(context, resp.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                      binding.  progressBar.setVisibility(View.GONE);
                        Toast.makeText(context, getResources().getString(R.string.errortryagain), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Login_response> call, Throwable t) {
                    Toast.makeText(context, getResources().getString(R.string.errortryagain), Toast.LENGTH_SHORT).show();
                   binding. progressBar.setVisibility(View.GONE);
                    Log.d("respooooonse", t.toString());

                }
            });

        } else {
            Toast.makeText(context, getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
        }


    }

    public  void setListener(LoginListener loginListener){
        this.loginListener=loginListener;
    }

}