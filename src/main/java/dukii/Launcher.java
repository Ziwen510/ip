package dukii;

/**
 * Launcher class to workaround JavaFX classpath issues with fat JARs.
 */
public final class Launcher {
    private Launcher() { }

    public static void main(String[] args) {
        Main.main(args);
    }
}


