/*	COMPSCI 230 (2020) - University of Auckland
	ASSIGNMENT THREE - Bounce II
	Simon Shan  441147157
 */
package bounce;

import java.awt.Image;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * Class to represent a ractangle image shape.
 * 
 * @author Simon Shan
 *
 */
public class ImageRectangleShape extends RectangleShape {

	protected Image _image;

	/**	Constructor arguments.
	 * @param deltaX horizontal speed.
	 * @param deltaY vertical speed.
	 * @param image  to display.
	 */
	public ImageRectangleShape(int deltaX, int deltaY,
											Image image) {
		super(DEFAULT_X_POS, DEFAULT_Y_POS, deltaX, deltaY,
			image.getWidth(null), image.getHeight(null));
		_image  = image;
	}

	/** loads image
	 * @param imageFileName
	 * @param shapeWidth to draw
	 * @return image loaded in memory
	 */
	public static Image makeImage(String imageFileName,
									int shapeWidth) {
		BufferedImage image = null;
		// read image
		try {
			image = ImageIO.read(new File(imageFileName));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		// get scale of image
		double scaleFactor = (double) shapeWidth / image.getWidth();
		int shapeHeight = (int) (scaleFactor
						* (double) image.getHeight());
		// resize image from scale
		BufferedImage scaled_image = new BufferedImage(shapeWidth,
									shapeHeight, image.getType());
		Graphics2D g = scaled_image.createGraphics();
		g.drawImage(image, 0, 0, shapeWidth, shapeHeight, null);
		g.dispose();

		return scaled_image;
	}
	
	/**
	 * Paints the image using the supplied Painter.
	 */
	@Override
	protected void doPaint(Painter painter) {
		painter.drawImage(_image,_x,_y,_width,_height);
	}
}
