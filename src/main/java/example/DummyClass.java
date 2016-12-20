package example;

/**
 * A Dummy Class used for testing.
 * <p>
 * Created by lamd on 12/12/2016.
 */
public class DummyClass {
    private OtherDummyClass privateDummy;
    public OtherDummyClass publicDummy;

    public DummyClass(OtherDummyClass dummy) {
        this.privateDummy = dummy;
        this.publicDummy = dummy;
    }

    private void printPrivateString() {
        System.out.println(this.privateDummy);
    }

    public OtherDummyClass getPublicString() {
        return this.publicDummy;
    }

    protected double someProtectedMethod() {
        System.out.println("I am protected");
        return 0.0;
    }
}
