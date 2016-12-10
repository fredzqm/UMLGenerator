package generator;

public interface IModifier {
	String getModifierSymbol();
	
	void switchByCase(Switcher switcher);

	public interface Switcher {
		void ifPrivate();

		void ifPublic();

		void ifProtected();

		void ifDefault();
	}

}
