package client;

public class SenderTextSentimentModification {
    public static Tone getToneOfText(String text) {
        return Tone.getTone(text);
    }

    public static String removeArgs(String text) {
        String marker = getToneOfText(text).getMarker();
        return text.split(marker)[0];
    }
  public static String updateString(String text) {
    String newText = text.replaceAll("what you doing", "wyd").replaceAll("what are you doing", "wyd")
    .replaceAll("how about you", "hbu").replaceAll("on my way", "OMW").replaceFirst("Hello", "Hey!")
    .replaceAll(".", "\n");
    return newText;
  }


}
