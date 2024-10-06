import javax.swing.*;
import java.awt.*;

public class BrushSizePicker extends JPanel {
    int brushSize = 10;
    JLabel brushSizeLabel;
    JSlider brushSizeSlider;

    BrushSizePicker() {
        brushSizeLabel = new JLabel("Brush size: ");
        add(brushSizeLabel);

        brushSizeSlider = new JSlider(JSlider.HORIZONTAL, 2, 120, brushSize);
        brushSizeSlider.addChangeListener(e -> updateBrushSize());
        brushSizeSlider.setMajorTickSpacing(1);
        add(brushSizeSlider);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(brushSizeLabel, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(5, 0, 0, 0);
        add(brushSizeSlider, gbc);

        setOpaque(false);
        setVisible(true);
        updateBrushSize();
    }

    void updateBrushSize() {
        brushSize = brushSizeSlider.getValue();
        brushSizeLabel.setText("Brush size: " + brushSize);
    }

    public int getBrushSize() {
        return brushSize;
    }
}