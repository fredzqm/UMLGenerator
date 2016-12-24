package generator.relParser;

public class ParseGuide extends AbstractParseGuide {

	@Override
	public void initializeMap() {
		map(RelationExtendsClass.class, new GraphVizSuperClassRelParser());
		map(RelationImplement.class, new GraphVizInterfaceParser());
		map(RelationHasA.class, new GraphVizHasRelParser());
		map(RelationDependsOn.class, new GraphVizDependsOnRelParser());
	}

	/**
	 * A GraphVizParser for the model's depends on Relationship.
	 * <p>
	 * Created by lamd on 12/14/2016.
	 */
	class GraphVizDependsOnRelParser implements IParseGuide {

		@Override
		public String getEdgeStyle(IRelation edge) {
			StringBuilder edgeBuilder = new StringBuilder("arrowhead=vee style=dashed ");
			if (edge.isBijective()) {
				edgeBuilder.append("arrowtail=vee dir=both ");
			}

			return edgeBuilder.toString();
		}

	}

	/**
	 * A GraphVizParser for the model's HasRelations.
	 * <p>
	 * Created by lamd on 12/14/2016.
	 */
	class GraphVizHasRelParser implements IParseGuide {

		@Override
		public String getEdgeStyle(IRelation edge) {
			StringBuilder edgeBuilder = new StringBuilder("arrowhead=vee style=\"\" ");
			if (edge.isBijective()) {
				edgeBuilder.append("arrowtail=vee dir=both ");
			}

			return edgeBuilder.toString();
		}

	}

	/**
	 * A GraphVizParser for the model's interface.
	 * <p>
	 * Created by lamd on 12/14/2016.
	 */
	public class GraphVizInterfaceParser implements IParseGuide {

		@Override
		public String getEdgeStyle(IRelation edge) {
			return "arrowhead=onormal style=dashed ";
		}

	}

	/**
	 * A GraphVizParser for the model's SuperClass.
	 * <p>
	 * Created by lamd on 12/14/2016.
	 */
	public class GraphVizSuperClassRelParser implements IParseGuide {

		@Override
		public String getEdgeStyle(IRelation edge) {
			return "arrowhead=onormal style=\"\" ";
		}

	}
}
