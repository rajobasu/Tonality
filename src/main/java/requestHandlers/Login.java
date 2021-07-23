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

public class Login {

	private Profile profile;
	private User user;
	private String username;
	private String password;
	private LinkedList<Object> content;

	private ObjectOutputStream oos;
    private UserDatabase userDatabase;

    public Login(ObjectOutputStream oos,LinkedList<Object> content){
    	
    	this.content=content;
    	this.username=(String)content.get(0);
    	this.password=(String)content.get(1);
    	userDatabase=Server.getUserDatabase();
    	
    	try {
			this.oos=oos;
		    loginUser();
    	
    	 
    	} catch (IOException e) {
			e.printStackTrace();
		}
    }
    private void loginUser() throws IOException{
    	int errCode = 1;
		try {
			user = userDatabase.getUser(username, password);

			profile = new Profile(user);
			System.out.println("Successfully logged in :" + user.getUsername());

		} catch (UserNotFoundException e) {
			errCode = -1;
			e.printStackTrace();
		}

		content.clear();
		content.add(profile);
		oos.writeObject(new QueryMessage(Queries.SERVER_REPLY, content, errCode));
        content=null;
    }

}
