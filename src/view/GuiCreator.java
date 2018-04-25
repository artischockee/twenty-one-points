package view;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

final class GuiCreator {
    private GuiCreator() {}

    static JTextArea createTextArea(String text, boolean lineWrap, boolean opaque, boolean editable) {
        JTextArea textArea = new JTextArea(text);

        textArea.setLineWrap(lineWrap);
        textArea.setWrapStyleWord(true);
        textArea.setOpaque(opaque);
        textArea.setEditable(editable);

        return textArea;
    }

    static JLabel createLabel(String text, int horizontalAlignment, Font font) {
        JLabel label = new JLabel(text, horizontalAlignment);
        label.setFont(font);

        return label;
    }

    static JPanel createPanel(LayoutManager layout, Dimension preferredSize, Border border) {
        JPanel panel = createPanel(layout, preferredSize);
        panel.setBorder(border);

        return panel;
    }

    static JPanel createPanel(LayoutManager layout, Dimension preferredSize) {
        JPanel panel = createPanel(layout);
        panel.setPreferredSize(preferredSize);

        return panel;
    }

    static JPanel createPanel(LayoutManager layout, Border border) {
        JPanel panel = createPanel(layout);
        panel.setBorder(border);

        return panel;
    }

    static JPanel createPanel(LayoutManager layout) {
        JPanel panel = new JPanel();
        panel.setLayout(layout);

        return panel;
    }

    static JPanel createPanel(Border border) {
        JPanel panel = new JPanel();
        panel.setBorder(border);

        return panel;
    }

    static LayeredPane createLayeredPane(Dimension dimension, float alignmentX, float alignmentY) {
        LayeredPane layeredPane = createLayeredPane(dimension, alignmentX);
        layeredPane.setAlignmentY(alignmentY);

        return layeredPane;
    }

    static LayeredPane createLayeredPane(float alignmentX, float alignmentY) {
        LayeredPane layeredPane = createLayeredPane(alignmentX);
        layeredPane.setAlignmentY(alignmentY);

        return layeredPane;
    }

    static LayeredPane createLayeredPane(Dimension dimension, float alignmentX) {
        LayeredPane layeredPane = createLayeredPane(dimension);
        layeredPane.setAlignmentX(alignmentX);

        return layeredPane;
    }

    static LayeredPane createLayeredPane(float alignmentX) {
        LayeredPane layeredPane = new LayeredPane();
        layeredPane.setAlignmentX(alignmentX);

        return layeredPane;
    }

    static LayeredPane createLayeredPane(Dimension dimension) {
        LayeredPane layeredPane = new LayeredPane();
        layeredPane.setPreferredSize(dimension);

        return layeredPane;
    }
}
