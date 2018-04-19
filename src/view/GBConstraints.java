package view;

import java.awt.*;

class GBConstraints extends GridBagConstraints {
    GBConstraints(int gridx, int gridy, int fill, Insets insets) {
        this(gridx, gridy, fill);
        this.insets = insets;
    }

    GBConstraints(int gridx, int gridy, int fill) {
        this(gridx, gridy);
        this.fill = fill;
    }

    GBConstraints(int gridx, int gridy) {
        this.gridx = gridx;
        this.gridy = gridy;
    }

    GBConstraints(int fill) {
        if (fill < GridBagConstraints.NONE || fill > GridBagConstraints.VERTICAL)
            throw new IllegalArgumentException();

        this.fill = fill;
        this.gridx = 0;
        this.gridy = 0;
        this.weightx = 0;
        this.weighty = 0;
    }

    GBConstraints() {}

    void reset() {
        this.gridx = 0;
        this.gridy = 0;
        this.gridwidth = 0;
        this.gridheight = 0;
        this.weightx = 0.0D;
        this.weighty = 0.0D;
        this.anchor = 0;
        this.fill = 0;
        this.insets = new Insets(0, 0, 0, 0);
        this.ipadx = 0;
        this.ipady = 0;
    }
}
