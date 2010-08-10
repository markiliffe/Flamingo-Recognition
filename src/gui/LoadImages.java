package gui;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.*;

import javax.imageio.ImageIO;

@SuppressWarnings("serial")
public class LoadImages extends Component {

	BufferedImage img;
	BufferedImage scaled_image;
	Graphics2D graphics2D;
	ColorModel colour;

	public void paint(Graphics g) {
		g.drawImage(img, 0, 0, null);
	}

	public LoadImages() {
		try {
//			img = ImageIO.read(new File("/Users/mark/Desktop/12mpix Panoramas/06-05 - 12mpix panorama/34_06-05_12mpix.jpg"));
//			scaled_image = img;
//			graphics2D = img.createGraphics();
//			AffineTransform xform = AffineTransform.getScaleInstance(0.75, 0.75);
//			graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
//			graphics2D.drawImage(scaled_image, xform, null);
//			graphics2D.dispose();
		} catch (Exception e/*IOException e*/) {
		}

	}

	public void get_RGB(){

		int temp_RGB = 0;
		int n = 0;
		int i = 0;

		System.out.println(img.getHeight() + " " +  img.getWidth());
		while(i<=img.getWidth()-1){
			for(n = 0; n < img.getHeight() - 1;n++){
				temp_RGB = img.getRGB(i,n)+0xFFFFFF+1;

				int temp_red = ColorModel.getRGBdefault().getRed(temp_RGB);
				int temp_green = ColorModel.getRGBdefault().getBlue(temp_RGB);
				int temp_blue = ColorModel.getRGBdefault().getBlue(temp_RGB);

				System.out.println("i = " + i + " n = " + n + " " +  temp_red + " " + temp_green + " " + temp_blue );
			}
			n = 0;
			i++;
		}
	}

	public Dimension getPreferredSize() {
		if (img == null) {
			return new Dimension(100,100);
		} else {
			return new Dimension(img.getWidth(null), img.getHeight(null));
		}
	}
}
