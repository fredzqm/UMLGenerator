package dummy.favorDummy;

/**
 * Created by lamd on 1/15/2017.
 */
public class FavorDummyB extends FavorDummyA {
    public FavorDummyB() {
        super();
    }

    @Override
    public void printFavor() {
        System.out.println(this.favor + 10);
    }
}
