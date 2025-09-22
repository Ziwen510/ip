package dukii;

/**
 * Launcher class to workaround JavaFX classpath issues with fat JARs.
 */
public final class Launcher {
    private Launcher() { }

    /**
     * Delegates to {@link Main#main(String[])} to launch JavaFX.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Main.main(args);
    }
}


