package com.hony.app.Activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.hony.app.Model.ImageGroup;
import com.hony.app.R;
import com.hony.app.Utilities.PictureURLGetter;

import org.json.JSONException;

import java.io.IOException;

public class MainActivity extends ActionBarActivity implements OnTouchListener {

    PictureURLGetter pictureURLGetter = new PictureURLGetter();
    ImageView imageView;
    int currentPictureID = 0;
    ImageGroup currentImageGroup;

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private GestureDetector gestureDetector;
    View.OnTouchListener gestureListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) this.findViewById(R.id.image);
        // TODO: Set loading image
        new AcquireImageGroupTask().execute(currentPictureID);

        // Gesture detection
        gestureDetector = new GestureDetector(this, new MyGestureDetector());
        gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        };
        // Do this for each view added to the grid
        imageView.setOnTouchListener(gestureListener);
    }

    private class AcquireImageGroupTask extends AsyncTask<Integer, Void, ImageGroup> {

        // Only the first position is used, all others are ignored
        @Override
        protected ImageGroup doInBackground(Integer... positions) {
            int position = positions[0];
            // TODO: Use the position variable to load the right picture
            try {
                return pictureURLGetter.next();
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
        protected void onPostExecute(ImageGroup imageGroup) {
            if (imageGroup == null) {
                // TODO: Report error
                return;
            }
            MainActivity.this.currentImageGroup = imageGroup;
            new AcquireDrawableTask().execute(currentImageGroup);
        }
    }

    private class AcquireDrawableTask extends AsyncTask<ImageGroup, Void, Drawable> {

        // Only the first imageGroup is used, all others are ignored
        @Override
        protected Drawable doInBackground(ImageGroup... imageGroups) {
            ImageGroup imageGroup = imageGroups[0];
            try {
                return imageGroup.getDrawable();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Drawable drawable) {
            findViewById(R.id.loadingPanel).setVisibility(View.GONE);
            if (drawable == null) {
                // TODO: Report error
                return;
            }
            MainActivity.this.imageView.setImageDrawable(drawable);
        }
    }

    /**
     * Called when a touch event is dispatched to a view. This allows listeners to
     * get a chance to respond before the target view.
     *
     * @param v     The view the touch event has been dispatched to.
     * @param event The MotionEvent object containing full information about
     *              the event.
     * @return True if the listener has consumed the event, false otherwise.
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    // FIXME: From http://stackoverflow.com/a/938657/1928529
    class MyGestureDetector extends SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                    return false;
                // right to left swipe
                if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Toast.makeText(MainActivity.this, "Left Swipe", Toast.LENGTH_SHORT).show();
                }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Toast.makeText(MainActivity.this, "Right Swipe", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                // nothing
            }
            return false;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
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
