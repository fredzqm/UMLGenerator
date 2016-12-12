package analyzer;

/**
 * Created by lamd on 12/7/2016.
 */
public interface IPattern {
	/**
	 * 
	 * @return name of the pattern
	 */
	String getName();

	/**
	 * 
	 * @return the jobs in the Pattern
	 */
	Iterable<Job> getJobs();
}
