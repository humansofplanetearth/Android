package json;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;
import java.net.URL;

public class BlogTest  
{
	public static void main (String args[]) throws JsonParseException, JsonMappingException, IOException 
	{
		Blog blog = null;
		URL jasonUrl = new URL("http://api.tumblr.com/v2/blog/humansofnewyork.com/posts?api_key=7ag2CJXOuxuW3vlVS5wQG6pYA6a2ZQcSCjzZsAp2pDbVwf3xEk&notes_info=true&filter=text");
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		blog = (Blog) mapper.readValue(jasonUrl, Blog.class);
		System.out.println(blog);
		
		
		//Different attempt
		JsonFactory f = new MappingJsonFactory();
		com.fasterxml.jackson.core.JsonParser jp = f.createParser(jasonUrl);
		JsonToken current;
		current = jp.nextToken();
		
		//see if this was valid
		if(current != JsonToken.START_OBJECT)
		{
			System.out.println("Error: root should be object!");
		}
		
		while(jp.nextToken() != JsonToken.END_OBJECT)
		{
			String fieldName = jp.getCurrentName();
			
			//move from field name to field value
			current = jp.nextToken();
			if(fieldName.equals("response"))
			{
				if (current == JsonToken.START_ARRAY) 
				{
		              // For each of the records in the array
		              while (jp.nextToken() != JsonToken.END_ARRAY) 
		              {
		            	  JsonNode node = jp.readValueAsTree();
		                  // And now we have random access to everything in the object
		                  System.out.println("blog name: " + node.get("blog_name").asText());
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
}








