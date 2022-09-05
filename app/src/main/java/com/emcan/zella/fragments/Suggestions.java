package com.emcan.zella.fragments;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.emcan.zella.Config;
import com.emcan.zella.ConnectionDetection;
import com.emcan.zella.R;
import com.emcan.zella.RealPathUtil;
import com.emcan.zella.SharedPrefManager;
import com.emcan.zella.activities.MainActivity;
import com.emcan.zella.adapters.Images_Adapter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;


public class Suggestions extends Fragment {

    EditText title_message, content_message;
    Button send;
    ProgressBar progressBar;

    View view;
    Context context;
    private static int RESULT_LOAD_IMG = 1;

    Toolbar toolbar;
    TextView title, num;
    ImageView cart;

    TextView txt, txt1;
    RecyclerView recyclerView;
    Button add_photos;
    private Uri selectedImage;
    String realPathimg = "null";
    String path;
    ArrayList<String> images;

    String id;
    ConnectionDetection connectionDetection;
    AppCompatActivity activity1;
    int position;
    String lang;
    LinearLayoutManager layoutManager;
    Typeface typeface;

    public Suggestions() {
        // Required empty public constructor
    }


    public static Suggestions newInstance(String param1, String param2) {
        Suggestions fragment = new Suggestions();
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
        view = inflater.inflate(R.layout.fragment_suggestions, container, false);

        activity1 = (AppCompatActivity) getActivity();

        lang = SharedPrefManager.getInstance(activity1).getLang();
        if(lang.equals("en")){
            typeface = ResourcesCompat.getFont(activity1, R.font.amaranth_bold);
        }
        if(lang.equals("ar")){
            typeface= ResourcesCompat.getFont(activity1, R.font.bein_black);
        }
        init();

        setToolbar();


        content_message.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (view.getId() == R.id.rel_note) {
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


        RelativeLayout bar = activity1.findViewById(R.id.bar);
        bar.setVisibility(View.VISIBLE);
        ((MainActivity) activity1).select_icon("none");

        add_photos.setTypeface(typeface);
        add_photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload();
            }
        });

        send.setTypeface(typeface);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (title_message.getText().toString() == null || title_message.getText().toString().equals("") ||
                        content_message.getText().toString() == null || content_message.getText().toString().equals("")) {
                    Toast.makeText(context, activity1.getResources().getString(R.string.completedata), Toast.LENGTH_SHORT).show();


                } else {
                    InputMethodManager imm = (InputMethodManager) activity1.getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    send_complain();
                }
            }
        });


        return view;
    }

    private void send_complain() {
        if (connectionDetection.isConnected()) {
            new UploadFileToServer().execute("");
            progressBar.setVisibility(
                    View.VISIBLE
            );
            send.setEnabled(false);

        } else {
            Toast.makeText(getContext(), activity1.getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
        }
    }

    public void upload() {


        if (ActivityCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    2000);

            } else {

            Intent galleryIntent = new Intent();

//            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
//               MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            galleryIntent.setType("image/*");
            galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(galleryIntent, RESULT_LOAD_IMG);


        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode != RESULT_OK) return;
        else {

            if (requestCode == RESULT_LOAD_IMG) {

                ClipData cd = data.getClipData();

                if (cd == null) {
                    if (data.getData() != null) {
                        // images.clear();
                        realPathimg = RealPathUtil.getRealPath(context, data.getData());

                        Log.d("SIZE", images.size() + "");
                        if (images.size() + 1 <= 5) {
                            images.add(realPathimg);


                            Images_Adapter mAdapter = new Images_Adapter(context, images, this);
                            if(lang=="ar") {
                                 layoutManager
                                        = new LinearLayoutManager(activity1, LinearLayoutManager.HORIZONTAL, true);

                            }else{
                                 layoutManager
                                        = new LinearLayoutManager(activity1, LinearLayoutManager.HORIZONTAL, false);

                            }
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(mAdapter);
                        } else {
                            Toast.makeText(context, activity1.getResources().getString(R.string.add5image), Toast.LENGTH_SHORT).show();

                        }
                    }

                } else {

                    Log.e("++data", "" + data.getClipData().getItemCount());// Get count of image her

                    if (data.getClipData().getItemCount() > 5) {
                        //   adapter.notifyDataSetChanged();
                        Toast.makeText(context, activity1.getResources().getString(R.string.add5image), Toast.LENGTH_SHORT).show();

                    } else {
                        //  images.clear();

                        if (images.size() + data.getClipData().getItemCount() <= 5) {

                            for (int i = 0; i < data.getClipData().getItemCount(); i++) {
                                realPathimg = RealPathUtil.getRealPath(context, data.getClipData().getItemAt(i).getUri());

                                images.add(realPathimg);
                            }
                            Log.d("SIZE", images.size() + "");

                            Images_Adapter mAdapter = new Images_Adapter(context, images, this);
                            LinearLayoutManager layoutManager
                                    = new LinearLayoutManager(activity1, LinearLayoutManager.HORIZONTAL, false);

                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(mAdapter);
                        } else {
                            Toast.makeText(context, activity1.getResources().getString(R.string.add5image), Toast.LENGTH_SHORT).show();

                        }

                    }
                }
            }
        }


    }

    public void getposition(final int pos) {
        position = pos;
        images.remove(position);
        Images_Adapter mAdapter = new Images_Adapter(context, images, this);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(activity1, LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);


    }

    private class UploadFileToServer extends AsyncTask<String, Void, String> {


        public UploadFileToServer() {


        }

        @Override
        protected String doInBackground(String... strings) {

            Log.d("llllll", "kkkkkkkkkkkkkkk");
            return uploadFile();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            if (s != null) {
                try {

                    Log.d("xxxxxxx", s);

                    // String substr = s.substring(48, s.length());

                    Log.d("asynccccc add eln", "cut string " + s);
                    JSONObject jsonObj = new JSONObject(s);
                    //  Log.d("xxxxxxx eln", (int) jsonObj.get("success"));
//                    path = (String) jsonObj.get("file_path");
//                    path = path.replace("\\", "");

                    int success = (int) jsonObj.get("success");
                    progressBar.setVisibility(View.GONE);
                    if (success == 1) {

                        content_message.setText("");
                        title_message.setText("");
                        Toast.makeText(getContext(), activity1.getResources().getString(R.string.thanks),
                                Toast.LENGTH_SHORT).show();
                        images.clear();
                        Images_Adapter mAdapter = new Images_Adapter(context, images, Suggestions.this);
                        LinearLayoutManager layoutManager
                                = new LinearLayoutManager(activity1, LinearLayoutManager.HORIZONTAL, false);

                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(mAdapter);


                    } else {
                        Toast.makeText(getContext(), activity1.getResources().getString(R.string.errortryagain), Toast.LENGTH_SHORT).show();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;


            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(Config.upload);


            try {

                MultipartEntityBuilder builder = MultipartEntityBuilder.create()

                        .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                        .addPart("client_id", new StringBody(SharedPrefManager.getInstance(context).getUser().getClient_id())).
                                addPart("title", new StringBody(title_message.getText().toString(), Charset.forName("UTF-8")))
                        .addPart("content", new StringBody(content_message.getText().toString(), Charset.forName("UTF-8")));

                if (images.size() > 0) {
                    File sourceFile1 = new File(images.get(0));
                    builder.addPart("image_1", new FileBody(sourceFile1));
                }

                if (images.size() > 1) {
                    File sourceFile1 = new File(images.get(1));
                    builder.addPart("image_2", new FileBody(sourceFile1));
                }
                if (images.size() > 2) {
                    File sourceFile2 = new File(images.get(2));
                    builder.addPart("image_3", new FileBody(sourceFile2));

                }
                if (images.size() > 3) {
                    File sourceFile3 = new File(images.get(3));
                    builder.addPart("image_4", new FileBody(sourceFile3));
                }
                if (images.size() > 4) {
                    File sourceFile4 = new File(images.get(4));
                    builder.addPart("image_5", new FileBody(sourceFile4));
                }


                HttpEntity multiPartEntity = builder.build();

                httppost.setEntity(multiPartEntity);
                Log.d("xxxxxxx", " after  MultipartEntityBuilder ");

                HttpResponse response = httpclient.execute(httppost);

                String text = builder.toString();
                HttpEntity r_entity = response.getEntity();

                int statusCode = response.getStatusLine().getStatusCode();
                System.out.println("statusCode " + statusCode);
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                    Log.d("asynccccc", "sucess : " + responseString);
                } else {

                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;
                    Log.d("asynccccc", "error : " + statusCode);
                }


            } catch (ClientProtocolException e) {
                responseString = e.toString();
                System.out.println("responseString : " + responseString);
            } catch (IOException e) {
                responseString = e.toString();
                System.out.println("responseString : " + responseString);
            }


            return responseString;


        }
    }

    private void setToolbar() {
        toolbar = (Toolbar) activity1.findViewById(R.id.toolbar);
        activity1.setSupportActionBar(toolbar);

        RelativeLayout no = toolbar.findViewById(R.id.no_cart);
        RelativeLayout yes = toolbar.findViewById(R.id.yes_cart);



        num = toolbar.getRootView().findViewById(R.id.num);
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
        title.setText(activity1.getResources().getString(R.string.complains));
        ImageButton back = toolbar.findViewById(R.id.back);
        back.setVisibility(View.VISIBLE);

        cart.setVisibility(View.VISIBLE);
        ImageView menu = toolbar.findViewById(R.id.menu);
        menu.setVisibility(View.VISIBLE);


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

    private void init() {
        context = getContext();

        connectionDetection = new ConnectionDetection(getContext());
        title_message = view.findViewById(R.id.title);
        content_message = view.findViewById(R.id.content);
        send = view.findViewById(R.id.send);
        txt = view.findViewById(R.id.txt);
        txt1 = view.findViewById(R.id.txt1);
        add_photos = view.findViewById(R.id.add_photos);
        recyclerView = view.findViewById(R.id.recycler_view);
        progressBar = view.findViewById(R.id.progressBar);
        images = new ArrayList<>();

        title_message.setTypeface(typeface);
        content_message.setTypeface(typeface);
        add_photos.setTypeface(typeface);
        txt.setTypeface(typeface);
        txt1.setTypeface(typeface);
        send.setTypeface(typeface);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 2000: {


                if (ActivityCompat.checkSelfPermission(getActivity(),
                        android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                } else {

                    Intent galleryIntent = new Intent();

                    galleryIntent.setType("image/*");
                    galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(galleryIntent, RESULT_LOAD_IMG);


                }
                return;
            }


        }
    }
}
