package client;

import resources.Tone;

public class SenderTextToneParser {
    public static Tone getToneOfText(String text) {
        return Tone.getTone(text);
    }

    public static String removeArgs(String text) {
        String marker = getToneOfText(text).getMarker();
        return text.split(marker)[0];
    }
}
