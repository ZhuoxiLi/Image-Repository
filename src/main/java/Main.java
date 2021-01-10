import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class loadButtonListener implements ActionListener {
    MyModel m;
    public loadButtonListener(MyModel model) {
        m = model;
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        m.openFile();
    }
}

public class Main {
    public static void main(String[] args) {
        MyModel model = new MyModel();

        // Set up the window.
        JFrame frame = new JFrame("Display Your Image");
        frame.setLayout(new BorderLayout());

        model.notifyObservers();

        JMenuBar menubar = new JMenuBar();
        frame.setJMenuBar(menubar);
        JMenu fileMenu = new JMenu("File");
        menubar.add(fileMenu);

        ImageView imageView = new ImageView(model);
        imageView.setPreferredSize(new Dimension(800, 600));
        model.addObserver(imageView);

        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setPreferredSize(new Dimension(800, 600));
        jScrollPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int height = imageView.getHeight();
                int width = e.getComponent().getWidth();
                imageView.setPreferredSize(new Dimension(width, height));
            }
        });
        jScrollPane.setViewportView(imageView);

        Toolbar toolbar = new Toolbar(model);
        model.addObserver(toolbar);
        toolbar.setPreferredSize(new Dimension(800, 30));
        frame.add(toolbar, BorderLayout.PAGE_START);

        frame.add(jScrollPane);
        JMenuItem loadButton = new JMenuItem("Load...");
        loadButton.addActionListener(new loadButtonListener(model));
        fileMenu.add(loadButton);

        frame.setMinimumSize(new Dimension(800/3, 128));
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        model.notifyObservers();

        frame.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                model.storeAll();
            }
        });
        frame.setVisible(true);
    }
}
