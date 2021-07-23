package client;

import java.awt.EventQueue;

public class ChatClient {
	
    private Client client;
	public static void main(String[] args) {
		new ChatClient();
		
		
	}
    public ChatClient()
    {
    	client=new Client();
    	EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginWindow frame = new LoginWindow(client);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
    	//client =null;
    	
    } 
}
