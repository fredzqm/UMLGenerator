package config;

public class TestNoArgs {

    public static void main(String[] args) throws Exception {
        CommandLineParser c = new CommandLineParser("-e exepath -d outdir -o outfile -x extension -f public -k -n 10 -r me".split(" "));
        System.out.println(c.create());
    }

}
