import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.Observable;
import java.util.ArrayList;

public class Model extends Observable implements Serializable {
    private ArrayList<Shape> shape_collection = new ArrayList<Shape>();
    private Color select_color = Color.red;
    private float select_thickness = 3.0f;

    private int slider_pre = 0;
    private ArrayList<Shape> print_shape_collection = new ArrayList<Shape>();

    private boolean need_change_slider = false;
    private boolean with_timer = false;

    private boolean play = false;
    private boolean play_back = false;

    private boolean has_saved = false;

    public void set_has_saved(boolean b) {
        has_saved = b;
    }

    public boolean get_has_saved() {
        return has_saved;
    }

    private javax.swing.Timer timer = new javax.swing.Timer(50, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (slider_pre < 100 && play && !play_back) {
                //System.out.println(Integer.toString(slider_pre));
                slider_pre = slider_pre + 1;
                set_play_prentage(slider_pre);
                update_collection_now();
                need_change_slider = true;
                //System.out.println(Integer.toString(slider_pre));
            }
            if (slider_pre == 100 && !play_back) {
                play = false;
                timer.stop();
            }

            if (play_back) {
                //System.out.println(Integer.toString(slider_pre));
                slider_pre = slider_pre - 1;
                set_play_prentage(slider_pre);
                update_collection_now();
                need_change_slider = true;
                //System.out.println(Integer.toString(slider_pre));
            }
            if (slider_pre == 0 && play_back) {
                play_back = false;
                timer.stop();
            }
        }
    });

    public boolean get_with_timer() {
        return with_timer;
    }

    public void set_play() {
        if (shape_collection.size() == 0) { return; }
        play = true;
        with_timer = true;
        timer.start();
    }

    public void set_end() {
        if (shape_collection.size() == 0) { return; }
        set_play_prentage(100);
        need_change_slider = true;
        play_back = false;
        setChanged();
        notifyObservers();
    }

    public void set_start() {
        if (shape_collection.size() == 0) {return;}
        slider_pre = 0;
        set_play_prentage(slider_pre);
        need_change_slider = true;
        play = true;
        with_timer = true;
        timer.start();
    }

    public void set_play_back() {
        set_end();
        if (shape_collection.size() == 0) {return;}
        play_back = true;
        with_timer = true;
        need_change_slider = true;
        timer.start();
    }

    Model() {
        setChanged();
    }

    public void set_play_prentage(int i) {
        slider_pre = i;
        update_collection_now();
        has_saved = false;
    }

    public void update_collection_now() {
        update_collection();
        setChanged();
        notifyObservers();
    }

    public void save_status() {
        if (slider_pre != 100) {
            shape_collection.clear();
            shape_collection.addAll(print_shape_collection);
        }
        slider_pre = 100;
        print_shape_collection.clear();
        need_change_slider = false;
        with_timer = false;
        play = false;
        has_saved = false;
        play_back = false;
    }

    public int get_slider_pre() {
        return slider_pre;
    }

    public void update_collection() {
        if (slider_pre == 100) { return; }

        int point_count = 0;
        for (Shape ss: shape_collection) {
            if (ss.get_points() != null) {
                point_count += ss.get_points().size();
            }
        }

        int need_print = (int) (point_count * slider_pre / 100);
        //System.out.println("total: " + Integer.toString(point_count));
        //System.out.println("need to print: " + Integer.toString(need_print));
        int keep_track = 0;
        int i;
        for (i = 0; keep_track <= need_print; ++i) {
            if (shape_collection.get(i).get_points() != null) {
                keep_track += shape_collection.get(i).get_points().size();
                //System.out.println("add : " + Integer.toString(shape_collection.get(i).get_points().size()));
            }
        }
        if (i == 0) { return; }
        keep_track -= shape_collection.get(i - 1).get_points().size();
        i--;

        //System.out.println("total shapes: " + Integer.toString(shape_collection.size()));
        print_shape_collection.clear();
        for(int t = 0; t < i; ++t) {
            print_shape_collection.add(shape_collection.get(t));
        }
        //System.out.println("need to print shapes : " + Integer.toString(print_shape_collection.size()));
        Shape temp = new Shape();
        temp.setColour(shape_collection.get(i).getColour());
        temp.set_thickness(shape_collection.get(i).getStrokeThickness());
        temp.setScale(1.0f);
        for(int a = 0; a <= need_print - keep_track; ++a) {
            temp.addPoint(shape_collection.get(i).get_points().get(a));
        }
        print_shape_collection.add(temp);
    }

    public boolean get_need_change_slider() {
        return need_change_slider;
    }

    public void set_need_change_slider(boolean b) {
        need_change_slider = b;
    }

    public void add_shape(Shape s) {
        if (slider_pre != 100) {
            shape_collection.clear();
            for (Shape ss : print_shape_collection) {
                shape_collection.add(ss);
            }
        }
        slider_pre = 100;
        shape_collection.add(s);
        need_change_slider = true;
        has_saved = false;
        setChanged();
        notifyObservers();
    }
    
    public void clear_collection() {
        shape_collection.clear();
        print_shape_collection.clear();
        setChanged();
        notifyObservers();
    }

    public ArrayList<Shape> get_shape_collection() {
        return shape_collection;
    }

    public ArrayList<Shape> get_print_shape_collection() {
        return print_shape_collection;
    }

    public void change_color(Color c) {
        select_color = c;
        //System.out.println("changed" + c.toString());
        setChanged();
        notifyObservers();
    }

    public Color get_color() {
        return select_color;
    }

    public void change_thickness(float f) {
        select_thickness = f;
        //System.out.println("changed " + Float.toString(f));
        setChanged();
        notifyObservers();
    }

    public float get_thickness() {
        return select_thickness;
    }
}
