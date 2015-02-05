package com.hony.app.Utilities;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.hony.app.Model.ImageGroup;
import com.hony.app.R;

import java.io.IOException;

public class ImageLoader extends AsyncTask<ImageGViewPair, Void, DrawableViewPair> {

    // Only the first imageGroup is used, all others are ignored
    @Override
    protected DrawableViewPair doInBackground(ImageGViewPair... imageGViewPairs) {
        ImageGroup imageGroup = imageGViewPairs[0].imageGroup;
        ImageView view = imageGViewPairs[0].view;
        try {
            return new DrawableViewPair(imageGroup.getDrawable(), view);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(DrawableViewPair drawableViewPair) {
        if (drawableViewPair.drawable == null) {
            // TODO: Report error
            Log.e("LOADING", "Error loading image");
            return;
        }
        drawableViewPair.view.setImageDrawable(drawableViewPair.drawable);
    }
}
