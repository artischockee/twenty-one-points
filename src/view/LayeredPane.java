package view;

import javax.swing.*;

public class LayeredPane extends JLayeredPane {
    // Attributes ::

    public static final int OFFSET = 30;

    private int _boundx;
    private int _boundy;
    private int _layerPosition;

    // Constructor ::

    public LayeredPane() {
        super();

        _boundx = 0;
        _boundy = 0;
        _layerPosition = 0;
    }


    // Accessors (get/set) ::

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


    // Methods ::

    public void reset() {
        this.removeAll();
        this.revalidate();
        this.repaint();

        _boundx = 0;
        _boundy = 0;
        _layerPosition = 0;
    }
}
