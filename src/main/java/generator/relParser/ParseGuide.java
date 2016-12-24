package generator.relParser;

public class ParseGuide extends AbstractParseGuide {

	@Override
	public void initializeMap() {
		map(RelationDecBidir.class, new BidirectionRelParser());
		map(RelationExtendsClass.class, new GraphVizSuperClassRelParser());
		map(RelationImplement.class, new GraphVizInterfaceParser());
		map(RelationHasA.class, new GraphVizHasRelParser());
		map(RelationDependsOn.class, new GraphVizDependsOnRelParser());
		map(RelationBidirHasA.class, new GraphVizBiHasAParser());
	}

	/**
	 * A GraphVizParser for the model's depends on Relationship.
	 * <p>
	 * Created by lamd on 12/14/2016.
	 */
	class GraphVizDependsOnRelParser implements IParseGuide {

		@Override
		public String getEdgeStyle(IRelationInfo info) {
			StringBuilder infoBuilder = new StringBuilder("arrowhead=vee style=dashed ");
			return infoBuilder.toString();
		}

	}

	/**
	 * A GraphVizParser for the model's HasRelations.
	 * <p>
	 * Created by lamd on 12/14/2016.
	 */
	class GraphVizHasRelParser implements IParseGuide {

		@Override
		public String getEdgeStyle(IRelationInfo info) {
			RelationHasA rel = (RelationHasA) info;
			StringBuilder infoBuilder = new StringBuilder("arrowhead=vee style=\"\" ");
//			if (rel.getCount() > 0) {
//				infoBuilder.append("headlabel=0..n ");
//			}
			return infoBuilder.toString();
		}

	}

	public class GraphVizBiHasAParser implements IParseGuide {

		@Override
		public String getEdgeStyle(IRelationInfo info) {
			RelationHasA rel = ((RelationBidirHasA) info).getForward();
			StringBuilder infoBuilder = new StringBuilder("arrowhead=vee style=\"\" ");
//			if (rel.getCount() > 0) {
//				infoBuilder.append("headlabel=0..n ");
//			}
			return infoBuilder.toString();
		}

	}

	public class BidirectionRelParser implements IParseGuide {
		@Override
		public String getEdgeStyle(IRelationInfo info) {
			RelationDecBidir rel = (RelationDecBidir) info;
			return ParseGuide.this.getEdgeStyle(rel.getDecorated()) + "arrowtail=vee dir=both ";
		}

	}

	/**
	 * A GraphVizParser for the model's interface.
	 * <p>
	 * Created by lamd on 12/14/2016.
	 */
	public class GraphVizInterfaceParser implements IParseGuide {

		@Override
		public String getEdgeStyle(IRelationInfo info) {
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
		public String getEdgeStyle(IRelationInfo info) {
			return "arrowhead=onormal style=\"\" ";
		}

	}
}
