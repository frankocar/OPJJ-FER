package hr.fer.zemris.math;

/**
 * A class representing a 3D vector.
 * Every object is immutable, every method will return a new object.
 * 
 * @author Franko Car
 */
public class Vector3 {

	/** x component */
	private double x;
	/** y component */
	private double y;
	/** z component */
	private double z;
	
	/**
	 * A constructor taking in 3 components
	 * 
	 * @param x component
	 * @param y component
	 * @param z component
	 */
	public Vector3(double x, double y, double z) {
		this.x = x; 
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Returns the vector norm
	 * 
	 * @return norm
	 */
	public double norm() {
		return Math.sqrt(x*x + y*y + z*z);
	}
	
	/**
	 * Returns the normalized vector
	 * 
	 * @return normalized vector
	 */
	public Vector3 normalized() {
		return new Vector3(x / this.norm(), y / this.norm(), z / this.norm());
	}
	
	/**
	 * Adds two vectors
	 * 
	 * @param other vector
	 * @return resulting vector
	 */
	public Vector3 add(Vector3 other) {
		if (other == null) {
			throw new IllegalArgumentException("Can't do operations on a null object");
		}
		
		return new Vector3(this.x + other.x, this.y + other.y, this.z + other.z);
	}
	
	/**
	 * Subtracts two vectors
	 * 
	 * @param other vector
	 * @return resulting vector
	 */
	public Vector3 sub(Vector3 other) {
		if (other == null) {
			throw new IllegalArgumentException("Can't do operations on a null object");
		}
		
		return new Vector3(this.x - other.x, this.y - other.y, this.z - other.z);
	}
	
	/**
	 * Dot product of two vectors
	 * 
	 * @param other vector
	 * @return resulting vector
	 */
	public double dot(Vector3 other) {
		if (other == null) {
			throw new IllegalArgumentException("Can't do operations on a null object");
		}
		
		return this.x * other.x + this.y * other.y + this.z * other.z;
	}
	
	/**
	 * Cross product of two vectors
	 * 
	 * @param other vector
	 * @return resulting vector
	 */
	public Vector3 cross(Vector3 other) {
		if (other == null) {
			throw new IllegalArgumentException("Can't do operations on a null object");
		}
		
		double productX = this.y * other.z - this.z * other.y;
		double productY = this.z * other.x - this.x * other.z;
		double productZ = this.x * other.y - this.y * other.x;
		
		return new Vector3(productX, productY, productZ);
	}
	
	/**
	 * Scales the factor by factor s
	 * 
	 * @param s factor
	 * @return resulting vector
	 */
	public Vector3 scale(double s) {
		return new Vector3(s * x, s * y, s * z);
	}
	
	/**
	 * Calculates the cosine of an angle between two vectors
	 * 
	 * @param other vector
	 * @return cosine of the angle 
	 */
	public double cosAngle(Vector3 other) {
		if (other == null) {
			throw new IllegalArgumentException("Can't do operations on a null object");
		}
		
		double numerator = Math.abs(this.dot(other));
		double denominator = this.norm() * other.norm();
		
		return numerator/denominator;
	}
	
	/**
	 * x component getter
	 * 
	 * @return x component
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * y component getter
	 * 
	 * @return y component
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * z component getter
	 * 
	 * @return z component
	 */
	public double getZ() {
		return z;
	}
	
	/**
	 * Returns vector components as an array of 3 doubles
	 * 
	 * @return resulting array
	 */
	public double[] toArray() {
		return new double[] {x, y, z};
	}
	
	@Override
	public String toString() {
		return String.format("(%.6f, %.6f, %.6f)", x, y, z);
	}
}
