package resources;

import java.util.LinkedList;

public class ChatRoom {

	
	private String name;
	private LinkedList<UserMessage> msg=new LinkedList<UserMessage>();
	
	public boolean haveNewMessage(long timeStamp){
		
		if(msg.size()<1)return false;
		if(msg.getLast().getTimestamp()>timeStamp){
			return true;
		}
		
		return false;
		
	}
	
	public ChatRoom(String name) {
	
	    this.name=name;
	}


	public String getName() {
		return name;
	}
    public LinkedList<UserMessage> getMessage(long timeStamp){
        LinkedList<UserMessage> _msg=new LinkedList<UserMessage>();
        for(UserMessage _msg_:msg)
        {
        	if(_msg_.getTimestamp()>timeStamp){
        		_msg.add(_msg_);
        	}
        }
    	
    	
    	return _msg;
    }
    public void addMessage(UserMessage userMsg){msg.add(userMsg);}
}
