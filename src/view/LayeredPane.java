package view;

import javax.swing.*;
import java.awt.*;

public class LayeredPane extends JLayeredPane {
    public static final int OFFSET = 30;
    public static final Dimension DEFAULT_DIMENSION;
    private static final int DEFAULT_CARD_WIDTH = 84;   // these fields should probably lie
    private static final int DEFAULT_CARD_HEIGHT = 128; // in Card class, but who knows..

    private int boundx;
    private int boundy;

    static {
        DEFAULT_DIMENSION = new Dimension(DEFAULT_CARD_WIDTH, DEFAULT_CARD_HEIGHT);
    }

    LayeredPane() {
        boundx = 0;
        boundy = 0;
    }

    public int getBoundX() {
        return boundx;
    }

    public int getBoundY() {
        return boundy;
    }

    @Override
    public Component add(Component comp) {
        this.addImpl(comp, null, 0);

        boundx += OFFSET;

        return comp;
    }

    public void increaseWidth() {
        this.setPreferredSize(new Dimension(this.getWidth() + OFFSET, DEFAULT_CARD_HEIGHT));
    }

    public void reset() {
        this.removeAll();
        this.setPreferredSize(DEFAULT_DIMENSION);
        this.revalidate();
        this.repaint();

        boundx = 0;
        boundy = 0;
    }
}
