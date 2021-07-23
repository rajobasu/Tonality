package requestHandlers;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

import resources.MessageDatabase;
import resources.Queries;
import resources.QueryMessage;
import resources.UserMessage;
import servers.Server;

public class MessageExchange {
   
	
	private MessageDatabase msgDatabase;
    private LinkedList<Object> content;
	private ObjectOutputStream oos;
	public MessageExchange(ObjectOutputStream oos,LinkedList<Object> content){
		this.content=content;
	    this.oos=oos;
	    msgDatabase=Server.getMsgDatabase();
	}
    public void addMessage()throws IOException{
    
		UserMessage userMessage = (UserMessage) content.get(0);
		msgDatabase.addMessage(userMessage);
		content = null;
		oos.writeObject(new QueryMessage(Queries.SERVER_REPLY, null, 1));
		System.out.println("Message Received");

    }	
    public void getMessage()throws IOException{
    	
		String senderID = (String) content.get(0);
		String receiverID = (String) content.get(1);
		long time=(Long)content.get(2);
		content.clear();
		LinkedList<UserMessage> messages = msgDatabase.getMessage(time,senderID, receiverID);
		content.add(messages);
		oos.writeObject(new QueryMessage(Queries.SERVER_REPLY, content, 1));


    }
    public void haveNewMessage()throws IOException{
    	String senderID = (String) content.get(0);
		String receiverID = (String) content.get(1);
		long time=(Long)content.get(2);
		content.clear();
		boolean has=msgDatabase.haveNewMessage(time, senderID,receiverID);
		content.add(has);
		oos.writeObject(new QueryMessage(Queries.SERVER_REPLY, content, 1));
	

    }
   
}
