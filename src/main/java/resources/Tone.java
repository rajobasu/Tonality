package resources;

import java.util.Arrays;

public enum Tone {
    ANGER("/ag", ToneColor.BLOOD_RED),
    FEAR("/fr", ToneColor.BURGUNDY),
    JOY("/jy", ToneColor.HOT_PINK),
    SADNESS("/sd", ToneColor.LIGHT_PURPLE),
    ANALYTICAL("/an", ToneColor.MUTED_RED),
    CONFIDENT("/cd", ToneColor.LIGHT_ORANGE),
    SARCASTIC("/sr"),
    DISAPPOINTMENT("/ds"),
    JOKING("/jk"),
    SERIOUS("/sr"),
    LITTLE_UPSET("/lu"),
    EXCITED("/"),
    TENTATIVE("/tt"),
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
