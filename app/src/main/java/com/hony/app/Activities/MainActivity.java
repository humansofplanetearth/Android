package com.hony.app.Activities;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.hony.app.R;
import com.hony.app.Utilities.PictureURLGetter;

import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    List<URL> urls_url;
    GridView grid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        grid = (GridView) findViewById(R.id.gvGrid);
        new DownloadImageTask().execute();
    }

    // FIXME: Experimental, copied from (http://stackoverflow.com/questions/3375166/android-drawable-images-from-url)
    Drawable drawable_from_url(URL url, String src_name) throws java.net.MalformedURLException, java.io.IOException {
        return Drawable.createFromStream(((InputStream)url.getContent()), src_name);
    }

    private class DownloadImageTask extends AsyncTask<Void, Void, List> {
        // FIXME: Experimental code

        // TODO: Do something sensible if there are multiple urls
        @Override
        protected List<URL> doInBackground(Void... notUsed) {
            PictureURLGetter pictureURLGetter = new PictureURLGetter();
            try {
                pictureURLGetter.loadURLs();
                urls_url = pictureURLGetter.getURL();

              //  URL imageURL = pictureURLGetter.next();
                //Drawable drawable = drawable_from_url(imageURL, "");
                return urls_url;
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
        protected void onPostExecute(List urls) {

            fillGridView();
           /** if (drawable == null) {
                // TODO: Show an error message somewhere
            }
            ImageView imgView=(ImageView) findViewById(R.id.imageView);
            imgView.setImageDrawable(drawable);
            imgView.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, ImageActivity.class);
                    startActivity(intent);
                }
            });
            */

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
     * Fills Gridview
     */
    public void fillGridView() {

        ArrayAdapter<URL> adapter = new ArrayAdapter<URL>(this,R.layout.texts ,urls_url);
        grid.setAdapter(adapter);
    }
}
