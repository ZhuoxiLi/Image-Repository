import javax.swing.*;
import java.awt.*;

public class PopFrame extends JComponent {
    ImageIcon imageIcon;
    int imageWidth;
    int imageHeight;
    PopFrame(ImageIcon ic, int width, int height) {
        imageIcon = ic;
        imageWidth = width;
        imageHeight = height;
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.setColor(Color.BLACK);
        imageIcon.paintIcon(null, g2, (getWidth() - imageWidth)/2, (getHeight() - imageHeight)/2);
    }
}
