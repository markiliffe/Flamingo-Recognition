package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;

import core.*;
import algorithms.*;

import output.ReadCSV;
import output.ReadCSVCamera;
import output.ReadCSVNonPhone;


/**
 * This 
 * 
 * @author mark
 */

@SuppressWarnings("serial")
public class Main_GUI extends JFrame{

	public JMenuBar bar;
	public JMenu menu;
	public JRadioButtonMenuItem classify, raster, countFlamingos, sift, surf, kMeans;
	public final JFileChooser fileChooser = new JFileChooser();
	public final JFileChooser folderChooser = new JFileChooser();
	public final JFileChooser csvFileChooser = new JFileChooser();

	public final JMenuItem openImageFolder = new JMenuItem();
	public final JMenuItem openImageFile = new JMenuItem();
	public final JMenuItem openCSVFile = new JMenuItem();
	public final JMenuItem openCSVFileNonPhone = new JMenuItem();
	public final JMenuItem openClassesFile = new JMenuItem();
	public final JMenuItem openSamplesFile = new JMenuItem();
	public final JMenuItem openSignaturesFile = new JMenuItem();
	public final JMenuItem printVectors = new JMenuItem();
	public final JMenuItem quit = new JMenuItem();

	public final ReadCSV readCSV = new ReadCSV();
	public final ReadCSVCamera readCSVCamera = new ReadCSVCamera();
	public final ReadCSVNonPhone readCSVNonPhone = new ReadCSVNonPhone();

	public String filePathSignatures; 
	public String filePathSamples; 
	public String filePathClasses;
	public String filePathPhoto;
	public Vector<String> filePathPhotoVector = new Vector<String>();

	File file = new File("null");

	StringTokenizer tempStringTokenizer;

	public String folderPath;
	public String csvPath;

	boolean isClassified = false;

	enum algorithms {raster, sift, surf, kMeans};

