import java.awt.*;
public enum Colors {
  GREY(128,128,128),
  RED(179, 35, 27),
  GREEN(144,238,144),
  PURPLE(197, 139, 231),
  LIGHTBLUE (30,144,255),
  YELLOW (255, 255, 0),
  ORANGE (255,127,80),
  LIGHT_ORANGE (255,179,71),
  BURGUNDY (172, 89, 94),
  MUTED_RED (203, 76, 78),
  YELLOW_ROSE (255,240,0),
  HOT_PINK (255,105,180),
  LIGHT_PURPLE (197, 139, 231),
  BLOOD_RED (136, 8, 8),
  PINK (255,192,203);



  private final int r;
  private final int g;
  private final int b;
  private final String rgb;

  private Colors(final int r,final int g,final int b) {
    this.r = r;
    this.g = g;
    this.b = b;
    this.rgb = r + ", " + g + ", " + b;
  }

  public String getRGB() {
    return rgb;
  }

  public int getRed(){
    return r;
  }

  public int getGreen(){
        return g;
    }

    public int getBlue(){
        return r;
    }

    public Color getColor(){
        return new Color(r,g,b);
    }
}


