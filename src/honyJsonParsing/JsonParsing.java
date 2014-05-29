package honyJsonParsing;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.MappingJsonFactory;

public class JsonParsing {
	/**
	 * ArrayList containing the list of urls of the pictures
	 */
	private ArrayList<URL> urlList;
	
	/**
	 * ArrayList containing the picture information for each picture
	 */
	private ArrayList<String> captions;
	
	private String blogName;
	private URL jsonURL;
	
	public JsonParsing(URL jsonLocation, String blogNameToFind)
	{
		jsonURL = jsonLocation;
		blogName = blogNameToFind;
	}
	
	public void parseFile() throws JsonParseException, IOException //TODO, simplify and break into smaller methods
	{
		JsonFactory f = new MappingJsonFactory();
		com.fasterxml.jackson.core.JsonParser jp = f.createParser(jsonURL);
		JsonToken current;
		current = jp.nextToken();
		
		//see if this was valid
		if(current != JsonToken.START_OBJECT)
		{
			System.out.println("Error: root should be object!");
		}
		
		//Start searching the json file for the blog information
		while(jp.nextToken() != JsonToken.END_OBJECT)
		{
			String fieldName = jp.getCurrentName();
			current = jp.nextToken();
			
			//Find the area to start searching the file
			if(fieldName != null && fieldName.equals("response"))
			{
				while(jp.nextToken() != JsonToken.END_OBJECT)
				{
					current = jp.nextToken();
					fieldName = jp.getCurrentName();
					if(fieldName != null && fieldName.equals("posts"))
					{
						System.out.println("progress");
						
						//repeat this long horrible process 
						while(jp.nextToken() != JsonToken.END_OBJECT)
						{
							current = jp.nextToken();
							fieldName = jp.getCurrentName();
							if(fieldName != null && fieldName.equals("image_permalink"))
							{
								System.out.println("We are at the first picture link!");
								
								/*current = jp.getCurrentToken();
								if (current == JsonToken.START_ARRAY) 
								{
									System.out.println("worked");
								}*/
								
								/*JsonNode node = jp.readValueAsTree();
								System.out.println(node.get(":"));*/;
								System.out.println(jp.getText());
								System.out.println(jp.getText());
							}
						}
					}
					else 
					{
						System.out.println("Unprocessed property: " + fieldName);
				      	jp.skipChildren();
					}
				}
			} 
			else
			{
				System.out.println("Unprocessed property: " + fieldName);
		      	jp.skipChildren();
			}
		}
	}
	
	
	public static void main(String args[]) throws MalformedURLException, JsonParseException, IOException
	{
		URL url = new URL("http://api.tumblr.com/v2/blog/humansofnewyork.com/posts?api_key=7ag2CJXOuxuW3vlVS5wQG6pYA6a2ZQcSCjzZsAp2pDbVwf3xEk&notes_info=true&filter=text");
		String blog = "humansofnewyork";
		
		JsonParsing parse = new JsonParsing(url, blog);
		parse.parseFile();
	}
	
}