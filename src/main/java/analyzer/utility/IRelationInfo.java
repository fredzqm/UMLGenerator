package analyzer.utility;

public abstract class IRelationInfo {
    private StyleMap styleMap;

    public IRelationInfo() {
        styleMap = new StyleMap();
    }

    public void addStyle(String key, String value) {
        styleMap.addStyle(key, value);
    }

    public String getEdgeSytle() {
        return getBaseEdgeStyle() + " " + styleMap;
    }

    /**
     * Returns the String of the Relations edge style.
     *
     * @return String of the Edge Style.
     */
    public abstract String getBaseEdgeStyle();

}
