package resources;

import java.util.Arrays;

public enum Tones {
    ANGER("/ag"),
    FEAR("/fr"),
    JOY("/jy"),
    SADNESS("/sd"),
    ANALYTICAL("/an"),
    CONFIDENT("/cd"),
    SARCASTIC("/sr"),
    DISAPPOINTMENT("/ds"),
    JOKING("/jk"),
    SERIOUS("/sr"),
    LITTLE_UPSET("/lu"),
    EXCITED("/"),
    TENTATIVE ("/tt"),
    NONE ("");

    private String marker;

    Tones(String marker) {
        this.marker = marker;
    }

    public static Tones getTone(String text) {
        return Arrays.stream(Tones.values())
                    .filter(s -> text.contains(s.marker))
                    
    }
}
