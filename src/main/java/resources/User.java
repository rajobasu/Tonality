package resources;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.LinkedList;

public class User implements Serializable
{
    private String username;
    private String password;
    private long ID;
    private BufferedImage picture;
    private LinkedList<Profile> friendList=new LinkedList<Profile>();
	private boolean active;
	private LinkedList<Profile> frndReqList=new LinkedList<>();
	
	public User(User user,long ID){
		this.username=user.username;
		this.password=user.password;
		this.ID=ID;
		this.picture=user.picture;
		this.friendList=user.friendList;
	}
    public User(String username,String password,BufferedImage picture){
    	this.username=username;
    	this.password=password;
    	this.picture=picture;
    }


    
    public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public long getID() {
		return ID;
	}
	public void setID(long iD) {
		ID = iD;
	}
	public BufferedImage getPicture() {
		return picture;
	}
	public void setPicture(BufferedImage picture) {
		this.picture = picture;
	}
	public LinkedList<Profile> getFriendList() {
		return friendList;
	}
	public void setFriendList(LinkedList<Profile> friendList) {
		this.friendList = friendList;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
    public void addFriend(Profile friend){friendList.add(friend);}
    public String toString(){
    	return username;
    }
    public void addFriendRequest(Profile p){
    	frndReqList.add(p);
    }
    public void removeFriendRequest(Profile p){
    	frndReqList.remove(p);
    }
    public LinkedList<Profile> getFriendRequestList(){
    	return frndReqList;
    }



}
