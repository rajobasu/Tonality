package requestHandlers;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;

import resources.Queries;
import resources.QueryMessage;
import resources.User;
import resources.UserDatabase;
import servers.Server;

public class Register {
	private UserDatabase users;

	private ObjectOutputStream oos;
	private LinkedList<Object> content;

	public Register(ObjectOutputStream oos, LinkedList<Object> content) {

		this.oos = oos;
		users = Server.getUserDatabase();
		this.content = content;
		registerUser();

	}

	private void registerUser() {
		String username = (String) content.get(0);
		String password = (String) content.get(1);

		try {
			if (!users.isDuplicateUser(username)) {
				users.addUser(new User(username, password, null));
				oos.writeObject(new QueryMessage(Queries.SERVER_REPLY, null, 1));
			} else {
				oos.writeObject(new QueryMessage(Queries.SERVER_REPLY, null, 0));

			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			content = null;

		}
	}

}
