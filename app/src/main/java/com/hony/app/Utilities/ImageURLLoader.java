package com.hony.app.Utilities;

import com.hony.app.Model.ImageGroup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.apache.commons.io.IOUtils;

public class ImageURLLoader {

    private final static String baseUrl = "http://api.tumblr.com/v2/blog/humansofnewyork.com/posts/photo";
    private final static String tumblrAPIKey = "7ag2CJXOuxuW3vlVS5wQG6pYA6a2ZQcSCjzZsAp2pDbVwf3xEk";
    private final static int imageGroupsPerQuery = 3;
    private int imageGroupOffset = 0;
    private List<ImageGroup> imageGroups;
    private int imageGroupsReported = 0;

    public ImageURLLoader() {
        this.imageGroups = new ArrayList<ImageGroup>();
    }

    public ImageGroup next() throws IOException, JSONException {
        if (imageGroupsReported >= imageGroups.size()) {
            this.loadImageGroups();
        }
        return imageGroups.get(imageGroupsReported++);
    }

    private void loadImageGroups() throws IOException, JSONException {
        URL tumblrAPIURL = new URL(baseUrl +
                "?api_key=" + tumblrAPIKey +
                "&filter=text" +
                "&limit=" + imageGroupsPerQuery +
                "&offset=" + imageGroupOffset);
        JSONObject jsonRoot = readJsonFromUrl(tumblrAPIURL);
        JSONObject jsonMeta = jsonRoot.getJSONObject("meta");
        if ((! jsonMeta.getString("status").equals("200")) ||
                (! jsonMeta.getString("msg").equals("OK"))) {
            // TODO: Show a message indicating that there is a problem loading the image URLs using the Tumblr API
        }
        JSONObject jsonResponse = jsonRoot.getJSONObject("response");
        JSONArray jsonPosts = jsonResponse.getJSONArray("posts");
        for (int i = 0; i < jsonPosts.length(); ++ i) {
            JSONObject post = jsonPosts.getJSONObject(i);
            String caption = post.getString("caption");
            JSONArray photos = post.getJSONArray("photos");
            for (int j = 0; j < photos.length(); ++ j) {
                JSONObject photo = photos.getJSONObject(j);
                ImageGroup imageGroup = new ImageGroup(caption);
                JSONArray altSizes = photo.getJSONArray("alt_sizes");
                // Process the alternative sizes of the photo
                for (int k = 0; k < altSizes.length(); ++ k) {
                    JSONObject sizePhoto = altSizes.getJSONObject(k);
                    URL imageUrl = new URL(sizePhoto.getString("url"));
                    int imageWidth = sizePhoto.getInt("width");
                    int imageHeight = sizePhoto.getInt("height");
                    imageGroup.addImage(imageUrl, imageWidth, imageHeight);
                }
                imageGroups.add(imageGroup);
                imageGroupOffset++;
            }
        }

    }

    // FROM: http://stackoverflow.com/a/4308662/1928529
    public static JSONObject readJsonFromUrl(URL url) throws IOException, JSONException {
        InputStream is = url.openStream();
        try {
            String jsonText = IOUtils.toString(new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8"))));
            return new JSONObject(jsonText);
        } finally {
            is.close();
        }
    }
}
