/*	COMPSCI 230 (2020) - University of Auckland
	ASSIGNMENT THREE - Bounce II
	Simon Shan  441147157
 */
package bounce;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract superclass to represent the general concept of a Shape. This class
 * defines state common to all special kinds of Shape instances and implements
 * a common movement algorithm. Shape subclasses must override method paint()
 * to handle shape-specific painting.
 * 
 * @author Ian Warren, Simon Shan
 *
 */
public abstract class Shape {

	// === Constants for default values. === //
	protected static final int DEFAULT_X_POS   = 0 ;
	protected static final int DEFAULT_Y_POS   = 0 ;
	protected static final int DEFAULT_DELTA_X = 5 ;
	protected static final int DEFAULT_DELTA_Y = 5 ;
	protected static final int DEFAULT_HEIGHT  = 35;
	protected static final int DEFAULT_WIDTH   = 25;

	// === Instance variables, accessible by subclasses. ===//
	// initialised with default values
	protected int _x      = DEFAULT_X_POS  ;
	protected int _y      = DEFAULT_Y_POS  ;
	protected int _deltaX = DEFAULT_DELTA_X;
	protected int _deltaY = DEFAULT_DELTA_Y;
	protected int _width  = DEFAULT_WIDTH  ;
	protected int _height = DEFAULT_HEIGHT ;

	/**	Constructor arguments.
	 * @param x position.
	 * @param y position.
	 * @param deltaX horizontal speed.
	 * @param deltaY vertical speed.
	 * @param width  in pixels.
	 * @param height in pixels.
	 * @param text to display on the shape.
	 */
	public Shape() {
	}
	public Shape(int x, int y) {
		_x = x;
		_y = y;
	}
	public Shape(int x, int y, int deltaX, int deltaY) {
		this(x, y);
		_deltaX = deltaX;
		_deltaY = deltaY;
	}
	public Shape(int x, int y, int deltaX, int deltaY,
	                           int width,  int height) {
		this(x, y, deltaX, deltaY);
		_width  = width ;
		_height = height;
	}
	
	/**
	 * Moves this Shape object within the specified bounds. On hitting a 
	 * boundary the Shape instance bounces off and back into the two- 
	 * dimensional world. 
	 * @param width width of two-dimensional world.
	 * @param height height of two-dimensional world.
	 */
	public void move(int width, int height) {
		_x += _deltaX;
		_y += _deltaY;

		if (_x <= 0) {					// bounce off left wall
			_x = 0;
			_deltaX *= -1;
		} else
		if (_x + _width >= width) {		// bounce off right wall
			_x = width - _width;
			_deltaX *= -1;
		}

		if (_y <= 0) {					// bounce off top wall
			_y = 0;
			_deltaY *= -1;
		} else
		if (_y + _height >= height) {	// bounce off bottom wall
			_y = height - _height;
			_deltaY *= -1;
		}
	}

	// public abstract void paint(Painter painter);
	// rewrote below in Bounce-II section

	/**
	 * Returns shape's x, y position.
	 */
	public int x() {
		return _x;
	}
	public int y() {
		return _y;
	}
	
	/**
	 * Returns shape's speed and direction.
	 */
	public int deltaX() {
		return _deltaX;
	}
	public int deltaY() {
		return _deltaY;
	}
	
	/**
	 * Returns shape's width, height.
	 */
	public int width() {
		return _width;
	}
	public int height() {
		return _height;
	}

	/**
	 * Returns shape's text
	 */
	public String text() {
		return _text;
	}
	
	/**
	 * Returns a String whose value is the fully qualified name of this class 
	 * of object. E.g., when called on a RectangleShape instance, this method 
	 * will return "bounce.RectangleShape".
	 */
	@Override
	public String toString() {
		return getClass().getName();
	}

/*************************************************************/
/***********************   BOUNCE-II   ***********************/
/*************************************************************/

	protected NestingShape _parent;
	protected String _text;

	public Shape(int x, int y, int deltaX, int deltaY,
					int width, int height, String text) {
		this(x, y, deltaX, deltaY, width, height);
		_text = text;
	}

	/**
	 * Returns the NestingShape that contains the Shape that method parent is called on. If the callee
	 * object is not a child within a NestingShape instance this method returns null.
	 *
	 * Sets the parent NestingShape of the shape object that this method is called on.
	 */
	public NestingShape parent() {
		return _parent;
	}
	protected void setParent(NestingShape parent) {
		_parent = parent;
	}

	/**
	 * Returns an ordered list of Shape objects. The first item within the list is the root NestingShape
	 * of the containment hierarchy. The last item within the list is the callee object (hence this method
	 * always returns a list with at least one item). Any intermediate items are NestingShapes that
	 * connect the root NestingShape to the callee Shape. E.g. given:
	 * a call to last.path() yields: [root , intermediate , last] 
	 */
	public List<Shape> path() {
		List<Shape> path = _parent==null  ?		// if doesn't have a parent
						new ArrayList<>() :		// create an empty path
						_parent.path();			// otherwise parent's path
		path.add(this);							// add this shape to path
		return path;
	}

	/** [!] Template Method [!]
	 * Paints the shape (hook) **and then** the text.
	 ! Please override *mandatory hook method* doPaint() in subclasses.
	 */
	public final void paint(Painter painter) {
		  doPaint(painter);
		textPaint(painter);
	}

	/** [!] Mandatory Hook Method [!]
	 * Paints the specific shape using the supplied Painter.
	 ! Please @Override in subclasses.
	 */
	protected abstract void doPaint(Painter painter);

	/**
	 * Paints the text if the object has one.
	 */
	private void textPaint(Painter painter) {
		if (_text!=null) {
			int centerX = _x + _width /2;
			int centerY = _y + _height/2;
			painter.drawCenteredText(_text, centerX, centerY);
		}
	}

}
