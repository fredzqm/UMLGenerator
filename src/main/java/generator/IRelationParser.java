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
     * TODO: Fred fix this.
     *
     * @param dataList
     * @return
     */
    default String parse(IClassModel thisClass, Iterable<? extends IClassModel> otherClassList) {
        StringBuilder sb = new StringBuilder();
        otherClassList.forEach((data) -> sb.append(parse(thisClass, data)));
        return sb.toString();
    }

    Iterable<? extends IClassModel> getRelatedClasses(IClassModel thisClass);

    default String parse(IClassModel thisClass) {
        return parse(thisClass, getRelatedClasses(thisClass));
    }

    default String parse(Iterable<? extends IClassModel> classes) {
        StringBuilder sb = new StringBuilder();
        classes.forEach((classModel) -> sb.append(parse(classModel)));
        return sb.toString();
    }

}
