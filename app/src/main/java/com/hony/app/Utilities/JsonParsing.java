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

public class JsonParsing {
    private static final String layer1 = "response";
    private static final String layer2 = "posts";
    private static final String layer3 = "photos";
    //private static final String layer4 = "photos";
    private static final String dataField1 = "caption";
    private static final String dataField2 = "url";

    private URL jsonURL;
    private int counter;
    // TODO: REMOVE private String fieldName;
    private ObjectNode node;
    private com.fasterxml.jackson.core.JsonParser jsonParser;

    public JsonParsing(URL jsonLocation) {
        this.jsonURL = jsonLocation;
    }

    // TODO: simplify and break into smaller methods
    public void parseFile(int layers, List<String> data) throws IOException {
        JsonFactory factory = new JsonFactory();
        jsonParser = factory.createParser(jsonURL);

        //Wrapper for jp to a java object with its parser set as jp
        ObjectMapper mapper = new ObjectMapper(factory);
        jsonParser.setCodec(mapper);
        node = mapper.createObjectNode();

        JsonToken current;
        current = jsonParser.nextToken();

        //see jsonToken registers as valid
        if(current != JsonToken.START_OBJECT) {
            System.out.println("Error: root should be object!");
        }

        counter = 0;
        //begin the parsing!
        while(jsonParser.nextToken() != JsonToken.END_OBJECT) {
            goLayer(layers);
            if(counter == layers) {
                System.out.println("1");
                String info = getData(dataField2);
                System.out.println(info);
                data.add(info);

                counter = 2;
            }
        }
    }

    private void goLayer(int layers) throws IOException {
        String fieldToFind = setLayerString(counter + 1);

        while(jsonParser.nextToken() != JsonToken.END_OBJECT) {
            JsonToken current;
            String fieldName = jsonParser.getCurrentName();
            current = jsonParser.nextToken();

            //Find the area to start searching the file
            if(fieldName != null && fieldName.equals(fieldToFind)) {
                counter += 1;
                return;
            } else {
                jsonParser.skipChildren();
                System.out.println("Unprocessed property while " + counter + " layers deep. Property: " + fieldName);
            }
        }
    }

    private String getData(String dataName) throws IOException {
        String fieldName = null;
        while (! dataName.equals(fieldName)) {
            JsonToken current = jsonParser.nextToken();
            fieldName = jsonParser.getCurrentName();
        }
        node = jsonParser.readValueAsTree();
        return node.get(dataName).asText();
    }

    private String setLayerString(int layer) {
        switch(layer) {
            case 1: return layer1;
            case 2: return layer2;
            case 3: return layer3;
            //case 4: return layer4;
            default: return layer1;
        }
    }

    public static void main(String args[]) throws IOException {
        URL url = new URL("http://api.tumblr.com/v2/blog/humansofnewyork.com/posts?api_key=7ag2CJXOuxuW3vlVS5wQG6pYA6a2ZQcSCjzZsAp2pDbVwf3xEk&notes_info=true&filter=text");

        JsonParsing parse = new JsonParsing(url);

        List<String> urls = new ArrayList<String>();
        parse.parseFile(3, urls);

        System.out.println(urls.size());
    }
}