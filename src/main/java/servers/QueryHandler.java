package servers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import requestHandlers.ChatRoomActivities;
import requestHandlers.Login;
import requestHandlers.MessageExchange;
import requestHandlers.ProfileUpdate;
import requestHandlers.Register;
import resources.Queries;
import resources.QueryMessage;

public class QueryHandler implements Runnable {

	
	private Socket socket;
	private Queries query;
	private QueryMessage queryMessage;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	public QueryHandler(Socket socket) {
		this.socket = socket;

	
	
	
		
	}

	public void run() {

		try {

			// START OF MAIN BODY
			ois = new ObjectInputStream(socket.getInputStream());
			oos = new ObjectOutputStream(socket.getOutputStream());

			queryMessage = (QueryMessage) ois.readObject();

			
			query = queryMessage.getQuery();

			// TAKE ACTION FOR QUERY
			switch (query) {
			case REGISTER: {
				new Register(oos, queryMessage.getContent());
				break;

			}
			case LOGIN: {
				new Login(oos, queryMessage.getContent());
				break;
			}
			case SEND_MESSAGE: {
			    new MessageExchange(oos, queryMessage.getContent()).addMessage();
				break;
			}
			case GET_MESSAGE: {
				new MessageExchange(oos, queryMessage.getContent()).getMessage();
				break;
			}
			case GET_CHATROOM_LIST: {
				new ChatRoomActivities(oos, queryMessage.getContent()).getChatRoomList();
				break;
			}
			case CREATE_NEW_CHAT_ROOM: {
				
				new ChatRoomActivities(oos, queryMessage.getContent()).createNewChatRoom();
				break;
			}

			case GET_CHAT_ROOM_MESSAGES: {
				new ChatRoomActivities(oos, queryMessage.getContent()).getChatRoomMessage();
				break;
			}
			case SEND_CHAT_ROOM_MESSAGE: {
				new ChatRoomActivities(oos, queryMessage.getContent()).addChatRoomMessage();
				break;
			}
			case HAVE_NEW_CHAT_ROOM_MESSAGE: {
				System.out.println("message received ");
				new ChatRoomActivities(oos, queryMessage.getContent()).haveNewMessage();
				break;
			}
			case HAVE_NEW_FRIEND_MESSAGE:{
				new MessageExchange(oos, queryMessage.getContent()).haveNewMessage();
				break;
			}
			case ADD_FRIEND: {
				new ProfileUpdate(oos, queryMessage.getContent()).addFriend();
				break;
			}
			case GET_FRIEND_LIST:{
			    new ProfileUpdate(oos, queryMessage.getContent()).getFriendList();
				break;	
			}
			case REQUEST_FRIEND:{
				new ProfileUpdate(oos, queryMessage.getContent()).addFriendRequest();
				break;
			}case GET_FRIEND_REQUST_LIST:{
				new ProfileUpdate(oos, queryMessage.getContent()).getFriendRequestList();
				break;
				
			}
			case UPDATE_PROFILE: {
				
				break;
			}
			case LOGOUT: {
				break;
			}
			case REFRESH:
				break;
			case SERVER_REPLY:
				break;
			
		}

			// END OF MAIN BODY
		} catch (Exception e) {
			System.out.println(System.currentTimeMillis());
			e.printStackTrace();
		} finally {
			try {
				oos.close();
				ois.close();

				socket.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

		
	}

}
