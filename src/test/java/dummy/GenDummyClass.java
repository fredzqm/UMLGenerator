package dummy;

/**
 * A Dummy Class used for testing.
 * <p>
 * Created by lamd on 12/12/2016.
 */
public class GenDummyClass {
    public int publicInt;
    public String publicString;
    private int privateInt;
    private String privateString;

    public GenDummyClass(int privateInt, String privateString) {
        this.privateInt = privateInt;
        this.publicInt = privateInt + 5;

        this.privateString = privateString;
        this.publicString = privateString + " hello";
    }

    private void printPrivateString() {
        System.out.println(this.privateString);
    }

    public String getPublicString() {
        return this.publicString;
    }

    int getPublicInt() {
        return this.publicInt;
    }

    protected double someProtectedMethod() {
        System.out.println("I am protected");
        return 0.0;
    }
}
