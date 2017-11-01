package hr.fer.zemris.java.hw05.observer1;
import org.junit.Test;

import hr.fer.zemris.java.hw05.observer1.IntegerStorage;
import hr.fer.zemris.java.hw05.observer1.IntegerStorageObserver;

public class IntegerStorageTest {

	@Test
	public void emptyListRemoveTest() {
		IntegerStorage is = new IntegerStorage(1);
		is.removeObserver(new IntegerStorageObserver() {			
			@Override
			public void valueChanged(IntegerStorage istorage) {
				System.out.println("Does it matter?");
				
			}
		});
	}

}
