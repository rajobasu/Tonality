package client;

public class SenderTextSentimentModification {

    public static String updateString(String text) {
        String newText = text.replaceAll("what you doing", "wyd")
            .replaceAll("what are you doing", "wyd")
            .replaceAll("how about you", "hbu")
            .replaceAll("on my way", "OMW")
            .replaceFirst("Hello", "Hey!")
            .replaceAll("\\.", "\n")
            .replaceFirst("\\?", "...?")
            .replaceAll("How are you doing\\?", "What's Up!!")
            .replaceFirst("I don't know", "idk");

        return newText;
    }


}
