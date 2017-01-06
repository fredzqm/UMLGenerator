package generator.relationshipParser;

public class GraphVizParseGuide extends AbstractParseGuide {

    @Override
    public void initializeMap() {
        // Call map METHOD.
        map(ReleationBijectiveDecorator.class, new GraphVizBijectiveRelationshipParser());
        map(RelationExtendsClass.class, new GraphVizSuperClassRelationshipParser());
        map(RelationImplement.class, new GraphVizInterfaceParser());
        map(RelationHasA.class, new GraphVizHasRelationshipParser());
        map(RelationDependsOn.class, new GraphVizDependsOnRelationshipParser());
        map(RelationHasABijective.class, new GraphVizHasABijectiveParser());
    }

    /**
     * A GraphVizParser for the model's depends on Relationship.
     * <p>
     * Created by lamd on 12/14/2016.
     */
    class GraphVizDependsOnRelationshipParser implements IParseGuide {
        @Override
        public String getEdgeStyle(IRelationInfo info) {
            // TODO: Implement Cardinality.
            RelationDependsOn dependsOnRelation = (RelationDependsOn) info;
            return "arrowhead=\"vee\" style=dashed ";
        }
    }

	/**
	 * A GraphVizParser for the model's HasRelations.
	 * <p>
	 * Created by lamd on 12/14/2016.
	 */
	class GraphVizHasRelationshipParser implements IParseGuide {
		@Override
		public String getEdgeStyle(IRelationInfo info) {
			RelationHasA hasARelation = (RelationHasA) info;
			StringBuilder edgeBuilder = new StringBuilder("arrowhead=\"vee\" style=\"\" ");

            if (hasARelation.isMany() || hasARelation.getCount() > 1) {
                edgeBuilder.append("taillabel=\"1..*\" ");
            }

			return edgeBuilder.toString();
		}
	}

	public class GraphVizHasABijectiveParser implements IParseGuide {
		@Override
		public String getEdgeStyle(IRelationInfo info) {
			RelationHasA forward = ((RelationHasABijective) info).getForward();
			StringBuilder edgeBuilder = new StringBuilder("arrowhead=\"vee\" style=\"\" dir=both ");

            if (forward.isMany() || forward.getCount() > 1) {
                edgeBuilder.append("headlabel=\"1..*\" ");
            }

            RelationHasA backward = ((RelationHasABijective) info).getBackward();
            if (backward.isMany() || backward.getCount() > 1) {
                edgeBuilder.append("taillabel=\"1..*\" ");
            }

			return edgeBuilder.toString();
		}
	}

    /**
     *
     *
     */
    public class GraphVizBijectiveRelationshipParser implements IParseGuide {
        @Override
        public String getEdgeStyle(IRelationInfo info) {
            ReleationBijectiveDecorator rel = (ReleationBijectiveDecorator) info;
            return GraphVizParseGuide.this.getEdgeStyle(rel.getDecorated()) + "arrowtail=\"vee\" style=\"\" dir=both ";
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
			return "arrowhead=\"onormal\" style=dashed ";
		}
	}

	/**
	 * A GraphVizParser for the model's SuperClass.
	 * <p>
	 * Created by lamd on 12/14/2016.
	 */
	public class GraphVizSuperClassRelationshipParser implements IParseGuide {
		@Override
		public String getEdgeStyle(IRelationInfo info) {
			return "arrowhead=\"onormal\" style=\"\" ";
		}
	}
}
