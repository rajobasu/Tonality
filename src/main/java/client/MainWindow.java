package client;

import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

import javax.swing.*;

import resources.Profile;

public class MainWindow {

	private int prevFriendNum;
	private int chatRoomSelected;
	private int friendSelected;
	private final Profile profile;
	private final Client client;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField textField;
	private JPanel panel_6;
	private JPanel panel_8;

	private LinkedList<String> chatRooms;
	private JTextField tf_frndReq;
	private JTextArea fndReq;

	/**
	 * Create the application.
	 */
	public MainWindow(Profile profile,Client client) {
		
		this.profile=profile;
		this.client=client;
		
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		chatRooms =client.getChatRoomsList();
		LinkedList<Profile> friends = profile.getFriendList();
		prevFriendNum=profile.getFriendList().size();

		JFrame frmFriendsconnect = new JFrame();
		frmFriendsconnect.setTitle("FriendsConnect");
		frmFriendsconnect.getContentPane().setBackground(new Color(51, 102, 204));
		frmFriendsconnect.setResizable(false);
		frmFriendsconnect.setBounds(100, 100, 445, 483);
		frmFriendsconnect.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmFriendsconnect.getContentPane().setLayout(null);
		frmFriendsconnect.setVisible(true);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setForeground(SystemColor.textHighlight);
		tabbedPane.setBounds(8, 239, 421, 204);
		frmFriendsconnect.getContentPane().add(tabbedPane);

		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(204, 255, 204));
		panel_4.setForeground(new Color(153, 255, 153));
		tabbedPane.addTab("JoinChatRoom", null, panel_4, null);
		panel_4.setLayout(null);

