package dummy.decoratorDummy;

/**
 * Created by lamd on 2/4/2017.
 */
public class GoodConcreteDecorator extends GoodDecorator {
    public GoodConcreteDecorator(Component component) {
        super(component);
    }

    @Override
    public String createString() {
        return super.createString() + " concreteDecorator";
    }
}
