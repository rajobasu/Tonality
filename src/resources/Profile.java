package resources;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.LinkedList;

public class Profile implements Serializable{

	private String username;
	private long ID=10; 
	private LinkedList<Profile> friendList=new LinkedList<Profile>();
	private LinkedList<Profile> friendReqList=new LinkedList<Profile>();
	private BufferedImage image;
	
	public Profile(User user) {
		this.username=user.getUsername();
		this.image=user.getPicture();
	    this.friendList=user.getFriendList();
	    this.friendReqList=user.getFriendRequestList();
	    this.ID=user.getID();
	
	}

	public String getUsername() {
		return username;
	}

	public long getID() {
		return ID;
	}

	public LinkedList<Profile> getFriendList() {
		return friendList;
	}
	public LinkedList<Profile> getFriendRequestList() {
		return friendReqList;
	}
    public void addFriends(LinkedList<Profile> friends){
    	LinkedList<String> names=new LinkedList<>();
    	for(Profile name:friendList){
    		names.add(name.getUsername());
    	}
    	for(Profile friend:friends){
    	   if(!(names.contains(friend.getUsername()))){
    		   friendList.add(friend);
    	   }
    	}
    }
	public BufferedImage getImage() {
		return image;
	}
    public String toString(){
    	return username;
    }
    

}
