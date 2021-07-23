package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.LinkedList;

import resources.Profile;
import resources.Queries;
import resources.QueryMessage;
import resources.User;
import resources.UserMessage;

public class Client {

	private User user;
	private Profile profile;
	private Socket socket;
	private QueryMessage qMsg;
	// private OutputStream os;
	private OutputStreamWriter osw;
	private ObjectOutputStream oos;
	private BufferedWriter bw;

	// private InputStream is;
	private InputStreamReader isr;
	private ObjectInputStream ois;
	private BufferedReader br;
	private DatagramSocket c;
	private String host = "localhost";
	private int port = 20000;

	public Client() {
		// Find the server using UDP broadcast
		try {
			// Open a random port to send the package
			c = new DatagramSocket();
			c.setBroadcast(true);
			byte[] sendData = "DISCOVER_FUIFSERVER_REQUEST".getBytes();
			// Try the 255.255.255.255 first
			try {
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,
						InetAddress.getByName("255.255.255.255"), 8888);
				c.send(sendPacket);
				System.out.println(getClass().getName() + ">>> Request packet sent to: 255.255.255.255 (DEFAULT)");
			} catch (Exception e) {
			}
			// Broadcast the message over all the network interfaces
			Enumeration interfaces = NetworkInterface.getNetworkInterfaces();
			while (interfaces.hasMoreElements()) {
				NetworkInterface networkInterface = (NetworkInterface) interfaces.nextElement();
				if (networkInterface.isLoopback() || !networkInterface.isUp()) {
					continue; // Don't want to broadcast to the loop back
								// interface
				}
				for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
					InetAddress broadcast = interfaceAddress.getBroadcast();
					if (broadcast == null) {
						continue;
					}
					// Send the broadcast package!
					try {
						DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, broadcast, 8888);
						c.send(sendPacket);
					} catch (Exception e) {
					}
					System.out.println(getClass().getName() + ">>> Request packet sent to: "
							+ broadcast.getHostAddress() + "; Interface: " + networkInterface.getDisplayName());
				}
			}
			System.out.println(
					getClass().getName() + ">>> Done looping over all network interfaces. Now waiting for a reply!");
			// Wait for a response
			byte[] recvBuf = new byte[15000];
			DatagramPacket receivePacket = new DatagramPacket(recvBuf, recvBuf.length);
			c.receive(receivePacket);
			// We have a response
			System.out.println(getClass().getName() + ">>> Broadcast response from server: "
					+ receivePacket.getAddress().getHostAddress());
			String host = receivePacket.getAddress().getHostAddress();
			// Check if the message is correct
			String message = new String(receivePacket.getData()).trim();
			if (message.equals("DISCOVER_FUIFSERVER_RESPONSE")) {
				// DO SOMETHING WITH THE SERVER'S IP (for example, store it in
				// your controller)
			}
			// Close the port!
			this.host = host;
			c.close();
		} catch (IOException ex) {
		}
	}

	public boolean register(String username, String password) {
		boolean result = false;
		try {
			socket = new Socket(host, port);

			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());

			LinkedList<Object> content = new LinkedList<Object>();
			content.add(username);
			content.add(password);

			qMsg = new QueryMessage(Queries.REGISTER, content, 0);
			oos.writeObject(qMsg);
			qMsg = (QueryMessage) ois.readObject();

			if (qMsg.getErrorCode() == 1) {
				System.out.println("Registration Successful");
				result = true;
			} else if (qMsg.getErrorCode() == 0) {
				System.out.println("Duplicate User");
			}

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
			}
		}
		return result;
	}

	public Profile login(String username, String password) {
		Profile p = null;
		try {
			socket = new Socket(host, port);

			isr = new InputStreamReader(socket.getInputStream());
			br = new BufferedReader(isr);
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			osw = new OutputStreamWriter(socket.getOutputStream());
			bw = new BufferedWriter(osw);

			LinkedList<Object> content = new LinkedList<Object>();
			content.add(new String(username));
			content.add(new String(password));
			qMsg = new QueryMessage(Queries.LOGIN, content, 0);

			oos.writeObject(qMsg);

			qMsg = (QueryMessage) ois.readObject();

			if (qMsg.getErrorCode() == 1) {
				p = (Profile) qMsg.getContent().get(0);
				System.out.println("Login Successful ...");

			}

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {

				socket.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		return p;
	}

	public LinkedList<String> getChatRoomsList() {
		LinkedList<String> rooms = null;
		try {
			socket = new Socket(host, port);
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());

			qMsg = new QueryMessage(Queries.GET_CHATROOM_LIST, null, 0);

			oos.writeObject(qMsg);
			qMsg = (QueryMessage) ois.readObject();

			if (qMsg.getErrorCode() == 1) {
				rooms = (LinkedList<String>) qMsg.getContent().get(0);
			}

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {

				socket.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

		return rooms;
	}

	public LinkedList<UserMessage> getChatRoomMessages(String name, long timeStamp) {
		LinkedList<UserMessage> msg = null;
		LinkedList<Object> content = new LinkedList<>();
		try {
			socket = new Socket(host, port);
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			content.add(name);
			content.add(timeStamp);
			qMsg = new QueryMessage(Queries.GET_CHAT_ROOM_MESSAGES, content, 0);
			content = null;
			oos.writeObject(qMsg);
			qMsg = (QueryMessage) ois.readObject();

			if (qMsg.getErrorCode() == 1) {
				msg = (LinkedList<UserMessage>) qMsg.getContent().get(0);
			}

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {

				socket.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

		return msg;

	}

	public void addChatRoomMessage(String name, String body, String senderID) {
		LinkedList<Object> content = new LinkedList<>();
		content.add(name);
		content.add(new UserMessage(body, senderID, ""));

		try {
			socket = new Socket(host, port);
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());

			qMsg = new QueryMessage(Queries.SEND_CHAT_ROOM_MESSAGE, content, 0);

			oos.writeObject(qMsg);
			qMsg = (QueryMessage) ois.readObject();

			if (qMsg.getErrorCode() == 1) {
			}

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {

				socket.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

	}

	public boolean haveNewMessage( String from, long timeStamp) {
		LinkedList<Object> content = new LinkedList<>();
		try {
			socket = new Socket(host, port);
			content.add(from);
			content.add(timeStamp);

			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());

			qMsg = new QueryMessage(Queries.HAVE_NEW_CHAT_ROOM_MESSAGE, content, 0);
			oos.writeObject(qMsg);
			qMsg = (QueryMessage) ois.readObject();

			if (qMsg.getErrorCode() == 1) {
				return (Boolean) qMsg.getContent().get(0);
		}

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return false;
	}
	public boolean haveNewMessage(String senderID, String receiverID, long timeStamp) {
		LinkedList<Object> content = new LinkedList<>();
		try {
			socket = new Socket(host, port);
			content.add(senderID);
			content.add(receiverID);
			content.add(timeStamp);

			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());

			qMsg = new QueryMessage(Queries.HAVE_NEW_FRIEND_MESSAGE, content, 0);
			oos.writeObject(qMsg);
			qMsg = (QueryMessage) ois.readObject();

			if (qMsg.getErrorCode() == 1) {
				return (Boolean) qMsg.getContent().get(0);

			}

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return false;
	}

	public void createNewChatRoom(String name) {
		LinkedList<Object> content = new LinkedList<>();
		try {
			socket = new Socket(host, port);
			content.add(name);

			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());

			qMsg = new QueryMessage(Queries.CREATE_NEW_CHAT_ROOM, content, 0);
			oos.writeObject(qMsg);
			qMsg = (QueryMessage) ois.readObject();

			if (qMsg.getErrorCode() == 1) {

			}

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
    public void addFriend(String friendName,String username ){
    	LinkedList<Object> content=new LinkedList<>();
    	content.add(username);
    	content.add(friendName);
    	
    	try {
			socket=new Socket(host, port);
		
    	    oos=new ObjectOutputStream(socket.getOutputStream());
    	    ois=new ObjectInputStream(socket.getInputStream());
    	    
    	    qMsg=new QueryMessage(Queries.ADD_FRIEND, content, 1);
    	    oos.writeObject(qMsg);
    	    qMsg=(QueryMessage)ois.readObject();
    	    
    	    
    	    if(qMsg.getErrorCode()==1){
    	    	
    	    	System.out.println("**********************************");
    	    	System.out.println("FriendName  :"+friendName);
    	    	//System.out.println((Profile)qMsg.getContent().get(0));
    	    	System.out.println("**********************************");
    	    	//return (Profile)qMsg.getContent().get(0);
    	    }
    	
    	
    	} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    	
    	//return null;
    }
    public LinkedList<Profile> getfriendList(String name){
    	LinkedList<Object> content=new LinkedList<>();
    	LinkedList<Profile> friends = null;
		try {
			socket = new Socket(host, port);
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
            content.add(name);
			qMsg = new QueryMessage(Queries.GET_FRIEND_LIST, content, 0);

			oos.writeObject(qMsg);
			qMsg = (QueryMessage) ois.readObject();

			if (qMsg.getErrorCode() == 1) {
				friends = (LinkedList<Profile>) qMsg.getContent().get(0);
			}

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {

				socket.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

		return friends;
    }
    
    public void sendMessage(String message,String senderID,String receiverID){
    	UserMessage umsg=new UserMessage(message, senderID, receiverID);
        LinkedList<Object> content=new LinkedList<>();
        content.add(umsg);
        try {
			socket=new Socket(host, port);
			oos=new ObjectOutputStream(socket.getOutputStream());
		    ois=new ObjectInputStream(socket.getInputStream());
        
        
            qMsg=new QueryMessage(Queries.SEND_MESSAGE, content, 0);
            oos.writeObject(qMsg);
            qMsg=(QueryMessage)ois.readObject();
            
        
        } catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
     
    
    
    }
    public LinkedList<UserMessage> getMessage(String senderID,String receiverID,long timeStamp){
    	LinkedList<Object> content=new LinkedList<>();
    	LinkedList<UserMessage> msg=null;
    	content.add(senderID);
    	content.add(receiverID);
    	content.add(timeStamp);
    	try {
			socket=new Socket(host, port);
			oos=new ObjectOutputStream(socket.getOutputStream());
			ois=new ObjectInputStream(socket.getInputStream());
			
			
			qMsg=new QueryMessage(Queries.GET_MESSAGE, content, 0);
			oos.writeObject(qMsg);
		
			
			qMsg=(QueryMessage)ois.readObject();
    	    if(qMsg.getErrorCode()==1){
    	    	msg=(LinkedList<UserMessage>) qMsg.getContent().get(0);
    	    }
    	
    	
    	
    	} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    	
    	return msg;
    }

    public void sendFriendRequest(String username,String friendName){
    	LinkedList<Object> content=new LinkedList<>();
    	content.add(username);
    	content.add(friendName);
    	
    	try {
			socket=new Socket(host, port);
		
    	    oos=new ObjectOutputStream(socket.getOutputStream());
    	    ois=new ObjectInputStream(socket.getInputStream());
    	    
    	    qMsg=new QueryMessage(Queries.REQUEST_FRIEND, content, 1);
    	    oos.writeObject(qMsg);
    	    qMsg=(QueryMessage)ois.readObject();
    	    
    	    
    	    if(qMsg.getErrorCode()==1){
    	    	
    	    	System.out.println("**********************************");
    	    	System.out.println("FriendReqName  :"+friendName);
    	    	//System.out.println((Profile)qMsg.getContent().get(0));
    	    	System.out.println("**********************************");
    	    	//return (Profile)qMsg.getContent().get(0);
    	    }
    	
    	
    	} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    	
    	//return null;
    	
    }
    public LinkedList<Profile> getfriendRequestList(String name){
    	LinkedList<Object> content=new LinkedList<>();
    	LinkedList<Profile> friends = null;
		try {
			socket = new Socket(host, port);
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
            content.add(name);
			qMsg = new QueryMessage(Queries.GET_FRIEND_REQUST_LIST, content, 0);

			oos.writeObject(qMsg);
			qMsg = (QueryMessage) ois.readObject();

			if (qMsg.getErrorCode() == 1) {
				friends = (LinkedList<Profile>) qMsg.getContent().get(0);
			}

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {

				socket.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

		return friends;
    }
    

}
