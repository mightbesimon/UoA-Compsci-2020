/*	COMPSCI 230 (2020) - University of Auckland
	ASSIGNMENT FOUR - Bounce III
	Simon Shan  441147157
 */

package bounce.views;

import java.util.List;
import java.util.ArrayList;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;

import bounce.Shape;
import bounce.ShapeModel;
import bounce.ShapeModelEvent;
import bounce.ShapeModelListener;

/**
 * [ ADAPTER CLASS ]
 * This class adapts ShapeModel to TreeModel.
 * 
 * @author Simon Shan
 *
 */
public class TreeModelAdapterWithShapeModelListener
		extends TreeModelAdapter implements ShapeModelListener
{
	private List<TreeModelListener> _treeModelListeners;

	/**
	 * Create an adapter for specified ShapeModel.
	 */
	public TreeModelAdapterWithShapeModelListener(ShapeModel adaptee) {
		super(adaptee);
		_treeModelListeners = new ArrayList<TreeModelListener>();
	}

	/**
	 * Add TreeModelListener to this adapter's list of listeners.
	 */
	@Override
	public void addTreeModelListener(TreeModelListener l) {
		_treeModelListeners.add(l);
	}

	/**
	 * Remove TreeModelListener from this adapter's list of listeners.
	 */
	@Override
	public void removeTreeModelListener(TreeModelListener l) {
		_treeModelListeners.remove(l);
	}

	/**
	 * Update TreeModel from received ShapeModelEvent,
	 * by firing TreeModelEvent to TreeModelListeners.
	 */
	@Override
	public void update(ShapeModelEvent event) {
		Shape    shape = event.parent()==null ?
		                 event.operand() : event.parent();
		Object[] path  = shape.path().toArray();

		TreeModelEvent treeEvent = new TreeModelEvent(this, path,
									new int[] {event.index()},
									new Object[] {event.operand()});

		switch (event.eventType()) {

		case ShapeAdded :
			for (TreeModelListener listener :_treeModelListeners) {
				listener.treeNodesInserted(treeEvent);
			}
			break;

		case ShapeRemoved :
			for (TreeModelListener listener :_treeModelListeners) {
				listener.treeNodesRemoved(treeEvent);
			}
			break;

		default :
			break;
		}
	}
}
