package com.hony.app.Model;

import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ImageGroup {
    private List<Image> variants;
    private String caption;

    public ImageGroup(String caption) {
        variants = new ArrayList<Image>();
        this.caption = caption;
    }

    public void addImage(URL url, int width, int height) {
        Image image = new Image(url, width, height);
        variants.add(image);
    }

    public Drawable getDrawable() throws IOException {
        return variants.get(0).getDrawable();
    }
    
    public String getCaption() {
        return this.caption;
    }
}