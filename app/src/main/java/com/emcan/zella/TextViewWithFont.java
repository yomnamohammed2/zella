package com.emcan.zella;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.res.ResourcesCompat;

import java.util.Locale;

public class TextViewWithFont extends AppCompatTextView {

    private Context context;

    public TextViewWithFont(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initFont();
    }

    public TextViewWithFont(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        initFont();
    }

    public TextViewWithFont(Context context) {
        super(context);
        this.context = context;
        initFont();
    }

    private void initFont() {
        Typeface fontType;
        if (SharedPrefManager.getInstance(context).getLang().equals("en")) {
            fontType = ResourcesCompat.getFont(getContext(), R.font.amaranth_bold);//"Amaranth-Regular.otf");
        } else {
            fontType = ResourcesCompat.getFont(getContext(), R.font.bein_black);
        }
        this.setTypeface(fontType);
    }


    private Locale getCurrentLocale(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            return context.getResources().getConfiguration().getLocales().get(0);
        } else{
            //noinspection deprecation
            return context.getResources().getConfiguration().locale;
        }
    }

}