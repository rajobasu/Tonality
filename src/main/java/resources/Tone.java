package resources;

import java.util.Arrays;

public enum Tone {
    ANGER("/ag", ToneColor.BLOOD_RED),
    FEAR("/fr", ToneColor.BURGUNDY),
    JOY("/jy", ToneColor.GREEN),
    SADNESS("/sd", ToneColor.LIGHT_PURPLE),
    ANALYTICAL("/an", ToneColor.MUTED_RED),
    CONFIDENT("/cd", ToneColor.ORANGE),
    SARCASTIC("/sr", ToneColor.GREY),
    DISAPPOINTMENT("/ds", ToneColor.LIGHTBLUE),
    JOKING("/jk", ToneColor.YELLOW),
    SERIOUS("/sr", ToneColor.RED),
    LITTLE_UPSET("/lu", ToneColor.LIGHT_ORANGE),
    EXCITED("/", ToneColor.YELLOW_ROSE),
    TENTATIVE("/tt", ToneColor.HOT_PINK),
    NONE("/NONE");

    private String marker;
    private ToneColor associatedToneColor;

    Tone(String marker, ToneColor associatedColor) {
        this.marker = marker;
        this.associatedToneColor = associatedColor;
    }

    Tone(String marker) {
        this(marker, ToneColor.WHITE);
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
}
