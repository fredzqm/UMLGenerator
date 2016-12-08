package main.model;

public class ClassModel implements Visitable<ClassModel>{
	private String name;

	public String getName() {
		return name;
	}

	@Override
	public void visit(Visitor<ClassModel> visitor) {
		visitor.visit(this);
	}

}
