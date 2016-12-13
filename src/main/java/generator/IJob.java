package generator;

import java.util.List;

/**
 * Created by lamd on 12/7/2016.
 */
public interface IJob {
	
	public String getName();

	public List<String> getClasses();

	public IFormat getFormat();

}
