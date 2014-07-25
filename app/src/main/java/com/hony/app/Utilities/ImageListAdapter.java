package com.hony.app.Utilities;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.hony.app.Model.Image;
import com.hony.app.Model.ImageGroup;
import com.hony.app.R;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class ImageListAdapter extends ArrayAdapter<Image> {

    PictureURLGetter pictureURLGetter = new PictureURLGetter();

    public ImageListAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public int getCount() {
        return 20;
    }

    private class AcquireImageTask extends AsyncTask<ImageView, Void, Drawable> {
        // FIXME: Experimental code

        Collection<ImageGroup> pictures = new ArrayList<ImageGroup>();

        ImageView imageView;

        @Override
        protected Drawable doInBackground(ImageView... views) {

            for (ImageView imageView : views) {
                this.imageView = imageView;
            }

            try {
                ImageGroup picture = pictureURLGetter.next();
                this.pictures.add(picture);
                Drawable drawable = picture.getDrawable();
                return drawable;
            } catch (IOException e) {
                // TODO: Show a message indicating that something went wrong (most probably no internet connection)
                e.printStackTrace();
            } catch (JSONException e) {
                // TODO: Show a message indicating that something went wrong (most probably no internet connection)
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Drawable drawable) {
            if (drawable == null) {
                // TODO: Show an error message somewhere
                return;
            }
            this.imageView.setImageDrawable(drawable);
        }

        @Override
        protected void onCancelled() {
            // TODO: Check if this should have some effect
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new ImageView(this.getContext());
            convertView.setBackgroundColor(Color.BLUE);
        } else {
            convertView.setBackgroundColor(Color.RED);
        }
        ((ImageView)convertView).setImageDrawable(this.getContext().getResources().getDrawable(R.drawable.loading));
        new AcquireImageTask().execute((ImageView)convertView);
        return convertView;
    }
}
