package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import resources.Profile;
import resources.UserMessage;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Dialog.ModalExclusionType;

public class ChatWindow extends JFrame {

	private Profile profile;
	private Profile friend;
	private Timer timer;
	private String name;
	private Client client;
	private JPanel contentPane;
	private JTextField textField;
	private JTextArea textArea;
	private JPanel panel;
	private JPanel panel_1;
	private JButton btnNewButton;
	private long timeStamp;
    private int type;
	/**
	 * Create the frame.
	 * @wbp.parser.constructor
	 */
	public ChatWindow(Client client, String name, Profile profile, int type) {
		System.out.println("Type :: "+type);
		this.client = client;
 		this.name = name;
        this.type=type;
 		this.profile=profile;
        init();
 	}

	public ChatWindow(Client client,Profile profile,Profile friend){
		this.client=client;
	    this.type=1;
	    this.profile=profile;
	    this.friend=friend;
	    init();
	}
	
	public void init() {
		setTitle("Chat Window");

		setAlwaysOnTop(true);
	
		setResizable(false);

		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		panel = new JPanel();
		panel.setBounds(0, 0, 444, 218);
		contentPane.add(panel);
		panel.setLayout(null);

		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(10, 11, 424, 196);
		panel.add(textArea);

		panel_1 = new JPanel();
		panel_1.setBounds(0, 227, 434, 33);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		textField = new JTextField();
		textField.setBounds(10, 0, 347, 33);
		panel_1.add(textField);
		textField.setColumns(10);

		btnNewButton = new JButton("send");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = textField.getText();
				textField.setText("");
				if (type == 0)client.addChatRoomMessage(name, text, profile.getUsername());
                if(type==1){
                	client.sendMessage(text,profile.getUsername(),friend.getUsername());
                }
			}
		});
		btnNewButton.setBounds(367, 5, 67, 23);
		panel_1.add(btnNewButton);

		ActionListener acLst = new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				if (type == 0) {
					if (client.haveNewMessage( name, timeStamp)) {

						LinkedList<UserMessage> _msg = client.getChatRoomMessages(name, timeStamp);
						for (UserMessage message : _msg) {
							textArea.append("\n" + message.getSenderID() + " ~~>>  " + message.getBody());
						}
						timeStamp = System.currentTimeMillis();
						System.out.println("New Message Received ");
					}
				} else if (type == 1) {

					if(client.haveNewMessage(profile.getUsername(), friend.getUsername(), timeStamp)){
						LinkedList<UserMessage> _msg=client.getMessage(profile.getUsername(), friend.getUsername(), timeStamp);
						for (UserMessage message : _msg) {
							textArea.append("\n" + message.getSenderID() + " :||>" + message.getBody());
						}
						timeStamp = System.currentTimeMillis();
						
					
					}
				}
			}
		};
		timer = new Timer(500, acLst);
		timer.start();

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				if (timer.isRunning())
					timer.stop();

			}
		});

	}

}
