package generator;

import java.util.Collection;

/**
 * The IFormat for the format created in analyzer.
 * <p>
 * Created by lamd on 12/7/2016.
 */
public interface IFormat {
    Collection<IFormatter> getFormatters();
}
