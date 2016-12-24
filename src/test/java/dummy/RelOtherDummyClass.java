package dummy;

/**
 * A Dummy Class used for testing.
 * <p>
 * Created by lamd on 12/12/2016.
 */
public class RelOtherDummyClass {
    private int dummyInt;

    private RelDummyClass bijectiveDependency;

    public RelOtherDummyClass(int dummyInt) {
        System.out.println("I am dummyInt");
        this.dummyInt = dummyInt;
    }
}
