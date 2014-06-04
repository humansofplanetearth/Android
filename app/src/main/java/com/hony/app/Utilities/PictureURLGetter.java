package com.hony.app.Utilities;

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
    private List<URL> urls;
    private int urlsReported = 0;

    public PictureURLGetter() {
        this.urls = new ArrayList<URL>();
    }

    public URL next() throws IOException, JSONException {
        if (urlsReported >= urls.size()) {
            this.loadURLs();
        }
        return urls.get(urlsReported++);
    }

    private void loadURLs() throws IOException, JSONException {
        URL tumblrAPIURL = new URL(baseUrl +
                "?api_key=" + tumblrAPIKey +
                "&filter=text" +
                "&limit=" + picturesPerQuery +
                "&offset=" + pictureOffset);
        JSONObject jsonRoot = readJsonFromUrl(tumblrAPIURL);
        // TODO: Check JSONObject "meta"
        JSONObject jsonResponse = jsonRoot.getJSONObject("response");
        JSONArray jsonPosts = jsonResponse.getJSONArray("posts");
        int numberOfPosts = jsonPosts.length();
        for (int i = 0; i < numberOfPosts; ++ i) {
            JSONObject post = jsonPosts.getJSONObject(i);
            JSONArray photos = post.getJSONArray("photos");
            int numberOfPhotos = photos.length();
            for (int j = 0; j < numberOfPhotos; ++ j) {
                JSONObject photo = photos.getJSONObject(j);
                // FIXME: Not sure if "original_size" is always available,
                // because it's not specified in the api documentation (http://www.tumblr.com/docs/en/api/v2)
                JSONObject original_size = photo.getJSONObject("original_size");
                String imageUrl = original_size.getString("url");
                urls.add(new URL(imageUrl));
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