package com.emcan.zella.fragments;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.emcan.zella.Api_Service;
import com.emcan.zella.Beans.Complain_response;
import com.emcan.zella.Config;
import com.emcan.zella.R;
import com.emcan.zella.SharedPrefManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Complains_Detaills extends Fragment {

    View view;
    Complain_response.Complain complain;
    ArrayList <Complain_response.Messages_model> messages;
    LinearLayout contain;
    EditText comment;
    ScrollView scrollView;
    String lang;
    AppCompatActivity activity;
    Typeface typeface;

    public Complains_Detaills() {
        // Required empty public constructor
    }


    public static Complains_Detaills newInstance(Complain_response.Complain complain_id) {
        Complains_Detaills fragment = new Complains_Detaills();
        Bundle args = new Bundle();
        args.putSerializable("id", complain_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            complain = (Complain_response.Complain) getArguments().getSerializable("id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_complains__detaills, container, false);
        activity= (AppCompatActivity) getActivity();
        lang= SharedPrefManager.getInstance(activity).getLang();
        if(lang.equals("en")){
            typeface = ResourcesCompat.getFont(activity, R.font.amaranth_bold);
        }
        if(lang.equals("ar")){
            typeface= ResourcesCompat.getFont(activity, R.font.bein_black);
        }
        messages=new ArrayList<>();
        messages=complain.getMessages();

        contain=view.findViewById(R.id.container);
        scrollView=view.findViewById(R.id.scrollview);
        LayoutInflater inflate = LayoutInflater.from(getContext());
//
//        final RelativeLayout
//                layout1 = (RelativeLayout) inflate.inflate(R.layout.my_message, null, false);
//        TextView title1 = layout1.findViewById(R.id.text);
//        title1.setText(complain.getContent());
//        title1.setTypeface(m_typeFace);
//        contain.addView(layout1);

        if(messages.size()>0){
            for (int i=0;i< messages.size();i++) {

                if (messages.get(i).getType().equals("1")) {
                    final RelativeLayout
                            layout = (RelativeLayout) inflate.inflate(R.layout.my_message, null, false);
                    TextView title = layout.findViewById(R.id.text);
                    title.setText(messages.get(i).getContent());
                    title.setTypeface(typeface);

                    contain.addView(layout);
                } else {
                    if (messages.get(i).getType().equals("0")) {

                        final RelativeLayout
                                layout = (RelativeLayout) inflate.inflate(R.layout.sofra_message, null, false);
                        TextView title = layout.findViewById(R.id.text);
                        title.setText(messages.get(i).getContent());
                        title.setTypeface(typeface);
                        contain.addView(layout);

                    }
                }
            }
        }
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });

         comment=view.findViewById(R.id.comment);
        Button send=view.findViewById(R.id.send);
        comment.setTypeface(typeface);
        send.setTypeface(typeface);

        comment.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                                              @Override
                                              public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {


                                                  scrollView.post(new Runnable() {
                                                      @Override
                                                      public void run() {
                                                          scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                                                      }
                                                  });

                                                  return false;
                                              }
        });

        send.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        if (comment.getText().toString().length() < 1 || comment.getText().toString().equals("")) {
                                       //     Toast.makeText(context, "رجاء ادخل رسالتك", Toast.LENGTH_SHORT).show();
                                        } else {

                                            add_comment(comment.getText().toString());
                                            comment.setText("");
                                        }
                                    }
                                }
        );


        return view;
    }


    private void add_comment(final String txt){

        Api_Service requestInterface = Config.getClient().create(Api_Service.class);
        Complain_response.Complain message=new Complain_response.Complain();
        message.setMessage_type_id("1");
        message.setClient_id(SharedPrefManager.getInstance(getContext()).getUser().getClient_id());
        message.setContent(comment.getText().toString());
        message.setComplaint_id(complain.getComplaint_id());


        final Call<Complain_response> res = requestInterface.add_message(message);
        res.enqueue(new Callback<Complain_response>() {
            @Override
            public void onResponse(Call<Complain_response> call, Response<Complain_response> response) {

                if (response.body().getSuccess() == 1) {
                    Toast.makeText(getActivity(), activity.getResources().getString(R.string.msgsent), Toast.LENGTH_LONG).show();
                    LayoutInflater inflate = LayoutInflater.from(getContext());
                    final RelativeLayout
                            layout = (RelativeLayout) inflate.inflate(R.layout.my_message, null, false);
                    TextView title = layout.findViewById(R.id.text);
                    title.setText(txt);

                    contain.addView(layout);
                    scrollView.post(new Runnable() {
                        @Override
                        public void run() {
                            scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                        }
                    });

                }
                else{
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<Complain_response> call, Throwable t) {
                Toast.makeText(getActivity(), activity.getResources().getString(R.string.errortryagain), Toast.LENGTH_LONG).show();

            }
        });
    }



}
