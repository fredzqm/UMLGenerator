package configs;

import java.util.Collection;

/**
 * Created by lamd on 12/7/2016.
 */
public interface Configuration {
    Collection<String> getClasses();
    IFormat getFormat();
}
