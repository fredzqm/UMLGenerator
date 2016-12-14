package generator;

public interface IParser<T> {
	/**
	 * parse single data
	 * 
	 * @param data
	 * @return
	 */
	String parse(T data);

	/**
	 * parse a data list and append them together
	 * 
	 * @param dataList
	 * @return
	 */
	default String parse(Iterable<? extends T> dataList) {
		StringBuilder sb = new StringBuilder();
		for (T d : dataList)
			sb.append(parse(d));
		return sb.toString();
	}
}
