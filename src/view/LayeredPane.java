package view;

import javax.swing.*;
import java.awt.*;

public class LayeredPane extends JLayeredPane {
    public static final int OFFSET = CardImage.getOffset();
    public static final Dimension DEFAULT_DIMENSION;

    private int boundx;
    private int boundy;

    static {
        DEFAULT_DIMENSION = new Dimension(
            CardImage.getCardWidth(),
            CardImage.getCardHeight()
        );
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
        this.setPreferredSize(new Dimension(
            this.getWidth() + OFFSET,
            (int) DEFAULT_DIMENSION.getHeight()
        ));
    }

    void reset() {
        this.removeAll();
        this.setPreferredSize(DEFAULT_DIMENSION);
        this.revalidate();
        this.repaint();

        boundx = 0;
        boundy = 0;
    }
}
