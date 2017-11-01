package hr.fer.zemris.bf.qmc;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Optional;
import java.util.TreeSet;

import org.junit.Test;

public class MaskTest {

	@Test
	public void indexConstructorTest() {
		Mask mask = new Mask(7, 3, false);
		assertEquals(3, mask.countOfOnes());
	}
	
	@Test
	public void arrayConstructorTest() {
		Mask mask = new Mask(new byte[] {1, 0, 1}, new TreeSet<Integer>(Arrays.asList(5)), false);
		assertEquals(2, mask.countOfOnes());
	}
	
	@Test
	public void sizeTest() {
		Mask mask = new Mask(new byte[] {1, 0, 1}, new TreeSet<Integer>(Arrays.asList(5)), false);
		assertEquals(3, mask.size());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void combineSizeMismatch() {
		Mask mask1 = new Mask(7, 3, false);
		Mask mask2 = new Mask(new byte[] {1, 0, 1, 1}, new TreeSet<Integer>(Arrays.asList(11)), false);
		mask1.combineWith(mask2);
	}
	
	@Test
	public void combineUncombinable() {
		Mask mask1 = new Mask(7, 3, false);
		Mask mask2 = new Mask(new byte[] {0, 0, 1}, new TreeSet<Integer>(Arrays.asList(1)), false);
		assertEquals(false, mask1.combineWith(mask2).isPresent());
	}
	
	@Test
	public void combineTest() {
		Mask mask1 = new Mask(7, 3, false);
		Mask mask2 = new Mask(new byte[] {1, 0, 1}, new TreeSet<Integer>(Arrays.asList(5)), true);
		Optional<Mask> mask3 = mask1.combineWith(mask2);
		assertEquals(true, mask3.isPresent());		
		assertEquals(mask3.get().getIndexes(), new TreeSet<Integer>(Arrays.asList(5, 7)));
		assertEquals(false, mask3.get().isDontCare());
		
		assertEquals(1, mask3.get().getValueAt(0));
		assertEquals(2, mask3.get().getValueAt(1));
		assertEquals(1, mask3.get().getValueAt(2));
	}
	
	@Test
	public void combineDontCares() {
		Mask mask1 = new Mask(7, 3, true);
		Mask mask2 = new Mask(new byte[] {1, 0, 1}, new TreeSet<Integer>(Arrays.asList(5)), true);
		Optional<Mask> mask3 = mask1.combineWith(mask2);
		assertEquals(true, mask3.isPresent());		
		assertEquals(mask3.get().getIndexes(), new TreeSet<Integer>(Arrays.asList(5, 7)));
		assertEquals(true, mask3.get().isDontCare());
	}

}
