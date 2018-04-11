package view;

import java.awt.*;

public class GBConstraints extends GridBagConstraints {
    private int _gridX;
    private int _gridY;

    GBConstraints(int fill) {
        if (fill < GridBagConstraints.NONE || fill > GridBagConstraints.VERTICAL)
            throw new IllegalArgumentException();

        this.fill = fill;

        _gridX = 0;
        _gridY = 0;
    }

    public void assembly(Insets insets) {
        if (insets == null)
            throw new IllegalArgumentException();

        this.assembly();
        this.insets = insets;
    }

    public void assembly() {
        this.gridx = _gridX;
        this.gridy = _gridY++;
    }
}
