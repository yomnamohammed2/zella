package com.emcan.zella.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.emcan.zella.Api_Service;
import com.emcan.zella.Beans.Additions_Model;
import com.emcan.zella.Beans.Branch_Model;
import com.emcan.zella.Beans.CartResponse;
import com.emcan.zella.Beans.Cart_Response2;
import com.emcan.zella.Beans.Check_client;
import com.emcan.zella.Beans.Prices_Model;
import com.emcan.zella.Beans.Remove_Response;
import com.emcan.zella.Beans.Sub_Category;
import com.emcan.zella.Config;
import com.emcan.zella.ConnectionDetection;
import com.emcan.zella.Data_Manger;
import com.emcan.zella.GET_DATA;
import com.emcan.zella.Get_Photos;
import com.emcan.zella.R;
import com.emcan.zella.SharedPrefManager;
import com.emcan.zella.activities.MainActivity;
import com.emcan.zella.adapters.Additions_adpter;
import com.emcan.zella.adapters.MainSliderAdapter;
import com.emcan.zella.adapters.MainSliderAdapter2;
import com.emcan.zella.adapters.Sizes_Adapter;
import com.emcan.zella.adapters.Without_adapters;
import com.emcan.zella.databinding.FragmentDishFragmentsBinding;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class Dish_Fragments extends Fragment implements GET_DATA,Get_Photos {
    public static Sub_Category dish;
    String parent_name;
    PopupWindow popupWindow;
    String size_price;
    float addition_price=0;
    Button spicy,original;
    RelativeLayout spicy_rel;
    int spicy_option=0;
   public ArrayList<Prices_Model.Price> prices;
    View view;

    RelativeLayout sep2,sep3;
    public RecyclerView recyclerView_sizes,recyclerView_additions,recyclerView_removes;

    TextView name,desc,price;
    static FragmentManager frag_manger;
    int quantity = 1;
    FragmentManager fragmentManager;
    ImageButton back;
    String lang;
    Typeface typeface;

    private static final String DESCRIBABLE_KEY = "describable_key";
    private static final String PARENT_NAME = "parent_name";
    Toolbar toolbar;
    TextView title, num;
    ImageView cart;
    RelativeLayout no, yes;

    public ConnectionDetection connectionDetection;
    public ProgressBar progressBar;
    AppCompatActivity activity;
    Context context;
    String price_id="";

    RelativeLayout additions_rel,removes_rel;

    TextView additions_txt,removes_txt,addition,remove;
    ImageView back3,back4,back5;

     String without_id,remove_title;
     ArrayList<Remove_Response.Removes> removes;

    public  ArrayList<Remove_Response.Removes> removes_list;

    ArrayList<Additions_Model.Addition> chosen;
    public ArrayList<Additions_Model.Addition> additions;

    String addition_name, addition_id;
    FragmentDishFragmentsBinding binding;


    public Dish_Fragments() {
        // Required empty public constructor
    }


    public static Dish_Fragments newInstance(Sub_Category dish, String parent_name) {
        Dish_Fragments fragment = new Dish_Fragments();
        Bundle bundle = new Bundle();
        bundle.putSerializable(DESCRIBABLE_KEY, dish);
        bundle.putString(PARENT_NAME, parent_name);
        fragment.setArguments(bundle);
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
        binding = FragmentDishFragmentsBinding.inflate(inflater, container, false);

        view=binding.getRoot();

        //----------------get dish data and parent _name-------------.//
        final Bundle bundle = this.getArguments();
        dish = (Sub_Category) bundle.getSerializable("describable_key");
        parent_name = bundle.getString("parent_name");
        activity = (AppCompatActivity) getActivity();
        lang = SharedPrefManager.getInstance(activity).getLang();
        if(lang.equals("en")){
            typeface = ResourcesCompat.getFont(activity, R.font.amaranth_bold);
        }
        if(lang.equals("ar")){
            typeface= ResourcesCompat.getFont(activity, R.font.bein_black);
        }
        init();
        setUp_Toolbar();

        RelativeLayout bar = activity.findViewById(R.id.bar);
        bar.setVisibility(View.GONE);
        ((MainActivity) activity).select_icon("home");

        if (lang.equals("en")) {
            back.setRotation(180);
            back3.setRotation(180);
            back4.setRotation(180);
            back5.setRotation(180);
        }
        if (lang.equals("ar")) {
            back.setRotation(0);
            back3.setRotation(0);
            back4.setRotation(0);
            back5.setRotation(0);
        }


        name.setText(dish.getSub_category_name());
        desc.setText(dish.getSub_category_desc());

//        Log.d("ppppppppppppppp",dish.getSub_category_id()+"/"+
//                dish.getAddition_type()+"/"+dish.getParent_category_id());





        if(dish.getSizes()!=null&&dish.getSizes().size()>0) {
            price.setText(dish.getSizes().get(0).getSub_category_size_price() + " " +
                    context.getResources().getString(R.string.bhd));

            set_Data();
        }


        if (dish.getImages()!=null&&dish.getImages().size() > 0) {
            binding.imageSlider.setVisibility(View.VISIBLE);

            MainSliderAdapter2 sliderAdapter1 = new MainSliderAdapter2(activity,dish.getImages());
            binding.imageSlider.setSliderAdapter(sliderAdapter1);
        }


        final TextView quan = view.findViewById(R.id.quantity);
        quan.setTypeface(typeface);
        ImageView plus = view.findViewById(R.id.plus);
        ImageView minus = view.findViewById(R.id.minus);

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity++;
                quan.setText(String.valueOf(quantity));
                set_Price();

            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity > 1) {
                    quantity--;
                    quan.setText(String.valueOf(quantity));
                    set_Price();

                }
            }
        });
        RelativeLayout Add = view.findViewById(R.id.addtocart);
        binding.txt.setTypeface(typeface);
        binding.cartPrice.setTypeface(typeface);
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (SharedPrefManager.getInstance(context).isLoggedIn()) {

                    check_user();
                } else {
                    Toast.makeText(context, activity.getResources().getString(R.string.signinfirst), Toast.LENGTH_SHORT).show();
                }

            }
        });


      //-------------------------get Additions---------------------//

        if(dish.getAddition_type()!=null&&dish.getAddition_type().equals("1")) {
            if (connectionDetection.isConnected()) {
                Data_Manger.get_Additions(Dish_Fragments.this, context, dish.getSub_category_id());

            } else {
                Toast.makeText(context, activity.getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
            }

        }

        //----------------get removes------------------------//


        if(dish.getRemoves_type()!=null&&dish.getRemoves_type().equals("1")) {
            if (connectionDetection.isConnected()) {

                Data_Manger.get_Removes(Dish_Fragments.this, context, dish.getSub_category_id());

            } else {
                Toast.makeText(context, activity.getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
            }

        }

        binding.comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = new Reviews();
                Bundle b=new Bundle();
                b.putString("sub_cat_id",dish.getSub_category_id());
                fragment.setArguments(b);
                fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack("xyz")
                        .commit();
            }
        });
        binding.noteTxt.setTypeface(typeface);
        binding.note.setTypeface(typeface);

        binding.note.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (view.getId() == R.id.note_rel1) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_UP:
                            view.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;
            }
        });
