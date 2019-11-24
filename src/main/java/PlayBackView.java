import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observer;
import java.util.Observable;

public class PlayBackView extends JPanel implements Observer {
    private int max_stroke = 100;
    private int min_stroke = 0;

    private JButton play = new JButton(new ImageIcon("images/play.png"));
    private JSlider slider = new JSlider(min_stroke, max_stroke, min_stroke);
    private JButton start = new JButton(new ImageIcon("images/start.png"));
    private JButton end = new JButton(new ImageIcon("images/end.png"));
    private JButton play_back = new JButton(new ImageIcon("images/play_back.png"));

    private Model model;

    private void config_slider() {
        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JSlider s = (JSlider)e.getSource();
                model.set_play_prentage(s.getValue());
                model.update_collection_now();
            }
        });
        slider.setPaintTicks(true);
    }

    private void config_button() {
        play.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                model.set_play();
                //System.out.println("play");
            }
        });

        end.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                model.set_end();
                //System.out.println("end");
            }
        });

        start.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                model.set_start();
                //slider.setValue(0);
                //System.out.println("start");
            }
        });

        play_back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                model.set_play_back();
            }
        });
    }

    //Create a new View.
    PlayBackView(Model model) {
        this.model = model;

        this.setLayout(new GridBagLayout());
        // create a constraints object
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 0.1; // the proportion of space to give this column
        config_button();
        this.add(play, gc);
        config_slider();
        gc.weightx = 0.7; // the proportion of space to give this column
        this.add(slider, gc);
        gc.weightx = 0.1; // the proportion of space to give this column
        this.add(start, gc);
        this.add(end, gc);
        this.add(play_back, gc);
    }

    private void update_slider() {
        int number_stroke = model.get_shape_collection().size();
        if (number_stroke > 0) {
            float space = (max_stroke - min_stroke) / number_stroke;
            slider.setMajorTickSpacing((int) space);
        }

        if (model.get_need_change_slider() && model.get_with_timer()) {
            //System.out.println("first if");
            slider.setValue(model.get_slider_pre());
            model.set_need_change_slider(false);
        } else if (model.get_need_change_slider()) {
            //System.out.println("second if");
            slider.setValue(max_stroke);
            model.set_need_change_slider(false);
        }
    }

    //Update with data from the model.
    @Override
    public void update(Observable arg0, Object arg1) {
        update_slider();
        //System.out.println("PlayBackView: Model changed!");
    }
}
