package dummy.adapter;

public class Adapter implements ITarget{

	private Adaptee a;
	
	public Adapter(Adaptee a){
		this.a =a;
	}
	
	@Override
	public void method1() {
		a.m1();
		
	}

	@Override
	public void method2() {
		a.m2();
		
	}

}
