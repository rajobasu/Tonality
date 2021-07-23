package requestHandlers;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

import resources.Profile;
import resources.Queries;
import resources.QueryMessage;
import resources.User;
import resources.UserDatabase;
import resources.UserNotFoundException;
import servers.Server;

public class ProfileUpdate {
	private LinkedList<Object> content;
	private ObjectOutputStream oos;
	private UserDatabase users;

	public ProfileUpdate(ObjectOutputStream oos, LinkedList<Object> content) {
		this.content = content;
		this.oos = oos;
		this.users = Server.getUserDatabase();
	}

	public void addFriend() throws IOException {
		String username = (String) content.get(0);
		String friendName = (String) content.get(1);

		try {
			User user_friend = users.getUser(friendName);
			User user_own = users.getUser(username);

			Profile own = new Profile(user_own);
			Profile friend = new Profile(user_friend);

			Profile reqProfile=null;
			boolean allow=false;
			for(Profile p:user_own.getFriendRequestList()){
				if(p.getUsername().equalsIgnoreCase(friendName)){
					allow=true;
					reqProfile=p;
				}
			}
					
			if(allow){
				user_friend.addFriend(own);
				user_own.addFriend(friend);
				user_own.removeFriendRequest(reqProfile);
				
			}else{
				throw new UserNotFoundException();
			}
			content.clear();

			oos.writeObject(new QueryMessage(Queries.SERVER_REPLY, null, 1));

		} catch (UserNotFoundException e) {
			oos.writeObject(new QueryMessage(Queries.SERVER_REPLY, null, -1));
			e.printStackTrace();
		}

	}

	public void getFriendList() {
		String name = (String) content.get(0);
		content.clear();
		try {
			content.add(users.getUser(name).getFriendList());
			oos.writeObject(new QueryMessage(Queries.SERVER_REPLY, content, 1));

		} catch (UserNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getFriendRequestList() {
		String name = (String) content.get(0);
		content.clear();
		try {
			content.add(users.getUser(name).getFriendRequestList());
			oos.writeObject(new QueryMessage(Queries.SERVER_REPLY, content, 1));

		} catch (UserNotFoundException e) {
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addFriendRequest() throws IOException {
		String username = (String) content.get(0);
		String friendName = (String) content.get(1);
		try {
			User user_friend = users.getUser(friendName);
			User user_own = users.getUser(username);

			Profile own = new Profile(user_own);
			
			boolean duplicate=false;
			for(Profile p:user_friend.getFriendRequestList()){
				if(username.equalsIgnoreCase(p.getUsername())){
					duplicate=true;
					break;
				}
			}
			for(Profile p:user_friend.getFriendList()){
				if(username.equalsIgnoreCase(p.getUsername())){
					duplicate=true;
					break;
				}
			}

			for(Profile p:user_own.getFriendRequestList()){
				if(friendName.equalsIgnoreCase(p.getUsername())){
					duplicate=true;
					break;
				}
			}
			if(!duplicate)user_friend.addFriendRequest(own);
			
			oos.writeObject(new QueryMessage(Queries.SERVER_REPLY, null, 1));
			
		} catch (UserNotFoundException u) {
			oos.writeObject(new QueryMessage(Queries.SERVER_REPLY, null, -1));
		}

	}

	
}
