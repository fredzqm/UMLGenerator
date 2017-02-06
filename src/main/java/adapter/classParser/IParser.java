package adapter.classParser;

import analyzer.utility.ISystemModel;

/**
 * An Interface for Parsers.
 *
 * @param <T>
 */
public interface IParser<T> {

    /**
     * Parse single data.
     *
     * @param data
     *            Data to be parsed.
     * @param config
     * @return String of Parsed Data.
     */
    String parse(T data, ISystemModel systemModel, ClassParserConfiguration config);

    /**
     * Parse a data list and append them together.
     *
     * @param dataList
     *            List of Data to be parsed.
     * @param config
     * @return String of all parsed data.
     */
    default String parse(Iterable<? extends T> dataList, ISystemModel systemModel, ClassParserConfiguration config) {
        StringBuilder sb = new StringBuilder();
        dataList.forEach((data) -> sb.append(parse(data, systemModel, config)));
        return sb.toString();
    }
}
