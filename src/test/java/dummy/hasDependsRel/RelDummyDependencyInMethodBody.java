package dummy.hasDependsRel;

/**
 * Created by lamd on 1/12/2017.
 */
public class RelDummyDependencyInMethodBody {
    public void methodWithBodyDependency() {
        // Explicitly typed and used.
        String word = "foo";

        // Stream not typed (stored as variable).
        word.chars();
    }
}
