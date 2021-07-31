/*	COMPSCI 230 (2020) - University of Auckland
	ASSIGNMENT FOUR - Bounce III
	Simon Shan  441147157
 */

package bounce.views;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import bounce.NestingShape;
import bounce.Shape;
import bounce.ShapeModel;

/**
 * [ ADAPTER CLASS ]
 * This class adapts ShapeModel to TreeModel.
 * 
 * @author Simon Shan
 *
 */
public class TreeModelAdapter implements TreeModel {

	private ShapeModel _model;

	/**
	 * Create an adapter for specified ShapeModel.
	 */
	public TreeModelAdapter(ShapeModel adaptee) {
		_model = adaptee;
	}

	/**
	 * The root is ShapeModel's root.
	 */
	@Override
	public Object getRoot() {
		return _model.root();
	}

	/**
	 * NestingShapes are not leaves.
	 * All other Shapes are leaves.
	 */
	@Override
	public boolean isLeaf(Object node) {
		return !(node instanceof NestingShape);
	}

	/**
	 * Number of child shapes in a NestingShape.
	 */
	@Override
	public int getChildCount(Object parent) {
		return parent instanceof NestingShape ?
			((NestingShape)parent).shapeCount() : 0;
		// return ((NestingShape)parent).shapeCount();
	}

	/**
	 * Child shape at specified index.
	 */
	@Override
	public Object getChild(Object parent, int index) {
		if (parent instanceof NestingShape)
		     return ((NestingShape)parent).shapeCount() > index ?
					((NestingShape)parent).shapeAt(index) : null;
		else return null;
		// return ((NestingShape)parent).shapeAt(index);
	}

	/**
	 * Index of specified child shape in parent shape.
	 * If not child-parent than default null;
	 */
	@Override
	public int getIndexOfChild(Object parent, Object child) {
		return parent instanceof NestingShape ?
		    ((NestingShape)parent).indexOf((Shape)child) : -1;
		// return -1;
	}

	/* No implementation required. */
	public void addTreeModelListener(TreeModelListener l) {}
	public void removeTreeModelListener(TreeModelListener l) {}
	public void valueForPathChanged(TreePath path, Object newValue) {}
}
