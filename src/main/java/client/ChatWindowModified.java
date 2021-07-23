package client;

import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

import javax.swing.*;

import resources.Profile;
import resources.UserMessage;

public class ChatWindowModified extends JFrame {
    private Timer refreshLoop;
    private Client client;
    private String name;
    private Profile profile;
    private JButton sendButton;
    private JPanel overallContentPane;
    private JPanel textAreaContainerPanel;
    private JPanel textSendPanel;
    private JTextField textField;
    private long timeStamp;


    /**
     * Essentially type 0
     *
     * @param client
     * @param name
     * @param profile
     */
    public ChatWindowModified(Client client, String name, Profile profile) {
        this.client = client;
        this.name = name;
        this.profile = profile;
        init();
    }

    public void init() {
        this.setTitle("Chat Window");
        this.setAlwaysOnTop(true);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(450, 300);
        this.setLayout(new BorderLayout());

        overallContentPane = new JPanel();
        overallContentPane.setLayout(new BoxLayout(overallContentPane, BoxLayout.PAGE_AXIS));

        textAreaContainerPanel = new JPanel();
        textAreaContainerPanel.setLayout(new BoxLayout(textAreaContainerPanel, BoxLayout.PAGE_AXIS));

        textSendPanel = new JPanel();
        textSendPanel.setLayout(new BoxLayout(textSendPanel, BoxLayout.LINE_AXIS));
        textSendPanel.setMaximumSize(new Dimension(450, 50));

        var messageScrollPane = new JScrollPane(textAreaContainerPanel, ScrollPaneLayout.VERTICAL_SCROLLBAR_ALWAYS,
            ScrollPaneLayout.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        textField = new JTextField();

        sendButton = new JButton("send");
        sendButton.addActionListener(e -> {
            String text = textField.getText();
            textField.setText("");
            client.addChatRoomMessage(name, text, profile.getUsername());
        });

        textSendPanel.add(textField);
        textSendPanel.add(sendButton);


        overallContentPane.add(messageScrollPane);
        overallContentPane.add(textSendPanel);

        setContentPane(overallContentPane);


        refreshLoop = new Timer(500, (ActionEvent argo0) -> {
            if (client.haveNewMessage(name, timeStamp)) {

                LinkedList<UserMessage> _msg = client.getChatRoomMessages(name, timeStamp);
                for (UserMessage message : _msg) {
                    var ta = new JTextArea();
                    ta.setText(message.getSenderID() + " ~~>>  " + message.getBody());
                    ta.setBackground(message.getTone().getAssociatedToneColor().getColor());
                    ta.setMaximumSize(new Dimension(450, 30));
                    textAreaContainerPanel.add(ta);
                    textAreaContainerPanel.revalidate();
                }
                timeStamp = System.currentTimeMillis();
            }
        });
        refreshLoop.start();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent arg0) {
                if (refreshLoop.isRunning()) {
                    refreshLoop.stop();
                }
            }
        });
    }
}
