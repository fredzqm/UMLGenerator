package generator;

import java.util.List;

/**
 * The IJob interface for analyzer.
 * <p>
 * Created by lamd on 12/7/2016.
 */
public interface IJob {

    /**
     * @return names
     */
    String getName();

    /**
     * @return classes
     */
    List<String> getClasses();
}
