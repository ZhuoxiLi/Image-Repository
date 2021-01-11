import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.*;

import static java.lang.Math.max;

class ImageController extends MouseAdapter {
    private MyModel model;
    private int totalWidth = 0;
    public ImageController(MyModel model){
        this.model = model;
    }

    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        int width = Thumbnail.WIDTH;

        if (totalWidth == 0) totalWidth = 800;
        int num_per_row = totalWidth / width;
        int id = -1;

        for (int i = 0; i < model.getThumbnails().size(); i++) {
            if (model.getDisplayMode().equals("list")) {
                num_per_row = 1;
            }
            int n_row = i / num_per_row;
            int n_col = i % num_per_row;
            int xx = n_col * Thumbnail.WIDTH;
            int yy = n_row * Thumbnail.HEIGHT;
            int border = n_row * Thumbnail.BORDER;
            if ((x > xx + border && x < xx + border + 240) && (y > yy + border && y < yy + border + 160)) {
                id = i;
            }
        }
        if (id == -1) return;
        if (e.getButton() == MouseEvent.BUTTON1) {
            // Left Click, zoom in the image
            model.setCurrentImg(id);

            JFrame frame = new JFrame();
            PopFrame p = new PopFrame(model.getCurrentImg(), model.getCurrentWidth(), model.getCurrentHeight());
            frame.add(p);
            frame.setSize(new Dimension(model.getCurrentWidth(), model.getCurrentHeight()));
            frame.setVisible(true);
        }
    }

    public void setWidth(int width) {
        totalWidth = width;
    }
}


public class ImageView extends JPanel implements Observer {

    private MyModel model;
    private ImageController controller;

    ImageView(MyModel model) {
        this.model = model;

        controller = new ImageController(model);
        controller.setWidth(800);
        this.addMouseListener(controller);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        List<Thumbnail> thumbnails = model.getThumbnails();
        int pict_width = 259;//new Thumbnail(model).getWidth();
        int pict_height = 260;//new Thumbnail(model).getHeight();
        int total_pics = thumbnails.size();
        int pics_per_row = getWidth() / pict_width;
        int rows;
        if (model.getDisplayMode().equals("grid")) {
            rows =total_pics / pics_per_row + (total_pics % pics_per_row == 0 ? 0 : 1);
        } else {
            rows = model.getThumbnails().size();
        }

        this.setPreferredSize(new Dimension(getWidth(), max(600, rows * pict_height)));
        for (int i = 0; i < total_pics; i++) {
            thumbnails.get(i).draw(g2, getWidth(), i, model.getDisplayMode());
        }

        repaint();
    }

    public void update(Object observable) {
        if (controller != null) {
            controller.setWidth(getWidth());
        }
        repaint();
    }
}
