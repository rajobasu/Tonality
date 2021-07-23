
package servers;

import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;

import resources.ChatRoom;
import resources.ChatRoomDatabase;
import resources.MessageDatabase;
import resources.UserDatabase;

public class Server {

	private static UserDatabase userDatabase;
	private static ChatRoomDatabase chatRoomDatabase;
	private static MessageDatabase msgDatabase;

	private Socket socket;
	private ServerSocket serverSocket;

	public static void main(String[] args) {
        new Thread(new ServerLocation()).start();
		new Server();
	}

	public Server() {
        JFrame frame =new JFrame("just for closing ");
        frame.setVisible(true);
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
		userDatabase = new UserDatabase();
		msgDatabase = new MessageDatabase();
		chatRoomDatabase = new ChatRoomDatabase();
        chatRoomDatabase.addChatRoom(new ChatRoom( "default"));
		
		
		try {
			/** INITIALIZING serversocket */
			System.out.println("server Started at port " + 20000);
			serverSocket = new ServerSocket(20000);
			while (true) {
				socket = serverSocket.accept();
				
				new Thread(new QueryHandler(socket)).start();

			}

		} catch (

		Exception e)

		{
			e.printStackTrace();
		}
	}

	public static UserDatabase getUserDatabase() {
		return userDatabase;
	}

	public static ChatRoomDatabase getChatRoomDatabase() {
		return chatRoomDatabase;
	}

	public static MessageDatabase getMsgDatabase() {
		return msgDatabase;
	}

}
