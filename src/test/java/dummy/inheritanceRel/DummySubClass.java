package dummy.inheritanceRel;

public class DummySubClass extends DummySuperClas{

	public DummySubClass(String s) {
		super(s);
	}
	
	@Override
	public void A() {
		System.out.println(s+s);
	}

}
