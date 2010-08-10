package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import javax.swing.*;

import output.*;

@SuppressWarnings("serial")
public class Main_GUI extends JFrame{

	public JMenuBar bar;
	public JMenu menu;
	public JRadioButtonMenuItem classify, raster, sift, surf, kMeans;
	//public JFrame panec = new JFrame();
	public LoadImages images;
	public final JFileChooser fileChooser = new JFileChooser();
	public final JFileChooser csvFileChooser = new JFileChooser();

	public final JMenuItem openImageFolder = new JMenuItem();
	public final JMenuItem openCSVFile = new JMenuItem();
	public final JMenuItem quit = new JMenuItem();
	public final ReadCSV readCSV = new ReadCSV();

	File file = new File("null");

	String folderPath;
	String csvPath;

	boolean isClassified = false;

	enum algorithms {raster, sift, surf, kMeans};

	class MenuActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			//Deal open a folder selector.
			if (e.getSource() == openImageFolder) {
				//				//FIX HERE TO ADD FOLDERS TO THE MANIFEST
				//				int returnVal = fileChooser.showOpenDialog(Main_GUI.this);
				//				if (returnVal == JFileChooser.APPROVE_OPTION) {
				//					File file = fileChooser.getSelectedFile();
				//					System.out.println(file.getPath());	
				//				} else {
				//					System.out.println("Open command cancelled by user.");
				//				}
			} else if(e.getSource() == openCSVFile) {
				int returnVal = csvFileChooser.showOpenDialog(Main_GUI.this);
				File file = csvFileChooser.getSelectedFile();
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					if (file.getPath() == null) {
						JOptionPane.showMessageDialog(new JFrame(), "Please Select a File");
					} else {
						System.out.println(file.getPath());
						readCSV.read(file.getPath());
					}
				} else {
					System.out.println("Open command cancelled by user.");
				} 
			} else if (e.getSource() == classify){
				//Launch the classification algorithm
				System.out.println("Classification Here");
			} else if (e.getSource() == raster){
				System.out.println("Raster Here");
			} else if (e.getSource() == quit) {
				System.out.println("System Exiting");
				System.exit(0);
			}
		}
	}



	public Main_GUI(){
		System.out.println("Main_GUI Started");
	}



	public void main_menu_bar() {
		menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_O);

		openImageFolder.setText("Open Folder");
		//fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		openImageFolder.addActionListener(new MenuActionListener());
		menu.add(openImageFolder);

		openCSVFile.setText("Read CSV");
		//csvFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		openCSVFile.addActionListener(new MenuActionListener());
		menu.add(openCSVFile);


		menu.addSeparator();
		quit.setText("Quit");
		quit.addActionListener(new MenuActionListener());
		menu.add(quit);


	}

	public void algorithms_menu_bar() {
		menu = new JMenu("Algorithms");
		menu.setMnemonic(KeyEvent.VK_O);

		ButtonGroup group = new ButtonGroup();

		classify = new JRadioButtonMenuItem();
		classify.setText("Classify");
		classify.addActionListener(new MenuActionListener());
		group.add(classify);
		menu.add(classify);

		menu.addSeparator();

		raster = new JRadioButtonMenuItem();
		raster.setText("Raster Analysis");
		raster.addActionListener(new MenuActionListener());
		group.add(raster);
		menu.add(raster);

		//	sift = new JRadioButtonMenuItem("SIFT");
		//	group.add(sift);
		//	menu.add(sift);
		//
		//	surf = new JRadioButtonMenuItem("SURF");
		//	group.add(surf);
		//	menu.add(surf);
		//
		//	kMeans = new JRadioButtonMenuItem("K-Means");
		//	group.add(kMeans);
		//	menu.add(kMeans);
	}

	public void help_menu_bar() {
		menu = new JMenu("Help");
		menu.setMnemonic(KeyEvent.VK_O);
		menu.add(new JMenuItem("Flamingos"));
		menu.add(new JMenuItem("About"));		
	}

	public void run(){
		JFrame f = new JFrame("Flamingo Census Program");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		bar = new JMenuBar();
		main_menu_bar();
		bar.add(menu);

		algorithms_menu_bar();
		bar.add(menu);

		help_menu_bar();
		bar.add(menu);

		f.setJMenuBar(bar);
		f.setSize(900, 600);

		f.setVisible(true);

	}
}
