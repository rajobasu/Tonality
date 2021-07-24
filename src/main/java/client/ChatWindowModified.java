package client;

import static client.SenderTextSentimentModification.updateString;
import static client.SenderTextToneParser.removeArgs;

import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.concurrent.CompletableFuture;

import javax.swing.*;

import resources.Profile;
import resources.Tone;
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
    private JPanel predictiveSentiment;
    private long timeStamp;
    private JTextArea prediction;
    private JButton checkSentiment;


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
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(450, 400);
        this.setLayout(new BorderLayout());

        overallContentPane = new JPanel();
        overallContentPane.setLayout(new BoxLayout(overallContentPane, BoxLayout.PAGE_AXIS));

        textAreaContainerPanel = new JPanel();
        textAreaContainerPanel.setLayout(new BoxLayout(textAreaContainerPanel, BoxLayout.PAGE_AXIS));

        textSendPanel = new JPanel();
        textSendPanel.setLayout(new BoxLayout(textSendPanel, BoxLayout.LINE_AXIS));
        textSendPanel.setMaximumSize(new Dimension(450, 50));

        predictiveSentiment = new JPanel();
        predictiveSentiment.setLayout(new BoxLayout(predictiveSentiment, BoxLayout.LINE_AXIS));
        predictiveSentiment.setMaximumSize(new Dimension(450, 50));

        var messageScrollPane = new JScrollPane(textAreaContainerPanel, ScrollPaneLayout.VERTICAL_SCROLLBAR_ALWAYS,
            ScrollPaneLayout.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        textField = new JTextField();

        sendButton = new JButton("send");
        sendButton.addActionListener(e -> {
            String text = textField.getText();
            Tone tone = SenderTextToneParser.getToneOfText(text);
            LinkedList<String> modifiedText = updateString(removeArgs(text));
            textField.setText("");
            modifiedText.forEach(st -> {
                client.addChatRoomMessage(name, st, profile.getUsername(), tone);
            });
        });

        textSendPanel.add(textField);
        textSendPanel.add(sendButton);

        prediction = new JTextArea();
        checkSentiment = new JButton("Check");
        checkSentiment.addActionListener(
            e -> CompletableFuture.supplyAsync(() -> new WatsonAPI(removeArgs(textField.getText())).result())
                .thenApply(tone -> {
                    System.out.println("tone has been predicted: " + tone);
                    prediction.setText(tone.toString());
                    System.out.println("tone has been predicted: " + tone);
                    return 1;
                }));

        predictiveSentiment.add(prediction);
        predictiveSentiment.add(checkSentiment);

        overallContentPane.add(messageScrollPane);
        overallContentPane.add(textSendPanel);
        overallContentPane.add(predictiveSentiment);

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
