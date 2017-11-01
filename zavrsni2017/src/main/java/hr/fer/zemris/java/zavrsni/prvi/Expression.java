package hr.fer.zemris.java.zavrsni.prvi;

public class Expression {

	private String rightVar;
	private String firstLeftVar;
	private String secondLeftVal;
	private String oper;
	
	
	public Expression(String rightVar, String firstLeftVar, String secondLeftVal, String oper) {
		this.rightVar = rightVar;
		this.firstLeftVar = firstLeftVar;
		this.secondLeftVal = secondLeftVal;
		this.oper = oper;
	}
	
	public String getRightVar() {
		return rightVar;
	}
	
	public String getFirstLeftVar() {
		return firstLeftVar;
	}
	
	public String getSecondLeftVal() {
		return secondLeftVal;
	}
	
	public String getOper() {
		return oper;
	}
	
}
