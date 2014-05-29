
package json;

public class Notes{
   	private String blog_name;
   	private String blog_url;
   	private String post_id;
   	private String timestamp;
   	private String type;

   	public Notes() {
   	}
   	
 	public String getBlog_name() {
		return this.blog_name;
	}
 	
	public void setBlog_name(String blog_name) {
		this.blog_name = blog_name;
	}
 	public String getBlog_url() {
		return this.blog_url;
	}
 	
	public void setBlog_url(String blog_url) {
		this.blog_url = blog_url;
	}
	
 	public String getPost_id(){
		return this.post_id;
	}
	public void setPost_id(String post_id) {
		this.post_id = post_id;
	}
	
 	public String getTimestamp() {
		return this.timestamp;
	}
 	
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
 	public String getType() {
		return this.type;
	}
 	
	public void setType(String type) {
		this.type = type;
	}
}
