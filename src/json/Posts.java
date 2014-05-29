
package json;

import java.util.List;

public class Posts {
   	private String blog_name;
   	private String caption;
   	private String date;
   	private String format;
   	private List highlighted;
   	private Number id;
   	private String image_permalink;
   	private Number note_count;
   	private List notes;
   	private List photos;
   	private String post_url;
   	private String reblog_key;
   	private String short_url;
   	private String slug;
   	private String state;
   	private List tags;
   	private Number timestamp;
   	private String type;

   	public Posts() {
   		
   	}
 	public String getBlog_name(){
		return this.blog_name;
	}
	public void setBlog_name(String blog_name){
		this.blog_name = blog_name;
	}
 	public String getCaption(){
		return this.caption;
	}
	public void setCaption(String caption){
		this.caption = caption;
	}
 	public String getDate(){
		return this.date;
	}
	public void setDate(String date){
		this.date = date;
	}
 	public String getFormat(){
		return this.format;
	}
	public void setFormat(String format){
		this.format = format;
	}
 	public List getHighlighted(){
		return this.highlighted;
	}
	public void setHighlighted(List highlighted){
		this.highlighted = highlighted;
	}
 	public Number getId(){
		return this.id;
	}
	public void setId(Number id){
		this.id = id;
	}
 	public String getImage_permalink(){
		return this.image_permalink;
	}
	public void setImage_permalink(String image_permalink){
		this.image_permalink = image_permalink;
	}
 	public Number getNote_count(){
		return this.note_count;
	}
	public void setNote_count(Number note_count){
		this.note_count = note_count;
	}
 	public List getNotes(){
		return this.notes;
	}
	public void setNotes(List notes){
		this.notes = notes;
	}
 	public List getPhotos(){
		return this.photos;
	}
	public void setPhotos(List photos){
		this.photos = photos;
	}
 	public String getPost_url(){
		return this.post_url;
	}
	public void setPost_url(String post_url){
		this.post_url = post_url;
	}
 	public String getReblog_key(){
		return this.reblog_key;
	}
	public void setReblog_key(String reblog_key){
		this.reblog_key = reblog_key;
	}
 	public String getShort_url(){
		return this.short_url;
	}
	public void setShort_url(String short_url){
		this.short_url = short_url;
	}
 	public String getSlug(){
		return this.slug;
	}
	public void setSlug(String slug){
		this.slug = slug;
	}
 	public String getState(){
		return this.state;
	}
	public void setState(String state){
		this.state = state;
	}
 	public List getTags(){
		return this.tags;
	}
	public void setTags(List tags){
		this.tags = tags;
	}
 	public Number getTimestamp(){
		return this.timestamp;
	}
	public void setTimestamp(Number timestamp){
		this.timestamp = timestamp;
	}
 	public String getType(){
		return this.type;
	}
	public void setType(String type){
		this.type = type;
	}
}
