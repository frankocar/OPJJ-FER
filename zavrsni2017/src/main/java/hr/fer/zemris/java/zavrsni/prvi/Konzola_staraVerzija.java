package hr.fer.zemris.java.zavrsni.prvi;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Konzola_staraVerzija {

	public static void main(String[] args) {
		FileParser fp = new FileParser("file1-in.txt");
		System.out.println();

		List<List<Double>> inputValues = fp.getInputValues();
		for (List<Double> inputSet : inputValues) {
			getOutput(inputSet, fp);
		}
	}

	private static void getOutput(List<Double> inputSet, FileParser fp) {
		Map<String, Double> vars = new HashMap<>();
		for (int i = 0, n = inputSet.size(); i < n; i++) {
			vars.put(fp.getInputVars().get(i), inputSet.get(i));
		}
		
		calcExpressions(vars, fp.getExpressions());
		printOutput(vars, fp);
		
	}

	private static void printOutput(Map<String, Double> vars, FileParser fp) {
		StringBuilder sb = new StringBuilder();
		
		for (String var : fp.getInputVars()) {
			sb.append(vars.get(var));
			sb.append(";");
		}
		
		for (String var : fp.getOutputFormat()) {
			sb.append(vars.get(var));
			sb.append(";");
		}
		
		sb.deleteCharAt(sb.length() - 1);
		System.out.println(sb.toString());
		
	}

	private static void calcExpressions(Map<String, Double> vars, List<Expression> expressions) {
		Set<Expression> getLater = new LinkedHashSet<>();
		
		for (Expression ex : expressions) {
			Double leftVal = vars.get(ex.getFirstLeftVar());
			Double rightVal = vars.get(ex.getSecondLeftVal());
			
			if (leftVal == null || rightVal == null) {
				getLater.add(ex);
				continue;
			}
			
			Double res = eval(leftVal, rightVal, ex);
			
			vars.put(ex.getRightVar(), res);			
		}
		
		while (!getLater.isEmpty()) {
			Iterator<Expression> it = getLater.iterator();
			while (it.hasNext()) {
				Expression ex = it.next();
				Double leftVal = vars.get(ex.getFirstLeftVar());
				Double rightVal = vars.get(ex.getSecondLeftVal());
				
				if (leftVal == null || rightVal == null) {
					continue;
				}
				
				Double res = eval(leftVal, rightVal, ex);
				
				vars.put(ex.getRightVar(), res);	
				it.remove();
			}
		}
		
		
		
	}

	private static Double eval(Double leftVal, Double rightVal, Expression ex) {
		Double res;
		
		switch (ex.getOper()) {
		case "+":
			res = leftVal + rightVal;
			break;
		case "*":
			res = leftVal * rightVal;
			break;
		case "-":
			res = leftVal - rightVal;
			break;
		case "/":
			res = leftVal / rightVal;
			break;
		default:
			throw new IllegalArgumentException("Invalid expression");
		}
		
		return res;
	}
	
}
