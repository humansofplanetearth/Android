package com.hony.app.Model;

import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Image {
    URL url;
    int width;
    int height;
    Drawable drawable;

    Image(URL url) {
        this.url = url;
    }

    Image(URL url, int width, int height) {
        this(url);
        this.width = width;
        this.height = height;
    }

    Drawable getDrawable() throws IOException {
        if (drawable == null) {
            drawable = getDrawableFromURL(url);
        }
        return  drawable;
    }

    // FIXME: Experimental, copied from (http://stackoverflow.com/questions/3375166/android-drawable-images-from-url)
    private Drawable getDrawableFromURL(URL url) throws java.io.IOException {
        return Drawable.createFromStream(((InputStream)url.getContent()), "");
    }
}
