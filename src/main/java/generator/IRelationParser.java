package generator;

public interface IRelationParser {

    /**
     * parse single relationship
     *
     * @param data
     * @return
     */
    String parse(IClassModel thisClass, IClassModel otherClass);

    /**
     * parse a data list of relationship and append them together
     *
     * @param dataList
     * @return
     */
    default String parse(IClassModel thisClass, Iterable<? extends IClassModel> otherClassLs) {
        StringBuilder sb = new StringBuilder();
        for (IClassModel d : otherClassLs)
            sb.append(parse(thisClass, d));
        return sb.toString();
    }

    Iterable<? extends IClassModel> getRelatedClasses(IClassModel thisClass);

    default String parse(IClassModel thisClass) {
        return parse(thisClass, getRelatedClasses(thisClass));
    }

    default String parse(Iterable<? extends IClassModel> classes) {
        StringBuilder sb = new StringBuilder();
        for (IClassModel d : classes)
            sb.append(parse(d));
        return sb.toString();
    }

}
