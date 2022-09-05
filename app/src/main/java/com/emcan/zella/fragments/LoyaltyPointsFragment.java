package com.emcan.zella.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.emcan.zella.Api_Service;
import com.emcan.zella.Beans.LoyaltyPointsResponse;
import com.emcan.zella.Config;
import com.emcan.zella.ConnectionDetection;
import com.emcan.zella.R;
import com.emcan.zella.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoyaltyPointsFragment extends Fragment {

    ConnectionDetection connectionDetection;
    Toolbar toolbar;
    TextView title, num;
    ImageView cart;
    String lang;
    Typeface typeface;
    TextView txtMessage,txtPoints;
    RelativeLayout pickup;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.points_fragment, container, false);


        txtMessage=view.findViewById(R.id.txt_message);
        txtPoints=view.findViewById(R.id.txt_points);

        final AppCompatActivity activity = (AppCompatActivity) getActivity();

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        ImageButton back = toolbar.findViewById(R.id.back);
        lang = SharedPrefManager.getInstance(activity).getLang();
        if (lang.equals("en")) {
            typeface = ResourcesCompat.getFont(activity, R.font.amaranth_bold);
            back.setRotation(180);
        }
        if (lang.equals("ar")) {
            typeface = ResourcesCompat.getFont(activity, R.font.bein_black);
        }
        //   title=(TextView)toolbar.getRootView().findViewById(R.id.toolbar_title);
        RelativeLayout no = toolbar.findViewById(R.id.no_cart);
        RelativeLayout yes = toolbar.findViewById(R.id.yes_cart);


        num = toolbar.getRootView().findViewById(R.id.num);
        title=toolbar.getRootView().findViewById(R.id.toolbar_title);

        if (SharedPrefManager.getInstance(getContext()).get_Cart() > 0) {
            cart = toolbar.getRootView().findViewById(R.id.cart);

            no.setVisibility(View.INVISIBLE);
            yes.setVisibility(View.VISIBLE);
            num.setText(String.valueOf(SharedPrefManager.getInstance(getContext()).get_Cart()));

        } else {
            cart = toolbar.getRootView().findViewById(R.id.cart1);
            yes.setVisibility(View.INVISIBLE);
            no.setVisibility(View.VISIBLE);
        }

        title.setTypeface(typeface);
        title.setText(activity.getResources().getString(R.string.loyalty_points));
        cart.setVisibility(View.VISIBLE);
        ImageView menu = toolbar.findViewById(R.id.menu);
        menu.setVisibility(View.VISIBLE);

        back.setVisibility(View.VISIBLE);


        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new MyCart();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment).addToBackStack("xyz")
                        .commit();
            }
        });

        connectionDetection = new ConnectionDetection(getContext());

        //--------------get favourites from server------------//


        if (SharedPrefManager.getInstance(getContext()).isLoggedIn()) {
            if (connectionDetection.isConnected()) {
                Api_Service requestInterface = Config.getClient().create(Api_Service.class);
                Call<LoyaltyPointsResponse> response1 = requestInterface.getPoints(SharedPrefManager.getInstance(getContext()).getUser()
                        .getClient_id(),lang,"1","","");
                response1.enqueue(new Callback<LoyaltyPointsResponse>() {
                    @Override
                    public void onResponse(Call<LoyaltyPointsResponse> call, Response<LoyaltyPointsResponse> response) {
                        LoyaltyPointsResponse resp = response.body();
                        if (resp != null) {
                            if (resp.getSuccess() == 1) {
                                if(resp.getPointsText()!=null) {
                                    txtMessage.setText(resp.getPointsText());
                                }
                                if(resp.getClientPoints()!=null) {
                                    txtPoints.setText(resp.getClientPoints()+" "+
                                            getContext().getResources().getString(R.string.points));
                                }
                            }
                        } else {
                            //  Toast.makeText(getContext(), "خطأ حاول مجددا", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoyaltyPointsResponse> call, Throwable t) {

                        //   Toast.makeText(getContext(), "خطأ حاول مجددا", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(getContext(), activity.getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
            }
        } else {
        }

        return view;
    }


}
