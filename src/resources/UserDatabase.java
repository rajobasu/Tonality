package resources;

import java.util.LinkedList;

public class UserDatabase {

	private LinkedList<User> users = new LinkedList<User>();

	public UserDatabase() {
         users.add(new User("a","a",null));  
	}

	public void addUser(User user) {
		users.add(new User(user,1000+users.size()));
	}

	public void removeUser(User user) {
		users.remove(user);
	}

	public User getUser(String username) throws UserNotFoundException {
		User tempUser, forReturn = null;
		for (int i = 0; i < users.size(); i++) {
			tempUser = users.get(i);
			if (tempUser.getUsername().equals(username.trim())) {
				forReturn = tempUser;
			}

		}

		if (forReturn == null)
			throw new UserNotFoundException();
		return forReturn;
	}

	public User getUser(String username, String password) throws UserNotFoundException {
		User tempUser, forReturn = null;
		for (int i = 0; i < users.size(); i++) {
			tempUser = users.get(i);
			if ((tempUser.getUsername().equals(username.trim())) && (tempUser.getPassword().equals(password.trim()))) {
				forReturn = tempUser;

			}

		}

		if (forReturn == null)
			throw new UserNotFoundException();
		return forReturn;
	}

    public boolean isDuplicateUser(String username){
    	boolean forReturn=false;
        for(User user:users){
        	if(username.trim().equalsIgnoreCase(user.getUsername().trim())){
        		forReturn=true;
        	}
        }
    
    
        return forReturn;
    }

}
