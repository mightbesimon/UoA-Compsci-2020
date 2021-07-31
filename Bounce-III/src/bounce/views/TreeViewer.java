package bounce.views;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;

import bounce.NestingShape;
import bounce.RectangleShape;
import bounce.ShapeModel;

/** 
 * Simple demo program that uses a JTree component whose model is an instance
 * class Task1. If the Task1 class is implemented correctly, the JTree 
 * component will display the contents of the Task1 object's associated 
 * ShapeModel.
 * 
 * @author Ian Warren
 * 
 */
@SuppressWarnings("serial")
public class TreeViewer extends JPanel {

	private TreeModelAdapter _adapter;
	private JTree _view;
	
	public TreeViewer() {
		// Create ShapeModel and populate it with shapes.
		ShapeModel model = new ShapeModel(new Dimension(500, 500));
		
		NestingShape top = new NestingShape(10, 10, 2, 2, 200, 250);
		NestingShape mid = new NestingShape(10, 10, 1, 3, 150, 100);
		
		model.add(mid, top);
		model.add(new RectangleShape(10, 10, 3, 2, 70, 40), top);
		model.add(new RectangleShape( 0,  0, 1, 1, 80, 35), mid);
		model.add(top, model.root());
		model.add(new RectangleShape(0, 0,  2,  3), model.root());
		model.add(new RectangleShape(0, 0,  1,  1), model.root());
		
		// Create an instance of Task2 and set its adaptee to the ShapeModel.
		_adapter = new TreeModelAdapter(model);
		
		/*
		 * Create the JTree component and set as its model the Task2 adapter 
		 * instance.
		 */
		_view = new JTree(_adapter);
		
		/*
		 * Add the JTree to a scrollPane - scrollbars will be activated as 
		 * and when necessary. Add the scrollpane to this JPanel.
		 */
		JScrollPane scrollPaneForTree = new JScrollPane(_view);
		scrollPaneForTree.setPreferredSize(new Dimension(200,300));
		add(scrollPaneForTree);
	}
	
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame("Tree viewer");
/*********************** simon: setting ***********************/
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
/*********************** simon: setting ***********************/
				frame.add(new TreeViewer());
		
				// Set window properties.
				frame.pack();
				frame.setResizable(false);
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}
}
