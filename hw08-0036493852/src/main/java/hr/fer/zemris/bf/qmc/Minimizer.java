package hr.fer.zemris.bf.qmc;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import hr.fer.zemris.bf.model.BinaryOperatorNode;
import hr.fer.zemris.bf.model.ConstantNode;
import hr.fer.zemris.bf.model.Node;
import hr.fer.zemris.bf.model.UnaryOperatorNode;
import hr.fer.zemris.bf.model.VariableNode;
import hr.fer.zemris.bf.utils.ExpressionStringBuilder;

/**
 * A class to calculate and store a minimal form of a boolean expression.
 * Uses Quine-McCluskey algortihm with Pyne-McCluskey approach
 * 
 * @author Franko Car
 *
 */
public class Minimizer {
	
	/** Set of minterms */
	private Set<Integer> mintermSet;
	/** Set of don't cares */
	private Set<Integer> dontCareSet;
	/** list of variables */
	private List<String> variables;
	/** List of minimal form sets */
	private List<Set<Mask>> minimalForms;
	
	/** Logger */
	private static final Logger LOG = Logger.getLogger("hr.fer.zemris.bf.qmc");

	/**
	 * A constructor
	 * 
	 * @param mintermSet Set of minterms
	 * @param dontCareSet Set of don't cares
	 * @param variables List of variables
	 * @throws IllegalArgumentException if there is a minterm or a don't care out of table range
	 */
	public Minimizer(Set<Integer> mintermSet, Set<Integer> dontCareSet, List<String> variables) {
		if (dontCareSet != null && !Collections.disjoint(mintermSet, dontCareSet)) {
			throw new IllegalArgumentException("Minterm set and don't care set have elements in common");
		}
		
		int maxSize = (int) Math.pow(2, variables.size());
		for (Integer x : mintermSet) {
			if (x < 0 || x > maxSize) {
				throw new IllegalArgumentException("Minterm: '" + x + "' is out of range");
			}
		}
		
		for (Integer x : dontCareSet) {
			if (x < 0 || x > maxSize) {
				throw new IllegalArgumentException("Don't care value: '" + x + "' is out of range");
			}
		}
		
		this.mintermSet = mintermSet;
		this.dontCareSet = dontCareSet;
		this.variables = variables;
				
		Set<Mask> primCover = findPrimaryImplicants();
		minimalForms = chooseMinimalCover(primCover);
		
		
	}

	/**
	 * A method to find minimal forms
	 * 
	 * @param primCover Set of primary implicants
	 * @return List of mask sets with minimal forms
	 */
	private List<Set<Mask>> chooseMinimalCover(Set<Mask> primCover) {
		Mask[] implicants = primCover.toArray(new Mask[primCover.size()]);
		Integer[] minterms = mintermSet.toArray(new Integer[mintermSet.size()]);
		
		Map<Integer, Integer> mintermToColumnMap = new HashMap<>();
		for (int i = 0; i < minterms.length; i++) {
			Integer index = minterms[i];
			mintermToColumnMap.put(index, i);
		}
		
		boolean[][] table = buildCoverTable(implicants, minterms, mintermToColumnMap);
		
		boolean[] coveredMinterms = new boolean[minterms.length];
	
		Set<Mask> importantSet = selectImportantPrimaryImplicants(
				implicants, mintermToColumnMap, table, coveredMinterms);
		
		List<Set<BitSet>> pFunction = buildPFunction(table, coveredMinterms);
		
		Set<BitSet> minset = findMinimalSet(pFunction);	
		
		List<Set<Mask>> minimalForms = new ArrayList<>();
		for (BitSet bs : minset) {
			Set<Mask> set = new LinkedHashSet<>(importantSet);
			bs.stream().forEach(i -> set.add(implicants[i]));
			minimalForms.add(set);
		}
		
		LOG.log(Level.FINE, () -> "");
		LOG.log(Level.FINE, () -> "Minimalni oblici funkcije su: ");
		for (int i = 0, n = minimalForms.size(); i < n; i++) {
			String print = String.format("%d. %s", i + 1, minimalForms.get(i).toString());
			LOG.log(Level.FINE, () -> print);
		}
		
		return minimalForms;
	}

	/**
	 * A method to find minimal set to cover the function
	 * 
	 * @param pFunction cover function
	 * @return Set of BitSet values
	 */
	private Set<BitSet> findMinimalSet(List<Set<BitSet>> pFunction) {
		Set<BitSet> minset = multiply(pFunction);
		
		LOG.log(Level.FINER, () -> "");
		LOG.log(Level.FINER, () -> "Nakon pretvorbe p-funkcije u sumu produkata: ");
		LOG.log(Level.FINER, () -> minset.toString());
		
		int minSize = Integer.MAX_VALUE;
		for (BitSet bits : minset) {
			if (bits.cardinality() < minSize) {
				minSize = bits.cardinality();
			}
		}
		
		Iterator<BitSet> it = minset.iterator();
		while (it.hasNext()) {
			if (it.next().cardinality() > minSize) {
				it.remove();
			}
		}		
		
		LOG.log(Level.FINER, () -> "");
		LOG.log(Level.FINER, () -> "Minimalna pokrivanja još trebaju: ");
		LOG.log(Level.FINER, () -> minset.toString());	
		
		return minset;
	}
	
