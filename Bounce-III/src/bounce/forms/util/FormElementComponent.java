package bounce.forms.util;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;


/**
 * FormElementComponent is an implementation of the FormElement interface. 
 * FormElementComponent inherits class JPanel, and so is a visual FormElement.
 * Instances of FormElementComponent are added to FormComponents (visual 
 * forms).
 * 
 * @see bounce.forms.util.FormElement 
 * 
 * @author Ian Warren
 *
 */
@SuppressWarnings("serial")
public abstract class FormElementComponent extends JPanel implements FormElement {
	/*
	 *  Each field within an FormElementComponent is represented by an entry 
	 *  in _fields and a corresponding entry in _fieldTypes. 
	 *  
	 *  For _fields, the key is field-name (a String) and the value is an
	 *  Object. For _fieldTypes, the key is field-name (a String) and the value 
	 *  is a Class.
	 */
	private Map<String,Object> _fields;
	private Map<String,Class<?>> _fieldTypes;

	/**
	 * Creates an empty FormElementComponent.
	 */
	public FormElementComponent() {
		_fields = new HashMap<String, Object>();
		_fieldTypes = new HashMap<String, Class<?>>();
	}
	
	@Override
	public <T> void addField(String name, T defaultValue, Class<T> type) {
		_fields.put(name, defaultValue);
		_fieldTypes.put(name,type);
	}
	
	@Override
	public void putFieldValue(String name, Object value) throws IllegalArgumentException {
		Class<?> fieldType = _fieldTypes.get(name);
		
		if(fieldType.isAssignableFrom(value.getClass())) {
			// Update the field's value.
			_fields.put(name, value);
		} else {
			// Field name is associated with a type that's incompatible with
			// value's type.
			throw new IllegalArgumentException();
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getFieldValue(Class<? extends T> type,  String name) {
		T result = null;
		
		Object obj = _fields.get(name);
		if((obj != null) && (type.isAssignableFrom(obj.getClass()))) {
			result = (T)obj;
		}

		return result;
	}
}
