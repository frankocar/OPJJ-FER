package hr.fer.zemris.java.raytracer;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * Determines the lightning of a predetermined scene using raycasting.
 * Multi threaded.
 * 
 * @author Franko Car
 *
 */
public class RayCasterParallel {
	
	/** Acceptable double error */
	private static final double DELTA = 1e-9;

	/**
	 * A class defining a job, a recursive action splittable into threads
	 * 
	 * @author Franko
	 *
	 */
	private static class Job extends RecursiveAction {
		
		/** Serial version UID */
		private static final long serialVersionUID = -4447095860179790916L;
		/** Job size treshold UID */
		static final int TRESHOLD = 16;
		/** Starting value of a current job */
		private int yMin; 
		/** Ending value of a current job */
		private int yMax;
		
		/** Scene */
		private Scene scene;
		/** Vantage point */
		private Point3D eye;
		/** Screen corner */
		private Point3D screenCorner;
		/** xAxis direction */
		private Point3D xAxis;
		/** yAxis direction */
		private Point3D yAxis;
		/** Screen width */
		private int width;
		/** Screen height */
		private int height;
		/** Horizontal size */
		private double horizontal;
		/** Vertical size */
		private double vertical;
		/** Red values array */
		private short[] red;
		/** Green values array */
		private short[] green;
		/** Blue values array */
		private short[] blue;
		/** Thread pool */
		private ForkJoinPool pool;
		
		
		/**
		 * A constructor
		 * 
		 * @param yMin Starting value
		 * @param yMax Ending value
		 * @param scene scene
		 * @param eye vantage point
		 * @param screenCorner screen corner
		 * @param xAxis xAxis direction
		 * @param yAxis yAxis direction
		 * @param width Screen width
		 * @param height Screen height
		 * @param horizontal Horizontal size
		 * @param vertical Vertical Size
		 * @param red Red values array
		 * @param green Green values array
		 * @param blue Blue values array
		 * @param pool Thread pool
		 */
		public Job(int yMin, int yMax, Scene scene, Point3D eye, Point3D screenCorner, Point3D xAxis, Point3D yAxis,
				int width, int height, double horizontal, double vertical, short[] red, short[] green, short[] blue,
				ForkJoinPool pool) {
			this.yMin = yMin;
			this.yMax = yMax;
			this.scene = scene;
			this.eye = eye;
			this.screenCorner = screenCorner;
			this.xAxis = xAxis;
			this.yAxis = yAxis;
			this.width = width;
			this.height = height;
			this.horizontal = horizontal;
			this.vertical = vertical;
			this.red = red;
			this.green = green;
			this.blue = blue;
			this.pool = pool;
		}
		
		@Override
		protected void compute() {
			if (yMax - yMin <= TRESHOLD) {
				computeDirect();
				return;
			}
			
			Job job1 = new Job(yMin, (yMin + yMax) / 2, scene, eye, screenCorner, xAxis, yAxis, width, height,
					horizontal, vertical, red, green, blue, pool);
			Job job2 = new Job((yMin + yMax) / 2, yMax, scene, eye, screenCorner, xAxis, yAxis, width, height,
					horizontal, vertical, red, green, blue, pool);
			invokeAll(job1, job2);
			
		}
		
		/**
		 * A method for final, direct computation of a given job
		 */
		private void computeDirect() {
			short[] rgb = new short[3];
			int offset = yMin * width;
			for (int y = yMin; y < yMax; y++) {
				for (int x = 0; x < width; x++) {
					Point3D screenPoint = screenCorner
							.add(xAxis.scalarMultiply((horizontal * x) / (double) (width - 1)))
							.sub(yAxis.scalarMultiply((vertical * y) / (double) (height - 1)));
					
					Ray ray = Ray.fromPoints(eye, screenPoint);
					
					tracer(scene, ray, rgb);
					
					red[offset] = rgb[0] > 255 ? 255 : rgb[0];
					green[offset] = rgb[1] > 255 ? 255 : rgb[1];
					blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
					offset++;
					
				}
			}
			
		}
		
		/**
		 * A tracer method to trace light reaching every pixel and 
		 * calculate color
		 * 
		 * @param scene
		 * @param ray
		 * @param rgb field of color values
		 */
		private void tracer(Scene scene, Ray ray, short[] rgb) {
			rgb[0] = 15;
			rgb[1] = 15;
			rgb[2] = 15;

			RayIntersection intersect = findClosestIntersection(scene, ray);
			if (intersect == null) {
				rgb[0] = 0;
				rgb[1] = 0;
				rgb[2] = 0;
				return;
			} 
			
			determineColorFor(intersect, ray, scene, rgb);

		}

