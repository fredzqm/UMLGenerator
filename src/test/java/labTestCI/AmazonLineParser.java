package labTestCI;

public class AmazonLineParser implements ILineParser {

    @Override
    public String parse(String line) {
//		aws1 ttl 100, aws2 ttl 450
        String[] sp = line.split(" ");
        return sp[0] + " : " + sp[2].substring(0, sp[2].length() - 1) + "\n" + sp[3] + " : " + sp[5];
    }

}
