package hr.fer.zemris.java.custom.scripting.exec;

/**
 * An object to wrap the values to be stored in a stack. It will store
 * an object of any kind, and allow additional operations on object that 
 * can be interpreted as a numeric value. If such an operation is tried
 * upon a non-numeric type, an exception will be thrown. It will interpret 
 * null as integer with the value of 0. The result will be an integer if
 * both values are integers and doubles otherwise.
 * 
 * @author Franko Car
 *
 */
public class ValueWrapper {

	/**
	 * Current value stored
	 */
	private Object value;
	/**
	 * Type of the value stored
	 */
	private ValueType type;

	/**
	 * A constructor
	 * 
	 * @param value Value to be stored
	 */
	public ValueWrapper(Object value) {
		super();
		this.value = value;
		this.type = findType(value);
	}
	
	/**
	 * A getter
	 * 
	 * @return Object Stored value 
	 */
	public Object getValue() {
		return value;
	}
	
	/**
	 * A setter for value
	 * 
	 * @param value New value
	 */
	public void setValue(Object value) {
		this.value = value;
		this.type = findType(value);
	}
	
	/**
	 * A method to add another object to the stored value
	 * 
	 * @param incValue Another object
	 * @throws IllegalArgumentException When the operation is unknown or division by 0 is attempted.
	 */
	public void add(Object incValue) {
		value = genericOperation(incValue, Operation.ADD);
	}
		
	/**
	 * A method to subtract another object to the stored value
	 * 
	 * @param decValue Another object
	 * @throws IllegalArgumentException When the operation is unknown or division by 0 is attempted.
	 */
	public void subtract(Object decValue) {
		value = genericOperation(decValue, Operation.SUBTRACT);
	}
	
	/**
	 * A method to multiply another object with the stored value
	 * 
	 * @param mulValue Another object
	 * @throws IllegalArgumentException When the operation is unknown or division by 0 is attempted.
	 */
	public void multiply(Object mulValue) {
		value = genericOperation(mulValue, Operation.MULTIPLY);
	}
	
	/**
	 * A method to divide stored value with another object
	 * 
	 * @param divValue Another object
	 * @throws IllegalArgumentException When the operation is unknown or division by 0 is attempted.
	 */
	public void divide(Object divValue) {
		value = genericOperation(divValue, Operation.DIVIDE);
	}
	
	/**
	 * A method to add compare object to the stored value.
	 * Will return 0 if objects are equal, negative value if
	 * the fist value is smaller and positive if larger.
	 * 
	 * @param withValue Another object
	 * @return Comparison value
	 * @throws IllegalArgumentException When the operation is unknown or division by 0 is attempted.
	 */
	public int numCompare(Object withValue) {
		return (int) genericOperation(withValue, Operation.COMPARE);
	}
	
	/**
	 * A method to perform an operation on a given object and a stored value 
	 * 
	 * @param secondValue Second object
	 * @param operation Operation to perform
	 * @return Value returned by the operation
	 * @throws IllegalArgumentException When the operation is unknown or division by 0 is attempted.
	 */
	private Object genericOperation(Object secondValue, Operation operation) throws IllegalArgumentException {
		ValueType secondType = findType(secondValue);
		if (secondType == ValueType.UNKNOWN || type == ValueType.UNKNOWN) {
			throw new RuntimeException("Value is not a numeric value: " + (type == ValueType.UNKNOWN ? value : secondValue));
		}
		
		if (type != ValueType.INTEGER || secondType != ValueType.INTEGER) {
			Double double1 = doubleValue(value);
			Double double2 = doubleValue(secondValue);
			
			return perform(double1, double2, operation);
		}
		
		Integer int1 = integerValue(value);
		Integer int2 = integerValue(secondValue);
		
		return perform(int1, int2, operation);
	}
	
