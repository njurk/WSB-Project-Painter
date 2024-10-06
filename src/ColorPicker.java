import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ColorPicker extends JPanel {
    private static JLabel colorPreview;
    private static JSlider sliderR;
    private static JSlider sliderG;
    private static JSlider sliderB;
    private static JLabel valueR;
    private static JLabel valueG;
    private static JLabel valueB;
    private static JLabel brushColorLabel;
    private static JTextField hexField;

    public ColorPicker() {
        setLayout(new GridBagLayout());

        colorPreview = createPreviewLabel();
        valueR = new JLabel("0");
        valueG = new JLabel("0");
        valueB = new JLabel("0");

        brushColorLabel = new JLabel("Brush color:");
        hexField = new JTextField(7);
        hexField.setFont(new Font("Consolas", Font.BOLD, 14));
        hexField.setText("#000000");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 10, 5);
        add(brushColorLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        add(hexField, gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 3;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(3, 15, 5, 8);
        add(colorPreview, gbc);

        gbc.insets = new Insets(0, 0, 0, 2);
        gbc.gridheight = 1;
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(new JLabel("R:"), gbc);
        gbc.gridx = 2;
        add(valueR, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        add(new JLabel("G:"), gbc);
        gbc.gridx = 2;
        add(valueG, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        add(new JLabel("B:"), gbc);
        gbc.gridx = 2;
        add(valueB, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(8, 0, 2, 0);
        add(sliderR = createColorSlider(0), gbc);

        gbc.gridy = 5;
        add(sliderG = createColorSlider(0), gbc);

        gbc.gridy = 6;
        add(sliderB = createColorSlider(0), gbc);

        updateColorPreview();

        hexField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String hexText = hexField.getText().trim();
                if (isValidHexColor(hexText)) {
                    Color newColor = Color.decode(hexText);
                    setBrushColor(newColor);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid hex color code!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    static void updateColorPreview() {
        int r = sliderR.getValue(), g = sliderG.getValue(), b = sliderB.getValue();
        valueR.setText(String.valueOf(r));
        valueG.setText(String.valueOf(g));
        valueB.setText(String.valueOf(b));
        colorPreview.setBackground(new Color(r, g, b));

        String hexValue = String.format("#%02X%02X%02X", r, g, b);
        hexField.setText(hexValue);
    }

    public static Color getBrushColor() {
        int r = sliderR.getValue();
        int g = sliderG.getValue();
        int b = sliderB.getValue();

        return new Color(r, g, b);
    }

    public static void setBrushColor(Color color) {
        sliderR.setValue(color.getRed());
        sliderG.setValue(color.getGreen());
        sliderB.setValue(color.getBlue());

        updateColorPreview();
    }

    private JLabel createPreviewLabel() {
        JLabel preview = new JLabel();
        preview.setPreferredSize(new Dimension(45, 45));
        preview.setOpaque(true);
        return preview;
    }

    private JSlider createColorSlider(int initialValue) {
        JSlider slider = new JSlider(0, 255, initialValue);
        slider.addChangeListener(e -> updateColorPreview());
        return slider;
    }

    private boolean isValidHexColor(String hex) {
        return hex.matches("^#([0-9A-Fa-f]{6})$");
    }
}