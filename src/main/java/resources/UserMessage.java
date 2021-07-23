package resources;

import java.io.Serializable;

public class UserMessage implements Serializable{

	private String senderID;
	private String receiverID;
	private String body;
	private long timeStamp;
	public UserMessage(String body,String senderID,String receiverID) {
		this.body=body;
		this.senderID=senderID;
		this.receiverID=receiverID;
	    this.timeStamp=System.currentTimeMillis();
	}
	
	public String getSenderID() {
		return senderID;
	}
	public String getReceiverID() {
		return receiverID;
	}
	public String getBody() {
		return body;
	}
    public long getTimestamp(){
    	return timeStamp;
    }
    public String toString(){
    	return body+" Time :: "+timeStamp;
    }
	
	
}
