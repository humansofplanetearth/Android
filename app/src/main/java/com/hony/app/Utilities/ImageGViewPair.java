package com.hony.app.Utilities;

import android.widget.ImageView;

import com.hony.app.Model.ImageGroup;

public class ImageGViewPair {
    public ImageGroup imageGroup;
    public ImageView view;
    
    public ImageGViewPair(ImageGroup imageGroup, ImageView view){
        this.imageGroup = imageGroup;
        this.view = view;
    }
}