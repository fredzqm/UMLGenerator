package generator.relParser;

public class RelationHasA extends Relation {
    private final boolean many;
    private final int count;

    public RelationHasA(String from, String to, int count) {
        super(from, to);
        this.many = count <= 0;
        this.count = Math.abs(count);
    }

    public boolean isMany() {
        return this.many;
    }

    public int getCount() {
        return this.count;
    }
    
//    public int getCardinalityTo() {
//        return this.toCardinality;
//    }
//
//    public int getCardinalityFrom() {
//        return this.fromCardinality;
//    }
	// public void setCardinalityTo(int count) {
	// this.toCardinality = count;
	// }
	//
	// public void setCardinalityFrom(int count) {
	// this.fromCardinality = count;
	// }

}
