package resources;

import java.util.LinkedList;

public class ChatRoomDatabase {

	private LinkedList<ChatRoom> chatrooms=new LinkedList<>();
	
	
	public ChatRoomDatabase() {
	
	}

	public void addChatRoom(ChatRoom chatroom){chatrooms.add(chatroom);}
	public LinkedList<String> getChatroomsList(){
	    LinkedList<String> names=new LinkedList<>();
		for(ChatRoom room:chatrooms){
			names.add(room.getName());
		}
		
		return names;
	}
    public ChatRoom getChatRoom(String name){
    	ChatRoom room=null;
    	for(ChatRoom _room:chatrooms)
    	{
    		if(_room.getName().toLowerCase().equals(name.toLowerCase())){
    			room=_room;
    			break;
    		}
    	}
    	return room;
    }
    
}
