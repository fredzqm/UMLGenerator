package generator.relParser;

public class ParseGuide extends AbstractParseGuide {

    @Override
    public void initializeMap() {
        // Call map METHOD.
        map(ReleationBijectiveDecorator.class, new BidirectionRelParser());
        map(RelationExtendsClass.class, new GraphVizSuperClassRelParser());
        map(RelationImplement.class, new GraphVizInterfaceParser());
        map(RelationHasA.class, new GraphVizHasRelParser());
        map(RelationDependsOn.class, new GraphVizDependsOnRelParser());
        map(RelationHasABijective.class, new GraphVizBiHasAParser());
    }

    /**
     * A GraphVizParser for the model's depends on Relationship.
     * <p>
     * Created by lamd on 12/14/2016.
     */
    class GraphVizDependsOnRelParser implements IParseGuide {
        @Override
        public String getEdgeStyle(IRelationInfo info) {
            return "arrowhead=vee style=dashed ";
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
            RelationHasA hasARelation = (RelationHasA) info;
            StringBuilder edgeBuilder = new StringBuilder("arrowhead=vee style=\"\" ");

            if (hasARelation.getCount() > 1) {
                edgeBuilder.append("taillabel=\"1..*\" ");
            }

            return edgeBuilder.toString();
        }
    }

    public class GraphVizBiHasAParser implements IParseGuide {
        @Override
        public String getEdgeStyle(IRelationInfo info) {
            RelationHasA forward = ((RelationHasABijective) info).getForward();
            StringBuilder edgeBuilder = new StringBuilder("arrowhead=vee style=\"\" dir=both ");

			if (forward.getCount() > 1) {
				edgeBuilder.append("headlabel=\"1..*\" ");
			}

			RelationHasA backward = ((RelationHasABijective) info).getBackward();
			if (backward.getCount() > 1) {
                edgeBuilder.append("taillabel=\"1..*\" ");
            }

            return edgeBuilder.toString();
        }
    }

    public class BidirectionRelParser implements IParseGuide {
        @Override
        public String getEdgeStyle(IRelationInfo info) {
            ReleationBijectiveDecorator rel = (ReleationBijectiveDecorator) info;
            return ParseGuide.this.getEdgeStyle(rel.getDecorated()) + "arrowtail=vee style=\"\" dir=both ";
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
