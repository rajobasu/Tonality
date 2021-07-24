package resources;

import java.util.Arrays;

public enum Tone {
    ANGER("/ag", ToneColor.BLOOD_RED, "This text might be perceived by the receiver as if sent with an emotion " +
        "of anger"),
    FEAR("/fr", ToneColor.BURGUNDY,"This text might be perceived by the receiver as if sent with a sense of fear"),
    JOY("/jy", ToneColor.GREEN, "This text might be perceived by the receiver as if sent out of joy."),
    SADNESS("/sd", ToneColor.LIGHT_PURPLE, "This text might be perceived by the receiver as if sent with an emotion " +
        "of sadness"),
    ANALYTICAL("/an", ToneColor.MUTED_RED, "This text might make the receiver believe as you being an intellectual"),
    CONFIDENT("/cd", ToneColor.ORANGE, "This text might make the receiver believe that you are quite confident"),
    SARCASTIC("/sr", ToneColor.GREY, "This text might make the receiver think that you are being sarcastic"),
    DISAPPOINTMENT("/ds", ToneColor.LIGHTBLUE, "This text might be perceived by the receiver as if sent out of " +
        "disappointment"),
    JOKING("/jk", ToneColor.YELLOW, "This text might make the receiver think that you are joking"),
    SERIOUS("/sr", ToneColor.RED, "This text might be perceived by the receiver as if you are damn serious"),
    LITTLE_UPSET("/lu", ToneColor.LIGHT_ORANGE, "This text might be perceived by the receiver as if you are little " +
        "upset"),
    EXCITED("/ex", ToneColor.YELLOW_ROSE, "This text might be perceived by the receiver as if sent out of excitement"),
    TENTATIVE("/tt", ToneColor.HOT_PINK, "This text might be perceived by the receiver as if sent with a sense of " +
        "doubt"),
    NONE("/NONE");

    private String marker;
    private ToneColor associatedToneColor;
    private String description;

    Tone(String marker, ToneColor associatedColor, String description) {
        this.marker = marker;
        this.associatedToneColor = associatedColor;
        this.description = description;
    }

    Tone(String marker) {
        this(marker, ToneColor.WHITE, "No Specific tone detected!");
    }

    public static Tone getTone(String text) {
        return Arrays.stream(Tone.values())
            .filter(s -> text.contains(s.marker))
            .findFirst()
            .orElse(NONE);
    }

    public ToneColor getAssociatedToneColor() {
        return associatedToneColor;
    }

    public String getMarker() {
        return marker;
    }

    public String getDescription() {
        return description;
    }
}
