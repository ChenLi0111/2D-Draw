import javax.swing.*;
import java.awt.*;

public class Main {

	public static void main(String[] args){	
		JFrame frame = new JFrame("2D Draw");
		frame.setBackground(Color.BLACK);
		// create Model and initialize it
		Model model = new Model();

		// create DrawView, tell it about model (and controller)
		DrawView drawview = new DrawView(model);
		drawview.setBorder(BorderFactory.createEtchedBorder());
		// tell Model about DrawView.
		model.addObserver(drawview);
		
		// create ColorView, tell it about model (and controller)
		ColorLineView colorlineview = new ColorLineView(model);
		colorlineview.setPreferredSize(new Dimension(150, 300));
		colorlineview.setBorder(BorderFactory.createEtchedBorder());
		// tell Model about ColorLineView.
		model.addObserver(colorlineview);

		// create PlayBackView, tell it about model (and controller)
		PlayBackView playbackview = new PlayBackView(model);
		playbackview.setBorder(BorderFactory.createEtchedBorder());
		// tell Model about PlayBackView.
		model.addObserver(playbackview);

		// create TopBarView, tell it about model (and controller)
		MenuBarView menubarview = new MenuBarView(model);
		menubarview.setBorder(BorderFactory.createEtchedBorder());
		// tell Model about MenuBarView.
		model.addObserver(menubarview);

		//ScrollbarsView scrollbarsview = new ScrollbarsView(drawview);
		//frame.add(scrollbarsview);
		
		// let all the views know that they're connected to the model
		model.notifyObservers();
		
		frame.getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.BOTH;

		gc.gridwidth = 2; // 1 grid cell wide
		gc.gridheight = 1; // 3 grid cells tall
		gc.gridx = 0;
		gc.gridy = 0;
		gc.weighty = 0; // the proportion of space to give this row
		frame.getContentPane().add(menubarview, gc);

		gc.gridwidth = 1; // 1 grid cell wide
		gc.gridheight = 1; // 3 grid cells tall
		gc.gridx = 0;
		gc.gridy = 1;
		gc.weightx = 0.1; // the proportion of space to give this column
		gc.weighty = 0.1; // the proportion of space to give this row
		frame.getContentPane().add(colorlineview, gc);

		gc.gridwidth = 1; // 1 grid cell wide
		gc.gridheight = 1; // 3 grid cells tall
		gc.gridx = 1;
		gc.gridy = 1;
		gc.weightx = 0.8; // the proportion of space to give this column
		gc.weighty = 0.6; // the proportion of space to give this row
		frame.getContentPane().add(drawview, gc);

		gc.gridwidth = 2; // 1 grid cell wide
		gc.gridheight = 1; // 3 grid cells tall
		gc.gridx = 0;
		gc.gridy = 2;
		gc.weighty = 0; // the proportion of space to give this row
		frame.getContentPane().add(playbackview, gc);

		// setup window
		frame.setPreferredSize(new Dimension(800,600));
		frame.setMinimumSize(new Dimension(440,330));
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	} 
}
