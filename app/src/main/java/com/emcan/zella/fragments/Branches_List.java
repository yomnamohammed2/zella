package com.emcan.zella.fragments;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.emcan.zella.Api_Service;
import com.emcan.zella.Beans.Branch_Model;
import com.emcan.zella.Config;
import com.emcan.zella.ConnectionDetection;
import com.emcan.zella.R;
import com.emcan.zella.SharedPrefManager;
import com.emcan.zella.adapters.Branches_Adapter2;
import com.emcan.zella.databinding.FragmentBranchesListBinding;

import java.util.ArrayList;


public class Branches_List extends Fragment {

FragmentBranchesListBinding binding;
ConnectionDetection connectionDetection;
Context context;
AppCompatActivity activity;
ArrayList<Branch_Model.Branch> branchs;

    public Branches_List() {
        // Required empty public constructor
    }


    public static Branches_List newInstance(String param1, String param2) {
        Branches_List fragment = new Branches_List();
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
        binding= FragmentBranchesListBinding.inflate(inflater, container, false);

        context=getContext();
        activity=(AppCompatActivity) getActivity();

        connectionDetection=new ConnectionDetection(context);
        branchs=new ArrayList<>();

        getBranches();


        return binding.getRoot();

    }

    public void getBranches() {

        if (connectionDetection.isConnected()) {
            binding.progressBar.setVisibility(View.VISIBLE);
            Api_Service requestInterface = Config.getClient().create(Api_Service.class);
            Branch_Model model=new Branch_Model();
            model.setLang(SharedPrefManager.getInstance(activity).getLang());
            model.setUser_lat(SharedPrefManager.getInstance(activity).getLatti());
            model.setUser_long(SharedPrefManager.getInstance(activity).getLongi());
            Call<Branch_Model> response1 = requestInterface.get_Branches(SharedPrefManager.getInstance(activity).getLang());
            response1.enqueue(new Callback<Branch_Model>() {
                @Override
                public void onResponse(Call<Branch_Model> call, retrofit2.Response<Branch_Model> response) {
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    Branch_Model resp = response.body();
                    if (resp != null) {

                        if (resp.getSuccess() == 1) {
                            branchs = resp.getProduct();
                            if (branchs.size() > 0) {

                                Branches_Adapter2 mAdapter = new Branches_Adapter2(context, branchs);
                                // mAdapter.notifyDataSetChanged();
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                                binding.recycler.setLayoutManager(mLayoutManager);

                                binding.recycler.setItemAnimator(new DefaultItemAnimator());
                                binding.recycler.setAdapter(mAdapter);

                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<Branch_Model> call, Throwable t) {
                   binding. progressBar.setVisibility(View.INVISIBLE);

                }
            });
        } else {
          binding.  progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(getContext(), activity.getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 1:
                if (grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED) {

                } else {
                }
                break;
        }
    }

}