	/**
	 * This inner class deals with user clicks 
	 * 
	 * 
	 * @author mark
	 *
	 */
	class MenuActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			//Deal open a folder selector.
			if (event.getSource() == openImageFolder) {
				int returnVal = folderChooser.showOpenDialog(Main_GUI.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = folderChooser.getSelectedFile();
					File[] fileName = file.listFiles();
					for(int i=0;i<fileName.length;i++){
						try {
							filePathPhotoVector.add(fileName[i].getPath());
							getIndexFromString(fileName[i].getName(), fileName[i].getPath());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					JOptionPane.showMessageDialog(new JFrame(), "Files Loaded");
				} else {
					System.out.println("Open command cancelled by user.");
				}
			} else if(event.getSource() == openImageFile) {
				int returnVal = fileChooser.showOpenDialog(Main_GUI.this);
				File file = fileChooser.getSelectedFile();
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					if (file.getPath() == null) {
						JOptionPane.showMessageDialog(new JFrame(), "An Image To Create Signatures");
					} else {
						filePathPhoto = file.getPath();
						filePathPhotoVector.add(filePathPhoto);
						System.out.println("Image File: " + file.getPath());
					}
				} else {
					System.out.println("Open command cancelled by user.");
				} 
			} else if(event.getSource() == openCSVFileNonPhone) {
				int returnVal = csvFileChooser.showOpenDialog(Main_GUI.this);
				File file = csvFileChooser.getSelectedFile();
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					if (file.getPath() == null) {
						JOptionPane.showMessageDialog(new JFrame(), "Please Select a non Phone Produced CSV File");
					} else {
						System.out.println(file.getPath());
						readCSVCamera.read(file.getPath());
					}
				} else {
					System.out.println("Open command cancelled by user.");
				} 
			} else if(event.getSource() == openCSVFile) {
				int returnVal = csvFileChooser.showOpenDialog(Main_GUI.this);
				File file = csvFileChooser.getSelectedFile();
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					if (file.getPath() == null) {
						JOptionPane.showMessageDialog(new JFrame(), "Please Select a CSV File");
					} else {
						System.out.println(file.getPath());
						readCSV.read(file.getPath());
					}
				} else {
					System.out.println("Open command cancelled by user.");
				} 
			} else if(event.getSource() == openClassesFile) {
				int returnVal = csvFileChooser.showOpenDialog(Main_GUI.this);
				File file = csvFileChooser.getSelectedFile();
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					filePathClasses = file.getPath();
					System.out.println("The file path is now..." + filePathClasses);
					JOptionPane.showMessageDialog(new JFrame(), "Classes File: " + file.getName() + " Parsed");

				} else {
					System.out.println("Open command cancelled by user.");
				} 
			} else if(event.getSource() == openSamplesFile) {
				int returnVal = fileChooser.showOpenDialog(Main_GUI.this);
				File file = fileChooser.getSelectedFile();
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					filePathSamples = file.getPath();
					System.out.println(filePathSamples);
				} else {
					System.out.println("Open command cancelled by user.");
				} 
			} else if(event.getSource() == openSignaturesFile) {
				int returnVal = csvFileChooser.showOpenDialog(Main_GUI.this);
				File file = csvFileChooser.getSelectedFile();
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					filePathSignatures = file.getPath();
					System.out.println(filePathSignatures);
				} else {
					System.out.println("Open command cancelled by user.");
				} 
			} else if(event.getSource() == printVectors) {
				Vector<ReportInstanceNonPhone> tempCameraVector =  Main.getReportInstanceNonPhoneVector();
				if(tempCameraVector.size() != 0){
					for(int i =0;i<tempCameraVector.size();i++){
						System.out.println("Reading Vector");
						System.out.print(tempCameraVector.elementAt(i).getTime() + " ");
						System.out.print(tempCameraVector.elementAt(i).getLatitude() + " ");
						System.out.print(tempCameraVector.elementAt(i).getLatitude() + " ");
					}
				} else {
					System.out.println("Nothing in Vector");
				}
			} else if (event.getSource() == classify){
				try {
					System.out.println("Classification Here");
					ParallelepipedSignature pSignatureAlgo = new ParallelepipedSignature(filePathPhoto, filePathClasses, filePathSamples);
					boolean runRight = pSignatureAlgo.run();
					if(runRight){
						JOptionPane.showMessageDialog(new JFrame(), "Signature File Created");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else if (event.getSource() == raster){
				System.out.println("Raster Here");
				try {
					ParallelepipedAlgorithmClassification pAlgo = new ParallelepipedAlgorithmClassification(filePathPhotoVector ,filePathClasses, filePathSignatures);
					pAlgo.classify();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (event.getSource() == countFlamingos){
				System.out.println("Count Flamingos");
				Census_Flamingos cAlgo = new Census_Flamingos();
				try {
					cAlgo.run(filePathPhoto);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (event.getSource() == quit) {
				System.out.println("System Exiting");
				System.exit(0);
			}
		}
	}

	/**
	 * Constructor, informs the user that the gui has launched.
	 * 
	 */

	public Main_GUI(){
		System.out.println("Main_GUI Started");
	}

	/**
	 * Creates the main menu bar and populates it with the open, save and exit buttons
	 *  
	 */

	public void main_menu_bar() {
		menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_O);

		openImageFolder.setText("Open Folder");
		folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		openImageFolder.addActionListener(new MenuActionListener());
		menu.add(openImageFolder);

		openImageFile.setText("Open Image");
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		openImageFile.addActionListener(new MenuActionListener());
		menu.add(openImageFile);

		menu.addSeparator();

		openClassesFile.setText("Open Classes File");
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		openClassesFile.addActionListener(new MenuActionListener());
		menu.add(openClassesFile);

		openSamplesFile.setText("Open Samples File");
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		openSamplesFile.addActionListener(new MenuActionListener());
		menu.add(openSamplesFile);

		openSignaturesFile.setText("Open Signatures File");
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		openSignaturesFile.addActionListener(new MenuActionListener());
		menu.add(openSignaturesFile);

		menu.addSeparator();


		openCSVFile.setText("Read CSV");
		csvFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		openCSVFile.addActionListener(new MenuActionListener());
		menu.add(openCSVFile);

		openCSVFileNonPhone.setText("Read Camera CSV");
		csvFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		openCSVFileNonPhone.addActionListener(new MenuActionListener());
		menu.add(openCSVFileNonPhone);

		menu.addSeparator();

		printVectors.setText("Print Vectors");
		printVectors.addActionListener(new MenuActionListener());
		menu.add(printVectors);


		menu.addSeparator();
		quit.setText("Quit");
		quit.addActionListener(new MenuActionListener());
		menu.add(quit);


	}

	/**
	 * Opens a menu bar that populates the menu with algorithmic options
	 * 
	 * 
	 */

	public void algorithms_menu_bar() {
		menu = new JMenu("Algorithms");
		menu.setMnemonic(KeyEvent.VK_O);

		ButtonGroup group = new ButtonGroup();

		classify = new JRadioButtonMenuItem();
		classify.setText("Parallelepiped Classification");
		classify.addActionListener(new MenuActionListener());
		group.add(classify);
		menu.add(classify);

		menu.addSeparator();

		raster = new JRadioButtonMenuItem();
		raster.setText("Parallelepiped Analysis");
		raster.addActionListener(new MenuActionListener());
		group.add(raster);
		menu.add(raster);

		menu.addSeparator();

		countFlamingos = new JRadioButtonMenuItem();
		countFlamingos.setText("Census Flamingos");
		countFlamingos.addActionListener(new MenuActionListener());
		group.add(countFlamingos);
		menu.add(countFlamingos);

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

	//	public void help_menu_bar() {
	//		menu = new JMenu("Help");
	//		menu.setMnemonic(KeyEvent.VK_O);
	//		menu.add(new JMenuItem("Flamingos"));
	//		menu.add(new JMenuItem("About"));		
	//	}

	/**
	 * This method takes the filenames and filepaths, taking the number assigned to it when processed against the GPS
	 * position storing it within the program's global hashmap by calling the enterIntoHashMap class...
	 * 
	 */

	public void getIndexFromString(String filename, String filepath) throws Exception{
		tempStringTokenizer = new StringTokenizer(filename, "_");
		String tempToken = tempStringTokenizer.nextToken();
		try{
			int index = Integer.parseInt(tempToken);
			enterIntoHashMap(index, filepath);
		}
		catch (Exception e) {
			e.getMessage();
		}
	}

	/**
	 * This class once having pulled the Vector of file paths from the selected folder, takes their title 
	 * and provides the methodology of indexing.
	 * 
	 */
	public void enterIntoHashMap(int index, String filePath){
		Main.addElementHashMap(index, filePath);
	}

	/**
	 * 
	 * 
	 */
	public void run(){
		JFrame f = new JFrame("Flamingo Census Program");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		bar = new JMenuBar();
		main_menu_bar();
		bar.add(menu);

		algorithms_menu_bar();
		bar.add(menu);

		//help_menu_bar();
		//bar.add(menu);

		f.setJMenuBar(bar);
		f.setSize(300, 42);
		f.setResizable(false);
		f.setVisible(true);
	}
}
