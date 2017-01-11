package dummy;

public class DummySubClass extends DummySuperClass{

	public DummySubClass(String s) {
		super(s);
	}
	
	@Override
	public void A() {
		System.out.println(s+s);
	}

}
