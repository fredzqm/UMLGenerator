package main.model;

public class FieldModel implements Visitable<FieldModel> {

	@Override
	public void visit(Visitor<FieldModel> visitor) {
		visitor.visit(this);
	}

}
