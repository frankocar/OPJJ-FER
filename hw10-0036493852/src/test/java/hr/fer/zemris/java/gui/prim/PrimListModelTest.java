package hr.fer.zemris.java.gui.prim;

import static org.junit.Assert.assertEquals;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import org.junit.Test;

public class PrimListModelTest {

	@Test
	public void isPrimeTest1() {
		assertEquals(true, PrimListModel.isPrime(2));		
	}
	
	@Test
	public void isPrimeTest2() {
		assertEquals(true, PrimListModel.isPrime(9973));		
	}
	
	@Test
	public void isPrimeTest3() {
		assertEquals(true, PrimListModel.isPrime(2_147_483_647));		
	}
	
	@Test
	public void isPrimeTest4() {
		assertEquals(false, PrimListModel.isPrime(4));		
	}
	
	@Test
	public void isPrimeTest5() {
		assertEquals(false, PrimListModel.isPrime(120_000_000));		
	}
	
	@Test
	public void nextTest1() {
		PrimListModel model = new PrimListModel();
		model.next();
		assertEquals(2, model.getPrevious());
	}
	
	@Test
	public void nextTest2() {
		PrimListModel model = new PrimListModel();
		model.next();
		model.next();
		model.next();
		model.next();
		model.next();
		assertEquals(11, model.getPrevious());
	}
	
	@Test
	public void nextTest3() {
		PrimListModel model = new PrimListModel();
		for (int i = 0; i < 5000; i++) {
			model.next();
		}
		assertEquals(48611, model.getPrevious());
	}
	
	private boolean change = false;
	@Test
	public void listenerTest() {		
		PrimListModel model = new PrimListModel();
		model.addListDataListener(new ListDataListener() {			
			@Override
			public void intervalRemoved(ListDataEvent e) {
			}
			
			@Override
			public void intervalAdded(ListDataEvent e) {
				change = true;				
			}
			
			@Override
			public void contentsChanged(ListDataEvent e) {
			}
		});
		assertEquals(false, change);
		model.next();
		assertEquals(true, change);
	}

}
