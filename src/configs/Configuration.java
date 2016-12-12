package configs;


/**
 * Created by lamd on 12/7/2016.
 */
public interface Configuration {
    Iterable<String> getClasses();

    IFormat getFormat();
}
