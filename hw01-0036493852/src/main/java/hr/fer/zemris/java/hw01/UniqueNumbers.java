package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * A program that implements a simple Binary Search Tree and fills it
 * with data read from the standard input.
 * 
 * @author Franko Car
 *
 */
public class UniqueNumbers {

	/**
	 * A simple class used as a structure to store 
	 * tree nodes.
	 */
	static public class TreeNode {
		TreeNode left;
		TreeNode right;
		int value;
	}
	
	/**
	 * Main method that starts the program and reads user input.
	 * Doesn't require any arguments.
	 */
	public static void main(String[] args) {
		TreeNode root = null;
		
		Scanner sc = new Scanner(System.in);
		while(true){
			System.out.print("Unesite broj > ");
			if(sc.hasNextInt()) {
				int value = sc.nextInt();
				if(!containsValue(root, value)) {
					root = addNode(root, value);
					System.out.println("Dodano.");	
				} else {
					System.out.println("Broj već postoji. Preskačem.");
				}
			} else {
				String input = sc.next();
				if(input.equals("kraj")) {
					break;
				} else {
					System.out.format("'%s' nije cijeli broj.%n", input);
				}
			}
		}
		
		System.out.print("Ispis od najmanjeg: ");
		sortedTreePrint(root, false);
		System.out.format("%nIspis od najvećeg: ");
		sortedTreePrint(root, true);
		System.out.format("%nBroj čvorova: %d%n", treeSize(root));
		sc.close();
	}
	
	/**
	 * A method to add nodes to a tree starting with a given root
	 * 
	 * @param root Tree root
	 * @param value A value to be added
	 * @return Reference to the root of the tree
	 */
	public static TreeNode addNode(TreeNode root, int value) {
		if(root == null) {
			root = new TreeNode();
			root.value = value;
		} else {
			if(root.value == value) {
				return root;
			}
			
			if(value <= root.value) {
				root.left = addNode(root.left, value);
			} else {
				root.right = addNode(root.right, value);
			}
		}
		
		
		return root;
	}
	
	/**
	 * A method that counts nodes in a tree
	 * 
	 * @param root Tree root
	 * @return Number of nodes, integer.
	 */
	public static int treeSize(TreeNode root) {
		if(root == null) {
			return 0;
		}
		
		return 1 + treeSize(root.left) + treeSize(root.right);
	}
	
	/**
	 * A method that checks whether a given value is already contained in
	 * a tree
	 * 
	 * @param root Tree root
	 * @param value Value to check
	 * @return Boolean value, true if found, false if not.
	 */
	public static boolean containsValue(TreeNode root, int value) {
		
		if(root == null) {
			return false;
		} else {
			if(value == root.value) {
				return true;
			} else {
				if(value < root.value) {
					return(containsValue(root.left, value));
				} else {
					return(containsValue(root.right, value));
				}
			}
		}
	}
	
	/**
	 * Prints a tree in order, either ascending or descending
	 * 
	 * @param root Tree root
	 * @param reverse Prints in ascending order if false, descending if true
	 */
	private static void sortedTreePrint(TreeNode root, boolean reverse) {
		if(root != null) {
			sortedTreePrint(reverse ? root.right : root.left, reverse);
			System.out.print(root.value + " ");
			sortedTreePrint(reverse ? root.left : root.right, reverse);
		}
	}
		
}
