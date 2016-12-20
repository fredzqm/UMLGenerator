package dummy;

/**
 * A Dummy Class used for testing.
 * <p>
 * Created by lamd on 12/12/2016.
 */
public class RelDummyClass {
    private RelOtherDummyClass privateDummy;
    public RelOtherDummyClass publicDummy;

    public RelDummyClass(RelOtherDummyClass dummy) {
        this.privateDummy = dummy;
        this.publicDummy = dummy;
    }

    private void printPrivateString() {
        System.out.println(this.privateDummy);
    }

    public RelOtherDummyClass getPublicString() {
        return this.publicDummy;
    }

    protected double someProtectedMethod() {
        System.out.println("I am protected");
        return 0.0;
    }
}
