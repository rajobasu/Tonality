package resources;

import java.util.LinkedList;

public class MessageDatabase {

	private LinkedList<UserMessage> msg = new LinkedList<UserMessage>();

	public MessageDatabase() {
		
	}

	
	public synchronized void addMessage(UserMessage msg) {
		this.msg.add(msg);
	}

	public synchronized void removeUser(UserMessage msg) {
		this.msg.remove(msg);
	}

	public synchronized LinkedList<UserMessage> getMessage(long timestamp, String... ID) {
		LinkedList<UserMessage> msgList = new LinkedList<UserMessage>();

		String senderID = ID[0].trim();
		String receiverID = ID[1].trim();

		for (UserMessage _msg : msg) {
			if ((_msg.getSenderID().equals(senderID) && _msg.getReceiverID().equals(receiverID))
					|| (_msg.getSenderID().equals(receiverID) && _msg.getReceiverID().equals(senderID))) {

				if (_msg.getTimestamp() > timestamp)
					msgList.add(_msg);

			}
		}

		return msgList;
	}

	public synchronized boolean haveNewMessage(long timestamp, String... ID) {

		String senderID = ID[0].trim();
		String receiverID = ID[1].trim();

		for (UserMessage _msg : msg) {
			if ((_msg.getSenderID().equals(senderID) && _msg.getReceiverID().equals(receiverID))
					|| (_msg.getSenderID().equals(receiverID) && _msg.getReceiverID().equals(senderID))) {

				if (_msg.getTimestamp() > timestamp)
					return true;

			}
		}

		return false;
	}

}
