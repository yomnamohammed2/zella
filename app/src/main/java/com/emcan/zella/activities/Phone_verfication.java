package com.emcan.zella.activities;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.emcan.zella.Api_Service;
import com.emcan.zella.Beans.Login_response;
import com.emcan.zella.Beans.User;
import com.emcan.zella.Beans.Verify_Model;
import com.emcan.zella.Config;
import com.emcan.zella.ConnectionDetection;
import com.emcan.zella.R;
import com.emcan.zella.SharedPrefManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Phone_verfication extends AppCompatActivity {

    ImageView back;
    TextView txt1,txt2,txt3;
    String mVerificationId;
    String name,password,email;
    Button login;
    ProgressBar progressBar;
    EditText digit1,digit2,digit3,digit4,digit5,digit6;
    String phone;
    String code="";
    RelativeLayout rel1,rel2,rel3,rel4,rel5,rel6;
    private FirebaseAuth mAuth;
    CountDownTimer timer;
    Context context;
    ConnectionDetection connectionDetection;
    Typeface typeface;
    String lang;
    int fl;
    String client_id,phone_code;
    static String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verfication);

          lang= SharedPrefManager.getInstance(this).getLang();

          fl=getIntent().getIntExtra("fl",0);
          client_id=getIntent().getStringExtra("client_id");

        if(lang.equals("ar")){
            typeface = ResourcesCompat.getFont(Phone_verfication.this, R.font.amaranth_bold);
            Locale myLocale = new Locale("ar");
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
            conf.setLayoutDirection(new Locale("ar"));

        }else{
            typeface= ResourcesCompat.getFont(Phone_verfication.this, R.font.bein_black);
            Locale myLocale = new Locale("en");
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
            conf.setLayoutDirection(new Locale("en"));

        }

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            phone=getIntent().getStringExtra("phone");
            password=getIntent().getStringExtra("password");
            name=getIntent().getStringExtra("name");
            email=getIntent().getStringExtra("email");
            phone_code=getIntent().getStringExtra("phone_code");

        if(lang.equals("en")){
            typeface = ResourcesCompat.getFont(Phone_verfication.this, R.font.amaranth_bold);
        }

        if(lang.equals("ar")){
            typeface= ResourcesCompat.getFont(Phone_verfication.this, R.font.bein_black);
        }

        init();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Phone_verfication.super.onBackPressed();
            }
        });

        progressBar = (ProgressBar) findViewById(R.id.progressBar);


        verifyPhone("+973"+phone);

        timer=new CountDownTimer(300000, 1000) {

            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                /*txt3.setText( String.format("%02d", minutes)
                        + ":" + String.format("%02d", seconds) +" دقائق ");*/
            }

            public void onFinish() {
                //txt3.setText("00:00");
            }
        }.start();


        digit1 .addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // add a condition to check length here - you can give here length according to your requirement to go to next EditTexts.
                if(digit1.getText().toString().trim().length() >0){
                    digit1.clearFocus();
                    digit2.requestFocus();

                }
            }
        });

        digit2 .addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // add a condition to check length here - you can give here length according to your requirement to go to next EditTexts.
                if(digit2.getText().toString().trim().length() >0){
                    digit2.clearFocus();
                    digit3.requestFocus();

                }
            }
        });
        digit3 .addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // add a condition to check length here - you can give here length according to your requirement to go to next EditTexts.
                if(digit3.getText().toString().trim().length() >0){
                    digit3.clearFocus();
                    digit4.requestFocus();
                }
            }
        });
        digit4 .addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // add a condition to check length here - you can give here length according to your requirement to go to next EditTexts.
                if(digit4.getText().toString().trim().length() >0){

                    digit4.clearFocus();
                    digit5.requestFocus();


                }
            }
        });

        digit5 .addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // add a condition to check length here - you can give here length according to your requirement to go to next EditTexts.
                if(digit5.getText().toString().trim().length() >0){

                    digit5.clearFocus();
                    digit6.requestFocus();
                }
            }
        });
        digit6.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // add a condition to check length here - you can give here length according to your requirement to go to next EditTexts.
                if(digit6.getText().toString().trim().length() >0){
                    //  digit5.clearFocus();
                }
            }
        });


        digit1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if (keyCode == KeyEvent.KEYCODE_DEL) {

                    if(digit1.isFocused()) {
                        digit1.setText("");
                        digit1.requestFocus();
                    }

                }
                return false;
            }

        });

        digit2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    if(digit2.getText().toString()==null||digit2.getText().toString().equals("")) {
                        digit1.requestFocus();
                    }else {

                        digit2.setText("");
                    }


                }
                return false;
            }

        });
        digit3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if(keyCode == KeyEvent.KEYCODE_DEL) {

                    if(digit3.getText().toString()==null||digit3.getText().toString().equals("")) {
                        {
                            digit2.requestFocus();}
                    }else {
                        digit3.setText("");
                    }


                }
                return false;
            }

        });
        digit4.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if(keyCode == KeyEvent.KEYCODE_DEL) {
                    if(digit4.getText().toString()==null||digit4.getText().toString().equals("")) {
                        digit3.requestFocus();
                    }else {
                        digit4.setText("");
                    }


                }
                return false;
            }

        });
        digit5.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if(keyCode == KeyEvent.KEYCODE_DEL) {

                    if(digit5.getText().toString()==null||digit5.getText().toString().equals("")){
                        digit4.requestFocus();
                    }else
                    {
                        digit5.setText("");
                    }


                }
                return false;
            }

        });
        digit6.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if(keyCode == KeyEvent.KEYCODE_DEL) {

                    if(digit6.getText().toString()==null||digit6.getText().toString().equals("")){
                        digit5.requestFocus();
                    }else
                    {
                        digit6.setText("");
                    }



                }
                return false;
            }

        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);

                String verify=digit1.getText().toString().trim()+digit2.getText().toString().trim()+
                        digit3.getText().toString().trim()+
                        digit4.getText().toString().trim();

                verify=verify+digit5.getText().toString().trim();

                verify=verify+digit6.getText().toString().trim();

                if (verify.length()<6 || verify.equals("")) {
                    Toast.makeText(context, context.getResources().getString(R.string.alert_code), Toast.LENGTH_SHORT).show();

                } else {

                    if (verify.toString().equals(code)) {
                        if(fl==1){
                           Intent i=new Intent(Phone_verfication.this,RestoreActivity.class);
                           i.putExtra("client_id",client_id);
                           i.putExtra("phone",phone);
                           startActivity(i);
                        }
                        else{
                            signup();
                        }
                    }
                }

            }
        });

        txt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://wa.me/" + "+97333405497");
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                }
            }
        });

    }

