package gui;

import java.awt.event.KeyEvent;
import javax.swing.*;

@SuppressWarnings("serial")
public class Main_GUI extends JFrame{

	public JMenuBar bar;
	public JMenu menu;
	public JMenuItem menuItem;
	public JFrame pane;
	public LoadImages images;

	public Main_GUI(){

		JFrame f = new JFrame("Flamingo Census Program");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        options_bar();
		images = new LoadImages();
		
		bar = new JMenuBar();
		main_menu_bar();
		bar.add(menu);

		algorithms_menu_bar();
		bar.add(menu);
		
		help_menu_bar();
		bar.add(menu);
		
		f.setJMenuBar(bar);
		f.setSize(889, 600);

        f.add(new LoadImages());
        images.get_RGB();
        f.setVisible(true);
        pane.setVisible(true);
	}
	
	public void options_bar(){
		pane = new JFrame();
		pane.setSize(250, 600);
		pane.setVisible(true);
		pane.setTitle("Flamingo Options");
		
	}
	
	public void main_menu_bar() {
		menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_O);

		menu.add(new JMenuItem("Open File"));
		menu.add(new JMenuItem("Open Folder"));
		menu.addSeparator();
		menu.add(new JMenuItem("-"));
		menu.addSeparator();
		menu.add(new JMenuItem("Quit"));
	}

	public void algorithms_menu_bar() {
		menu = new JMenu("Algorithms");
		menu.setMnemonic(KeyEvent.VK_O);

		ButtonGroup group = new ButtonGroup();

		JRadioButtonMenuItem menuItem = new JRadioButtonMenuItem("Raster Analysis");
		group.add(menuItem);
		menu.add(menuItem);

		menuItem = new JRadioButtonMenuItem("SIFT");
		group.add(menuItem);
		menu.add(menuItem);

		menuItem = new JRadioButtonMenuItem("SURF");
		group.add(menuItem);
		menu.add(menuItem);
		
		menuItem = new JRadioButtonMenuItem("K-Means");
		group.add(menuItem);
		menu.add(menuItem);
	}
	
	public void help_menu_bar() {
		menu = new JMenu("Help");
		menu.setMnemonic(KeyEvent.VK_O);
		menu.add(new JMenuItem("Flamingos"));
		menu.add(new JMenuItem("About"));		
	}
	
}
