package generator;

public interface IParser<T> {
    /**
     * Parse single data.
     *
     * @param data
     * @return
     */
    String parse(T data);

    /**
     * Parse a data list and append them together.
     *
     * @param dataList
     * @return
     */
    default String parse(Iterable<? extends T> dataList) {
        StringBuilder sb = new StringBuilder();
        dataList.forEach((data) -> sb.append(parse(data)));
        return sb.toString();
    }
}
