package client;

public class SenderTextSentimentModification {


  public static String updateString(String text) {
    String newText = text.replaceAll("what you doing", "wyd").replaceAll("What you doing?", "Wyd!?").
    replaceAll("What are you doing", "Wyd").replaceAll("what are you doing", "wyd")
    .replaceAll("how about you", "hbu").replaceAll("on my way", "OMW").replaceFirst("Hello", "Hey!")
    .replaceAll(".", "\n").replaceFirst("?", "...?").replaceAll("How are you doing?", "What's Up!!")
    .replaceAll("I have", "I've").replaceAll("It is", "It's").replaceAll("I am", "I'm")
    .replaceFirst("I don't know", "idk");
    return newText;
    //try the String "Hello. How are you doing? I am fine! Sorry I have been busy. What you doing."
  }


}