//    public void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                                if(fl==1){
//                                    Intent i=new Intent(Phone_verfication.this,RestoreActivity.class);
//                                    i.putExtra("client_id",client_id);
//                                    i.putExtra("phone",phone);
//                                    startActivity(i);
//                                }
//                                else{
//                                    signup();
//                                }
//
//                        } else {
//                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
//                                mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
//                                    @Override
//                                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                                        if(firebaseAuth.getCurrentUser()!=null){
//                                            //Log.d("error",firebaseAuth.getCurrentUser().getPhoneNumber()+"   n5n");
//                                            if(fl==1){
//                                                Intent i=new Intent(Phone_verfication.this,RestoreActivity.class);
//                                                i.putExtra("client_id",client_id);
//                                                i.putExtra("phone",phone);
//                                                startActivity(i);
//                                            }
//                                            else{
//                                                signup();
//                                            }
//                                        }
//                                        else{
//                                            Log.i("", "Failure");
//                                            Toast.makeText(context, context.getResources().getString(R.string.errortryagain), Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                                });
//
//                            }else {
//                                Log.i("", "Failure");
//                                Toast.makeText(context, context.getResources().getString(R.string.errortryagain), Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }
//                });
//
//    }

//    public void onMyCodeSent(String verificationId, String token) {
//        this.verificationId = verificationId;
//
//    }

