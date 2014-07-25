package com.hony.app.Utilities;

import com.hony.app.Model.ImageGroup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PictureURLGetter {

    private final static String baseUrl = "http://api.tumblr.com/v2/blog/humansofnewyork.com/posts/photo";
    private final static String tumblrAPIKey = "7ag2CJXOuxuW3vlVS5wQG6pYA6a2ZQcSCjzZsAp2pDbVwf3xEk";
    private final static int picturesPerQuery = 3;
    private int pictureOffset = 0;
    private List<ImageGroup> pictures;
    private int picturesReported = 0;

    public PictureURLGetter() {
        this.pictures = new ArrayList<ImageGroup>();
    }

    public ImageGroup next() throws IOException, JSONException {
        if (picturesReported >= pictures.size()) {
            this.loadURLs();
        }
        return pictures.get(picturesReported++);
    }

    private void loadURLs() throws IOException, JSONException {
        URL tumblrAPIURL = new URL(baseUrl +
                "?api_key=" + tumblrAPIKey +
                "&filter=text" +
                "&limit=" + picturesPerQuery +
                "&offset=" + pictureOffset);
        JSONObject jsonRoot = readJsonFromUrl(tumblrAPIURL);
        JSONObject jsonMeta = jsonRoot.getJSONObject("meta");
        if ((! jsonMeta.getString("status").equals("200")) ||
                (! jsonMeta.getString("msg").equals("OK"))) {
            // TODO: Show a message indicating that there is a problem loading the image URLs using the Tumblr API
        }
        JSONObject jsonResponse = jsonRoot.getJSONObject("response");
        JSONArray jsonPosts = jsonResponse.getJSONArray("posts");
        int numberOfPosts = jsonPosts.length();
        for (int i = 0; i < numberOfPosts; ++ i) {
            JSONObject post = jsonPosts.getJSONObject(i);
            JSONArray photos = post.getJSONArray("photos");
            int numberOfPhotos = photos.length();
            for (int j = 0; j < numberOfPhotos; ++ j) {
                JSONObject photo = photos.getJSONObject(j);
                ImageGroup imageGroup = new ImageGroup();
                JSONArray altSizes = photo.getJSONArray("alt_sizes");
                // Process the alternative sizes of the photo
                int numberOfAltSizes = altSizes.length();
                for (int k = 0; k < numberOfAltSizes; ++ k) {
                    JSONObject sizePhoto = altSizes.getJSONObject(k);
                    URL imageUrl = new URL(sizePhoto.getString("url"));
                    int imageWidth = sizePhoto.getInt("width");
                    int imageHeight = sizePhoto.getInt("height");
                    imageGroup.addImage(imageUrl, imageWidth, imageHeight);
                }
                pictures.add(imageGroup);
                pictureOffset ++;
            }
        }

    }

    // FROM: http://stackoverflow.com/a/4308662/1928529
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    // FROM: http://stackoverflow.com/a/4308662/1928529
    public static JSONObject readJsonFromUrl(URL url) throws IOException, JSONException {
        InputStream is = url.openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    
    public static void main(String args[]) throws IOException, JSONException {
        PictureURLGetter pictureURLGetter = new PictureURLGetter();
        for (int i = 0; i < 20; ++ i) {
            System.out.println(pictureURLGetter.next());
        }
    }
}
