package model;

public class Dummy {
	private int privateField;
	public int publicField;
	int defaultField;
	protected String proctedField;

	static {

	}

	public Dummy(int a) {
	}

	public Dummy(int a, int b) {

	}

	private void privateMethod() {
		
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
