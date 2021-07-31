package bounce;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent a shape composition. Classes whose instances are 
 * interested in the changing state of a ShapeModel instance should implement 
 * the ShapeModelListener interface so that their instances can be registered
 * as listeners on a ShapeModel. Whenever a Shape is added to or removed from a
 * Shape Model, or whenever a Shape within the model changes (i.e. due to a 
 * move() request) the ShapeModel notifies all registered listeners by firing a
 * ShapeModelEvent.
 * 
 * @author Ian Warren
 *
 */
public class ShapeModel {
	
	// Root of the shape composition.
	private NestingShape _root;
	
	// Boundaries governing Shape movement within a ShapeModel.
	private Dimension _bounds;
	
	// List of ShapeModelListeners.
	private List<ShapeModelListener> _listeners;
	
	
	/**
	 * Creates a ShapeModel with specified height and width bounds.
	 */
	public ShapeModel(Dimension bounds) {
		_root = new NestingShape(0, 0, 0, 0, bounds.width, bounds.height);
		_bounds = bounds;
		_listeners = new ArrayList<ShapeModelListener>();
	}

	/**
	 * Returns the root NestingShape of this ShapeModel object.
	 */
	public NestingShape root() {
		return _root;
	}
	
	/**
	 * Attempts to add a new Shape to a specified NestingShape held within the
	 * ShapeModel. If the shape cannot be added, no action is taken and this 
	 * method returns false. Possible reasons for this outcome are that the 
	 * shape is too large to fit into the NestingShape or that the shape is
	 * already part of a NestingShape. If the shape can be added, it is and
	 * this method fires a ShapeModelEvent to registered listeners alerting
	 * them of the new shape.
	 * @param shape the new shape to add to this ShapeModel.
	 * @param parent the intended parent of the new shape.
	 */
	public boolean add(Shape shape, NestingShape parent) {
		boolean success = true;
		
		try {
			parent.add(shape);
			
			// Fire event.
			fire(ShapeModelEvent.makeShapeAddedEvent(shape, this));
		} catch(IllegalArgumentException e) {
			success = false;
		}
		return success;
	}
	
	/**
	 * Attempts to remove the specified Shape from this ShapeModel instance. If
	 * the shape specified does not have a parent (i.e. it is not part of this
	 * ShapeModel), this method has no effect. In other cases, the specified 
	 * shape is removed and registered ShapeModelListeners are notified via a
	 * ShapeModelEvent.
	 * @param shape the Shape to remove.
	 */
	public void remove(Shape shape) {
		// Remove shape from its parent.
		NestingShape parent = shape.parent();
		
		if(parent != null) {
			int index = parent.indexOf(shape);
			parent.remove(shape);
		
			// Fire event.
			fire(ShapeModelEvent.makeShapeRemovedEvent(shape, parent, index, this));
		}
	}

	/**
	 * Progresses the animation. Calling this method causes each Shape in this 
	 * ShapeModel to move before notifying each registered ShapeModelListener 
	 * of the movement. Note that a clock() call results in ONE ShapeModelEvent
	 * being fired; the event identifies the root NestingShape.
	 */
	public void clock() {
		_root.move(_bounds.width, _bounds.height);
		
		// Fire event.
		fire(ShapeModelEvent.makeShapeMovedEvent(_root, this));
	}

	/**
	 * Registers a ShapeModelListener on this ShapeModel object.
	 */
	public void addShapeModelListener(ShapeModelListener listener) {
		_listeners.add(listener);
	}
	
	/**
	 * Deregisters a ShapeModelListener from this ShapeModel object.
	 */
	public void removeShapeModelListener(ShapeModelListener listener) {
		_listeners.remove(listener);
	}
	
	/*
	 * Iterates through registered ShapeModelListeners and fires a 
	 * ShapeModelEvent to each in turn.
	 */
	private void fire(ShapeModelEvent event) {
		for(ShapeModelListener listener : _listeners) {
			listener.update(event);
		}
	}
}
