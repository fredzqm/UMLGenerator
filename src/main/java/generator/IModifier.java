package generator;

/**
 * An Interface for Access Modifers.
 */
public interface IModifier {
    /**
     * Returns the Modifier Symbol.
     *
     * @return String of the Modifier Symbol.
     */
    String getModifierSymbol();

    /**
     * TODO: Fred
     *
     * @param switcher
     */
    void switchByCase(Switcher switcher);

    interface Switcher {
        void ifPrivate();

        void ifPublic();

        void ifProtected();

        void ifDefault();
    }

}
