package com.emcan.zella;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

public class Rounded_image_corners1 extends AppCompatImageView {

    private float radius = 50.0f;
    private Path path;
    private RectF rect;

    public Rounded_image_corners1(Context context) {
        super(context);
        init();
    }

    public Rounded_image_corners1(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Rounded_image_corners1(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        path = new Path();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        rect = new RectF(0, 0, this.getWidth(), this.getHeight());
        path.addRoundRect(rect, radius, radius, Path.Direction.CW);
        canvas.clipPath(path);
        super.onDraw(canvas);
    }
}