package hr.fer.zemirs.java.hw01;

import static org.junit.Assert.*;

import org.junit.Test;

import hr.fer.zemris.java.hw01.UniqueNumbers;
import hr.fer.zemris.java.hw01.UniqueNumbers.*;

/**
 * Tests for UniqueNumbers class
 * 
 * @author Franko Car
 *
 */
public class UniqueNumbersTest {

	@Test
	public void addSizeContainsTest() {
		TreeNode root = null;
		root = UniqueNumbers.addNode(root, 1);
		root = UniqueNumbers.addNode(root, 3);
		root = UniqueNumbers.addNode(root, 8);
		root = UniqueNumbers.addNode(root, 45);
		root = UniqueNumbers.addNode(root, 54);
		root = UniqueNumbers.addNode(root, 3);
		root = UniqueNumbers.addNode(root, 8);
				
		if(UniqueNumbers.treeSize(root) != 5) {
			fail(String.format("Size should be 5, it's %d", UniqueNumbers.treeSize(root)));
		} 
		
		if(!UniqueNumbers.containsValue(root, 8)) {
			fail("Added value is not in the BST");
		}
	}
	
	@Test
	public void addMultiple() {
		TreeNode root = null;
		root = UniqueNumbers.addNode(root, 1);
		root = UniqueNumbers.addNode(root, 1);
		root = UniqueNumbers.addNode(root, 1);
		root = UniqueNumbers.addNode(root, 1);
		
		if(UniqueNumbers.treeSize(root) != 1) {
			fail(String.format("Size should be 1, it's %d", UniqueNumbers.treeSize(root)));
		}
	}
	
	@Test
	public void largeTest() {
		TreeNode root = null;
		
		for(int i = 0; i < 1000; i++) {
			root = UniqueNumbers.addNode(root, i);
		}
		
		if(UniqueNumbers.treeSize(root) != 1000) {
			fail(String.format("Size should be 1000, it's %d", UniqueNumbers.treeSize(root)));
		}
	}
	
	@Test
	public void checkContains() {
		TreeNode root = null;
		root = UniqueNumbers.addNode(root, 1);
		root = UniqueNumbers.addNode(root, 3);
		root = UniqueNumbers.addNode(root, 5);
		root = UniqueNumbers.addNode(root, 7);
	
		if(!UniqueNumbers.containsValue(root, 1)) {
			fail("Added value is not in the BST");
		}
		
		if(!UniqueNumbers.containsValue(root, 3)) {
			fail("Added value is not in the BST");
		}
		
		if(!UniqueNumbers.containsValue(root, 5)) {
			fail("Added value is not in the BST");
		}
		
		if(!UniqueNumbers.containsValue(root, 7)) {
			fail("Added value is not in the BST");
		}
		
		if(UniqueNumbers.containsValue(root, 0)) {
			fail("Value 0 shouldn't be in the BST");
		}
		
		if(UniqueNumbers.containsValue(root, 6)) {
			fail("Value 6 shouldn't be in the BST");
		}
	}
	

}
