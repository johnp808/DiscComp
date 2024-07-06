package uzis.world.gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

class MyListCellRenderer extends DefaultListCellRenderer {
    private Color textColor = new Color(202, 178, 251); // Example: purple color

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        String text = value.toString();

        // Ensure the background of each cell is transparent
        setBackground(new Color(0, 0, 0, 0)); // Transparent background
        setOpaque(isSelected); // Only make background opaque when item is selected

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setForeground(textColor);
        }

        // Use HTML to style the text color and optionally add a separator.
        String styledText = "<html><body style='color: rgb(" +
                            textColor.getRed() + "," + textColor.getGreen() + "," +
                            textColor.getBlue() + ");'>";
        styledText += text;
        if (index < list.getModel().getSize() - 1) { // No separator after last item
            styledText += "<br>---------------------------------------------------------------------------------";
        }
        styledText += "</body></html>";
        setText(styledText);

        return this;
    }
}
