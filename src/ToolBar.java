import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class ToolBar extends JPanel {
    private final JButton btnUndo;
    private final JButton btnRedo;
    private final JButton btnExport;
    private final JButton btnClear;
    private final ColorPicker colorPicker;
    private final ColorSave colorSave;
    private final BrushSizePicker brushSizePicker;

    public ToolBar(ColorPicker colorPicker, ColorSave colorSave, BrushSizePicker brushSizePicker) {
        this.colorPicker = colorPicker;
        this.colorSave = colorSave;
        this.brushSizePicker = brushSizePicker;

        setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel exportPanel = new JPanel(new GridLayout(1, 1));
        btnExport = new JButton("Export");
        btnExport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportImage();
            }
        });
        exportPanel.add(btnExport);
        add(exportPanel);

        JPanel blank = new JPanel();
        blank.setSize(new Dimension(30, 25));
        add(blank);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));
        add(buttonPanel);

        btnUndo = new JButton("Undo");
        btnUndo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PainterFrame.getCanvas().undo();
            }
        });

        btnRedo = new JButton("Redo");
        btnRedo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PainterFrame.getCanvas().redo();
            }
        });

        buttonPanel.add(btnUndo);
        buttonPanel.add(btnRedo);

        add(colorPicker);
        add(colorSave);
        add(brushSizePicker);

        btnClear = new JButton("Clear");
        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PainterFrame.getCanvas().clearCanvas();
            }
        });

        JPanel clearPanel = new JPanel(new GridLayout(1, 1));
        clearPanel.add(btnClear);
        add(clearPanel);
    }

    private void exportImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Image");

        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("PNG Images", "png"));

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            if (!fileToSave.getAbsolutePath().endsWith(".png")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".png");
            }

            try {
                ImageIO.write(Canvas.getImage(), "png", fileToSave);
                JOptionPane.showMessageDialog(null, "Image saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error saving image: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public BrushSizePicker getBrushSizePicker() {
        return brushSizePicker;
    }
}