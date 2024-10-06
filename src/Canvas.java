import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Canvas extends JPanel {
    private static BufferedImage image;
    private final List<BufferedImage> states = new ArrayList<>();
    private int currentStateIndex = -1;
    private int prevX, prevY;

    public Canvas() {
        setSize(1000, 800);
        image = new BufferedImage(1000, 800, BufferedImage.TYPE_INT_RGB);
        clearImage();
        saveState();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    prevX = e.getX();
                    prevY = e.getY();
                    drawOnCanvas(prevX, prevY, prevX, prevY);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    saveState();
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    int currX = e.getX();
                    int currY = e.getY();
                    drawOnCanvas(prevX, prevY, currX, currY);
                    prevX = currX;
                    prevY = currY;
                }
            }
        });
    }

    public static BufferedImage getImage() {
        return image;
    }

    private void drawOnCanvas(int x1, int y1, int x2, int y2) {
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(ColorPicker.getBrushColor());
        int brushSize = PainterFrame.getToolBar().getBrushSizePicker().getBrushSize();
        g2d.setStroke(new BasicStroke(brushSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        g2d.drawLine(x1, y1, x2, y2);
        g2d.dispose();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }

    public void clearCanvas() {
        clearImage();
        repaint();
        saveState();
    }

    private void clearImage() {
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
        g2d.dispose();
    }

    private void saveState() {
        if (currentStateIndex != states.size() - 1) {
            states.subList(currentStateIndex + 1, states.size()).clear();
        }

        BufferedImage state = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        Graphics2D g2d = state.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        states.add(state);
        currentStateIndex++;
    }

    public void undo() {
        if (currentStateIndex > 0) {
            currentStateIndex--;
            restoreState();
        }
    }

    public void redo() {
        if (currentStateIndex < states.size() - 1) {
            currentStateIndex++;
            restoreState();
        }
    }

    private void restoreState() {
        BufferedImage state = states.get(currentStateIndex);
        Graphics2D g2d = image.createGraphics();
        g2d.drawImage(state, 0, 0, null);
        g2d.dispose();
        repaint();
    }
}