package bounce.views;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import bounce.GraphicsPainter;
import bounce.NestingShape;
import bounce.Painter;
import bounce.ShapeModelEvent;
import bounce.ShapeModelListener;

/**
 * Class that presents an animation view of a ShapeModel. This class implements
 * the ShapeModelListener interface, hence an AnimationViewer instance is
 * notified via a ShapeModelEvent whenever the model has changed. A registered
 * AnimationViewer responds to a ShapeModelEvent by adding any new shape to the
 * animation, removing a deleted shape from the animation, or updating the 
 * positions of shapes that have moved.
 * 
 * @author Ian Warren
 *
 */
public class AnimationView extends JPanel implements ShapeModelListener {

	private static final long serialVersionUID = 8567651321082907674L;

	// Reference to root NestingShape.
	private NestingShape _root;
	
	/**
	 * Creates an AnimationView object with specified bounds.
	 */
	public AnimationView(Dimension bounds) {
		_root = null;
		setSize(bounds.width, bounds.height);
	}
	
	/**
	 * Implements custom painting to display the animation.
	 */
	public void paintComponent(Graphics g) {
		// Call inherited implementation to handle background painting.
		super.paintComponent(g);
		
		// Create a GraphicsPainter to paint the Swing component.
		Painter painter = new GraphicsPainter(g);
		
		/*
		 * Paint the shapes, starting with the root and recursively work
		 * through the composition structure.
		 */
		if(_root != null) {
			_root.paint(painter);
		}
	}
	
	/**
	 * Updates this AnimationView so that it is consistent with the ShapeModel
	 * that made the update() call.
	 */
	public void update(ShapeModelEvent event) {
		_root = event.source().root();
		repaint();
	}
	
}
