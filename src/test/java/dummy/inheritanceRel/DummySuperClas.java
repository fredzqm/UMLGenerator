package dummy.inheritanceRel;

public class DummySuperClas implements DummyInterface{

	protected String s;
	
	public DummySuperClas(String s) {
		this.s = s;
	}
	
	@Override
	public void A() {
		System.out.println(s);
	}

}