		JLabel lblJoinchatRoom = new JLabel("Join Chat Room :");
		lblJoinchatRoom.setForeground(new Color(102, 0, 0));
		lblJoinchatRoom.setFont(new Font("Sitka Text", Font.BOLD, 13));
		lblJoinchatRoom.setHorizontalAlignment(SwingConstants.CENTER);
		lblJoinchatRoom.setBounds(0, 0, 120, 26);
		panel_4.add(lblJoinchatRoom);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 37, 363, 100);
		panel_4.add(scrollPane_1);
		
		panel_6 = new JPanel();
		scrollPane_1.setViewportView(panel_6);
		GridBagLayout gbl_panel_6 = new GridBagLayout();
		gbl_panel_6.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel_6.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel_6.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_6.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_6.setLayout(gbl_panel_6);
		JButton btnNewButton_1 = new JButton("Create new Chat Room");
		btnNewButton_1.setBackground(new Color(255, 255, 204));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			    String name=JOptionPane.showInputDialog("Enter Name");
			    client.createNewChatRoom(name);
			}
		});
		btnNewButton_1.setBounds(10, 142, 363, 23);
		panel_4.add(btnNewButton_1);

		JButton btnStart = new JButton("Start");
		btnStart.setBackground(new Color(255, 255, 204));
		btnStart.setFont(new Font("Yu Mincho Demibold", Font.BOLD, 15));
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			   new ChatWindowModified(client,chatRooms.get(chatRoomSelected),profile);
			}
		});
		btnStart.setBounds(273, 2, 133, 23);
		panel_4.add(btnStart);

		JPanel panel_5 = new JPanel();
		panel_5.setBackground(new Color(204, 204, 255));
		panel_5.setForeground(new Color(204, 255, 0));
		tabbedPane.addTab("Converse with friends", null, panel_5, null);
		panel_5.setLayout(null);

		JLabel lblChatWithFriends = new JLabel("Chat With Friends Individually  :");
		lblChatWithFriends.setForeground(new Color(102, 0, 0));
		lblChatWithFriends.setFont(new Font("Poor Richard", Font.PLAIN, 16));
		lblChatWithFriends.setBounds(10, 0, 240, 25);
		panel_5.add(lblChatWithFriends);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 23, 396, 127);
		panel_5.add(scrollPane);
		
		panel_8 = new JPanel();
		scrollPane.setViewportView(panel_8);
		GridBagLayout gbl_panel_8 = new GridBagLayout();
		gbl_panel_8.columnWidths = new int[]{109, 0};
		gbl_panel_8.rowHeights = new int[]{23, 23, 23, 23, 23, 23, 0};
		gbl_panel_8.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel_8.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_8.setLayout(gbl_panel_8);

		JButton btnStart_1 = new JButton("Start");
		btnStart_1.setBackground(new Color(255, 255, 204));
		btnStart_1.setFont(new Font("Poor Richard", Font.PLAIN, 15));
		btnStart_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
			new ChatWindow(client, profile, profile.getFriendList().get(friendSelected));
			}
		});
		btnStart_1.setBounds(10, 153, 396, 23);
		panel_5.add(btnStart_1);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(153, 51, 0));
		panel.setBounds(8, 119, 179, 109);
		frmFriendsconnect.getContentPane().add(panel);

		JButton btnAddFriend = new JButton("Add Friend");
		btnAddFriend.setBackground(new Color(255, 255, 204));
		btnAddFriend.setFont(new Font("Poor Richard", Font.PLAIN, 16));
		btnAddFriend.setBounds(10, 5, 159, 23);
		btnAddFriend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			    
				client.addFriend(textField.getText(), profile.getUsername());
			}
		});
		panel.setLayout(null);
		panel.add(btnAddFriend);
		
		textField = new JTextField();
		textField.setBounds(53, 39, 116, 31);
		panel.add(textField);
		textField.setColumns(10);

		JLabel lblName = new JLabel("Name :");
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		lblName.setForeground(new Color(204, 204, 255));
		lblName.setBackground(new Color(204, 255, 255));
		lblName.setBounds(1, 46, 49, 24);
		panel.add(lblName);

		JPanel panel_7 = new JPanel();
		panel_7.setBackground(new Color(51, 102, 0));
		panel_7.setBounds(8, 45, 179, 63);
		frmFriendsconnect.getContentPane().add(panel_7);
		panel_7.setLayout(null);

		JLabel lblName_1 = new JLabel("Name :");
		lblName_1.setForeground(new Color(255, 153, 153));
		lblName_1.setFont(new Font("PMingLiU-ExtB", Font.BOLD, 15));
		lblName_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblName_1.setBounds(0, 25, 64, 27);
		panel_7.add(lblName_1);

		JTextArea textArea_3 = new JTextArea();
		textArea_3.setBounds(74, 27, 95, 28);
		textArea_3.setEditable(false);
		panel_7.add(textArea_3);
		textArea_3.setText(profile.getUsername());

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 0, 0));
		panel_1.setLayout(null);
		panel_1.setBounds(197, 11, 232, 95);
		frmFriendsconnect.getContentPane().add(panel_1);

		JButton btnFriendRequest = new JButton("Friend Request");
		btnFriendRequest.setBackground(new Color(255, 255, 204));
		btnFriendRequest.setFont(new Font("Poor Richard", Font.PLAIN, 15));
		btnFriendRequest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				client.sendFriendRequest(profile.getUsername(), tf_frndReq.getText());
			}
		});
		btnFriendRequest.setBounds(81, 5, 141, 23);
		panel_1.add(btnFriendRequest);
		
		tf_frndReq = new JTextField();
		tf_frndReq.setColumns(10);
		tf_frndReq.setBounds(75, 39, 147, 45);
		panel_1.add(tf_frndReq);
		
		JLabel label = new JLabel("Name :");
		label.setFont(new Font("Shelldon", Font.PLAIN, 16));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(new Color(255, 255, 204));
		label.setBackground(new Color(0, 204, 153));
		label.setBounds(1, 46, 64, 38);
		panel_1.add(label);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(197, 117, 232, 111);
		frmFriendsconnect.getContentPane().add(scrollPane_2);
		
		fndReq = new JTextArea();
		fndReq.setForeground(new Color(51, 102, 51));
		fndReq.setEditable(false);
		scrollPane_2.setViewportView(fndReq);
		
		JLabel lblFriendRequests = new JLabel("Friend Requests");
		lblFriendRequests.setFont(new Font("Sitka Subheading", Font.PLAIN, 11));
		scrollPane_2.setColumnHeaderView(lblFriendRequests);
		
		JButton btnRefresh = new JButton("Refresh Page");
		btnRefresh.setBackground(new Color(255, 255, 204));
		btnRefresh.setFont(new Font("Poor Richard", Font.PLAIN, 15));
		btnRefresh.setBounds(8, 11, 156, 23);
		frmFriendsconnect.getContentPane().add(btnRefresh);
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refresh_1();
				refresh_2();
				refresh_3();
			}
		});
		
		JRadioButton btn;
		GridBagConstraints gbc_btn;
		////FOR FRIENDS
		int index=0;
		for(Profile profile: friends)
		{
		    btn=new JRadioButton();
			btn.setActionCommand(Integer.toString(index));
			btn.setText(profile.getUsername());
			btn.addActionListener(new ActionListener() {
				
				
				public void actionPerformed(ActionEvent e) {
				     friendSelected=Integer.parseInt(e.getActionCommand()); 	
				}
			});
			buttonGroup.add(btn);
			gbc_btn=new GridBagConstraints();
			gbc_btn.anchor=GridBagConstraints.WEST;
			gbc_btn.insets = new Insets(0, 0, 5, 0);
			gbc_btn.gridx = 0;
			gbc_btn.gridy = index;
			
			panel_8.add(btn,gbc_btn);
			index++;
			
		}
		prevFriendNum=profile.getFriendList().size();
		////FOR CHATROOMS
		index=0;
		for(String name:chatRooms)
		{
		    btn=new JRadioButton();
			btn.setActionCommand(Integer.toString(index));
			btn.setText(name);
			btn.addActionListener(new ActionListener() {
				
				
				public void actionPerformed(ActionEvent e) {
				     chatRoomSelected=Integer.parseInt(e.getActionCommand()); 	
				}
			});
			buttonGroup.add(btn);
			gbc_btn=new GridBagConstraints();
			gbc_btn.anchor=GridBagConstraints.WEST;
			gbc_btn.insets = new Insets(0, 0, 5, 0);
			gbc_btn.gridx = 0;
			gbc_btn.gridy = index;
			
			panel_6.add(btn,gbc_btn);
			index++;
			
		}
		
	
		/*
     	
		rdbtnNewRadioButton_0 = new JRadioButton("New radio button");
		buttonGroup.add(rdbtnNewRadioButton_0);
		GridBagConstraints gbc_rdbtnNewRadioButton_0 = new GridBagConstraints();
		gbc_rdbtnNewRadioButton_0.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnNewRadioButton_0.gridx = 0;
		gbc_rdbtnNewRadioButton_0.gridy = 0;
		panel_6.add(rdbtnNewRadioButton_0, gbc_rdbtnNewRadioButton_0);
     	*/
	}
	
	private void refresh_1(){
		 profile.addFriends(client.getfriendList(profile.getUsername()));
			
			JRadioButton btn;
			GridBagConstraints gbc_btn;
			int index=prevFriendNum;
			System.out.println(profile.getFriendList());
     
			int index_ref=0;
			for(Profile name:profile.getFriendList())
			{
				index_ref++;
				if(!(index_ref>(index))){
			    	
			        System.out.println(name);
			        System.out.println("index_ref   :"+index_ref+"  Index   :"+index);
			        continue;
			    }
			    System.out.println(name);
			    
			    btn=new JRadioButton();
				btn.setActionCommand(Integer.toString(index));
				btn.setText(name.getUsername());
				btn.addActionListener(new ActionListener() {
					
					
					public void actionPerformed(ActionEvent e) {
					     friendSelected=Integer.parseInt(e.getActionCommand()); 	
			
					}
				});
				buttonGroup.add(btn);
				gbc_btn=new GridBagConstraints();
				gbc_btn.anchor=GridBagConstraints.WEST;
				gbc_btn.insets = new Insets(0, 0, 5, 0);
				gbc_btn.gridx = 0;
				gbc_btn.gridy = index_ref;
				
				panel_8.add(btn,gbc_btn);
				System.out.println("Button Added");
				System.out.println();
			
			}	
			prevFriendNum=profile.getFriendList().size();
	}
	private void refresh_2(){
		LinkedList<String> rooms=client.getChatRoomsList();
 		///////////////////////////////////
		//////////////////////////
		int index=chatRooms.size();
		chatRooms=rooms;
		System.out.println(rooms);
		System.out.println(chatRooms);
		JRadioButton btn;
		GridBagConstraints gbc_btn;
		
		int index_ref=0;
		for(String name:chatRooms)
		{
			index_ref++;
			if(!(index_ref>index)){
		    	
		        System.out.println(name);
		        System.out.println("index_ref   :"+index_ref+"  Index   :"+index);
		        continue;
		    }
		    System.out.println(name);
		    
		    btn=new JRadioButton();
			btn.setActionCommand(Integer.toString(index));
			btn.setText(name);
			btn.addActionListener(new ActionListener() {
				
				
				public void actionPerformed(ActionEvent e) {
				    chatRoomSelected=Integer.parseInt(e.getActionCommand()); 	
				}
			});
			buttonGroup.add(btn);
			gbc_btn=new GridBagConstraints();
			gbc_btn.anchor=GridBagConstraints.WEST;
			gbc_btn.insets = new Insets(0, 0, 5, 0);
			gbc_btn.gridx = 0;
			gbc_btn.gridy = index_ref;
			
			panel_6.add(btn,gbc_btn);
			System.out.println("Button Added");
			System.out.println();
		
		}
		
	}
	private void refresh_3(){
		LinkedList<Profile> list=client.getfriendRequestList(profile.getUsername());
		fndReq.setText("");
		for(Profile p:list){
			fndReq.append("~> "+p.getUsername()+"\n");
		}
	}
	
}
