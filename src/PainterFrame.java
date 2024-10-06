import javax.swing.*;
import java.awt.*;

public class PainterFrame extends JFrame {
    private static ToolBar toolBar;
    private static Canvas canvas;

    public PainterFrame() {
        toolBar = new ToolBar(new ColorPicker(), new ColorSave(), new BrushSizePicker());
        canvas = new Canvas();

        setTitle("Painter");
        setSize(new Dimension(1100, 700));
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        add(toolBar, BorderLayout.EAST);
        add(canvas, BorderLayout.CENTER);

        setVisible(true);
    }

    public static ToolBar getToolBar() {
        return toolBar;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PainterFrame::new);
    }

    public static Canvas getCanvas() {
        return canvas;
    }
}