	/**
	 * A method to cross multiply sets of bitsets
	 * 
	 * @param pFunction cover function
	 * @return Set of BitSet values
	 */
	private Set<BitSet> multiply(List<Set<BitSet>> pFunction) {
		Set<BitSet> sum = new HashSet<>();

		if (pFunction.size() == 0) {
			sum.add(new BitSet());
			return sum;
		}
		
		Set<BitSet> first = pFunction.remove(0);
		Set<BitSet> remaining = multiply(pFunction);
		
		for (BitSet bits1 : first) {
			for (BitSet bits2 : remaining) {
				BitSet tmp = (BitSet) bits1.clone();
				tmp.or(bits2);
				sum.add(tmp);
			}
		}
		
		return sum;
	}

	/**
	 * A method to build a cover function
	 * 
	 * @param table cover table
	 * @param coveredMinterms minterms already covered.
	 * @return List of BitSet sets
	 */
	private List<Set<BitSet>> buildPFunction(boolean[][] table, boolean[] coveredMinterms) {
		
		List<Set<BitSet>> list = new LinkedList<>();
		
		int n = table.length == 0 ? 0 : table[0].length;		
		for (int j = 0; j < n; j++) {
			if (coveredMinterms[j]) {
				continue;
			}
			
			Set<BitSet> set = new HashSet<>();
			for (int i = 0; i < table.length - 1; i++) {
				for (int k = i + 1; k < table.length; k++) {
					if (table[i][j] == true && table[k][j] == true) {
						BitSet bitSet1 = new BitSet();
						bitSet1.set(i);
						BitSet bitSet2 = new BitSet();
						bitSet2.set(k);
						set.add(bitSet1);
						set.add(bitSet2);
					}
				}
			}
			list.add(set);
			coveredMinterms[j] = true;
		}
		
		LOG.log(Level.FINER, () -> "");
		LOG.log(Level.FINER, () -> "p funkcija je: ");
		LOG.log(Level.FINER, () -> list.toString());
		
		return list;
	}

	/**
	 * A method to select important primary implicants
	 * 
	 * @param implicants
	 * @param mintermToColumnMap
	 * @param table
	 * @param coveredMinterms
	 * @return Set of Mask objects
	 * @throws IllegalStateException if there is an uncovered minterm
	 */
	private Set<Mask> selectImportantPrimaryImplicants(Mask[] implicants, Map<Integer, Integer> mintermToColumnMap,
			boolean[][] table, boolean[] coveredMinterms) {
		
		Set<Mask> importantPrims = new HashSet<>();
		
		int n = table.length == 0 ? 0 : table[0].length;		
		for (int j = 0; j < n; j++) {
			int marked = 0;
			int index = 0;
			for (int i = 0; i < table.length; i++) {
				if (table[i][j] == true) {
					marked++;
					index = i;
				}
			}
			
			if (marked == 0) {
				throw new IllegalStateException("Minterm left uncovered");
			}						
			if (marked != 1) {
				continue;
			}
			
			importantPrims.add(implicants[index]);
			
			for (int x : implicants[index].getIndexes()) {
				if (dontCareSet.contains(x)) {
					continue;
				}
				coveredMinterms[mintermToColumnMap.get(x)] = true;
			}
			
		}
		
		LOG.log(Level.FINE, () -> "");
		LOG.log(Level.FINE, () -> "Bitni primarni implikanti su: ");
		for (Mask mask : importantPrims) {
			LOG.log(Level.FINE, mask::toString);
		}
		
		return importantPrims;
	}

	/**
	 * A method to build cover table
	 * 
	 * @param implicants
	 * @param minterms
	 * @param mintermToColumnMap
	 * @return Coverage table
	 */
	private boolean[][] buildCoverTable(Mask[] implicants, Integer[] minterms, Map<Integer, Integer> mintermToColumnMap) {

		boolean[][] coverTable = new boolean[implicants.length][minterms.length];
		
		for (int i = 0; i < implicants.length; i++) {
			for (Integer index : implicants[i].getIndexes()) {
				if (!mintermToColumnMap.containsKey(index)) {
					continue;
				}
				coverTable[i][mintermToColumnMap.get(index)] = true;
			}
		}
		
		return coverTable;
	}

	/**
	 * A method to find primary implicants
	 * 
	 * @return Set of primary implicants
	 */
	private Set<Mask> findPrimaryImplicants() {
		List<Set<Mask>> column = createFirstColumn();		
		Set<Mask> primaryImplicants = new HashSet<>();
		
		while (true) {
			boolean stop = true;
			for (Set<Mask> set : column) {
				stop = stop && set.isEmpty();
			}			
			if (stop) {
				break;
			}
			
			List<Set<Mask>> nextColumn = buildNextColumn(column);
			logTable(column, Level.FINER);
			
			for (Set<Mask> set : column) {
				for (Mask mask : set) {
					if (!mask.isCombined() && !mask.isDontCare()) {
						primaryImplicants.add(mask);
						LOG.log(Level.FINEST, () -> String.format("Pronašao primarni implikant: %s", mask.toString()));
					}
				}
			}			
			column = nextColumn;
		}
		
		LOG.log(Level.FINE, () -> "Svi primarni implikanti:");
		for (Mask mask : primaryImplicants) {
			LOG.log(Level.FINE, mask::toString);
		}
		
		return primaryImplicants;
		
	}

