/*	COMPSCI 230 (2020) - University of Auckland
	ASSIGNMENT THREE - Bounce II
	Simon Shan  441147157
 */
package bounce;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent a container shape
 * that can hold other shapes.
 * 
 * @author Simon Shan
 *
 */
public class NestingShape extends Shape {

	protected List<Shape>_children = new ArrayList<>();
	/**	Constructor arguments.
	 * @param x position.
	 * @param y position.
	 * @param deltaX horizontal speed.
	 * @param deltaY vertical speed.
	 * @param width  in pixels.
	 * @param height in pixels.
	 * @param text to display on the shape.
	 */
	public NestingShape() {
		super();
	}
	public NestingShape(int x, int y) {
		super(x, y);
	}
	public NestingShape(int x, int y, int deltaX, int deltaY) {
		super(x, y, deltaX, deltaY);
	}
	public NestingShape(int x, int y, int deltaX, int deltaY,
	                                  int width,  int height) {
		super(x, y, deltaX, deltaY, width, height);
	}
	public NestingShape(int x, int y, int deltaX, int deltaY,
						int width,  int height, String text) {
		super(x, y, deltaX, deltaY, width, height, text);
	}

	/**
	 * Moves a NestingShape object (including its children) within the bounds specified by arguments
	 * width and height. A NestingShape first moves itself, and then moves its children.
	 */
	@Override
	public void move(int width, int height) {
		super.move(width, height);
		for (Shape child : _children) {
			child.move(_width, _height);
		}
	}

	/**
	 * Paints a NestingShape object by drawing a rectangle around the edge of its bounding box. Once
	 * the NestingShape's border has been painted, a NestingShape paints its children.
	 */
	@Override
	protected void doPaint(Painter painter) {
		painter.drawRect(_x,_y,_width,_height);
											// paints the border of nesting shape
		painter.translate( _x,  _y);		// set new origin to top-left corner
											// for paiting children shapes
		for (Shape child : _children) {
			child.paint(painter);
		}
		painter.translate(-_x, -_y);		// restores origin to 0,0
	}

	/**
	 * Attempts to add a Shape to a NestingShape object. If successful, a two-way link is established
	 * between the NestingShape and the newly added Shape. Note that this method has package
	 * visibility - for reasons that will become apparent in Bounce III.
	 * @param shape the shape to be added.
	 * @throws IllegalArgumentException if an attempt is made to add a Shape to a NestingShape
	 * instance where the Shape argument is already a child within a NestingShape instance. An
	 * IllegalArgumentException is also thrown when an attempt is made to add a Shape that will not fit
	 * within the bounds of the proposed NestingShape object.
	 */
	void add(Shape shape) throws IllegalArgumentException {
		// check if already a child
		if (shape.parent()!=null)
			throw new IllegalArgumentException();
		// check if would fit
		if (		shape.x()+shape.width()  > _width
				&&	shape.y()+shape.height() > _height	)
			throw new IllegalArgumentException();

		_children.add(shape);
		shape.setParent(this);
	}

	/**
	 * Removes a particular Shape from a NestingShape instance. Once removed, the two-way link
	   between the NestingShape and its former child is destroyed. This method has no effect if the
	   Shape specified to remove is not a child of the NestingShape. Note that this method has package
	   visibility - for reasons that will become apparent in Bounce III.
	 * @param shape the shape to be removed.
	 */
	void remove(Shape shape) {
		_children.remove(shape);
		shape.setParent(null);		// reset shape's parent
	}

	/**
	 * Returns the Shape at a specified position within a NestingShape. If the position specified is less
	 * than zero or greater than the number of children stored in the NestingShape less one this method
	 * throws an IndexOutOfBoundsException.
	 * @param index the specified index position.
	 */
	public Shape shapeAt(int index) throws IndexOutOfBoundsException {
		return _children.get(index);
	}

	/**
	 * Returns the number of children contained within a NestingShape object. Note this method is not
	 * recursive - it simply returns the number of children at the top level within the callee
	 * NestingShape object.
	 */
	public int shapeCount() {
		return _children.size();
	}

	/**
	 * Returns the index of a specified child within a NestingShape object. If the Shape specified is not
	 * actually a child of the NestingShape this method returns -1; otherwise the value returned is in the
	 * range 0 .. shapeCount() - 1.
	 * @param shape whose index position within the NestingShape is requested.
	 */
	public int indexOf(Shape shape) {
		return _children.indexOf(shape);
	}

	/**
	 * Returns true if the Shape argument is a child of the NestingShape object on which this method is
	 * called , false otherwise.
	 */
	public boolean contains(Shape shape) {
		return _children.contains(shape);
	}
}
