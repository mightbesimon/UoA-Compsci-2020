package bounce.forms.util;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;

/**
 * 
 * A FormComponent displays a visual form into which users can enter 
 * information. 
 * 
 * A FormComponent contains two fundamental objects: a list of 
 * FormElementcomponents and a FormHandler. Each FormElementComponent is a 
 * group of GUI fields, and a FormComponent can contain multiple 
 * FormElementsComponents. 
 * 
 * For the Bounce application, for example, a FormComponent might contain two
 * FormElementsComponents: a ShapeFormElementComponent and a 
 * ColourFormElementComponent. The ShapeFormElementComponent would display 
 * fields common to all Shapes (location, width, height etc.), while the 
 * ColourFormElementComponent would allow the user to choose a colour for 
 * painting the shape. The combination of the two FormElements would allow a 
 * user to enter sufficient information for describing a Shape with colour.
 * 
 * The FormComponent's FormHandler is responsible for processing FormElement
 * field data. In the Bounce application, FormHandler implementations create
 * different kinds of Shapes based on form data.
 * 
 * @see bounce.forms.util.Form
 * @see bounce.forms.util.FormElementComponent
 * 
 * @author Ian Warren
 *
 */
@SuppressWarnings("serial")
public class FormComponent extends JDialog implements Form {
	private List<FormElementComponent> _elements; 
	private FormHandler _handler;
	private JButton _btnSubmit;         // Button to invoke the FormHandler.
	
	/**
	 * Creates a FormComponent instance without any FormElements or 
	 * FormHandler.
	 */
	public FormComponent() {
		_elements = new ArrayList<FormElementComponent>();
		
		_btnSubmit = new JButton("Submit");
		_btnSubmit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_handler.processForm(FormComponent.this);
				FormComponent.this.dispose();
			}
		});
		
		// Set GUI properties of the Form.
		setRootPaneCheckingEnabled(false);
		setLayout(new GridBagLayout());
		setModalityType(ModalityType.APPLICATION_MODAL);
	}
	
	/**
	 * Adds a FormElement to this Form.
	 */
	@Override
	public void addFormElement(FormElement element) throws IllegalArgumentException {
		/*
		 *  FormComponent is expected to store GUI ForElementComponents rather 
		 *  than the more general FormElements.
		 */
		if(element instanceof FormElementComponent) {
			_elements.add((FormElementComponent)element);
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * Sets the FormHandler of this Form.
	 */
	@Override
	public void setFormHandler(FormHandler handler) {
		_handler = handler;
	}
	
	/**
	 * Lays out the Form comprising its constituent FormElements and Submit
	 * button.
	 */
	@Override
	public void prepare() {
		// Clear any components previously added to the Form.
		removeAll();
		
		// Populate the Form.
		FormUtility formUtility = new FormUtility();
		for(FormElementComponent element : _elements) {
			formUtility.addLastField(element, this);
		}
		formUtility.addLastField(_btnSubmit, this);

		pack();
	}
	
	/**
	 * Returns the value for a specified field within the Form.
	 * @param type the type of the field whose value is requested.
	 * @param name the name of the field whose value is requested.
	 * @return the value of the required field, or null if there is no field
	 *         that matches the specified name and type.
	 */
	@Override
	public <T> T getFieldValue(Class<? extends T> type,  String name) {
		// Iterate over the Form's FormElements, searching for the field.
		for(FormElement element : _elements) {
			T fieldValue = element.getFieldValue(type, name);
		
			if(fieldValue != null) {
				// Field found, return its value.
				return fieldValue;
			}
		}
		return null;
	}
}
