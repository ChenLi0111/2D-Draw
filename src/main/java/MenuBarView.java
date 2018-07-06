import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Observer;
import java.util.Observable;

public class MenuBarView extends JPanel implements Observer {
    private Model model;

    // create a menu
    private JMenu menu_file = new JMenu("File");
    private JMenuBar menubar_file = new JMenuBar();

    private void add_menu_file () {
        for (String s: new String[] {"Save", "Load", "New", "Exit" }) {
            // add this menu item to the menu
            JMenuItem mi = new JMenuItem(s);
            // set the listener when events occur
            mi.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JMenuItem mi = (JMenuItem)e.getSource();
                    if (mi.getText() == "Exit") {
                        if (model.get_shape_collection().size() > 0) {
                            if (model.get_has_saved() != true) {
                                call_save();
                            }
                        }
                        System.exit(0);
                    } else if (mi.getText() == "New") {
                        if (model.get_has_saved() != true) {
                            call_save();
                        }
                        call_new();
                    } else if (mi.getText() == "Load") {
                        if (model.get_shape_collection().size() > 0) {
                            if (model.get_has_saved() != true) {
                                call_save();
                            }
                        }
                        call_load();
                    } else if (mi.getText() == "Save") {
                        call_save();
                    }
                }
            });
            // add this menu item to the menu
            menu_file.add(mi);
        }
        menubar_file.add(menu_file);
    }

    //Create a new View.
    MenuBarView(Model model) {
        // Hook up this observer so that it will be notified when the model changes.
        this.model = model;
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add_menu_file ();

        this.add(menubar_file);
    }

    private void call_save() {
        JFileChooser file_chooser = new JFileChooser();
        int save_or_not = file_chooser.showSaveDialog(null);

        if (save_or_not == 1) { // cancel
            return;
        } else {
            File file = file_chooser.getSelectedFile();
            try{
                FileOutputStream fos = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                //System.out.println("model " + Integer.toString(model.get_shape_collection().size()));
                model.save_status();
                oos.writeObject(model);
                //System.out.println("saved!");
                oos.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        model.set_has_saved(true);
    }

    private void call_load() {
        JFileChooser file_chooser = new JFileChooser();

        FileNameExtensionFilter file_ext_filter = new FileNameExtensionFilter("ser file", "ser");
        file_chooser.setFileFilter(file_ext_filter);

        int load_or_not = file_chooser.showOpenDialog(null);
        if (load_or_not == 1) { // cancel
            return;
        } else { // load
            File file = file_chooser.getSelectedFile();

            try {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                Model temp = (Model) ois.readObject();
                //System.out.println("temp " + Integer.toString(temp.get_shape_collection().size()));
                model.change_color(temp.get_color());
                model.change_thickness(temp.get_thickness());
                //System.out.println("model 1 " + Integer.toString(model.get_shape_collection().size()));
                model.clear_collection();
                //System.out.println("model 2 " + Integer.toString(model.get_shape_collection().size()));

                for (Shape s: temp.get_shape_collection()) {
                    if (s != null) {
                        model.add_shape(s);
                    }
                }
                //System.out.println("model 3 " + Integer.toString(model.get_shape_collection().size()));
                ois.close();
                fis.close();
            } catch (IOException e_1) {
                e_1.printStackTrace();
            } catch (ClassNotFoundException e_2) {
                e_2.printStackTrace();
                return;
            }
        }
    }

    private void call_new() {
        model.clear_collection();
        model.change_thickness(3.0f);
        model.change_color(Color.red);
    }

    //Update with data from the model.
    @Override
    public void update(Observable arg0, Object arg1) {
        //System.out.println("MenuBarView: updateView");
    }
}
