package com.hony.app.Utilities;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonParser {

    URL baseURL;

    JsonParser(URL baseURL) {
        this.baseURL = baseURL;
    }

    
    public static void main(String args[]) throws MalformedURLException {
        URL honyTumblrBaseURL = new URL("http://api.tumblr.com/v2/blog/humansofnewyork.com/posts/photo");
        JsonParser jsonParser = new JsonParser(honyTumblrBaseURL);
    }
}