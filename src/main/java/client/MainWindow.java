package client;

import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

import javax.swing.*;

import resources.Profile;

public class MainWindow {

    private final Profile profile;
    private final Client client;
    private final ButtonGroup buttonGroup = new ButtonGroup();
    private int chatRoomSelected;
    private JPanel panel_6;
    private LinkedList<String> chatRooms;

    /**
     * Create the application.
     */
    public MainWindow(Profile profile, Client client) {

        this.profile = profile;
        this.client = client;

        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        chatRooms = client.getChatRoomsList();

        JFrame frmFriendsconnect = new JFrame();
        frmFriendsconnect.setTitle("FriendsConnect");
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
        tabbedPane.addTab("JoinChatRoom", null, panel_4, null);
        panel_4.setLayout(null);

        JLabel lblJoinchatRoom = new JLabel("Join Chat Room :");
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
        btnNewButton_1.addActionListener(arg0 -> {
            String name = JOptionPane.showInputDialog("Enter Name");
            client.createNewChatRoom(name);
        });
        btnNewButton_1.setBounds(10, 142, 363, 23);
        panel_4.add(btnNewButton_1);

        JButton btnStart = new JButton("Start");
        btnStart.setFont(new Font("Yu Mincho Demibold", Font.BOLD, 15));
        btnStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ChatWindowModified(client, chatRooms.get(chatRoomSelected), profile);
            }
        });

        btnStart.setBounds(273, 2, 133, 23);
        panel_4.add(btnStart);


        JPanel panel_7 = new JPanel();
        panel_7.setBounds(8, 45, 179, 63);
        frmFriendsconnect.getContentPane().add(panel_7);
        panel_7.setLayout(null);

        JLabel lblName_1 = new JLabel("Nam33e :");
        lblName_1.setFont(new Font("PMingLiU-ExtB", Font.BOLD, 15));
        lblName_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblName_1.setBounds(0, 25, 64, 27);
        panel_7.add(lblName_1);

        JTextArea textArea_3 = new JTextArea();
        textArea_3.setBounds(74, 27, 95, 28);
        textArea_3.setEditable(false);
        panel_7.add(textArea_3);
        textArea_3.setText(profile.getUsername());

        JButton btnRefresh = new JButton("Refresh Page");
        btnRefresh.setFont(new Font("Poor Richard", Font.PLAIN, 15));
        btnRefresh.setBounds(8, 11, 156, 23);
        frmFriendsconnect.getContentPane().add(btnRefresh);
        btnRefresh.addActionListener(e -> {
            refresh_2();

        });

        JRadioButton btn;
        GridBagConstraints gbc_btn;

        ////FOR CHATROOMS
        int index = 0;
        for (String name : chatRooms) {
            btn = new JRadioButton();
            btn.setActionCommand(Integer.toString(index));
            btn.setText(name);
            btn.addActionListener(e -> chatRoomSelected = Integer.parseInt(e.getActionCommand()));
            buttonGroup.add(btn);
            gbc_btn = new GridBagConstraints();
            gbc_btn.anchor = GridBagConstraints.WEST;
            gbc_btn.insets = new Insets(0, 0, 5, 0);
            gbc_btn.gridx = 0;
            gbc_btn.gridy = index;

            panel_6.add(btn, gbc_btn);
            index++;

        }
    }

    private void refresh_2() {
        LinkedList<String> rooms = client.getChatRoomsList();
        ///////////////////////////////////
        //////////////////////////
        int index = chatRooms.size();
        chatRooms = rooms;
        System.out.println(rooms);
        System.out.println(chatRooms);
        JRadioButton btn;
        GridBagConstraints gbc_btn;

        int index_ref = 0;
        for (String name : chatRooms) {
            index_ref++;
            if (!(index_ref > index)) {

                System.out.println(name);
                System.out.println("index_ref   :" + index_ref + "  Index   :" + index);
                continue;
            }
            System.out.println(name);

            btn = new JRadioButton();
            btn.setActionCommand(Integer.toString(index));
            btn.setText(name);
            btn.addActionListener(new ActionListener() {


                public void actionPerformed(ActionEvent e) {
                    chatRoomSelected = Integer.parseInt(e.getActionCommand());
                }
            });
            buttonGroup.add(btn);
            gbc_btn = new GridBagConstraints();
            gbc_btn.anchor = GridBagConstraints.WEST;
            gbc_btn.insets = new Insets(0, 0, 5, 0);
            gbc_btn.gridx = 0;
            gbc_btn.gridy = index_ref;

            panel_6.add(btn, gbc_btn);
            System.out.println("Button Added");
            System.out.println();

        }

    }

}
