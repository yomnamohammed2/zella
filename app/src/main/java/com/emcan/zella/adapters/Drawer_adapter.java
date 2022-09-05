package com.emcan.zella.adapters;

import android.app.Activity;
import android.graphics.Typeface;
import androidx.core.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.emcan.zella.R;
import com.emcan.zella.SharedPrefManager;


public class Drawer_adapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] txt;
    private final int[] imageId;
    String lang;
    Typeface typeface;

    public Drawer_adapter(Activity context,
                          String[] txt, int[] imageId) {
        super(context, R.layout.drawer_list_item, txt);
        this.context = context;
        this.txt = txt;
        this.imageId = imageId;
        lang= SharedPrefManager.getInstance(context).getLang();
        if(lang.equals("en")){
            typeface = ResourcesCompat.getFont(context, R.font.amaranth_bold);
        }
        if(lang.equals("ar")){
            typeface= ResourcesCompat.getFont(context, R.font.bein_black);
        }
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.drawer_list_item, null, true);

            TextView txtTitle = (TextView) rowView.findViewById(R.id.name);

            ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);

            txtTitle.setText(txt[position]);
            txtTitle.setTypeface(typeface);

            imageView.setImageResource(imageId[position]);

        if(lang.equals("en")) {
        txtTitle.setTextSize(17);
        }


        return rowView;

    }


}