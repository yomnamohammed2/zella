package com.emcan.zella.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import retrofit2.Call;
import retrofit2.Callback;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.emcan.zella.Api_Service;
import com.emcan.zella.Beans.Check_Model;
import com.emcan.zella.Config;
import com.emcan.zella.ConnectionDetection;
import com.emcan.zella.R;
import com.emcan.zella.SharedPrefManager;
import com.emcan.zella.activities.MainActivity;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


public class My_Account extends Fragment {


    Toolbar toolbar;
    TextView title,num;
    ImageView cart;
    RelativeLayout no,yes;
    String deviceToken;
    Button edit,address;
    RelativeLayout pickup;
    FragmentManager fragmentManager;
    Context context;
    ConnectionDetection connectionDetection;

    ImageView back1,back2,back3;
    TextView txt1,txt2,txt3;
    RelativeLayout card1,card2,card3;
    PopupWindow popupWindow;

    String lang;
    Typeface typeface;

    public My_Account() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_my__account, container, false);

        //----------------------tool bar and title--------------//


        final AppCompatActivity activity = (AppCompatActivity) getActivity();

        RelativeLayout bar = activity.findViewById(R.id.bar);
        bar.setVisibility(View.VISIBLE);
        ((MainActivity) activity).select_icon("none");

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ImageButton back=toolbar.findViewById(R.id.back);

        lang= SharedPrefManager.getInstance(activity).getLang();
        if(lang.equals("en")){
            typeface = ResourcesCompat.getFont(activity, R.font.amaranth_bold);
            back.setRotation(180);
        }
        if(lang.equals("ar")){
            typeface= ResourcesCompat.getFont(activity, R.font.bein_black);
        }
        activity.setSupportActionBar(toolbar);
        fragmentManager=activity.getSupportFragmentManager();
        context=getContext();
        connectionDetection=new ConnectionDetection(context);

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
        title.setText(activity.getResources().getString(R.string.myacc));

        back.setVisibility(View.VISIBLE);

        cart.setVisibility(View.VISIBLE);
        ImageView menu=toolbar.findViewById(R.id.menu);
        menu.setVisibility(View.VISIBLE);

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new MyCart();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment).addToBackStack("xyz")
                        .commit();
            }
        });

        txt1=view.findViewById(R.id.txt1);
        txt2=view.findViewById(R.id.txt2);

        txt3=view.findViewById(R.id.txt3);

        back1=view.findViewById(R.id.back1);
        back2=view.findViewById(R.id.back2);
        back3=view.findViewById(R.id.back3);

        card1=view.findViewById(R.id.card1);
        card2=view.findViewById(R.id.card2);
        card3=view.findViewById(R.id.card3);



        txt1.setTypeface(typeface);
        txt2.setTypeface(typeface);
        txt3.setTypeface(typeface);

        if(lang.equals("ar")){
            back1.setRotationY(180);
            back2.setRotationY(180);
            back3.setRotationY(180);
        }

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new Edit_Account();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment).addToBackStack(null)
                        .commit();
            }
        });


        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new Client_address();
                Bundle g=new Bundle();
                g.putString("flag","hh");
                fragment.setArguments(g);
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment).addToBackStack(null)
                        .commit();
            }
        });

        card3.setOnClickListener(new View.OnClickListener() {
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



        return view;
    }



}
