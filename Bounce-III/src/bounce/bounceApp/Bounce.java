package bounce.bounceApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import bounce.DynamicRectangleShape;
import bounce.NestingShape;
import bounce.OvalShape;
import bounce.RectangleShape;

import bounce.Shape;
import bounce.ShapeModel;
import bounce.forms.FormResolver;
import bounce.forms.util.FormComponent;
import bounce.forms.util.FormHandler;
import bounce.views.AnimationView;
import bounce.views.TreeModelAdapterWithShapeModelListener;
import bounce.views.TableModelAdapter;


/**
 * Main program for Bounce application. A Bounce instance sets up a GUI 
 * comprising three views of a ShapeModel: an animation view, a table view and
 * a tree view. In addition the GUI includes buttons and associated event
 * handlers to add new shapes to the animation and to remove existing shapes. 
 * A Bounce object uses a Timer to progress the animation; this results in the 
 * ShapeModel being sent a clock() message to which it responds by moving its
 * constituent Shape objects and then by notifying the three views 
 * (ShapeModelListeners). The application uses a BounceConfig object to read 
 * properties from the bounce.properties file, one of which is the name of a
 * ShapeFactory implementation class that is used to create Shapes on request. 
 * 
 * @author Ian Warren, Simon Shan
 * 
 */
@SuppressWarnings("serial")
public class Bounce extends JPanel {
	private static final int DELAY = 25;

	// Underlying model for the application.
	private ShapeModel _model;
	
	private ShapeClassComboBoxModel _comboBoxModel;
	
	// View instances.
	private JTree _treeView;
	private AnimationView _animationView;
	private JTable _tabularView;
	
	/*
	 * Adapter objects (ShapeModelListeners) that transform ShapeModelEvents 
	 * into Swing TreeModel and TableModel events. 
	 */ 
	private TreeModelAdapterWithShapeModelListener _treeModelAdapter;
	private TableModelAdapter _tableModelAdapter;
	
	// Swing components to handle user input.
	private JButton _newShape;
	private JButton _deleteShape;
	private JComboBox<Class<? extends Shape>> _shapeTypes;
	
	// Shape selected in the JTree view.
	private Shape _shapeSelected;

