import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observer;
import java.util.Observable;
import javax.swing.JButton;

public class ColorLineView extends JPanel implements Observer {

    private Model model;

    private JButton red_button = new JButton("");
    private JButton blue_button = new JButton("");
    private JButton green_button = new JButton("");
    private JButton orange_button = new JButton("");
    private JButton yellow_button = new JButton("");
    private JButton pink_button = new JButton("");
    private JButton cyan_button = new JButton("");
    private JButton custom_button = new JButton("Custom");

    private JButton thick_3 = new JButton();
    private JButton thick_5 = new JButton();
    private JButton thick_7 = new JButton();
    private JButton thick_9 = new JButton();
    private JButton thick_11 = new JButton();
    private JButton thick_13 = new JButton();

    private ImageIcon temp_3 = new ImageIcon("images/3.png");
    private ImageIcon temp_5 = new ImageIcon("images/5.png");
    private ImageIcon temp_7 = new ImageIcon("images/7.png");
    private ImageIcon temp_9 = new ImageIcon("images/9.png");
    private ImageIcon temp_11 = new ImageIcon("images/11.png");
    private ImageIcon temp_13 = new ImageIcon("images/13.png");

    private JLabel current_color = new JLabel("Color");
    private JLabel now_color = new JLabel("");
    private JLabel current_thickness = new JLabel("Thickness");
    private JLabel now_thickness = new JLabel(new ImageIcon("images/3.png"), JLabel.CENTER);

    private void button_color() {
        red_button.setIcon(new ImageIcon("images/red.png"));
        blue_button.setIcon(new ImageIcon("images/blue.png"));
        green_button.setIcon(new ImageIcon("images/green.png"));
        orange_button.setIcon(new ImageIcon("images/orange.png"));
        yellow_button.setIcon(new ImageIcon("images/yellow.png"));
        pink_button.setIcon(new ImageIcon("images/pink.png"));
        cyan_button.setIcon(new ImageIcon("images/cyan.png"));
    }

    private void button_thick() {
        thick_3.setIcon(temp_3);
        thick_5.setIcon(temp_5);
        thick_7.setIcon(temp_7);
        thick_9.setIcon(temp_9);
        thick_11.setIcon(temp_11);
        thick_13.setIcon(temp_13);
    }

    private void config_color_button() {
        //System.out.println("config color");
        red_button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                model.change_color(Color.red);
                //System.out.println("changing red");
            }
        });

        blue_button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                model.change_color(Color.blue);
                //System.out.println("changing blue");
            }
        });

        green_button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                model.change_color(Color.green);
                //System.out.println("changing green");
            }
        });

        orange_button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                model.change_color(Color.orange);
                //System.out.println("changing orange");
            }
        });

        yellow_button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                model.change_color(Color.yellow);
                //System.out.println("changing yellow");
            }
        });

        pink_button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                model.change_color(Color.pink);
                //System.out.println("changing pink");
            }
        });

        cyan_button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                model.change_color(Color.cyan);
                //System.out.println("changing cyan");
            }
        });

        custom_button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Color c = null;
                c = JColorChooser.showDialog(null, "Please select", c);
                if (c == null) {
                    c = model.get_color();
                }
                model.change_color(c);
                //System.out.println("changing custom");
            }
        });

        now_color.setBackground(Color.red);
        now_color.setOpaque(true);
    }

    private void config_thickness_button() {
        //System.out.println("config thickness");
        thick_3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                model.change_thickness(3.0f);
                //System.out.println("changing 3");
            }
        });

        thick_5.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                model.change_thickness(5.0f);
                //System.out.println("changing 5");
            }
        });

        thick_7.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                model.change_thickness(7.0f);
                //System.out.println("changing 7");
            }
        });

        thick_9.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                model.change_thickness(9.0f);
                //System.out.println("changing 9");
            }
        });

        thick_11.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                model.change_thickness(11.0f);
                //System.out.println("changing 11");
            }
        });

        thick_13.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                model.change_thickness(13.0f);
                //System.out.println("changing 13");
            }
        });
    }

    //Create a new View.
    ColorLineView(Model model) {
        // Hook up this observer so that it will be notified when the model changes.
        this.model = model;

        // use BoxLayout
        this.setLayout(new GridLayout(9,2));

        config_color_button();
        config_thickness_button();

        // Add the components
        button_color();
        this.add(red_button);
        this.add(blue_button);
        this.add(green_button);
        this.add(orange_button);
        this.add(yellow_button);
        this.add(pink_button);
        this.add(cyan_button);
        this.add(custom_button);
        button_thick();
        this.add(thick_3);
        this.add(thick_5);
        this.add(thick_7);
        this.add(thick_9);
        this.add(thick_11);
        this.add(thick_13);
        this.add(current_color);
        this.add(now_color);
        this.add(current_thickness);
        this.add(now_thickness);
    }

    //Update with data from the model.i
    @Override
    public void update(Observable arg0, Object arg1) {
        //System.out.println("ColorLineView: Model changed!");

        //update selected color
        now_color.setBackground(model.get_color());

        //update selected thickness
        if (model.get_thickness() == 3.0f) {
            now_thickness.setIcon(temp_3);
        } else if (model.get_thickness() == 5.0f) {
            now_thickness.setIcon(temp_5);
        } else if (model.get_thickness() == 7.0f) {
            now_thickness.setIcon(temp_7);
        } else if (model.get_thickness() == 9.0f) {
            now_thickness.setIcon(temp_9);
        } else if (model.get_thickness() == 11.0f) {
            now_thickness.setIcon(temp_11);
        } else if (model.get_thickness() == 13.0f) {
            now_thickness.setIcon(temp_13);
        }
    }
}
