import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.List;

public class MyModel {
    private List<Observer> observers; // will be notified if model got changed
    private List<Thumbnail> thumbnails = new ArrayList<>();
    private String displayMode = "grid"; // default mode

    private ImageIcon currentImg = null;
    private int currentWidth;
    private int currentHeight;

    public MyModel() {
        this.observers = new ArrayList<>();
        try {
            loadCache();
        } catch (Exception e) {
            try {
                PrintWriter writer = new PrintWriter(".loaded_image.txt");
                writer.print("");
                writer.close();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void loadCache() throws IOException, InputMismatchException {
        FileReader fileReader = new FileReader(".loaded_image.txt");

        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append("\n");
        }
        Scanner scanner = new Scanner(stringBuilder.toString());
        while (scanner.hasNext()) {
            String name = scanner.nextLine();
            String time = scanner.nextLine();
            int width = scanner.nextInt();
            scanner.nextLine();
            int height = scanner.nextInt();
            scanner.nextLine();
            String path = scanner.nextLine();

            ImageIcon iconOri = new ImageIcon(path);
            ImageIcon icon = new ImageIcon();

            if (width >= 800 || height >= 600) {
                width = 800;
                height = 600;
                iconOri.setImage(iconOri.getImage().getScaledInstance(800, 600, Image.SCALE_DEFAULT));
            }
            icon.setImage(iconOri.getImage().getScaledInstance(240, 160, Image.SCALE_DEFAULT));
            Thumbnail t = new Thumbnail(this, icon, iconOri, name, time, width, height, path);
            addThumbnail(t);
        }
        fileReader.close();
    }

    public void addThumbnail(Thumbnail t) {
        thumbnails.add(t);
    }

    public void openFile() {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setDialogTitle("Open File");
        jFileChooser.showOpenDialog(null);
        String path;
        String name;
        try {
            path = jFileChooser.getSelectedFile().getPath();
            name = jFileChooser.getSelectedFile().getName();
        } catch (NullPointerException e){
            e.printStackTrace();
            return;
        }

        int width = 240, height = 160;
        try {
            BufferedImage image = ImageIO.read(jFileChooser.getSelectedFile());
            width = image.getWidth();
            height = image.getHeight();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String time = sdf.format(jFileChooser.getSelectedFile().lastModified());
        ImageIcon iconOri = new ImageIcon(path);
        ImageIcon icon = new ImageIcon();

        if (width > 800 || height > 600) {
            width = 800;
            height = 600;
            iconOri.setImage(iconOri.getImage().getScaledInstance(800, 600, Image.SCALE_DEFAULT));
        }
        icon.setImage(iconOri.getImage().getScaledInstance(240, 160, Image.SCALE_DEFAULT));
        addThumbnail(new Thumbnail(this, icon, iconOri, name, time, width, height, path));
    }

    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }

    public void notifyObservers() {
        for (Observer observer: this.observers) {
            observer.update(this);
        }
    }

    public List<Thumbnail> getThumbnails() {
        return thumbnails;
    }

    public void setDisplayMode(String grid) {
        displayMode = grid;
        notifyObservers();
    }

    public String getDisplayMode() {
        return displayMode;
    }

    public ImageIcon getCurrentImg() {
        return currentImg;
    }

    public void setCurrentImg(int i) {
        if (i < thumbnails.size()) {
            Thumbnail t = thumbnails.get(i);
            currentImg = t.getImage();
            currentWidth = t.getImageWidth();
            currentHeight = t.getImageHeight();
        }
    }

    public int getCurrentWidth() {
        return currentWidth;
    }

    public int getCurrentHeight() {
        return currentHeight;
    }

    public void storeAll() {
        // Save all loaded Images
        try {
            FileWriter fileWriter = new FileWriter(".loaded_image.txt");
            for (Thumbnail t : thumbnails) {
                fileWriter.write(t.getFile_Name());
                fileWriter.write("\n");
                fileWriter.write(t.getFile_Time());
                fileWriter.write("\n");
                fileWriter.write(""+t.getFile_Width());
                fileWriter.write("\n");
                fileWriter.write(""+t.getFile_Height());
                fileWriter.write("\n");
                fileWriter.write(t.getFile_path());
                fileWriter.write("\n");
            }
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "[ERROR] Invalid Operation");
        }
    }
}
