package model;

public class Logger {
    private static boolean verbose;

    public static void setVerbose(boolean verbose) {
        Logger.verbose = verbose;
    }

    public static void logError(String error) {
        if (verbose) {
            System.err.println(error);
        }
    }
}
