package generator.relParser;

public class RelationHasA extends Relation {

    private boolean many;
    private int count;

    public RelationHasA(String from, String to, int count) {
        super(from, to);
        many = count <= 0;
        count = Math.abs(count);
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
