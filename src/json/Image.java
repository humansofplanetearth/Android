
package json;

public class Image{
   	private Meta meta;
   	private Response response;
   	
   	public Image() {
   		
   	}

 	public Meta getMeta(){
		return this.meta;
	}
	public void setMeta(Meta meta){
		this.meta = meta;
	}
 	public Response getResponse(){
		return this.response;
	}
	public void setResponse(Response response){
		this.response = response;
	}
}
