package com.hony.app.Activities;

import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.hony.app.R;
import com.hony.app.Utilities.PictureURLGetter;

import android.content.Intent;

import java.io.IOException;
import java.net.URL;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // FIXME: Experimental code
        PictureURLGetter pictureURLGetter = new PictureURLGetter();
        try {
            URL imageURL = pictureURLGetter.next();
            // http://stackoverflow.com/questions/8937180/how-to-show-image-in-android-using-java-code
            // http://stackoverflow.com/questions/3375166/android-drawable-images-from-url

        } catch (IOException e) {
            // TODO: Show a message indicating that something went wrong (most probably no internet connection)
            e.printStackTrace();
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
}
