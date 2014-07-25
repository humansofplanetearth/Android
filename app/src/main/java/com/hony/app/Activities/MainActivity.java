package com.hony.app.Activities;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.hony.app.Model.ImageGroup;
import com.hony.app.R;
import com.hony.app.Utilities.ImageListAdapter;
import com.hony.app.Utilities.PictureURLGetter;

import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MainActivity extends ActionBarActivity {

//    PictureURLGetter pictureURLGetter = new PictureURLGetter();

    List<ImageGroup> pictures;
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pictures = new ArrayList<ImageGroup>();
        listview = (ListView) findViewById(R.id.list);

        ImageListAdapter adapter = new ImageListAdapter(this);
        listview.setAdapter(adapter);

//        new AcquireURLTask().execute();
    }

//    private class AcquireURLTask extends AsyncTask<Void, Void, Drawable> {
//        // FIXME: Experimental code
//
//        Collection<ImageGroup> pictures = new ArrayList<ImageGroup>();
//
//        @Override
//        protected Drawable doInBackground(Void... notUsed) {
//
//            try {
//                ImageGroup picture = pictureURLGetter.next();
//                this.pictures.add(picture);
//                picture = pictureURLGetter.next();
//                this.pictures.add(picture);
//                picture = pictureURLGetter.next();
//                this.pictures.add(picture);
//                Drawable drawable = picture.getDrawable();
//                return drawable;
//            } catch (IOException e) {
//                // TODO: Show a message indicating that something went wrong (most probably no internet connection)
//                e.printStackTrace();
//            } catch (JSONException e) {
//                // TODO: Show a message indicating that something went wrong (most probably no internet connection)
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Drawable drawable) {
//            if (drawable == null) {
//                // TODO: Show an error message somewhere
//            }
//
//            MainActivity.this.pictures.addAll(this.pictures);
//        }
//
//        @Override
//        protected void onCancelled() {
//            // TODO: Check if this should have some effect
//        }
//    }

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
}
