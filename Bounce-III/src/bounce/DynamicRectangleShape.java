/*	COMPSCI 230 (2020) - University of Auckland
	ASSIGNMENT THREE - Bounce II
	Simon Shan  441147157
 */
package bounce;

import java.awt.Color;

/**
 * Class to represent a changing rectangle shape.
 * 
 * @author Simon Shan
 *
 */
public class DynamicRectangleShape extends RectangleShape {

	// Default colour.
	protected Color _color = Color.RED;
	protected int   _state = 1;

	/**	Constructor arguments.
	 * @param x position.
	 * @param y position.
	 * @param deltaX horizontal speed.
	 * @param deltaY vertical speed.
	 * @param width  in pixels.
	 * @param height in pixels.
	 * @param text to display on the shape.
	 * @param color  of solid shape.
	 */
	public DynamicRectangleShape(
			int x, int y, int deltaX, int deltaY,
			int width, int height)
	{
		super(x, y, deltaX, deltaY, width, height);
	}
	public DynamicRectangleShape(
			int x, int y, int deltaX, int deltaY,
			int width, int height, Color color)
	{
		super(x, y, deltaX, deltaY, width, height);
		_color = color;
	}
/************************* BOUNCE-II *************************/
	public DynamicRectangleShape(
			int x, int y, int deltaX, int deltaY,
			int width, int height, String text, Color color)
	{
		super(x, y, deltaX, deltaY, width, height, text);
		_color = color;
	}
/************************* BOUNCE-II *************************/

	/**
	 * Changes state when bouncing.
	 */
	@Override
	public void move(int width, int height) {
		super.move(width, height);
		/*	if bounced off left/right wall
			changes to state 1				*/
		if (_x==0 ||_x==width -_width ) {
			_state = 1;
		} else
		/*	if bounced off top/bottom wall
			changes to state 0				*/
		if (_y==0 ||_y==height-_height) {
			_state = 0;
		}
	}

	/**
	 * Paints the shape using the supplied Painter.
	 * state 0: paints outline
	 * state 1: paints solid
	 */
	@Override
/************************* BOUNCE-II *************************/
	protected void doPaint(Painter painter) {
		switch (_state) {
			case 0: super.doPaint(painter);
					break;
/************************* BOUNCE-II *************************/
			case 1: solid_doPaint(painter);
					break;
		}
	}

	/**
	 * Paints a solid rectangle as opposed to
	 * a hollow one in superclass.
	 */
	private void solid_doPaint(Painter painter) {
		// saves original colour
		Color _old_color = painter.getColor();
		// draw solid
		painter.setColor(_color);
		painter.fillRect(_x,_y,_width,_height);
		// sets colour back
		painter.setColor(_old_color);
	}
}
