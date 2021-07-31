package bounce;

/**
 * Class to describe a change to the state of a ShapeModel object. An instance
 * of ShapeModelEvent is sent in a notification message (an update() call) by a
 * ShapeModel when communicating updates to its ShapeModelListeners.
 * 
 * @author Ian Warren
 *
 */
public class ShapeModelEvent {

	// Set of event types.
	public enum EventType { ShapeAdded, ShapeRemoved, ShapeMoved };
	
	private EventType _type;      // Type of event.
	private Shape _operand;       // Shape to which the event relates.
	private ShapeModel _source;   // ShapeModel object that fired the event.
	private int _index;           // Index of fOperand within its parent, 
	                              // -1 if fOperand is the root NestingShape.
	private NestingShape _parent; // Parent NestingShape of fOperand; for 
	                              // ShapeRemoved events this is the former 
	                              // parent of fOperand.
	                    
	/**
	 * Creates a ShapeAdded ShapeModelEvent.
	 * @param shapeAdded the Shape that has been added to a ShapeModel.
	 * @param source the ShapeModel object that fires the event.
	 */
	public static ShapeModelEvent makeShapeAddedEvent(
			Shape shapeAdded, ShapeModel source) {
		NestingShape parent = shapeAdded.parent();
		int index = parent.indexOf(shapeAdded);
		
		return new ShapeModelEvent(EventType.ShapeAdded, shapeAdded, parent, index, source);
	}
	
	
	/**
	 * Creates a ShapeRemoved ShapeModelEvent.
	 * @param shapeRemoved the Shape object that has been removed from a 
	 * ShapeModel.
	 * @param formerParent the former parent of shapeRemoved. 
	 * @param index the index position that shapeRemoved used to be stored at
	 * within formerParent.
	 * @param source the ShapeModel object that fires the event.
	 */
	public static ShapeModelEvent makeShapeRemovedEvent(
			Shape shapeRemoved, NestingShape formerParent, int index, ShapeModel source) {
		return new ShapeModelEvent(
				EventType.ShapeRemoved, shapeRemoved, formerParent, index, source);
		
	}
	
	/**
	 * Creates a ShapeMoved ShapeModelEvent. 
	 * @param shapeMoved the Shape object that has moved.
	 * @param source the ShapeModel object that fires the event.
	 */
	public static ShapeModelEvent makeShapeMovedEvent(
			Shape shapeMoved, ShapeModel source) {
		NestingShape parent = shapeMoved.parent();
		int index = -1;
		
		if(parent != null) {
			index = parent.indexOf(shapeMoved);
		}
		
		return new ShapeModelEvent(EventType.ShapeMoved, shapeMoved, parent, index, source);
	}
	
	/*
	 * Hidden constructor used by the static factory methods. 
	 */
	private ShapeModelEvent(EventType type, Shape operand, NestingShape parent, int index, ShapeModel source) {
		_type = type;
		_operand = operand;
		_parent = parent;
		_index = index;
		_source = source;
	}
	
	/**
	 * Returns the type of the event, one of ShapeAdded, ShapeRemoved, 
	 * ShapeMoved.
	 */
	public EventType eventType() {
		return _type;
	}
	
	/**
	 * Returns the Shape object to which this ShapeModelEvent applies.
	 */
	public Shape operand() {
		return _operand;
	}
	
	/**
	 * Returns the parent NestingShape of the Shape to which this 
	 * ShapeModelEvent applies. If the type of this ShapeModelEvent is
	 * ShapeRemoval, this method returns the former parent. If the Shape to 
	 * which this ShapeModelEvent applies does not have a parent (e.g. it is
	 * the root NestingShape) this method returns null.
	 */
	public NestingShape parent() {
		return _parent;
	}
	
	/**
	 * Returns the ShapeModel that fired this ShapeModelEvent.
	 */
	public ShapeModel source() {
		return _source;
	}
	
	/**
	 * Returns the index position of the Shape object returned by operand()
	 * within its NestingShape parent. If the type of this ShapeModelEvent is
	 * ShapeRemoved, this method returns the position the Shape occupied within
	 * its parent before it was removed.
	 * @return
	 */
	public int index() {
		return _index;
	}
}