	/**
	 * Creates a Bounce object.
	 */
	public Bounce() {
		// Instantiate model and populate it with an initial set of shapes.
		BounceConfig config = BounceConfig.instance();
		_model = new ShapeModel(config.getAnimationBounds());
		populateModel();
		
		_comboBoxModel = new ShapeClassComboBoxModel();
		
		// Instantiate GUI objects and construct GUI.
		buildGUI();
		
		// Register views with models.
		_model.addShapeModelListener(_animationView);
		_model.addShapeModelListener(_tableModelAdapter);
		_model.addShapeModelListener(_treeModelAdapter);
		
		// Setup event handlers to process user input.
		setUpEventHandlers();
		
		// Show GUI and ensure the root shape within the JTree view is selected.
		_treeView.setSelectionPath(new TreePath(_model.root()));
		
		// Start animation.
		Timer timer = new Timer(DELAY, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_model.clock();
			}
		} );
		timer.start();
	}

	private static void createAndShowGUI() {
		JFrame frame = new JFrame("Bounce");
/*********************** simon: setting ***********************/
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
/*********************** simon: setting ***********************/
		JComponent newContentPane = new Bounce();
		frame.add(newContentPane);
		frame.pack();
	    frame.setLocationRelativeTo(null);
	    frame.setResizable(false);
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				createAndShowGUI();
			}
		});
	}
	
	/*
	 * Adds shapes to the model.
	 */
	private void populateModel() {
		NestingShape root = _model.root();

/*********************** simon: shapes ************************/
		// _model.add(new GemShape(20, 20, 4, 4, 200, 20, "Bounce"), root);
		//*********** +---------------------+---++---+---+---++---++---++------+ ***********//
		//*********** |        shape        | x || y | dx| dy|| w || l || name | ***********//
		//*********** +---------------------+---++---+---+---++---++---++------+ ***********//
		NestingShape child= new NestingShape( 10, 200,  4,  2, 150, 150, "Frederic");
		_model.add(new        RectangleShape( 10,  10,  1,  1,  32,  24, "Fred"), child);
		_model.add(new             OvalShape( 10,  10,  2,  1,  60,  60, "Eric"), child);
		_model.add(child, root);

		_model.add(new DynamicRectangleShape(250, 400,  2,  4,  64,  48, "Osward", Color.ORANGE), root);
		_model.add(new        RectangleShape(440,   0, 10,  8,   4,   2, "Timmy"), root);
		_model.add(new             OvalShape( 50, 110,  2,  2, 100, 100, "Anthony"), root);
/*********************** simon: shapes ************************/

	}
	
	/*
	 * Registers event handlers with Swing components to process user inputs.
	 */
	private void setUpEventHandlers() {
		/*
		 * Event handling code to be executed whenever the users presses the 
		 * "New" button. Based on the Shape type selected in the combo box, a
		 * suitable Form/FormHandler pair is acquired from the FormResolver.
		 * The Form is used by the user to specify attribute values for the 
		 * new Shape to be created, and the FormHandler is responsible for 
		 * instantiating the correct Shape subclass and adding the new instance
		 * to the application's ShapeModel. 
		 */
		_newShape.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unchecked")
				Class<? extends Shape> cls = (Class<? extends Shape>)_comboBoxModel.getSelectedItem();
				FormComponent form = FormResolver.getForm(cls);
				FormHandler handler = FormResolver.getFormHandler(cls, _model, (NestingShape)_shapeSelected);
				form.setFormHandler(handler);
				form.prepare();
				
				// Display the form.
				form.setLocationRelativeTo(null);
				form.setVisible(true);

			}
		});
		
		/*
		 * Event handling code to be executed whenever the user presses the
		 * "Delete" button. The shape that is currently selected in the JTree
		 * view is removed from the model. During removal, the removed shape's
		 * former parent is selected in the JTree.
		 */
		_deleteShape.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Shape selection = _shapeSelected;
				NestingShape parent = selection.parent();
				
				_treeView.setSelectionPath(new TreePath(parent.path().toArray()));
				_model.remove(selection);
				
			}
		});
		
		/*
		 * Event handling code to be executed whenever the user selects a node
		 * within the JTree view. The event handler records which shape is
		 * selected and in addition enables/disables the "New" and "Delete"
		 * buttons appropriately. In addition, the TableModel representing the
		 * the shape selected in the JTree component is informed of the newly
		 * selected shape.
		 */
		_treeView.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				TreePath selectionPath = _treeView.getSelectionPath();
				_shapeSelected = (Shape)selectionPath.getLastPathComponent();
				
				/*
				 * Enable button fNewShape only if what is selected in the 
				 * JTree is a NestingShape. Rationale: new shapes can only be
				 * added to NestingShape instances. 
				 */
				_newShape.setEnabled(_shapeSelected instanceof NestingShape);
				
				/*
				 * Enable button fDeleteShape only if what is selected in the
				 * JTree is not the root node. Rationale: any shape can be 
				 * removed with the exception of the root.
				 */
				_deleteShape.setEnabled(_shapeSelected != _model.root());
				
				/*
				 * Tell the table model to represent the shape that is now
				 * selected in the JTree component.
				 */
				_tableModelAdapter.setAdaptee(_shapeSelected);
			}
		});
	}
	
	/*
	 * Creates and lays out GUI components. Note: there is nothing particularly
	 * interesting about this method - it simply builds up a composition of GUI
	 * components and makes use of borders, scroll bars and layout managers. 
	 */
	//@SuppressWarnings("unchecked")
	private void buildGUI() {
		// Create Swing model objects.
		_treeModelAdapter = new TreeModelAdapterWithShapeModelListener(_model);
		_tableModelAdapter = new TableModelAdapter(_model.root());
		
		// Create main Swing components.
		_treeView = new JTree(_treeModelAdapter);
		_treeView.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		_tabularView = new JTable(_tableModelAdapter);
		_animationView = new AnimationView(BounceConfig.instance().getAnimationBounds());
		
		/*
		 * Create a panel to house the JTree component. The panel includes a 
		 * titled border and scrollbars that will be activated when necessary.
		 */
		JPanel treePanel = new JPanel();
		treePanel.setBorder(BorderFactory.createTitledBorder("Shape composition hierarchy"));
		JScrollPane scrollPaneForTree = new JScrollPane(_treeView);
		scrollPaneForTree.setPreferredSize(new Dimension(300,504));
		treePanel.add(scrollPaneForTree);
		
		/*
		 * Create a panel to house the animation view. This panel includes a 
		 * titled border and scroll bars if the animation area exceeds the 
		 * allocated screen space.
		 */
		JPanel animationPanel = new JPanel();
		animationPanel.setBorder(BorderFactory.createTitledBorder("Shape animation"));
		JScrollPane scrollPaneForAnimation = new JScrollPane(_animationView);
		scrollPaneForAnimation.setPreferredSize(new Dimension(504,504));
		animationPanel.add(scrollPaneForAnimation);
		_animationView.setPreferredSize(BounceConfig.instance().getAnimationBounds());

		
		/*
		 * Create a panel to house the tabular view. Again, decorate the 
		 * tabular view with a border and enable automatic activation of 
		 * scroll bars.
		 */
		JPanel tablePanel = new JPanel();
		tablePanel.setBorder(BorderFactory.createTitledBorder("Shape state"));
		JScrollPane scrollPaneForTable = new JScrollPane(_tabularView);
		scrollPaneForTable.setPreferredSize(new Dimension(810,150));
		tablePanel.add(scrollPaneForTable);
		
		/*
		 * Create a control panel housing buttons for creating and destroying 
		 * shapes, plus a combo box for selecting the type of shape to create.
		 */
		JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		controlPanel.setBorder(BorderFactory.createTitledBorder("Control panel"));
		_newShape = new JButton("New");
		_deleteShape = new JButton("Delete");
		_shapeTypes = new JComboBox<Class<? extends Shape>>(_comboBoxModel);
		
		/*
		 * Set up a custom renderer for the Combo box. Instead of displaying 
		 * the fully qualified names (that include packages) of Shape 
		 * subclasses, display onlt the class names (without the package 
		 * prefixes).
		 */
		_shapeTypes.setRenderer(new BasicComboBoxRenderer() {
			@Override
			public Component getListCellRendererComponent(@SuppressWarnings("rawtypes") JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				String className = value.toString().substring(value.toString().lastIndexOf('.') + 1);
				return super.getListCellRendererComponent(list, className, index, isSelected, cellHasFocus);
			}
		});
		
		
		controlPanel.add(_newShape);
		controlPanel.add(_deleteShape);
		controlPanel.add(_shapeTypes);
		
		JPanel top = new JPanel(new BorderLayout());
		top.add(animationPanel, BorderLayout.CENTER);
		top.add(treePanel, BorderLayout.WEST);
		top.add(tablePanel, BorderLayout.SOUTH);
		
		setLayout(new BorderLayout());
		add(top, BorderLayout.CENTER);
		add(controlPanel, BorderLayout.SOUTH);
	}
	
	
	/*
	 * Helper class to define a custom model for the Combo box. This 
	 * ComboBoxModel stores Shape subclasses that are acquired from 
	 * BounceConfig. 
	 */
	private class ShapeClassComboBoxModel extends
			DefaultComboBoxModel<Class<? extends Shape>> {

		public ShapeClassComboBoxModel() {
			List<Class<? extends Shape>> shapeClasses = BounceConfig.instance()
					.getShapeClasses();

			for (Class<? extends Shape> cls : shapeClasses) {
				addElement(cls);
			}
		}

	}
}
