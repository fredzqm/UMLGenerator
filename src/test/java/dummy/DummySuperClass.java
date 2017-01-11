package dummy;

public class DummySuperClass implements DummyInterface{

	protected String s;
	
	public DummySuperClass(String s) {
		this.s = s;
	}
	
	@Override
	public void A() {
		System.out.println(s);
	}

}