////
//
        additions_rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(recyclerView_additions.getVisibility()==View.VISIBLE){
                    recyclerView_additions.setVisibility(View.GONE);
                    if(lang.equals("ar")){
                        back3.setRotation(0);
                    }
                    else {
                        back3.setRotation(180);
                    }
                }else{
                    recyclerView_additions .setVisibility(View.VISIBLE);
                    recyclerView_removes.setVisibility(View.GONE);

                    if(lang.equals("ar")){
                        back3.setRotation(-90);
                        back5.setRotation(0);
                        back4.setRotation(0);
                    }
                    else {
                        back3.setRotation(90);
                        back5.setRotation(180);
                        back4.setRotation(180);
                    }

                }
            }
        });


        removes_rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(recyclerView_removes.getVisibility()==View.VISIBLE){
                    recyclerView_removes.setVisibility(View.GONE);
                    if(lang.equals("ar")){
                        back4.setRotation(0);
                    }
                    else {
                        back4.setRotation(180);
                    }
                }else{
                    recyclerView_removes .setVisibility(View.VISIBLE);

                    recyclerView_additions.setVisibility(View.GONE);
                    if(lang.equals("ar")){
                        back4.setRotation(-90);
                        back3.setRotation(0);
                        back5.setRotation(0);
                    }
                    else {
                        back4.setRotation(90);
                        back5.setRotation(180);
                        back3.setRotation(180);
                    }

                }
            }
        });

        binding.noteRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.  noteRel1.getVisibility()==View.VISIBLE){
                    binding.  noteRel1.setVisibility(View.GONE);
                    if(lang.equals("ar")){
                        back5.setRotation(0);
                    }
                    else {
                        back5.setRotation(180);
                    }
                }else{
                   binding.  noteRel1.setVisibility(View.VISIBLE);

                    recyclerView_additions.setVisibility(View.GONE);
                    recyclerView_removes.setVisibility(View.GONE);
                    if(lang.equals("ar")){
                        back5.setRotation(-90);
                        back3.setRotation(0);
                        back4.setRotation(0);

                    }
                    else {
                        back5.setRotation(90);
                        back4.setRotation(180);
                        back3.setRotation(180);

                    }
                }
            }
        });

        return view;
    }


    private void update_toolbar() {

        title = toolbar.getRootView().findViewById(R.id.toolbar_title);
        cart = toolbar.getRootView().findViewById(R.id.cart);
        no.setVisibility(View.INVISIBLE);
        yes.setVisibility(View.VISIBLE);
        num.setText(String.valueOf(SharedPrefManager.getInstance(context).get_Cart()));
        title.setTypeface(typeface);
        title.setText(parent_name);


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
    }




    @Override
    public void get_add(Additions_Model.Addition addition, int f) {

        addition_price=0;
        if(lang.equals("ar")){
            binding.back3.setRotation(0);
        }
        else {
            binding. back3.setRotation(180);
        }
        if (f == 1) {
            chosen.add(addition);
        } else {
            for (int i = 0; i < chosen.size(); i++) {
                if (chosen.get(i).getAddition_id().equals(addition.getAddition_id())) {
                    chosen.remove(i);
                }
            }
        }

        if (chosen.size() > 0) {
            addition_id = chosen.get(0).getAddition_id();
            addition_name = chosen.get(0).getAddition_name();
            addition_price=Float.parseFloat(chosen.get(0).getAddition_price())+addition_price;

        }else{
            addition_name="";
        }
        if (chosen.size() > 1) {
            for (int i = 1; i < chosen.size(); i++) {
                addition_id = addition_id + "," + chosen.get(i).getAddition_id();
                addition_name = addition_name + "," + chosen.get(i).getAddition_name();
                addition_price=Float.parseFloat(chosen.get(i).getAddition_price())+addition_price;
            }
        }
        set_Price();
        binding.additions.setText(addition_name);
    }



