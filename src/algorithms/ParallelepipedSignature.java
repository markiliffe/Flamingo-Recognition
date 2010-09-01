package algorithms;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.imageio.ImageIO;

/**
 * This class creates the Parallelepiped signature bounds for the classification algorithm.
 * It requires am image, sample regions to derive the thresholds and the class file
 * to build a TreeMap data structure, with which the thresholds are placed in memory before
 * outputting 
 * @author mark
 *
 */
public class ParallelepipedSignature {

	private String imageFilePath;
	private String classesFilePath;
	private String samplesFilePath;

	private TreeMap<Integer,Color> classMap = new TreeMap<Integer, Color>();
	private TreeMap<Integer,int[]> lowerMap = new TreeMap<Integer, int[]>();
	private TreeMap<Integer,int[]>upperMap = new TreeMap<Integer, int[]>();

	private BufferedImage input;
	BufferedReader reader;
	private BufferedWriter writer;

	/**
	 * 
	 * @param imageFilePath
	 * @param classFilePath
	 * @param samplesFilePath
	 */
	public ParallelepipedSignature(String imageFilePath, String classFilePath, String samplesFilePath){
		this.imageFilePath = imageFilePath;
		this.classesFilePath = classFilePath;
		this.samplesFilePath = samplesFilePath;
	}

	public boolean run() throws IOException  {
		System.out.println("Have Read:" + imageFilePath + "\n" + classesFilePath +"\n"+ samplesFilePath +"\n");

		//Pass the filepaths of the photograph and the class file.
		input = ImageIO.read(new File(imageFilePath));
		reader = new BufferedReader(new FileReader(classesFilePath));

		//Use a string tokenizer to read the class file placing the values of the class into the provided tree map
		while(true)	{
			String line = reader.readLine(); 
			if (line == null) break;
			if (line.startsWith("#")) continue;
			StringTokenizer stringTokenizer = new StringTokenizer(line);
			if (stringTokenizer.countTokens() < 4) continue;
			int classId = Integer.parseInt(stringTokenizer.nextToken());
			int red = Integer.parseInt(stringTokenizer.nextToken());
			int green = Integer.parseInt(stringTokenizer.nextToken());
			int blue = Integer.parseInt(stringTokenizer.nextToken());
			classMap.put(classId,new Color(red, green, blue));
		}
		reader.close();

		//Thresholds for the size of the parallelepipeds.
		for(Integer classIndex:classMap.keySet()) {
			lowerMap.put(classIndex,new int[]{1000,1000,1000}); // large enough
			upperMap.put(classIndex,new int[]{-1,-1,-1}); // small enough
		}

		// Open the file with the coordinates and get the pixels' values for those coordinates.
		reader = new BufferedReader(new FileReader(samplesFilePath));

		while(true)	{
			String line = reader.readLine(); 
			if (line == null) break;
			if (line.startsWith("#")) continue;
			StringTokenizer st = new StringTokenizer(line);
			if (st.countTokens() < 5) continue;
			int classId = Integer.parseInt(st.nextToken());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			int width = Integer.parseInt(st.nextToken());
			int height = Integer.parseInt(st.nextToken());
			Color colour = classMap.get(classId);
			//Do regions exist?
			if (colour != null)	{
				// Get the region's bounding box.
				int[] lower = lowerMap.get(classId);
				int[] upper = upperMap.get(classId);
				for(int row=0;row<=height;row++) {
					//Bit Shift!
					for(int column=0;column<=width;column++) {
						int rgb = input.getRGB(x+column,y+row);
						int red = (int)((rgb&0x00FF0000)>>>16);
						int green = (int)((rgb&0x0000FF00)>>>8);
						int blue  = (int) (rgb&0x000000FF);
						lower[0] = Math.min(lower[0],red); upper[0] = Math.max(upper[0],red);
						lower[1] = Math.min(lower[1],green); upper[1] = Math.max(upper[1],green);
						lower[2] = Math.min(lower[2],blue); upper[2] = Math.max(upper[2],blue);
					} 
					//Place the bounds into the map, with the assoiciated class identification number.
					lowerMap.put(classId,lower);
					upperMap.put(classId,upper);
				}
			}
		}
		reader.close();

		//Write the signatures to a file, would be easier if the file could be named dynamically according to the photos produced.
		writer = new BufferedWriter(new FileWriter("flalowergo_signatures.txt"));

		//Iterate through the treemap, pulling the lower and upper thresholds and writing them to a file
		for(Integer classId:classMap.keySet()) {
			writer.write(classId + " ");
			int[] lower = lowerMap.get(classId);
			int[] upper = upperMap.get(classId);
			writer.write(lower[0] + " " + lower[1] + " " + lower[2] + " ");
			writer.write(upper[0] + " " + upper[1] + " " + upper[2] + " ");
			writer.newLine();      
		}
		writer.close();

		/**Return true, if this is false, the code would have stopped working, this should 
		 * probably be connected to a JDialogBox informing the user that the signature creation
		 * was successful...
		 */
		return true;
	}
}