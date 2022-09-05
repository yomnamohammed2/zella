package com.emcan.zella.fragments;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.emcan.zella.Api_Service;
import com.emcan.zella.Beans.Sub_Category;
import com.emcan.zella.Beans.Sub_Category_Model;
import com.emcan.zella.Config;
import com.emcan.zella.ConnectionDetection;
import com.emcan.zella.R;
import com.emcan.zella.SharedPrefManager;
import com.emcan.zella.activities.MainActivity;
import com.emcan.zella.adapters.Recycler_adapter;
import com.emcan.zella.databinding.FragmentSearchBinding;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;


public class Search extends Fragment  {


    ImageView back;
    FragmentSearchBinding binding;
    Toolbar toolbar;
    TextView title;
    String lang;
    Typeface typeface;
    Context context;
    AppCompatActivity activity;
    FragmentManager fragmentManager;
    ConnectionDetection connectionDetection;
    ArrayList<Sub_Category> cats;
    TextView  num;
    ImageView cart;
    ImageButton menu;
    RelativeLayout no, yes;
    long delay = 300; // 1 seconds after user stops typing
    long last_text_edit = 0;
    Handler handler = new Handler();



    public Search() {
        // Required empty public constructor
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
        binding = FragmentSearchBinding.inflate(inflater, container, false);

        View view=binding.getRoot();
        context=getContext();
        activity=(AppCompatActivity) getActivity();
        lang= SharedPrefManager.getInstance(context).getLang();
        fragmentManager =activity.getSupportFragmentManager();
        connectionDetection=new ConnectionDetection(context);

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        title = toolbar.getRootView().findViewById(R.id.toolbar_title);

        menu = toolbar.getRootView().findViewById(R.id.menu);
        menu.setVisibility(View.VISIBLE);

        activity.setSupportActionBar(toolbar);
        back=activity.findViewById(R.id.back);
        back.setVisibility(View.VISIBLE);

        title.setTypeface(typeface);


        no = toolbar.getRootView().findViewById(R.id.no_cart);
        yes = toolbar.getRootView().findViewById(R.id.yes_cart);

        num = toolbar.getRootView().findViewById(R.id.num);

        RelativeLayout bar = activity.findViewById(R.id.bar);
        bar.setVisibility(View.VISIBLE);
        ((MainActivity) activity).select_icon("more");

        updateToolbar();

        cats=new ArrayList<>();

        binding.progressBar.getIndeterminateDrawable().setColorFilter
                (getResources().getColor(R.color.colorAccent),
                PorterDuff.Mode.SRC_IN);


        binding.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.name.clearFocus();
                binding.name.requestFocus();


            }
        });



        if(lang.equals("ar")){
          binding. name.setGravity(Gravity.RIGHT);
          binding.searchIcon.setRotationY(180);
        }else{
          binding. name.setGravity(Gravity.LEFT);
        }

         final Runnable input_finish_checker = new Runnable() {
            public void run() {
                if (System.currentTimeMillis() > (last_text_edit + delay - 500)) {
                cats.clear();
                if (binding.name.getText().toString().length()>0) {

                  search(binding.name.getText().toString());
                  binding.recyclerView.setVisibility(View.GONE);

                }
                else {

                    binding.recyclerView.setVisibility(View.VISIBLE);

                }

                }
            }
        };

        binding.name.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                handler.removeCallbacks(input_finish_checker);

//                Log.d("jjjjjj", String.valueOf(count)+s);
//                rst.clear();
//                cats.clear();
//                if (count > 0) {
//
//                  search(String.valueOf(s));
//
//                }
//                else {
//                  binding. recyclerOutlet.setVisibility(View.GONE);
//                  binding. recyclerRestaurant.setVisibility(View.GONE);
//
//                    binding. restaurantRel.setVisibility(View.GONE);
//                    binding. outletsRel.setVisibility(View.GONE);
//
//                }

            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    last_text_edit = System.currentTimeMillis();
                    handler.postDelayed(input_finish_checker, delay);
                } else {

                    binding.recyclerView.setVisibility(View.VISIBLE);

                }
            }
        });


        return binding.getRoot();

    }


    public static void setDynamicHeight(GridView gridView) {
        ListAdapter gridViewAdapter = gridView.getAdapter();
        if (gridViewAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int items = gridViewAdapter.getCount();
        double rows = 0;

        View listItem = gridViewAdapter.getView(0, null, gridView);
        listItem.measure(0, 0);
        totalHeight = listItem.getMeasuredHeight();

        double x = 1;
        if (items > 2) {
            x = items /(double)2;
            rows = (double) (x+0.8);
            totalHeight *= rows;
        }

        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight;
        gridView.setLayoutParams(params);
    }


    public  void search(String msg){

        if(!msg.equals("")) {

Log.d("pppppppppppppppppppp",msg);


            if (connectionDetection.isConnected()) {
                binding.progressBar.setVisibility(View.VISIBLE);

                Api_Service requestInterface = Config.getClient().create(Api_Service.class);
                String id="";
                if(SharedPrefManager.getInstance(context).isLoggedIn()){
                    id=SharedPrefManager.getInstance(context).getUser().getClient_id();
                }
                Call<Sub_Category_Model> response1 = requestInterface.search(msg, id, SharedPrefManager.getInstance(context).getLang());
                response1.enqueue(new Callback<Sub_Category_Model>() {
                    @Override
                    public void onResponse(Call<Sub_Category_Model> call, retrofit2.Response<Sub_Category_Model> response) {
                        binding.progressBar.setVisibility(View.INVISIBLE);
                        Sub_Category_Model resp = response.body();
                        Log.d("pppppppppppppp","ooooooooooooooooooo");

                        if (resp.getProduct() != null && resp.getProduct().size() > 0) {

                            Log.d("pppppppppppppp","pppppppppppppppppppppppppppp");
                            Recycler_adapter mAdapter = new Recycler_adapter(context,
                                    resp.getProduct(),"");

                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);

                            binding.recyclerView.setLayoutManager(layoutManager);
                            binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
                            binding.recyclerView.setAdapter(mAdapter);

                            binding.recyclerView.setVisibility(View.VISIBLE);


                        } else {
                        binding.recyclerView.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    public void onFailure(Call<Sub_Category_Model> call, Throwable t) {
                        binding.progressBar.setVisibility(View.INVISIBLE);
                    }
                });

            } else {
                Toast.makeText(context, activity.getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
            }
        }else{

            binding.recyclerView.setVisibility(View.GONE);


        }



    }

    public void updateToolbar(){
        title = toolbar.getRootView().findViewById(R.id.toolbar_title);

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
        title.setText(context.getResources().getString(R.string.search));

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new MyCart();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment).addToBackStack(null)
                        .commit();
            }
        });
    }
}