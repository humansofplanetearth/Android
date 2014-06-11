package com.hony.app.Activities;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.hony.app.R;
import com.hony.app.Utilities.PictureURLGetter;

import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    List<URL> urls;
    ImageView image;
    GridView grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        urls = new ArrayList<URL>();
        grid = (GridView) findViewById(R.id.gvGrid);

        new AcquireURLTask().execute();
    }

    // FIXME: Experimental, copied from (http://stackoverflow.com/questions/3375166/android-drawable-images-from-url)
    Drawable drawable_from_url(URL url, String src_name) throws java.net.MalformedURLException, java.io.IOException {
        return Drawable.createFromStream(((InputStream)url.getContent()), src_name);
    }

    private class AcquireURLTask extends AsyncTask<Void, Void, Drawable> {
        // FIXME: Experimental code

        Collection<URL> urls = new ArrayList<URL>();

        // TODO: Do something sensible if there are multiple urls
        @Override
        protected Drawable doInBackground(Void... notUsed) {
            PictureURLGetter pictureURLGetter = new PictureURLGetter();
            try {
                URL imageURL = pictureURLGetter.next();
                this.urls.add(imageURL);
              //  Drawable drawable = drawable_from_url(imageURL, "");
                return null;
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
            }
            /**ImageView imgView=(ImageView) findViewById(R.id.imageView);
            imgView.setImageDrawable(drawable);
            imgView.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, ImageActivity.class);
                    startActivity(intent);
                }
            });
             */
            MainActivity.this.urls.addAll(this.urls);
            fillGridView(MainActivity.this.urls);
        }

        @Override
        protected void onCancelled() {
            // TODO: Check if this should have some effect
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Fill girdView
     * @param url_list
     */
    private void fillGridView(List<URL> url_list) {

        ArrayAdapter<URL> adapter = new ArrayAdapter<URL>(this, R.layout.texts, url_list);
        grid.setAdapter(adapter);


    }
}