	/**
	 * A method to log the current column
	 * 
	 * @param column to log
	 * @param level Log level to use
	 */
	private void logTable(List<Set<Mask>> column, Level level) {
		LOG.log(level, () -> "");
		LOG.log(level, () -> "Stupac tablice: ");
		LOG.log(level, () -> "=================================");
		
		for (Set<Mask> set : column) {
			if (!set.isEmpty()) {
				for (Mask mask : set) {
					LOG.log(level, mask::toString);
				}
				LOG.log(level, () -> "------------------------------");
			}
		}
		
	}

	/**
	 * A method to build next column
	 * 
	 * @param column previous column
	 * @return new column as a list of Mask sets
	 */
	private List<Set<Mask>> buildNextColumn(List<Set<Mask>> column) {
		List<Set<Mask>> nextColumn = new ArrayList<>();
		for (int i = 0, n = column.size(); i < n; i++) {
			nextColumn.add(new HashSet<Mask>());
		}
		
		for (int i = 0, n = column.size() - 1; i < n; i++) {
			Set<Mask> set1 = column.get(i);
			Set<Mask> set2 = column.get(i + 1);			
			for (Mask mask1 : set1) {
				for (Mask mask2 : set2) {
					Optional<Mask> tmp = mask1.combineWith(mask2);
					if (!tmp.isPresent()) {
						continue;
					}
					
					Mask mask3 = tmp.get();
					mask1.setCombined(true);
					mask2.setCombined(true);
					nextColumn.get(mask3.countOfOnes()).add(mask3);
				}
			}
		}
		
		return nextColumn;
	}
	
	/**
	 * A method to build first column
	 * 
	 * @return new column as a list of Mask sets
	 */
	private List<Set<Mask>> createFirstColumn() {
		List<Set<Mask>> list = new ArrayList<>();
		for (int i = 0, n = variables.size(); i <= n; i++) {
			list.add(new HashSet<Mask>());
		}
		
		for (Integer x : mintermSet) {
			Mask temp = new Mask(x, variables.size(), false);
			list.get(temp.countOfOnes()).add(temp);
		}
		
		for (Integer x : dontCareSet) {
			Mask temp = new Mask(x, variables.size(), true);
			list.get(temp.countOfOnes()).add(temp);
		}
		
		return list;
	}
	
	/**
	 * A method to build an expression from minimal forms
	 * 
	 * @return List of top-level nodes
	 */
	public List<Node> getMinimalFormsAsExpressions() {
		List<Node> nodes = new ArrayList<>();
		for (Set<Mask> set : minimalForms) {			
			if (set.isEmpty()) {
				nodes.add(new ConstantNode(false));
				continue;
			}
			
			Node topLevel;
			List<Node> children = new ArrayList<>();
			for (Mask mask : set) {
				children.add(getMaskAsExpression(mask));
			}

			if (set.size() > 1) {
				topLevel = new BinaryOperatorNode("or", children, (x, y) -> x || y);
			} else {
				topLevel = children.get(0);
			}
			
			nodes.add(topLevel);
		}
		
		return nodes;
	}	
	
	/**
	 * A method to build an expression from each mask
	 * 
	 * @param mask
	 * @return Expression node
	 */
	private Node getMaskAsExpression(Mask mask) {
		List<Node> children = new ArrayList<>();
		for (int i = 0, n = mask.size(); i < n; i++) {
			if (mask.getValueAt(i) == 0) {
				UnaryOperatorNode notNode = new UnaryOperatorNode("not", new VariableNode(variables.get(i)), x -> !x);
				children.add(notNode);
			} else if (mask.getValueAt(i) == 1) {
				children.add(new VariableNode(variables.get(i)));
			}

		}
		
		if (children.size() == 0 ) {
			return new ConstantNode(true);
		}
		
		if (children.size() == 1) {
			return children.get(0);
		}
		
		return new BinaryOperatorNode("and", children, (x, y) -> x && y);
	}

	/**
	 * A method to build a string from minimal form
	 * 
	 * @return List of strings
	 */
	public List<String> getMinimalFormsAsString() {
		List<String> strings = new ArrayList<>();
		
		List<Node> nodes = getMinimalFormsAsExpressions();
		for (Node node : nodes) {
			ExpressionStringBuilder expressionStringBuilder = new ExpressionStringBuilder();
			node.accept(expressionStringBuilder);
			strings.add(expressionStringBuilder.toString());
		}
		
		return strings;
	}
	
	/**
	 * Getter for minimal forms as a list of sets
	 * 
	 * @return List<Set<Mask>> of minimal forms
	 */
	public List<Set<Mask>> getMinimalForms() {
		return minimalForms;
	}
}
