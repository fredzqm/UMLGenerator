package dummy.decoratorDummy;

/**
 * Created by lamd on 2/4/2017.
 */
public abstract class Adapter implements AdaptedTo {
    private Adapee component;

    public Adapter(Adapee component) {
        this.component = component;
    }

    @Override
    public String createString() {
        return this.component.adapeeCreateString();
    }
}
