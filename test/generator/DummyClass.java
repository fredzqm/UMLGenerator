package generator;

/**
 * Created by lamd on 12/12/2016.
 */
public class DummyClass {
    private int privateInt;
    public int publicInt;

    private String privateString;
    public String publicString;

    public DummyClass(int privateInt, String privateString) {
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
