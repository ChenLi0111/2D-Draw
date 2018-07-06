import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observer;
import java.util.Observable;

public class DrawView extends JPanel implements Observer {

    private Model model;
    private Shape shape;

    //Create a new View.
    DrawView(Model model) {
        // Hook up this observer so that it will be notified when the model changes.
        this.model = model;

        this.addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e) {
                shape = new Shape();
                // set shape color
                shape.setColour(model.get_color());
                //change shape
                shape.set_thickness(model.get_thickness());
                // set scale
                shape.setScale(1.0f);
                model.add_shape(shape);
                repaint();
            }
        });

        this.addMouseMotionListener(new MouseAdapter(){
            public void mouseDragged(MouseEvent e) {
                shape.addPoint(e.getX(), e.getY());
                repaint();
            }
        });
    }

    // custom graphics drawing
    @Override
    public void paintComponent(Graphics g) {
        setBackground(Color.white);
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g; // cast to get 2D drawing methods
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  // antialiasing look nicer
                RenderingHints.VALUE_ANTIALIAS_ON);

        if (model.get_slider_pre() != 100) {
            for (Shape s: model.get_print_shape_collection()) {
                if (s != null) {
                    s.draw(g2);
                }
            }
        } else {
            for (Shape s: model.get_shape_collection()) {
                if (s != null) {
                    s.draw(g2);
                }
            }
        }
    }

    //Update with data from the model.
    @Override
    public void update(Observable arg0, Object arg1) {
        revalidate();
        repaint();
        //System.out.println("DrawView: Model changed!");
    }
}
