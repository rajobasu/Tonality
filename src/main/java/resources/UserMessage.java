package resources;

import java.io.Serializable;

import client.SenderTextToneParser;

public class UserMessage implements Serializable {

    private String senderID;
    private String receiverID;
    private String body;
    private Tone tone;
    private long timeStamp;

    public UserMessage(String body, String senderID, String receiverID, Tone tone) {
        this.body = body;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.timeStamp = System.currentTimeMillis();
        this.tone = tone;
    }

    public UserMessage(String body, String senderID, String receiverID) {
        this(SenderTextToneParser.removeArgs(body), senderID, receiverID,
            SenderTextToneParser.getToneOfText(body));
    }


    public String getSenderID() {
        return senderID;
    }

    public String getReceiverID() {
        return receiverID;
    }

    public String getBody() {
        return body;
    }

    public long getTimestamp() {
        return timeStamp;
    }

    public Tone getTone() {
        return tone;
    }

    public String toString() {
        return body + " Time :: " + timeStamp;
    }
}
