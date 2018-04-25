package view;

import javax.swing.*;
import java.awt.*;

public class LayeredPane extends JLayeredPane {
    public static final int OFFSET = 30;
    public static final int DEFAULT_CARD_WIDTH = 84;
    public static final int DEFAULT_CARD_HEIGHT = 128;

    private int _boundx;
    private int _boundy;
    private int _layerPosition;


    public LayeredPane() {
        super();

        _boundx = 0;
        _boundy = 0;
        _layerPosition = 0;
    }


    public int getBoundX() {
        return _boundx;
    }

    public void setBoundX(int bound) {
        _boundx = bound;
    }

    public int getBoundY() {
        return _boundy;
    }

    public void setBoundY(int bound) {
        _boundy = bound;
    }

    public int getLayerPosition() {
        return _layerPosition;
    }

    public void setLayerPosition(int position) {
        _layerPosition = position;
    }


    public void reset() {
        this.removeAll();
        this.setPreferredSize(new Dimension(DEFAULT_CARD_WIDTH, DEFAULT_CARD_HEIGHT));
        this.revalidate();
        this.repaint();

        _boundx = 0;
        _boundy = 0;
        _layerPosition = 0;
    }
}
