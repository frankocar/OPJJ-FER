package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Implementation of FractalProducer interface for Newton-Raphson iteration-based 
 * method.
 * 
 * @author Franko Car
 *
 */
public class FractalProducerImpl implements IFractalProducer {

	/** Distance to root */
	private static final double ROOT_DISTANCE = 0.002;
	/** Convergence treshold */
	private static final double CONVERGENCE_TRESHOLD = 0.001;
	/** Maximum number of iterations */
	private static final int MAX_ITERATIONS = 256;
	
	/** Polynomial in ComplexRootedPolynomial form */
	private ComplexRootedPolynomial rootedPolynomial;
	/** Polynomial in ComplexPolynomial form */
	private ComplexPolynomial polynomial;
	/** Pool for executor service */
	private ExecutorService pool;
	/** Data array for each pixel */
	private short[] data;
	
	/**
	 * A constructor
	 * 
	 * @param rootedPolynomial polynomial to draw
	 */
	public FractalProducerImpl(ComplexRootedPolynomial rootedPolynomial) {
		if (rootedPolynomial == null) {
			throw new IllegalArgumentException("Polynomial cant be null");
		}
		
		this.rootedPolynomial = rootedPolynomial;
		polynomial = rootedPolynomial.toComplexPolynom();
		
		pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), (r) -> {
			Thread t = new Thread(r);
			t.setDaemon(true);
			return t;
		});
	}
	
	@Override
	public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height, long requestNo,
			IFractalResultObserver observer) {

		data = new short[width * height];
		
		System.out.println("Calculation start");
		long start = System.currentTimeMillis();
		
		final int numOfJobs = 8 * Runtime.getRuntime().availableProcessors();
		final int jobSize = height/numOfJobs;
		
		List<Future<Void>> results = new ArrayList<>();
		
		for (int i = 0; i < numOfJobs; i++) {
			int yMin = i * jobSize;
			int yMax = (i + 1) * jobSize - 1;
			
			if (i == numOfJobs - 1) {
				yMax = height - 1;
			}
			
			Job job = new Job(yMin, yMax, yMin * width, reMax, reMin, imMax, imMin, width, height);
			results.add(pool.submit(job));
		}
		
		for (Future<Void> result : results) {
			try {
				result.get();
			} catch (InterruptedException | ExecutionException e) {
				System.err.println("Job interrupted");
			} 
		}
		
		long end = System.currentTimeMillis();
		System.out.println("Time required: " + (end - start));
		System.out.println("Calculation over");
		observer.acceptResult(data, (short) (polynomial.order() + 1), requestNo);

	}
	
	/**
	 * A job for each thread to work on
	 * 
	 * @author Franko Car
	 *
	 */
	private class Job implements Callable<Void> {

		/** Minimum y value */
		private int yMin;
		/** Maximum y value */
		private int yMax;
		/** Data array offset */
		private int offset;
		/** Minimum real value */
		private double reMin;
		/** Maximum real value */
		private double reMax;
		/** Minimum imaginary value */
		private double imMin;
		/** Maximum imaginary value */
		private double imMax;
		/** Total width */
		private int width;
		/** Total heigth */
		private int height;
		
		/**
		 * A constructor
		 * 
		 * @param yMin
		 * @param yMax
		 * @param offset
		 * @param reMax
		 * @param reMin
		 * @param imMax
		 * @param imMin
		 * @param width
		 * @param height
		 */
		public Job(int yMin, int yMax, int offset, double reMax, double reMin, double imMax, double imMin, int width, int height) {
			super();
			this.yMin = yMin;
			this.yMax = yMax;
			this.offset = offset;
			this.reMax = reMax;
			this.reMin = reMin;
			this.imMax = imMax;
			this.imMin = imMin;
			this.width = width;
			this.height = height;
		}
		
		@Override
		public Void call() throws Exception {	
			
			for (int y = yMin; y <= yMax; y++) {
				for (int x = 0; x < width; x++) {
					double real = x / (width - 1.0) * (reMax - reMin) + reMin;
					double imaginary = (height - 1.0 - y) / (height - 1) * (imMax - imMin) + imMin;
					
					Complex first = new Complex(real, imaginary);
					Complex second;
					
					double module = 0;
					int iter = 0;
					
					do {
						second = first.sub(polynomial.apply(first).divide(polynomial.derive().apply(first)));
						module = second.sub(first).module();
						first = second;
						iter++;
					} while (module > CONVERGENCE_TRESHOLD && iter < MAX_ITERATIONS);
					
					int index = rootedPolynomial.indexOfClosestRootFor(second, ROOT_DISTANCE);
					
					if (index == - 1) {
						data[offset++] = 0;
					} else {
						data[offset++] = (short) index;
					}
				}
			}
			
			return null;
		}
		
	}

}
