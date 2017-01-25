package demos;

public class EagerChocolateBoiler {
	private boolean empty;
	private boolean boiled;
	private static EagerChocolateBoiler uniqueInstance = new EagerChocolateBoiler();;
  
	private EagerChocolateBoiler() {
		empty = true;
		boiled = false;
	}
  
	public static EagerChocolateBoiler getInstance() {
		return uniqueInstance;
	}

	public void fill() {
		if (isEmpty()) {
			empty = false;
			boiled = false;
			// fill the boiler with a milk/chocolate mixture
		}
	}
 
	public void drain() {
		if (!isEmpty() && isBoiled()) {
			// drain the boiled milk and chocolate
			empty = true;
		}
	}
 
	public void boil() {
		if (!isEmpty() && !isBoiled()) {
			// bring the contents to a boil
			boiled = true;
		}
	}
  
	public boolean isEmpty() {
		return empty;
	}
 
	public boolean isBoiled() {
		return boiled;
	}
}
