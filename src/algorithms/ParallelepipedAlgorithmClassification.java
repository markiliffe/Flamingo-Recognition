package algorithms;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * This class takes an image, class file and signatures file to discriminate on the pixels
 * writing a new image based on how each pixel is discriminated.
 * 
 * @author mark
 *
 */

@SuppressWarnings("serial")
public class ParallelepipedAlgorithmClassification extends JFrame{

	BufferedReader bufferedReader;

	private Vector<String> filePathPhotoVector;
	private String filePathClasses;
	private String filePathSignatures;

	TreeMap<Integer,Color> classMap = new TreeMap<Integer, Color>();
	TreeMap<Integer,int[]> lowerMap = new TreeMap<Integer, int[]>();
	TreeMap<Integer,int[]> upperMap = new TreeMap<Integer, int[]>();

	public int counter;

	/**
	 * The application entry point. We must pass three parameters: the original
	 * image file name, the name of the file with the description of the classes,
	 * and the name of the file with the signatures for the classes.
	 * 
	 * @throws IOException 
	 */

	public ParallelepipedAlgorithmClassification(Vector<String> filePathPhotoVector,String filePathClasses, String filePathSignatures) throws IOException{
		this.filePathPhotoVector = filePathPhotoVector;
		this.filePathClasses = filePathClasses;
		this.filePathSignatures = filePathSignatures;
		if(filePathPhotoVector != null){
			System.out.println("Size is:" + filePathPhotoVector.size());
			classify();
		} else {
			System.out.println("It's null");
			System.exit(1);
		}
	}

	/**
	 * Catch if any files are missing, if not launch the run method.
	 * 
	 * @return
	 * @throws IOException
	 */

	public void classify() throws IOException {
		if (filePathPhotoVector == null  || filePathClasses == null || filePathSignatures == null){
			if (filePathPhotoVector.size() == 0) 	{
				JOptionPane.showMessageDialog(new JFrame(), "Images loaded, please load photo directory", "Information Message", JOptionPane.INFORMATION_MESSAGE);
			} else if(filePathClasses == null){
				JOptionPane.showMessageDialog(new JFrame(), "Please Select a Classes File");
			} else if(filePathSignatures == null){
				JOptionPane.showMessageDialog(new JFrame(), "Please Select a Signatures File");
			}
		} else {
			JOptionPane.showMessageDialog(new JFrame(), "Able to Analyse Photos");
			for(int i=0;i<filePathPhotoVector.size();i++){
				System.out.println("The File Being Analysed is: " + filePathPhotoVector.elementAt(i));
				run(filePathPhotoVector.elementAt(i));
			}
		}
	}

	/**
	 * 
	 * @param filePath
	 * @throws IOException
	 */
	public void run(String filePath) throws IOException{
		//This counter is used to name the outputted files, as a key.
		counter++;
		System.out.println("Starting Classification");
		BufferedImage input = ImageIO.read(new File(filePath));

		bufferedReader = new BufferedReader(new FileReader(new File(filePathClasses)));
		System.out.println("Read Classes File");

		//Use a StringTokenizer to read the class file
		while(true)	{
			String line = bufferedReader.readLine(); 
			if (line == null) break;
			if (line.startsWith("#")) continue;
			StringTokenizer stringTokenizer = new StringTokenizer(line);
			if (stringTokenizer.countTokens() < 4) continue;
			int classId = Integer.parseInt(stringTokenizer.nextToken());
			int red = Integer.parseInt(stringTokenizer.nextToken());
			int green = Integer.parseInt(stringTokenizer.nextToken());
			int blue = Integer.parseInt(stringTokenizer.nextToken());
			classMap.put(classId,new Color(red, green ,blue));
		}
		bufferedReader.close();

		//Read signatures file
		bufferedReader = new BufferedReader(new FileReader(new File(filePathSignatures)));

		//StringTokenizer to read the signatures file into a map, from the filePathSignatures file.
		while(true)	{
			String line = bufferedReader.readLine(); 
			if (line == null) break;
			if (line.startsWith("#")) continue;
			StringTokenizer stringTokenizer = new StringTokenizer(line);
			if (stringTokenizer.countTokens() < 7) continue;
			int classId = Integer.parseInt(stringTokenizer.nextToken());
			int[] lower = new int[3]; int[] upper = new int[3];
			lower[0] = Integer.parseInt(stringTokenizer.nextToken());
			lower[1] = Integer.parseInt(stringTokenizer.nextToken());
			lower[2] = Integer.parseInt(stringTokenizer.nextToken());
			upper[0] = Integer.parseInt(stringTokenizer.nextToken());
			upper[1] = Integer.parseInt(stringTokenizer.nextToken());
			upper[2] = Integer.parseInt(stringTokenizer.nextToken());
			lowerMap.put(classId,lower);
			upperMap.put(classId,upper);
		}
		bufferedReader.close();
		//Create a color image to hold the results of the classification.
		BufferedImage results = new BufferedImage(input.getWidth(),input.getHeight(),BufferedImage.TYPE_INT_RGB);

		//Discriminate!
		for(int row=0;row<input.getHeight();row++) {
			for(int col=0;col<input.getWidth();col++)	{
				int rgb = input.getRGB(col,row);
				int red = (int)((rgb&0x00FF0000)>>>16);
				int green = (int)((rgb&0x0000FF00)>>>8); 
				int blue = (int) (rgb&0x000000FF);      
				//Create a new colour to be soon associated with the discriminated pixel
				Color assignedClass = new Color(0,0,0);
				for(int key:lowerMap.keySet()) {
					if (isBetween(red, green, blue, lowerMap.get(key), upperMap.get(key))) {
						assignedClass = classMap.get(key);            
					}
				}
				//Once discriminant function has ended, write the resulting pixel to the classified image
				results.setRGB(col,row,assignedClass.getRGB());
			}
			//This is here to provide a feedback mechanism, other wise it's hard to tell if the computer has crashed.
			System.out.println("Now at Row: " + row + " of " + input.getWidth());
			// Write image
			ImageIO.write(results,"PNG",new File("classified_" + counter + ".png"));
		}
	}

	/**
	 * The discriminate function; takes an RGB value and the upper and lower thresholds and decides if the 
	 * pixel is within these bounds, 
	 * 
	 * @param red
	 * @param green
	 * @param blue
	 * @param min
	 * @param max
	 * @return
	 */

	private static boolean isBetween(int red,int green,int blue,int[] lower,int[] upper) {
		return ((lower[0] <= red) && (red <= upper[0]) && (lower[1] <= green) && (green <= upper[1]) &&	(lower[2] <= blue) && (blue <= upper[2]));
	}
}