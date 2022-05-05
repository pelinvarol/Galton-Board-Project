import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;


public class GaltonBoard {
    @Option(name = "-numBins")
    static int numBins = 20;

    @Option(name = "-numThread")
    static int numThread = 30000;
    int sumOfBinValues = 0;

    public static void main(String[] args) {

        GaltonBoard galtonBoard = new GaltonBoard();
        CmdLineParser cmdLineParser = new CmdLineParser(galtonBoard);
        try {
            cmdLineParser.parseArgument(args);
            galtonBoard.run();
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());

        }

    }

    void run() {

        int[] galtonBoard = new int[numBins];

        ReleaseBalls(numThread, galtonBoard);
        System.out.println(Arrays.toString(galtonBoard));
        System.out.println("Number of requested & total threads:  " + Arrays.stream(galtonBoard).sum());

        for (int i = 0; i < galtonBoard.length; i++) {
            sumOfBinValues += galtonBoard[i];
        }
        System.out.println("Sum of bin values: " + sumOfBinValues);

        if (Arrays.stream(galtonBoard).sum() == sumOfBinValues) {
            System.out.println("Nice work! Both of them are equal!");
        } else {
            System.out.println("The total number of threads and sum of bin values are not equal! Please look again " +
                    "and correct the mistake!");
        }


    }

    static void ReleaseBalls(int n, int[] galtonBoard) {
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Main t = new Main(galtonBoard);
            Thread threads = new Thread(t);
            threadList.add(threads);
            threads.start();
        }
        try {
            for (Thread t : threadList) {
                t.join();
            }
        } catch (Exception ignored) {
            System.out.println(ignored);
        }

    }
}

class Main implements Runnable {
    int[] galtonBoard;


    public Main(int[] galtonBoard) {
        this.galtonBoard = galtonBoard;
    }

    public void run() {
        int result = 0;
        for (int i = 0; i < galtonBoard.length - 1; i++) {
            result += (int) Math.round(Math.random());
        }
        synchronized (galtonBoard) {
            galtonBoard[result] += 1;

        }

    }

}