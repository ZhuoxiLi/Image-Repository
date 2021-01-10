import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class ToolbarController extends MouseAdapter {
    private MyModel model;
    private int totalWidth = 0;
    public ToolbarController(MyModel model){
        this.model = model;
    }

    public void mouseClicked(MouseEvent e) {}
}

class ListButtonController extends MouseAdapter {
    private MyModel model;
    public ListButtonController(MyModel model){
        this.model = model;
    }

    public void mouseClicked(MouseEvent e){
        model.setDisplayMode("list");
    }
}
class GridButtonController extends MouseAdapter {
    private MyModel model;
    public GridButtonController(MyModel model){
        this.model = model;
    }

    public void mouseClicked(MouseEvent e){
        model.setDisplayMode("grid");
    }
}
public class Toolbar extends JPanel implements Observer{
    MyModel model;

    private JLabel gridLabel;
    private JLabel listLabel;

    public Toolbar(MyModel model) {
        this.model = model;
        gridLabel = new JLabel(new ImageIcon("src/main/image/grid.png"));
        gridLabel.addMouseListener(new GridButtonController(model));
        listLabel = new JLabel(new ImageIcon("src/main/image/list.png"));
        listLabel.addMouseListener(new ListButtonController(model));

        this.add(gridLabel);
        this.add(listLabel);
        this.addMouseListener(new ToolbarController(model));
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 80, 0));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    @Override
    public void update(Object observable) {
        gridLabel.setEnabled(true);
        listLabel.setEnabled(true);
        if(model.getDisplayMode().equals("grid")){
            gridLabel.setEnabled(false);
        }else{
            listLabel.setEnabled(false);
        }
        repaint();
    }
}
