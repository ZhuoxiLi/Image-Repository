import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class Thumbnail extends Component {
    public final static int WIDTH = 259;
    public final static int HEIGHT = 244;
    public final static int BORDER = 10;

    private MyModel model;
    private final String file_path;
    private final String file_name;
    private final String file_date;
    private int imageWidth;
    private int imageHeight;
    private ImageIcon image;
    private ImageIcon imageOri;

    public int getWidth() {
        return WIDTH;
    }

    Thumbnail(MyModel model, ImageIcon image, ImageIcon imageOri, String name, String date, int imageWidth, int imageHeight, String path) {
        this.model = model;
        this.image = image;
        this.imageOri = imageOri;
        file_name = name;
        file_date = date;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.file_path = path;
    }

    public void draw(Graphics2D g2, int total_width, int id, String status) {
        int width = WIDTH, height = HEIGHT;

        if (status.equals("list")) {
            width = total_width;
        }

        final int number_per_row = total_width / width;
        final int row = id / number_per_row;
        final int col = id % number_per_row;
        final int start_x = col * width;
        final int start_y = row * height;

        image.paintIcon(this, g2, start_x + BORDER, start_y + BORDER);

        if (status.equals("list")) {
            g2.drawString(file_name, getStartX(total_width, id, status), getStartY(total_width, id, status) + 40 -130);
            g2.drawString(file_date, getStartX(total_width, id, status), getStartY(total_width, id, status) + 60-130);
        } else {
            g2.drawString(file_name, getStartX(total_width, id, status), getStartY(total_width, id, status) + 40);
            g2.drawString(file_date, getStartX(total_width, id, status), getStartY(total_width, id, status) + 60);
        }

    }

    private int getStartX(int total_width, int id, String status) {
        int width = WIDTH;

        if (status.equals("list")) {
            width = total_width;
        }

        final int number_per_row = total_width / width;
        final int col = id % number_per_row;
        final int start_x =  col * width;

        return start_x + (width - 140) / 2;
    }

    private int getStartY(int total_width, int id, String status) {
        int width = WIDTH, height = HEIGHT;

        if (status.equals("list")) {
            width = total_width;
        }

        final int number_per_row = total_width / width;
        final int row = id / number_per_row;
        final int start_y =  row * height;

        return start_y + height - 65;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public ImageIcon getImage() {
        return imageOri;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public String getFile_path() {
        return file_path;
    }

    public String getFile_Name() {
        return file_name;
    }

    public String getFile_Time() {
        return file_date;
    }

    public int getFile_Width() {
        return imageWidth;
    }

    public int getFile_Height() {
        return imageHeight;
    }
}