@Override
    public void get_without(Remove_Response.Removes remove, int f) {

        if (f == 1) {
            removes.add(remove);
        } else {
            for (int i = 0; i < removes.size(); i++) {
                if (removes.get(i).getRemove_id().equals(remove.getRemove_id())) {
                    removes.remove(i);
                }
            }
        }
        if (removes.size() > 0) {
                            without_id = removes.get(0).getRemove_id();
                            remove_title = removes.get(0).getRemove_title();
                        }else{
            remove_title="";
        }
                        if (removes.size() > 1) {
                            for (int i = 1; i < removes.size(); i++) {
                                without_id = without_id + "," + removes.get(i).getRemove_id();
                                remove_title = remove_title + "," + removes.get(i).getRemove_title();
                            }
                        }
                        this.remove.setText(remove_title);


    }


    private void addtocart() {

        if (SharedPrefManager.getInstance(context).isLoggedIn()) {
            if (price_id != null && !price_id.equals("")) {


                    if (connectionDetection.isConnected()) {

                      addTo_Cart();

                    } else {
                        Toast.makeText(context, activity.getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
                    }


            } else {
                Toast.makeText(context, activity.getResources().getString(R.string.ll), Toast.LENGTH_SHORT).show();
            }

        }
        else {
            Toast.makeText(context, activity.getResources().getString(R.string.signfirst), Toast.LENGTH_SHORT).show();
        }


    }

    private void check_user(){

        Api_Service requestInterface = Config.getClient().create(Api_Service.class);

        Call<Check_client> response1 = requestInterface.check_client(
                SharedPrefManager.getInstance(context).getUser().getClient_id());
        response1.enqueue(new Callback<Check_client>() {
            @Override
            public void onResponse(Call<Check_client> call, retrofit2.Response<Check_client> response) {
                Check_client resp = response.body();
                if (resp != null) {

                    if(resp.getSuccess()==1){


                        if(resp.getProduct().get(0).getExist()==0){

                            SharedPrefManager.getInstance(context).logout();
                            Intent intent = new Intent(context, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            activity. startActivity(intent);

                        }else{
                         addtocart();
                        }
                    }else{
//                        Toast.makeText(getContext(),"error",Toast.LENGTH_SHORT).show();
                    }
                }
            }


            @Override
            public void onFailure(Call<Check_client> call, Throwable t) {
//                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });

    }


    private void init(){
        context=getContext();

//        linearLayout= view.findViewById(R.id.dish_frg);
        frag_manger=getChildFragmentManager();
        progressBar=view.findViewById(R.id.progressBar);
        connectionDetection=new ConnectionDetection(context);
        name=view.findViewById(R.id.name);
        name.setTypeface(typeface);
        desc =view.findViewById(R.id.desc);
        desc.setTypeface(typeface);
        spicy=view.findViewById(R.id.spicy);
        original=view.findViewById(R.id.orignal);
        spicy_rel=view.findViewById(R.id.spicy_rel);


        price =view.findViewById(R.id.price);
        price.setTypeface(typeface);

        recyclerView_sizes=view.findViewById(R.id.recycler_sizes);

        sep2=view.findViewById(R.id.sep2);
        sep3=view.findViewById(R.id.sep3);

        recyclerView_additions=view.findViewById(R.id.recycler_additions);
        additions_rel=view.findViewById(R.id.additions_rel);
        additions_txt=view.findViewById(R.id.additions_txt);
        back3=view.findViewById(R.id.back3);

        recyclerView_removes=view.findViewById(R.id.recycler_removes);
        removes_rel=view.findViewById(R.id.removes_rel);
        removes_txt=view.findViewById(R.id.removes_txt);
        back4=view.findViewById(R.id.back4);

        addition=view.findViewById(R.id.additions);
        remove=view.findViewById(R.id.removes);

        addition.setTypeface(typeface);
        remove.setTypeface(typeface);
        additions_txt.setTypeface(typeface);
        removes_txt.setTypeface(typeface);




        additions=new ArrayList<>();
        removes_list=new ArrayList<>();
        chosen=new ArrayList<>();
        removes=new ArrayList<>();

        back5=view.findViewById(R.id.back5);



    }

    private void setUp_Toolbar(){
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        back=toolbar.findViewById(R.id.back);
        no=toolbar.findViewById(R.id.no_cart);
        yes=toolbar.findViewById(R.id.yes_cart);

        num=toolbar.getRootView().findViewById(R.id.num);
        fragmentManager=activity.getSupportFragmentManager();
//        comments=toolbar.findViewById(R.id.comment);
//        comments.setVisibility(View.VISIBLE);
        title=toolbar.getRootView().findViewById(R.id.toolbar_title);

        if(SharedPrefManager.getInstance(context).get_Cart()>0){
            cart=toolbar.getRootView().findViewById(R.id.cart);

            no.setVisibility(View.INVISIBLE);
            yes.setVisibility(View.VISIBLE);
            num.setText(String.valueOf(SharedPrefManager.getInstance(context).get_Cart()));

        }else{
            cart=toolbar.getRootView().findViewById(R.id.cart1);
            yes.setVisibility(View.INVISIBLE);
            no.setVisibility(View.VISIBLE);
        }
        title.setTypeface(typeface);
        title.setText(getResources().getString(R.string.app_name));
        back.setVisibility(View.INVISIBLE);

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


    }

    @Override
    public void onPause() {
        super.onPause();
        back.setVisibility(View.VISIBLE);
    }


    @Override
    public void get_ID(Branch_Model.Branch branch) {

    }

    @Override
    public void remove_ID() {

    }



    @Override
    public void getSize(Prices_Model.Price size) {

        this.price_id=size.getSub_category_size_price_id();
        if(size.getDiscount()!=null&&!size.getDiscount().equals("0")){
            this.size_price = size.getSub_category_size_price_after_disc();

        }else {
            this.size_price = size.getSub_category_size_price();
        }
        set_Price();
    }

    @Override
    public void get_Price(ArrayList<Prices_Model.Price> prices_models) {
        prices = prices_models;
        Log.d("sizessss",prices.size()+"");
        if (prices.size() > 0) {

            LinearLayoutManager mLayoutManager1 = new LinearLayoutManager(context
                    , LinearLayoutManager.HORIZONTAL, false);
            if (lang.equals("ar")) {
                mLayoutManager1.setReverseLayout(true);

            }
            Sizes_Adapter adapter = new Sizes_Adapter(context, prices, Dish_Fragments.this);
            if(prices!=null&&prices.size()>0&&prices.size()==1){
                price_id=prices.get(0).getSub_category_size_price_id();
            }
            recyclerView_sizes.setLayoutManager(mLayoutManager1);
            recyclerView_sizes.setItemAnimator(new DefaultItemAnimator());
            recyclerView_sizes.setAdapter(adapter);
            if(prices!=null&&prices.size()>1){
                recyclerView_sizes.setVisibility(View.VISIBLE);
            }
        }
    }


    private void addTo_Cart(){

        Api_Service requestInterface = Config.getClient().create(Api_Service.class);

        Log.d("jjjj", price_id + "/" + addition_id+" "+ SharedPrefManager.getInstance(context).getUser().getClient_id());

        CartResponse.CartModel mode = new CartResponse.CartModel();
        mode.setSub_category_id(dish.getSub_category_id());
        mode.setClient_id(SharedPrefManager.getInstance(getContext()).getUser().getClient_id());
        mode.setQuantity(String.valueOf(quantity));
        mode.setAddition_id(addition_id);
        mode.setSize_id(price_id);
        mode.setRemove_id(without_id);
        mode.setSpicy_type(String.valueOf(spicy_option));
        Log.d("ppppppppp",new Gson().toJson(mode));

        Call<Cart_Response2> response1 = requestInterface.addTOCart(mode);
        response1.enqueue(new Callback<Cart_Response2>() {
            @Override
            public void onResponse(Call<Cart_Response2> call, retrofit2.Response<Cart_Response2> response) {
                Cart_Response2 resp = response.body();
                if (resp != null) {
                    if (resp.getSuccess() == 1) {
                        addition_id = "";

                        chosen.clear();
                        removes.clear();
                        without_id = "";

                        getCart();

                        Log.d("ppppppppppppp", new Gson().toJson(resp));
//                        Toast.makeText(context, activity.getResources().getString(R.string.addedtocart), Toast.LENGTH_SHORT).show();
//                        SharedPrefManager.getInstance(context).add_Cart();
//                        update_toolbar();
//                        fragmentManager.popBackStack();
//                        Fragment fragment = new MyCart();
//                        fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack("xyz")
//                                .commit();


                    } else {
                        Toast.makeText(context, resp.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, resp.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<Cart_Response2> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }



    @Override
    public void get_Additions(ArrayList<Additions_Model.Addition> additions) {

        if(additions.size()>0) {
            additions_rel.setVisibility(View.VISIBLE);
            Additions_adpter adapter = new Additions_adpter(context, additions, Dish_Fragments.this);
            recyclerView_additions.setAdapter(adapter);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            recyclerView_additions.setLayoutManager(mLayoutManager);
        }
    }

    @Override
    public void get_Removes(ArrayList<Remove_Response.Removes> removes) {
        if(removes.size()>0) {
            removes_rel.setVisibility(View.VISIBLE);
        }

        Without_adapters adapter = new Without_adapters(context,removes, Dish_Fragments.this);
        Log.d("removes",removes.size()+" ");
       recyclerView_removes.setAdapter(adapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView_removes.setLayoutManager(mLayoutManager);
    }


    public void set_Data(){
        if (dish.getSizes() != null && dish.getSizes().size() > 0) {

            binding.recyclerSizes.setVisibility(View.VISIBLE);
            Sizes_Adapter mAdapter1 = new Sizes_Adapter(context, dish.getSizes(), Dish_Fragments.this);
            LinearLayoutManager mLayoutManager1 = new LinearLayoutManager(context,
                    LinearLayoutManager.HORIZONTAL, false);


            if (lang.equals("ar")) {
                mLayoutManager1.setReverseLayout(false);

            }
            binding.recyclerSizes.setLayoutManager(mLayoutManager1);
            binding.recyclerSizes.setItemAnimator(new DefaultItemAnimator());
            binding.recyclerSizes.setAdapter(mAdapter1);


            price_id=dish.getSizes().get(0).getSub_category_size_price_id();
            if (dish.getSizes().get(0).getDiscount().equals("1")) {
                binding.price.setText(dish.getSizes().get(0).getSub_category_size_price_after_disc() +
                        " " + context.getResources().getString(R.string.bhd));
                binding.cartPrice.setText("("+dish.getSizes().get(0).getSub_category_size_price_after_disc()+" "+
                        context.getResources().getString(R.string.bhd)+")");
                this.size_price=dish.getSizes().get(0).getSub_category_size_price_after_disc();
            } else {
                binding.price.setText(dish.getSizes().get(0).getSub_category_size_price() +
                        " " + context.getResources().getString(R.string.bhd));
                binding.cartPrice.setText("("+dish.getSizes().get(0).getSub_category_size_price()
                        +" "+context.getResources().getString(R.string.bhd)+")");
                this.size_price=dish.getSizes().get(0).getSub_category_size_price();
            }
        }
    }
    public void set_Price(){
        if (dish.getSizes()!=null&&dish.getSizes().size()>0) {
            if (dish.getSizes() != null && dish.getSizes().size() > 0 &&
                    dish.getSizes().get(0).getDiscount() != null &&
                    dish.getSizes().get(0).getDiscount().equals("1")) {
//            binding.price.setText(
//                    String.valueOf(Float.valueOf(quantity)* (Float.parseFloat(dish.getSizes().get(0).getSub_category_size_price_after_disc())
//                            +addition_price)) +
//                            " " + context.getResources().getString(R.string.bhd));

            } else {
//            binding.price.setText(
//                    String.valueOf(Float.valueOf(quantity)* (Float.parseFloat(dish.getSizes().get(0).getSub_category_size_price()
//                    )+addition_price)) +
//                            " " + context.getResources().getString(R.string.bhd));
            }
            binding.cartPrice.setText("(" + String.valueOf(Float.valueOf(quantity) * (Float.parseFloat(size_price) + addition_price)) + " " +
                    context.getResources().getString(R.string.bhd) + ")");

        }
    }

    public void getCart(){
        if (connectionDetection.isConnected()) {
            progressBar.setVisibility(View.VISIBLE);
            Log.d("pppppppppppp",SharedPrefManager.getInstance(context).getUser().getClient_id());
            final Api_Service requestInterface = Config.getClient().create(Api_Service.class);
            Call<Cart_Response2> response1 = requestInterface.getCart(SharedPrefManager.getInstance(context).getUser().getClient_id(),lang
                    ,"");
            response1.enqueue(new Callback<Cart_Response2>() {
                @Override
                public void onResponse(Call<Cart_Response2> call, retrofit2.Response<Cart_Response2> response) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Cart_Response2 resp = response.body();
                    if (resp != null) {
                        if (resp.getSuccess() == 1) {

                            binding.addtocart.setVisibility(View.GONE);
                            binding.added.setVisibility(View.VISIBLE);
                            binding.num.setTypeface(typeface);
                            binding.num.setText(resp.getProduct().size()-1+" "+
                                    context.getResources().getString(R.string.items));

                            binding.cartPrice2.setTypeface(typeface);
                            binding.cartPrice2.setText(resp.getProduct().get(resp.getProduct().size()-1).getSummary()+" "+
                                    context.getResources().getString(R.string.bhd));
                            binding.txt1.setTypeface(typeface);
                            binding.added.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    Fragment fragment = new MyCart();
                             fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack("xyz")
                                     .commit();
                                }
                            });
                        }
                    }
                }


                @Override
                public void onFailure(Call<Cart_Response2> call, Throwable t) {


                }
            });

        } else {
            Toast.makeText(getContext(), context.getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
        }
    }
}

