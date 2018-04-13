package view;

import java.awt.*;

class GBConstraints extends GridBagConstraints {
    static final int X = 0;
    static final int Y = 1;

    GBConstraints(int fill) {
        if (fill < GridBagConstraints.NONE || fill > GridBagConstraints.VERTICAL)
            throw new IllegalArgumentException();

        this.fill = fill;
        this.gridx = 0;
        this.gridy = 0;
        this.weightx = 0;
        this.weighty = 0;
    }

    void assembly(int direction, Insets insets) {
        this.assembly(direction);
        this.insets = insets;
    }

    void assembly(int direction) {
        switch (direction) {
            case X:
                this.gridx++;
                break;
            case Y:
                this.gridy++;
                break;
        }
    }

    void assembly(Insets insets) {
        this.insets = insets;
    }

    void reset() {
        this.gridx = 0;
        this.gridy = 0;
        this.weightx = 0;
        this.weighty = 0;
    }
}
