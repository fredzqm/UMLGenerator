package analyzer.classParser;

/**
 * An Interface for Parsers.
 *
 * @param <T>
 */
public interface IParser<T> {

    /**
     * Parse single data.
     *
     * @param data   Data to be parsed.
     * @param config
     * @return String of Parsed Data.
     */
    String parse(T data, IClassParserConfiguration config);

    /**
     * Parse a data list and append them together.
     *
     * @param dataList List of Data to be parsed.
     * @param config
     * @return String of all parsed data.
     */
    default String parse(Iterable<? extends T> dataList, IClassParserConfiguration config) {
        StringBuilder sb = new StringBuilder();
        dataList.forEach((data) -> sb.append(parse(data, config)));
        return sb.toString();
    }
}
