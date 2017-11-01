package hr.fer.zemris.java.zavrsni.prvi;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FileParser {

	private String path;
	private String outputData;	
	private List<String> outputFormat;
	private List<String> expressionData;
	private List<String> inputData;
	private String inputDataVars;
	
	private List<String> directives;
	
	private List<Expression> expressions;
	private List<List<Double>> inputValues;
	private List<String> inputVars;
	
	public FileParser(String path) {
		this.path = path;
		getData(path);
	}
	
	private void getData(String path) {
		List<String> input;
		try {
			input = Files.readAllLines(Paths.get(path));
		} catch (IOException e) {
			throw new IllegalArgumentException("Can't read file");
		}
		
		expressionData = new ArrayList<>();
		inputData = new ArrayList<>();
		directives = new ArrayList<>();
		
		for (String line : input) {
			if (line.isEmpty()) {
				continue;
			} else if (line.startsWith("#EXPAND")) {
				outputData = line.substring("#EXPAND".length()).trim();
				directives.add(line);
			} else if (line.startsWith("#EXPR")) {
				expressionData.add(line.substring("#EXPR".length()).trim());
				directives.add(line);
			} else if (line.startsWith("#LABELS")) {
				inputDataVars = line.substring("#LABELS".length()).trim();
				directives.add(line);
			} else if (Character.isDigit(line.trim().charAt(0))) {
				inputData.add(line.trim());
			}
		}
		
		parseInput();
		parseOutputFormat();
		parseExpressions();
		
	}

	private void parseExpressions() {
		expressions = new ArrayList<>();
		for (String expr : expressionData) {
			String[] temp = expr.trim().split("=");
			if (temp.length != 2) {
				throw new IllegalArgumentException("Invalid expression");
			}
			String rightVar = temp[0].trim().substring(1);
			
			String[] leftSide = temp[1].trim().split("[*]|[/]|[+]|[-]");
			String firstLeft = leftSide[0].trim().substring(1);
			String secondLeft = leftSide[1].trim().substring(1);
			
			String oper = null;
			if (temp[1].contains("+")) {
				oper = "+";
			} else if (temp[1].contains("*")) {
				oper = "*";
			} else if (temp[1].contains("/")) {
				oper = "/";
			} else if (temp[1].contains("-")) {
				oper = "-";
			} else {
				throw new IllegalArgumentException("Invalid expression");
			}
			
			expressions.add(new Expression(rightVar, firstLeft, secondLeft, oper));
		}
		
	}

	private void parseOutputFormat() {
		outputFormat = Arrays.asList(outputData.replaceAll("\\s+", "").split(";"));
		
	}

	private void parseInput() {
		inputValues = new ArrayList<>();
		inputVars = Arrays.asList(inputDataVars.replaceAll("\\s+", "").split(";"));
		for (String line : inputData) {			
			double[] numbers = Arrays.stream(line.replaceAll("\\s+", "").split(";")).mapToDouble(Double::parseDouble).toArray();
			List<Double> temp = new ArrayList<>();
			for (double x : numbers) {
				temp.add((Double)x);
			}
			inputValues.add(temp);			
		}		
	}
	
	public List<Expression> getExpressions() {
		return expressions;
	}
	
	public List<String> getOutputFormat() {
		return outputFormat;
	}
	
	public List<List<Double>> getInputValues() {
		return inputValues;
	}
	
	public List<String> getInputVars() {
		return inputVars;
	}
	
	public List<String> getDirectives() {
		return directives;
	}
	
	public List<String> getOutputList() {
		List<String> out = new ArrayList<>();
		for (List<Double> inputSet : inputValues) {
			out.add(getOutput(inputSet));
		}
		return out;
	}
	
	private String getOutput(List<Double> inputSet) {
		Map<String, Double> vars = new HashMap<>();
		for (int i = 0, n = inputSet.size(); i < n; i++) {
			vars.put(inputVars.get(i), inputSet.get(i));
		}
		
		calcExpressions(vars, expressions);
		StringBuilder sb = new StringBuilder();
		
		for (String var : inputVars) {
			sb.append(vars.get(var));
			sb.append(";");
		}
		
		for (String var : outputFormat) {
			sb.append(vars.get(var));
			sb.append(";");
		}
		
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
		
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