		/**
		 * A method to determine color at every intersect
		 * 
		 * @param intersect
		 * @param ray
		 * @param scene
		 * @param rgb
		 */
		private void determineColorFor(RayIntersection intersect, Ray ray, Scene scene, short[] rgb) {
			for (LightSource ls : scene.getLights()) {
				Ray r = Ray.fromPoints(ls.getPoint(), intersect.getPoint());
				RayIntersection i = findClosestIntersection(scene, r);
				
				if (i != null) {
					double dist1 = ls.getPoint().sub(intersect.getPoint()).norm();
					double dist2 = ls.getPoint().sub(i.getPoint()).norm();
					
					if (dist2 + DELTA >= dist1) {
						addReflective(ls, ray, rgb, i);
						addDiffuse(ls, rgb, i);
					}
				}
			}
		}
		
		/**
		 * A method to add diffuse color component
		 * 
		 * @param ls LightSource
		 * @param rgb array of color values
		 * @param i intersect
		 */
		private void addDiffuse(LightSource ls, short[] rgb, RayIntersection i) {

			Point3D normal = i.getNormal();
			Point3D light = ls.getPoint().sub(i.getPoint()).normalize();

			double value = normal.scalarProduct(light);
			value = value > 0 ? value : 0;

			rgb[0] += (short) (ls.getR() * i.getKdr() * value);
			rgb[1] += (short) (ls.getG() * i.getKdg() * value);
			rgb[2] += (short) (ls.getB() * i.getKdb() * value);
		}
		
		/**
		 *  A method to add reflection color component
		 * 
		 * @param ls LightSource
		 * @param rgb array of color values
		 * @param i intersect
		 * @param ray ray to check
		 */
		private void addReflective(LightSource ls, Ray ray, short[] rgb, RayIntersection i) {

			Point3D light = ls.getPoint().sub(i.getPoint()).normalize();
			Point3D reflect = i.getNormal().normalize()
					.scalarMultiply(2 * light.scalarProduct(i.getNormal()) / i.getNormal().norm()).sub(light)
					.normalize();

			double vector = reflect.scalarProduct(ray.start.sub(i.getPoint()).normalize());
			double value = Math.pow(vector, i.getKrn());
			value = value > 0 ? value : 0;

			if (vector >= 0) {
				rgb[0] += (short) (ls.getR() * i.getKrr() * value);
				rgb[1] += (short) (ls.getG() * i.getKrg() * value);
				rgb[2] += (short) (ls.getB() * i.getKrb() * value);
			}
		}
		
		/**
		 * A method to find the closest intersection of objects on scene and a given ray
		 * 
		 * @param scene
		 * @param ray
		 * @return closest intersection
		 */
		private RayIntersection findClosestIntersection(Scene scene, Ray ray) {
			RayIntersection intersect = null;
			for (GraphicalObject object : scene.getObjects()) {
				RayIntersection tmp = object.findClosestRayIntersection(ray);
				
				if (tmp == null) {
					continue;
				}
				
				if (intersect == null || tmp.getDistance() < intersect.getDistance()) {
					intersect = tmp;
				}
			}
			
			return intersect;
		}
	}
	
	/**
	 * Main method
	 * 
	 * @param args none required
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), 
				new Point3D(10, 0, 0), 
				new Point3D(0, 0, 0),
				new Point3D(0, 0, 10),
				20, 20);
	}

	/**
	 * A method that creates and returns a new implementation of IRayTracerProducer
	 * appropriate for use on a given scene 
	 * 
	 * @return appropriate IRayTracerProducer
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		
		return new IRayTracerProducer() {
			
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical,
					int width, int height, long requestNo, IRayTracerResultObserver observer) {
				
				System.out.println("Započinjem izračune...");
				long start = System.currentTimeMillis();
				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];
				
				Point3D zAxis = view.sub(eye).normalize();
				
				Point3D vectorY = zAxis.scalarMultiply(zAxis.scalarProduct(viewUp.normalize()));
				vectorY = viewUp.sub(vectorY);
				Point3D yAxis = vectorY.normalize();
				
				Point3D xAxis = zAxis.vectorProduct(yAxis).normalize();
				
				Point3D horizontalVec = xAxis.scalarMultiply(horizontal / 2);
				Point3D verticalVec = yAxis.scalarMultiply(vertical / 2);
				Point3D screenCorner = view.sub(horizontalVec).add(verticalVec);
				
				Scene scene = RayTracerViewer.createPredefinedScene();
				
				ForkJoinPool pool = new ForkJoinPool();
				Job job = new Job(0, height, scene, eye, screenCorner, xAxis, yAxis, width, height, horizontal,
						vertical, red, green, blue, pool);
				pool.invoke(job);
				
				System.out.println("Izračuni gotovi...");
				long end = System.currentTimeMillis();
				System.out.println("Vrijeme[ms]: " + (end - start));
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}

		};
	}

}
