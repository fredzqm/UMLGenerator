package demos;

public class EagerChocolateBoiler {
	private static EagerChocolateBoiler boiler = new EagerChocolateBoiler();
	
	private EagerChocolateBoiler() {
	}
	
	public static EagerChocolateBoiler instanceOf() {
		return boiler;
	}
}
