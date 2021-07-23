package requestHandlers;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

import resources.ChatRoom;
import resources.ChatRoomDatabase;
import resources.Queries;
import resources.QueryMessage;
import resources.UserMessage;
import servers.Server;

public class ChatRoomActivities {
    private ObjectOutputStream oos;
    private LinkedList<Object> content;
    private ChatRoomDatabase chatRoomDatabase;
    
    public ChatRoomActivities(ObjectOutputStream oos,LinkedList<Object> content){
    	chatRoomDatabase=Server.getChatRoomDatabase();
    	this.oos=oos;
    	this.content=content;
    
    }
    
    public void addChatRoomMessage()throws IOException{
    	
    	String name=(String)content.get(0);
    	chatRoomDatabase.getChatRoom(name).addMessage((UserMessage)content.get(1));
        oos.writeObject(new QueryMessage(Queries.SERVER_REPLY, null, 1));
        System.out.println("ChatRoomMessage Added :: Chatroom : "+chatRoomDatabase.getChatRoom(name)+" Message : "+(UserMessage)content.get(1));
    }
    public void getChatRoomMessage() throws IOException{
    	String name=(String)content.get(0);
    	long timeStamp=(Long)content.get(1);
    	content.clear();
    	content.add(chatRoomDatabase.getChatRoom(name).getMessage(timeStamp));
    	oos.writeObject(new QueryMessage(Queries.SERVER_REPLY, content, 1)); 
    }
    public void haveNewMessage() throws IOException{
    	boolean has=false;
    	
		String from = (String) content.get(0);
		long timeStamp = (Long) content.get(1);
		if (chatRoomDatabase.getChatRoom(from).haveNewMessage(timeStamp)) {
			has = true;
		}
		LinkedList<Object> content = new LinkedList<>();
		content.clear();
		content.add(has);
		oos.writeObject(new QueryMessage(Queries.SERVER_REPLY, content, 1));

    }
    public void getChatRoomList() throws IOException{
    	LinkedList<Object> content =new LinkedList<>();
    	content.clear();
    	content.add(chatRoomDatabase.getChatroomsList());
    	oos.writeObject(new QueryMessage(Queries.SERVER_REPLY, content, 1));
        	
    }
    public void createNewChatRoom()throws IOException{
    	String name=(String)content.get(0);
    	chatRoomDatabase.addChatRoom(new ChatRoom(name));
    	oos.writeObject(new QueryMessage(Queries.SERVER_REPLY,null,1));
    }

}
