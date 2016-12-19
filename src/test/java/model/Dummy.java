package model;

import java.util.ArrayList;

public class Dummy {
	private int privateField;
	public int publicField;
	int defaultField;
	protected String proctedField;
	private ArrayList<String> x;
	
	static {

	}

	public Dummy(int a) {
	}

	public Dummy(int a, int b) {

	}

	private int privateMethod() {
		defaultField = 4;
		String x = proctedField;
		proctedField = proctedField + "34";
		return publicField;
	}

	public String publicMethod() {
		StringBuilder a = new StringBuilder();
		a.append("1");
		a.append("2");
		return a.toString();
	}

	public static int staticMethod() {
		return 3;
	}
}
