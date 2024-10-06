import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ColorSave extends JPanel {
    private static final int GRID_SIZE = 20;
    private final ArrayList<Color> savedColors;
    private final JPanel[] colorCells;
    private final JButton btnSave;
    private Color selectedColor;

    public ColorSave() {
        savedColors = new ArrayList<>();
        colorCells = new JPanel[GRID_SIZE];

        setLayout(new BorderLayout());

        JLabel label = new JLabel("Saved colors:");
        label.setHorizontalAlignment(SwingConstants.LEFT);
        add(label, BorderLayout.NORTH);

        JPanel gridPanel = new JPanel(new GridLayout(4, 5));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(gridPanel, BorderLayout.CENTER);

        for (int i = 0; i < GRID_SIZE; i++) {
            colorCells[i] = createInactiveCell();
            gridPanel.add(colorCells[i]);
        }

        initializeDefaultColors();

        btnSave = new JButton("Save current color");
        btnSave.addActionListener(e -> saveColor(getCurrentColor()));
        add(btnSave, BorderLayout.SOUTH);
    }

    private JPanel createInactiveCell() {
        JPanel cell = new JPanel();
        cell.setOpaque(false);
        cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return cell;
    }

    private void makeCellActive(JPanel cell, Color color) {
        cell.setOpaque(true);
        cell.setBackground(color);
        cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        cell.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    selectedColor = cell.getBackground();
                    passColorToPicker(selectedColor);
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    removeColor(cell);
                }
            }
        });
    }

    private void initializeDefaultColors() {
        Color[] defaultColors = {Color.BLACK, Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};
        for (Color color : defaultColors) {
            saveColor(color);
        }
    }

    public void saveColor(Color color) {
        if (savedColors.contains(color)) {
            JOptionPane.showMessageDialog(null, "This color is already saved!", "Duplicate Color", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (savedColors.size() < GRID_SIZE) {
            for (int i = 0; i < GRID_SIZE; i++) {
                if (colorCells[i].getBackground() == null || !colorCells[i].isOpaque()) {
                    savedColors.add(color);
                    makeCellActive(colorCells[i], color);
                    return;
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "You have saved the maximum number of colors!", "Maximum Colors Reached", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void removeColor(JPanel cell) {
        int index = -1;
        for (int i = 0; i < savedColors.size(); i++) {
            if (colorCells[i] == cell) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            savedColors.remove(index);
            for (int i = index; i < savedColors.size(); i++) {
                colorCells[i].setBackground(savedColors.get(i));
            }

            colorCells[savedColors.size()].setOpaque(false);
            colorCells[savedColors.size()].setBackground(null);
            colorCells[savedColors.size()].removeMouseListener(colorCells[savedColors.size()].getMouseListeners()[0]);
        }
    }

    public Color getCurrentColor() {
        return ColorPicker.getBrushColor();
    }

    public void passColorToPicker(Color color) {
        ColorPicker.setBrushColor(color);
    }
}