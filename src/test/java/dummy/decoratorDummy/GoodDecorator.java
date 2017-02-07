package dummy.decoratorDummy;

/**
 * Created by lamd on 2/4/2017.
 */
public abstract class GoodDecorator extends Component {
    private Component component;

    public GoodDecorator(Component component) {
        this.component = component;
    }

    @Override
    public String createString() {
        return this.component.createString();
    }
}
