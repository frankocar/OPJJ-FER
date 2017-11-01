package hr.fer.zemris.java.raytracer.model;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;

/**
 * A model of Sphere as a {@link GraphicalObject}
 * 
 * @author Franko Car
 *
 */
public class Sphere extends GraphicalObject {

	/** Center point */
	private Point3D center;
	/** Sphere radius */
	private double radius;
	/** Coefficient for diffuse component for red color */
	private double kdr;
	/** Coefficient for diffuse component for green color */
	private double kdg;
	/** Coefficient for diffuse component for blue color */
	private double kdb;
	/** Coefficient for reflective component for red color */
	private double krr;
	/** Coefficient for reflective component for green color */
	private double krg;
	/** Coefficient for reflective component for blue color */
	private double krb;
	/** Coefficient n for reflective component */
	private double krn;
	
	/**
	 * A constructor
	 * 
	 * @param center Center point
	 * @param radius Sphere radius  
	 * @param kdr Coefficient for diffuse component for red color
	 * @param kdg Coefficient for diffuse component for green color
	 * @param kdb Coefficient for diffuse component for blue color
	 * @param krr Coefficient for reflective component for red color
	 * @param krg Coefficient for reflective component for green color
	 * @param krb Coefficient for reflective component for blue color 
	 * @param krn Coefficient n for reflective component
	 * @throws IllegalArgumentException If a value is invalid
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr, double krg, double krb, double krn) {
		if (center == null) {
			throw new IllegalArgumentException("Center can't be null");
		}		
		if (radius < 0) {
			throw new IllegalArgumentException("Radius can't be negative");
		}
		if (kdr < 0 || kdr > 1 || kdg < 0 || kdg > 1 || kdb < 0 || kdb > 1) {
			throw new IllegalArgumentException("Invalid diffuse values.");
		}
		if (krr < 0 || krr > 1 || krg < 0 || krg > 1 || krb < 0 || krb > 1) {
			throw new IllegalArgumentException("Invalid reflect values.");
		}
		
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {
		if (ray == null) {
			throw new IllegalArgumentException("Ray can't be null.");
		}
		
		Point3D distToCenter = ray.start.sub(center);
		
		double a = ray.direction.scalarProduct(ray.direction);
		double b = ray.direction.scalarMultiply(2).scalarProduct(distToCenter);
		double c = distToCenter.scalarProduct(distToCenter) - radius * radius;
		
		double d = b * b - 4 * a * c;
		if (d < 0) {
			return null;
		}
		d = Math.sqrt(d);
		
		double intersect1 = (-b + d) / (2 * a);
		double intersect2 = (-b - d) / (2 * a);
		
		if (intersect1 <= 0 && intersect2 <= 0) { //both are behind
			return null;
		}
		
		double dist = Math.min(intersect1, intersect2); //take the one in front
		Point3D intersect = ray.start.add(ray.direction.scalarMultiply(dist));
		
		return new RayIntersection(intersect, dist, intersect.sub(ray.start).norm() > radius) {
			
			@Override
			public Point3D getNormal() {
				return getPoint().sub(center).normalize();
			}
			
			@Override
			public double getKrr() {
				return krr;
			}
			
			@Override
			public double getKrn() {
				return krn;
			}
			
			@Override
			public double getKrg() {
				return krg;
			}
			
			@Override
			public double getKrb() {
				return krb;
			}
			
			@Override
			public double getKdr() {
				return kdr;
			}
			
			@Override
			public double getKdg() {
				return kdg;
			}
			
			@Override
			public double getKdb() {
				return kdb;
			}
		};
	}
}
