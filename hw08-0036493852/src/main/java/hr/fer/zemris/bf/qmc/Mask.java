package hr.fer.zemris.bf.qmc;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import hr.fer.zemris.bf.utils.Util;

/**
 * A class storing binary masks.
 * 
 * @author Franko Car
 *
 */
public class Mask {

	/** Mask itself */
	private byte[] mask;
	/** Whether the mask is a don't care value */
	private boolean dontCare;
	/** Whether the mask is a combined with another mask */
	private boolean combined;
	/** Indexes of contained minterms */
	private Set<Integer> indexes;
	/** Hash value */
	private int hash;
	
	/**
	 * A constructor taking in the minterm index, number of variables and a boolean
	 * value marking it a don't care
	 * 
	 * @param index minterm
	 * @param numberOfVariables Number of variables
	 * @param dontCare don't care value
	 * @throws IllegalArgumentException if number of variables is negative
	 */
	public Mask(int index, int numberOfVariables, boolean dontCare) {
		if (numberOfVariables <= 0) {
			throw new IllegalArgumentException("Number of variables must be a positive value");
		}
		
		this.mask = Util.indexToByteArray(index, numberOfVariables);
		this.dontCare = dontCare;
		
		Set<Integer> temp = new TreeSet<>();		
		temp.add(index);
		
		this.indexes = Collections.unmodifiableSet(temp);		
		this.hash = Arrays.hashCode(mask);		
		
	}
	
	/**
	 * A constructor taking in a mask in a form of a byte array, set of integers
	 * denoting contained minterms and a boolean value marking it a don't care
	 * 
	 * @param values mask in a form of a byte array
	 * @param indexes set of integers denoting contained minterms
	 * @param dontCare don't care value
	 * @throws IllegalArgumentException if a given mask is null or there aren't any indexes
	 */
	public Mask(byte[] values, Set<Integer> indexes, boolean dontCare) {
		if (values == null) {
			throw new IllegalArgumentException("Given mask mustn't be null");
		}
		
		if (indexes == null || indexes.isEmpty()) {
			throw new IllegalArgumentException("Set of indices can't be empty");
		}
		
		this.mask = Arrays.copyOf(values, values.length);
		this.indexes = Collections.unmodifiableSet(new TreeSet<>(indexes));
		this.dontCare = dontCare;
	}
	
	/**
	 * A getter for combined state
	 * 
	 * @return true if combined
	 */
	public boolean isCombined() {
		return combined;
	}
	
	/**
	 * A setter for combined mark
	 * 
	 * @param combined true if combined
	 */
	public void setCombined(boolean combined) {
		this.combined = combined;
	}
	
	/**
	 * A getter for don't care state
	 * 
	 * @return true if don't care
	 */
	public boolean isDontCare() {
		return dontCare;
	}
		
	/**
	 * A getter for set of indexes
	 * 
	 * @return Set of indexes
	 */
	public Set<Integer> getIndexes() {
		return indexes;
	}
	
	/**
	 * A method to count the number of ones in a mask
	 * 
	 * @return Number of ones
	 */
	public int countOfOnes() {
		int count = 0;
		for (byte x : mask) {
			if (x == 1) {
				count++;
			}
		}
		return count;
	}
	
	/**
	 * Mask size
	 * 
	 * @return size of a mask
	 */
	public int size() {
		return mask.length;
	}
	
	/**
	 * A getter for a value at a desired position
	 * 
	 * @param position
	 * @return value
	 * @throws IllegalArgumentException if the requested index is out of range
	 */
	public byte getValueAt(int position) {
		if (position < 0 || position > mask.length - 1) {
			throw new IllegalArgumentException("Requested index is out of range");
		}
		
		return mask[position];
	}
	
	/**
	 * A method to combine two masks. It will return a new Optional object
	 * with te combination if it exists, and an empty one if it doesn't.
	 * 
	 * @param other another mask
	 * @return Optional<Mask> combination
	 * @throws IllegalArgumentException if the given mask is null or different in size
	 */
	public Optional<Mask> combineWith(Mask other) {
		if (other == null) {
			throw new IllegalArgumentException("Given mask is null");
		}
		
		if (other.size() != this.size()) {
			throw new IllegalArgumentException("Size mismatch");
		}
		
		int differences = 0;
		int diffIndex = 0;
		for (int i = 0; i < mask.length; i++) {
			if (this.mask[i] != other.mask[i] ) {
				differences++;
				diffIndex = i;
			}
		}
		
		if (differences != 1) {
			return Optional.empty();
		}
				
		byte[] newMask = Arrays.copyOf(mask, mask.length);
		newMask[diffIndex] = 2;
		
		Set<Integer> newIndexes = new TreeSet<>();
		newIndexes.addAll(this.indexes);
		newIndexes.addAll(other.indexes);
		
		return Optional.of(new Mask(newMask, newIndexes, this.dontCare && other.dontCare));
		
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (byte x : mask) {
			sb.append(x == 2 ? "-" : x);
		}		
		sb.append(" ");
		sb.append(dontCare ? "D" : ".");
		sb.append(" ");
		sb.append(combined ? "*" : " ");
		sb.append(" ");
		sb.append(indexes.toString());
		return sb.toString();
	}
	
	@Override
	public int hashCode() {
		return hash;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Mask))
			return false;
		Mask other = (Mask) obj;		
		if (this.hash != other.hash) {
			return false;
		}		
		if (!Arrays.equals(mask, other.mask))
			return false;
		return true;
	}	
}
