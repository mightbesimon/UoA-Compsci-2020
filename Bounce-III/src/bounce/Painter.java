package bounce;

import java.awt.Color;
import java.awt.Image;

/** 
 * Interface to represent a type that offers primitive drawing methods.
 * 
 * @author Ian Warren
 *
 */
public interface Painter {
	/**
	 * Draws a rectangle. 
	 * 
	 * Parameters x and y specify the top left corner of the
	 * oval. Parameters width and height specify its width and height.
	 */
	public void drawRect(int x, int y, int width, int height);
	
	/**
	 * Draws an oval. 
	 * 
	 * Parameters x and y specify the top left corner of the
	 * oval. Parameters width and height specify its width and height.
	 */
	public void drawOval(int x, int y, int width, int height);
	
	/**
	 * Draws a line. 
	 * 
	 * Parameters x1 and y1 specify the starting point of the 
	 * line, parameters x2 and y2 the ending point.
	 */
	public void drawLine(int x1, int y1, int x2, int y2);
	
	/**
	 * Draws a text string. 
	 * 
	 * Parameters x and y represent the centre point of a
	 * bounding box in which the text is to be painted.
	 */
	public void drawCenteredText(String text, int x, int y);
	
	/**
	 * Draws a filled rectangle. 
	 * 
	 * Parameters x and y specify the top left position of the rectangle. 
	 * Parameters width and height specify its width and height.
	 */
	public void fillRect(int x, int y, int width, int height);
	
	/**
	 * Returns the current color used for painting.
	 */
	public Color getColor();
	
	/**
	 * Sets the color to be used for painting.
	 */
	public void setColor(Color color);
	
	/**
	 * Translates the current coordinate system by x and y.
	 */
	public void translate(int x, int y);
	
	/**
	 * Draws an image within the rectangle that is specified by the x, y, width 
	 * and height parameters.
	 * 
	 * The image is scaled if necessary so as to fit within the rectangular 
	 * bounding box. The x,y coordinate specified the top left position of the 
	 * bounding box. 
	 */
	public void drawImage(Image img, int x, int y, int width, int height);
}
