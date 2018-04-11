package view;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public final class GuiCreator {
    private GuiCreator() {}

    public static JLabel createLabel(String text, int horizontalAlignment, Font font) {
        JLabel label = new JLabel(text, horizontalAlignment);
        label.setFont(font);

        return label;
    }

    public static JPanel createPanel(LayoutManager layout, Dimension preferredSize, Border border) {
        JPanel panel = createPanel(layout, preferredSize);
        panel.setBorder(border);

        return panel;
    }

    public static JPanel createPanel(LayoutManager layout, Dimension preferredSize) {
        JPanel panel = createPanel(layout);
        panel.setPreferredSize(preferredSize);

        return panel;
    }

    public static JPanel createPanel(LayoutManager layout, Border border) {
        JPanel panel = createPanel(layout);
        panel.setBorder(border);

        return panel;
    }

    public static JPanel createPanel(LayoutManager layout) {
        JPanel panel = new JPanel();
        panel.setLayout(layout);

        return panel;
    }

    public static JPanel createPanel(Border border) {
        // check args

        JPanel panel = new JPanel();
        panel.setBorder(border);

        return panel;
    }

    public static JLayeredPane createLayeredPane(Dimension dimension, float alignmentX) {
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(dimension);
        layeredPane.setAlignmentX(alignmentX);

        return layeredPane;
    }
}
