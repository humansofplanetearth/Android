package com.hony.app.Utilities;

import java.io.IOException;
import java.net.URL;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

public class PictureURLGetter {

    final static String baseUrl = "http://api.tumblr.com/v2/blog/humansofnewyork.com/posts/photo";
    final static String tumblrAPIKey = "7ag2CJXOuxuW3vlVS5wQG6pYA6a2ZQcSCjzZsAp2pDbVwf3xEk";
    final static int picturesPerQuery = 3;
    final static int pictureOffset = 0;

    public PictureURLGetter() throws IOException {
        URL tumblrAPIURL = new URL(baseUrl +
                "?api_key=" + tumblrAPIKey +
                "&filter=text" +
                "&limit=" + picturesPerQuery +
                "&offset=" + pictureOffset);
        JsonFactory jsonFactory = new JsonFactory();
        JsonParser jsonParser = jsonFactory.createParser(tumblrAPIURL);

        jsonParser.nextToken(); // Skip the JsonToken.START_OBJECT
        processJsonRoot(jsonParser);
        jsonParser.close();
    }

    private void processJsonRoot(JsonParser jsonParser) throws IOException {
        while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
            String fieldName = jsonParser.getCurrentName();
            jsonParser.nextToken();
            if (fieldName.equals("response")) {
                processResponse(jsonParser);
            } else {
                jsonParser.skipChildren();
            }
        }
    }

    private void processResponse(JsonParser jsonParser) throws IOException {
        while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
            String fieldName = jsonParser.getCurrentName();
            jsonParser.nextToken();
            if (fieldName.equals("posts")) {
                processPosts(jsonParser);
            } else {
                jsonParser.skipChildren();
            }
        }
    }

    private void processPosts(JsonParser jsonParser) throws IOException {
        jsonParser.nextToken(); // Skip JsonToken.START_ARRAY
        while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
            processPost(jsonParser);
        }
    }

    private void processPost(JsonParser jsonParser) throws IOException {
        while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
            String fieldName = jsonParser.getCurrentName();
            jsonParser.nextToken();
            if (fieldName.equals("photos")) {
                System.err.println("Entering \"photos\"");
                processPhotos(jsonParser);
                System.err.println("Leaving \"photos\"");
            } else {
                jsonParser.skipChildren();
            }
        }
    }

    private void processPhotos(JsonParser jsonParser) throws IOException {
        jsonParser.skipChildren();
    }

    
    public static void main(String args[]) throws IOException {
        PictureURLGetter pictureURLGetter = new PictureURLGetter();
    }
}