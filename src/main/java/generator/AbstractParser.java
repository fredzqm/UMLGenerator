package generator;

/**
 * General interface for a parser, which takes in a data entity and parse it as
 * a string
 * 
 * @author zhang
 *
 * @param <T>
 */
public abstract class AbstractParser<T> {

	public abstract String parse(T data);

	public String parse(Iterable<? extends T> dataList) {
		StringBuilder sb = new StringBuilder();
		for (T d : dataList)
			sb.append(parse(d));
		return sb.toString();
	}
}
