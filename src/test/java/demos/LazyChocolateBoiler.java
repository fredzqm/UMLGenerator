package demos;

public class LazyChocolateBoiler {
	private static LazyChocolateBoiler boiler;
	
	private LazyChocolateBoiler() {
		
	}
	
	public static LazyChocolateBoiler instanceOf() {
		if(boiler == null)
			boiler = new LazyChocolateBoiler();
		return boiler;
	}
}
