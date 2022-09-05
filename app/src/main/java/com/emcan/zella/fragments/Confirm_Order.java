package com.emcan.zella.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import android.os.Handler;
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
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emcan.zella.Api_Service;
import com.emcan.zella.Beans.Avaliable_Model;
import com.emcan.zella.Beans.Cart_Response2;
import com.emcan.zella.Beans.Check_client;
import com.emcan.zella.Beans.DiscountResponse;
import com.emcan.zella.Beans.Last_order_pojo;
import com.emcan.zella.Beans.LoyaltyPointsResponse;
import com.emcan.zella.Beans.Order_respose;
import com.emcan.zella.Beans.PaymentResponse;
import com.emcan.zella.Beans.Paymentt;
import com.emcan.zella.Beans.Sub_Cat_Images_Model;
import com.emcan.zella.Config;
import com.emcan.zella.ConnectionDetection;
import com.emcan.zella.R;
import com.emcan.zella.Request_manger;
import com.emcan.zella.SharedPrefManager;
import com.emcan.zella.activities.MainActivity;
import com.emcan.zella.activities.Payment;
import com.emcan.zella.activities.Payment2;
import com.emcan.zella.adapters.Last_order_adapter;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


public class Confirm_Order extends Fragment {

    TextView txt1, txt2, txt3, txt33, txt6;
    TextView region,flat, note;
    ConnectionDetection connectionDetection;
    public ArrayList<Cart_Response2.CartModel2> cart1;
    String address_id;
    TextView title,cash_txt,titlee,debit_txt,credit_txt,coin,a,ad,p,po,txt222;
    ImageView cart;
    public String id_cart;
    PopupWindow popupWindow;
    RelativeLayout pickup;

    View view;
    public ProgressBar progressBar;
    public RecyclerView recyclerView;
    Button confirm_order;
    String deliver_id;
    RelativeLayout address;
    public String lang;
    Typeface typeface;
    AppCompatActivity activity;
    String branch_name, branch_id;

    Last_order_pojo.last order;
    String order_id, reorder;
    double discount;

    ImageButton back;
    Toolbar toolbar;
    Context context;
    String points;
    boolean use_points,clicked,applied;

    RadioButton cash, credit, debit;
    String payment_type = "",loyalty="0";

    ImageView icon1,icon2,icon3,icon4,icon5;
    Button apply;
    EditText edit;
    public RelativeLayout sep,rel1,rel2,rel3,rel5,rel4;
    public TextView txt,txt11,txt22,vat,vat_value,total_,discountt,total_txt,charge_txt,j,kk,k,jj,jjj,chargee;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_confirm__order, container, false);

        activity = (AppCompatActivity) getActivity();

        coin=view.findViewById(R.id.coin);

        j=view.findViewById(R.id.j);
        kk=view.findViewById(R.id.kk);
        k=view.findViewById(R.id.k);
        jj=view.findViewById(R.id.jj);
        jjj=view.findViewById(R.id.jjj);
        chargee=view.findViewById(R.id.chargee);