//    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//        @Override
//        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
//
//            //Getting the code sent by SMS
//            code = phoneAuthCredential.getSmsCode();
//            //Log.d("llll",code);
//            progressBar.setVisibility(View.GONE);
//            timer.cancel();
//
//            if (code != null) {
//
//
//                String[] arr = code.trim().split("");
//
//
//                digit1.setText(arr[1]);
//
//                digit2.setText(arr[2]);
//
//                digit3.setText(arr[3]);
//
//                digit4.setText(arr[4]);
//                digit5.setText(arr[5]);
//                digit6.setText(arr[6]);
//
//
//
//                //     verifyVerificationCode(code);
//            }
//        }
//
//        @Override
//        public void onVerificationFailed(FirebaseException e) {
//            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
//        }
//
//        @Override
//        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//            super.onCodeSent(s, forceResendingToken);
//            //storing the verification id that is sent to the user
//            mVerificationId = s;
//        }
//    };


    private void init(){
        txt1=findViewById(R.id.txt1);
        txt2=findViewById(R.id.txt2);
        txt3=findViewById(R.id.txt3);

        txt2.setTypeface(typeface);
        txt1.setTypeface(typeface);
        txt3.setTypeface(typeface);


        login=findViewById(R.id.login);
        login.setTypeface(typeface);
        context =getApplicationContext();

        progressBar=findViewById(R.id.progressBar);

        connectionDetection=new ConnectionDetection(getApplicationContext());

        progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorAccent),
                PorterDuff.Mode.SRC_IN);


        digit1=findViewById(R.id.digit1);
        digit2=findViewById(R.id.digit2);
        digit3=findViewById(R.id.digit3);
        digit4=findViewById(R.id.digit4);
        digit5=findViewById(R.id.digit5);
        digit6=findViewById(R.id.digit6);
        rel5=findViewById(R.id.rel5);
        rel4=findViewById(R.id.rel4);
        rel3=findViewById(R.id.rel3);
        rel2=findViewById(R.id.rel2);
        rel1=findViewById(R.id.rel1);
        rel6=findViewById(R.id.rel6);

        back=findViewById(R.id.back);
        back.setRotation(180);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void signup() {

        if (connectionDetection.isConnected()) {
            progressBar.setVisibility(View.VISIBLE);
            Api_Service requestInterface = Config.getClient().create(Api_Service.class);

            User user = new User();
            user.setClient_phone(phone);
            user.setClient_name(name);
            user.setClient_password(password);

            user.setClient_email(email);
            user.setType("android");
            user.setLang(lang);
            user.setDevice_token(SharedPrefManager.getInstance(context).get_token());

            Call<Login_response> response1 = requestInterface.signup(user);
            response1.enqueue(new Callback<Login_response>() {
                @Override
                public void onResponse(Call<Login_response> call, Response<Login_response> response) {
                    progressBar.setVisibility(View.GONE);
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
                                finish();
                                Toast.makeText(context, resp.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                             progressBar.setVisibility(View.GONE);
                            Toast.makeText(context, resp.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                         progressBar.setVisibility(View.GONE);
                        Toast.makeText(context, getResources().getString(R.string.errortryagain), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Login_response> call, Throwable t) {
                    Toast.makeText(context, getResources().getString(R.string.errortryagain), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    Log.d("respooooonse", t.toString());

                }
            });

        } else {
            Toast.makeText(context, getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
        }


    }

    private void verifyPhone(String phone) {

        if (connectionDetection.isConnected()) {
            progressBar.setVisibility(View.VISIBLE);
            Api_Service requestInterface = Config.getClient().create(Api_Service.class);

            Call<Verify_Model> response1 = requestInterface.verifyPhone(phone,SharedPrefManager.getInstance(context).getLang());
            response1.enqueue(new Callback<Verify_Model>() {
                @Override
                public void onResponse(Call<Verify_Model> call, Response<Verify_Model> response) {
                    progressBar.setVisibility(View.GONE);
                    Verify_Model resp = response.body();
                    if (resp != null) {
                        if (resp.getSuccess().equals("1")) {

                            code=resp.getCode();

                        }
                    }
                }

                @Override
                public void onFailure(Call<Verify_Model> call, Throwable t) {

                }
            });

        } else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
        }


    }
}
