package algorithms;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * 
 * 
 * 
 * @author mark
 *
 */

public class Census_Flamingos {

	private BufferedImage img;

	//These values are used when classifying 12m stitched images
	double nearDistance = 60;
	double farDistance = 687;

	/** //These values are used when classifying 9m stitched images
	 * double nearDistance = 184.0;
	 * double farDistance = 885.0;
	 */

	/** // These values are used when classifying 3m stitched images
	 * double nearDistance = 626.0;
	 * double farDistance = 683.0;
	 */

	/** //These values are used when classifying 3m Android Images
	 * // double nearDistance = 548.0;
	 * // double farDistance = 704.0;
	 */

	//These values are used when classifying 12m stitched images.
	int nearPixelWidth = 103;
	int farPixelWidth = 37;
	int nearPixelHeight = 35;
	int farPixelHeight = 12;


	/** //These values are used when classifying 9m stitched images.
	 * int nearPixelWidth = 76;
	 * int farPixelWidth = 24;
	 * int nearPixelHeight = 24;
	 * int farPixelHeight = 6;
	 */

	/** // These values are used when classifying 3m Mobile images
	 * //int nearPixelWidth = 10;
	 * //int farPixelWidth = 3;
	 * //int nearPixelHeight = 6;
	 * //int farPixelHeight = 3;
	 */

	/** // These values are used when classifying 3m Mobile images
	 * //	int nearPixelWidth = 18;
	 * //	int farPixelWidth = 1;
	 * //	int nearPixelHeight = 10;
	 * //	int farPixelHeight = 1;
	 * 
	 */


	private double flamingoNumber;

	/**
	 * Default constructor, creates the class instance, ready for an image. 
	 * 
	 */
	public Census_Flamingos() {
		System.out.println("Census Algorithm Constructor Created");
	}

	/**
	 * Reads the image to be processed into the class, and launches the get_RGB method 
	 * 
	 * 
	 * @param filePathImage
	 * @throws IOException
	 */
	public void run(String filePathImage) throws IOException{
		img = ImageIO.read(new File(filePathImage));
		get_RGB();
	}

	/**
	 * Iterate through each pixel within the loaded image.
	 * Bit-shift on the RGB hex-value, enquire if the pixel
	 * is a flamingo pixel 
	 * 
	 */
	public void get_RGB(){
		int temp_RGB = 0;
		System.out.println(img.getHeight() + " " +  img.getWidth());
		for(int row = 0; row < img.getWidth(); row++){
			for(int col = 0; col < img.getHeight() - 1;col++){
				temp_RGB = img.getRGB(row,col);
				int temp_red = (int)(temp_RGB&0x00FF0000)>>>16;
		int temp_green = (int)((temp_RGB&0x0000FF00)>>>8);
		int temp_blue = (int)(temp_RGB&0x000000FF);
		isFlamingo(temp_red, temp_green, temp_blue, row, col);
			}
		}
		System.out.println("There are " + (flamingoNumber*10000) + " Flamingos in this image");
	}

	/**
	 * This method increases the Flamingo counter by one, when enough pixels have been identified as
	 * being Flamingo. Due the inaccuracies of the image writer pixel values aren't exactly as defined
	 * being ±10 more within the RGB spectrum. 
	 *  
	 * The exception being thrown is a divide by zero exception. 
	 *  
	 */ 

	private void isFlamingo(int red, int green, int blue, int row, int col){
		if(red >= 245 && red < 256 && green >= 20 && green <= 40 && blue >= 100 && blue <= 120 ) {
			System.out.println("row: " + row + " column " + col + " Flamingo!");
			try{
				flamingoPercentage(interpolateY(row, col), interpolateX(row, col));
			} catch (Exception e) {
				flamingoNumber = flamingoNumber++;
			}
		}
	}

	/**
	 * 
	 * Performs the first interpolation
	 * 
	 * 
	 * @param row
	 * @param col
	 * @return
	 */

	public double interpolateX(int row, int col){
		double tempInterpolationXDimension;
		double tempX = ((img.getHeight()-col) + 0);
		double tempY = ((farPixelWidth - nearPixelWidth)/(col-0));
		tempInterpolationXDimension = nearPixelWidth + tempX + tempY;
		return tempInterpolationXDimension;
	}

	/**
	 * Performs the second interpolation.
	 * 
	 * @param row
	 * @param col
	 * @return
	 */

	private double interpolateY(int row, int col){
		double tempInterpolationYDimension;
		double tempX = ((img.getHeight()-col) + farDistance);
		double tempY = ((nearPixelHeight-farPixelHeight)/(nearDistance-farDistance));
		tempInterpolationYDimension = nearPixelHeight + tempX * tempY;
		return tempInterpolationYDimension;
	}


	/**
	 * This method holds sway over the final flamingo number, 
	 * getting the absolute value incase the interpolation tries to divide by zero.
	 * 
	 * @param interpolationX
	 * @param interpolationY
	 */
	private void flamingoPercentage(double interpolationX, double interpolationY){
		flamingoNumber = flamingoNumber + Math.abs(1/(interpolationX * interpolationY));
	}
}