//        Log.d("chargeeee",getArguments().getString("region")+"   "+getArguments().getString("block")+"   "+
//                getArguments().getString("road")+"   "+getArguments().getString("building")+"  "+address_id+"   ");

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        back = toolbar.findViewById(R.id.back);


        lang = SharedPrefManager.getInstance(activity).getLang();
        if (lang.equals("en")) {
            typeface = ResourcesCompat.getFont(activity, R.font.amaranth_bold);
            back.setRotation(180);
        }
        if (lang.equals("ar")) {
            typeface = ResourcesCompat.getFont(activity, R.font.bein_black);
        }

        icon1= view.findViewById(R.id.icon1);
        icon2= view.findViewById(R.id.icon2);
        icon3= view.findViewById(R.id.icon3);
        icon4= view.findViewById(R.id.icon4);
        icon5= view.findViewById(R.id.icon5);
        p= view.findViewById(R.id.p);
        setToolbar();
        init();

            if (getArguments() != null) {
                if (getArguments().get("deliver_type") != null) {
                    deliver_id = getArguments().getString("deliver_type");


                    if (getArguments().getString("deliver_type").equals("1")) {

                        region.setText(getResources().getString(R.string.region)+" "+getArguments().getString("region")+"" +
                                " , "+
                                getResources().getString(R.string.block)+" "+  getArguments().getString("block")+" , "+
                                getResources().getString(R.string.road)+" "+     getArguments().getString("road") +" , " +
                                getResources().getString(R.string.building_number)+" "+getArguments().getString("building")
                             );

                        address_id = getArguments().getString("address_id");



                        if (getArguments().getString("flat") != null && !getArguments().getString("flat").equals("")) {
                            region.setText(getResources().getString(R.string.region)+" "+getArguments().getString("region")+"" +
                                    " , "+
                                    getResources().getString(R.string.block)+" "+  getArguments().getString("block")+" , "+
                                    getResources().getString(R.string.road)+" "+     getArguments().getString("road") +" , " +
                                    getResources().getString(R.string.building_number)+" "+getArguments().getString("building")
                                            + " , "+
                                    getResources().getString(R.string.flat)+" "+getArguments().getString("flat"));
                        }
                        if (getArguments().getString("note") != null && !getArguments().getString("note").equals("")) {
                            note.setVisibility(View.VISIBLE);
                            txt6.setVisibility(View.VISIBLE);
                            note.setText(getArguments().getString("note"));
                        }
                    }
                    if (getArguments().getString("deliver_type").equals("2")) {

                        branch_name = getArguments().getString("branch_name");
                        branch_id = getArguments().getString("branch_id");
                        address_id = "";
                        address.setVisibility(View.GONE);
                    }

                    if (getArguments().getString("deliver_type").equals("3")) {
                        branch_name = getArguments().getString("branch_name");
                        branch_id = getArguments().getString("branch_id");

                        address_id = "";
                        address.setVisibility(View.GONE);
                    }
                }

                if (getArguments() != null && getArguments().getString("reorder") != null) {
                    reorder = getArguments().getString("reorder");
                    order_id = getArguments().getString("order_id");
                    discount = getArguments().getDouble("discount");
                    order = (Last_order_pojo.last) getArguments().getSerializable("order");
                }
            }


            if (connectionDetection.isConnected()) {
                Request_manger.getcart(context, this, view);

            } else {

                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), activity.getResources().getString(R.string.networkerror),
                        Toast.LENGTH_SHORT).show();

        }
        getFullCart();

        confirm_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {


                if (!payment_type.equals("")) {

                    check_user();
                    confirm_order.setClickable(false);
                    confirm_order.setEnabled(false);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(getActivity()!=null&&!getActivity().isFinishing()) {
                                confirm_order.setClickable(true);
                                confirm_order.setEnabled(true);
                            }
                        }
                    },3000);
                } else {
                    Toast.makeText(context, "من فضلك اختر طريقة الدفع ", Toast.LENGTH_SHORT).show();
                }


            }
        });


        cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payment_type = "cash";
                debit.setChecked(false);
                credit.setChecked(false);
            }
        });

        debit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payment_type = "debit";

                cash.setChecked(false);
                credit.setChecked(false);
            }
        });

        credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payment_type = "credit";

                debit.setChecked(false);
                cash.setChecked(false);
            }
        });

        getCart();
        get_delivery();

        apply=view.findViewById(R.id.apply);
        edit=view.findViewById(R.id.edit);
        apply.setTypeface(typeface);
        edit.setTypeface(typeface);

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edit.getText().toString().trim().length() > 0) {
                    checkCoupon();
                } else {
                    Toast.makeText(activity, getResources().getString(R.string.pleaseaddcopoun), Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void checkCoupon() {
        if (connectionDetection.isConnected()) {
            Api_Service requestInterface = Config.getClient().create(Api_Service.class);

            requestInterface.checkCoupon(edit.getText().toString().trim(),SharedPrefManager.getInstance(activity).getUser().getClient_id()
                    ,loyalty,lang,address_id).enqueue(new Callback<DiscountResponse>() {
                @Override
                public void onResponse(Call<DiscountResponse> call, Response<DiscountResponse> response) {
                    DiscountResponse resp = response.body();
                    if (resp != null) {
                        if (resp.getSuccess() == 1) {
                            applied=true;
                            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(LAYOUT_INFLATER_SERVICE);
                            View customView = inflater.inflate(R.layout.message_popup, null);
                            popupWindow = new PopupWindow(
                                    customView,
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    //   700,
                                    LinearLayout.LayoutParams.MATCH_PARENT, true
                            );

                            Button ok = customView.findViewById(R.id.ok);
                            TextView text = customView.findViewById(R.id.text);
                            text.setText(resp.getMessage());

                            ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(final View view) {
                                    popupWindow.dismiss();
                                }
                            });
                            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                            if(resp.getDiscountPercentage()>0){
                                rel1.setVisibility(View.VISIBLE);
                                rel2.setVisibility(View.VISIBLE);
                                rel3.setVisibility(View.VISIBLE);
                                rel5.setVisibility(View.VISIBLE);
                                sep.setVisibility(View.VISIBLE);

                                total_txt.setText(resp.getTotalAmount());
                                total_.setText(resp.getTotalAmountAfterDisc());
                                if(resp.getCharge()!=null&&resp.getCharge().length()>0) {

                                    discountt.setText(resp.getSummary());
                                }
                                else{
                                    discountt.setText(resp.getSummary());
                                }
                                txt11.setText(getResources().getString(R.string.discountt)+" ("+resp.getDiscountCodePercentage()+"%) : ");
                                vat.setText(getResources().getString(R.string.vat)+" ("+resp.getVat()+"%) : ");
                                vat_value.setText(resp.getVatValue());

                                if(resp.getCharge()!=null&&resp.getCharge().length()>0&&!resp.getCharge().equals("0.000")){
                                    rel4.setVisibility(View.VISIBLE);
                                    charge_txt.setText(resp.getCharge());
                                }
                                else{
                                    rel4.setVisibility(View.GONE);
                                }

                                if(deliver_id!=null&&deliver_id.equals("2")){
                                    rel4.setVisibility(View.GONE);
                                }

                            }else {
                                rel1.setVisibility(View.VISIBLE);
                                rel2.setVisibility(View.GONE);
                                rel3.setVisibility(View.VISIBLE);
                                rel5.setVisibility(View.VISIBLE);
                                sep.setVisibility(View.VISIBLE);

                                total_txt.setText(resp.getTotalAmount());
                                total_.setText(resp.getTotalAmountAfterDisc());
                                txt11.setText(getResources().getString(R.string.discountt)+" ("+resp.getDiscountCodePercentage()+"%) : ");
                                vat.setText(getResources().getString(R.string.vat)+" ("+resp.getVat()+"%) : ");
                                vat_value.setText(resp.getVatValue());
                                if(resp.getCharge()!=null&&resp.getCharge().length()>0) {
                                    discountt.setText(resp.getSummary());
                                }
                                else{
                                    discountt.setText(resp.getSummary());
                                }

                                if(resp.getCharge()!=null&&resp.getCharge().length()>0&&!resp.getCharge().equals("0.000")){
                                    rel4.setVisibility(View.VISIBLE);
                                    charge_txt.setText(resp.getCharge());
                                }
                                else{
                                    rel4.setVisibility(View.GONE);
                                }

                                if(deliver_id!=null&&deliver_id.equals("2")){
                                    rel4.setVisibility(View.GONE);
                                }
                            }

                        } else {
                            edit.getText().clear();
                            getFullCart();
                            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(LAYOUT_INFLATER_SERVICE);
                            View customView = inflater.inflate(R.layout.message_popup, null);
                            popupWindow = new PopupWindow(
                                    customView,
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    //   700,
                                    LinearLayout.LayoutParams.MATCH_PARENT, true
                            );

                            Button ok = customView.findViewById(R.id.ok);
                            TextView text = customView.findViewById(R.id.text);
                            text.setText(resp.getMessage());

                            ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(final View view) {
                                    popupWindow.dismiss();
                                }
                            });
                            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);


                        }
                    }
                }
                @Override
                public void onFailure(Call<DiscountResponse> call, Throwable t) {
                    Toast.makeText(activity, R.string.errortryagain, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, R.string.networkerror, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void do_payment(String total, String type) {
        order_online(total,type);
    }



    //----------الحد الادني
    private void check() {
        Api_Service requestInterface = Config.getClient().create(Api_Service.class);

        Call<Sub_Cat_Images_Model> response1 = requestInterface.check_order(SharedPrefManager.
                        getInstance(getContext()).getUser().getClient_id(),
                address_id, id_cart, lang,"");
        response1.enqueue(new Callback<Sub_Cat_Images_Model>() {
            @Override
            public void onResponse(Call<Sub_Cat_Images_Model> call, retrofit2.Response<Sub_Cat_Images_Model> response) {
                Sub_Cat_Images_Model resp = response.body();
                if (resp != null) {
                    Log.d("check_order",resp.getSuccess()+"");
                    if (resp.getSuccess() == 1) {

                        confirm_order.setVisibility(View.VISIBLE);
                        if (payment_type.equals("cash")) {
                            order_cash();
                        } else {
                            LayoutInflater inflater = (LayoutInflater) getContext().
                                    getSystemService(LAYOUT_INFLATER_SERVICE);

                            View customView;
                            if(lang.equals("ar")) {
                                customView = inflater.inflate(R.layout.terms_conditions_layout, null);
                            }else{

                                customView = inflater.inflate(R.layout.terms_conditions_english, null);
                            }

                            popupWindow = new PopupWindow(
                                    customView,
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    //   700,
                                    LinearLayout.LayoutParams.MATCH_PARENT, true
                            );

                            Button confirm = customView.findViewById(R.id.confirm);
                            Button cancel = customView.findViewById(R.id.cancel);
                            TextView txt = customView.findViewById(R.id.txt);
                            TextView txt1 = customView.findViewById(R.id.txt1);
                            TextView total_price = customView.findViewById(R.id.total_price);
                            TextView coin=customView.findViewById(R.id.coin);


                            if(Float.parseFloat(cart1.get(cart1.size() - 1).getDiscount_percentage())>0) {
                                total_price.setText(discountt.getText().toString());
                            }else{
                                total_price.setText(discountt.getText().toString());

                            }

                            txt1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    popupWindow.dismiss();

                                    if (payment_type.equals("credit")) {
                                        Fragment fragment = Terms_Conditions.newInstance("2");

                                        FragmentManager fragmentManager = activity.getSupportFragmentManager();
                                        fragmentManager.beginTransaction()
                                                .replace(R.id.container, fragment).addToBackStack(null)
                                                .commit();
                                    } else {
                                        Fragment fragment = Terms_Conditions.newInstance("1");

                                        FragmentManager fragmentManager = activity.getSupportFragmentManager();
                                        fragmentManager.beginTransaction()
                                                .replace(R.id.container, fragment).addToBackStack(null)
                                                .commit();
                                    }
                                }
                            });


                            confirm.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (payment_type.equals("credit")) {
                                        popupWindow.dismiss();


                                            order_online_debit("credit");


                                    } else {
                                        popupWindow.dismiss();


                                            order_online_debit("debit");


                                    }
                                }
                            });


                            cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    popupWindow.dismiss();
                                }
                            });
                            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                        }


                    } else {
                        LayoutInflater inflater = (LayoutInflater) getContext().
                                getSystemService(LAYOUT_INFLATER_SERVICE);
                        View customView = inflater.inflate(R.layout.message_popup, null);
                        popupWindow = new PopupWindow(
                                customView,
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                //   700,
                                LinearLayout.LayoutParams.MATCH_PARENT, true
                        );

                        Button no1 = customView.findViewById(R.id.ok);
                        TextView text = customView.findViewById(R.id.text);
                        text.setText(resp.getMessage());
                        no1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                popupWindow.dismiss();
                            }
                        });
                        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                    }
                }
            }


            @Override
            public void onFailure(Call<Sub_Cat_Images_Model> call, Throwable t) {
                Toast.makeText(getContext(), activity.getResources().getString(R.string.errortryagain),
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void order_online_debit(String type) {
        String tot;

        if (deliver_id.equals("1")) {

                    tot = String.format(Locale.ENGLISH, "%.2f", Float.parseFloat(
                            discountt.getText().toString()));
            do_payment(tot, type);


        }

    }

    private void setToolbar() {

        RelativeLayout no = toolbar.findViewById(R.id.no_cart);
        RelativeLayout yes = toolbar.findViewById(R.id.yes_cart);


        title = toolbar.getRootView().findViewById(R.id.toolbar_title);
        cart = toolbar.getRootView().findViewById(R.id.cart1);
        yes.setVisibility(View.INVISIBLE);
        no.setVisibility(View.VISIBLE);


        cart.setVisibility(View.INVISIBLE);

        title.setTypeface(typeface);


        title.setText(activity.getResources().getString(R.string.confirmorder));
        ImageView menu = toolbar.findViewById(R.id.menu);
        menu.setVisibility(View.INVISIBLE);

        back.setVisibility(View.VISIBLE);
    }

    private void init() {
        context = getContext();
        connectionDetection = new ConnectionDetection(context);
        progressBar = view.findViewById(R.id.progressBar);
        recyclerView = view.findViewById(R.id.recycler_cart);
        confirm_order = view.findViewById(R.id.continu);
        confirm_order.setTypeface(typeface);
        debit_txt=view.findViewById(R.id.debit_txt);
        credit_txt=view.findViewById(R.id.credit_txt);
        debit_txt.setTypeface(typeface);
        credit_txt.setTypeface(typeface);

        rel1= view.findViewById(R.id.rel1);
        rel2= view.findViewById(R.id.rel2);
        rel3= view.findViewById(R.id.rel3);
        rel5=view.findViewById(R.id.rel5);
        rel4=view.findViewById(R.id.rel4);

        sep= view.findViewById(R.id.sep);
        vat=view.findViewById(R.id.vat);
        vat_value=view.findViewById(R.id.vat_value);
        txt=(TextView) view.findViewById(R.id.txt);
        txt11=(TextView) view.findViewById(R.id.txt11);
        txt22=(TextView) view.findViewById(R.id.txt22);
        total_txt=(TextView) view.findViewById(R.id.total_txt);
        total_=(TextView) view.findViewById(R.id.total_);
        discountt=(TextView) view.findViewById(R.id.discount);
        charge_txt=view.findViewById(R.id.charge);
        txt222=view.findViewById(R.id.txt222);

        txt.setTypeface(typeface);
        txt11.setTypeface(typeface);
        txt22.setTypeface(typeface);
        vat.setTypeface(typeface);
        txt222.setTypeface(typeface);


        debit = view.findViewById(R.id.debit);
        cash = view.findViewById(R.id.cash);
        credit = view.findViewById(R.id.credit);

        txt6 = view.findViewById(R.id.txt6);
        address = view.findViewById(R.id.address);
        cash_txt=view.findViewById(R.id.cash_txt);
        cash_txt.setTypeface(typeface);
        titlee=view.findViewById(R.id.title);
        titlee.setTypeface(typeface);

        region = view.findViewById(R.id.region);

        note = view.findViewById(R.id.note);
        txt1 = view.findViewById(R.id.txt1);
        txt2 = view.findViewById(R.id.txt2);
        txt3 = view.findViewById(R.id.txt3);
        txt33 = view.findViewById(R.id.txt33);

        txt6.setTypeface(typeface);
        region.setTypeface(typeface);
        note.setTypeface(typeface);
        txt33.setTypeface(typeface);

    }

    public void fire_alam(String message) {
        LayoutInflater inflater = (LayoutInflater) getContext().
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.message_popup, null);
        popupWindow = new PopupWindow(
                customView,
                LinearLayout.LayoutParams.MATCH_PARENT,
                //   700,
                LinearLayout.LayoutParams.MATCH_PARENT, true
        );

        Button no1 = customView.findViewById(R.id.ok);
        TextView text = customView.findViewById(R.id.text);
        text.setText(message);
        no1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }


    public void send_order() {

        if (connectionDetection.isConnected()) {
            progressBar.setVisibility(View.VISIBLE);
            Api_Service requestInterface = Config.getClient().create(Api_Service.class);


            Order_respose.Order_model order = new Order_respose.Order_model();
            order.setOrder_id(order_id);
            order.setClient_id(SharedPrefManager.
                    getInstance(getContext()).getUser().getClient_id());
            order.setPayment("cash");
            order.setClient_address_id(address_id);
            order.setDeliver_id(deliver_id);
            if (deliver_id.equals("1")) {
                order.setBranch_id("");
            } else {
                order.setBranch_id(branch_id);

            }

            Call<Last_order_pojo> response1 = requestInterface.re_Order(order);
            response1.enqueue(new Callback<Last_order_pojo>() {
                @Override
                public void onResponse(Call<Last_order_pojo> call, retrofit2.Response<Last_order_pojo> response) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Last_order_pojo resp = response.body();
                    if (resp != null) {
                        if (resp.getSuccess() == 1) {
                            SharedPrefManager.getInstance(getContext()).reset_Cart();

                            Intent intent = new Intent(getContext(), MainActivity.class);
                            intent.putExtra("type", "order_");
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent);


                        } else {
                            LayoutInflater inflater = (LayoutInflater) getContext().
                                    getSystemService(LAYOUT_INFLATER_SERVICE);
                            View customView = inflater.inflate(R.layout.message_popup, null);
                            popupWindow = new PopupWindow(
                                    customView,
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    //   700,
                                    LinearLayout.LayoutParams.MATCH_PARENT, true
                            );

                            RelativeLayout out = customView.findViewById(R.id.out);
                            out.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    popupWindow.dismiss();
                                }
                            });
                            Button no1 = customView.findViewById(R.id.ok);
                            TextView text = customView.findViewById(R.id.text);
                            text.setText(resp.getMessage());
                            no1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    popupWindow.dismiss();
                                }
                            });
                            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                        }
                    }
                }


                @Override
                public void onFailure(Call<Last_order_pojo> call, Throwable t) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(context, activity.getResources().getString(R.string.errortryagain),
                            Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(context, activity.getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();

        }
    }

    private void check_user() {
        progressBar.setVisibility(View.VISIBLE);

        Api_Service requestInterface = Config.getClient().create(Api_Service.class);

        Call<Check_client> response1 = requestInterface.check_client(
                SharedPrefManager.getInstance(context).getUser().getClient_id());
        response1.enqueue(new Callback<Check_client>() {
            @Override
            public void onResponse(Call<Check_client> call, retrofit2.Response<Check_client> response) {
                Check_client resp = response.body();
                progressBar.setVisibility(View.INVISIBLE);
                if (resp != null) {

                    if (resp.getSuccess() == 1) {


                        if (resp.getProduct().get(0).getExist() == 0) {

                            SharedPrefManager.getInstance(context).logout();
                            Intent intent = new Intent(context, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            activity.startActivity(intent);

                        } else {

                            if(clicked){
                                check_avaliability();
                                Log.d("cliekced",clicked+"");
                            }
                            else{
                                checkPoints();
                                Log.d("cliekcedoo",clicked+"");
                            }
                        }
                    } else {
//                        Toast.makeText(getContext(),"error",Toast.LENGTH_SHORT).show();
                    }
                }
            }


            @Override
            public void onFailure(Call<Check_client> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
//                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void check_avaliability() {

        if (connectionDetection.isConnected()) {
            Api_Service requestInterface = Config.getClient().create(Api_Service.class);

            Call<Avaliable_Model> response1 = requestInterface.aval();
            response1.enqueue(new Callback<Avaliable_Model>() {
                @Override
                public void onResponse(Call<Avaliable_Model> call, retrofit2.Response<Avaliable_Model> response) {
                    Avaliable_Model resp = response.body();
                    if (resp != null) {
                        if (resp.getSuccess() == 1) {
                            if (resp.getProduct().get(0).getAccept_orders().equals("1")) {

                                if (Float.parseFloat(resp.getProduct().get(0).getDiscount_percentage()) > 0) {
                                    discount = Double.parseDouble(resp.getProduct().get(0).getDiscount_percentage());
                                }

                                check_order_exist2();

                            } else {
                                LayoutInflater inflater = (LayoutInflater) getContext().
                                        getSystemService(LAYOUT_INFLATER_SERVICE);
                                View customView = inflater.inflate(R.layout.message_popup, null);
                                popupWindow = new PopupWindow(
                                        customView,
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        //   700,
                                        LinearLayout.LayoutParams.MATCH_PARENT, true
                                );

                                Button no1 = customView.findViewById(R.id.ok);
                                TextView text = customView.findViewById(R.id.text);
                                text.setText(activity.getResources().getString(R.string.cantorder));
                                no1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        popupWindow.dismiss();
                                    }
                                });
                                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                            }
                        }
                    }
                }


                @Override
                public void onFailure(Call<Avaliable_Model> call, Throwable t) {
                    Toast.makeText(getContext(), activity.getResources().getString(R.string.errortryagain),
                            Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getContext(), activity.getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
        }
    }

    private void check_order_exist2() {

        if (connectionDetection.isConnected()) {
            Api_Service requestInterface = Config.getClient().create(Api_Service.class);
            Log.d("jjjjjjjjjj", id_cart+" "+address_id+" "+branch_id);
            if(branch_id==null||branch_id.equals("")){
                Call<Avaliable_Model> response1 = requestInterface.check_order_exist22(id_cart, lang,address_id,
                        SharedPrefManager.getInstance(activity).getMobileVersion(),"android");
                response1.enqueue(new Callback<Avaliable_Model>() {
                    @Override
                    public void onResponse(Call<Avaliable_Model> call, retrofit2.Response<Avaliable_Model> response) {
                        Avaliable_Model resp = response.body();
                        //Log.d("jjjjj",resp.getSuccess()+"");
                        if (resp != null) {

                            if (resp.getNot_exist()!=null&&resp.getNot_exist().equals("0")) {

                                if (deliver_id.equals("1")) {

                                    check();
                                } else {

                                    if (payment_type.equals("cash")) {
                                        order_cash();
                                    } else {
                                        LayoutInflater inflater = (LayoutInflater) getContext().
                                                getSystemService(LAYOUT_INFLATER_SERVICE);
                                        View customView;
                                        if(lang.equals("ar")) {
                                            customView = inflater.inflate(R.layout.terms_conditions_layout, null);
                                        }else{

                                            customView = inflater.inflate(R.layout.terms_conditions_english, null);
                                        }
                                        popupWindow = new PopupWindow(
                                                customView,
                                                LinearLayout.LayoutParams.MATCH_PARENT,
                                                //   700,
                                                LinearLayout.LayoutParams.MATCH_PARENT, true
                                        );

                                        Button confirm = customView.findViewById(R.id.confirm);
                                        Button cancel = customView.findViewById(R.id.cancel);
                                        TextView txt = customView.findViewById(R.id.txt);
                                        TextView coin=customView.findViewById(R.id.coin);

                                        TextView total_price = customView.findViewById(R.id.total_price);
                                        confirm.setTypeface(typeface);
                                        txt.setTypeface(typeface);
                                        cancel.setTypeface(typeface);
                                        total_price.setTypeface(typeface);

                                        if (Float.parseFloat(cart1.get(cart1.size() - 1).getDiscount_percentage()) > 0) {
                                            total_price.setText(discountt.getText().toString());
                                        } else {
                                            total_price.setText(discountt.getText().toString());

                                        }

                                        TextView txt1 = customView.findViewById(R.id.txt1);
                                        txt1.setTypeface(typeface);
                                        txt1.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                popupWindow.dismiss();

                                                if (payment_type.equals("credit")) {
                                                    Fragment fragment = Terms_Conditions.newInstance("2");

                                                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                                                    fragmentManager.beginTransaction()
                                                            .replace(R.id.container, fragment).addToBackStack(null)
                                                            .commit();
                                                } else {
                                               /* Fragment fragment = Terms_Conditions.newInstance("1");

                                                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                                                fragmentManager.beginTransaction()
                                                        .replace(R.id.container, fragment).addToBackStack(null)
                                                        .commit();*/
                                                }
                                            }
                                        });


                                        confirm.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                if (payment_type.equals("credit")) {
                                                    popupWindow.dismiss();


                                                    order_online_debit("credit");

                                                } else {
                                                    popupWindow.dismiss();


                                                    order_online_debit("debit");

                                                }
                                            }
                                        });


                                        cancel.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                popupWindow.dismiss();
                                            }
                                        });
                                        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                                    }

                                }


                            } else {
                                LayoutInflater inflater = (LayoutInflater) getContext().
                                        getSystemService(LAYOUT_INFLATER_SERVICE);
                                View customView = inflater.inflate(R.layout.message_popup, null);
                                popupWindow = new PopupWindow(
                                        customView,
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        //   700,
                                        LinearLayout.LayoutParams.MATCH_PARENT, true
                                );

                                Button no1 = customView.findViewById(R.id.ok);
                                TextView text = customView.findViewById(R.id.text);
                                text.setText(resp.getMessage());
                                no1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        popupWindow.dismiss();
                                    }
                                });
                                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Avaliable_Model> call, Throwable t) {
                        Toast.makeText(getContext(), activity.getResources().getString(R.string.errortryagain),
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else if(address_id==null||address_id.equals("")){
                Call<Avaliable_Model> response1 = requestInterface.check_order_exist2(id_cart,
                        lang,branch_id,SharedPrefManager.getInstance(activity).getMobileVersion(),"android");
                Log.d("kkkkkkkk", id_cart+" "+branch_id +"/ "+SharedPrefManager.getInstance(activity).getMobileVersion());
                response1.enqueue(new Callback<Avaliable_Model>() {
                    @Override
                    public void onResponse(Call<Avaliable_Model> call, retrofit2.Response<Avaliable_Model> response) {
                        Avaliable_Model resp = response.body();
                        //Log.d("kkkk",resp.getSuccess()+"");
                        if (resp != null) {

                            if (resp.getNot_exist().equals("0")) {

                                if (deliver_id.equals("1")) {

                                    check();
                                } else {

                                    if (payment_type.equals("cash")) {
                                        order_cash();
                                    } else {
                                        LayoutInflater inflater = (LayoutInflater) getContext().
                                                getSystemService(LAYOUT_INFLATER_SERVICE);
                                        View customView;
                                        if(lang.equals("ar")) {
                                            customView = inflater.inflate(R.layout.terms_conditions_layout, null);
                                        }else{

                                            customView = inflater.inflate(R.layout.terms_conditions_english, null);
                                        }
                                        popupWindow = new PopupWindow(
                                                customView,
                                                LinearLayout.LayoutParams.MATCH_PARENT,
                                                //   700,
                                                LinearLayout.LayoutParams.MATCH_PARENT, true
                                        );

                                        Button confirm = customView.findViewById(R.id.confirm);
                                        Button cancel = customView.findViewById(R.id.cancel);
                                        TextView txt = customView.findViewById(R.id.txt);
                                        TextView total_price = customView.findViewById(R.id.total_price);
                                        TextView coin=customView.findViewById(R.id.coin);

                                        confirm.setTypeface(typeface);
                                        txt.setTypeface(typeface);
                                        cancel.setTypeface(typeface);
                                        total_price.setTypeface(typeface);

                                        if (Float.parseFloat(cart1.get(cart1.size() - 1).getDiscount_percentage()) > 0) {
                                            total_price.setText(discountt.getText().toString());
                                        } else {
                                            total_price.setText(discountt.getText().toString());

                                        }

                                        TextView txt1 = customView.findViewById(R.id.txt1);
                                        txt1.setTypeface(typeface);
                                        txt1.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                popupWindow.dismiss();

                                                if (payment_type.equals("credit")) {
                                                    Fragment fragment = Terms_Conditions.newInstance("2");

                                                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                                                    fragmentManager.beginTransaction()
                                                            .replace(R.id.container, fragment).addToBackStack(null)
                                                            .commit();
                                                } else {
                                               /* Fragment fragment = Terms_Conditions.newInstance("1");

                                                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                                                fragmentManager.beginTransaction()
                                                        .replace(R.id.container, fragment).addToBackStack(null)
                                                        .commit();*/
                                                }
                                            }
                                        });


                                        confirm.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                if (payment_type.equals("credit")) {
                                                    popupWindow.dismiss();


                                                    order_online_debit("credit");

                                                } else {
                                                    popupWindow.dismiss();


                                                    order_online_debit("debit");

                                                }
                                            }
                                        });


                                        cancel.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                popupWindow.dismiss();
                                            }
                                        });
                                        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                                    }

                                }


                            } else {
                                LayoutInflater inflater = (LayoutInflater) activity.
                                        getSystemService(LAYOUT_INFLATER_SERVICE);
                                View customView = inflater.inflate(R.layout.message_popup, null);
                                popupWindow = new PopupWindow(
                                        customView,
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        //   700,
                                        LinearLayout.LayoutParams.MATCH_PARENT, true
                                );

                                Button no1 = customView.findViewById(R.id.ok);
                                TextView text = customView.findViewById(R.id.text);
                                text.setText(resp.getMessage());
                                no1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        popupWindow.dismiss();
                                    }
                                });
                                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Avaliable_Model> call, Throwable t) {
                        Toast.makeText(getContext(), activity.getResources().getString(R.string.errortryagain),
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else {
            Toast.makeText(getContext(), activity.getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
        }
    }



    private void order_cash() {

        if (connectionDetection.isConnected()) {

            if (deliver_id.equals("1")) {

                    order_cash_method();

            } else {
                if (reorder != null) {
                    send_order();
                } else {

                    Api_Service requestInterface = Config.getClient().create(Api_Service.class);

                    Order_respose.Order_model order = new Order_respose.Order_model();
                    order.setCart_id(id_cart);
                    order.setClient_id(SharedPrefManager.
                            getInstance(activity).getUser().getClient_id());
                    order.setPayment("cash");
                    order.setClient_address_id("");
                    order.setDeliver_id(deliver_id);
                    order.setBranch_id(branch_id);
                    order.setLang(lang);
                    order.setDevice_type("android");
                    if(use_points){
                        order.setUse_points("1");
                    }
                    else{
                        order.setUse_points("0");
                    }
                    if(applied){
                        order.setDiscount_code(edit.getText().toString().trim());
                    }
                    else{
                        order.setDiscount_code("");
                    }
                    order.setIs_paid("1");
                    order.setMobile_version(SharedPrefManager.getInstance(context).getMobileVersion());

                    Call<Order_respose> response1 = requestInterface.confirm_order2(order);
                    response1.enqueue(new Callback<Order_respose>() {
                        @Override
                        public void onResponse(Call<Order_respose> call, retrofit2.Response<Order_respose> response) {
                            Order_respose resp = response.body();
                            if (resp != null) {
                                if (resp.getSuccess() == 1) {

                                    Toast.makeText(activity, activity.getResources().getString(
                                            R.string.orderconfirmed), Toast.LENGTH_SHORT).show();
                                    SharedPrefManager.getInstance(activity).reset_Cart();

                                    Intent intent = new Intent(activity, MainActivity.class);
                                    intent.putExtra("type", "order_");
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    startActivity(intent);


                                } else {

                                    LayoutInflater inflater = (LayoutInflater) getContext().
                                            getSystemService(LAYOUT_INFLATER_SERVICE);
                                    View customView = inflater.inflate(R.layout.message_popup, null);
                                    popupWindow = new PopupWindow(
                                            customView,
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            //   700,
                                            LinearLayout.LayoutParams.MATCH_PARENT, true
                                    );

                                    RelativeLayout out = customView.findViewById(R.id.out);
                                    out.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            popupWindow.dismiss();
                                        }
                                    });
                                    Button no1 = customView.findViewById(R.id.ok);
                                    TextView text = customView.findViewById(R.id.text);
                                    text.setText(resp.getMessage());
                                    no1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            popupWindow.dismiss();
                                        }
                                    });
                                    popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Order_respose> call, Throwable t) {
                            Toast.makeText(getContext(), activity.getResources().
                                    getString(R.string.errortryagain), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

        } else

        {
            Toast.makeText(getContext(), activity.getResources().
                    getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
        }
    }

    private void order_cash_method(){
        if (reorder != null) {
            send_order();
        } else {

            Api_Service requestInterface = Config.getClient().create(Api_Service.class);

            Order_respose.Order_model order = new Order_respose.Order_model();
            order.setCart_id(id_cart);
            order.setClient_id(SharedPrefManager.
                    getInstance(getContext()).getUser().getClient_id());
            order.setPayment("cash");
            order.setClient_address_id(address_id);
            order.setDeliver_id("1");
            order.setLang(lang);
            order.setDevice_type("android");
            if(use_points){
                order.setUse_points("1");
            }
            else{
                order.setUse_points("0");
            }
            order.setMobile_version(SharedPrefManager.getInstance(activity).getMobileVersion());
            if(applied){
                order.setDiscount_code(edit.getText().toString().trim());
            }
            else{
                order.setDiscount_code("");
            }
            order.setIs_paid("1");


            Call<Order_respose> response1 = requestInterface.confirm_order2(order);
            response1.enqueue(new Callback<Order_respose>() {
                @Override
                public void onResponse(Call<Order_respose> call, retrofit2.Response<Order_respose>
                        response) {
                    Order_respose resp = response.body();
                    if (resp != null) {
                        if (resp.getSuccess() == 1) {

                            Toast.makeText(activity, activity.getResources().
                                    getString(R.string.orderconfirmed), Toast.LENGTH_SHORT).show();
                            SharedPrefManager.getInstance(activity).reset_Cart();

                            Intent intent = new Intent(activity, MainActivity.class);
                            intent.putExtra("type", "order_");
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent);


                        } else {

                            LayoutInflater inflater = (LayoutInflater) getContext().
                                    getSystemService(LAYOUT_INFLATER_SERVICE);
                            View customView = inflater.inflate(R.layout.message_popup, null);
                            popupWindow = new PopupWindow(
                                    customView,
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    //   700,
                                    LinearLayout.LayoutParams.MATCH_PARENT, true
                            );

                            RelativeLayout out = customView.findViewById(R.id.out);
                            out.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    popupWindow.dismiss();
                                }
                            });
                            Button no1 = customView.findViewById(R.id.ok);
                            TextView text = customView.findViewById(R.id.text);
                            text.setText(resp.getMessage());
                            no1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    popupWindow.dismiss();
                                }
                            });
                            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                        }
                    }
                }

                @Override
                public void onFailure(Call<Order_respose> call, Throwable t) {
                    Toast.makeText(activity, activity.getResources().
                            getString(R.string.errortryagain), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    private void get_delivery() {

        Api_Service requestInterface = Config.getClient().create(Api_Service.class);
        Call<PaymentResponse> response1 = requestInterface.getPaymentMethod(lang,"");
        response1.enqueue(new Callback<PaymentResponse>() {
            @Override
            public void onResponse(Call<PaymentResponse> call, Response<PaymentResponse> response) {
                PaymentResponse resp = response.body();
                if (resp != null) {
                    if (resp.getSuccess() == 1) {

                        ArrayList<Paymentt> product = resp.getProduct();
                        cash.setText(product.get(0).getName());
                        debit.setText(product.get(1).getName());
                        credit.setText(product.get(2).getName());

                        if(product.get(0).getDisplay().equals("1")){
                            cash.setVisibility(View.VISIBLE);
                            icon1.setVisibility(View.VISIBLE);
                        }
                        if(product.get(2).getDisplay().equals("1")){
                            credit.setVisibility(View.VISIBLE);
                            icon3.setVisibility(View.VISIBLE);
                            icon4.setVisibility(View.VISIBLE);
                            icon5.setVisibility(View.VISIBLE);
                        }
                        if(product.get(1).getDisplay().equals("1")){
                            debit.setVisibility(View.VISIBLE);
                            icon2.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<PaymentResponse> call, Throwable t) {
                Toast.makeText(getContext(), getContext().getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getCart(){
        if (connectionDetection.isConnected()) {
            progressBar.setVisibility(View.VISIBLE);
            final Api_Service requestInterface = Config.getClient().create(Api_Service.class);
            Call<Cart_Response2> response1 = requestInterface.getCart(SharedPrefManager.getInstance(context).getUser().
                            getClient_id(),lang,address_id);
            response1.enqueue(new Callback<Cart_Response2>() {
                @Override
                public void onResponse(Call<Cart_Response2> call, retrofit2.Response<Cart_Response2> response) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Cart_Response2 resp = response.body();
                    if (resp != null) {
                        if (resp.getSuccess() == 1) {
                            cart1 = resp.getProduct();

                        }
                    }
                }
                @Override
                public void onFailure(Call<Cart_Response2> call, Throwable t) {
                    progressBar.setVisibility(View.INVISIBLE);

                }
            });

        } else {
            Toast.makeText(getContext(), context.getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
        }
    }

    void checkPoints(){
        if (connectionDetection.isConnected()) {
            Api_Service requestInterface = Config.getClient().create(Api_Service.class);
            Call<LoyaltyPointsResponse> response1 = requestInterface.getPoints(SharedPrefManager.getInstance(getContext()).getUser()
                    .getClient_id(),lang,"1",edit.getText().toString().trim(),address_id);
            response1.enqueue(new Callback<LoyaltyPointsResponse>() {
                @Override
                public void onResponse(Call<LoyaltyPointsResponse> call, Response<LoyaltyPointsResponse> response) {
                    LoyaltyPointsResponse resp = response.body();
                    if (resp != null) {
                        if (resp.getSuccess() == 1) {
                            if(resp.getClientPointsShowPop()!=null) {
                                points = resp.getClientPoints();

                                if(Double.parseDouble(points)>=Double.parseDouble(resp.getClientPointsShowPop())&&resp.getPoints_status().equals("1")){
                                    LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                                    View customView = inflater.inflate(R.layout.dialoge_alert, null);
                                    popupWindow = new PopupWindow(
                                            customView,
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            //   700,
                                            LinearLayout.LayoutParams.MATCH_PARENT, true
                                    );

                                    RelativeLayout out = customView.findViewById(R.id.out);
                                    out.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            popupWindow.dismiss();
                                            confirm_order.setEnabled(true);
                                            confirm_order.setClickable(true);
                                        }
                                    });
                                    Button no1 = customView.findViewById(R.id.no);
                                    Button yes1 = customView.findViewById(R.id.yes);
                                    TextView text = customView.findViewById(R.id.text);
                                    if(resp.getMessage()!=null) {
                                        text.setText(resp.getMessage());
                                    }

                                    no1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            popupWindow.dismiss();
                                            clicked=true;
                                            use_points=false;
                                        }
                                    });

                                    yes1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(final View view) {
                                            popupWindow.dismiss();
                                            clicked=true;
                                            use_points=true;
                                            loyalty="1";
                                            if(resp.getDiscount_percentage()>0){
                                                rel1.setVisibility(View.VISIBLE);
                                                rel2.setVisibility(View.VISIBLE);
                                                rel3.setVisibility(View.VISIBLE);
                                                rel5.setVisibility(View.VISIBLE);
                                                sep.setVisibility(View.VISIBLE);



                                                total_txt.setText(resp.getTotal_amount());
                                                total_.setText(resp.getTotal_amount_after_disc());
                                                if(resp.getCharge()!=null&&resp.getCharge().length()>0) {

                                                    discountt.setText(resp.getSummary());
                                                }
                                                else{
                                                    discountt.setText(resp.getSummary());
                                                }
                                                txt11.setText(getResources().getString(R.string.discountt)+" ("+resp.getDiscount_percentage()+"%) : ");
                                                vat.setText(getResources().getString(R.string.vat)+" ("+resp.getVat()+"%) : ");
                                                vat_value.setText(resp.getVat_value());

                                                if(resp.getCharge()!=null&&resp.getCharge().length()>0&&!resp.getCharge().equals("0.000")){
                                                    rel4.setVisibility(View.VISIBLE);
                                                    charge_txt.setText(resp.getCharge());
                                                }
                                                else{
                                                    rel4.setVisibility(View.GONE);
                                                }

                                                if(deliver_id!=null&&deliver_id.equals("2")){
                                                    rel4.setVisibility(View.GONE);
                                                }

                                            }else {
                                                rel1.setVisibility(View.VISIBLE);
                                                rel2.setVisibility(View.GONE);
                                                rel3.setVisibility(View.VISIBLE);
                                                rel5.setVisibility(View.VISIBLE);
                                                sep.setVisibility(View.VISIBLE);

                                                total_txt.setText(resp.getTotal_amount());
                                                total_.setText(resp.getTotal_amount_after_disc());
                                                txt11.setText(getResources().getString(R.string.discountt)+" ("+resp.getDiscount_percentage()+"%) : ");
                                                vat.setText(getResources().getString(R.string.vat)+" ("+resp.getVat()+"%) : ");
                                                vat_value.setText(resp.getVat_value());
                                                if(resp.getCharge()!=null&&resp.getCharge().length()>0) {
                                                    discountt.setText(resp.getSummary());
                                                }
                                                else{
                                                    discountt.setText(resp.getSummary());
                                                }

                                                if(resp.getCharge()!=null&&resp.getCharge().length()>0&&!resp.getCharge().equals("0.000")){
                                                    rel4.setVisibility(View.VISIBLE);
                                                    charge_txt.setText(resp.getCharge());
                                                }
                                                else{
                                                    rel4.setVisibility(View.GONE);
                                                }

                                                if(deliver_id!=null&&deliver_id.equals("2")){
                                                    rel4.setVisibility(View.GONE);
                                                }
                                            }
                                        }
                                    });
                                    popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                                }
                                else{
                                    use_points=false;
                                    check_avaliability();
                                }
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
    }

    void getFullCart(){
        final Api_Service requestInterface = Config.getClient().create(Api_Service.class);

        Call<Cart_Response2> response1 = requestInterface.getCart(SharedPrefManager.getInstance(context).getUser().getClient_id(),lang ,address_id);
        response1.enqueue(new Callback<Cart_Response2>() {
            @Override
            public void onResponse(Call<Cart_Response2> call, retrofit2.Response<Cart_Response2> response) {
                progressBar.setVisibility(View.INVISIBLE);
                Cart_Response2 resp = response.body();
                if (resp != null) {
                    if (resp.getSuccess() == 1) {
                        cart1 = resp.getProduct();
                        if (cart1.size() > 0) {

                            if(Float.parseFloat(cart1.get(cart1.size() - 1).getDiscount_percentage())>0){
                                rel1.setVisibility(View.VISIBLE);
                                rel2.setVisibility(View.VISIBLE);
                                rel3.setVisibility(View.VISIBLE);
                                rel5.setVisibility(View.VISIBLE);
                                sep.setVisibility(View.VISIBLE);

                                if(cart1.get(cart1.size() - 1).getCharge()!=null&&
                                        cart1.get(cart1.size() - 1).getCharge().length()>0 &&
                                        !cart1.get(cart1.size() - 1).getCharge().equals("0.000")){
                                    rel4.setVisibility(View.VISIBLE);
                                    charge_txt.setText(cart1.get(cart1.size() - 1).getCharge());
                                }
                                else{
                                    rel4.setVisibility(View.GONE);
                                }


                                if(deliver_id!=null&&deliver_id.equals("2")){
                                    rel4.setVisibility(View.GONE);
                                }

                                total_txt.setText(cart1.get(cart1.size() - 1).getTotal_amount());
                                total_.setText(cart1.get(cart1.size() - 1).getTotal_amount_after_disc());
                                if(cart1.get(cart1.size() - 1).getCharge()!=null&&cart1.get(cart1.size() - 1).getCharge().length()>0) {

                                    discountt.setText(cart1.get(cart1.size() - 1).getSummary());
                                }
                                else{
                                    discountt.setText(cart1.get(cart1.size() - 1).getSummary());
                                }
                                txt11.setText(getResources().getString(R.string.discountt)+" ("+cart1.get(cart1.size() - 1).getDiscount_percentage()+"%) : ");
                                vat.setText(getResources().getString(R.string.vat)+" ("+cart1.get(cart1.size() - 1).getVat()+"%) : ");
                                vat_value.setText(cart1.get(cart1.size() - 1).getVat_value());


                            }else {
                                rel1.setVisibility(View.VISIBLE);
                                rel2.setVisibility(View.GONE);
                                rel3.setVisibility(View.VISIBLE);
                                rel5.setVisibility(View.VISIBLE);
                                sep.setVisibility(View.VISIBLE);

                                total_txt.setText(cart1.get(cart1.size() - 1).getTotal_amount());
                                total_.setText(cart1.get(cart1.size() - 1).getTotal_amount_after_disc());
                                txt11.setText(getResources().getString(R.string.discountt)+" ("+cart1.get(cart1.size() - 1).getDiscount_percentage()+"%) : ");
                                vat.setText(getResources().getString(R.string.vat)+" ("+cart1.get(cart1.size() - 1).getVat()+"%) : ");
                                vat_value.setText(cart1.get(cart1.size() - 1).getVat_value());
                                if(cart1.get(cart1.size() - 1).getCharge()!=null&&cart1.get(cart1.size() - 1).getCharge().length()>0) {
                                    discountt.setText(cart1.get(cart1.size() - 1).getSummary());
                                }
                                else{
                                    discountt.setText(cart1.get(cart1.size() - 1).getSummary());
                                }
                                if(cart1.get(cart1.size() - 1).getCharge()!=null&&
                                        cart1.get(cart1.size() - 1).getCharge().length()>0 &&
                                        !cart1.get(cart1.size() - 1).getCharge().equals("0.000")){
                                    rel4.setVisibility(View.VISIBLE);
                                    charge_txt.setText(cart1.get(cart1.size() - 1).getCharge());
                                }
                                else{
                                    rel4.setVisibility(View.GONE);
                                }


                                if(deliver_id!=null&&deliver_id.equals("2")){
                                    rel4.setVisibility(View.GONE);
                                }
                            }

                        } else {

                        }

                    }
                }
            }


            @Override
            public void onFailure(Call<Cart_Response2> call, Throwable t) {

            }
        });
    }

    private void order_online(String total,String type){

        progressBar.setVisibility(View.VISIBLE);
        Api_Service requestInterface = Config.getClient().create(Api_Service.class);

        Order_respose.Order_model order = new Order_respose.Order_model();
        order.setCart_id(id_cart);
        order.setClient_id(SharedPrefManager.
                getInstance(activity).getUser().getClient_id());
        order.setPayment(payment_type);
        order.setClient_address_id(address_id);
        order.setDeliver_id(deliver_id);
        order.setBranch_id(branch_id);
        order.setLang(lang);
        order.setMobile_version(SharedPrefManager.getInstance(context).getMobileVersion());
        order.setDevice_type("android");
        if(use_points){
            order.setUse_points("1");
        }
        else{
            order.setUse_points("0");
        }
        order.setMobile_version(SharedPrefManager.getInstance(activity).getMobileVersion());
        if(applied){
            order.setDiscount_code(edit.getText().toString().trim());
        }
        else{
            order.setDiscount_code("");
        }

        order.setIs_paid("2");

        Call<Order_respose> response1 = requestInterface.confirm_order2(order);
        response1.enqueue(new Callback<Order_respose>() {
            @Override
            public void onResponse(Call<Order_respose> call, retrofit2.Response<Order_respose> response) {
                Order_respose resp = response.body();
                if (resp != null) {
                    if (resp.getSuccess() == 1) {
                        Intent i;
                        if (type.equals("debit")) {

                            i = new Intent(context, Payment.class);

                        } else {
                                i = new Intent(context, Payment2.class);

                        }
                        //   i.putExtra("pay_id",payment_id);
                        i.putExtra("cart_id", resp.getProduct().get(0).getOrder_id());
                        i.putExtra("total", total);
                        startActivity(i);
                    } else {

                        LayoutInflater inflater = (LayoutInflater) getContext().
                                getSystemService(LAYOUT_INFLATER_SERVICE);
                        View customView = inflater.inflate(R.layout.message_popup, null);
                        popupWindow = new PopupWindow(
                                customView,
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                //   700,
                                LinearLayout.LayoutParams.MATCH_PARENT, true
                        );

                        RelativeLayout out = customView.findViewById(R.id.out);
                        out.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                popupWindow.dismiss();
                            }
                        });
                        Button no1 = customView.findViewById(R.id.ok);
                        TextView text = customView.findViewById(R.id.text);
                        text.setText(resp.getMessage());
                        no1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                popupWindow.dismiss();
                            }
                        });
                        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                    }
                }
            }

            @Override
            public void onFailure(Call<Order_respose> call, Throwable t) {
                Toast.makeText(getContext(), activity.getResources().
                        getString(R.string.errortryagain), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
