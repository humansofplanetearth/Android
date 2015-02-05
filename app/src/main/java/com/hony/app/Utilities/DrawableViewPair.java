package com.hony.app.Utilities;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class DrawableViewPair {
    public Drawable drawable;
    public ImageView view;

    public DrawableViewPair(Drawable drawable, ImageView view) {
        this.drawable = drawable;
        this.view = view;
    }
}