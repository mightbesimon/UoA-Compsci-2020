package bounce;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Simple GUI program to show an animation of shapes. Class AnimationViewer is
 * a special kind of GUI component (JPanel), and as such an instance of 
 * AnimationViewer can be added to a JFrame object. A JFrame object is a 
 * window that can be closed, minimised, and maximised. The state of an
 * AnimationViewer object comprises a list of Shapes and a Timer object. An
 * AnimationViewer instance subscribes to events that are published by a Timer.
 * In response to receiving an event from the Timer, the AnimationViewer iterates 
 * through a list of Shapes requesting that each Shape paints and moves itself.
 * 
 * @author Ian Warren, Simon Shan
 *
 */
@SuppressWarnings("serial")
public class AnimationViewer extends JPanel implements ActionListener {
	// Frequency in milliseconds for the Timer to generate events.
	private static final int DELAY = 20;

	// Collection of Shapes to animate.
	private List<Shape> _shapes;

	private Timer _timer = new Timer(DELAY, this);

	/**
	 * Creates an AnimationViewer instance with a list of Shape objects and 
	 * starts the animation.
	 */
	public AnimationViewer() {
		_shapes = new ArrayList<Shape>();

/************************ simon: tests ************************/
		/* path to breezy horse image */
		// String path = getClass().getResource("..")
		// 						.toString()
		// 						.replaceAll("%20", " ")
		// 						.substring(5);
		// String filename = path + "../breezy horse.jpg";
		// System.out.printf("filename = %s\n", filename);

		/* test ImageRectangleShape */
		// Image image = ImageRectangleShape.makeImage(filename, 200);
		// Shape imgRect = new ImageRectangleShape(1, 1, image);
		// _shapes.add(imgRect);

		/* test NestingShape */
		NestingShape nest1 = new NestingShape(50, 40, 3, 3, 200, 200);
		NestingShape nest2 = new NestingShape(200, 100, 2, 2, 100, 100,
														"nesting inner");
		Shape oval = new OvalShape(80, 80, 1, 1, 20, 20);
		nest2.add(oval);
		nest2.add(new DynamicRectangleShape(25, 80, 1, 1, 20, 20, 
								"dyn rect inner", java.awt.Color.ORANGE));
		nest1.add(new OvalShape(100, 180, 2, 2, 40, 40));
		nest1.add(new DynamicRectangleShape(30, 180, 2, 2, 40, 40,
								"dyn rect outer", java.awt.Color.ORANGE));
		nest1.add(nest2);
		_shapes.add(nest1);
		// System.out.println(oval.path());
		// System.out.println(nest1.parent());

		/* test display text */
		// Shape rect = new RectangleShape(0, 200, 1, 1, 200, 200, "test");
		// _shapes.add(rect);
/************************ simon: tests ************************/
		
		// Start the animation.
		_timer.start();
	}

	/**
	 * Called by the Swing framework whenever this AnimationViewer object
	 * should be repainted. This can happen, for example, after an explicit 
	 * repaint() call or after the window that contains this AnimationViewer 
	 * object has been opened, exposed or moved.
	 * 
	 */
	@Override
	public void paintComponent(Graphics g) {
		// Call inherited implementation to handle background painting.
		super.paintComponent(g);
		
		// Calculate bounds of animation screen area.
		int width = getSize().width;
		int height = getSize().height;
		
		// Create a GraphicsPainter that Shape objects will use for drawing.
		// The GraphicsPainter delegates painting to a basic Graphics object.
		Painter painter = new GraphicsPainter(g);
		
		// Progress the animation.
		for(Shape s : _shapes) {
			s.paint(painter);
			s.move(width, height);
		}
	}

	/**
	 * Notifies this AnimationViewer object of an ActionEvent. ActionEvents are
	 * received by the Timer.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Request that the AnimationViewer repaints itself. The call to 
		// repaint() will cause the AnimationViewer's paintComponent() method 
		// to be called.
		repaint();
	}
	
	/**
	 * Main program method to create an AnimationViewer object and display this
	 * within a JFrame window.
	 */
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame("Animation viewer");
				frame.add(new AnimationViewer());
		
				// Set window properties.
				frame.setSize(500, 500);
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}
}
