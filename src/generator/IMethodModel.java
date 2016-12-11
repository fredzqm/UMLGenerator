package generator;

/**
 * Created by lamd on 12/7/2016.
 */
public interface IMethodModel {
	IClassModel getParentClass();

	IModifier getModifier();

	IMethodType getMethodType();

	boolean isFinal();

	String getName();

	ITypeModel getReturnType();

	Iterable<? extends ITypeModel> getArguments();

	public interface IMethodType {
		void switchByCase(Switcher switcher);

		public interface Switcher {
			void ifConstructor();

			void ifConcrete();

			void ifStatic();

			void ifStaticInitializer();

			void ifAbstract();

		}
	}
}
