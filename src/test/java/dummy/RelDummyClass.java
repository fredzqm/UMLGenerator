package dummy;

/**
 * A Dummy Class used for testing.
 * <p>
 * Created by lamd on 12/12/2016.
 */
public class RelDummyClass {
    public RelOtherDummyClass publicDummy;
    private RelOtherDummyClass privateDummy;

    public RelDummyClass(RelOtherDummyClass dummy) {
        this.privateDummy = dummy;
        this.publicDummy = dummy;
    }

    private void printPrivateString() {
    }

    public RelOtherDummyClass getPublicString() {
        return this.publicDummy;
    }

    protected double someProtectedMethod() {
        return 0.0;
    }
}