	/**
	 * A method to perform an operation on two integers
	 * 
	 * @param int1 First value
	 * @param int2 Second value
	 * @param operation Operation to perform
	 * @return Object value of operation
	 * @throws IllegalArgumentException When the operation is unknown or division by 0 is attempted.
	 */
	private Object perform(Integer int1, Integer int2, Operation operation) throws IllegalArgumentException {
		switch (operation) {
		case ADD:
			return int1 + int2;
		case SUBTRACT:
			return int1 - int2;
		case MULTIPLY:
			return int1 * int2;
		case DIVIDE:
			if (int2 == 0) {
				throw new IllegalArgumentException("Division by 0 is not defined");
			}
			return int1 / int2;
		case COMPARE:
			return int1.compareTo(int2);
		default:
			throw new IllegalArgumentException("Unknown operation");
		}
	}

	/**
	 * A method to perform an operation on two doubles
	 * 
	 * @param double1 First value
	 * @param double2 Second value
	 * @param operation Operation to perform
	 * @return Object value of operation
	 * @throws IllegalArgumentException When the operation is unknown or division by 0 is attempted.
	 */
	private Object perform(Double double1, Double double2, Operation operation) throws IllegalArgumentException {
		switch (operation) {
		case ADD:
			return double1 + double2;
		case SUBTRACT:
			return double1 - double2;
		case MULTIPLY:
			return double1 * double2;
		case DIVIDE:
			if (double2 == 0) {
				throw new IllegalArgumentException("Division by 0 is not defined");
			}
			return double1 / double2;
		case COMPARE:
			return double1.compareTo(double2);
		default:
			throw new IllegalArgumentException("Unknown operation");
		}
	}
	
	/**
	 * A method to return the value of an integer object
	 * 
	 * @param value Object to check
	 * @return Integer value
	 */
	private Integer integerValue(Object value) {
		if (value instanceof Integer) {
			return (Integer) value;
		}
		
		return Integer.valueOf(value == null ? 0 : Integer.parseInt(value.toString()));
	}
	
	/**
	 * A method to return the value of a double object
	 * 
	 * @param value Object to check
	 * @return Double value
	 */
	private Double doubleValue(Object value) {
		if (value instanceof Double) {
			return (Double) value;
		}
		
		return value == null ? 0.f : Double.parseDouble(value.toString());
	}
	
	/**
	 * A method to find the type of a given object
	 * 
	 * @param value Object
	 * @return ValueType type
	 */
	private ValueType findType(Object value) {
		if (value == null || isInteger(value)) {
			return ValueType.INTEGER;
		}
		
		if (isDouble(value)) {
			return ValueType.DOUBLE;
		}
		
		return ValueType.UNKNOWN;
	}
	
	/**
	 * A method to check if a value is usable as a double
	 * 
	 * @param value Object to check
	 * @return True if double, false otherwise
	 */
	private boolean isDouble(Object value) {
		if (value instanceof Double) {
			return true;
		}
		
		try {
			Double.parseDouble(value.toString());
		} catch (NumberFormatException e) {
			return false;
		}
		
		return true;
	}

	/**
	 * A method to check if a value is usable as an integer
	 * 
	 * @param value Object to check
	 * @return True if integer, false otherwise
	 */
	private boolean isInteger(Object value) {
		if (value instanceof Integer) {
			return true;
		}
		
		try {
			Integer.parseInt(value.toString());
		} catch (NumberFormatException e) {
			return false;
		}
		
		return true;
	}

	/**
	 * An enum with possible value types
	 * 
	 * @author Franko Car
	 */
	private enum ValueType {
		/**
		 * Object is usable as an integer
		 */
		INTEGER,
		/**
		 * Object is usable as a double
		 */
		DOUBLE,
		/**
		 * Object is not a numeric type
		 */
		UNKNOWN;
	}
	
	/**
	 * An enum with possible numerical operations.
	 * 
	 * @author Franko Car
	 */
	private enum Operation {
		/**
		 * Perform addition
		 */
		ADD,
		/**
		 * Perform subtraction
		 */
		SUBTRACT, 
		/**
		 * Perform division
		 */
		DIVIDE, 
		/**
		 * Perform multiplication
		 */
		MULTIPLY, 
		/**
		 * Compare values
		 */
		COMPARE;
	}
	
}
